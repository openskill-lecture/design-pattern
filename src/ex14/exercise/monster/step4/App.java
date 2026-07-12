package ex14.exercise.monster.step4;

// [step4] 팩토리 메서드는 완성됐다. 이제 진짜 보상을 손으로 확인하자.
// 새 몬스터를 추가할 때, 부모 MonsterSpawner를 고쳐야 할까?

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
    protected abstract Monster 생성();

    public Monster 소환() {
        System.out.println("던전의 문이 열린다.");
        Monster monster = 생성();
        System.out.println("몬스터가 등장한다!");
        return monster;
    }
}

class SlimeSpawner extends MonsterSpawner {
    protected Monster 생성() {
        return new Slime();
    }
}

class GoblinSpawner extends MonsterSpawner {
    protected Monster 생성() {
        return new Goblin();
    }
}

// TODO: 오크(Orc)를 던전에 추가하라. 단, 위 MonsterSpawner는 열어보지도 마라.
//   1) Orc 클래스 (공격한다 → "오크: 도끼 찍기! (15 damage)")
//   2) OrcSpawner extends MonsterSpawner — 생성()에서 new Orc()만 돌려주면 된다
//   3) main에서 OrcSpawner를 소환시킨다
//
// 확인: step1에선 몬스터 하나 추가에 소환 흐름을 통째로 복사했다.
//       지금은? 부모 MonsterSpawner 수정 0줄. 클래스 두 개만 추가했다.
//       이것이 팩토리 메서드의 보상이다.

public class App {
    public static void main(String[] args) {
        MonsterSpawner slimeSpawner = new SlimeSpawner();
        slimeSpawner.소환().공격한다();

        System.out.println("----------------------------");

        MonsterSpawner goblinSpawner = new GoblinSpawner();
        goblinSpawner.소환().공격한다();

        // TODO: 여기에 OrcSpawner 소환을 추가하라.
    }
}
