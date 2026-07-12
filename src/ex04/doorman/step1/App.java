package ex04.doorman.step1;

// 기본코드 : 문지기는 동물을 받아 쫓아낸다. 딱 그 일만.
abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// 문지기의 본업 : 쫓아내기.
class Doorman {
    void 쫓아내(Animal a) {
        System.out.println(a.getName() + " 쫓아내");
    }
}

public class App {
    public static void main(String[] args) {
        Doorman doorman = new Doorman();
        doorman.쫓아내(new Tiger());   // 호랑이 쫓아내
        doorman.쫓아내(new Mouse());   // 쥐 쫓아내
    }
}
