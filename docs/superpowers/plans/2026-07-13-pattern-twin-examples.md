# ex05~ex14 패턴별 쌍둥이 예제 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking. **각 예제의 Java 작성은 저장소 `lec-code` 스킬을 사용한다.**

**Goal:** ex05~ex14 각 패턴에 "같은 패턴·새 도메인"의 강의용 2번째 예제(step 진화형)를 하나씩 추가해 회당 수업 시간을 20~30분 늘린다.

**Architecture:** 기존 교육 예제와 나란한 형제 폴더에 `stepN/App.java`를 둔다. 각 예제는 기존 짝의 step 진화 뼈대(순진 → 함정 → 패턴)와 house style을 그대로 따르고 도메인만 바꾼다. 검증은 UTF-8 컴파일 + 각 step 실행 + 출력 육안 확인.

**Tech Stack:** Java (순수, 외부 의존성 없음), `javac -encoding UTF-8`, 실행 `java -Dfile.encoding=UTF-8`.

**설계 문서:** `docs/superpowers/specs/2026-07-13-pattern-twin-examples-design.md` — 각 예제 step 상세는 이 스펙의 "새 예제 11개 명세"를 SSOT로 삼는다.

## Global Constraints

- 패키지명은 폴더 경로 그대로: `package exNN.<domain>.stepN;`
- 클래스명 파스칼·영어 / 메서드명 한글 도메인 행위 / 변수명 카멜·영어 / getter는 `getX()` 허용
- 클래스·파일 위 한 줄 주석 = 그 step이 던지는 질문/상태
- step2/step3의 "착한 길 / 뚫리는 지점"은 `----------------------------` 구분선으로 분리 출력, 뚫리는 지점엔 결과를 콕 집는 주석
- **한 주제만** — 그 회차 패턴만. OCP·SOLID 등 다른 개념 금지
- 실무 완벽함보다 **설명력** 우선 (강의자료)
- 컴파일/실행은 **UTF-8**. 각 파일은 `public class App { public static void main(...) }` 하나
- 연습문제(exercise), ex15, 기존 예제 폴더는 **건드리지 않는다**
- 커밋 메시지 말미: `Co-Authored-By: Claude Opus 4.8 (1M context) <noreply@anthropic.com>`

## 공통 검증 절차 (모든 Task 공통)

각 예제 폴더 완성 후:

```bash
# 컴파일 (해당 예제만)
javac -encoding UTF-8 -d bin $(find src/exNN/<domain> -name App.java)
# 각 step 실행 후 출력 확인
java -Dfile.encoding=UTF-8 -cp bin exNN.<domain>.step1.App
java -Dfile.encoding=UTF-8 -cp bin exNN.<domain>.step2.App
# (step3 있으면) ...
```

**통과 기준:** 컴파일 에러 0, 각 step이 실행되고, step1/step2의 문제 상황과 마지막 step의 해결이 Task별 "기대 출력"대로 나온다. 한글이 깨지지 않는다.

---

### Task 1: ex05 Adapter — `charger` (2 step)

**Files:**
- Create: `src/ex05/charger/lib/OuterCharger.java` (외부 라이브러리, `둥근핀충전()`만 있음 — 우리가 못 고침)
- Create: `src/ex05/charger/step1/App.java`
- Create: `src/ex05/charger/step2/App.java`

**Interfaces (도메인 계약):**
- `TypeC` (abstract, `충전한다()`) / 구현 `PhoneCharger` 등 정상 케이스 1개
- `Laptop`(또는 `ChargePort`)이 `TypeC`만 받는다
- step2: `ChargerAdapter extends TypeC` — 내부에 `OuterCharger`를 품고 `충전한다()`→`둥근핀충전()` 변환

**참조:** 스펙 "ex05 · Adapter — charger". 기존 짝 `src/ex05/adapter` 구조를 그대로 따른다(외부 lib 분리, step1 컴파일 에러 주석, step2 어댑터).

