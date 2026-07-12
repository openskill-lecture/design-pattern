package ex06.exercise.config.start;

// [연습문제1 - 싱글톤] 앱 설정은 한 곳이어야 화면마다 안 어긋난다.
//
// AppSetting 은 다크모드 on/off 를 들고 있다. 앱 전체가 같은 설정을 봐야 한다.
// 그런데 지금은 아무나 new AppSetting() 을 해서 설정 객체가 여러 개 생긴다.
// 설정 화면에서 다크모드를 켜도, 메인 화면이 보는 건 딴 객체라 여전히 꺼져 있다!
//
// ── 먼저 이대로 실행해 보라 ──────────────────
// 설정 화면에서 다크모드를 켰는데, 메인 화면은 아직 밝은 화면이다. 설정이 갈렸다.
// (두 설정을 나중에 '합치는' 걸로는 못 고친다 — 읽는 그 순간 같은 설정을 봐야 한다.)
//
// TODO 1) AppSetting 을 싱글톤으로 고쳐라.
//         - 하나만 미리 만든다   : private static final AppSetting instance = new AppSetting();
//         - 생성자를 막는다      : private AppSetting() {}
//         - 꺼내는 통로를 연다   : static AppSetting getInstance() { return instance; }
//
// TODO 2) main 에서 new AppSetting() 대신 AppSetting.getInstance() 로 얻어라.
//         - 두 화면이 같은 설정을 보면, 켜자마자 메인 화면도 다크모드가 된다.

class AppSetting {
    boolean darkMode = false;

    void 다크모드켜기() {
        darkMode = true;
    }

    boolean isDarkMode() {
        return darkMode;
    }
}

public class App {
    public static void main(String[] args) {
        // 설정 화면과 메인 화면이 각자 설정을 만든다. (여기가 문제)
        AppSetting 설정화면 = new AppSetting();
        AppSetting 메인화면 = new AppSetting();

        설정화면.다크모드켜기();   // 설정 화면에서 다크모드를 켰다

        System.out.println("설정화면 다크모드 ? " + 설정화면.isDarkMode());   // true
        System.out.println("메인화면 다크모드 ? " + 메인화면.isDarkMode());   // false ← 안 켜졌다!

        System.out.println("----------------------------");

        System.out.println("같은 설정인가 ? " + (설정화면 == 메인화면));  // false

        // 여기서부터 네 코드 : AppSetting 을 싱글톤으로 고쳐, 설정이 한 곳이 되게 하라.
    }
}
