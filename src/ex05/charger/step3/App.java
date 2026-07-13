package ex05.charger.step3;

import ex05.charger.lib.OuterCharger;

// 어댑터가 맞춘다 : ChargerAdapter가 TypeC인 척하며,
//                  안에서 외부 충전기를 부리고 변압(110→220)까지 대신한다.

abstract class TypeC {
    abstract int 공급전압();
}

class SamsungCharger extends TypeC {
    int 공급전압() { return 220; }
}

class AppleCharger extends TypeC {
    int 공급전압() { return 220; }
}

// 노트북은 오직 TypeC만 안다. (step2의 외부 충전기 전용 메서드가 사라졌다)
class Laptop {
    void 충전(TypeC c) {
        int v = c.공급전압();
        System.out.println(v + "V — 충전 완료");
    }
}

// [어댑터] TypeC 규격을 뒤집어쓰고, 외부 충전기를 품는다.
// 노트북이 공급전압()을 부르면 → 안에서 voltage()를 받아 변압(110→220)해 돌려준다.
class ChargerAdapter extends TypeC {
    private OuterCharger outerCharger;

    ChargerAdapter(OuterCharger outerCharger) {
        this.outerCharger = outerCharger;
    }

    int 공급전압() {
        return outerCharger.voltage() * 2;   // 규격 변환 + 변압 : voltage() 110 → 220
    }
}

public class App {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();
        laptop.충전(new SamsungCharger());   // 220V — 충전 완료

        System.out.println("----------------------------");

        // 외부 충전기를 어댑터로 감싸면, 노트북 눈엔 그냥 TypeC다.
        TypeC c = new ChargerAdapter(new OuterCharger());
        laptop.충전(c);   // 220V — 충전 완료 — 노트북은 외부인 줄도, 변압이 있는 줄도 모른다
    }
}
