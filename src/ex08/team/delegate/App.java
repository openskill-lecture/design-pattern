package ex08.team.delegate;

// delegate(위임) : 팀장이 자기 할 일을 하고, 남은 일 하나를 대리에게 넘긴다.
//                 호출자는 팀장에게 시켰지만, 팀장도 일하고 대리도 일한다. (분업)

class Assistant {                        // 대리 : 넘겨받은 일을 한다
    void 보고서작성(String topic) {
        System.out.println("대리 : " + topic + " 보고서를 작성합니다.");
    }
}

class TeamLeader {                        // 팀장 : 자기 일 + 대리에게 위임
    private Assistant assistant = new Assistant();   // 일을 넘길 상대(delegate)를 들고 있다

    void 보고서제출(String topic) {
        System.out.println("팀장 : " + topic + " 자료를 취합합니다.");   // 팀장 자신의 일
        assistant.보고서작성(topic);                                     // 남은 일 하나를 대리에게 위임
        System.out.println("팀장 : 완성된 보고서를 제출합니다.");         // 팀장이 마무리
    }
}

public class App {
    public static void main(String[] args) {
        TeamLeader leader = new TeamLeader();
        leader.보고서제출("3분기 실적");

        System.out.println("----------------------------");
        // 호출자 → 팀장(위임자) → 대리(대리 수행자)
        // 팀장은 자기 일(취합·제출)을 하면서, '작성' 한 가지만 대리에게 넘긴다.
        // 위임자도 일한다. 넘기는 상대는 보통 하나. → 이게 delegate. (facade와 비교!)
    }
}
