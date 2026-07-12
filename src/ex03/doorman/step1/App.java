package ex03.doorman.step1;

// 시작코드 : 세상에 쥐밖에 없던 시절.
// 문지기는 "쥐"라는 구체 타입 하나만 알고, 그 쥐를 쫓아낸다.
class Mouse {
    String getName() { return "쥐"; }
}

// 문지기가 구체 타입(Mouse)에 딱 붙어 있다.
class Doorman {
    private Mouse mouse;

    Doorman(Mouse mouse) {
        this.mouse = mouse;
    }

    void 쫓아내() {
        System.out.println(mouse.getName() + " 쫓아내");
    }
}

public class App {
    public static void main(String[] args) {
        Doorman doorman = new Doorman(new Mouse());
        doorman.쫓아내();   // 쥐 쫓아내

        // 아직은 평화롭다. 그런데... 호랑이가 나타나면? → step2
    }
}
