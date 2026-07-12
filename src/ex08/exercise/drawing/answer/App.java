package ex08.exercise.drawing.answer;

// [연습문제1 정답 - 델리게이트] 복잡한 그리기는 화가에게 통째로 위임한다.
class Painter {
    void 그린다() {
        System.out.println("배경을 칠합니다.");
        System.out.println("테두리를 그립니다.");
        System.out.println("그림자를 넣습니다.");
    }
}

class Widget {
    private Painter painter = new Painter();   // 그리기를 넘길 상대(delegate)

    void 그리기() {
        System.out.println("위젯을 화면에 올립니다.");

        painter.그린다();                       // 복잡한 렌더링은 화가에게 위임
    }
}

public class App {
    public static void main(String[] args) {
        Widget widget = new Widget();

        widget.그리기();

        // 위젯 → 화가(Painter) : 복잡한 그리기를 위임.
        // 위젯은 '무엇을 언제 올릴지'만 알고, '어떻게 그리는지'는 화가가 안다.
    }
}
