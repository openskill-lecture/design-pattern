package ex10.notification.step2;

// 채널 '조합마다' 클래스를 새로 판다 → 채널 하나 늘 때마다 클래스가 2배씩 폭발.

class BasicNotifier {
    void send() {
        System.out.println("기본 알림");
    }
}

class SmsBasicNotifier {                  // 기본+문자
    void send() {
        System.out.println("기본 알림");
        System.out.println("문자 알림");
    }
}

class EmailBasicNotifier {                // 기본+이메일
    void send() {
        System.out.println("기본 알림");
        System.out.println("이메일 알림");
    }
}

class SmsEmailBasicNotifier {             // 기본+문자+이메일
    void send() {
        System.out.println("기본 알림");
        System.out.println("문자 알림");
        System.out.println("이메일 알림");
    }
}
// 여기에 '카카오톡'까지 넣으면? SmsKakaoBasic, EmailKakaoBasic,
// SmsEmailKakaoBasic … 채널 하나 늘 때마다 클래스가 2배씩 폭발한다.

public class App {
    public static void main(String[] args) {
        // 전체 알림 주문 (조합 클래스를 콕 집어 골라야 한다)
        SmsEmailBasicNotifier notifier = new SmsEmailBasicNotifier();
        notifier.send();

        System.out.println("----------------------------");
        // "이메일만 빼줘" → SmsBasicNotifier 로 통째로 갈아끼워야 한다. 런타임 변경 불가.
        System.out.println("(이메일만 빼고 싶은데... 클래스를 통째로 바꿔야 하네요)");
        // → 채널을 더하거나 빼려면 매번 새 클래스가 필요하다. 데코레이터로 바꿔보자.
    }
}
