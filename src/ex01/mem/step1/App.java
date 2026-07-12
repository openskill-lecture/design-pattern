package ex01.mem.step1;

// 다형성 없이 : 구체적인 것(강아지, 고양이)에게 직접 일을 시킨다.
class Dog {
    void 달린다() {
        System.out.println("강아지가 달린다");
    }
}

class Cat {
    void 달린다() {
        System.out.println("고양이가 달린다");
    }
}

public class App {

    // 나쁨 : 구체 타입마다 메서드가 하나씩 늘어난다
    static void 달리게한다(Dog dog) {
        dog.달린다();
    }

    static void 달리게한다(Cat cat) {
        cat.달린다();
    }
    // Horse 가 오면? 달리게한다(Horse) 를 또 추가해야 한다. 끝이 없다.

    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();

        달리게한다(dog); // 강아지가 달린다
        달리게한다(cat); // 고양이가 달린다
    }
}
