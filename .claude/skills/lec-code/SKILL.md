---
name: lec-code
description: Use when creating or extending 강의 예제(lecture example) code under `src/` in this repo (ex01, ex02, …) — scaffolding an example as a multi-step progression (step 폴더) that contrasts 코드의 상태 변화 and shows the difference through 실행 출력. Topic-agnostic — OOP는 여러 예시 중 하나일 뿐 강제가 아니다. Triggers on requests to add lecture examples, a new 예제/원칙 folder, or step folders following the repo's step-structure and 네이밍 conventions.
---

# lec-code — 강의 예제 생성

## Overview

강의 예제는 **한 파일에 bad/good을 나란히 두지 않는다.** `step` 폴더로 **코드의 상태 변화**를 쪼개,
순서대로 열고 실행하며 "무엇이 나아졌고, 무엇이 아직 문제인가"를 보여준다.

**핵심은 step 개수나 특정 주제(OOP 등)가 아니라, 각 step의 차이를 실행 출력으로 드러내는 것이다.**
step 구조·주제·네이밍의 구체적 형태는 예제마다 다를 수 있다. 아래 규칙은 그 뼈대이고,
`ex01`의 OOP 예제는 이 뼈대를 적용한 **하나의 예시**일 뿐이다.

## PLAN
코드는 절대 먼저 작성하지 않는다. 어떻게 할지 계획을 세우고, 승인을 받아야한다.

## 코드만 생성한다.
개념 설명은 PPT에서 하기 때문에 강의용 코드만 만들어주면 된다.

## When to use / not

- **쓴다**: 새 강의 예제(`src/exNN/...`)를 step 폴더 진화/대비로 스캐폴딩 / 기존 예제에 step 추가.
- **안 쓴다**: 강의 예제가 아닌 일반 자바 코드, 라이브러리, 프로덕션 로직, 테스트 프레임워크.

## step 구조는 자유다 (강제 아님)

step 개수와 의미는 **예제가 무엇을 보여주려는지에 따라 정한다.** 대표 패턴:

| 패턴 | step | 예 |
|------|------|-----|
| 3-step 진화 | 시작(그냥) → 문제/함정 → 완벽 | 필드 열림 → 뚫림 → private로 닫힘 |
| 잘못 고침 대비 | 시작 예제 → 잘못 고친 예제 → 잘 고친 예제 | 순진한 수정이 왜 나쁜지 보여준 뒤 제대로 |
| 2-step 비교 | A vs B (두 예제 대조) | 절차형 vs 객체형, 전 vs 후 |
| 그 외 | 필요한 만큼 | 주제에 맞게 자유 |

공통 원칙:
- 각 step은 **하나의 상태**를 담는다. 순서대로 열며 대비한다.
- 마지막(또는 대비 대상) step은 **실행 출력으로 차이를** 보여준다.
- "함정/누수/문제"가 있는 예제라면 그 문제를 **실행 출력으로** 드러낸다 (말로만 X).
- **step 개수를 억지로 3개로 맞추지 않는다.** 2개면 2개, 4개면 4개.

## 디렉터리 · 패키지 · 파일 규칙 (기술 뼈대, 주제 무관)

- 경로: `src/exNN/<group>/<name>/stepK/App.java`
  (`<group>`·`<name>`·step 개수는 예제 따라. 예: `ex01/oop/p2_invariant/step2`, `ex01/mem/step1`.)
- **패키지명 = 폴더 경로 그대로**: 폴더가 `ex01/oop/p2_invariant/step2`면 `package ex01.oop.p2_invariant.step2;`
- **파일 레이아웃**: `package` 줄 → 빈 줄 → package-private 도우미 클래스(`class Foo`, `public` 아님)를 **위에** 선언 → 그 아래 **단 하나의** `public class App`(main). 한 파일, 별도 클래스 파일 없음. `App`만 public.
- 컴파일 산출물은 `bin/` (VS Code 기본).

## 네이밍 규칙 (ex01 공통)

`ex01` 예제는 주제와 무관하게 아래 규칙을 공통으로 쓴다. 다른 회차(exNN)는 그 회차 관례를 따르되, 정해진 게 없으면 이 규칙을 기본값으로 쓴다.

| 대상 | 규칙 | 예 |
|------|------|-----|
| 클래스명 | 파스칼 · 영어 | `Account`, `CopyMachine`, `ColorPrinter` |
| 메서드명 | **한글** 도메인 행위 | `출금()`, `지불한다()`, `달린다()`, `인쇄()` |
| 변수/필드 | 카멜 · 영어 | `balance`, `amount`, `colorMachine` |

- 메서드는 **행위(일을 시키는 이름)**를 한글로. setter식 이름 대신 의미 있는 행위명(`출금()`).
- **getter 예외**: 결과 출력용 접근자는 자바 관례대로 영어 `getX()` 허용 (`getBalance()`, `getPrice()`).
- 변수/필드에는 한글을 쓰지 않는다 (`int 금액` → `int amount`).

