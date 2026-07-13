package ex06.spooler.step2;

import java.util.ArrayList;
import java.util.List;

// 프린터 대기열을 딱 하나 내놨다. 그런데 생성자를 안 막았다. 뚫릴까?

class PrintSpooler {
    // 프린터 대기열을 미리 하나 만들어, 다같이 이 한 개를 쓰라고 내놓는다.
    static PrintSpooler instance = new PrintSpooler();

    List<String> 대기열 = new ArrayList<>();

    // 생성자를 막지 않았다! 밖에서 아직 new PrintSpooler() 이 된다.
    PrintSpooler() {}

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
        // 착한 길 : 미리 세운 한 개(instance)를 같이 쓴다. 문서가 한 곳에 모인다.
        PrintSpooler a = PrintSpooler.instance;
        PrintSpooler b = PrintSpooler.instance;

        a.문서등록("월간보고서");
        b.출력();   // 출력 : 월간보고서 ← 같은 큐라 통한다
        System.out.println("a == b ? " + (a == b));  // true

        System.out.println("----------------------------");

        // 뚫리는 지점 : 생성자가 열려있어 딴 스풀러를 몰래 또 만든다.
        PrintSpooler c = new PrintSpooler();
        c.출력();   // 출력할 문서가 없다 ← 이 스풀러는 빈 큐, 등록한 문서가 안 보인다!
        System.out.println("a == c ? " + (a == c));  // false — 싱글톤이 깨졌다
    }
}
