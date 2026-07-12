package ex14.exercise.monster.step2;

// [step2] 중복이 싫어서 소환 흐름을 부모 MonsterSpawner로 끌어올렸다.
// 흐름은 한 곳으로 모였다. 좋아 보인다.
// 그런데 "무엇을 만들지"까지 부모가 쥐고 있다 → new Slime() 하드코딩.
// 실행해서 GoblinSpawner가 진짜 고블린을 내는지 보자.

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

// 부모가 소환 흐름을 쥔다. 그런데 무엇을 만들지까지 부모가 정해버렸다.
class MonsterSpawner {
    public Monster 소환() {
        System.out.println("던전의 문이 열린다.");
        Monster monster = new Slime();   // ← 부모가 슬라임으로 못박았다. 자식이 못 바꾼다!
        System.out.println("몬스터가 등장한다!");
        return monster;
    }
}

class SlimeSpawner extends MonsterSpawner {
    // 소환()을 그대로 물려받는다.
}

class GoblinSpawner extends MonsterSpawner {
    // 소환()을 그대로 물려받는다. 그런데... 고블린은 어디서 만들지?
}

// TODO: GoblinSpawner가 진짜 "고블린"을 내게 만들어보라.
//       단, 부모 MonsterSpawner의 소환() 흐름은 고치지 않는다.
//   시도 1) 부모 소환()을 고블린용으로 복사한다?       → step1로 되돌아간다. 중복 부활.
//   시도 2) 부모 소환()에 if(고블린이면 new Goblin)?   → 새 몬스터가 늘 때마다 부모를 또 고친다.
//   둘 다 막힌다.
//   그렇다면: 흐름은 부모에 두되, "무엇을 만들지" 그 한 점만 자식에게 넘길 방법은 없을까?
//   (→ step3에서 연다)

public class App {
    public static void main(String[] args) {
        MonsterSpawner slimeSpawner = new SlimeSpawner();
        slimeSpawner.소환().공격한다();

        System.out.println("----------------------------");

        MonsterSpawner goblinSpawner = new GoblinSpawner();
        goblinSpawner.소환().공격한다();   // 고블린을 소환했는데... 슬라임이 나온다!
    }
}
