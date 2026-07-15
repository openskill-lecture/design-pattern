package ex09.reactor.step2;

// 완걸형
// ── 라이브러리 없이 직접 정의한 Reactive Streams 계약 3종 ──
interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber); // 실제 reactive-streams와 동일: ? super T
}

interface Subscriber<T> {
    void onSubscribe(Subscription subscription); // 구독권을 넘겨받는다

    void onNext(T item); // 값이 흐른다 — 여러 번

    void onError(Throwable error); // 에러로 끝

    void onComplete(); // 정상 종료
}

interface Subscription {
    void request(long n); // 받을 만큼만 요청한다

    void cancel(); // 구독자가 흐름을 끊는다
}

// 완결형(cold, 유한): 1..max 를 내보내고 스스로 onComplete
class RangePublisher implements Publisher<Integer> {
    private final int max;

    RangePublisher(int max) {
        this.max = max;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> s) {
        s.onSubscribe(new RangeSubscription(s, max));
    }
}

class RangeSubscription implements Subscription {
    private final Subscriber<? super Integer> sub;
    private final int max;
    private int next = 1;
    private boolean cancelled = false;

    RangeSubscription(Subscriber<? super Integer> sub, int max) {
        this.sub = sub;
        this.max = max;
    }

    @Override
    public void request(long n) { // 당긴 만큼만 발행
        for (long i = 0; i < n && !cancelled; i++) {
            if (next > max) {
                sub.onComplete();
                return;
            } // 데이터 소진 → 스스로 종료
            sub.onNext(next++);
        }
    }

    @Override
    public void cancel() {
        cancelled = true;
    }
}

class MySubscriber implements Subscriber<Integer> {
    private Subscription subscription; // 계약을 "보관만" (자동 request 안 함)

    public Subscription upstream() {
        return subscription;
    } // main이 직접 당기게 노출

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        System.out.println("onSubscribe: 계약 수령 (아직 request 안 함 → 데이터 0개)");
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
        MySubscriber sub = new MySubscriber();
        new RangePublisher(5).subscribe(sub); // subscribe → onSubscribe만. 데이터 0개.

        Subscription s = sub.upstream();
        s.request(2); // onNext 1, 2
        s.request(2); // onNext 3, 4
        s.request(2); // onNext 5 → next>max → onComplete
    }
}