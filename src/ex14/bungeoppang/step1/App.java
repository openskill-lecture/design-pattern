package ex14.bungeoppang.step1;

// [step1] 굽는 흐름이 가게마다 복사되어 있다.
// 지금은 잘 돌아간다. 하지만 새 붕어빵을 넣을 때마다 이 흐름을 통째로 또 복사해야 한다.

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

class PatShop {
    public Bungeoppang 굽기() {
        System.out.println("가게 문을 연다.");
        Bungeoppang bungeoppang = new PatBung();
        System.out.println("붕어빵을 건넨다.");
        return bungeoppang;
    }
}

class CreamShop {
    public Bungeoppang 굽기() {
        System.out.println("가게 문을 연다.");   // ← PatShop과 똑같은 흐름을 복사했다
        Bungeoppang bungeoppang = new CreamBung();
        System.out.println("붕어빵을 건넨다.");
        return bungeoppang;
    }
}

// 붕어빵집을 하나 더 늘리면? 이 흐름 전체를 또 복사해야 한다.
// "문을 연다" 대사를 바꾸려면 이제 몇 군데를 고쳐야 할까?

public class App {
    public static void main(String[] args) {
        PatShop patShop = new PatShop();
        patShop.굽기().먹는다();

        System.out.println("----------------------------");

        CreamShop creamShop = new CreamShop();
        creamShop.굽기().먹는다();
    }
}
