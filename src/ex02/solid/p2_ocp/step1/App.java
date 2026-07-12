package ex02.solid.p2_ocp.step1;

// 새 음료가 생길 때마다 가격표를 뜯어고쳐야 할까?
class Menu {
    int 가격(String drink) {
        if (drink.equals("아메리카노")) return 4000;
        else if (drink.equals("라떼")) return 4500;
        return 0;
    }
}

public class App {
    public static void main(String[] args) {
        Menu menu = new Menu();
        System.out.println("아메리카노 : " + menu.가격("아메리카노")); // 4000
        System.out.println("라떼 : " + menu.가격("라떼"));           // 4500

        // 바닐라라떼를 추가하려면? Menu의 if-else를 또 뜯어고쳐야 한다.
        // 새 음료마다 기존 코드를 수정한다. ← OCP 위반 (확장에 닫혀있다)
        // [실습] 음료를 Drink 자식으로 만들고 가격을 각자 갖게 해, Menu를 안 고치게 바꿔보자 → step2
    }
}
