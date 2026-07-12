package ex10.exercise.character.answer;

// [정답2] 데코레이터 패턴 — 장비가 캐릭터를 '감싸서' 방어력·설명을 덧붙인다.
// 장비 하나 늘어도 클래스는 하나만 추가. 런타임에 자유 조합.

interface Character {                     // 통로(약속)
    int 방어력();
    String 설명();
}

class Warrior implements Character {      // 알맹이
    @Override public int 방어력()  { return 10; }
    @Override public String 설명() { return "전사"; }
}

abstract class Gear implements Character {  // 감싸는 장비(데코레이터)
    protected Character base;               // 감싸고 있는 대상
    Gear(Character base) { this.base = base; }
}

class Armor extends Gear {
    Armor(Character base) { super(base); }
    @Override public int 방어력()  { return base.방어력() + 20; }
    @Override public String 설명() { return base.설명() + "+갑옷"; }
}

class Shield extends Gear {
    Shield(Character base) { super(base); }
    @Override public int 방어력()  { return base.방어력() + 15; }
    @Override public String 설명() { return base.설명() + "+방패"; }
}

class Helmet extends Gear {
    Helmet(Character base) { super(base); }
    @Override public int 방어력()  { return base.방어력() + 5; }
    @Override public String 설명() { return base.설명() + "+투구"; }
}

public class App {
    public static void main(String[] args) {
        // 전사를 감싸며 장비를 덧붙인다 — 런타임에 원하는 만큼 조합
        Character hero = new Helmet(new Shield(new Armor(new Warrior())));
        System.out.println(hero.설명() + " : 방어력 " + hero.방어력());

        System.out.println("----------------------------");
        // 갑옷만 낀 전사? 클래스 안 만들고 감싸기만 바꾸면 끝.
        Character hero2 = new Armor(new Warrior());
        System.out.println(hero2.설명() + " : 방어력 " + hero2.방어력());
        // 장비를 더하든 빼든 조합만 바꾸면 된다. 새 클래스가 필요 없다.
    }
}
