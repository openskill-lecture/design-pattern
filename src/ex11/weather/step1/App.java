package ex11.weather.step1;

// 외부 날씨 API 팀은 30일 걸린다. 그런데 나(화면)는 먼저 개발해야 한다.
// step1 : 진짜(RealWeather)에 직접 묶여 있으면, 진짜가 나올 때까지 한 줄도 못 돌린다.

// 진짜 날씨 API : 외부 연동이 있어야 동작. 아직 개발 중(30일)이라 값을 못 준다.
class RealWeather {
    int getTemp() {
        // 외부 API 연동은 아직 미완성. (날씨팀 30일)
        throw new RuntimeException("아직 개발 중 : 외부 날씨 API 미완성 (30일 뒤 완성)");
    }
}

// 화면 서비스가 진짜 날씨 API를 직접 new 해서 쓴다. → 갈아끼울 문이 없다.
class WeatherService {
    private RealWeather weather = new RealWeather();   // 진짜에 딱 붙어버렸다

    void render() {
        int temp = weather.getTemp();
        System.out.println("온도 : " + temp);
    }
}

public class App {
    public static void main(String[] args) {
        WeatherService ws = new WeatherService();

        try {
            ws.render();                  // 진짜가 아직 없어서 여기서 터진다.
        } catch (RuntimeException e) {
            System.out.println("못 돌림 → " + e.getMessage());
            System.out.println("진짜가 나올 때까지 화면은 대기. 병렬 개발이 안 된다!");
        }
    }
}
