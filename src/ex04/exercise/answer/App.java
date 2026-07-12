package ex04.exercise.answer;

// [연습문제 정답 - 프록시패턴] 가상 프록시 : 실제로 그릴 때까지 로딩을 미룬다.
interface Image {
    void 그린다();
}

// [원본] 생성되는 순간 무거운 로딩이 일어난다.
class RealImage implements Image {
    private final String fileName;

    RealImage(String fileName) {
        this.fileName = fileName;
        System.out.println("[로딩] " + fileName + " 디스크에서 읽는 중...(무겁다)");
    }

    public void 그린다() {
        System.out.println(fileName + " 그린다");
    }
}

// [프록시] 같은 Image 타입. 처음 그려질 때만 RealImage 를 만든다(지연 로딩).
class ImageProxy implements Image {
    private final String fileName;
    private RealImage real;   // 아직 안 만든다

    ImageProxy(String fileName) {
        this.fileName = fileName;
    }

    public void 그린다() {
        if (real == null) {          // 처음 그릴 때 딱 한 번 로딩
            real = new RealImage(fileName);
        }
        real.그린다();
    }
}

public class App {
    public static void main(String[] args) {
        // 프록시는 만들어도 로딩되지 않는다.
        Image cat = new ImageProxy("cat.png");
        Image dog = new ImageProxy("dog.png");
        System.out.println("이미지 목록 준비 완료 (아직 아무것도 로딩 안 됨)");

        System.out.println("----------------------------");

        // 실제로 그리는 것만 이 순간 로딩된다.
        cat.그린다();   // [로딩] cat.png ... → cat.png 그린다
        cat.그린다();   // 두 번째부터는 로딩 없이 바로 그린다
        // dog 은 끝내 안 그렸으니 로딩도 안 된다.
    }
}
