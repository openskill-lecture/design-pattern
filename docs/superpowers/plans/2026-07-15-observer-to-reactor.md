# 옵저버 → Reactor (`ex09/reactor/`) — 숫자 최소 버전

> 강의 예제. 테스트 프레임워크 없음 — 검증 = UTF-8 컴파일 + 실행 + 기대 출력 대조.

**Goal:** 옵저버 예제를, 라이브러리 없이 손으로 구현한 Reactive Streams(Publisher/Subscriber/Subscription)로 진화시켜 숫자 1~5 최소 예제로 보여준다. `step1`(3신호, push)·`step2`(백프레셔 request(n), pull).

**Architecture:** `NumberPublisher`가 1~5를 흘리고 `PrintSubscriber`가 출력. step1은 구독하자마자 다 밀어붙이고(push) 끝나면 `onComplete`. step2는 emit 루프를 `request(n)` 안으로 옮겨 요청한 만큼만 흘린다(pull). 도메인 없음 — 표준 메서드 영어, 출력·주석 한글. 동기·단일 실행(결정적 출력).

## Global Constraints

- 패키지명 경로 그대로: `package ex09.reactor.step1;` / `step2;`.
- 한 step = `App.java` 한 파일(인터페이스 + NumberPublisher + PrintSubscriber + main).
- 빌드/실행 UTF-8: `javac -encoding UTF-8 -d bin ...`, `java -Dfile.encoding=UTF-8 -cp bin ...`.
- 클래스 영어 PascalCase, 표준 메서드 영어(`subscribe`/`onSubscribe`/`onNext`/`onError`/`onComplete`/`request`/`cancel`), 출력·주석 한글.
- 값은 정수 1~5. 재귀 회피 — 요청은 `onSubscribe`에서 한 번만. main은 구독 한 번(단일 실행).

---

### Task 1: `ex09/reactor/step1` — 3신호 (push)

**Files:** Create `src/ex09/reactor/step1/App.java`

**Interfaces (Produces):** `Publisher<T>.subscribe(Subscriber<T>)`, `Subscriber<T>` = `onSubscribe`/`onNext`/`onError`/`onComplete`, `Subscription.cancel()`. step2는 이 구조에 `request(long n)`만 추가.

- [ ] **Step 1: `App.java` 작성** — 아래 전체 코드.

```java
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
```

- [ ] **Step 2: 컴파일** — `javac -encoding UTF-8 -d bin $(find src/ex09/reactor/step1 -name App.java)` → 무출력, exit 0.
- [ ] **Step 3: 실행 대조** — `java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step1.App`:
```
구독 시작
받음: 1
받음: 2
받음: 3
받음: 4
받음: 5
완료!
```
- [ ] **Step 4: 커밋** (source만 add).

---

### Task 2: `ex09/reactor/step2` — 백프레셔 request(n) (pull)

**Files:** Create `src/ex09/reactor/step2/App.java`

**Interfaces:** Consumes Task 1과 동일 구조. Produces `Subscription` = `request(long n)` + `cancel()`; emit 루프가 `request` 안으로 이동, `next` 커서로 이어보냄.

- [ ] **Step 1: `App.java` 작성** — step1 대비 변화: ① `Subscription`에 `request(long n)` 추가, ② `subscribe()`에서 `start()` 제거, ③ emit 루프를 `request` 안으로 옮기고 `n`으로 게이팅, ④ `PrintSubscriber`가 `onSubscribe`에서 `request`.

```java
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
```

- [ ] **Step 2: 컴파일** — `javac -encoding UTF-8 -d bin $(find src/ex09/reactor/step2 -name App.java)` → 무출력, exit 0.
- [ ] **Step 3: 실행 대조** — `java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step2.App`:
```
구독 시작 → 3개 요청
받음: 1
받음: 2
받음: 3
```
(5개 중 3개만 요청 → 4·5는 보류, 완료 없음)

- [ ] **Step 4: 커밋** (source만 add).

---

## 구현 결과 (반영됨)

- 커밋 `1147e43`에서 두 파일 모두 숫자 버전으로 작성, 컴파일 클린, 위 기대 출력과 일치.
- `git push origin main` 완료(`3e8859c..1147e43`).
