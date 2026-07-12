package ex01.oop.p4_responsibility.step1;

import java.util.ArrayList;
import java.util.List;

// 책임이 있는 곳에 행위를 둔다.
class Item {
    String name;
    int price;

    Item(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

class Cart {
    List<Item> items = new ArrayList<>();
}

public class App {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.items.add(new Item("키보드", 30000));
        cart.items.add(new Item("마우스", 20000));

        // 나쁨 : 장바구니가 할 계산을 main 이 대신 한다.
        int sum = 0;
        for (Item item : cart.items) {
            sum += item.price;
        }
        System.out.println("총액 : " + sum);
    }
}
