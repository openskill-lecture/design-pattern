package ex11.weather.step3;

// step3 : 진짜(RealWeather)가 완성됐다. 서비스 코드는 한 줄도 안 고치고 '갈아끼우기'만.
// 이게 되는 이유 = 가짜도 진짜도 '같은 계약(Weather)'을 지켰기 때문.

interface Weather {
    int getTemp();
}

class FakeWeather implements Weather {   // 개발용 가짜(Stub) : 정해진 값을 대답
    public int getTemp() {
        return 20;
    }
}

class RealWeather implements Weather {   // 날씨팀이 완성한 진짜
    public int getTemp() {
        return 23;                       // 실제 외부 API에서 읽은 값
    }
}

class WeatherService {
    private Weather weather;

    WeatherService(Weather weather) {
        this.weather = weather;
    }

    void render() {
        int temp = weather.getTemp();
        System.out.println("온도 : " + temp);
    }
}

public class App {
    public static void main(String[] args) {
        System.out.println("== 개발 중 (가짜) ==");
        new WeatherService(new FakeWeather()).render();   // 온도 : 20

        System.out.println("----------------------------");

        System.out.println("== 배포 (진짜) ==");
        new WeatherService(new RealWeather()).render();   // 온도 : 23

        // WeatherService 는 그대로. new FakeWeather() → new RealWeather() 만 바꿨다.
    }
}
