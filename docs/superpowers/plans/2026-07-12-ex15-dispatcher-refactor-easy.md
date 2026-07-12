# ex15 Dispatcher 리팩터링 — 세상에서 가장 쉬운 코드 버전 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** ex15(리플렉션) 예제를 `Dispatcher` 중심 3-step 진화(if-else → 리플렉션 → ComponentScan)로 리팩터링하되, **초보자가 걸려 넘어지는 요소를 최대한 제거한 "가장 쉬운 코드"**로 작성한다.

**Architecture:** 구조와 진화 흐름은 원본 plan(`2026-07-12-ex15-dispatcher-refactor.md`)과 동일하다 — App은 세 step 내내 `dispatcher.dispatch(uri)`만 부르고 Dispatcher 내부만 진화한다. **차이는 "코드 스타일"에만 있다:** 어려운 예외 나열·try-catch를 걷어내고, 어려운 API 이름을 쉬운 것으로 바꾸고, 한 줄에 개념 하나씩 주석을 단다. 결과 동작(출력)은 원본과 완전히 같다.

**Tech Stack:** 순수 자바, `java.lang.reflect`, 커스텀 어노테이션(`@RequestMapping`, `@Controller`), `ClassLoader` 패키지 스캔.

## Global Constraints

- **인코딩:** 컴파일 `javac -encoding UTF-8`, 실행 `java -Dfile.encoding=UTF-8`.
- **패키지명:** `ex15.step1` / `ex15.step2` / `ex15.step3`.
- **메서드명(확정):** Dispatcher의 요청 처리 메서드는 세 step 모두 `dispatch(String uri)`.
- **동작 동일:** step1 → `insert 호출됨`, step2 → `update 호출됨`, step3 → 등록 로그 + 구분선 + `delete 호출됨`. (원본 plan과 출력이 같아야 한다)
- **한 주제 집중:** 회차 주제는 "리플렉션/어노테이션으로 프레임워크 전환"이다. OCP·SOLID 프레이밍은 넣지 않는다.
- **기존 파일 보존:** `BoardController.java`, `RequestMapping.java`, `Controller.java`는 변경 없음. 새로 만드는 것은 `Dispatcher.java`(step1·2·3)·`ComponentScan.java`(step3), 수정은 `App.java`뿐.

### "가장 쉬운 코드" 규칙 (이 plan의 핵심 — 원본과의 차이점)

| 항목 | 원본 plan | 이 쉬운 버전 | 이유 |
|------|-----------|--------------|------|
| 예외 처리 | `try { invoke } catch(Exception e){ e.printStackTrace(); }` | 메서드에 `throws Exception`, main도 `throws Exception` | 초보자에게 try-catch 블록은 노이즈. "에러 나면 그냥 위로 던진다" 한 줄로 끝. |
| 검사 예외 나열 | `throws URISyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException` | `throws Exception` 한 개 | 예외 이름 4개를 파싱하지 않아도 됨. |
| 어노테이션 조회 | `getDeclaredAnnotation(...)` | `getAnnotation(...)` | 더 흔하고 짧은 이름. 우리 예제에선 결과 동일. |
| 변수명 | `boardController`, `instances` | `controller`, `controllers` | 짧고 일반적. |
| 주석 | 문단형 설명 | **한 줄에 개념 하나**, 각 클래스 맨 위 `// [stepN] 한 줄 요약` | 판서·따라치기 쉬움. 번호 주석(1)2)3))으로 리플렉션 흐름을 순서대로 읽힘. |
| 객체 생성 | (동일) `clazz.newInstance()` | `clazz.newInstance()` 유지 | 가장 짧다. 컴파일 시 deprecated 경고가 뜨지만 **에러 아님** — 무시. |

> **컴파일 경고 안내:** step3에서 `newInstance()` 때문에 `javac`가
> `Note: ... uses or overrides a deprecated API.` 를 출력할 수 있다. 이는 경고이며 컴파일은 성공한다(exit 0). 쉬운 버전 유지를 위해 그대로 둔다.

> **참고 — 테스트 방식:** 강의용 예제라 테스트 프레임워크가 없다. 각 Task의 검증 = **UTF-8 컴파일 + 실행 후 stdout 확인**.

---

## File Structure

