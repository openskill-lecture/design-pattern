package ex14.step3;

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
