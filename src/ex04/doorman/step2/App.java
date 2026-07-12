package ex04.doorman.step2;

// 함정 : "쫓아낸 동물을 출입대장에 기록해줘" 라는 요구가 생겼다.
// 그래서 쫓아내() 안에 기록(로그)을 그냥 끼워 넣었다.
abstract class Animal {
    abstract String getName();
}

class Tiger extends Animal {
    String getName() { return "호랑이"; }
}

class Mouse extends Animal {
    String getName() { return "쥐"; }
}

// 문지기가 이제 "쫓아내기" + "출입대장 기록" 두 가지 일을 한다.
class Doorman {
    void 쫓아내(Animal a) {
        System.out.println("[출입대장] " + a.getName() + " 퇴장 기록");   // 곁다리 일이 본업에 끼어들었다
        System.out.println(a.getName() + " 쫓아내");                     // 진짜 본업
    }
}

public class App {
    public static void main(String[] args) {
        Doorman doorman = new Doorman();
        doorman.쫓아내(new Tiger());
        doorman.쫓아내(new Mouse());
        // 출력마다 [출입대장]이 따라붙는다.
        // 로그 규칙이 바뀌면? 로그를 잠깐 끄고 싶으면? 매번 '문지기'를 뜯어고쳐야 한다.
        // 문지기는 쫓아내는 일만 해야 하는데(SRP), 곁다리 관심사가 눌러앉았다. → step3
    }
}
