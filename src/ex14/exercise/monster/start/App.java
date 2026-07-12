package ex14.exercise.monster.start;

// [연습문제 - 시작] 던전이 몬스터를 직접 new 로 만든다.
//
// 몬스터 종류는 끝없이 늘어난다 : 슬라임 → 고블린 → 오크 → 드래곤 → 다음 DLC ...
// 지금은 소환할 때마다 new Slime(), new Goblin() 을 직접 쓴다.
// 새 몬스터가 생기면? 이 던전 코드(main)를 매번 다시 뜯어고쳐야 한다.
//
// TODO: "무엇을 만들지는 자식(서브클래스)이 정한다" — 팩토리 메서드 패턴으로 바꿔라.
//   1) abstract 부모 MonsterSpawner 를 만든다.
//        - 소환의 공통 흐름( 소환() )은 부모가 고정하고,
//        - 무엇을 만들지( abstract 생성() )는 자식에게 맡긴다.  ← 이게 팩토리 메서드
//   2) 몬스터마다 자기 스포너(SlimeSpawner, GoblinSpawner ...)를 둔다.
//   3) 목표 : 새 몬스터(예: Orc, Dragon)를 추가할 때
//             스포너 클래스 '하나만' 늘리고 기존 코드는 한 줄도 안 고치게 만든다.
//   ※ ex13 처럼 if-else 한 곳에서 분기하는 Simple Factory 로 풀지 말 것.
//      여기 목표는 추상 부모 + 자식 스포너다.

interface Monster {
    void 공격한다();
}

class Slime implements Monster {
    public void 공격한다() { System.out.println("슬라임: 몸통박치기! (3 damage)"); }
}

class Goblin implements Monster {
    public void 공격한다() { System.out.println("고블린: 단검 찌르기! (7 damage)"); }
}

public class App {
    public static void main(String[] args) {
        // 던전이 몬스터를 직접 new 한다. 종류가 늘면 이 자리가 계속 바뀐다.
        Monster m1 = new Slime();
        m1.공격한다();

        Monster m2 = new Goblin();
        m2.공격한다();

        // TODO: 새 몬스터 Orc, Dragon 을 추가할 때
        //       이 main 을 안 고치고 확장할 수 있는가?  ← 지금 구조로는 못 한다.
    }
}
