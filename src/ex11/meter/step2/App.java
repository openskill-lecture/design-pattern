package ex11.meter.step2;

// step2 : 계약(Meter 인터페이스)만 합의하면, 진짜 없이 '가짜(Stub)'로 먼저 돌린다.
// → 만보기팀(진짜)과 나(프론트)가 동시에 간다. 이게 '병렬 개발'이다.
//   AI 에게 한쪽을 맡길 때도, 이 계약이 곧 '무엇을 만들지'를 알려주는 스펙이 된다.

// 계약 : 서로 이것만 지켜서 만든다.
interface Meter {
    int getStep();
}

// 가짜 만보기 = Stub : '값을 대답해 주는' 객체. 정해진 값을 그냥 리턴한다.
// (Mock 이 아니다! Stub 은 return 값이 핵심 — 물어보면 정해진 답을 준다.)
class FakeMeter implements Meter {
    public int getStep() {
        return 5;                    // 정해진 값을 대답. 진짜 센서 없이도 화면이 돈다.
    }
}

// 화면 서비스는 '계약(Meter)'에만 의존한다. 진짜인지 가짜인지 모른다.
class MeterService {
    private Meter meter;

    MeterService(Meter meter) {      // 생성자 주입 : 가짜든 진짜든 갈아끼운다
        this.meter = meter;
    }

    void render() {
        int step = meter.getStep();
        System.out.println("걸음 수 : " + step);
    }
}

public class App {
    public static void main(String[] args) {
        // 진짜 만보기(30일)는 아직 없다. 그래도 가짜(Stub)로 프론트가 돈다!
        MeterService ms = new MeterService(new FakeMeter());
        ms.render();                 // 걸음 수 : 5

        System.out.println("----------------------------");
        System.out.println("진짜 없이 화면 개발 완료. 만보기팀과 나는 '동시에' 갔다. (병렬 개발)");
    }
}
