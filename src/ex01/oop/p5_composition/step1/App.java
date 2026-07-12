package ex01.oop.p5_composition.step1;

// 상속보다 조합을 우선한다.
class Printer {
    public void 인쇄() {
        System.out.println("흑백 인쇄");
    }
}

class Scanner {
    public void 스캔() {
        System.out.println("스캔");
    }
}

// 나쁨 : 상속으로 기능을 물려받으려 한다.
class CopyMachine extends Printer {
    // 스캐너도 물려받고 싶지만 extends Scanner 는 불가능! (부모는 하나뿐)
    // 결국 스캔 코드를 여기에 복붙하게 된다.
    public void 스캔() {
        System.out.println("스캔 (복사해 온 코드)");
    }
}

public class App {
    public static void main(String[] args) {
        CopyMachine machine = new CopyMachine();
        machine.스캔();
        machine.인쇄();
    }
}
