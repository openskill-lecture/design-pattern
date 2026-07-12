package ex02.solid.p2_ocp.step2;

// 완성코드 : 공격은 각 캐릭터가 스스로 안다. 게임(Game)은 시키기만 한다.
abstract class Hero {
    abstract void 공격();
}

class Warrior extends Hero {
    void 공격() { System.out.println("전사가 칼을 휘두른다"); }
}

class Archer extends Hero {
    void 공격() { System.out.println("궁수가 활을 쏜다"); }
}

// 새 캐릭터 = 클래스 추가만. Game도, 기존 캐릭터도 안 건드린다.
class Mage extends Hero {
    void 공격() { System.out.println("마법사가 파이어볼을 던진다"); }
}

class Game {
    void 전투(Hero hero) {
        hero.공격();   // 누가 왔는지 안 묻는다. 그냥 공격을 시킨다.
    }
}

public class App {
    public static void main(String[] args) {
        Game game = new Game();
        Hero[] party = { new Warrior(), new Archer(), new Mage() };

        for (Hero hero : party) {
            game.전투(hero);   // 각자 자기 방식으로 공격한다
        }

        // Mage를 추가했지만 Game은 한 줄도 안 고쳤다. ← OCP (확장에 열려있다)
    }
}
