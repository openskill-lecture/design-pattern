package ex09.observerpattern.step2;

import java.util.ArrayList;
import java.util.List;


interface Observer {
    void update(String message);
}


class Customer implements Observer {
    private final String name;

    Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " : " + message);
    }
}

interface Subject {
    void register(Observer observer);
    void remove(Observer observer);
    void noti(String message);
}

// 4. 구체적인 Subject
class Mart implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void noti(String msg) {
        observers.stream()
                .forEach(ob -> ob.update(msg));
    }

    public void 입고() {
        System.out.println("[마트] 입고 진행중....");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        System.out.println("[마트] 포도 입고 완료!");

        // 상태 변화가 발생했으므로 옵저버들에게 알린다.
        noti("포도 들어왔어요");
    }
}

// 5. 실행
public class App {

    public static void main(String[] args) {

        Observer customer1 = new Customer("손님1");
        Observer customer2 = new Customer("손님2");
        Observer customer3 = new Customer("손님3");

        Mart mart = new Mart();

        mart.register(customer1);
        mart.register(customer2);
        mart.register(customer3);

        // 손님2는 더 이상 알림을 받지 않는다.
        mart.remove(customer2);

        new Thread(mart::입고).start();
    }
}