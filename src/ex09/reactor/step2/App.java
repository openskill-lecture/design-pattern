package ex09.reactor.step2;

// step1: 구독하자마자 발행자가 1~5 를 다 밀어붙였다(push).
// step2: 구독자가 request(n) 한 만큼만 흐른다(pull) — 요청 안 하면 안 온다.

interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);
}

interface Subscriber<T> {
    void onSubscribe(Subscription subscription); // 구독권을 넘겨받는다
    void onNext(T item);                          // 값이 흐른다 — 여러 번
    void onError(Throwable error);                // 에러로 끝
    void onComplete();                            // 정상 종료
}

interface Subscription {
    void request(long n);                         // 받을 만큼만 요청한다
    void cancel();                                // 구독자가 흐름을 끊는다
}

// 요청받은 만큼만 1~5 를 흘리는 발행자
class NumberPublisher implements Publisher<Integer> {

    @Override
    public void subscribe(Subscriber<Integer> subscriber) {
        subscriber.onSubscribe(new NumberSubscription(subscriber));
        // start() 없음 — 이제 구독자가 request 해야 흐른다
    }

    static class NumberSubscription implements Subscription {
        private final Subscriber<Integer> subscriber;
        private int next = 1;                      // 다음에 보낼 숫자
        private boolean cancelled = false;

        NumberSubscription(Subscriber<Integer> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {             // 요청받은 개수만큼만 보낸다
            for (long i = 0; i < n && next <= 5 && !cancelled; i++) {
                subscriber.onNext(next++);
            }
            if (next > 5 && !cancelled) {
                subscriber.onComplete();          // 다 보냈으면 끝 신호
            }
        }

        @Override
        public void cancel() {
            cancelled = true;
        }
    }
}

class PrintSubscriber implements Subscriber<Integer> {
    private final long requestCount;

    PrintSubscriber(long requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("구독 시작 → " + requestCount + "개 요청");
        subscription.request(requestCount);       // 이만큼만 받을게요
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("받음: " + item);
    }

    @Override
    public void onError(Throwable error) {
        System.out.println("에러: " + error.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("완료!");
    }
}

public class App {
    public static void main(String[] args) {
        Publisher<Integer> publisher = new NumberPublisher();
        publisher.subscribe(new PrintSubscriber(3)); // 5개 중 3개만 요청
    }
}
