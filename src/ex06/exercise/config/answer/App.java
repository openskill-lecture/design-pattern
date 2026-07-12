package ex06.exercise.config.answer;

// [연습문제1 정답 - 싱글톤] 앱 설정을 하나로 모아 화면마다 같은 설정을 보게 한다.

class AppSetting {
    // 하나만 미리 만든다.
    private static final AppSetting instance = new AppSetting();

    boolean darkMode = false;

    // 생성자를 막는다. 밖에서 new AppSetting() 못한다.
    private AppSetting() {}

    // 그 하나를 꺼내주는 유일한 통로.
    static AppSetting getInstance() {
        return instance;
    }

    void 다크모드켜기() {
        darkMode = true;
    }

    boolean isDarkMode() {
        return darkMode;
    }
}

public class App {
    public static void main(String[] args) {
        // 두 화면이 같은 설정을 얻는다.
        AppSetting 설정화면 = AppSetting.getInstance();
        AppSetting 메인화면 = AppSetting.getInstance();

        설정화면.다크모드켜기();   // 설정 화면에서 다크모드를 켰다

        System.out.println("설정화면 다크모드 ? " + 설정화면.isDarkMode());   // true
        System.out.println("메인화면 다크모드 ? " + 메인화면.isDarkMode());   // true ← 같은 설정이라 켜졌다

        System.out.println("----------------------------");

        System.out.println("같은 설정인가 ? " + (설정화면 == 메인화면));  // true
    }
}
