package ex05.charger.step2;

import ex05.charger.lib.OuterCharger;

// 우겨넣기(함정) : 어댑터 없이, 노트북이 외부 충전기 전용 메서드를 하나 더 판다.
//                 돌아는 가지만 — 노트북이 '외부 타입'과 '변압 계산'을 직접 떠안는다.

abstract class TypeC {
    abstract int 공급전압();
}

class SamsungCharger extends TypeC {
    int 공급전압() { return 220; }
}

class AppleCharger extends TypeC {
    int 공급전압() { return 220; }
}

class Laptop {
    // 착한 길 : 우리 규격 TypeC
    void 충전(TypeC c) {
        int v = c.공급전압();
        System.out.println(v + "V — 충전 완료");
    }

    // 함정 : 외부 충전기를 받으려고 노트북에 '전용 메서드'를 또 만든다.
    //        노트북이 OuterCharger 타입을 직접 알고, 변압(110→220)까지 직접 한다.
    void 충전(OuterCharger o) {
        int v = o.voltage() * 2;   // 110 → 220 변압을 노트북이 떠안는다
        System.out.println(v + "V — 충전 완료 (노트북이 직접 변압)");
    }
}

public class App {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();
        laptop.충전(new SamsungCharger());   // 220V — 충전 완료

        System.out.println("----------------------------");

        // 외부 충전기도 충전은 된다. 하지만 노트북이 외부 타입 + 변압을 직접 떠안았다.
        OuterCharger outerCharger = new OuterCharger();
        laptop.충전(outerCharger);   // 220V — 충전 완료 (노트북이 직접 변압)

        // 외부 충전기 종류가 늘 때마다 노트북에 전용 메서드를 또 판다. 노트북이 지저분해진다.
        // → 이 지저분함(외부 타입·변압)을 노트북 밖으로 빼내자. (step3)
    }
}
