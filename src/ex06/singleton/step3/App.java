package ex06.singleton.step3;

import java.util.ArrayList;
import java.util.List;

// 생성자를 막았다. 이제 남문 문지기는 언제나 딱 한 명, 명단도 하나다.

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
    // 하나만 미리 만든다.
    private static final Doorman instance = new Doorman();

    List<String> 금지명단 = new ArrayList<>();

    // 생성자를 private으로 닫는다. 밖에서 new Doorman() 못한다.
    private Doorman() {}

    // 그 하나를 꺼내주는 유일한 통로.
    static Doorman getInstance() {
        return instance;
    }

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
        // Doorman c = new Doorman();
        // ↑ 주석을 풀면 컴파일 에러! 생성자가 private이라 문이 닫혔다.

        // 남문 문지기를 얻는 길은 getInstance() 하나뿐. 늘 같은 한 명, 같은 명단이다.
        Doorman a = Doorman.getInstance();
        Doorman b = Doorman.getInstance();

        a.출입금지("호랑이");
        b.검문(new Tiger());   // 쫓아내! ← 어디서 얻든 같은 명단이라 항상 막힌다
        b.검문(new Mouse());   // 통과 ← 명단에 없는 쥐는 지나간다

        System.out.println("----------------------------");

        System.out.println("a == b ? " + (a == b));  // true — 우회할 문이 없다
    }
}
