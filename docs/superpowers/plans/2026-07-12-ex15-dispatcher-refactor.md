# ex15 Dispatcher 리팩터링 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** ex15(리플렉션) 예제에서 요청 분배(dispatch) 책임을 `App.main()` 밖으로 빼내 `Dispatcher` 클래스로 모으고, step1→step2→step3로 가며 그 내부 구현만 if-else → 리플렉션 → 컴포넌트 스캔으로 진화시킨다.

**Architecture:** `App`은 세 step 내내 "uri 를 `dispatcher.dispatch(uri)` 로 넘긴다"만 한다(겉모습 고정). 바뀌는 것은 `Dispatcher` **내부**뿐이다: step1은 손으로 짠 if-else, step2는 리플렉션으로 교체(App은 한 줄도 안 바뀜), step3는 `ComponentScan`이 자동 등록한 컨트롤러 목록을 받아 그 위에서 dispatch. "겉(caller)은 그대로인데 속(mechanism)만 프레임워크로 진화한다"를 실행 출력으로 보여주는 것이 목적.

**Tech Stack:** Java (순수 자바, 외부 라이브러리·테스트 프레임워크 없음), `java.lang.reflect`, 커스텀 어노테이션(`@RequestMapping`, `@Controller`), `ClassLoader` 기반 패키지 스캔.

## Global Constraints

- **인코딩:** 한글 주석·출력이 있으므로 컴파일은 항상 `javac -encoding UTF-8`, 실행은 `java -Dfile.encoding=UTF-8`.
- **패키지명:** 폴더 경로 그대로 — `ex15.step1`, `ex15.step2`, `ex15.step3`.
- **메서드명(확정):** Dispatcher의 요청 처리 메서드는 세 step 모두 `dispatch(String uri)`. (`findUri` 아님)
- **네이밍:** 클래스명은 파스칼·영어(`Dispatcher`, `ComponentScan`), 변수명은 카멜·영어(`dispatcher`, `instances`), 도메인 행위 메서드는 한글 허용이나 여기서는 프레임워크 용어라 `dispatch`/`scan` 영어 유지.
- **한 주제 집중:** 이 회차 주제는 "리플렉션/어노테이션으로 프레임워크 전환"이다. OCP·SOLID 등 다른 원칙 프레이밍은 끌어들이지 않는다. (기존 step1 App의 OCP 설명 문장은 제거하고 dispatch/한계 관점으로 다시 씀)
- **컴파일 산출물:** `bin/` (기존과 동일). step3는 `.class`가 `bin/ex15/step3` 아래 있어야 스캔이 동작한다.
- **기존 파일 보존:** `BoardController.java`, `RequestMapping.java`, `Controller.java`는 내용 변경 없음. 새로 만드는 것은 `Dispatcher.java`(step1·2·3)와 `ComponentScan.java`(step3)뿐이고, `App.java`만 수정한다.

> **참고 — 테스트 방식:** 이 저장소는 강의용 예제라 JUnit 등 테스트 프레임워크가 없다. 각 Task의 "테스트"는 **UTF-8 컴파일 + 실행 후 표준출력(stdout) 문자열 확인**이다. 이것이 이 코드베이스의 정직한 검증 단위다.

---

## File Structure

```
src/ex15/
  step1/
    App.java             (수정) 이제 Dispatcher 에게 위임만
    Dispatcher.java      (신규) if-else 분기표를 여기로 모음
    BoardController.java  (변경 없음)
  step2/
    App.java             (수정) step1과 동일하게 dispatch 한 줄
    Dispatcher.java      (신규) 내부를 리플렉션으로 교체
    BoardController.java  (변경 없음)
    RequestMapping.java   (변경 없음)
  step3/
    App.java             (수정) ComponentScan + Dispatcher 조립만
    ComponentScan.java   (신규) @Controller 자동 등록
    Dispatcher.java      (신규) 스캔된 목록 위에서 dispatch
    BoardController.java  (변경 없음)
    Controller.java       (변경 없음)
    RequestMapping.java   (변경 없음)
```

각 step은 독립적으로 컴파일·실행되어 그 자체로 완결된 데모다. 그래서 step = Task로 1:1 매핑한다.

---

### Task 1: step1 — Dispatcher(if-else)로 분배 책임 추출

