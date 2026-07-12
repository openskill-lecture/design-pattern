package ex07.exercise.beverage.answer;

// [연습문제2 정답 - 템플릿 메서드] 골격은 부모가, 우려내기/첨가하기만 자식이 채운다.

abstract class CaffeineBeverage {
    // 템플릿 메서드 : 음료 만드는 순서(골격)를 정하고 final 로 잠근다.
    public final void 만들기() {
        물끓이기();
        우려내기();
        컵에따르기();
        첨가하기();
    }

    // 고정 스텝 : 어떤 음료든 똑같다. 부모가 구현.
    protected void 물끓이기()   { System.out.println("물 끓이기"); }
    protected void 컵에따르기() { System.out.println("컵에 따르기"); }

    // 변하는 스텝 : 음료마다 다르다. 자식이 반드시 채운다.
    protected abstract void 우려내기();
    protected abstract void 첨가하기();
}

class Coffee extends CaffeineBeverage {
    @Override
    protected void 우려내기() { System.out.println("필터에 커피가루 내리기"); }
    @Override
    protected void 첨가하기() { System.out.println("설탕 넣기"); }
}

class Tea extends CaffeineBeverage {
    @Override
    protected void 우려내기() { System.out.println("찻잎 우려내기"); }
    @Override
    protected void 첨가하기() { System.out.println("레몬 넣기"); }
}

public class App {
    public static void main(String[] args) {
        CaffeineBeverage coffee = new Coffee();
        CaffeineBeverage tea    = new Tea();

        coffee.만들기();     // 물끓이기→커피가루→컵에따르기→설탕

        System.out.println("----------------------------");

        tea.만들기();        // 물끓이기→찻잎→컵에따르기→레몬

        // 물끓이기/컵에따르기는 부모에 한 번만. 자식은 우려내기/첨가하기 두 칸만 채운다.
    }
}
