package ex14.exercise.monster.answer;

// [연습문제 정답 - 팩토리 메서드] 무엇을 만들지는 각 스포너(자식)가 정한다.

interface Monster {
    void 공격한다();
}

class Slime implements Monster {
    public void 공격한다() { System.out.println("슬라임: 몸통박치기! (3 damage)"); }
}

class Goblin implements Monster {
    public void 공격한다() { System.out.println("고블린: 단검 찌르기! (7 damage)"); }
}

class Orc implements Monster {
    public void 공격한다() { System.out.println("오크: 도끼 찍기! (15 damage)"); }
}

class Dragon implements Monster {
    public void 공격한다() { System.out.println("드래곤: 화염 브레스! (40 damage)"); }
}

// 부모 : 소환의 공통 흐름(틀)만 정해둔다. 무엇이 나올지는 모른다.
abstract class MonsterSpawner {

    // ↓ 이것이 '팩토리 메서드' : 무엇을 만들지는 자식이 결정한다
    protected abstract Monster 생성();

    // 생성 후 공통으로 처리할 흐름 (부모가 고정)
    public Monster 소환() {
        Monster m = 생성();
        System.out.println("몬스터가 등장한다!");
        return m;
    }
}

// 몬스터마다 자기 스포너 하나. 각자 무엇을 만들지만 책임진다.
class SlimeSpawner extends MonsterSpawner {
    protected Monster 생성() { return new Slime(); }
}

class GoblinSpawner extends MonsterSpawner {
    protected Monster 생성() { return new Goblin(); }
}

class OrcSpawner extends MonsterSpawner {
    protected Monster 생성() { return new Orc(); }
}

class DragonSpawner extends MonsterSpawner {   // ← 새 몬스터 추가 : 이 클래스 하나만 늘렸다
    protected Monster 생성() { return new Dragon(); }
}

public class App {
    public static void main(String[] args) {
        // 던전은 '어떤 스포너'인지만 알면 된다. new 몬스터를 직접 안 한다.
        MonsterSpawner[] spawners = { new SlimeSpawner(), new GoblinSpawner(), new OrcSpawner() };
        for (MonsterSpawner spawner : spawners) {
            spawner.소환().공격한다();
        }

        System.out.println("----------------------------");

        // 새 몬스터 Dragon 을 추가했다 = DragonSpawner 클래스 하나만 늘렸다.
        // 위 기존 코드(부모 MonsterSpawner, 다른 스포너들, 소환 흐름)는 한 줄도 안 고쳤다.
        MonsterSpawner dragon = new DragonSpawner();
        dragon.소환().공격한다();
    }
}
