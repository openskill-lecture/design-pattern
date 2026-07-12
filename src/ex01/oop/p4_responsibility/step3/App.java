package ex01.oop.p4_responsibility.step3;

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

class Cart {
    private List<Item> items = new ArrayList<>(); // 캡슐화 시킨다.

    public void 담는다(Item item) {
        items.add(item);
    }

    // 데이터를 가진 자가 계산한다 (책임이 있는 곳에 행위)
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

        // 좋음 : 계산 로직은 오직 Cart.총액() 한 곳에만 존재한다.
        // 할인 정책이 생겨도 여기 한 곳만 고치면 된다.
        System.out.println("총액 : " + cart.총액());
    }
}
