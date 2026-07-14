# 옵저버 → Reactor (`ex09/reactor/`) Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking. When creating the example code, follow the repo's `lec-code` skill conventions.

**Goal:** ex09 옵저버 예제를, 라이브러리 없이 손으로 구현한 Reactive Streams 표준(Publisher/Subscriber/Subscription)으로 진화시켜 `ex09/reactor/step1`(3신호+구독제어)·`step2`(백프레셔 request(n))로 보여준다.

**Architecture:** 마트=Publisher, 손님=Subscriber. step1은 `onNext`(여러 번)/`onError`/`onComplete` 세 신호와 손님이 쥔 `cancel()`. step2는 `Subscription`에 `request(long n)`을 더해 손님이 받을 만큼만 당기는 pull 균형. 두 step은 최대한 diff 가능한 평행 구조(둘 다 `Seat implements Subscription`).

**Tech Stack:** 순수 Java (JDK), plain `javac`/`java`, 테스트 프레임워크 없음. 검증 = UTF-8 컴파일 + 실행 + 기대 출력 대조.

## Global Constraints

- 패키지명은 경로 그대로: `package ex09.reactor.step1;`, `package ex09.reactor.step2;`.
- 한 step = `App.java` 한 파일(인터페이스 + 마트 + 손님 + main 전부).
- 컴파일/실행은 **반드시 UTF-8**: `javac -encoding UTF-8 -d bin ...`, `java -Dfile.encoding=UTF-8 -cp bin ...`.
- 네이밍: 클래스=영어 PascalCase, Reactive Streams 표준 메서드=영어 그대로(`subscribe`/`onNext`/`onError`/`onComplete`/`onSubscribe`/`request`/`cancel`), 도메인 행위=한글(`입고`), 변수=영어 카멜(`customer1`).
- 흐르는 값은 `Product(String name, int qty)`, `toString()` → `"포도 3박스"`.
- 라이브러리(Flux/Mono)·연산자(map)·스레드 미사용. emit는 동기(결정적 출력).
- 섹션 구분선 `----------------------------`로 신호 흐름을 판서용으로 구분.

---

### Task 1: `ex09/reactor/step1` — 핵심 3신호 + 구독제어

**Files:**
- Create: `src/ex09/reactor/step1/App.java`

**Interfaces:**
- Produces: `Publisher<T>.subscribe(Subscriber<T>)`, `Subscriber<T>` = `onSubscribe(Subscription)`/`onNext(T)`/`onError(Throwable)`/`onComplete()`, `Subscription.cancel()`, `Product(String name, int qty)`. Task 2는 이 구조를 그대로 복제하고 `Subscription`에 `request(long n)`만 추가한다.

- [ ] **Step 1: `App.java` 작성 (전체 코드)**

```java
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
```

- [ ] **Step 2: 컴파일 (에러 없어야 함)**

Run: `javac -encoding UTF-8 -d bin $(find src/ex09/reactor/step1 -name App.java)`
Expected: 출력 없음(성공), 종료코드 0.

- [ ] **Step 3: 실행 후 기대 출력 대조**

Run: `java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step1.App`
Expected (손님2는 포도 1개만 받고 취소 → 사과/바나나/onComplete 모두 못 받음):

```
손님1 : 구독 시작
손님2 : 구독 시작
손님3 : 구독 시작
============================
[마트] 포도 3박스 입고!
손님1 : 포도 3박스 받음 (누적 3박스)
손님2 : 포도 3박스 받음 (누적 3박스)
손님2 : 그만 받을래요 → 구독 취소
손님3 : 포도 3박스 받음 (누적 3박스)
----------------------------
[마트] 사과 5박스 입고!
손님1 : 사과 5박스 받음 (누적 8박스)
손님3 : 사과 5박스 받음 (누적 8박스)
----------------------------
[마트] 바나나 2박스 입고!
손님1 : 바나나 2박스 받음 (누적 10박스)
손님3 : 바나나 2박스 받음 (누적 10박스)
----------------------------
[마트] 오늘 영업 종료!
손님1 : 영업 종료 신호 받음 (최종 10박스)
손님3 : 영업 종료 신호 받음 (최종 10박스)
```

- [ ] **Step 4: 커밋**

```bash
git add src/ex09/reactor/step1/App.java
git commit -m "ex09 리액터: step1 옵저버→Reactive Streams 3신호+구독제어(cancel)"
```

