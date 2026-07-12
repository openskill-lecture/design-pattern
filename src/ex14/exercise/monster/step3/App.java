package ex14.exercise.monster.step3;

// [step3] 흐름은 부모가 쥐고, "무엇을 만들지"는 자식에게 통로로 넘긴다.
// 부모 소환()은 생성()을 부를 뿐, 무엇이 만들어지는지 모른다. 그게 팩토리 메서드다.

interface Monster {
    void 공격한다();
}

class Slime implements Monster {
    public void 공격한다() {
        System.out.println("슬라임: 몸통박치기! (3 damage)");
    }
}

class Goblin implements Monster {
    public void 공격한다() {
        System.out.println("고블린: 단검 찌르기! (7 damage)");
    }
}

abstract class MonsterSpawner {
    // 팩토리 메서드 — 무엇을 만들지는 자식이 정한다. 부모는 통로만 뚫어둔다.
    protected abstract Monster 생성();

    // 소환의 공통 흐름은 부모가 고정한다. 부모는 "무엇이" 만들어지는지 모른다.
    public Monster 소환() {
        System.out.println("던전의 문이 열린다.");
        Monster monster = 생성();   // ← 자식이 채운 생성()이 여기서 불린다 (동적바인딩)
        System.out.println("몬스터가 등장한다!");
        return monster;
    }
}

class SlimeSpawner extends MonsterSpawner {
    protected Monster 생성() {
        // TODO: 슬라임을 만들어 돌려줘라.
        return null; // ← 이 한 줄만 바꾸면 된다
    }
}

class GoblinSpawner extends MonsterSpawner {
    protected Monster 생성() {
        // TODO: 고블린을 만들어 돌려줘라. (부모 소환 흐름은 건드리지 않는다)
        return null; // ← 이 한 줄만 바꾸면 된다
    }
}

// 다 채웠으면 실행하자. step2에서 슬라임이 나오던 GoblinSpawner가
// 이제 진짜 고블린으로 공격하는가?

public class App {
    public static void main(String[] args) {
        MonsterSpawner slimeSpawner = new SlimeSpawner();
        slimeSpawner.소환().공격한다();

        System.out.println("----------------------------");

        MonsterSpawner goblinSpawner = new GoblinSpawner();
        goblinSpawner.소환().공격한다();   // step2와 달리, 이제 고블린이 나온다
    }
}