```
src/ex15/
  step1/  App.java(수정)  Dispatcher.java(신규)                       BoardController.java(그대로)
  step2/  App.java(수정)  Dispatcher.java(신규)                       BoardController.java·RequestMapping.java(그대로)
  step3/  App.java(수정)  Dispatcher.java(신규)  ComponentScan.java(신규)  BoardController.java·Controller.java·RequestMapping.java(그대로)
```

각 step은 독립 실행되는 완결 데모. step = Task 1:1.

---

### Task 1: step1 — Dispatcher(if-else), 가장 쉬운 형태

**Files:**
- Create: `src/ex15/step1/Dispatcher.java`
- Modify: `src/ex15/step1/App.java` (전체 교체)
- (변경 없음) `src/ex15/step1/BoardController.java`

**Interfaces:**
- Consumes: `BoardController.insert()` / `update()` / `delete()` (`void`, 기존 그대로)
- Produces: `class ex15.step1.Dispatcher` — 생성자 `Dispatcher()`, 메서드 `public void dispatch(String uri)`. (step1은 리플렉션이 없어 `throws` 불필요)

- [ ] **Step 1: `Dispatcher.java` 작성**

`src/ex15/step1/Dispatcher.java`:

```java
package ex15.step1;

// [step1] 아직은 사람이 손으로 uri 를 나눈다 (if-else)
public class Dispatcher {

    // 컨트롤러를 미리 하나 만들어 둔다
    private BoardController controller = new BoardController();

    // uri 를 받아서 "어떤 메서드를 부를지" 직접 고른다
    public void dispatch(String uri) {
        if (uri.equals("/insert")) {
            controller.insert();
        } else if (uri.equals("/update")) {
            controller.update();
        } else if (uri.equals("/delete")) {
            controller.delete();
        }
    }
}
```

- [ ] **Step 2: `App.java` 교체**

`src/ex15/step1/App.java` 전체 교체:

```java
package ex15.step1;

// [step1] App 은 uri 만 Dispatcher 에게 넘긴다
public class App {
    public static void main(String[] args) {
        String uri = "/insert";        // 요청이 들어왔다고 가정

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.dispatch(uri);      // 나머지는 Dispatcher 가 알아서
    }
}
```

- [ ] **Step 3: 컴파일**

Run:
```bash
javac -encoding UTF-8 -d bin src/ex15/step1/*.java
```
Expected: 에러·경고 없이 종료 (exit 0).

- [ ] **Step 4: 실행 후 출력 확인**

Run:
```bash
java -Dfile.encoding=UTF-8 -cp bin ex15.step1.App
```
Expected stdout:
```
insert 호출됨
```

- [ ] **Step 5: 커밋**

```bash
git add src/ex15/step1/Dispatcher.java src/ex15/step1/App.java
git commit -m "ex15 step1(easy): dispatch를 Dispatcher(if-else)로 추출, 쉬운 코드"
```

---

### Task 2: step2 — Dispatcher 내부를 리플렉션으로 (try-catch 없이 throws로 쉽게)

**Files:**
- Create: `src/ex15/step2/Dispatcher.java`
- Modify: `src/ex15/step2/App.java` (전체 교체)
- (변경 없음) `src/ex15/step2/BoardController.java`, `src/ex15/step2/RequestMapping.java`

**Interfaces:**
- Consumes: `@interface RequestMapping { String uri(); }`(RUNTIME/METHOD), `BoardController`의 `@RequestMapping` 메서드들 — 기존 그대로.
- Produces: `class ex15.step2.Dispatcher` — 생성자 `Dispatcher()`, 메서드 `public void dispatch(String uri) throws Exception`. **시그니처의 `throws Exception`이 step1과의 유일한 표면 차이**(리플렉션 invoke가 검사 예외를 던지기 때문). App은 main에 `throws Exception`만 붙이면 그대로 동작.

- [ ] **Step 1: `Dispatcher.java` 작성 (리플렉션, 번호 주석으로 흐름 설명)**

`src/ex15/step2/Dispatcher.java`:

```java
package ex15.step2;

import java.lang.reflect.Method;

// [step2] if-else 를 지우고, 리플렉션으로 메서드를 찾는다
public class Dispatcher {

    private BoardController controller = new BoardController();

    // 어려운 예외는 그냥 위로 던진다 (try-catch 없이 깔끔하게)
    public void dispatch(String uri) throws Exception {

        // 1) 컨트롤러가 가진 메서드들을 전부 가져온다
        Method[] methods = controller.getClass().getDeclaredMethods();

        // 2) 메서드를 하나씩 살펴본다
        for (Method method : methods) {

            // 3) 그 메서드에 붙은 @RequestMapping 을 꺼낸다
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            if (rm == null) continue;          // 어노테이션 없으면 건너뛴다

            // 4) uri 가 같으면 그 메서드를 호출하고 끝낸다
            if (rm.uri().equals(uri)) {
                method.invoke(controller);
                return;
            }
        }
    }
}
```

