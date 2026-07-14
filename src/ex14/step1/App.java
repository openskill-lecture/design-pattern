package ex14.step1;

// [step1] App 은 uri 만 Dispatcher 에게 넘긴다
public class App {
    public static void main(String[] args) {
        String uri = "/insert";        // 요청이 들어왔다고 가정

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.dispatch(uri);      // 나머지는 Dispatcher 가 알아서
    }
}
