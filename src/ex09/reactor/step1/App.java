package ex09.reactor.step1;

// 옵저버 update() 하나가, 흐름의 세 신호로 진화한다 — onNext(여러 번)·onError·onComplete.
// 구독자는 onSubscribe 로 받은 구독권으로 흐름을 제어한다(cancel).

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
    void cancel();                                // 구독자가 흐름을 끊는다
}

// 1~5 를 구독하자마자 그냥 밀어붙이는(push) 발행자
class NumberPublisher implements Publisher<Integer> {

    @Override
    public void subscribe(Subscriber<Integer> subscriber) {
        NumberSubscription subscription = new NumberSubscription(subscriber);
        subscriber.onSubscribe(subscription);
        subscription.start();                     // 구독하자마자 다 보낸다
    }

    static class NumberSubscription implements Subscription {
        private final Subscriber<Integer> subscriber;
        private boolean cancelled = false;

        NumberSubscription(Subscriber<Integer> subscriber) {
            this.subscriber = subscriber;
        }

        void start() {
            for (int i = 1; i <= 5; i++) {
                if (cancelled) return;            // 끊겼으면 멈춘다 (완료도 안 보냄)
                subscriber.onNext(i);
            }
            subscriber.onComplete();              // 다 보냈으면 끝 신호
        }

        @Override
        public void cancel() {
            cancelled = true;
        }
    }
}

class PrintSubscriber implements Subscriber<Integer> {

    @Override
    public void onSubscribe(Subscription subscription) {
        // 구독권을 받는다. 원하면 subscription.cancel() 로 흐름을 끊을 수 있다.
        System.out.println("구독 시작");
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
        publisher.subscribe(new PrintSubscriber());
    }
}
