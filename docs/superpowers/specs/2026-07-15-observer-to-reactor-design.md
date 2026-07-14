# 옵저버 → Reactor: `ex09/reactor/` 설계 (숫자 최소 버전)

## 목표

ex09 옵저버 패턴 예제를 출발점으로, **옵저버의 `update(String)` 한 신호가
Reactive Streams 표준으로 진화하는 과정**을 보여준다. 라이브러리 없이 표준
인터페이스(Publisher/Subscriber/Subscription)를 손으로 구현한다.

도메인은 걷어낸다. 마트/손님/좌석/상품별 구독은 개념(3신호·백프레셔)을 가리므로,
**숫자 1~5를 흘려보내는 가장 앙상한 예제**로 간다.

진화 축:
```
옵저버 update() 하나
  → 흐름의 세 신호(onNext 여러 번 · onError · onComplete)          [step1, push]
  → 구독자가 받을 만큼만 당겨오는 백프레셔(request(n))              [step2, pull]
```

## 배치

```
src/ex09/reactor/
  step1/App.java   3신호 (push)
  step2/App.java   + 백프레셔 request(n) (pull)
```
패키지명은 경로 그대로. 각 step은 `App.java` 한 파일에 인터페이스 + 발행자 + 구독자 + main.

## 공통 인터페이스 (Reactive Streams 표준을 손으로)

도메인이 없으므로 표준 메서드는 전부 영어, 출력·주석은 한글.

| 인터페이스 | step1 | step2 |
|-----------|-------|-------|
| `Publisher<T>` | `subscribe(Subscriber<T>)` | 동일 |
| `Subscriber<T>` | `onSubscribe(Subscription)` · `onNext(T)` · `onError(Throwable)` · `onComplete()` | 동일 |
| `Subscription` | `cancel()` | `cancel()` + **`request(long n)`** |

발행자 `NumberPublisher`가 1~5를 흘리고, 구독자 `PrintSubscriber`가 받아서 출력한다.

## step1 — 3신호 (push)

- `subscribe()`가 `Subscription`을 `onSubscribe`로 넘겨주고, **곧바로 1~5를 밀어붙인다**(push).
  다 보내면 `onComplete()`.
- **판서 포인트**: 옵저버 `update()` 하나 → 흐름엔 `onNext`(여러 번)와 `onComplete`(끝)가 있다.
  `onError`·`cancel`은 표준 계약으로 선언(코드/주석).
- 한 번만 실행하므로 step1은 **완료까지 가는 생애주기**를 보여준다(cancel은 코드에만; 실제
  중단 시연은 백프레셔가 주인공인 step2와 겹치지 않도록 생략).

기대 출력:
```
구독 시작
받음: 1
받음: 2
받음: 3
받음: 4
받음: 5
완료!
```

## step2 — + 백프레셔 `request(n)` (pull)

- 핵심 변화 하나: step1에서 `subscribe()`가 곧바로 돌리던 **emit 루프가 `Subscription.request(n)`
  안으로 이사**한다. `subscribe()`는 더 이상 자동으로 안 보낸다.
- 구독자가 `onSubscribe`에서 `request(3)` → **요청한 3개만** 흐른다. 4·5는 요청이 없어 보류(완료도 없음).
- **판서 포인트**: step1은 구독만 하면 5개가 자동으로 왔지만(push), step2는 요청해야 온다(pull).
  "요청 안 하면 안 흘러간다."

기대 출력:
```
구독 시작 → 3개 요청
받음: 1
받음: 2
받음: 3
```

## 실행/출력 규칙

- **동기 방식**·**단일 실행**(main에서 구독 한 번). 출력이 결정적이라 판서가 쉽다.
- 재귀(onNext 안에서 다시 request) 함정을 피한다 — 요청은 `onSubscribe`에서 한 번만.
- 클래스=영어 PascalCase, 표준 메서드=영어, 출력/주석=한글.

## 빌드

```bash
javac -encoding UTF-8 -d bin $(find src/ex09/reactor -name App.java)
java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step1.App
java -Dfile.encoding=UTF-8 -cp bin ex09.reactor.step2.App
```

## 스코프 밖 (YAGNI)

- 실제 Project Reactor 라이브러리(Flux/Mono) 미사용.
- 연산자(map/filter) 체이닝 미포함.
- 멀티스레드/비동기 스케줄러 미포함. 다중 구독자·도메인(마트 등) 미포함.
