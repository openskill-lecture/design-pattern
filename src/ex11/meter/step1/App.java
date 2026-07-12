package ex11.meter.step1;

// 만보기 라이브러리는 30일 걸린다. 그런데 나(프론트)는 10일 안에 화면을 만들어야 한다.
// step1 : 진짜(RealMeter)에 직접 묶여 있으면, 진짜가 나올 때까지 한 줄도 못 돌린다.

// 진짜 만보기 : 실제 센서가 있어야 동작. 아직 개발 중(30일)이라 값을 못 준다.
class RealMeter {
    int getStep() {
        // 진짜 센서 연동은 아직 미완성. (만보기팀 30일)
        throw new RuntimeException("아직 개발 중 : 센서 연동 미완성 (30일 뒤 완성)");
    }
}

// 화면 서비스가 진짜 만보기를 직접 new 해서 쓴다. → 갈아끼울 문이 없다.
class MeterService {
    private RealMeter meter = new RealMeter();   // 진짜에 딱 붙어버렸다

    void render() {
        int step = meter.getStep();
        System.out.println("걸음 수 : " + step);
    }
}

public class App {
    public static void main(String[] args) {
        MeterService ms = new MeterService();

        try {
            ms.render();                 // 진짜가 아직 없어서 여기서 터진다.
        } catch (RuntimeException e) {
            System.out.println("못 돌림 → " + e.getMessage());
            System.out.println("진짜가 나올 때까지 프론트는 대기. 병렬 개발이 안 된다!");
        }
    }
}
