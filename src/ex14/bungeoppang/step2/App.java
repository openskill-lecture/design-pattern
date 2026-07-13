package ex14.bungeoppang.step2;

// [step2] 중복이 싫어서 굽는 흐름을 부모 BungeoppangShop으로 끌어올렸다.
// 흐름은 한 곳으로 모였다. 좋아 보인다.
// 그런데 "무엇을 만들지"까지 부모가 쥐고 있다 → new PatBung() 하드코딩.
// 실행해서 CreamShop이 진짜 슈크림붕어빵을 내는지 보자.

interface Bungeoppang {
    void 먹는다();
}

class PatBung implements Bungeoppang {
    public void 먹는다() {
        System.out.println("팥붕어빵: 팥이 가득!");
    }
}

class CreamBung implements Bungeoppang {
    public void 먹는다() {
        System.out.println("슈크림붕어빵: 슈크림이 가득!");
    }
}

// 부모가 굽는 흐름을 쥔다. 그런데 무엇을 만들지까지 부모가 정해버렸다.
class BungeoppangShop {
    public Bungeoppang 굽기() {
        System.out.println("가게 문을 연다.");
        Bungeoppang bungeoppang = new PatBung();   // ← 부모가 팥으로 못박았다. 자식이 못 바꾼다!
        System.out.println("붕어빵을 건넨다.");
        return bungeoppang;
    }
}

class PatShop extends BungeoppangShop {
    // 굽기()를 그대로 물려받는다.
}

class CreamShop extends BungeoppangShop {
    // 굽기()를 그대로 물려받는다. 그런데... 슈크림은 어디서 만들지?
}

public class App {
    public static void main(String[] args) {
        BungeoppangShop patShop = new PatShop();
        patShop.굽기().먹는다();

        System.out.println("----------------------------");

        BungeoppangShop creamShop = new CreamShop();
        creamShop.굽기().먹는다();   // 슈크림 가게인데... 팥붕어빵이 나온다!
    }
}
