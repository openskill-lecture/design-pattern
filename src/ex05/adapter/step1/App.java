package ex05.adapter.step1;

import ex05.adapter.lib.OuterRabbit;

// 안 맞는다 : 문지기는 Animal(=getName())만 쫓아낸다.
//            외부 토끼는 Animal도 아니고, 이름 메서드도 getFullname() 이다.

abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// 문지기 : Animal만 받는다. 오직 getName()으로만 부른다.
class Doorman {
    void 쫓아내(Animal a) {
        System.out.println(a.getName() + " 쫓아내");
    }
}

public class App {
    public static void main(String[] args) {
        Doorman doorman = new Doorman();
        doorman.쫓아내(new Tiger());   // 호랑이 쫓아내
        doorman.쫓아내(new Mouse());   // 쥐 쫓아내

        System.out.println("----------------------------");

        // 외부 라이브러리 토끼가 왔다. 이름은 있다 — 단, getFullname() 이다.
        OuterRabbit rabbit = new OuterRabbit();
        System.out.println("외부 토끼가 왔다. 이름 : " + rabbit.getFullname());

        // doorman.쫓아내(rabbit);
        // ↑ 주석을 풀면 컴파일 에러! OuterRabbit은 Animal이 아니다.
        //   게다가 문지기가 부르는 getName()도 이 토끼엔 없다 (getFullname() 뿐).
        System.out.println("→ 규격이 안 맞아 문지기에게 줄 수 없다. 어떻게 끼울까? (step2)");
    }
}
