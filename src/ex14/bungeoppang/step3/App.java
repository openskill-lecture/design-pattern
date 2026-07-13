package ex14.bungeoppang.step3;

// [step3] 흐름은 부모가 쥐고, "무엇을 만들지"는 자식에게 통로로 넘긴다.
// 부모 굽기()는 반죽만들기()를 부를 뿐, 무엇이 만들어지는지 모른다. 그게 팩토리 메서드다.

interface Bungeoppang {
    void 먹는다();
    int 가격();
}

class PatBung implements Bungeoppang {
    public void 먹는다() {
        System.out.println("팥붕어빵: 팥이 가득! (" + 가격() + "원)");
    }
    public int 가격() {
        return 1500;
    }
}

class CreamBung implements Bungeoppang {
    public void 먹는다() {
        System.out.println("슈크림붕어빵: 슈크림이 가득! (" + 가격() + "원)");
    }
    public int 가격() {
        return 2000;
    }
}

abstract class BungeoppangShop {
    // 팩토리 메서드 — 무엇을 만들지는 자식이 정한다. 부모는 통로만 뚫어둔다.
    protected abstract Bungeoppang 반죽만들기();

    // 굽는 공통 흐름은 부모가 고정한다. 부모는 "무엇이" 만들어지는지 모른다.
    public Bungeoppang 굽기() {
        System.out.println("가게 문을 연다.");
        Bungeoppang bungeoppang = 반죽만들기();   // ← 자식이 채운 반죽만들기()가 여기서 불린다 (동적바인딩)
        System.out.println("붕어빵을 건넨다.");
        return bungeoppang;
    }
}

class PatShop extends BungeoppangShop {
    protected Bungeoppang 반죽만들기() {
        return new PatBung();
    }
}

class CreamShop extends BungeoppangShop {
    protected Bungeoppang 반죽만들기() {
        return new CreamBung();
    }
}

// 흐름은 부모, 무엇을 만들지는 자식. 새 붕어빵(예: 초코붕어빵)을 추가해도
// 부모 굽기()는 한 줄도 고치지 않는다 — ChocoShop 하나만 늘어난다.

public class App {
    public static void main(String[] args) {
        BungeoppangShop patShop = new PatShop();
        patShop.굽기().먹는다();

        System.out.println("----------------------------");

        BungeoppangShop creamShop = new CreamShop();
        creamShop.굽기().먹는다();   // step2와 달리, 이제 진짜 슈크림붕어빵이 2000원에 나온다
    }
}
