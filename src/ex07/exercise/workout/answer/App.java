package ex07.exercise.workout.answer;

// [연습문제1 정답 - 템플릿 메서드] 골격은 부모가, 본운동만 자식이 채운다.

abstract class Workout {
    // 템플릿 메서드 : 운동 흐름(골격)을 한 곳에 정하고 final 로 잠근다.
    public final void 운동하기() {
        준비운동();
        본운동();
        마무리운동();
    }

    // 고정 스텝 : 부모가 구현, protected 로 잠근다.
    protected void 준비운동()   { System.out.println("준비운동 : 스트레칭"); }
    protected void 마무리운동() { System.out.println("마무리운동 : 정리 스트레칭"); }

    // 변하는 스텝 : 자식이 반드시 채운다.
    protected abstract void 본운동();
}

class Running extends Workout {
    @Override
    protected void 본운동() { System.out.println("본운동 : 10km 달리기"); }
}

class Swimming extends Workout {
    @Override
    protected void 본운동() { System.out.println("본운동 : 자유형 20바퀴"); }
}

public class App {
    public static void main(String[] args) {
        Workout running  = new Running();
        Workout swimming = new Swimming();

        running.운동하기();      // 준비→10km 달리기→마무리

        System.out.println("----------------------------");

        swimming.운동하기();     // 준비→자유형 20바퀴→마무리

        // 준비/마무리는 부모에 한 번만. 자식은 본운동() 한 칸만 채운다.
    }
}
