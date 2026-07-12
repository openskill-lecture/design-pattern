package ex08.exercise.button.answer;

// [연습문제2 정답 - 델리게이트] 버튼은 '눌림'만 알리고, 실제 처리는 리스너에게 위임한다.
interface ClickListener {
    void 클릭됨();
}

class SaveController implements ClickListener {
    @Override
    public void 클릭됨() {
        System.out.println("파일을 저장합니다.");
    }
}

class Button {
    private ClickListener listener;   // 처리를 넘길 상대(delegate)

    Button(ClickListener listener) {
        this.listener = listener;
    }

    void 클릭() {
        System.out.println("버튼이 눌렸습니다.");

        listener.클릭됨();            // 실제 처리는 리스너에게 위임
    }
}

public class App {
    public static void main(String[] args) {
        ClickListener controller = new SaveController();
        Button button = new Button(controller);

        button.클릭();

        // 사용자 → 버튼(Button) → 리스너(ClickListener) : 처리를 위임.
        // GUI/안드로이드/JS 이벤트에서 늘 보는 델리게이트(콜백) 구조.
    }
}
