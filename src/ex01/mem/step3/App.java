package ex01.mem.step3;

/**
 * 동물의 달린다() 는 추상 → 몸통이 없다. 그냥 "통로"다.
 *
 * heap 메모리 생성 규칙 : new Dog() 를 하면
 *   부모(Animal)가 먼저 만들어지고, 그 위에 자식(Dog)이 만들어진다.
 *   [ heap : Animal(달린다-통로) ← Dog(달린다-재정의) ]
 *
 * 동적바인딩 : animal.달린다() 호출 → Animal 의 달린다(통로)으로 들어가면,
 *   자식이 재정의한 달린다 가 부모 것을 무효화(override)했으므로
 *   "실행 시점"에 진짜 타입(Dog)의 달린다 가 호출된다.
 */

abstract class Animal { // new 불가. 통로 역할.
    abstract void 달린다(); // 몸통 없음 = 통로
}

class Dog extends Animal {
    @Override // 재정의 : 부모의 통로를 실제 동작으로 채운다
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

    static void 달리게한다(Animal animal) {
        animal.달린다(); // 동적바인딩 : 실행 시점에 진짜 타입의 달린다 가 호출된다
    }

    public static void main(String[] args) {
        Animal dog = new Dog(); // heap : Animal(달린다-통로) ← Dog(달린다)
        Animal cat = new Cat(); // heap : Animal(달린다-통로) ← Cat(달린다)

        달리게한다(dog); // 강아지가 달린다
        달리게한다(cat); // 고양이가 달린다

        // Animal animal = new Animal(); // 컴파일 에러! 추상클래스는 new 할 수 없다.
        // "그냥 동물"이라는 실체 없는 객체가 아예 만들어질 수 없다. (step2의 문제가 원천 차단)

        // 내일 Horse 가 추가되어도?
        // class Horse extends Animal 만 만들면 끝. 달리게한다()는 안 고친다.
    }
}
