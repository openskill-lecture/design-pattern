package ex05.adapter.step2;

import ex05.adapter.lib.OuterRabbit;

// 어댑터가 맞춘다 : RabbitAdapter가 Animal인 척하며, 안에서 외부 토끼를 부린다.

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
    void 쫓아내(Animal a) {
        System.out.println(a.getName() + " 쫓아내");
    }
}

// [어댑터] Animal 규격을 뒤집어쓰고, 외부 토끼를 품는다.
// 문지기가 getName()을 부르면 → 안에서 토끼의 getFullname()으로 넘긴다. (이름만 바꿔 끼운다)
class RabbitAdapter extends Animal {
    private OuterRabbit outerRabbit;

    RabbitAdapter(OuterRabbit outerRabbit) {
        this.outerRabbit = outerRabbit;
    }

    String getName() {
        return outerRabbit.getFullname();   // 규격 변환 : getName() → getFullname()
    }
}

public class App {
    public static void main(String[] args) {
        Doorman doorman = new Doorman();
        doorman.쫓아내(new Tiger());   // 호랑이 쫓아내
        doorman.쫓아내(new Mouse());   // 쥐 쫓아내

        System.out.println("----------------------------");

        // 외부 토끼를 어댑터로 감싸면, 문지기 눈엔 그냥 Animal이다.
        Animal rabbit = new RabbitAdapter(new OuterRabbit());
        doorman.쫓아내(rabbit);        // 토끼 쫓아내 — 문지기는 이게 외부 토끼인 줄 모른다
    }
}