**Files:**
- Create: `src/ex15/step1/Dispatcher.java`
- Modify: `src/ex15/step1/App.java` (전체 교체)
- (변경 없음) `src/ex15/step1/BoardController.java`
- Test(수동): 컴파일 + 실행 stdout 확인

**Interfaces:**
- Consumes: `BoardController.insert()`, `update()`, `delete()` (기존 시그니처 그대로, `void`)
- Produces: `class ex15.step1.Dispatcher` — 생성자 `Dispatcher()`(기본), 메서드 `public void dispatch(String uri)`. 이 `dispatch(String)` 시그니처는 step2·step3에서도 동일하게 유지된다.

- [ ] **Step 1: `Dispatcher.java` 작성 (if-else 분기표)**

`src/ex15/step1/Dispatcher.java`:

```java
package ex15.step1;

/**
 * ch01 - 요청 분배(dispatch) 책임을 한 곳으로 모은다
 *
 * App(main)에 흩어져 있던 "uri -> 어떤 메서드" 분기를 Dispatcher 로 옮겼다.
 * 아직은 if-else 로 사람이 직접 나열한다. (다음 step 에서 리플렉션으로 바뀐다)
 *
 * => 새 기능(uri)이 생길 때마다 여기 else-if 를 계속 추가해야 한다. 이것이 한계.
 */
public class Dispatcher {

    private BoardController boardController = new BoardController();

    // uri 를 보고 어떤 메서드를 부를지 사람이 직접 분기한다.
    public void dispatch(String uri) {
        if (uri.equals("/insert")) {
            boardController.insert();
        } else if (uri.equals("/update")) {
            boardController.update();
        } else if (uri.equals("/delete")) {
            boardController.delete();
        }
    }
}
```

- [ ] **Step 2: `App.java` 를 위임 형태로 교체**

`src/ex15/step1/App.java` 전체를 아래로 교체:

```java
package ex15.step1;

/**
 * ch01 - 디자인 패턴의 한계 (출발점)
 *
 * App 은 이제 "무슨 uri 인지"만 Dispatcher 에게 넘긴다.
 * 하지만 Dispatcher 안에는 여전히 if-else 분기표가 사람 손으로 관리된다.
 * => 기능(uri)이 하나 늘 때마다 Dispatcher 의 else-if 를 계속 고쳐야 한다.
 */
public class App {
    public static void main(String[] args) {
        // 클라이언트가 보낸 요청 주소라고 가정
        String uri = "/insert";

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.dispatch(uri);
    }
}
```

- [ ] **Step 3: 컴파일 (UTF-8)**

Run:
```bash
javac -encoding UTF-8 -d bin src/ex15/step1/*.java
```
Expected: 에러·경고 없이 종료 (exit 0). `bin/ex15/step1/Dispatcher.class` 생성됨.

- [ ] **Step 4: 실행 후 출력 확인**

Run:
```bash
java -Dfile.encoding=UTF-8 -cp bin ex15.step1.App
```
Expected stdout (정확히 한 줄):
```
insert 호출됨
```

- [ ] **Step 5: 커밋**

```bash
git add src/ex15/step1/Dispatcher.java src/ex15/step1/App.java
git commit -m "ex15 step1: dispatch 책임을 Dispatcher(if-else)로 추출"
```

---

### Task 2: step2 — Dispatcher 내부를 리플렉션으로 교체 (App 불변)

**Files:**
- Create: `src/ex15/step2/Dispatcher.java`
- Modify: `src/ex15/step2/App.java` (전체 교체)
- (변경 없음) `src/ex15/step2/BoardController.java`, `src/ex15/step2/RequestMapping.java`
- Test(수동): 컴파일 + 실행 stdout 확인

**Interfaces:**
- Consumes: `BoardController`의 `@RequestMapping(uri=...)` 붙은 메서드들 (`insert`/`delete`/`update`/`select`), `@interface RequestMapping { String uri(); }` (RUNTIME 유지, METHOD 대상) — 둘 다 기존 파일 그대로.
- Produces: `class ex15.step2.Dispatcher` — 생성자 `Dispatcher()`(기본), 메서드 `public void dispatch(String uri)`. **시그니처는 step1과 동일**하다(App 코드가 안 바뀌는 이유).

- [ ] **Step 1: `Dispatcher.java` 작성 (리플렉션 버전)**

