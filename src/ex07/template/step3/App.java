package ex07.template.step3;

// 골격을 잠갔다. 자식은 '강의하기' 한 칸만 채운다.

abstract class Teacher {
    // final : 골격(순서)은 못 바꾼다. 이게 템플릿 메서드.
    public final void 수업하기() {
        입장하기();
        출석부르기();
        강의하기();
        퇴장하기();
    }

    // 고정 스텝 : protected 라 밖/자식이 함부로 못 건드린다.
    protected void 입장하기()   { System.out.println("입장하기"); }
    protected void 출석부르기() { System.out.println("출석부르기"); }
    protected void 퇴장하기()   { System.out.println("퇴장하기"); }

    // 변하는 스텝 : 자식이 반드시 채운다.
    protected abstract void 강의하기();
}

class JavaTeacher extends Teacher {
    @Override
    protected void 강의하기() { System.out.println("자바 강의하기"); }
}

class PythonTeacher extends Teacher {
    @Override
    protected void 강의하기() { System.out.println("파이썬 강의하기"); }

    // public void 수업하기() { ... }  // ← final 이라 오버라이드하면 컴파일 에러! 골격은 못 뚫는다.
}

public class App {
    public static void main(String[] args) {
        Teacher java = new JavaTeacher();
        Teacher python = new PythonTeacher();

        java.수업하기();      // 입장→출석→자바 강의→퇴장

        System.out.println("----------------------------");

        python.수업하기();    // 입장→출석→파이썬 강의→퇴장

        // 골격은 부모가 한 번만 정하고 잠갔다(final).
        // 자식은 강의하기() 한 칸만 채운다. 순서를 깰 문이 없다.
    }
}
