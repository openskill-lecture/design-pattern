package ex15.step7;

// STEP 7. 책임 연쇄로 검증 순서를 연결하고 실패한 지점에서 멈춘다.
class User {
    String id;
    boolean login;
    boolean statementManager;
    String allowedAccountNo;

    User(String id, boolean login, boolean statementManager, String allowedAccountNo) {
        this.id = id;
        this.login = login;
        this.statementManager = statementManager;
        this.allowedAccountNo = allowedAccountNo;
    }
}

class StatementRequest {
    String accountNo;

    StatementRequest(String accountNo) {
        this.accountNo = accountNo;
    }
}

abstract class RequestValidator {
    private RequestValidator next;

    RequestValidator setNext(RequestValidator next) {
        this.next = next;
        return next;
    }

    public final boolean 검증한다(User user, StatementRequest request) {
        if (!현재검증을통과한다(user, request)) {
            return false;
        }

        return next == null || next.검증한다(user, request);
    }

    protected abstract boolean 현재검증을통과한다(User user, StatementRequest request);
}

class LoginValidator extends RequestValidator {
    @Override
    protected boolean 현재검증을통과한다(User user, StatementRequest request) {
        if (!user.login) {
            System.out.println("[중단] 로그인이 필요합니다.");
            return false;
        }
        System.out.println("[통과] 로그인 확인");
        return true;
    }
}

class RoleValidator extends RequestValidator {
    @Override
    protected boolean 현재검증을통과한다(User user, StatementRequest request) {
        if (!user.statementManager) {
            System.out.println("[중단] 거래명세서 발송 권한이 없습니다.");
            return false;
        }
        System.out.println("[통과] 직원 권한 확인");
        return true;
    }
}

class AccountValidator extends RequestValidator {
    @Override
    protected boolean 현재검증을통과한다(User user, StatementRequest request) {
        if (!user.allowedAccountNo.equals(request.accountNo)) {
            System.out.println("[중단] 이 계좌에 접근할 수 없습니다.");
            return false;
        }
        System.out.println("[통과] 계좌 접근 권한 확인");
        return true;
    }
}

public class App {
    public static void main(String[] args) {
        RequestValidator validationChain = new LoginValidator();
        validationChain
                .setNext(new RoleValidator())
                .setNext(new AccountValidator());

        User user = new User("staff01", true, true, "110-123-456789");

        StatementRequest deniedRequest = new StatementRequest("999-999-999999");
        System.out.println("검증 결과: " + validationChain.검증한다(user, deniedRequest));

        System.out.println();

        StatementRequest allowedRequest = new StatementRequest("110-123-456789");
        System.out.println("검증 결과: " + validationChain.검증한다(user, allowedRequest));
    }
}
