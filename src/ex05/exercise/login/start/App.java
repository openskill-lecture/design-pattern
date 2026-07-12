package ex05.exercise.login.start;

// [연습문제 - 어댑터패턴] 소셜 로그인 : 제각각인 provider 응답을 내 User 하나로 통일하라.
//
// 내 User 규격(고정) : id, username, email, provider
// 지금 앱은 '네이버에만' 맞춰져 있다. NaverResponse는 마침 id/username/email 이라 그대로 담긴다.
// 그런데 카카오도 로그인시켜야 한다. KakaoResponse는 필드 이름이 다르다 : no / uId.
//   → 네이버용 코드를 그대로 못 쓴다. 카카오용을 또 심으면? 구글 오면 또... 앱(서비스)이 계속 바뀐다.
//
// ── 먼저 이대로 실행해 보라 ──────────────────
// 네이버 로그인만 찍힌다. 카카오는 담을 규격이 없다(아래 참고).
//
// TODO 1) 타깃 인터페이스 SocialLogin 을 만든다. 내 User가 원하는 모양 그대로:
//         - String getId(); String getUsername(); String getEmail(); String getProvider();
//
// TODO 2) NaverLoginAdapter implements SocialLogin 를 만든다. NaverResponse를 품는다.
//         - getId()→res.getId(),  getUsername()→res.getUsername(),  getEmail()→res.getEmail()
//         - getProvider()→"naver"   (네이버는 이름이 같아 그대로 전달된다)
//
// TODO 3) KakaoLoginAdapter implements SocialLogin 를 만든다. KakaoResponse를 품는다.
//         - getId()→res.getNo(),   getUsername()→res.getUId()      ← 이름을 바꿔 끼운다!
//         - getEmail()→res.getEmail(),  getProvider()→"kakao"
//
// TODO 4) MemberService 가 SocialLogin '하나만' 보고 User를 조립하는 로그인(SocialLogin) 으로 바꾼다.
//         - 이 한 줄이 네이버·카카오·(나중에)구글까지 그대로 처리된다.
//         - main에서 네이버·카카오를 각각 어댑터로 감싸 로그인()에 넘겨라.

// 내 User 규격 (고정)
class User {
    private String id, username, email, provider;
    User(String id, String username, String email, String provider) {
        this.id = id; this.username = username; this.email = email; this.provider = provider;
    }
    public String toString() {
        return "User{id=" + id + ", username=" + username + ", email=" + email + ", provider=" + provider + "}";
    }
}

// [외부 라이브러리] 네이버 응답. 못 고친다. 필드 : id / username / email
class NaverResponse {
    String getId()       { return "naver-1001"; }
    String getUsername() { return "김네이버"; }
    String getEmail()    { return "kim@naver.com"; }
}

// [외부 라이브러리] 카카오 응답. 못 고친다. 필드 이름이 다르다 : no / uId / email
class KakaoResponse {
    String getNo()    { return "kakao-2002"; }
    String getUId()   { return "박카카오"; }
    String getEmail() { return "park@kakao.com"; }
}

// 회원 서비스 : 지금은 '네이버 응답'에 직접 묶여 있다.
class MemberService {
    void 네이버로그인(NaverResponse res) {
        User user = new User(res.getId(), res.getUsername(), res.getEmail(), "naver");
        System.out.println("naver 로그인 → " + user);
    }
    // 카카오도 하려면? 필드 이름이 달라(no/uId) 위 코드를 그대로 못 쓴다.
    //   네이버로그인() 옆에 카카오로그인(KakaoResponse)을 또 만들어야 하고(가능은 하다),
    //   구글이 오면 구글로그인()... provider마다 서비스가 뚱뚱해진다.
    //   → SocialLogin 하나로 통일하고, 어댑터가 응답을 그 규격에 맞추게 하라. (TODO)
}

public class App {
    public static void main(String[] args) {
        MemberService service = new MemberService();

        service.네이버로그인(new NaverResponse());   // naver 로그인 → User{...}

        System.out.println("----------------------------");

        // 카카오가 왔다. 하지만 담을 규격이 없다.
        KakaoResponse kakao = new KakaoResponse();
        // service.네이버로그인(kakao);
        // ↑ 주석을 풀면 컴파일 에러! KakaoResponse는 NaverResponse가 아니다.
        //   설령 넣어도 no/uId라 이름이 안 맞는다.
        System.out.println("카카오 응답은 왔지만(no=" + kakao.getNo() + ") 담을 규격이 없다. 어댑터로 통일하라.");

        // 여기서부터 네 코드 (TODO 완료 후):
        // SocialLogin naverLogin = new NaverLoginAdapter(new NaverResponse());
        // SocialLogin kakaoLogin = new KakaoLoginAdapter(new KakaoResponse());
        // service.로그인(naverLogin);
        // service.로그인(kakaoLogin);
    }
}
