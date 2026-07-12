package ex09.exercise.youtube.start;

// [연습] 유튜브 채널이 구독자에게 새 영상을 알린다.
// 지금은 채널이 구독자를 '직접 필드로' 박아둬서 강결합이다.
//
// TODO: 옵저버 패턴으로 바꿔라 —
//   1) Subscriber 를 인터페이스(통로)로.
//   2) Channel 이 List<Subscriber> 명단만 알게.
//   3) 구독()/구독취소()/알림() 으로 자유롭게 추가·취소되게.
// 목표 : 구독취소한 구독자는 알림을 받지 않는다. (정답은 answer 폴더)

class Subscriber {
    private String name;
    Subscriber(String name) { this.name = name; }

    void 알림받기(String msg) {
        System.out.println(name + " : " + msg);
    }
}

class Channel {
    private Subscriber sub1;
    private Subscriber sub2;

    void 구독(Subscriber a, Subscriber b) {
        this.sub1 = a;
        this.sub2 = b;
    }

    void 동영상올리기(String title) {
        System.out.println("[채널] 새 영상 : " + title);
        sub1.알림받기(title + " 올라옴!");
        sub2.알림받기(title + " 올라옴!");
        // 구독자3을 늘리거나, 구독취소를 하려면? 여기를 고쳐야 한다. (바꿔보자)
    }
}

public class App {
    public static void main(String[] args) {
        Subscriber sub1 = new Subscriber("구독자1");
        Subscriber sub2 = new Subscriber("구독자2");

        Channel channel = new Channel();
        channel.구독(sub1, sub2);

        channel.동영상올리기("옵저버 패턴 10분 완성");
    }
}
