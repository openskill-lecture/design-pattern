package ex04.doorman.step3;

// 완성코드 : 곁다리 일(기록)은 프록시에게 맡긴다.
// 원본 문지기는 다시 "쫓아내기"만 하는 순수한 몸이 된다.
abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// 클라이언트는 이 타입만 안다. 원본인지 프록시인지 모른다(투명).
interface Doorman {
    void 쫓아내(Animal a);
}

// [원본 real subject] 오직 본업만. SRP 회복.
class RealDoorman implements Doorman {
    public void 쫓아내(Animal a) {
        System.out.println(a.getName() + " 쫓아내");
    }
}

// [프록시] 같은 타입(Doorman)으로 원본을 감싸고, 곁다리 일을 얹은 뒤 원본에 위임.
class DoormanProxy implements Doorman {
    private final Doorman real;   // has-a : 원본을 감싼다

    DoormanProxy(Doorman real) {
        this.real = real;
    }

    public void 쫓아내(Animal a) {
        System.out.println("[출입대장] " + a.getName() + " 퇴장 기록");   // 부가기능은 프록시가
        real.쫓아내(a);                                                 // 본업은 원본에게 위임
    }
}

public class App {
    public static void main(String[] args) {
        // 클라이언트는 Doorman 타입만 본다. 뒤에 프록시가 끼었는지 모른다.
        Doorman doorman = new DoormanProxy(new RealDoorman());
        doorman.쫓아내(new Tiger());
        doorman.쫓아내(new Mouse());

        System.out.println("----------------------------");

        // 로그가 필요 없으면? 프록시를 빼고 원본만 꽂으면 된다. 원본은 한 줄도 안 바뀐다.
        Doorman pure = new RealDoorman();
        pure.쫓아내(new Mouse());
    }
}
