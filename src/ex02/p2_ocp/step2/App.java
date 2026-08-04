package ex02.solid.p2_ocp.step2;

abstract class Hero {
    abstract void 공격();
}

class Warrior extends Hero {
    void 공격() {
        System.out.println("전사가 칼을 휘두른다");
    }
}

class Archer extends Hero {
    void 공격() {
        System.out.println("궁수가 활을 쏜다");
    }
}

class Mage extends Hero {
    void 공격() {
        System.out.println("마법사가 파이어볼을 던진다");
    }
}

class Game {
    void 전투(Hero hero) {
        hero.공격();
    }
}

public class App {
    public static void main(String[] args) {
        Game game = new Game();

        game.전투(new Warrior());
        game.전투(new Archer());
        game.전투(new Mage());
    }
}