package ex07.exercise.beverage.start;

// [연습문제2 - 템플릿 메서드] 음료 만드는 순서는 같고, '두 스텝'만 다르다.
//
// 카페인 음료는 물끓이기 → 우려내기 → 컵에따르기 → 첨가하기 순서로 만든다.
// 물끓이기/컵에따르기는 어떤 음료든 똑같다. 우려내기/첨가하기만 음료마다 다르다.
// 그런데 지금은 커피/홍차가 각자 만들기()에 네 스텝을 통째로 복붙한다.
//   → 물끓이기/컵에따르기가 중복되고, 순서를 강제하는 게 없다.
//
// ── 먼저 이대로 실행해 보라 ──────────────────
// 커피와 홍차가 같은 물끓이기/컵에따르기를 각자 출력한다. (똑같은 코드가 두 번)
//
// 이번엔 변하는 스텝이 '두 개'다 (우려내기, 첨가하기). 둘 다 abstract 로 연다.
//
// TODO 1) 부모 abstract class CaffeineBeverage 를 만든다.
//
// TODO 2) 템플릿 메서드 만들기() 로 골격을 옮기고 final 로 잠근다:
//         public final void 만들기() {
//             물끓이기();
//             우려내기();
//             컵에따르기();
//             첨가하기();
//         }
//
// TODO 3) 고정 스텝은 protected 로 부모가 구현한다:
//         protected void 물끓이기()   { System.out.println("물 끓이기"); }
//         protected void 컵에따르기() { System.out.println("컵에 따르기"); }
//
// TODO 4) 변하는 스텝은 '두 개' 모두 abstract:
//         protected abstract void 우려내기();
//         protected abstract void 첨가하기();
//
// TODO 5) Coffee / Tea 가 CaffeineBeverage 를 상속하고 그 두 칸만 채운다.
//         - Coffee 우려내기 : "필터에 커피가루 내리기"   / 첨가하기 : "설탕 넣기"
//         - Tea   우려내기 : "찻잎 우려내기"            / 첨가하기 : "레몬 넣기"
//
// TODO 6) main 에서 CaffeineBeverage 타입으로 받아 만들기() 를 호출하라.

// 커피 : 흐름을 직접 다 적는다.
class Coffee {
    void 만들기() {
        System.out.println("물 끓이기");
        System.out.println("필터에 커피가루 내리기");
        System.out.println("컵에 따르기");
        System.out.println("설탕 넣기");
    }
}

// 홍차 : 똑같은 물끓이기/컵에따르기를 또 복붙한다. (여기가 문제)
class Tea {
    void 만들기() {
        System.out.println("물 끓이기");
        System.out.println("찻잎 우려내기");
        System.out.println("컵에 따르기");
        System.out.println("레몬 넣기");
    }
}

public class App {
    public static void main(String[] args) {
        new Coffee().만들기();

        System.out.println("----------------------------");

        new Tea().만들기();   // 물끓이기/컵에따르기가 커피와 똑같이 반복된다.

        // 여기서부터 네 코드 (TODO 완료 후):
        // CaffeineBeverage coffee = new Coffee();
        // CaffeineBeverage tea    = new Tea();
        // coffee.만들기();
        // tea.만들기();
    }
}
