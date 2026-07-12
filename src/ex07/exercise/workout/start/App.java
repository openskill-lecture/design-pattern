package ex07.exercise.workout.start;

// [연습문제1 - 템플릿 메서드] 운동 흐름은 똑같고 '본운동'만 다르다.
//
// 모든 운동은 준비운동 → 본운동 → 마무리운동 순서로 진행한다.
// 그런데 지금은 러닝/수영이 각자 운동하기()에 이 흐름을 통째로 복붙한다.
//   → 준비운동/마무리운동 코드가 그대로 중복되고, 순서를 강제하는 게 없다.
//
// ── 먼저 이대로 실행해 보라 ──────────────────
// 러닝과 수영이 같은 준비/마무리를 각자 출력한다. (똑같은 코드가 두 번)
//
// 목표 : 변하지 않는 흐름(골격)을 부모로 끌어올리고, 변하는 '본운동'만 자식이 채우게 하라.
//
// TODO 1) 부모 abstract class Workout 을 만든다.
//
// TODO 2) 공통 흐름을 '템플릿 메서드' 운동하기() 로 옮긴다. 골격은 못 바꾸게 final:
//         public final void 운동하기() {
//             준비운동();
//             본운동();
//             마무리운동();
//         }
//
// TODO 3) 고정 스텝은 protected 로 부모가 직접 구현한다. (자식이 못 건드리게)
//         protected void 준비운동()   { System.out.println("준비운동 : 스트레칭"); }
//         protected void 마무리운동() { System.out.println("마무리운동 : 정리 스트레칭"); }
//
// TODO 4) 변하는 스텝은 abstract 로 통로만 연다. (자식이 반드시 채운다)
//         protected abstract void 본운동();
//
// TODO 5) Running / Swimming 이 Workout 을 상속하고 본운동() '한 칸'만 채운다.
//         - Running 본운동  : "본운동 : 10km 달리기"
//         - Swimming 본운동 : "본운동 : 자유형 20바퀴"
//
// TODO 6) main 에서 Workout 타입으로 받아 운동하기() 를 호출하라. 골격은 똑같고 본운동만 달라진다.

// 러닝 : 흐름을 직접 다 적는다.
class Running {
    void 운동하기() {
        System.out.println("준비운동 : 스트레칭");
        System.out.println("본운동 : 10km 달리기");
        System.out.println("마무리운동 : 정리 스트레칭");
    }
}

// 수영 : 똑같은 준비/마무리를 또 복붙한다. (여기가 문제)
class Swimming {
    void 운동하기() {
        System.out.println("준비운동 : 스트레칭");
        System.out.println("본운동 : 자유형 20바퀴");
        System.out.println("마무리운동 : 정리 스트레칭");
    }
}

public class App {
    public static void main(String[] args) {
        new Running().운동하기();

        System.out.println("----------------------------");

        new Swimming().운동하기();   // 준비/마무리가 러닝과 똑같이 반복된다.

        // 여기서부터 네 코드 (TODO 완료 후):
        // Workout running  = new Running();
        // Workout swimming = new Swimming();
        // running.운동하기();
        // swimming.운동하기();
    }
}
