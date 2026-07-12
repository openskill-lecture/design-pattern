package ex04.doorman.step4;

// 조합으로 감싸기 : 프록시가 문지기를 품어서(HAS-A) 곁다리 일을 얹는다.
// 감쌀 원본을 밖에서 주입받으니, 물려받는 것보다 유연하다.
abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// [원본] 그대로 본업만. (인터페이스로 바꾸지 않는다 — 구체 Doorman 그대로)
class Doorman {
    void 쫓아내(Animal a) {
        System.out.println(a.getName() + " 쫓아내");
    }
}

// [프록시-조합] Doorman을 필드로 품는다(has-a). 감쌀 원본은 생성자로 주입받는다.
class DoormanProxy2 {
    private final Doorman real;   // has-a : 원본을 품어서 감싼다

    DoormanProxy2(Doorman real) {
        this.real = real;
    }

    void 쫓아내(Animal a) {
        System.out.println("[출입대장] " + a.getName() + " 퇴장 기록");   // 부가기능은 프록시가
        real.쫓아내(a);                                                 // 본업은 품고 있는 원본에게 위임
    }
}

public class App {
    public static void main(String[] args) {
        // 감쌀 원본을 밖에서 만들어 주입한다. 프록시는 '물려받은' 게 아니라 '품은' 것.
        DoormanProxy2 doorman = new DoormanProxy2(new Doorman());
        doorman.쫓아내(new Tiger());   // [출입대장] 호랑이 ... → 호랑이 쫓아내
        doorman.쫓아내(new Mouse());

        System.out.println("----------------------------");

        // 로그가 필요 없으면 원본만 그대로 쓰면 된다.
        Doorman pure = new Doorman();
        pure.쫓아내(new Mouse());       // 쥐 쫓아내 (기록 없음)
    }
}
