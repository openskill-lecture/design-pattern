package ex02.solid.p2_ocp.step1;

// 새 캐릭터가 생길 때마다 전투 로직을 뜯어고쳐야 할까?
class Game {
    void 공격(String job) {
        if (job.equals("전사")) System.out.println("전사가 칼을 휘두른다");
        else if (job.equals("궁수")) System.out.println("궁수가 활을 쏜다");
    }
}

public class App {
    public static void main(String[] args) {
        Game game = new Game();
        game.공격("전사"); // 전사가 칼을 휘두른다
        game.공격("궁수"); // 궁수가 활을 쏜다

        // 마법사를 추가하려면? Game의 if-else를 또 뜯어고쳐야 한다.
        // 확장하려고 기존 파일(Game)을 또 연다. ← OCP 위반 (확장에 닫혀있다)
        // [실습] 캐릭터를 Hero 자식으로 만들고 공격을 각자 갖게 해, Game을 안 고치게 바꿔보자 → step2
    }
}
