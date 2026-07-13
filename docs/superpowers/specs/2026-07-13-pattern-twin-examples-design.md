# ex05~ex14 패턴별 "쌍둥이 예제" 추가 — 설계 문서

## 배경 / 목적

`src/exNN/`는 회차별 디자인패턴 강의 예제다. 현재 각 회차는 **패턴 교육 예제 1개(step 진화형)**
와 **연습문제(exercise)** 로 구성된다. 실측 수업 시간:

- 패턴 예제(step1~2/3) 설명: ~20분
- 연습문제 풀이: ~30분
- 개념 설명: ~20분
- **합계 ~70분 → 2시간을 못 채운다.**

**해결**: ex05~ex14 각 패턴에 **강의용 2번째 예제("쌍둥이 예제")를 하나씩** 추가해
회당 20~30분을 더 확보한다. ex15는 제외.

## 원칙 (반드시 지킬 것)

- **A안 — 같은 패턴, 새 도메인.** 기존 예제와 **동일한 step 진화(순진 → 함정 → 패턴)**,
  동일한 house style을 유지하고 **이야기(도메인)만 새로** 바꾼다. "같은 모양, 다른 이야기"로 반복 학습.
- **한 주제만.** 그 회차 패턴만 설명한다. OCP·SOLID 등 다른 개념을 끌어들이지 않는다.
- **실무 완벽함보다 설명력.** 강의자료다. 패턴이 눈에 확 들어오는 게 최우선.
- **연습문제는 만들지 않는다.** 이미 존재. 순수 강의용 2번째 예제만 추가.
- **house style** (CLAUDE.md 준수):
  - 클래스명 파스칼·영어(`Charger`), 메서드명 한글 도메인 행위(`충전한다()`), 변수명 카멜·영어(`amount`)
  - getter는 `getX()` 영어 관례 허용
  - 클래스/파일 위 한 줄 주석 = 그 step이 던지는 질문/상태
  - step2/step3의 "착한 길 / 뚫리는 지점"은 `----------------------------` 구분선으로 분리 출력
  - 뚫리는 지점엔 결과를 콕 집는 주석 (`// 통과 ← 뚫린다!`)
  - 한글 주석·출력이므로 **컴파일/실행은 UTF-8** (`javac -encoding UTF-8`, 필요시 `-Dfile.encoding=UTF-8`)
  - 각 예제 폴더는 `stepN/App.java` 하나, 패키지명은 경로 그대로 (`package ex05.charger.step1;`)

## 폴더 배치

각 새 예제는 기존 교육 예제와 **나란한 형제 폴더**로 둔다. 연습문제(exercise)는 건드리지 않는다.

```
src/ex05/charger/step1/App.java, step2/App.java        (기존 ex05/adapter 옆)
src/ex06/spooler/step1..step3/App.java
src/ex07/cook/step1..step3/App.java
src/ex08/office/step1..step2/App.java
src/ex09/stock/step1..step3/App.java
src/ex10/gift/step1..step3/App.java
src/ex11/weather/step1..step3/App.java
src/ex12/signup/step1..step3/App.java
src/ex13/shape/step1..step2/App.java     (simple factory 변형)
src/ex13/money/step1..step2/App.java     (static factory 변형)
src/ex14/bungeoppang/step1..step3/App.java   (ex14에 없던 "메인 교육 예제" 신설, monster 연습은 유지)
```

## 새 예제 11개 명세

각 예제는 기존 짝의 step 진화 뼈대를 그대로 따른다. "핵심 대비"는 실행 출력으로 드러낸다.

### ex05 · Adapter — `charger` (2 step)
기존 짝: 외부 토끼(`getFullname`)를 `Animal(getName)` 규격에 맞춤.
- **도메인**: 노트북은 `TypeC` 규격 통로로만 충전(`충전한다()`). 빌려온 외부 충전기는 우리가 못 고치고
  `둥근핀충전()` 메서드만 있다(`lib/` 외부 라이브러리로 분리).
- **step1**: 노트북 충전 포트는 `TypeC`만 받는다. 외부 충전기는 `TypeC`도 아니고 메서드도 `둥근핀충전()`.
  → 못 꽂는다(주석 풀면 컴파일 에러). "어떻게 끼울까? (step2)"
- **step2**: `ChargerAdapter`가 `TypeC`인 척 규격을 뒤집어쓰고, 안에서 외부 충전기의 `둥근핀충전()`을 부른다.
  → 노트북 눈엔 그냥 `TypeC`. 규격 변환 성공.

