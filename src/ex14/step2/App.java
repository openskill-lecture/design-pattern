package ex14.step2;

// [step2] App 은 step1 과 똑같다! 바뀐 건 Dispatcher 속뿐
public class App {
    public static void main(String[] args) throws Exception {
        String uri = "/update";

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.dispatch(uri);
    }
}
