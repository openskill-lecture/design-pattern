package ex09.observerpattern.step3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 어떤 상품을 누가 구독할지 등록
    void register(String product, Observer observer);

    // 어떤 상품의 구독을 취소할지 지정
    void remove(String product, Observer observer);

    // 특정 상품을 구독한 사람들에게만 알림
    void noti(String product, String message);
}

class Mart implements Subject {

    /*
     * 상품 이름 → 해당 상품을 구독한 손님 목록
     *
     * 포도 → [손님1, 손님3]
     * 사과 → [손님2, 손님3]
     */
    private final Map<String, List<Observer>> observersByProduct = new HashMap<>();

    @Override
    public void register(String product, Observer observer) {

        // 해당 상품의 구독자 목록을 가져온다.
        List<Observer> observers = observersByProduct.get(product);

        // 아직 해당 상품의 구독자 목록이 없다면 새로 만든다.
        if (observers == null) {
            observers = new ArrayList<>();

            // 새로 만든 구독자 목록을 Map에 저장한다.
            observersByProduct.put(product, observers);
        }

        // 해당 상품의 구독자 목록에 손님을 추가한다.
        observers.add(observer);
    }

    @Override
    public void remove(String product, Observer observer) {

        List<Observer> observers = observersByProduct.get(product);

        // 해당 상품의 구독자 목록이 있을 때만 제거한다.
        if (observers != null) {
            observers.remove(observer);
        }
    }

    @Override
    public void noti(String product, String message) {

        List<Observer> observers = observersByProduct.get(product);

        // 해당 상품의 구독자가 없으면 알림을 보내지 않는다.
        if (observers == null) {
            return;
        }

        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void 입고(String product) {

        System.out.println("[마트] " + product + " 입고 진행 중...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        System.out.println("[마트] " + product + " 입고 완료!");

        // 해당 상품을 구독한 손님에게만 알림
        noti(product, product + " 들어왔어요");
    }
}

public class App {

    public static void main(String[] args) {

        Observer customer1 = new Customer("손님1");
        Observer customer2 = new Customer("손님2");
        Observer customer3 = new Customer("손님3");

        Mart mart = new Mart();

        // 손님1은 포도 구독
        mart.register("포도", customer1);

        // 손님2는 사과 구독
        mart.register("사과", customer2);

        // 손님3은 포도와 사과 모두 구독
        mart.register("포도", customer3);
        mart.register("사과", customer3);

        // 손님2는 사과 구독 취소
        mart.remove("사과", customer2);

        new Thread(() -> mart.입고("사과")).start();
    }
}