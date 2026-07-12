package ex07.template.step2;

// 흐름을 부모로 끌어올렸다. 그런데 이 골격, 정말 고정일까?
// 수업하기()가 final 이 아니고, 스텝들이 public 이라 문이 열려 있다.

class Teacher {
    // 수업의 골격(템플릿). 하지만 final 이 아니라 자식이 갈아엎을 수 있다.
    public void 수업하기() {
        입장하기();
        출석부르기();
        강의하기();
        퇴장하기();
    }

    // 고정이어야 할 스텝들인데 public 이라 자식이 마음대로 바꿀 수 있다.
    public void 입장하기()   { System.out.println("입장하기"); }
    public void 출석부르기() { System.out.println("출석부르기"); }
    public void 강의하기()   { System.out.println("강의하기"); }
    public void 퇴장하기()   { System.out.println("퇴장하기"); }
}

// 착한 자식 : 강의하기()만 바꿔 골격을 그대로 따른다.
class JavaTeacher extends Teacher {
    @Override
    public void 강의하기() { System.out.println("자바 강의하기"); }
}

// 뚫는 자식 (a) : 수업하기() 자체를 오버라이드해 골격을 갈아엎는다.
class CheatTeacher extends Teacher {
    @Override
    public void 수업하기() {        // 골격을 통째로 다시 쓴다!
        입장하기();
        강의하기();                 // 출석은 건너뛰고
        퇴장하기();
        출석부르기();               // 퇴장한 뒤에 출석? 순서가 깨졌다.
    }
    @Override
    public void 강의하기() { System.out.println("대충 강의하기"); }
}

// 뚫는 자식 (b) : 열린 public 스텝을 빈 껍데기로 오버라이드한다.
class LazyTeacher extends Teacher {
    @Override
    public void 출석부르기() { /* 아무것도 안 함 */ }   // 골격은 그대로인데 스텝 속이 비었다.
    @Override
    public void 강의하기() { System.out.println("건성 강의하기"); }
}

public class App {
    public static void main(String[] args) {
        new JavaTeacher().수업하기();     // 착한 길 : 입장→출석→자바 강의→퇴장

        System.out.println("----------------------------");

        new CheatTeacher().수업하기();    // 골격이 뚫렸다 : 순서가 엉키고 출석이 뒤로 밀린다

        System.out.println("----------------------------");

        new LazyTeacher().수업하기();     // 골격은 도는데 출석이 소리 없이 사라졌다

        // 템플릿은 만들었지만 final 도, 캡슐화도 없어 자식이 골격을 뚫는다.
        // → 골격은 잠그고(final), 변하는 스텝만 열어야 한다.
    }
}
