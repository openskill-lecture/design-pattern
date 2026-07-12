package ex07.template.step1;

// 템플릿이 없다. 수업 흐름을 누가 지켜줄까?
// 선생님마다 수업하기()에 입장→출석→강의→퇴장을 통째로 적는다.

// 자바 선생님 : 흐름을 직접 다 적는다.
class JavaTeacher {
    void 수업하기() {
        System.out.println("입장하기");
        System.out.println("출석부르기");
        System.out.println("자바 강의하기");
        System.out.println("퇴장하기");
    }
}

// 파이썬 선생님 : 똑같은 입장/출석/퇴장을 또 복붙한다.
// 그런데 복붙하다 출석부르기를 깜빡했다! (막아주는 게 아무것도 없다)
class PythonTeacher {
    void 수업하기() {
        System.out.println("입장하기");
        // 출석부르기  ← 깜빡 빠뜨림. 아무도 안 잡아준다.
        System.out.println("파이썬 강의하기");
        System.out.println("퇴장하기");
    }
}

public class App {
    public static void main(String[] args) {
        new JavaTeacher().수업하기();

        System.out.println("----------------------------");

        new PythonTeacher().수업하기();   // 출석이 빠졌다! 흐름이 제각각이다.

        // 입장/출석/퇴장이 선생님마다 똑같이 반복(중복)되고,
        // 순서와 스텝을 강제하는 게 없어 한 명이 깜빡하면 그대로 뚫린다.
        // → 변하지 않는 흐름(골격)을 한 곳으로 끌어올려야 한다.
    }
}
