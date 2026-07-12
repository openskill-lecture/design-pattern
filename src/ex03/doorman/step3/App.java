package ex03.doorman.step3;

// 완성코드 : 문지기는 "동물(추상)"에게만 의존한다.
// 무엇을 쫓아낼지는 런타임에 전략으로 갈아끼운다.
abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// 새 동물이 와도 문지기는 한 줄도 바뀌지 않는다.
class Rabbit extends Animal {
    String getName() { return "토끼"; }
}

// [Context] 전략(Animal)을 필드로 보유하고 setter로 갈아끼운다.
class Doorman {
    private Animal target; 

    public Doorman(){

    }

    void setTarget(Animal target) {   // 행위를 교체하는 전략
        this.target = target;
    }

    void 쫓아내() {                    // 파라미터 없이, 현재 보유한 전략에게 위임
        System.out.println(target.getName() + " 쫓아내");
    }
}

public class App {
    public static void main(String[] args) {
        Doorman doorman = new Doorman();

        doorman.setTarget(new Tiger());
        doorman.쫓아내();   // 호랑이 쫓아내

        System.out.println("----------------------------");

        // ★ 같은 doorman, 같은 쫓아내() — 전략만 갈아끼운다
        doorman.setTarget(new Mouse());
        doorman.쫓아내();   // 쥐 쫓아내

        // 새 동물이 와도 문지기 수정 0 — Rabbit 클래스만 추가하면 끝
        doorman.setTarget(new Rabbit());
        doorman.쫓아내();   // 토끼 쫓아내
    }
}
