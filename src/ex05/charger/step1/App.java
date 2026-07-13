package ex05.charger.step1;

import ex05.charger.lib.OuterCharger;

// 안 맞는다 : 노트북은 TypeC(=충전한다())만 받는다.
//            외부 충전기는 TypeC도 아니고, 충전 메서드도 둥근핀충전() 이다.

abstract class TypeC {
    abstract void 충전한다();
}

class SamsungCharger extends TypeC {
    void 충전한다() { System.out.println("삼성 충전기로 충전 중..."); }
}

class AppleCharger extends TypeC {
    void 충전한다() { System.out.println("애플 충전기로 충전 중..."); }
}

// 노트북 : TypeC만 받는다. 오직 충전한다()로만 부른다.
class Laptop {
    void 충전(TypeC c) {
        c.충전한다();
    }
}

public class App {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();
        laptop.충전(new SamsungCharger());   // 삼성 충전기로 충전 중...
        laptop.충전(new AppleCharger());     // 애플 충전기로 충전 중...

        System.out.println("----------------------------");

        // 외부 라이브러리 충전기가 왔다. 충전은 된다 — 단, 둥근핀충전() 이다.
        OuterCharger outerCharger = new OuterCharger();
        System.out.println("외부 충전기가 왔다.");
        outerCharger.둥근핀충전();

        // laptop.충전(outerCharger);
        // ↑ 주석을 풀면 컴파일 에러! OuterCharger는 TypeC가 아니다.
        //   게다가 노트북이 부르는 충전한다()도 이 충전기엔 없다 (둥근핀충전() 뿐).
        System.out.println("→ 규격이 안 맞아 노트북에 꽂을 수 없다. 어떻게 끼울까? (step2)");
    }
}
