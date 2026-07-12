package ex08.compare.proxy;

// [프록시] 매니저가 연예인 '앞에' 같은 얼굴로 선다. 섭외자는 연예인인 줄 안다.
// 매니저 ──품는다(has-a)──▶ 진짜 연예인.  겉모습이 같다(둘 다 Celebrity) = 대역.
interface Celebrity {
    void 섭외응답(String program);
}

// [진짜] 실제로 출연을 승낙하는 연예인
class RealStar implements Celebrity {
    @Override
    public void 섭외응답(String program) {
        System.out.println("연예인 : " + program + " 출연하겠습니다.");
    }
}

// [프록시] 진짜 연예인과 같은 겉모습(Celebrity). 진짜를 품고(has-a) 앞에서 거른다.
class ManagerProxy implements Celebrity {
    private final Celebrity real;   // 매니저가 진짜 연예인을 품는다

    ManagerProxy(Celebrity real) {
        this.real = real;
    }

    @Override
    public void 섭외응답(String program) {
        System.out.println("[매니저] " + program + " 스케줄·조건 확인");   // 문지기(부가기능)는 매니저가
        real.섭외응답(program);                                          // 통과되면 본업은 진짜에게 넘김
    }
}

public class App {
    public static void main(String[] args) {
        // 섭외자는 Celebrity(연예인)에게 요청한다고 생각한다. 사실은 매니저(대역)가 앞에서 받는다.
        Celebrity star = new ManagerProxy(new RealStar());
        star.섭외응답("음악방송");   // [매니저] 확인 → 연예인 출연

        System.out.println("----------------------------");

        // 매니저를 진짜 연예인으로 바꿔 껴도 섭외자 코드는 그대로다(같은 Celebrity 타입). = 대역이 가능하다.
        Celebrity direct = new RealStar();
        direct.섭외응답("라디오");   // 문지기 없이 바로 (매니저 안 거침)
    }
}
