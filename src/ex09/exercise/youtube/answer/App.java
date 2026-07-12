package ex09.exercise.youtube.answer;

import java.util.ArrayList;
import java.util.List;

// [정답] 옵저버 패턴 — 채널은 구독 명단만 안다. 추가·취소가 자유롭다.

interface Subscriber {                  // 통로(약속)
    void 알림받기(String msg);
}

class Viewer implements Subscriber {
    private String name;
    Viewer(String name) { this.name = name; }

    @Override
    public void 알림받기(String msg) {
        System.out.println(name + " : " + msg);
    }
}

class Channel {
    private List<Subscriber> subscriberList = new ArrayList<>();   // 구독 명단

    void 구독(Subscriber s)     { subscriberList.add(s); }
    void 구독취소(Subscriber s) { subscriberList.remove(s); }

    private void 알림(String msg) {
        subscriberList.forEach(s -> s.알림받기(msg));
    }

    void 동영상올리기(String title) {
        System.out.println("[채널] 새 영상 : " + title);
        알림(title + " 올라옴!");
    }
}

public class App {
    public static void main(String[] args) {
        Subscriber sub1 = new Viewer("구독자1");
        Subscriber sub2 = new Viewer("구독자2");
        Subscriber sub3 = new Viewer("구독자3");

        Channel channel = new Channel();
        channel.구독(sub1);
        channel.구독(sub2);
        channel.구독(sub3);

        channel.구독취소(sub2);      // 구독자2 취소 — 채널 코드는 안 건드림!

        channel.동영상올리기("옵저버 패턴 10분 완성");
        // 명단에 남은 구독자1·구독자3만 알림을 받는다.
    }
}
