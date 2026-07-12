package ex10.exercise.character.start;

// [연습2] 게임 캐릭터에 장비(갑옷·방패·투구)를 장착해 방어력을 누적한다.
// 지금은 '조합마다' 클래스를 새로 판다 → 장비 하나 늘 때마다 클래스가 2배씩 폭발.
//
// TODO: 데코레이터 패턴으로 바꿔라 —
//   1) Character 를 인터페이스(통로)로 : int 방어력(); String 설명();
//   2) Warrior(알맹이) 하나 + 장비를 '감싸는' 추상 Gear.
//   3) Armor/Shield/Helmet 이 Character 를 감싸 방어력·설명을 덧붙이게.
// 목표 : new Helmet(new Armor(new Warrior())) 처럼 런타임에 자유 조합. (정답은 answer 폴더)

class Warrior {
    int 방어력()  { return 10; }
    String 설명() { return "전사"; }
}

class ArmorWarrior {                      // +갑옷
    int 방어력()  { return 30; }
    String 설명() { return "전사+갑옷"; }
}

class ShieldWarrior {                     // +방패
    int 방어력()  { return 25; }
    String 설명() { return "전사+방패"; }
}

class ArmorShieldWarrior {                // +갑옷+방패
    int 방어력()  { return 45; }
    String 설명() { return "전사+갑옷+방패"; }
}
// 여기에 '투구'까지 넣으면? ArmorHelmetWarrior, ShieldHelmetWarrior,
// ArmorShieldHelmetWarrior … 장비 하나 늘 때마다 클래스가 2배씩 폭발한다.

public class App {
    public static void main(String[] args) {
        // 갑옷+방패 낀 전사 (조합 클래스를 콕 집어 골라야 한다)
        ArmorShieldWarrior hero = new ArmorShieldWarrior();
        System.out.println(hero.설명() + " : 방어력 " + hero.방어력());

        System.out.println("----------------------------");
        // 전투 중 "방패를 내려놔" → ArmorWarrior 로 갈아끼워야 한다. 런타임 변경 불가.
        System.out.println("전사 : (방패만 내려놓고 싶은데... 캐릭터를 통째로 바꿔야 하네요)");
        // → 장비를 더하거나 빼려면 매번 새 클래스가 필요하다. 데코레이터로 바꿔보자.
    }
}
