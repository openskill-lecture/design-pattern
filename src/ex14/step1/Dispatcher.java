package ex14.step1;

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
