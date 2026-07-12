package ex04.exercise.start;

// [연습문제 - 프록시패턴] 무거운 이미지 로딩을 '실제로 보여줄 때'까지 미뤄라 (가상 프록시).
//
// RealImage 는 생성되는 순간 디스크에서 파일을 읽어(무겁다) 버린다.
// 화면에 그리지도 않을 이미지까지 미리 다 읽으면 낭비다.
// 프록시로 감싸서, 처음 그려질(보여질) 때만 RealImage 를 로딩하게 하라.
//
// TODO 1) Image 인터페이스를 만든다 ( 그린다() )
// TODO 2) RealImage 가 Image 를 구현하고, 생성 시 '로딩' 메시지를 찍는다
// TODO 3) ImageProxy 가 같은 Image 타입으로, 그린다() 가 처음 불릴 때만 RealImage 를 생성한다
// TODO 4) main 에서 프록시를 여러 개 만들되 일부만 그려서, 그린 것만 로딩되는지 확인한다

class RealImage {
    RealImage(String fileName) {
        System.out.println("[로딩] " + fileName + " 디스크에서 읽는 중...(무겁다)");
    }
    void 그린다() {
        System.out.println("그린다");
    }
}

public class App {
    public static void main(String[] args) {
        // 지금은 만들자마자 무조건 로딩된다. 그리지도 않는데!
        RealImage image = new RealImage("cat.png");

        // 목표 : 프록시로 감싸 '그릴 때' 로딩되게 만들기
    }
}