`src/ex15/step2/Dispatcher.java`:

```java
package ex15.step2;

import java.lang.reflect.Method;

/**
 * ch02 - Dispatcher 내부를 리플렉션으로 교체
 *
 * dispatch(uri) 의 "겉모습(시그니처)"은 step1 과 똑같다. 그래서 App 은 전혀 안 바뀐다.
 * 하지만 내부의 if-else 분기표가 통째로 사라지고 리플렉션으로 대체됐다:
 *   컨트롤러의 메서드들을 훑어 @RequestMapping 의 uri 가 요청과 같으면 invoke.
 *
 * => BoardController 에 새 메서드를 아무리 추가해도 Dispatcher 는 절대 안 바뀐다.
 *    라이브러리(내가 부른다) -> 프레임워크(프레임워크가 나를 부른다)로의 전환.
 */
public class Dispatcher {

    private BoardController boardController = new BoardController();

    public void dispatch(String uri) {
        // 컨트롤러가 가진 모든 메서드를 런타임에 조사한다.
        Method[] methods = boardController.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping rm = method.getDeclaredAnnotation(RequestMapping.class);

            // @RequestMapping 이 안 붙은 메서드면 null -> 건너뛴다 (NPE 방지)
            if (rm == null) {
                continue;
            }

            // 외부에서 들어온 uri 와 어노테이션의 uri 가 같으면 그 메서드를 호출
            if (rm.uri().equals(uri)) {
                try {
                    method.invoke(boardController); // 리플렉션으로 호출
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

- [ ] **Step 2: `App.java` 교체 (step1과 사실상 동일한 형태)**

`src/ex15/step2/App.java` 전체를 아래로 교체:

```java
package ex15.step2;

/**
 * ch02 - 어노테이션 + 리플렉션으로 한계 돌파
 *
 * App 은 step1 과 똑같이 dispatch(uri) 한 줄만 부른다. (한 글자도 안 바뀜)
 * 바뀐 것은 Dispatcher 내부뿐 — if-else 가 리플렉션으로 교체됐다.
 * => 이것이 "코드를 안 고치고 확장한다"의 실체. 프레임워크로의 전환점.
 */
public class App {
    public static void main(String[] args) {
        // 클라이언트가 보낸 요청 주소라고 가정
        String uri = "/update";

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.dispatch(uri);
    }
}
```

- [ ] **Step 3: 컴파일 (UTF-8)**

Run:
```bash
javac -encoding UTF-8 -d bin src/ex15/step2/*.java
```
Expected: 에러·경고 없이 종료 (exit 0). `bin/ex15/step2/Dispatcher.class` 생성됨.

- [ ] **Step 4: 실행 후 출력 확인**

Run:
```bash
java -Dfile.encoding=UTF-8 -cp bin ex15.step2.App
```
Expected stdout (정확히 한 줄):
```
update 호출됨
```

- [ ] **Step 5: 커밋**

```bash
git add src/ex15/step2/Dispatcher.java src/ex15/step2/App.java
git commit -m "ex15 step2: Dispatcher 내부를 리플렉션으로 교체(App 불변)"
```

---

### Task 3: step3 — ComponentScan으로 자동 등록 + Dispatcher가 목록 위에서 dispatch

**Files:**
- Create: `src/ex15/step3/ComponentScan.java`
- Create: `src/ex15/step3/Dispatcher.java`
- Modify: `src/ex15/step3/App.java` (전체 교체 — 기존 `findUri` static 메서드 제거)
- (변경 없음) `src/ex15/step3/BoardController.java`, `src/ex15/step3/Controller.java`, `src/ex15/step3/RequestMapping.java`
- Test(수동): 컴파일 + 실행 stdout 확인

**Interfaces:**
- Consumes:
  - `@interface Controller` (RUNTIME, TYPE 대상) — 기존 파일 그대로.
  - `@interface RequestMapping { String uri(); }` (RUNTIME, METHOD 대상) — 기존 파일 그대로.
  - `BoardController`에 `@Controller` + 5개 `@RequestMapping` 메서드(`insert`/`delete`/`update`/`select`/`create`) — 기존 파일 그대로.
- Produces:
  - `class ex15.step3.ComponentScan` — 메서드 `public List<Object> scan(String packageName) throws URISyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException`. `packageName`은 슬래시 경로(예: `"ex15/step3"`). 반환값은 `@Controller`가 붙은 클래스들의 인스턴스 목록.
  - `class ex15.step3.Dispatcher` — 생성자 `public Dispatcher(List<Object> instances)`, 메서드 `public void dispatch(String uri)`. **step3에서는 생성자만 바뀌고(무인자 → 인스턴스 목록 주입) `dispatch(String)` 시그니처는 그대로 유지**된다.

- [ ] **Step 1: `ComponentScan.java` 작성 (자동 등록)**

`src/ex15/step3/ComponentScan.java`:

```java
package ex15.step3;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ch03 - 컴포넌트 스캔 (스프링 ComponentScan 의 축소판) * 시연용 *
 *
 * 패키지 폴더의 .class 파일을 훑어 @Controller 가 붙은 클래스만 골라
 * 객체로 만들어(자동 등록) 목록으로 돌려준다.
 * App 은 어떤 컨트롤러가 있는지 하나도 몰라도 된다.
 *
 * (주의) IDE/빌드로 .class 가 bin/ex15/step3 에 생성된 상태에서 동작한다.
 */
