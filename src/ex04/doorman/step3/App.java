package ex04.doorman.step3;

// 상속으로 감싸기 : 프록시가 문지기를 물려받아(IS-A) 곁다리 일을 얹는다.
abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// [원본] 문지기는 다시 본업(쫓아내기)만 하는 순수한 몸이 된다.
class Doorman {
    void 쫓아내(Animal a) {
        System.out.println(a.getName() + " 쫓아내");
    }
}

// [프록시-상속] Doorman을 물려받아 같은 쫓아내()를 재정의한다.
// 곁다리 일(기록)을 먼저 하고, 진짜 일은 부모(super)에게 맡긴다.
class DoormanProxy extends Doorman {
    @Override
    void 쫓아내(Animal a) {
        System.out.println("[출입대장] " + a.getName() + " 퇴장 기록");   // 부가기능은 프록시가
        super.쫓아내(a);                                                 // 본업은 원본(부모)에게 위임
    }
}

public class App {
    public static void main(String[] args) {
        // 상속이라 프록시는 그냥 Doorman이다. 클라이언트는 Doorman 타입으로 쓴다.
        Doorman doorman = new DoormanProxy();
        doorman.쫓아내(new Tiger());   // [출입대장] 호랑이 ... → 호랑이 쫓아내
        doorman.쫓아내(new Mouse());

        System.out.println("----------------------------");

        // 로그가 필요 없으면 원본만. 원본은 한 줄도 안 바뀐다.
        Doorman pure = new Doorman();
        pure.쫓아내(new Mouse());       // 쥐 쫓아내 (기록 없음)
    }
}
