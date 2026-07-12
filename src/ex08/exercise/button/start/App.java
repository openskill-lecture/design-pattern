package ex08.exercise.button.start;

// [연습문제2 - 시작] 버튼이 눌리면 저장 처리까지 버튼이 직접 한다.
// TODO: 실제 처리를 리스너(ClickListener)에게 위임해보자.
class SaveButton {
    void 클릭() {
        System.out.println("저장 버튼이 눌렸습니다.");

        // 버튼이 저장 로직까지 직접 안다. 버튼은 '눌림'만 알아야 하는데.
        System.out.println("파일을 저장합니다.");
    }
}

public class App {
    public static void main(String[] args) {
        SaveButton button = new SaveButton();

        button.클릭();   // 버튼이 눌림 감지 + 저장까지 혼자.
    }
}
