package ex01.mem.step2;

// 공통 부모 Animal 로 묶었다. 그런데 Animal 이 "그냥 클래스"라서 몸통을 억지로 가진다.
class Animal {
    void 달린다() {
        System.out.println("동물이 달린다"); // 실체 없는 기본 동작 (의미 없음)
    }
}

class Dog extends Animal {
    @Override
    void 달린다() {
        System.out.println("강아지가 달린다");
    }
}

class Cat extends Animal {
    @Override
    void 달린다() {
        System.out.println("고양이가 달린다");
    }
}

public class App {

    // 좋아진 점 : 이제 메서드 하나로 모든 동물에게 시킨다 (다형성)
    static void 달리게한다(Animal animal) {
        animal.달린다();
    }

    public static void main(String[] args) {
        Animal dog = new Dog(); // 강아지는 두 개의 이름을 가진다 (강아지이자 동물)
        Animal cat = new Cat();

        달리게한다(dog); // 강아지가 달린다 (동적바인딩)
        달리게한다(cat); // 고양이가 달린다 (동적바인딩)

        System.out.println("----------------------------");

        // 문제 : "그냥 동물"이 실체로 만들어진다. 동물이 뭔데 달리지?
        Animal animal = new Animal();
        달리게한다(animal); // 동물이 달린다 (의미 없는 기본 동작이 조용히 실행된다)
    }
}
