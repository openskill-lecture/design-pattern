package ex01.oop.p5_composition.step3;

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

// 좋음 : 부품을 밖에서 주입받는다. 몇 개든 가질 수 있고, 언제든 갈아끼울 수 있다.
class CopyMachine {
    private Printer printer;
    private Scanner scanner;

    // 의존성 주입 (추상적인 것을 주입)
    public CopyMachine(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    public void 복사() {
        scanner.스캔();
        printer.인쇄();
    }
}

public class App {
    public static void main(String[] args) {
        CopyMachine machine = new CopyMachine(new Printer(), new Scanner());
        machine.복사();

        System.out.println("----------------------------");

        // 부품만 갈아끼우면 컬러 복합기 완성! CopyMachine 코드는 한 줄도 안 고쳤다. (느슨한 결합)
        CopyMachine colorMachine = new CopyMachine(new ColorPrinter(), new Scanner());
        colorMachine.복사();
    }
}
