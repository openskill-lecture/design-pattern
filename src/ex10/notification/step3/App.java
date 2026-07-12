package ex10.notification.step3;

// 데코레이터 패턴 — 채널이 알림을 '감싸서' 기능을 덧붙인다.
// 채널 하나 늘어도 클래스는 하나만 추가. 런타임에 자유 조합.

interface Notifier {                      // 통로(약속)
    void send();
}

class BasicNotifier implements Notifier { // 알맹이
    @Override
    public void send() {
        System.out.println("기본 알림");
    }
}

abstract class NotifierDecorator implements Notifier {  // 감싸는 포장(데코레이터)
    protected Notifier notifier;          // 감싸고 있는 대상
    NotifierDecorator(Notifier notifier) { this.notifier = notifier; }
}

class SmsNotifier extends NotifierDecorator {
    SmsNotifier(Notifier notifier) { super(notifier); }
    @Override
    public void send() {
        notifier.send();                  // 감싼 대상 먼저 보내고
        System.out.println("문자 알림");   // 내 채널을 덧붙인다
    }
}

class EmailNotifier extends NotifierDecorator {
    EmailNotifier(Notifier notifier) { super(notifier); }
    @Override
    public void send() {
        notifier.send();
        System.out.println("이메일 알림");
    }
}

public class App {
    public static void main(String[] args) {
        // 기본 알림을 감싸며 채널을 덧붙인다 — 런타임에 원하는 만큼 조합
        Notifier all = new EmailNotifier(new SmsNotifier(new BasicNotifier()));
        all.send();

        System.out.println("----------------------------");
        // 순서를 바꾸든(이메일→문자), 채널을 빼든 조합만 바꾸면 끝. 새 클래스가 필요 없다.
        Notifier smsOnly = new SmsNotifier(new BasicNotifier());
        smsOnly.send();
    }
}
