package ex06.spooler.step1;

import java.util.ArrayList;
import java.util.List;

// 회사 프린터 대기열은 하나여야 하는데, 아무나 new 하면?

class PrintSpooler {
    List<String> 대기열 = new ArrayList<>();

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
        // 스풀러 하나. 그런데 생성자가 열려 있어 코드 여기저기서 new PrintSpooler() 을 한다.
        PrintSpooler 관리팀 = new PrintSpooler();     // 관리팀에서 만든 스풀러
        PrintSpooler 실제프린터 = new PrintSpooler();  // 실제 프린터 앞에 놓인 스풀러

        관리팀.문서등록("월간보고서");  // 관리팀에서 문서를 등록했다

        System.out.println("----------------------------");

        // 그런데 프린터 앞에 선 건 딴 스풀러라 그 문서를 모른다.
        실제프린터.출력();  // 출력할 문서가 없다 ← 등록한 문서가 사라졌다! (구멍)
        System.out.println("관리팀 == 실제프린터 ? " + (관리팀 == 실제프린터));  // false

        // 큐를 '합치면' 될까? 등록하는 순간 프린터 앞 스풀러가 최신 큐를 갖고 있어야 한다.
        // 스풀러가 여럿이면 큐가 갈려 그 순간 문서가 사라진다. 하나로 묶으려면? (step2)
    }
}
