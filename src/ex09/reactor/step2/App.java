package ex09.reactor.step2;

// step1: 마트가 밀어붙였다(push) — 손님이 감당 못해도 다 왔다.
// step2: 손님이 request(n)으로 '받을 만큼만' 당긴다(pull) — 요청 안 하면 안 흘러간다.

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
    void request(long n);                         // 받을 만큼만 요청 — 이만큼 흘려보내라
    void cancel();                                // 손님이 스스로 구독을 끊는다
}
// ──────────────────────────────────────────────────────────

class Mart implements Publisher<Product> {

    // 손님별 좌석: 구독자 + 요청 잔량(demand) + 취소 여부.
    private static class Seat implements Subscription {
        final Subscriber<Product> subscriber;
        long demand = 0;                          // 손님이 아직 받겠다고 요청한 개수
        boolean cancelled = false;

        Seat(Subscriber<Product> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            demand += n;                          // 요청은 쌓인다
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
        subscriber.onSubscribe(seat);             // 손님이 이 좌석으로 request/cancel 한다
    }

    // 상품이 시간차로 여러 번 들어온다 → onNext 를 여러 번, 끝나면 onComplete
    public void 입고(Product... products) {
        for (Product product : products) {
            System.out.println("[마트] " + product + " 입고!");
            for (Seat seat : seats) {
                if (seat.cancelled) {
                    continue;
                }
                if (seat.demand > 0) {            // 요청 잔량이 있을 때만 흘린다
                    seat.demand--;
                    seat.subscriber.onNext(product);
                } else {                          // 요청이 없으면 이 손님에겐 안 흘러간다
                    System.out.println("   (" + seat.subscriber + " 요청 소진 — 건너뜀)");
                }
            }
            System.out.println("----------------------------");
        }
        // 더 들어올 상품이 없다 → 흐름 종료를 알린다 (완료는 흐름의 신호다)
        System.out.println("[마트] 오늘 영업 종료!");
        for (Seat seat : seats) {
            if (!seat.cancelled) {
                seat.subscriber.onComplete();     // 완료는 요청 잔량과 무관한 종료 신호
            }
        }
    }
}

class Customer implements Subscriber<Product> {
    private final String name;
    private final int initialRequest;   // 구독하며 처음 요청할 개수
    private final boolean keepRequesting; // onNext마다 하나씩 더 당길지
    private Subscription subscription;
    private int totalBoxes = 0;

    Customer(String name, int initialRequest, boolean keepRequesting) {
        this.name = name;
        this.initialRequest = initialRequest;
        this.keepRequesting = keepRequesting;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription; // 제어권을 보관해 둔다
        System.out.println(name + " : 구독 시작 → " + initialRequest + "개 요청(request " + initialRequest + ")");
        subscription.request(initialRequest);
    }

    @Override
    public void onNext(Product item) {
        totalBoxes += item.qty;
        System.out.println(name + " : " + item + " 받음 (누적 " + totalBoxes + "박스)");
        if (keepRequesting) {
            System.out.println(name + " : 하나 더 주세요(request 1)");
            subscription.request(1);
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

    @Override
    public String toString() {
        return name;
    }
}

public class App {
    public static void main(String[] args) {
        Mart mart = new Mart();

        // 손님1: 2개만 요청하고 더 안 당김 → 2개 받고 멈춘다
        Customer customer1 = new Customer("손님1", 2, false);
        // 손님2: 1개 요청 후 받을 때마다 1개씩 더 당김 → 끝까지 흐른다
        Customer customer2 = new Customer("손님2", 1, true);

        mart.subscribe(customer1);
        mart.subscribe(customer2);

        System.out.println("============================");
        mart.입고(
                new Product("포도", 3),
                new Product("사과", 5),
                new Product("바나나", 2),
                new Product("딸기", 4)
        );
    }
}
