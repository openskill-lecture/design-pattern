package ex15.step1;

// STEP 1. 검증, 조회, 문서 생성, 발송이 한 메서드에 섞인 레거시 코드
class User {
    String id;
    boolean login;
    boolean statementManager;

    User(String id, boolean login, boolean statementManager) {
        this.id = id;
        this.login = login;
        this.statementManager = statementManager;
    }
}

class StatementRequest {
    String accountNo;
    String period;
    String format;
    String channel;

    StatementRequest(String accountNo, String period, String format, String channel) {
        this.accountNo = accountNo;
        this.period = period;
        this.format = format;
        this.channel = channel;
    }
}

class LegacyFaxClient {
    void transmit(String phoneNumber, String document) {
        System.out.println("[기존 팩스] " + phoneNumber + "로 전송: " + document);
    }
}

class LegacyStatementService {

    void 명세서를발송한다(User user, StatementRequest request) {
        System.out.println("[요청] 거래명세서 발송을 시작합니다.");

        if (!user.login) {
            System.out.println("[중단] 로그인이 필요합니다.");
            return;
        }

        if (!user.statementManager) {
            System.out.println("[중단] 거래명세서 발송 권한이 없습니다.");
            return;
        }

        System.out.println("[원장] " + request.accountNo + " / " + request.period + " 거래내역 조회");
        String transactions = "입금 100,000원 / 출금 30,000원";

        String document;
        if (request.format.equals("PDF")) {
            System.out.println("[PDF] 머리말 작성");
            System.out.println("[PDF] 거래내역 작성: " + transactions);
            System.out.println("[PDF] 꼬리말 작성");
            document = "PDF 거래명세서";
        } else if (request.format.equals("CSV")) {
            System.out.println("[CSV] 열 이름 작성");
            System.out.println("[CSV] 거래내역 작성: " + transactions);
            System.out.println("[CSV] 파일 마감");
            document = "CSV 거래명세서";
        } else {
            System.out.println("[중단] 지원하지 않는 문서 형식입니다.");
            return;
        }

        if (request.channel.equals("EMAIL")) {
            System.out.println("[이메일] " + user.id + "에게 발송: " + document);
        } else if (request.channel.equals("SMS")) {
            System.out.println("[문자] 다운로드 링크 발송: " + document);
        } else if (request.channel.equals("FAX")) {
            LegacyFaxClient faxClient = new LegacyFaxClient();
            faxClient.transmit("051-000-0000", document);
        } else {
            System.out.println("[중단] 지원하지 않는 발송 채널입니다.");
            return;
        }

        System.out.println("[감사] " + user.id + "의 발송 이력을 기록합니다.");
    }
}

public class App {
    public static void main(String[] args) {
        User user = new User("staff01", true, true);
        StatementRequest request = new StatementRequest(
                "110-123-456789",
                "2026-08",
                "PDF",
                "EMAIL"
        );

        new LegacyStatementService().명세서를발송한다(user, request);
    }
}
