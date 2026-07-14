package ex09.reactor.step1;

// 옵저버 update() 하나가, 흐름의 세 신호로 진화한다 — 값(onNext 여러 번)·끝(onComplete)·에러(onError).
// 그리고 구독은 손님이 쥐고 스스로 끊는다(cancel).

import java.util.ArrayList;
import java.util.List;

// 흐르는 값: 상품명 + 수량
class Product {
    final String name;
    final int qty;

    Product(String name, int qty) {
        this.name = name;
        this.qty = qty;
    }

    @Override
    public String toString() {
        return name + " " + qty + "박스";
    }
}

// ── Reactive Streams 표준을 손으로 ─────────────────────────
interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);
}

interface Subscriber<T> {
    void onSubscribe(Subscription subscription); // 구독 제어권을 넘겨받는다
    void onNext(T item);                          // 값이 흐른다 — 여러 번
    void onError(Throwable error);                // 흐름이 에러로 끝
    void onComplete();                            // 흐름이 정상 종료
}

interface Subscription {
    void cancel();                                // 손님이 스스로 구독을 끊는다
}
// ──────────────────────────────────────────────────────────

class Mart implements Publisher<Product> {

    // 손님별 좌석: 구독자 + 취소 여부. (자기 자신이 Subscription 역할)
    private static class Seat implements Subscription {
        final Subscriber<Product> subscriber;
        boolean cancelled = false;

        Seat(Subscriber<Product> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void cancel() {
            cancelled = true;
        }
    }

    private final List<Seat> seats = new ArrayList<>();

    @Override
    public void subscribe(Subscriber<Product> subscriber) {
        Seat seat = new Seat(subscriber);
        seats.add(seat);
        // 구독과 동시에, 손님에게 '구독을 끊을 수 있는 제어권'을 넘긴다.
        subscriber.onSubscribe(seat);
    }

    // 상품이 시간차로 여러 번 들어온다 → onNext 를 여러 번, 끝나면 onComplete
    public void 입고(Product... products) {
        for (Product product : products) {
            System.out.println("[마트] " + product + " 입고!");
            for (Seat seat : seats) {
                if (!seat.cancelled) {
                    seat.subscriber.onNext(product);
                }
            }
            System.out.println("----------------------------");
        }
        // 더 들어올 상품이 없다 → 흐름 종료를 알린다 (완료는 흐름의 신호다)
        System.out.println("[마트] 오늘 영업 종료!");
        for (Seat seat : seats) {
            if (!seat.cancelled) {
                seat.subscriber.onComplete();
            }
        }
    }
}

class Customer implements Subscriber<Product> {
    private final String name;
    private final int cancelAfter;        // 몇 개 받고 끊을지 (0이면 끝까지 받음)
    private Subscription subscription;
    private int received = 0;
    private int totalBoxes = 0;

    Customer(String name, int cancelAfter) {
        this.name = name;
        this.cancelAfter = cancelAfter;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription; // 제어권을 보관해 둔다
        System.out.println(name + " : 구독 시작");
    }

    @Override
    public void onNext(Product item) {
        received++;
        totalBoxes += item.qty;
        System.out.println(name + " : " + item + " 받음 (누적 " + totalBoxes + "박스)");
        if (cancelAfter > 0 && received >= cancelAfter) {
            System.out.println(name + " : 그만 받을래요 → 구독 취소");
            subscription.cancel();
        }
    }

    @Override
    public void onError(Throwable error) {
        System.out.println(name + " : 에러로 흐름 종료 — " + error.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println(name + " : 영업 종료 신호 받음 (최종 " + totalBoxes + "박스)");
    }
}

public class App {
    public static void main(String[] args) {
        Mart mart = new Mart();

        Customer customer1 = new Customer("손님1", 0); // 끝까지 받는다
        Customer customer2 = new Customer("손님2", 1); // 1개만 받고 끊는다
        Customer customer3 = new Customer("손님3", 0);

        mart.subscribe(customer1);
        mart.subscribe(customer2);
        mart.subscribe(customer3);

        System.out.println("============================");
        mart.입고(
                new Product("포도", 3),
                new Product("사과", 5),
                new Product("바나나", 2)
        );
    }
}
