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
