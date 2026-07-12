package ex10.exercise.pizza.answer;

// [정답1] 데코레이터 패턴 — 토핑이 피자를 '감싸서' 가격·설명을 덧붙인다.
// 토핑 하나 늘어도 클래스는 하나만 추가. 런타임에 자유 조합.

interface Pizza {                         // 통로(약속)
    int 가격();
    String 설명();
}

class PlainPizza implements Pizza {       // 알맹이
    @Override public int 가격()   { return 10000; }
    @Override public String 설명() { return "기본피자"; }
}

abstract class Topping implements Pizza {  // 감싸는 포장(데코레이터)
    protected Pizza base;                  // 감싸고 있는 대상
    Topping(Pizza base) { this.base = base; }
}

class Cheese extends Topping {
    Cheese(Pizza base) { super(base); }
    @Override public int 가격()   { return base.가격() + 2000; }
    @Override public String 설명() { return base.설명() + "+치즈"; }
}

class Potato extends Topping {
    Potato(Pizza base) { super(base); }
    @Override public int 가격()   { return base.가격() + 3000; }
    @Override public String 설명() { return base.설명() + "+감자"; }
}

class Bulgogi extends Topping {
    Bulgogi(Pizza base) { super(base); }
    @Override public int 가격()   { return base.가격() + 4000; }
    @Override public String 설명() { return base.설명() + "+불고기"; }
}

public class App {
    public static void main(String[] args) {
        // 기본피자를 감싸며 토핑을 덧붙인다 — 런타임에 원하는 만큼 조합
        Pizza pizza = new Bulgogi(new Cheese(new PlainPizza()));
        System.out.println(pizza.설명() + " : " + pizza.가격() + "원");

        System.out.println("----------------------------");
        // 감자만 추가한 피자? 클래스 안 만들고 감싸기만 바꾸면 끝.
        Pizza pizza2 = new Potato(new PlainPizza());
        System.out.println(pizza2.설명() + " : " + pizza2.가격() + "원");
        // 토핑을 더하든 빼든 조합만 바꾸면 된다. 새 클래스가 필요 없다.
    }
}
