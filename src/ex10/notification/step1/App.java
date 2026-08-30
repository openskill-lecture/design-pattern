package ex10.notification.step1;

// 알림은 기본 하나뿐. 여기에 문자·이메일도 '같이' 보내고 싶어진다.

class BasicNotifier {
    void send() {
        System.out.println("기본 알림");
    }
}

public class App {
    public static void main(String[] args) {
        BasicNotifier notifier = new BasicNotifier();
        notifier.send();

        // 문자도, 이메일도 같이 보내려면? 여기서부터 기능을 늘려야 한다. (다음 step)
    }
}
