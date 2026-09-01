package ex15.step2;

// STEP 2. SRP로 책임을 나눴지만 서비스가 구체 구현을 직접 생성한다.
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

    StatementRequest(String accountNo, String period) {
        this.accountNo = accountNo;
        this.period = period;
    }
}

class AccessChecker {
    boolean 통과한다(User user) {
        if (!user.login) {
            System.out.println("[중단] 로그인이 필요합니다.");
            return false;
        }

        if (!user.statementManager) {
            System.out.println("[중단] 거래명세서 발송 권한이 없습니다.");
            return false;
        }

        return true;
    }
}

class TransactionReader {
    String 거래내역조회(String accountNo, String period) {
        System.out.println("[원장] " + accountNo + " / " + period + " 거래내역 조회");
        return "입금 100,000원 / 출금 30,000원";
    }
}

class PdfStatementGenerator {
    String 생성한다(String transactions) {
        System.out.println("[PDF] 거래명세서 생성: " + transactions);
        return "PDF 거래명세서";
    }
}

class EmailStatementSender {
    void 발송한다(String receiver, String document) {
        System.out.println("[이메일] " + receiver + "에게 발송: " + document);
    }
}

class AuditLogger {
    void 기록한다(String userId) {
        System.out.println("[감사] " + userId + "의 발송 이력을 기록합니다.");
    }
}

class StatementService {
    private final AccessChecker accessChecker = new AccessChecker();
    private final TransactionReader transactionReader = new TransactionReader();
    private final PdfStatementGenerator generator = new PdfStatementGenerator();
    private final EmailStatementSender sender = new EmailStatementSender();
    private final AuditLogger auditLogger = new AuditLogger();

    void 명세서를발송한다(User user, StatementRequest request) {
        if (!accessChecker.통과한다(user)) {
            return;
        }

        String transactions = transactionReader.거래내역조회(request.accountNo, request.period);
        String document = generator.생성한다(transactions);
        sender.발송한다(user.id, document);
        auditLogger.기록한다(user.id);
    }
}

public class App {
    public static void main(String[] args) {
        User user = new User("staff01", true, true);
        StatementRequest request = new StatementRequest("110-123-456789", "2026-08");

        new StatementService().명세서를발송한다(user, request);
    }
}
