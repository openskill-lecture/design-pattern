package ex07.cook.step1;

// 템플릿이 없다. 조리 흐름을 누가 지켜줄까?
// 가게마다 조리하기()에 물끓이기→면넣기→비법→그릇담기를 통째로 적는다.

// 홍가네 라면집 : 흐름을 직접 다 적는다.
class HonganeRamen {
    void 조리하기() {
        System.out.println("물끓이기");
        System.out.println("면넣기");
        System.out.println("홍가네 비법소스 넣기");
        System.out.println("그릇담기");
    }
}

// 분식집 : 똑같은 물끓이기/그릇담기를 또 복붙한다.
// 그런데 복붙하다 면넣기를 깜빡했다! (막아주는 게 아무것도 없다)
class SnackbarRamen {
    void 조리하기() {
        System.out.println("물끓이기");
        // 면넣기  ← 깜빡 빠뜨림. 아무도 안 잡아준다.
        System.out.println("분식집 비법다시다 넣기");
        System.out.println("그릇담기");
    }
}

public class App {
    public static void main(String[] args) {
        new HonganeRamen().조리하기();

        System.out.println("----------------------------");

        new SnackbarRamen().조리하기();   // 면이 빠졌다! 흐름이 제각각이다.

        // 물끓이기/그릇담기가 가게마다 똑같이 반복(중복)되고,
        // 순서와 스텝을 강제하는 게 없어 한 곳이 깜빡하면 그대로 뚫린다(면 없는 라면).
        // → 변하지 않는 흐름(골격)을 한 곳으로 끌어올려야 한다.
    }
}
