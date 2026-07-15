package ex10.notification.step3;

// 데코레이터 패턴 — 채널이 알림을 '감싸서' 기능을 덧붙인다.
// 채널 하나 늘어도 클래스는 하나만 추가. 런타임에 자유 조합.

interface Notifier { // 통로(약속)
    void send();
}

class BasicNotifier implements Notifier {

    // 재정의
    public void send() {
        System.out.println("기본 알림");
    }
}

class EmailNotifier implements Notifier {

    private Notifier notifier;

    public EmailNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public EmailNotifier() {
    }

    // 재정의
    public void send() {
        if (notifier != null)
            notifier.send();
        System.out.println("이메일 알림");
    }
}

class SmsNotifier implements Notifier {

    private Notifier notifier;

    public SmsNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public SmsNotifier() {
    }

    // 재정의
    public void send() {
        if (notifier != null)
            notifier.send(); // 기능 확장
        System.out.println("문자 알림");
    }
}

public class App {
    public static void main(String[] args) {
        // 1. 전체 알림 (기본알림 -> 문자알림 -> 이메일알림)
        Notifier allNotifier = new EmailNotifier(new SmsNotifier(new BasicNotifier()));
        allNotifier.send();
        System.out.println("__end");

        // 2. 전체 알림 (기본알림 -> 이메일알림 -> 문자알림)
        Notifier allNotifier2 = new SmsNotifier(new EmailNotifier(new BasicNotifier()));
        allNotifier2.send();
        System.out.println("__end");

        // 3. 전체 알림 (기본알림 -> ~~~~~~~~)
        Notifier allNotifier3 = new SmsNotifier(new EmailNotifier(new EmailNotifier(new BasicNotifier())));
        allNotifier3.send();
        System.out.println("__end");

        // 4. 기본 알림
        Notifier basicNotifier = new BasicNotifier();
        basicNotifier.send();
        System.out.println("__end");

        // 5. 기본 알림 + 문자 알림
        Notifier smsNotifier = new SmsNotifier(new BasicNotifier());
        smsNotifier.send();
        System.out.println("__end");

        // 6. 기본 알림 + 이메일 알림
        Notifier emailNotifier = new EmailNotifier(new BasicNotifier());
        emailNotifier.send();
        System.out.println("__end");

        // 7. 이메일 알림
        Notifier onlyEmailNotifier = new EmailNotifier();
        onlyEmailNotifier.send();
        System.out.println("__end");

        // 8. 문자 알림
        Notifier onlySmsNotifier = new SmsNotifier();
        onlySmsNotifier.send();
        System.out.println("__end");

        // 9. 문자알림 + 이메일알림
        Notifier smsAndEmailNotifier = new EmailNotifier(new SmsNotifier());
        smsAndEmailNotifier.send();
        System.out.println("__end");
    }
}
