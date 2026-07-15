package ex09.reactor.step1;

// ── Reactive Streams 계약 3종 (라이브러리 없이 직접) ──
interface Publisher<T> {
    void subscribe(Subscriber<? super T> s);
}

interface Subscriber<T> {
    void onSubscribe(Subscription s); // ① 계약을 받는다

    void onNext(T item); // ② 값이 흐른다 (여러 번)

    void onError(Throwable t); // 에러로 끝

    void onComplete(); // ③ 정상 종료
}

interface Subscription {
    void request(long n); // 받을 만큼 요청

    void cancel(); // 그만 받기
}

// 계약(Subscription)을 클래스로 — Publisher와 Subscriber 사이의 약속을 담는다
class SimpleSubscription implements Subscription {
    private final Subscriber<? super Integer> sub; // 누구에게 신호를 보낼 계약인지

    SimpleSubscription(Subscriber<? super Integer> sub) {
        this.sub = sub;
    }

    @Override
    public void request(long n) {
        // "달라"고 하면 1,2,3 내보내고 완결
        sub.onNext(1);
        sub.onNext(2);
        sub.onNext(3);
        sub.onComplete();
    }

    @Override
    public void cancel() {
    } // 이 예제에선 안 씀
}

// 가장 단순한 Publisher: 계약을 만들어 소비자에게 건넨다
class SimplePublisher implements Publisher<Integer> {
    @Override
    public void subscribe(Subscriber<? super Integer> sub) {
        sub.onSubscribe(new SimpleSubscription(sub)); // 계약 생성 → onSubscribe로 전달
    }
}

// 가장 단순한 Subscriber: "전부 주세요"라고만 함
class SimpleSubscriber implements Subscriber<Integer> {
    @Override
    public void onSubscribe(Subscription s) {
        // request(n) = "n개까지 받을게"라는 빚(demand). Long.MAX_VALUE는 922경(long 최대값) →
        // 사실상 무제한이라 빚이 마르지 않는다 = "몇 개든 다 밀어줘, 난 안 막아".
        // request(3)처럼 적으면 3개 받고 멈춘다(백프레셔). 여기선 그 조절을 안 하니 demand 계산도 불필요.
        s.request(Long.MAX_VALUE); // ★ "다 줘" = backpressure 사실상 끔 (push 주도)
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("onNext: " + item);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("onError: " + t);
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete!");
    }
}

public class App {
    public static void main(String[] args) {
        Publisher<Integer> pub = new SimplePublisher();
        Subscriber<Integer> sub = new SimpleSubscriber();
        pub.subscribe(sub); // 이 한 줄이 전부. 신호가 알아서 흐른다.
    }
}