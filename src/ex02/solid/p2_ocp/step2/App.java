package ex02.solid.p2_ocp.step2;

// 완성코드 : 가격은 각 음료가 스스로 안다. 가격표(Menu)는 묻기만 한다.
abstract class Drink {
    abstract String getName();
    abstract int 가격();
}

class Americano extends Drink {
    String getName() { return "아메리카노"; }
    int 가격() { return 4000; }
}

class Latte extends Drink {
    String getName() { return "라떼"; }
    int 가격() { return 4500; }
}

// 새 음료 = 클래스 추가만. Menu도, 기존 음료도 안 건드린다.
class VanillaLatte extends Drink {
    String getName() { return "바닐라라떼"; }
    int 가격() { return 5000; }
}

class Menu {
    void 출력(Drink drink) {
        System.out.println(drink.getName() + " : " + drink.가격());
    }
}

public class App {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Drink[] drinks = { new Americano(), new Latte(), new VanillaLatte() };

        for (Drink drink : drinks) {
            menu.출력(drink);   // 각자 자기 가격을 말한다
        }

        // VanillaLatte를 추가했지만 Menu는 한 줄도 안 고쳤다. ← OCP (확장에 열려있다)
    }
}
