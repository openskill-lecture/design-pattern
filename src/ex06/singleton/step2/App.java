package ex06.singleton.step2;

import java.util.ArrayList;
import java.util.List;

// 남문에 문지기를 딱 하나 세워 다같이 쓰게 했다. 그런데 생성자를 안 막았다. 뚫릴까?

abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

class Doorman {
    // 남문 문지기를 미리 하나 만들어, 다같이 이 한 명을 쓰라고 내놓는다.
    static Doorman instance = new Doorman();

    List<String> 금지명단 = new ArrayList<>();

    // 생성자를 막지 않았다! 밖에서 아직 new Doorman() 이 된다.
    Doorman() {}

    void 출입금지(String name) {
        금지명단.add(name);
        System.out.println("[명단 등록] " + name + " 출입금지");
    }

    void 검문(Animal a) {
        if (금지명단.contains(a.getName())) {
            System.out.println(a.getName() + " → 쫓아내! (명단에 있음)");
        } else {
            System.out.println(a.getName() + " → 통과");
        }
    }
}

public class App {
    public static void main(String[] args) {
        // 착한 길 : 남문에 세운 한 명(instance)을 같이 쓴다. 명단이 한 곳에 모인다.
        Doorman a = Doorman.instance;
        Doorman b = Doorman.instance;

        a.출입금지("호랑이");
        b.검문(new Tiger());   // 쫓아내! ← 같은 명단이라 통한다
        System.out.println("a == b ? " + (a == b));  // true

        System.out.println("----------------------------");

        // 뚫리는 지점 : 생성자가 열려있어 남문에 딴 문지기를 몰래 또 세운다.
        Doorman c = new Doorman();
        c.검문(new Tiger());   // 통과 ← 이 문지기는 빈 명단, 호랑이가 들어온다!
        System.out.println("a == c ? " + (a == c));  // false — 싱글톤이 깨졌다
    }
}