- [ ] **Step 2: `App.java` 교체 (step1과 거의 동일, main에 throws만 추가)**

`src/ex15/step2/App.java` 전체 교체:

```java
package ex15.step2;

// [step2] App 은 step1 과 똑같다! 바뀐 건 Dispatcher 속뿐
public class App {
    public static void main(String[] args) throws Exception {
        String uri = "/update";

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.dispatch(uri);
    }
}
```

- [ ] **Step 3: 컴파일**

Run:
```bash
javac -encoding UTF-8 -d bin src/ex15/step2/*.java
```
Expected: 에러·경고 없이 종료 (exit 0).

- [ ] **Step 4: 실행 후 출력 확인**

Run:
```bash
java -Dfile.encoding=UTF-8 -cp bin ex15.step2.App
```
Expected stdout:
```
update 호출됨
```

- [ ] **Step 5: 커밋**

```bash
git add src/ex15/step2/Dispatcher.java src/ex15/step2/App.java
git commit -m "ex15 step2(easy): Dispatcher 내부를 리플렉션으로, throws로 쉽게"
```

---

### Task 3: step3 — ComponentScan 자동 등록 + Dispatcher 목록 주입 (예외는 throws Exception 하나로)

**Files:**
- Create: `src/ex15/step3/ComponentScan.java`
- Create: `src/ex15/step3/Dispatcher.java`
- Modify: `src/ex15/step3/App.java` (전체 교체 — 기존 `findUri` static 제거)
- (변경 없음) `src/ex15/step3/BoardController.java`, `src/ex15/step3/Controller.java`, `src/ex15/step3/RequestMapping.java`

**Interfaces:**
- Consumes: `@interface Controller`(RUNTIME/TYPE), `@interface RequestMapping { String uri(); }`(RUNTIME/METHOD), `BoardController`(@Controller + 5개 @RequestMapping) — 기존 그대로.
- Produces:
  - `class ex15.step3.ComponentScan` — 메서드 `public List<Object> scan(String packageName) throws Exception`. `packageName`은 슬래시 경로(예: `"ex15/step3"`), 반환은 `@Controller` 붙은 클래스 인스턴스 목록.
  - `class ex15.step3.Dispatcher` — 생성자 `public Dispatcher(List<Object> controllers)`, 메서드 `public void dispatch(String uri) throws Exception`. **step3에서는 생성자만 목록 주입으로 바뀌고 `dispatch(String)`은 그대로.**

- [ ] **Step 1: `ComponentScan.java` 작성 (throws Exception 하나로)**

`src/ex15/step3/ComponentScan.java`:

```java
package ex15.step3;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// [step3] 폴더를 뒤져서 @Controller 붙은 클래스를 자동으로 만들어 준다
public class ComponentScan {

    // 어려운 예외는 그냥 위로 던진다 (한 개로 묶어서 쉽게)
    public List<Object> scan(String packageName) throws Exception {

        List<Object> controllers = new ArrayList<>();   // 찾은 컨트롤러를 담을 통

        // 1) 이 패키지 폴더가 어디인지 물어본다
        URL url = Thread.currentThread().getContextClassLoader().getResource(packageName);
        File folder = new File(url.toURI());

        // 2) 폴더 안의 파일을 하나씩 본다
        for (File file : folder.listFiles()) {

            if (!file.getName().endsWith(".class")) continue;   // .class 만 본다

            // 3) 파일명을 진짜 클래스 이름으로 바꾼다 (BoardController.class -> ex15.step3.BoardController)
            String className = packageName.replace("/", ".") + "." + file.getName().replace(".class", "");
            Class<?> clazz = Class.forName(className);

            // 4) @Controller 가 붙어 있으면 객체로 만들어 통에 담는다
            if (clazz.isAnnotationPresent(Controller.class)) {
                System.out.println("등록된 컨트롤러: " + file.getName());
                controllers.add(clazz.newInstance());
            }
        }

        return controllers;
    }
}
```

