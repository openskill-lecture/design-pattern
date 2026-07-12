package ex14.exercise.monster.step1;

// [step1] 소환 흐름이 스포너마다 복사되어 있다.
// 지금은 잘 돌아간다. 하지만 새 몬스터를 넣을 때마다 이 흐름을 통째로 또 복사해야 한다.

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

class SlimeSpawner {
    public Monster 소환() {
        System.out.println("던전의 문이 열린다.");
        Monster monster = new Slime();
        System.out.println("몬스터가 등장한다!");
        return monster;
    }
}

class GoblinSpawner {
    public Monster 소환() {
        System.out.println("던전의 문이 열린다.");   // ← SlimeSpawner와 똑같은 흐름을 복사했다
        Monster monster = new Goblin();
        System.out.println("몬스터가 등장한다!");
        return monster;
    }
}

// TODO: 오크(Orc)를 던전에 추가하라.
//   1) Orc 클래스를 만든다 (공격한다 → "오크: 도끼 찍기! (15 damage)")
//   2) OrcSpawner를 만든다 — SlimeSpawner의 소환()을 복사해서 new Orc()로 바꾼다
//   3) main에서 OrcSpawner를 소환시킨다
//
// 다 했으면 세어보자: 방금 소환() 흐름을 몇 번째 복사했지?
// 소환 대사를 "⚔️ 던전의 문이 열린다"로 바꾸려면 이제 몇 군데를 고쳐야 할까?

public class App {
    public static void main(String[] args) {
        SlimeSpawner slimeSpawner = new SlimeSpawner();
        slimeSpawner.소환().공격한다();

        System.out.println("----------------------------");

        GoblinSpawner goblinSpawner = new GoblinSpawner();
        goblinSpawner.소환().공격한다();

        // TODO: 여기에 OrcSpawner 소환을 추가하라.
    }
}
