package ex08.exercise.drawing.start;

// [연습문제1 - 시작] 위젯이 복잡한 그리기를 전부 혼자 한다.
// TODO: 복잡한 렌더링을 '화가(Painter)'에게 통째로 위임해보자.
class Widget {
    void 그리기() {
        System.out.println("위젯을 화면에 올립니다.");

        // 복잡한 렌더링을 위젯이 직접 다 한다.
        System.out.println("배경을 칠합니다.");
        System.out.println("테두리를 그립니다.");
        System.out.println("그림자를 넣습니다.");
    }
}

public class App {
    public static void main(String[] args) {
        Widget widget = new Widget();

        widget.그리기();   // 위젯 혼자 복잡한 그리기까지.
    }
}
