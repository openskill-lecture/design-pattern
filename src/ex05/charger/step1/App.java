package ex05.charger.step1;

import ex05.charger.lib.OuterCharger;

// 안 맞는다 : 노트북은 TypeC(=공급전압(), 220V)만 받는다.
//            외부 충전기는 TypeC도 아니고, 메서드도 voltage(), 전압도 110V 다.

abstract class TypeC {
    abstract int 공급전압();   // 노트북에 몇 V를 주는가
}

class SamsungCharger extends TypeC {
    int 공급전압() { return 220; }
}

class AppleCharger extends TypeC {
    int 공급전압() { return 220; }
}

// 노트북 : TypeC만 받는다. 220V라야 충전된다.
class Laptop {
    void 충전(TypeC c) {
        int v = c.공급전압();
        if (v == 220) System.out.println(v + "V — 충전 완료");
        else          System.out.println(v + "V — 전압이 안 맞아 충전 실패!");
    }
}

public class App {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();
        laptop.충전(new SamsungCharger());   // 220V — 충전 완료
        laptop.충전(new AppleCharger());     // 220V — 충전 완료

        System.out.println("----------------------------");

        // 외부(해외) 충전기가 왔다. 전압은 주는데 — 규격이 두 군데나 어긋난다.
        OuterCharger outerCharger = new OuterCharger();
        System.out.println("외부 충전기가 왔다. 전압 : " + outerCharger.voltage() + "V");
        //  ↑ (1) 메서드가 공급전압()이 아니라 voltage()   (2) 값이 220이 아니라 110

        // laptop.충전(outerCharger);
        // ↑ 주석을 풀면 컴파일 에러! OuterCharger는 TypeC가 아니다.
        //   설령 값을 꺼내도 110V라 노트북엔 안 맞는다.
        System.out.println("→ 타입도 값도 안 맞아 노트북에 꽂을 수 없다. 어떻게 끼울까? (step2)");
    }
}
