package ex05.exercise.login.answer;

// [연습문제 정답 - 어댑터패턴] 제각각인 provider 응답을 SocialLogin 하나로 통일한다.
// 어댑터가 응답의 다른 필드 이름을 내 규격으로 변환 → User는 provider 몰라도 한 줄로 조립된다.

// 내 User 규격 (고정)
class User {
    private String id, username, email, provider;
    User(String id, String username, String email, String provider) {
        this.id = id; this.username = username; this.email = email; this.provider = provider;
    }
    public String getProvider() { return provider; }
    public String toString() {
        return "User{id=" + id + ", username=" + username + ", email=" + email + ", provider=" + provider + "}";
    }
}

// [외부 라이브러리] 못 고친다. 네이버 : id / username / email
class NaverResponse {
    String getId()       { return "naver-1001"; }
    String getUsername() { return "김네이버"; }
    String getEmail()    { return "kim@naver.com"; }
}

// [외부 라이브러리] 못 고친다. 카카오 : no / uId / email  (이름이 다르다)
class KakaoResponse {
    String getNo()    { return "kakao-2002"; }
    String getUId()   { return "박카카오"; }
    String getEmail() { return "park@kakao.com"; }
}

// [타깃] 내 앱이 아는 유일한 규격 (= User가 원하는 모양)
interface SocialLogin {
    String getId();
    String getUsername();
    String getEmail();
    String getProvider();
}

// [어댑터] 네이버 : 이름이 같아 그대로 전달(pass-through). 그래도 '규격'을 맞추려 감싼다.
class NaverLoginAdapter implements SocialLogin {
    private NaverResponse res;
    NaverLoginAdapter(NaverResponse res) { this.res = res; }
    public String getId()       { return res.getId(); }
    public String getUsername() { return res.getUsername(); }
    public String getEmail()    { return res.getEmail(); }
    public String getProvider() { return "naver"; }
}

// [어댑터] 카카오 : 이름을 바꿔 끼운다. no→id, uId→username
class KakaoLoginAdapter implements SocialLogin {
    private KakaoResponse res;
    KakaoLoginAdapter(KakaoResponse res) { this.res = res; }
    public String getId()       { return res.getNo(); }    // no  → id
    public String getUsername() { return res.getUId(); }   // uId → username
    public String getEmail()    { return res.getEmail(); }
    public String getProvider() { return "kakao"; }
}

// 회원 서비스 : SocialLogin '하나만' 안다. 어느 provider인지 몰라도 된다.
class MemberService {
    void 로그인(SocialLogin login) {
        // 이 한 줄이 네이버·카카오·(나중에)구글까지 그대로 처리한다.
        User user = new User(login.getId(), login.getUsername(), login.getEmail(), login.getProvider());
        System.out.println(user.getProvider() + " 로그인 → " + user);
    }
}

public class App {
    public static void main(String[] args) {
        MemberService service = new MemberService();

        // 외부 응답을 어댑터로 감싸면, 서비스 눈엔 똑같은 SocialLogin이다.
        SocialLogin naver = new NaverLoginAdapter(new NaverResponse());
        SocialLogin kakao = new KakaoLoginAdapter(new KakaoResponse());

        service.로그인(naver);   // naver 로그인 → User{...}
        service.로그인(kakao);   // kakao 로그인 → User{...}  ← no/uId가 id/username으로 변환됨

        // 구글이 오면? GoogleLoginAdapter 하나만 더 만들면 끝.
        // MemberService.로그인()도, 위 User 조립 한 줄도 안 바뀐다.
    }
}