## 주석 · 판서 스타일 (범용)

- 클래스/step 위 한 줄: 그 step이 던지는 질문이나 상태 (예: `// 상태는 행위를 통해서만 변할까?`).
- 대비되는/문제가 드러나는 라인엔 결과를 콕 집는 주석 (예: `// -40000. 은행이 무너졌다.`).
- step 간 "착한 길 / 뚫리는 지점"이나 "전 / 후"를 나눌 때 `main` 안에서
  `System.out.println("----------------------------");`(28 dashes) 구분선을 쓰면 판서가 쉽다.
- 한글 식별자/메서드명 사용 가능 (`물약을마신다()`, `총액()`, `지불한다()`).

## 생성 워크플로

1. **대상·패턴 판별**: 어떤 회차/그룹의 예제인지, 무엇을 대비시키려는지 → step 개수와 각 step의 의미를 먼저 정한다 (위 "step 구조는 자유다" 참고). 주제가 OOP가 아니어도 된다.
2. **step 순서대로 작성**: 각 step은 하나의 상태. 문제를 보여주는 예제라면 그 문제가 **실행 출력에 실제로 찍히게** 만든다.
3. 아래 명령으로 **컴파일·실행**해서 의도한 차이(문제/개선)가 출력에 드러나는지 확인.
4. **결과 요약**: 각 step이 "무엇이 나아졌고 무엇이 아직 문제인가"를 한 줄씩 사용자에게 보고.

## 빌드 / 실행 검증 (인코딩 UTF-8 필수)

한글 주석·출력이 있으므로 반드시 UTF-8. (`pathN`은 예제 폴더 경로로 치환.)

```bash
# Bash 도구
javac -encoding UTF-8 -d bin $(find src/exNN/<group>/<name> -name App.java)
java -Dfile.encoding=UTF-8 -cp bin exNN.<group>.<name>.step2.App
```

```powershell
# PowerShell 도구
$files = Get-ChildItem -Recurse src/exNN/<group>/<name> -Filter App.java | ForEach-Object FullName
javac -encoding UTF-8 -d bin $files
java -Dfile.encoding=UTF-8 -cp bin exNN.<group>.<name>.step2.App
```

---

## 예시 적용: ex01 (OOP · 다형성) — 참고용, 강제 아님

`ex01`은 위 뼈대를 **이렇게 적용한 하나의 예시**다. 새 예제가 OOP일 필요는 없다.

- **`ex01/oop/pN_*` (OOP 원칙)** — 3-step 진화로 적용:
  step1 필드가 열려 그냥 변함 → **step2 함정**(행위는 생겼지만 필드/검사가 열려 뚫림, 실행 출력으로 드러냄) → step3 `private`+행위로만 변경. step2가 이 형태의 핵심.
  step2 "누수"의 두 형태: (a) 열린 필드 직접 조작, (b) 검사 책임을 호출자에게 떠넘김. 교훈은 같다 — **불변식 검사는 객체 자신이 한다.**
- **`ex01/mem` (다형성·메모리)** — 축은 "구체에게 시키기 → 추상에게 시키기":
  step1 타입마다 메서드 증식 → step2 공통 부모(일반 클래스)로 다형성은 얻지만 `new Animal()` 함정 → step3 `abstract`=통로, 동적바인딩. heap은 주석으로 `// [ heap : Animal(달린다-통로) ← Dog(달린다) ]`.

정본 스타일이 필요하면 저장소의 표준형을 열어본다 — 검사/출금을 분리해 함정을 만들고(step2),
`private` 재검사 + 생성자 불변식으로 닫는(step3):
`src/ex01/oop/p2_invariant/step1/App.java`, `.../step2/App.java`, `.../step3/App.java`.

압축 예시 (p2 step2 — 문제를 실행 출력으로 드러내는 정석):

```java
package ex01.oop.p2_invariant.step2;

// 검사와 출금이 따로 논다. 검사는 "호출자 책임"이 되어버렸다.
class Account {
    int balance = 10000;

    boolean 잔액충분(int amount) {   // 검사만 (호출자가 불러줘야 한다)
        return amount <= balance;
    }

    void 출금(int amount) {          // 출금은 검사를 안 한다. 누가 검사했겠거니 믿는다.
        balance -= amount;
        System.out.println(amount + "원 출금. 잔액 : " + balance);
    }
}

public class App {
    public static void main(String[] args) {
        Account account = new Account();

        if (account.잔액충분(3000)) {     // 착한 호출자 : 검사하고 출금
            account.출금(3000);          // 7000
        }

        System.out.println("----------------------------");

        account.출금(50000);            // 깜빡한 호출자 : 검사 없이 그대로 → 음수!
        account.balance = -5000;        // 미친 호출자 : 그냥 상태를 바꾼다.
    }
}
```