- [ ] **Step 2: `Dispatcher.java` 작성 (목록 주입, 리플렉션)**

`src/ex15/step3/Dispatcher.java`:

```java
package ex15.step3;

import java.lang.reflect.Method;
import java.util.List;

// [step3] 자동 등록된 컨트롤러 목록에서 메서드를 찾는다
public class Dispatcher {

    private List<Object> controllers;   // 스캔으로 찾은 컨트롤러들

    public Dispatcher(List<Object> controllers) {
        this.controllers = controllers;
    }

    public void dispatch(String uri) throws Exception {

        // 컨트롤러를 하나씩 돌면서
        for (Object controller : controllers) {

            // 그 컨트롤러의 메서드들을 본다
            for (Method method : controller.getClass().getDeclaredMethods()) {

                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                if (rm == null) continue;

                if (rm.uri().equals(uri)) {
                    method.invoke(controller);
                    return;
                }
            }
        }
    }
}
```

- [ ] **Step 3: `App.java` 교체 (조립만, 기존 findUri 제거)**

`src/ex15/step3/App.java` 전체 교체:

```java
package ex15.step3;

import java.util.List;

// [step3] App 은 "스캔해줘" -> "이 uri 처리해줘" 두 마디만 한다
public class App {
    public static void main(String[] args) throws Exception {

        // 1) 폴더를 스캔해서 컨트롤러를 자동으로 모은다
        List<Object> controllers = new ComponentScan().scan("ex15/step3");

        System.out.println("--------------------------------");

        // 2) 모은 컨트롤러들에게 uri 를 처리시킨다
        new Dispatcher(controllers).dispatch("/delete");
    }
}
```

- [ ] **Step 4: 재컴파일 (이전 App.class는 같은 파일명으로 덮어써짐)**

Run:
```bash
javac -encoding UTF-8 -d bin src/ex15/step3/*.java
```
Expected: exit 0. (`newInstance()` deprecated 경고가 나올 수 있으나 에러 아님) `bin/ex15/step3/ComponentScan.class`, `bin/ex15/step3/Dispatcher.class` 생성.

- [ ] **Step 5: 실행 후 출력 확인**

Run:
```bash
java -Dfile.encoding=UTF-8 -cp bin ex15.step3.App
```
Expected stdout:
```
등록된 컨트롤러: BoardController.class
--------------------------------
delete 호출됨
```
> 확인 포인트: 등록 로그는 `BoardController.class` **한 줄만**(App/Dispatcher/ComponentScan/Controller/RequestMapping은 `@Controller`가 없어 등록 안 됨), 마지막 줄은 `delete 호출됨`.

- [ ] **Step 6: 커밋**

```bash
git add src/ex15/step3/ComponentScan.java src/ex15/step3/Dispatcher.java src/ex15/step3/App.java
git commit -m "ex15 step3(easy): ComponentScan 자동 등록 + Dispatcher 목록 주입, 쉬운 코드"
```

---

## Self-Review

**1. Spec coverage:**
- "같은 3-step 유지" → Task 1~3이 if-else → 리플렉션 → ComponentScan 진화를 그대로 따름 ✔
- "초보자 친화(예외/제네릭/어려운 이름 최소화)" → `throws Exception` 하나로, try-catch 제거, `getAnnotation`, 짧은 변수명, 한 줄 개념 주석 → Global Constraints 표에 규칙화 ✔
- "출력은 원본과 동일" → step1 insert / step2 update / step3 등록로그+구분선+delete, 각 Task의 Expected stdout에 명시 ✔

**2. Placeholder scan:** TBD/TODO/추상 표현 없음. 모든 코드 완성본. ✔

**3. Type consistency:**
- `dispatch(String uri)` — 세 step 동일. step2·3은 `throws Exception` 추가(리플렉션 때문), 문서에 명시 ✔
- `ComponentScan.scan(String) : List<Object>` ↔ `Dispatcher(List<Object>)` 타입 일치 ✔
- `getAnnotation` / `getDeclaredMethods` / `isAnnotationPresent(Controller.class)` 이름 세 파일에서 일관 ✔

**4. 원본 plan과의 관계:** 구조·출력 동일, "코드 스타일"만 쉬움. 두 plan은 택일(둘 중 하나만 적용) 관계다. 같은 파일을 건드리므로 **두 plan을 동시에 실행하지 말 것** — 하나를 골라 실행한다. ✔
