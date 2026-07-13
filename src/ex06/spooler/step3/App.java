package ex06.spooler.step3;

import java.util.ArrayList;
import java.util.List;

// 생성자를 막았다. 이제 프린터 대기열은 언제나 딱 하나다.

class PrintSpooler {
    // 하나만 미리 만든다.
    private static final PrintSpooler instance = new PrintSpooler();

    List<String> 대기열 = new ArrayList<>();

    // 생성자를 private으로 닫는다. 밖에서 new PrintSpooler() 못한다.
    private PrintSpooler() {}

    // 그 하나를 꺼내주는 유일한 통로.
    static PrintSpooler getInstance() {
        return instance;
    }

    void 문서등록(String doc) {
        대기열.add(doc);
        System.out.println("[등록] " + doc);
    }

    void 출력() {
        if (대기열.isEmpty()) {
            System.out.println("→ 출력할 문서가 없다");
            return;
        }
        for (String doc : 대기열) {
            System.out.println("→ 출력 : " + doc);
        }
    }
}

public class App {
    public static void main(String[] args) {
        // PrintSpooler c = new PrintSpooler();
        // ↑ 주석을 풀면 컴파일 에러! 생성자가 private이라 문이 닫혔다.

        // 대기열을 얻는 길은 getInstance() 하나뿐. 늘 같은 큐다.
        PrintSpooler a = PrintSpooler.getInstance();
        PrintSpooler b = PrintSpooler.getInstance();

        a.문서등록("월간보고서");
        b.출력();   // 출력 : 월간보고서 ← 어디서 얻든 같은 큐라 항상 모인다

        System.out.println("----------------------------");

        System.out.println("a == b ? " + (a == b));  // true — 우회할 문이 없다
    }
}
