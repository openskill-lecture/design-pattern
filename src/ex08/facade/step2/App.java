package ex08.facade.step2;

// facade(파사드) : 팀장이 여러 대리(마케팅·개발·디자인)를 대신 조율한다.
//                 호출자(임원)는 "출시 준비해줘" 한 마디면 끝. 내부 복잡함을 모른다. (편의)

class MarketingAssistant { void 홍보준비() { System.out.println("마케팅대리 : 홍보물을 준비합니다."); } }
class DevAssistant        { void 배포준비() { System.out.println("개발대리 : 서버를 배포합니다."); } }
class DesignAssistant     { void 시안준비() { System.out.println("디자인대리 : 출시 시안을 만듭니다."); } }

class TeamLeader {                        // 파사드 : 여러 대리를 알아서 조율한다
    private MarketingAssistant marketing = new MarketingAssistant();
    private DevAssistant dev = new DevAssistant();
    private DesignAssistant design = new DesignAssistant();

    void 출시준비() {                      // 임원은 이 한 마디만 부른다
        marketing.홍보준비();
        dev.배포준비();
        design.시안준비();
    }
}

public class App {
    public static void main(String[] args) {
        TeamLeader leader = new TeamLeader();
        leader.출시준비();                 // 임원은 "출시 준비해줘" 한 마디. 안에 대리가 몇인지 모른다.

        System.out.println("----------------------------");
        // 임원 → 팀장(파사드) → 마케팅/개발/디자인 대리 (여럿)
        // 호출자는 한 번만 부르고, 팀장이 여러 하위를 조율해 복잡함을 숨긴다.
        // 여러 하위를 묶어 '편의'를 준다. → 이게 facade. (delegate와 비교!)
    }
}