---

### Task 2: `ex09/reactor/step2` — 백프레셔 `request(n)`

**Files:**
- Create: `src/ex09/reactor/step2/App.java`

**Interfaces:**
- Consumes: Task 1과 동일한 표준 인터페이스 구조(`Publisher`/`Subscriber`/`Product`).
- Produces: `Subscription` = `request(long n)` + `cancel()`; `Mart.Seat`가 `demand` 잔량을 추적해 요청받은 만큼만 `onNext`.

- [ ] **Step 1: `App.java` 작성 (전체 코드)**

step1 대비 변경점은 세 곳뿐: ① `Subscription`에 `request(long n)` 추가, ② `Seat`가 `demand`를 갖고 요청 잔량이 있을 때만 emit, ③ `Customer`가 `onSubscribe`/`onNext`에서 `request`.

```java
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
    void onSubscribe(Subscription subscription);
    void onNext(T item);
    void onError(Throwable error);
    void onComplete();
}

interface Subscription {
    void request(long n);                         // 받을 만큼만 요청 — 이만큼 흘려보내라
    void cancel();
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
        this.subscription = subscription;
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
```

- [ ] **Step 2: 컴파일 (에러 없어야 함)**

Run: `javac -encoding UTF-8 -d bin $(find src/ex09/reactor/step2 -name App.java)`
Expected: 출력 없음(성공), 종료코드 0.

- [ ] **Step 3: 실행 후 기대 출력 대조**

Run: `java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step2.App`
Expected (손님1은 2개 받고 요청 소진 → 바나나/딸기 건너뜀; 손님2는 1개씩 당겨 끝까지):

```
손님1 : 구독 시작 → 2개 요청(request 2)
손님2 : 구독 시작 → 1개 요청(request 1)
============================
[마트] 포도 3박스 입고!
손님1 : 포도 3박스 받음 (누적 3박스)
손님2 : 포도 3박스 받음 (누적 3박스)
손님2 : 하나 더 주세요(request 1)
----------------------------
[마트] 사과 5박스 입고!
손님1 : 사과 5박스 받음 (누적 8박스)
손님2 : 사과 5박스 받음 (누적 8박스)
손님2 : 하나 더 주세요(request 1)
----------------------------
[마트] 바나나 2박스 입고!
   (손님1 요청 소진 — 건너뜀)
손님2 : 바나나 2박스 받음 (누적 10박스)
손님2 : 하나 더 주세요(request 1)
----------------------------
[마트] 딸기 4박스 입고!
   (손님1 요청 소진 — 건너뜀)
손님2 : 딸기 4박스 받음 (누적 14박스)
손님2 : 하나 더 주세요(request 1)
----------------------------
[마트] 오늘 영업 종료!
손님1 : 영업 종료 신호 받음 (최종 8박스)
손님2 : 영업 종료 신호 받음 (최종 14박스)
```

- [ ] **Step 4: 커밋**

```bash
git add src/ex09/reactor/step2/App.java
git commit -m "ex09 리액터: step2 백프레셔 request(n) — push→pull 균형"
```

---

## Self-Review

**Spec coverage:**
- 진화 축(3신호+구독제어 → 백프레셔) → Task 1, Task 2 ✓
- 배치 `src/ex09/reactor/step{1,2}/App.java` → 각 Task의 Create 경로 ✓
- 공통 인터페이스(Publisher/Subscriber/Subscription, step2에 request 추가) → 두 Task 코드에 반영 ✓
- 흐르는 값 `Product(name, qty)` + 누적 표시 → 두 Task 코드 ✓
- step1 cancel 시나리오(손님2 1개만) / step2 request 대비(손님1 멈춤·손님2 계속) → 기대 출력에 명시 ✓
- 동기 emit·구분선·UTF-8 빌드 → Global Constraints + Step 명령 ✓
- YAGNI(라이브러리·연산자·스레드 없음) → 코드에 미포함 ✓

**Placeholder scan:** TBD/TODO/"적절히 처리" 없음. 모든 코드·명령·기대 출력이 실제 값 ✓

**Type consistency:** `subscribe`/`onSubscribe`/`onNext`/`onError`/`onComplete`/`cancel`/`request`/`입고`/`Product`/`Seat`/`demand` 명칭이 두 Task에서 일치. step2는 step1에 `request(long n)`과 `demand`만 추가하는 상위집합 ✓
