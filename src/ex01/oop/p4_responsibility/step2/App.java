package ex01.oop.p4_responsibility.step2;

import java.util.ArrayList;
import java.util.List;

class Item {
    private String name;
    private int price;

    Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

// 총액()이라는 행위는 있지만, 목록도 그대로 내어준다.
class Cart {
    public List<Item> items = new ArrayList<>();

    public void 담는다(Item item) {
        items.add(item);
    }

    public int 총액() {
        int sum = 0;
        for (Item item : items) {
            sum += item.getPrice();
        }
        return sum;
    }
}

public class App {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.담는다(new Item("키보드", 30000));
        cart.담는다(new Item("마우스", 20000));

        System.out.println("총액 : " + cart.총액()); // 책임자에게 시킨다 (착한 길)

        System.out.println("----------------------------");

        // 하지만 목록이 열려있어, 다른 화면에선 여전히 직접 합산한다 → 같은 로직이 복제된다
        int sum = 0;
        for (Item item : cart.items) {
            sum += item.getPrice();
        }
        System.out.println("직접 계산한 총액 : " + sum);
    }
}