### ex06 · Singleton — `spooler` (3 step)
기존 짝: 문지기 금지명단이 하나여야 함.
- **도메인**: 회사 프린터 대기열(`PrintSpooler`)은 하나여야 한다. 큐가 갈리면 문서가 누락/뒤섞인다.
  `대기열` = `List<String>`.
- **step1**: 생성자가 열려 여기저기 `new PrintSpooler()`. A가 등록한 문서를 B 큐가 모른다 → 출력 누락.
  `== ` false.
- **step2**: `static instance`를 내놨지만 **생성자를 안 막음**(착한 길은 통하나, `new`로 딴 큐를 또 만들면 뚫림).
  → 빈 큐가 생겨 문서가 사라진다. 싱글톤이 깨진다.
- **step3**: `private` 생성자 + `getInstance()`. 큐는 언제나 하나. 우회할 문이 없다.

### ex07 · Template Method — `cook` (3 step)
기존 짝: 선생님 `수업하기()` 골격(입장→출석→강의→퇴장).
- **도메인**: 라면집. 조리 흐름 = `물끓이기()→면넣기()→비법()→그릇담기()`. 변하는 스텝은 `비법()`(가게마다 다름).
- **step1**: 가게마다 조리 흐름을 통째로 복붙. 한 가게가 `면넣기()`를 깜빡 → 아무도 안 잡아줌.
- **step2**: 흐름을 부모 `Cook`으로 끌어올림. 그러나 `끓이기()`가 `final`이 아니고 스텝이 `public` →
  자식이 골격을 갈아엎거나(순서 붕괴) 스텝을 빈 껍데기로 오버라이드(조용히 누락). (함정)
- **step3**: `조리하기()`를 `final`로 잠그고 고정 스텝은 `protected`, `비법()`만 `abstract`.
  자식은 비법 한 칸만 채운다. 골격을 깰 문이 없다.

### ex08 · Delegation — `office` (2 step)
기존 짝: 점원(`Waiter`)이 요리를 주방장(`Chef`)에게 위임.
- **도메인**: 사장(`Boss`)이 일정을 잡는다. 실제 예약 전화는 비서(`Secretary`)가 한다.
- **step1**: 사장이 `일정잡기()` 안에서 예약 전화까지 직접 건다. 역할이 너무 많다.
- **step2**: 사장은 `Secretary`를 필드로 들고 `secretary.예약전화()`로 위임. 사장은 예약 방법을 몰라도 된다.
  손님 → 사장(위임자) → 비서(대리 수행자).

### ex09 · Observer — `stock` (3 step)
기존 짝: 마트(`Mart`)가 입고 시 손님에게 알림.
- **도메인**: 증권사(`StockCenter`)가 시세 변동 시 투자자(`Investor`)에게 알림. 시세는 '나중에' 바뀐다(스레드).
- **step1**: 투자자가 1초마다 시세를 직접 물어본다(폴링). 헛물음 낭비.
- **step2**: 증권사가 알려준다(푸시). 그러나 투자자를 필드로 직접 박아둠(`investor1`, `investor2`) → 강결합.
  투자자 추가/구독취소하려면 증권사를 뜯어고쳐야 함.
- **step3**: 증권사는 '구독 명단'(`List<Investor>`)만 안다. 투자자는 인터페이스(통로)로 약속만 지킴.
  추가·취소해도 증권사 코드는 그대로.

### ex10 · Decorator — `gift` (3 step)
기존 짝: 기본 알림을 채널(문자·이메일)이 감싸 덧붙임.
- **도메인**: 선물(`BasicGift`)을 상자·포장지·리본으로 **감싼다**. `포장한다()`가 안쪽을 먼저 처리하고 자기를 덧붙임.
- **step1**: 기본 선물 하나. 여기에 상자·리본을 같이 씌우고 싶어진다.
- **step2**: 조합마다 클래스를 새로 판다(`BoxRibbonGift`, `WrapRibbonGift`, `BoxWrapRibbonGift`…) →
  포장 하나 늘 때마다 클래스가 2배씩 폭발. "리본만 빼줘"가 안 됨(런타임 변경 불가).
- **step3**: 데코레이터. `Gift` 인터페이스 + `GiftDecorator`. `RibbonGift(new BoxGift(new BasicGift()))`처럼
  런타임 자유 조합. 순서 바꾸든 빼든 새 클래스 불필요.

### ex11 · Proxy(Stub) — `weather` (3 step)
기존 짝: 만보기 진짜/가짜(Stub) 병렬 개발.
- **도메인**: 외부 날씨 API 팀은 30일 걸린다. 나(화면)는 먼저 개발해야 한다. `Weather.getTemp()`.
- **step1**: 화면 서비스가 `RealWeather`에 직접 `new`로 묶임 → 진짜가 아직 없어 `getTemp()`에서 터짐. 대기.
- **step2**: 계약(`Weather` 인터페이스)만 합의. `FakeWeather`(Stub)가 정해진 값(예: 20) 리턴.
  서비스는 생성자 주입으로 계약에만 의존 → 진짜 없이 병렬 개발. (Stub = return 값이 핵심)
