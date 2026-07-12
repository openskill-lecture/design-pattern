package ex05.adapter.lib;

// [외부 라이브러리] 다른 회사가 만든 토끼. 우리는 이 파일을 못 고친다.
// 이름을 주는 메서드가 우리 규격(getName)이 아니라 getFullname() 이다.
public class OuterRabbit {
    private String fullname = "토끼";

    public String getFullname() {
        return fullname;
    }
}
