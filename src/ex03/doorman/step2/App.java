package ex03.doorman.step2;

// 틀린코드 : 호랑이가 나타났다. 문지기에게 호랑이도 쥐도 다 떠안겼다.
// 문지기가 동물 종류를 하나하나 "구체 타입"으로 들고 있다.
class Tiger {
    String getName() { return "호랑이"; }
}

class Mouse {
    String getName() { return "쥐"; }
}

// 동물이 늘 때마다 필드·생성자·쫓아내()가 전부 뚱뚱해진다.
class Doorman {
    private Tiger tiger;
    private Mouse mouse;

    Doorman(Tiger tiger, Mouse mouse) {   // 동물 종류만큼 파라미터가 늘어난다
        this.tiger = tiger;
        this.mouse = mouse;
    }

    void 쫓아내() {
        System.out.println(tiger.getName() + " 쫓아내");
        System.out.println(mouse.getName() + " 쫓아내");
        // Rabbit이 오면? 필드도, 생성자도, 이 메서드도 전부 다시 고쳐야 한다.
        // 구체 타입 하나하나에 의존 → 동물이 늘 때마다 문지기가 망가진다 (OCP 위배)
    }
}

public class App {
    public static void main(String[] args) {
        Doorman doorman = new Doorman(new Tiger(), new Mouse());
        doorman.쫓아내();
        // 출력 :
        //   호랑이 쫓아내
        //   쥐 쫓아내
        // 동물이 셋, 넷 늘어나면 문지기를 계속 뜯어고쳐야 한다. 이게 맞나? → step3
    }
}