- **step3**: `RealWeather` 완성. 서비스 코드 0줄 수정, `new FakeWeather()→new RealWeather()`만 교체.
  같은 계약을 지켰기 때문.

### ex12 · Mock — `signup` (3 step)
기존 짝: 주문/결제 Mock (호출 횟수 → 호출 순서 검증).
- **도메인**: 회원가입 서비스가 저장소(`UserRepository`)를 호출해 회원을 저장한다.
- **step1**: 서비스 안에 '진짜 저장'이 박혀 있다 → 테스트마다 진짜 DB에 쓰인다. 자동 테스트 불가.
- **step2**: 저장소를 계약(`UserRepository`)으로 뽑고 `MockRepository`가 `save()` **호출 횟수(count)** 기록.
  버그: 가입 한 번에 `save()`를 두 번 호출(중복 저장). 결과값만 보면 통과처럼 보이나 count로 적발. (= Mock)
- **step3**: 규칙은 '중복검사 → 저장' 순서. Mock이 **호출 순서**를 기록. 버그: 저장을 먼저 함 →
  count로는 못 잡는 순서 버그를 순서 기록으로 적발.

### ex13 · Simple Factory — `shape` (2 step)
기존 짝: DB 팩토리(MariaDB/OracleDB).
- **도메인**: 도형. 공통 통로 `Shape` 인터페이스(`그린다()`), 구현 `Circle`/`Rectangle`.
- **step1**: 클라이언트가 구체 타입을 직접 알고 `new Circle()` + 초기화까지 떠안는다. 바꾸면 클라를 또 고침.
- **step2**: `ShapeFactory.create(ShapeType.CIRCLE)`로 생성을 한 곳에 모음(`enum` + if).
  클라는 무엇을 만들지만 고르고 `Shape`로 받는다. 어떻게 만드는지는 모른다.

### ex13 · Static Factory — `money` (2 step)
기존 짝: `User.student()/staff()` 이름붙인 정적 팩토리.
- **도메인**: 돈. `Money`가 금액과 통화를 가진다.
- **step1**: `new Money(1000, "KRW")` 생성자가 열려 오타 통화("KWR")도 그대로 생성 → 실행까지 통과하는 버그.
- **step2**: 생성자를 `private`으로 감추고 `Money.won(1000)` / `Money.dollar(10)` 이름붙인 정적 팩토리로만 생성.
  오타는 아예 컴파일 불가. 매직 스트링·if 분기가 부르는 쪽에서 사라진다.

### ex14 · Factory Method — `bungeoppang` (3 step)
기존: `ex14/exercise/monster`는 발견형 4-step **연습**만 존재(별도 교육 예제 없음). → 짧은 교육 예제 신설.
- **도메인**: 붕어빵 틀. 굽는 흐름 = `문연다→반죽굽기(반죽만들기())→건넨다`. 반죽 종류(팥/슈크림)는 가게마다 다름.
  제품 인터페이스 `Bungeoppang`(`먹는다()`), 구현 `PatBung`/`CreamBung`.
- **step1**: 가게(`PatShop`/`CreamShop`)마다 굽는 흐름을 복붙. 차이는 `new PatBung()` 한 줄. 흐름 대사 바꾸면 여러 곳 수정.
- **step2**: 흐름을 부모 `BungeoppangShop`으로 끌어올림. 그러나 `new PatBung()`을 **부모가 하드코딩** →
  `CreamShop`도 팥붕어빵이 나온다(함정). 자식이 바꿀 수 없다.
- **step3**: `반죽만들기()`를 `abstract`로, 흐름은 부모가 고정. 자식이 무엇을 만들지 정함(동적바인딩).
  이제 `CreamShop`이 진짜 슈크림을 낸다. → 팩토리 메서드.

## 빌드 / 검증

각 예제 구현 후 UTF-8로 컴파일하고 각 step을 실행해 의도한 출력(특히 "뚫리는 지점")이 나오는지 확인한다.

```bash
javac -encoding UTF-8 -d bin $(find src/exNN/<domain> -name App.java)
java -Dfile.encoding=UTF-8 -cp bin exNN.<domain>.stepN.App
```

성공 기준: 11개 예제 33개 step 파일 모두 컴파일·실행되고, step1/step2의 문제 상황과
마지막 step의 해결이 실행 출력으로 확인된다.
