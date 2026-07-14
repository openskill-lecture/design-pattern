package ex09.basic;

import java.util.ArrayList;
import java.util.List;

public class Owner { // Publisher
    List<Observer> observers = new ArrayList<>();
    void register(Observer observer){
        observers.add(observer);
    }

    void noti(){
        observers.stream()
            .forEach(o -> o.update());
    }
}
