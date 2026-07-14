# 옵저버 → Reactor: `ex09/reactor/` 설계

## 목표

ex09 옵저버 패턴 예제(`observerpattern/step2`, 기본 push 옵저버)를 출발점으로,
**옵저버의 `update(String)` 한 신호가 Reactive Streams 표준으로 진화하는 과정**을
같은 마트/손님 도메인으로 보여준다. 라이브러리 없이 표준 인터페이스를 손으로 구현한다.

진화 축:
```
옵저버 update() 하나
  → 흐름의 세 신호(onNext 여러 번 · onError · onComplete) + 구독제어(cancel)   [step1]
  → 손님이 받을 만큼만 당겨오는 백프레셔(request(n))                              [step2]
```

## 배치

```
src/ex09/reactor/
  step1/App.java   핵심 3신호 + 구독제어
  step2/App.java   + 백프레셔 request(n)
```
패키지명은 경로 그대로: `package ex09.reactor.step1;`, `package ex09.reactor.step2;`
각 step은 `App.java` 한 파일에 인터페이스 + 마트 + 손님 + main 을 모두 담는다(observerpattern과 동일한 결).

## 공통 인터페이스 (Reactive Streams 표준을 손으로)

표준 스펙 메서드명은 영어 그대로, 도메인 행위(`입고`)는 한글 — observerpattern 네이밍 결 유지.

| 인터페이스 | step1 | step2 |
|-----------|-------|-------|
| `Publisher<T>` | `subscribe(Subscriber<T>)` | 동일 |
| `Subscriber<T>` | `onSubscribe(Subscription)` · `onNext(T)` · `onError(Throwable)` · `onComplete()` | 동일 |
| `Subscription` | `cancel()` | `cancel()` + **`request(long n)`** |

## 흐르는 값

상품명 + 수량. 값이 도드라지도록 문자열이 아니라 작은 타입으로.

```java
class Product {
    final String name;   // "포도"
    final int qty;       // 3 (박스)
    // toString → "포도 3박스"
}
```
손님은 받은 수량을 누적해, cancel/백프레셔로 **덜 받은 손님**이 출력에서 드러나게 한다.

## step1 — 핵심 3신호 + 구독제어

- `Mart implements Publisher<Product>`: `입고()`가 상품 목록을 순회하며 **여러 번 `onNext`**,
  다 끝나면 **`onComplete()`**(영업종료), 문제가 나면 **`onError()`**.
- `subscribe()`가 손님별 `Subscription`을 만들어 `onSubscribe`로 넘겨준다.
  손님은 이 `Subscription`을 보관했다가 `cancel()` 호출 → 그 손님만 이후 `onNext`가 끊긴다.
- **대비**: 옵저버의 `remove()`(주체가 지움)가 → 손님 손에 쥐어진 `cancel()`(구독자가 스스로 끊음)로 진화.
- **판서 포인트**: `update()` 하나 → 흐름엔 값·끝·에러 세 신호가 있다. 구독은 손님이 끊는다.
- **실행 시나리오**: 손님2가 첫 상품을 받은 직후 `cancel()` → 3개 중 1개만 받고 끊긴 게 출력으로 보임.
  나머지 손님은 전체 + `onComplete`까지 받음.

## step2 — + 백프레셔 `request(n)`

- `Subscription`에 `request(long n)` 추가. 마트는 손님별 **요청 잔량(demand)만큼만** `onNext`.
- 손님이 `onSubscribe`에서 `request(2)`처럼 먼저 요청해야 흐른다. 요청 잔량이 0이면 그 손님에겐 안 흘러감.
- **자체 대비**(step2 안에서): 손님A는 `request(2)` 한 번만 → 2개 받고 멈춤.
  손님B는 `onNext`마다 `request(1)` → 흐름이 계속 이어짐.
- **판서 포인트**: step1은 마트가 **밀어붙임**(push, 손님이 감당 못해도 다 옴) → step2는 손님이 **당긴다**(pull 균형).
  "요청 안 하면 안 흘러간다."

## 실행/출력 규칙

- **동기 방식**으로 emit — 출력이 결정적이라 판서가 쉽다(observerpattern step2의 `new Thread`와 달리 스레드 없음).
- `App.main`: 마트 1개 + 손님 2~3명. 섹션 구분선(`----`)으로 신호 흐름을 눈에 보이게.
- 한글 주석/식별자 허용. 클래스=영어 PascalCase, 표준 메서드=영어, 도메인 행위=한글.

## 빌드

```bash
javac -encoding UTF-8 -d bin $(find src/ex09/reactor -name App.java)
java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step1.App
java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step2.App
```

## 스코프 밖 (YAGNI)

- 실제 Project Reactor 라이브러리(Flux/Mono) 미사용.
- 연산자(map/filter) 체이닝 미포함.
- 멀티스레드/비동기 스케줄러 미포함(백프레셔는 동기 pull 모델로만 시연).
