package ex04.exercise.start;

// [연습문제 - 프록시패턴] 무거운 이미지 로딩을 '실제로 보여줄 때'까지 미뤄라 (가상 프록시).
//
// RealImage 는 생성되는 순간 디스크에서 파일을 읽어(무겁다) 버린다.
// 화면에 그리지도 않을 이미지까지 미리 다 읽으면 낭비다.
// 프록시로 감싸서, 처음 그려질(보여질) 때만 RealImage 를 로딩하게 하라.
//
// ── 먼저 이대로 실행해 보라 ──────────────────
// 그리지도 않았는데 [로딩] 메시지가 찍힌다. 이게 바로 낭비다(문제를 눈으로 확인).
// 이제 아래 순서대로 고쳐서, '그릴 때만' 로딩되게 만든다.
//
// TODO 1) Image 인터페이스를 만든다.
//         - 그린다() 메서드 하나만 있으면 된다.
//
// TODO 2) RealImage 가 Image 를 구현하게 한다 (implements Image).
//         - 아래 RealImage 를 고쳐서 Image 타입으로 만든다.
//
// TODO 3) ImageProxy 를 새로 만든다. RealImage 와 '같은 Image 타입'이다.
//         - 생성자에서는 파일명만 저장한다. 아직 RealImage 를 만들지 마라(로딩 X).
//         - 그린다() 가 처음 불릴 때 딱 한 번 RealImage 를 new 한다. (이게 가장 중요함)
//         - 두 번째부터는 이미 만든 RealImage 를 재사용한다(로딩 X).
//
// TODO 4) main 을 고친다.
//         - 아래 new RealImage(...) 줄들을 주석 처리한다.
//         - 대신 new ImageProxy(...) 로 만든다. (변수 타입은 Image 로 받는다)
//         - 여러 개(cat, dog) 만들어도 [로딩] 이 안 찍히는지 확인한다.
//         - 그 중 하나만 .그린다() 를 호출 → 그 순간 딱 한 번 [로딩] 되는지 확인한다.
//         - 안 그린 이미지는 끝까지 로딩되지 않아야 한다.

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
        // ── 지금 상태(BEFORE): 만들자마자 무조건 로딩된다. 그리지도 않는데! ──
        // TODO 4 : 아래 두 줄을 주석 처리하고, 프록시로 바꿔라.
        RealImage cat = new RealImage("cat.png");   // [로딩] 찍힌다
        RealImage dog = new RealImage("dog.png");   // [로딩] 찍힌다 — 근데 dog 은 안 그릴 건데?

        // 여기서부터 네 코드 :
        // (프록시로 바꾸면, 이 지점까지 [로딩] 이 하나도 안 찍혀야 한다.
        //  그리고 .그린다() 를 부른 이미지만 그 순간 로딩된다.)
    }
}
