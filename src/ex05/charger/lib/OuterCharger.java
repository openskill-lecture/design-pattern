package ex05.charger.lib;

// [외부 라이브러리] 다른 회사가 만든 해외 충전기. 우리는 이 파일을 못 고친다.
// 규격이 두 군데나 어긋난다 :
//   (1) 전압을 주는 메서드가 우리 규격(공급전압)이 아니라 voltage() 이고,
//   (2) 값도 우리 220V가 아니라 미국식 110V 다.
public class OuterCharger {
    private int volt = 110;   // 미국식 110V

    public int voltage() {    // 우리 규격 공급전압()이 아니다
        return volt;
    }
}
