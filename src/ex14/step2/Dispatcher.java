package ex14.step2;

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