- [ ] **Step 1:** `lec-code` 스킬로 `lib/OuterCharger.java` + step1/step2 작성 (스펙 step beats 준수)
- [ ] **Step 2:** 컴파일 — `javac -encoding UTF-8 -d bin $(find src/ex05/charger -name App.java)` → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 정상 충전 출력 후 구분선, "외부 충전기는 규격이 안 맞아 못 꽂는다 → step2" 취지 출력
  - step2: 정상 충전 + 어댑터로 감싼 외부 충전기가 `TypeC`처럼 충전되는 출력
- [ ] **Step 4:** 커밋 — `git add src/ex05/charger && git commit -m "ex05 어댑터: charger 쌍둥이 예제 추가 (step1~2)"`

---

### Task 2: ex06 Singleton — `spooler` (3 step)

**Files:**
- Create: `src/ex06/spooler/step1/App.java`, `step2/App.java`, `step3/App.java`

**Interfaces (도메인 계약):**
- `PrintSpooler` — `대기열`(`List<String>`), `문서등록(String)`, `출력()`
- step1: 생성자 열림, 여러 개 `new` → 큐 분리
- step2: `static PrintSpooler instance = new PrintSpooler();` + 생성자 안 막음 (뚫림)
- step3: `private` 생성자 + `private static final instance` + `getInstance()`

**참조:** 스펙 "ex06 · Singleton — spooler". 기존 짝 `src/ex06/singleton` 3-step 뼈대(step2가 함정) 그대로.

