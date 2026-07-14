package ex14.step3;

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
