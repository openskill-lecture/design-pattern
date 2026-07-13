package ex05.charger.step2;

import ex05.charger.lib.OuterCharger;

// 어댑터가 맞춘다 : ChargerAdapter가 TypeC인 척하며, 안에서 외부 충전기를 부린다.

abstract class TypeC {
    abstract void 충전한다();
}

class SamsungCharger extends TypeC {
    void 충전한다() { System.out.println("삼성 충전기로 충전 중..."); }
}

class AppleCharger extends TypeC {
    void 충전한다() { System.out.println("애플 충전기로 충전 중..."); }
}

class Laptop {
    void 충전(TypeC c) {
        c.충전한다();
    }
}

// [어댑터] TypeC 규격을 뒤집어쓰고, 외부 충전기를 품는다.
// 노트북이 충전한다()를 부르면 → 안에서 충전기의 둥근핀충전()으로 넘긴다. (규격만 바꿔 끼운다)
class ChargerAdapter extends TypeC {
    private OuterCharger outerCharger;

    ChargerAdapter(OuterCharger outerCharger) {
        this.outerCharger = outerCharger;
    }

    void 충전한다() {
        outerCharger.둥근핀충전();   // 규격 변환 : 충전한다() → 둥근핀충전()
    }
}

public class App {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();
        laptop.충전(new SamsungCharger());   // 삼성 충전기로 충전 중...
        laptop.충전(new AppleCharger());     // 애플 충전기로 충전 중...

        System.out.println("----------------------------");

        // 외부 충전기를 어댑터로 감싸면, 노트북 눈엔 그냥 TypeC다.
        TypeC c = new ChargerAdapter(new OuterCharger());
        laptop.충전(c);   // 둥근핀으로 충전 중... — 노트북은 이게 외부 충전기인 줄 모른다
    }
}