public class ComponentScan {

    // packageName 예: "ex15/step3"
    public List<Object> scan(String packageName)
            throws URISyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        List<Object> instances = new ArrayList<>();

        // 패키지 폴더 위치를 클래스로더에게 물어본다.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageUrl = classLoader.getResource(packageName);
        File dir = new File(packageUrl.toURI());

        // 폴더 안의 파일을 전부 순회
        File[] files = dir.listFiles();
        for (File file : files) {
            // .class 파일만 대상으로 삼는다
            if (!file.getName().endsWith(".class")) {
                continue;
            }

            // 파일명 -> 정규 클래스명 (BoardController.class -> ex15.step3.BoardController)
            String className = packageName.replace("/", ".") + "." + file.getName().replace(".class", "");
            Class<?> cls = Class.forName(className);

            // @Controller 가 붙은 클래스만 골라 객체로 만든다(자동 등록)
            if (cls.isAnnotationPresent(Controller.class)) {
                System.out.println("등록된 컨트롤러: " + file.getName());
                instances.add(cls.newInstance());
            }
        }

        return instances;
    }
}
```

- [ ] **Step 2: `Dispatcher.java` 작성 (주입받은 목록 위에서 dispatch)**

`src/ex15/step3/Dispatcher.java`:

```java
package ex15.step3;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ch03 - 스캔으로 등록된 컨트롤러들 위에서 dispatch
 *
 * step2 의 Dispatcher 는 BoardController 하나를 직접 new 했다.
 * 여기서는 ComponentScan 이 자동 등록한 컨트롤러 목록을 생성자로 주입받아,
 * 그 중에서 uri 가 맞는 메서드를 찾아 호출한다.
 * dispatch(uri) 시그니처는 step1·step2 와 똑같다 — 바뀐 건 "무엇을 뒤지느냐"뿐.
 */
public class Dispatcher {

    private List<Object> instances;

    public Dispatcher(List<Object> instances) {
        this.instances = instances;
    }

