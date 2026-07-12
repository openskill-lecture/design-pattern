package ex11.meter.step3;

// step3 : 진짜(RealMeter)가 완성됐다. 서비스 코드는 한 줄도 안 고치고 '갈아끼우기'만.
// 이게 되는 이유 = 가짜도 진짜도 '같은 계약(Meter)'을 지켰기 때문.

interface Meter {
    int getStep();
}

class FakeMeter implements Meter {   // 개발용 가짜(Stub) : 정해진 값을 대답
    public int getStep() {
        return 5;
    }
}

class RealMeter implements Meter {   // 만보기팀이 완성한 진짜
    public int getStep() {
        return 8231;                 // 실제 센서에서 읽은 값
    }
}

class MeterService {
    private Meter meter;

    MeterService(Meter meter) {
        this.meter = meter;
    }

    void render() {
        int step = meter.getStep();
        System.out.println("걸음 수 : " + step);
    }
}

public class App {
    public static void main(String[] args) {
        System.out.println("== 개발 중 (가짜) ==");
        new MeterService(new FakeMeter()).render();   // 걸음 수 : 5

        System.out.println("----------------------------");

        System.out.println("== 배포 (진짜) ==");
        new MeterService(new RealMeter()).render();   // 걸음 수 : 8231

        // MeterService 는 그대로. new FakeMeter() → new RealMeter() 만 바꿨다.
    }
}
