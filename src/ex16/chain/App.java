package ex16.chain;

abstract class Filter {
    protected Filter next;

    public Filter setNext(Filter next) {
        this.next = next;
        return next;
    }

    public void doFilter(Request request) {
        if (next != null) {
            next.doFilter(request);
        }
    }
}

class Request {
    boolean login;
    boolean admin;

    Request(boolean login, boolean admin) {
        this.login = login;
        this.admin = admin;
    }
}

class LogFilter extends Filter {
    @Override
    public void doFilter(Request request) {
        System.out.println("1. 요청 로그 기록");
        super.doFilter(request);
    }
}

class LoginFilter extends Filter {
    @Override
    public void doFilter(Request request) {
        if (!request.login) {
            System.out.println("2. 로그인 안됨 → 요청 중단");
            return;
        }

        System.out.println("2. 로그인 확인");
        super.doFilter(request);
    }
}

class AdminFilter extends Filter {
    @Override
    public void doFilter(Request request) {
        if (!request.admin) {
            System.out.println("3. 관리자 권한 없음 → 요청 중단");
            return;
        }

        System.out.println("3. 관리자 권한 확인");
        super.doFilter(request);
    }
}

public class App {
    public static void main(String[] args) {
        Filter logFilter = new LogFilter();
        Filter loginFilter = new LoginFilter();
        Filter adminFilter = new AdminFilter();

        logFilter
                .setNext(loginFilter)
                .setNext(adminFilter);

        logFilter.doFilter(new Request(true, true));
    }
}
