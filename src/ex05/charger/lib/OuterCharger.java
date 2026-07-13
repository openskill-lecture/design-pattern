package ex05.charger.lib;

// [외부 라이브러리] 다른 회사가 만든 충전기. 우리는 이 파일을 못 고친다.
// 충전 메서드가 우리 규격(TypeC/충전한다)이 아니라 둥근핀충전() 이다.
public class OuterCharger {
    public void 둥근핀충전() {
        System.out.println("둥근핀으로 충전 중...");
    }
}
