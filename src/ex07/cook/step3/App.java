package ex07.cook.step3;

// 골격을 잠갔다. 자식은 '비법' 한 칸만 채운다.
// 스텝마다 조리시간(분)을 반환하고, 조리하기()가 그 값을 더한다.

abstract class Ramen {
    // final : 골격(순서)은 못 바꾼다. 이게 템플릿 메서드.
    public final void 조리하기() {
        int total = 물끓이기() + 면넣기() + 비법() + 그릇담기();
        System.out.println("총 조리시간 : " + total + "분");
    }

    // 고정 스텝 : protected 라 밖/자식이 함부로 못 건드린다.
    protected int 물끓이기() { System.out.println("물끓이기"); return 5; }
    protected int 면넣기()   { System.out.println("면넣기"); return 3; }
    protected int 그릇담기() { System.out.println("그릇담기"); return 1; }

    // 변하는 스텝 : 자식이 반드시 채운다.
    protected abstract int 비법();
}

class HonganeRamen extends Ramen {
    @Override
    protected int 비법() { System.out.println("홍가네 비법소스 넣기"); return 2; }
}

class SnackbarRamen extends Ramen {
    @Override
    protected int 비법() { System.out.println("분식집 비법다시다 넣기"); return 1; }

    // public void 조리하기() { ... }  // ← final 이라 오버라이드하면 컴파일 에러! 골격은 못 뚫는다.
}

public class App {
    public static void main(String[] args) {
        Ramen hongane = new HonganeRamen();
        Ramen snackbar = new SnackbarRamen();

        hongane.조리하기();    // 물끓이기→면넣기→홍가네 비법→그릇담기, 11분

        System.out.println("----------------------------");

        snackbar.조리하기();   // 물끓이기→면넣기→분식집 비법→그릇담기, 10분

        // 골격은 부모가 한 번만 정하고 잠갔다(final).
        // 자식은 비법() 한 칸만 채운다. 순서를 깰 문이 없다. 시간도 자동으로 정확히 더해진다.
    }
}
