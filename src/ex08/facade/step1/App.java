package ex08.facade.step1;

// facade(파사드) : 팀장이 여러 대리(마케팅·개발·디자인)를 대신 조율한다.
//                 호출자(임원)는 "출시 준비해줘" 한 마디면 끝. 내부 복잡함을 모른다. (편의)

class MarketingAssistant {
    void 홍보준비() {
        System.out.println("마케팅대리 : 홍보물을 준비합니다.");
    }
}

class DevAssistant {
    void 배포준비() {
        System.out.println("개발대리 : 서버를 배포합니다.");
    }
}

class DesignAssistant {
    void 시안준비() {
        System.out.println("디자인대리 : 출시 시안을 만듭니다.");
    }
}

public class App {
    public static void main(String[] args) {
        MarketingAssistant marketing = new MarketingAssistant();
        DevAssistant dev = new DevAssistant();
        DesignAssistant design = new DesignAssistant();

        marketing.홍보준비();
        dev.배포준비();
        design.시안준비();
    }
}