- [ ] **Step 1:** `lec-code` 스킬로 step1~3 작성
- [ ] **Step 2:** 컴파일 (`src/ex06/spooler`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 다른 큐라 등록 문서가 사라짐, `==` false
  - step2: 착한 길(같은 instance)은 통함 / 구분선 / `new`로 만든 딴 큐에서 문서 누락, 싱글톤 깨짐
  - step3: `new` 컴파일 에러 주석, 언제나 같은 큐(`==` true)
- [ ] **Step 4:** 커밋 — `git commit -m "ex06 싱글톤: spooler 쌍둥이 예제 추가 (step1~3)"`

---

### Task 3: ex07 Template Method — `cook` (3 step)

**Files:**
- Create: `src/ex07/cook/step1/App.java`, `step2/App.java`, `step3/App.java`

**Interfaces (도메인 계약):**
- 흐름 = `물끓이기()→면넣기()→비법()→그릇담기()`, 변하는 스텝 `비법()`
- step2: 부모 `Cook`, `조리하기()`가 `final` 아님 + 스텝 `public` → 골격 갈아엎기 / 빈 껍데기 오버라이드
- step3: `조리하기()` `final`, 고정 스텝 `protected`, `비법()` `abstract`

**참조:** 스펙 "ex07 · Template Method — cook". 기존 짝 `src/ex07/template` 뼈대. **beverage/workout(연습문제 도메인) 회피 완료.**

- [ ] **Step 1:** `lec-code` 스킬로 step1~3 작성
- [ ] **Step 2:** 컴파일 (`src/ex07/cook`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 한 가게가 `면넣기` 깜빡 → 흐름 제각각
  - step2: 착한 자식 정상 / 골격 뚫는 자식(순서 붕괴)·빈 껍데기 자식(조용한 누락)
  - step3: `조리하기` 오버라이드 시 컴파일 에러 주석, 자식은 `비법`만 채움
- [ ] **Step 4:** 커밋 — `git commit -m "ex07 템플릿 메서드: cook 쌍둥이 예제 추가 (step1~3)"`

---

### Task 4: ex08 Delegation — `office` (2 step)

**Files:**
- Create: `src/ex08/office/step1/App.java`, `step2/App.java`

**Interfaces (도메인 계약):**
- step1: `Boss.일정잡기(String)` 안에서 예약 전화까지 직접
- step2: `Secretary`(`예약전화(String)`), `Boss`가 `private Secretary secretary = new Secretary();` 필드로 위임

**참조:** 스펙 "ex08 · Delegation — office". 기존 짝 `src/ex08/delegate` 2-step 뼈대(위임자→대리 수행자).

- [ ] **Step 1:** `lec-code` 스킬로 step1~2 작성
- [ ] **Step 2:** 컴파일 (`src/ex08/office`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 사장이 일정+예약전화 혼자
  - step2: 사장은 `일정잡기`만, 예약전화는 비서에게 위임 / "손님 → 사장(위임자) → 비서(대리)" 취지 주석
- [ ] **Step 4:** 커밋 — `git commit -m "ex08 델리게이트: office 쌍둥이 예제 추가 (step1~2)"`

---

### Task 5: ex09 Observer — `stock` (3 step)

**Files:**
- Create: `src/ex09/stock/step1/App.java`, `step2/App.java`, `step3/App.java`

**Interfaces (도메인 계약):**
- `StockCenter`(시세 변동은 스레드로 '나중에'), `Investor`(`알림받기(String)`)
- step1: 투자자가 1초마다 `getPrice()` 폴링
- step2: `StockCenter`가 `investor1/investor2` 필드로 직접 앎 (강결합, 취소 불가)
- step3: `List<Investor>` 구독 명단 + `Investor` 인터페이스(통로), `구독()/구독취소()`

**참조:** 스펙 "ex09 · Observer — stock". 기존 짝 `src/ex09/observer` 3-step 뼈대(폴링→푸시 강결합→구독 명단). **youtube(연습) 회피 완료.**

- [ ] **Step 1:** `lec-code` 스킬로 step1~3 작성
- [ ] **Step 2:** 컴파일 (`src/ex09/stock`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: "아직 안 왔어요?" 류 헛물음 반복 후 도착
  - step2: 푸시는 되나 구독취소 불가 취지 출력
  - step3: 구독취소한 투자자는 알림 못 받고 남은 투자자만 받음
- [ ] **Step 4:** 커밋 — `git commit -m "ex09 옵저버: stock 쌍둥이 예제 추가 (step1~3)"`

---

### Task 6: ex10 Decorator — `gift` (3 step)

**Files:**
- Create: `src/ex10/gift/step1/App.java`, `step2/App.java`, `step3/App.java`

**Interfaces (도메인 계약):**
- step1: `BasicGift.포장한다()`
- step2: 조합 클래스 폭발 (`BoxRibbonGift`, `WrapRibbonGift`, `BoxWrapRibbonGift`…)
- step3: `Gift` 인터페이스 + `BasicGift` + `abstract GiftDecorator`(감쌀 대상 필드) + `BoxGift`/`WrapGift`/`RibbonGift`

**참조:** 스펙 "ex10 · Decorator — gift". 기존 짝 `src/ex10/notification` 뼈대(조합 폭발 → 데코레이터). **pizza(연습) 회피 완료.**

- [ ] **Step 1:** `lec-code` 스킬로 step1~3 작성
- [ ] **Step 2:** 컴파일 (`src/ex10/gift`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step2: 전체 포장은 조합 클래스로 되나 "리본만 빼줘"가 클래스 교체 필요
  - step3: `new RibbonGift(new BoxGift(new BasicGift()))` 런타임 조합, 순서/생략 자유
- [ ] **Step 4:** 커밋 — `git commit -m "ex10 데코레이터: gift 쌍둥이 예제 추가 (step1~3)"`

---

### Task 7: ex11 Proxy(Stub) — `weather` (3 step)

**Files:**
- Create: `src/ex11/weather/step1/App.java`, `step2/App.java`, `step3/App.java`

**Interfaces (도메인 계약):**
- step1: `RealWeather.getTemp()`가 "아직 개발 중" 예외, `WeatherService`가 `new RealWeather()`로 직접 묶임
- step2: `interface Weather { int getTemp(); }`, `FakeWeather`(Stub, 정해진 값 리턴), `WeatherService`는 생성자 주입
- step3: `RealWeather implements Weather`, 서비스 0줄 수정하고 주입만 교체

**참조:** 스펙 "ex11 · Proxy(Stub) — weather". 기존 짝 `src/ex11/meter` 뼈대(병렬 개발, Stub=return 값이 핵심).

- [ ] **Step 1:** `lec-code` 스킬로 step1~3 작성
- [ ] **Step 2:** 컴파일 (`src/ex11/weather`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 진짜 없어서 터지고 "병렬 개발 안 됨"
  - step2: Fake로 화면 돎 (병렬 개발)
  - step3: Fake→Real 주입만 바꿔 실제 값 출력, 서비스 코드 동일
- [ ] **Step 4:** 커밋 — `git commit -m "ex11 프록시(Stub): weather 쌍둥이 예제 추가 (step1~3)"`

---

### Task 8: ex12 Mock — `signup` (3 step)

**Files:**
- Create: `src/ex12/signup/step1/App.java`, `step2/App.java`, `step3/App.java`

**Interfaces (도메인 계약):**
- step1: `SignupService` 안에 '진짜 저장' 박힘 → 테스트마다 진짜 DB 쓰기
- step2: `interface UserRepository { void save(String); }`, `MockRepository`가 `count` 기록. 버그: `save()` 2회 호출(중복 저장). 결과값만 보면 통과, count로 적발
- step3: `MockRepository`가 호출 **순서** 기록. 규칙 '중복검사 → 저장'. 버그: 저장 먼저 → 순서로 적발

**참조:** 스펙 "ex12 · Mock — signup". 기존 짝 `src/ex12/order` 뼈대(Stub과의 차이=호출 기록, count→순서). **mail(연습)과 도메인 구분됨.**

- [ ] **Step 1:** `lec-code` 스킬로 step1~3 작성
- [ ] **Step 2:** 컴파일 (`src/ex12/signup`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 진짜 저장 발생, 자동 테스트 불가 취지
  - step2: `[실패] 중복 저장 적발! save가 2번` 류 출력 (count로 잡음)
  - step3: `[실패] 순서 뒤바뀜 적발` 류 출력 (순서로 잡음, count로는 못 잡음)
- [ ] **Step 4:** 커밋 — `git commit -m "ex12 목(Mock): signup 쌍둥이 예제 추가 (step1~3)"`

---

### Task 9: ex13 Simple Factory — `shape` (2 step)

**Files:**
- Create: `src/ex13/shape/step1/App.java`, `step2/App.java`

**Interfaces (도메인 계약):**
- `interface Shape { void 그린다(); }`, `Circle`/`Rectangle`
- step1: 클라가 `new Circle()` + 초기화 직접
- step2: `enum ShapeType { CIRCLE, RECTANGLE }`, `ShapeFactory.create(ShapeType)` (if 분기), 클라는 `Shape`로 받음

**참조:** 스펙 "ex13 · Simple Factory — shape". 기존 짝 `src/ex13/simplefactory` 2-step 뼈대. 파일 상단 주석에 "simple factory 변형" 명시.

- [ ] **Step 1:** `lec-code` 스킬로 step1~2 작성
- [ ] **Step 2:** 컴파일 (`src/ex13/shape`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 구체 타입 직접 new, 바꾸면 클라 수정
  - step2: `factory.create(ShapeType.CIRCLE)`만 바꿔 교체, 생성 절차는 팩토리 안
- [ ] **Step 4:** 커밋 — `git commit -m "ex13 심플 팩토리: shape 쌍둥이 예제 추가 (step1~2)"`

---

### Task 10: ex13 Static Factory — `money` (2 step)

**Files:**
- Create: `src/ex13/money/step1/App.java`, `step2/App.java`

**Interfaces (도메인 계약):**
- `Money`(금액 `amount`, 통화 `currency`), `getAmount()/getCurrency()`
- step1: `public Money(int, String)` 열림 → 오타 통화("KWR")도 생성됨 (실행까지 통과하는 버그)
- step2: `private` 생성자 + `static Money won(int)` / `static Money dollar(int)`

**참조:** 스펙 "ex13 · Static Factory — money". 기존 짝 `src/ex13/staticfactory` 2-step 뼈대(오타 버그 → 이름붙인 정적 팩토리). 파일 상단 주석에 "static factory 변형" 명시.

- [ ] **Step 1:** `lec-code` 스킬로 step1~2 작성
- [ ] **Step 2:** 컴파일 (`src/ex13/money`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: `new Money(1000,"KWR")` 오타가 그대로 생성/출력됨
  - step2: `Money.won(1000)`/`Money.dollar(10)`, 오타는 컴파일 불가 주석
- [ ] **Step 4:** 커밋 — `git commit -m "ex13 정적 팩토리: money 쌍둥이 예제 추가 (step1~2)"`

---

### Task 11: ex14 Factory Method — `bungeoppang` (3 step)

**Files:**
- Create: `src/ex14/bungeoppang/step1/App.java`, `step2/App.java`, `step3/App.java`

**Interfaces (도메인 계약):**
- `interface Bungeoppang { void 먹는다(); }`, `PatBung`/`CreamBung`
- 흐름 = `문연다()→반죽굽기(반죽만들기())→건넨다()`
- step1: `PatShop`/`CreamShop`가 굽는 흐름 복붙, 차이는 `new PatBung()` 한 줄
- step2: 부모 `BungeoppangShop`이 흐름 쥐지만 `new PatBung()` 하드코딩 → `CreamShop`도 팥붕어빵 나옴 (함정)
- step3: `abstract Bungeoppang 반죽만들기();` 자식이 정함, 흐름은 부모 고정 (동적바인딩)

**참조:** 스펙 "ex14 · Factory Method — bungeoppang". ex14의 **신설 메인 교육 예제** (monster 발견형 연습은 그대로 둠). 기존 monster의 팩토리 메서드 논리를 짧은 교육 예제로 재구성.

- [ ] **Step 1:** `lec-code` 스킬로 step1~3 작성
- [ ] **Step 2:** 컴파일 (`src/ex14/bungeoppang`) → 에러 0
- [ ] **Step 3:** 실행 확인
  - step1: 흐름 복붙, 대사 하나 바꾸려면 여러 곳 수정 취지
  - step2: `CreamShop` 소환했는데 팥붕어빵이 나옴 (함정 확인)
  - step3: `CreamShop`이 진짜 슈크림붕어빵을 냄 (팩토리 메서드 완성)
- [ ] **Step 4:** 커밋 — `git commit -m "ex14 팩토리 메서드: bungeoppang 교육 예제 신설 (step1~3)"`

---

### Task 12: 전체 회귀 확인

**Files:** (없음 — 검증만)

- [ ] **Step 1:** 새 예제 전체 일괄 컴파일
  ```bash
  javac -encoding UTF-8 -d bin $(find src/ex05/charger src/ex06/spooler src/ex07/cook src/ex08/office src/ex09/stock src/ex10/gift src/ex11/weather src/ex12/signup src/ex13/shape src/ex13/money src/ex14/bungeoppang -name App.java)
  ```
  Expected: 에러 0
- [ ] **Step 2:** 기존 예제가 깨지지 않았는지 확인 — 기존 폴더는 수정 안 했으므로 전체 재컴파일로 확인
  ```bash
  javac -encoding UTF-8 -d bin $(find src -name App.java)
  ```
  Expected: 에러 0
- [ ] **Step 3:** 계획/스펙 문서와 실제 폴더 일치 확인 (11개 예제, 33개 step 파일 — ex13 2개는 각 2 step)
- [ ] **Step 4:** 최종 커밋 없음(각 Task에서 커밋 완료). 필요 시 브랜치 정리.

## Self-Review (작성자 확인 완료)

- **Spec coverage:** 스펙 "새 예제 11개 명세" 11개 항목 → Task 1~11 각 1:1 대응. ex13 2변형 = Task 9, 10. ex14 신설 = Task 11. ✅
- **Placeholder scan:** 각 Task에 파일 경로·도메인 계약·기대 출력·커밋 명시. Java 전체 코드는 스펙이 SSOT이고 `lec-code` 스킬로 작성(강의 예제 특성상 로직이 스펙에 고정). ✅
- **Type consistency:** 도메인 계약의 클래스/메서드명이 스펙과 일치. house style(한글 메서드, 영어 클래스/변수) 통일. ✅
- **테스트 사이클 적응:** 단위 테스트 대신 "UTF-8 컴파일 → step 실행 → 출력 확인"을 각 Task의 test 사이클로 사용(강의 예제에 적합). ✅
