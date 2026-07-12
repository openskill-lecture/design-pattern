package ex10.exercise.pizza.start;

// [연습1] 피자에 토핑(치즈·감자·불고기)을 얹어 가격·설명을 누적한다.
// 지금은 '조합마다' 클래스를 새로 판다 → 토핑 하나 늘 때마다 클래스가 2배씩 폭발.
//
// TODO: 데코레이터 패턴으로 바꿔라 —
//   1) Pizza 를 인터페이스(통로)로 : int 가격(); String 설명();
//   2) PlainPizza(알맹이) 하나 + 토핑을 '감싸는' 추상 Topping.
//   3) Cheese/Potato/Bulgogi 가 Pizza 를 감싸 가격·설명을 덧붙이게.
// 목표 : new Bulgogi(new Cheese(new PlainPizza())) 처럼 런타임에 자유 조합. (정답은 answer 폴더)

class PlainPizza {
    int 가격()   { return 10000; }
    String 설명() { return "기본피자"; }
}

class CheesePizza {                       // +치즈
    int 가격()   { return 12000; }
    String 설명() { return "기본피자+치즈"; }
}

class PotatoPizza {                       // +감자
    int 가격()   { return 13000; }
    String 설명() { return "기본피자+감자"; }
}

class CheesePotatoPizza {                 // +치즈+감자
    int 가격()   { return 15000; }
    String 설명() { return "기본피자+치즈+감자"; }
}
// 여기에 '불고기'까지 넣으면? CheeseBulgogiPizza, PotatoBulgogiPizza,
// CheesePotatoBulgogiPizza … 토핑 하나 늘 때마다 클래스가 2배씩 폭발한다.

public class App {
    public static void main(String[] args) {
        // 치즈+감자 피자 주문 (조합 클래스를 콕 집어 골라야 한다)
        CheesePotatoPizza pizza = new CheesePotatoPizza();
        System.out.println(pizza.설명() + " : " + pizza.가격() + "원");

        System.out.println("----------------------------");
        // 손님 : "감자만 빼주세요" → CheesePizza 로 갈아끼워야 한다. 런타임 변경 불가.
        System.out.println("손님 : (감자만 빼고 싶은데... 클래스를 통째로 바꿔야 하네요)");
        // → 토핑을 더하거나 빼려면 매번 새 클래스가 필요하다. 데코레이터로 바꿔보자.
    }
}
