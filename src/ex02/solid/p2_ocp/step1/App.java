package ex02.solid.p2_ocp.step1;

class Warrior {
    void 공격() {
        System.out.println("전사가 칼을 휘두른다");
    }
}

class Archer {
    void 공격() {
        System.out.println("궁수가 활을 쏜다");
    }
}

class Game {
    void 전사전투(Warrior warrior) {
        warrior.공격();
    }

    void 궁수전투(Archer archer) {
        archer.공격();
    }

    // 마법사를 추가하려면?
    // void 마법사전투(Mage mage) { ... }
    // Game을 또 수정해야 한다.
}

public class App {
    public static void main(String[] args) {
        Game game = new Game();

        game.전사전투(new Warrior());
        game.궁수전투(new Archer());
    }
}