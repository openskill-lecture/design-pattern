package ex06.singleton.step1;

import java.util.ArrayList;
import java.util.List;

// 남문 문지기는 한 명이어야 하는데, 아무나 new 하면?

abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// 문지기 : 출입금지 명단을 들고, 명단에 오른 동물만 쫓아낸다.
class Doorman {
    List<String> 금지명단 = new ArrayList<>();

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
        // 남문 하나. 그런데 생성자가 열려 있어 코드 여기저기서 new Doorman() 을 한다.
        Doorman 관리실 = new Doorman();   // 관리실에서 만든 문지기
        Doorman 남문앞 = new Doorman();   // 실제 남문 앞에 선 문지기

        관리실.출입금지("호랑이");         // 관리실에서 호랑이를 금지시켰다

        System.out.println("----------------------------");

        // 그런데 문 앞에 선 건 딴 문지기라 명단을 모른다.
        남문앞.검문(new Tiger());         // 통과 ← 금지시킨 호랑이가 그냥 들어온다! (구멍)
        System.out.println("관리실 == 남문앞 ? " + (관리실 == 남문앞));  // false

        // 명단을 '합치면' 될까? 검문하는 순간 문 앞 문지기가 최신 명단을 갖고 있어야 한다.
        // 문지기가 여럿이면 명단이 갈려 그 순간 뚫린다. 하나로 묶으려면? (step2)
    }
}