    public void dispatch(String uri) {
        for (Object instance : instances) {
            Method[] methods = instance.getClass().getDeclaredMethods();
            for (Method method : methods) {
                RequestMapping rm = method.getDeclaredAnnotation(RequestMapping.class);

                // @RequestMapping 이 없는 메서드는 건너뛴다 (NPE 방지)
                if (rm == null) {
                    continue;
                }

                if (rm.uri().equals(uri)) {
                    try {
                        method.invoke(instance);
                        return; // 첫 매칭에서 처리 끝
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

- [ ] **Step 3: `App.java` 교체 (조립만 담당, 기존 findUri 제거)**

`src/ex15/step3/App.java` 전체를 아래로 교체:

```java
package ex15.step3;

import java.util.List;

/**
 * ch03 - 패키지 스캔 (DispatcherServlet 의 축소판) * 시연용 *
 *
 * App 은 이제 두 협력자에게 일을 시키기만 한다:
 *   1) ComponentScan : @Controller 붙은 클래스를 자동 등록해 목록으로 돌려줌
 *   2) Dispatcher    : 그 목록 위에서 uri 에 맞는 메서드를 찾아 호출
 * 개발자는 @Controller, @RequestMapping 만 붙이면 된다.
 * "어노테이션만 달았는데 연결됐다"의 실체 = 컴포넌트 스캔 + 핸들러 매핑.
 *
 * (주의) 이 코드는 IDE/빌드로 .class 가 생성된 상태에서 동작한다.
 */
public class App {
    public static void main(String[] args) throws Exception {
        // 1) 패키지를 스캔해 @Controller 객체들을 자동 등록
        ComponentScan componentScan = new ComponentScan();
        List<Object> instances = componentScan.scan("ex15/step3");

        System.out.println("--------------------------------");

        // 2) 스캔된 컨트롤러들 위에서 요청 uri 를 dispatch
        Dispatcher dispatcher = new Dispatcher(instances);
        dispatcher.dispatch("/delete");
    }
}
```

- [ ] **Step 4: 이전 .class 정리 후 컴파일 (UTF-8)**

> 기존 `bin/ex15/step3`에는 리팩터링 전 `App.class`가 남아 있다. 스캔은 폴더의 모든 `.class`를 훑으므로, 새 소스로 덮어써 재컴파일하면 된다(파일명이 같아 자동 대체). 새로 생기는 `ComponentScan.class`, `Dispatcher.class`는 `@Controller`가 없어 등록 대상이 아니다.

Run:
```bash
javac -encoding UTF-8 -d bin src/ex15/step3/*.java
```
Expected: 에러·경고 없이 종료 (exit 0). `bin/ex15/step3/ComponentScan.class`, `bin/ex15/step3/Dispatcher.class` 생성됨.

- [ ] **Step 5: 실행 후 출력 확인**

Run:
```bash
java -Dfile.encoding=UTF-8 -cp bin ex15.step3.App
```
Expected stdout (컨트롤러는 `BoardController` 하나만 `@Controller`이므로):
```
등록된 컨트롤러: BoardController.class
--------------------------------
delete 호출됨
```
> 확인 포인트: 등록 로그가 `BoardController.class` **한 줄만** 나와야 한다(App/Dispatcher/ComponentScan/Controller/RequestMapping의 .class는 `@Controller`가 없어 등록 안 됨). 마지막 줄은 반드시 `delete 호출됨`.

- [ ] **Step 6: 커밋**

```bash
git add src/ex15/step3/ComponentScan.java src/ex15/step3/Dispatcher.java src/ex15/step3/App.java
git commit -m "ex15 step3: ComponentScan 자동 등록 + Dispatcher 목록 주입 구조로 재구성"
```

---

## Self-Review

**1. Spec coverage (사용자 요청 3가지):**
- "step1부터 Dispatcher.java 하나 만들어 if-else 모으기" → Task 1 ✔ (`dispatch(String uri)` 안에 if-else)
- "함수명 dispatch 확정(findUri 대신)" → Global Constraints + 세 Task 모두 `dispatch` ✔
- "step2는 Dispatcher의 메서드를 리플렉션으로" → Task 2 ✔ (시그니처 고정, 내부만 교체 → App 불변 강조)
- "step3는 ComponentScan 클래스로 처리" → Task 3 ✔ (스캔 로직을 App에서 ComponentScan으로 이동, Dispatcher는 목록 주입)

**2. Placeholder scan:** TBD/TODO/"적절히 처리" 없음. 모든 코드 블록은 그대로 붙여 쓸 수 있는 완성본. ✔

**3. Type consistency:**
- `dispatch(String uri)` — step1·2·3 전부 동일 시그니처, 반환 `void` ✔
- step3 Dispatcher 생성자만 `Dispatcher(List<Object> instances)`로 바뀜(문서에 명시) ✔
- `ComponentScan.scan(String packageName)` 반환 `List<Object>` ↔ `Dispatcher(List<Object>)` 소비 타입 일치 ✔
- `packageName`은 슬래시 경로(`"ex15/step3"`); `getResource`에 그대로 쓰고, 클래스명 조립 시 `replace("/", ".")` 적용 ✔

**4. 교육적 일관성:** 세 step 모두 `dispatcher.dispatch(uri)` 호출이 App에 남아, "겉은 그대로 속만 진화"라는 핵심 메시지가 코드 diff로 드러남. OCP 프레이밍은 제거해 회차 주제(리플렉션/프레임워크 전환)에 집중. ✔
