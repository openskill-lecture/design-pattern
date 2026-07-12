package ex01.oop.p5_composition.step2;

class Printer {
    public void 인쇄() {
        System.out.println("흑백 인쇄");
    }
}

class ColorPrinter extends Printer {
    @Override
    public void 인쇄() {
        System.out.println("컬러 인쇄");
    }
}

class Scanner {
    public void 스캔() {
        System.out.println("스캔");
    }
}

// 조합(has-a)으로 바꿨지만, 부품을 안에서 직접 만든다.
class CopyMachine {
    private Printer printer = new Printer(); // 여기서 굳어버린다
    private Scanner scanner = new Scanner();

    public void 복사() {
        scanner.스캔();
        printer.인쇄();
    }
}

public class App {
    public static void main(String[] args) {
        CopyMachine machine = new CopyMachine();
        machine.복사();

        // 컬러프린터로 바꾸고 싶어도? CopyMachine 안의 new Printer() 를 뜯어고쳐야 한다.
        // ColorPrinter 를 만들어 놨지만 갈아끼울 방법이 없다. (강한 결합)
    }
}
