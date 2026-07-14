package ex09.basic;

public class App {
    public static void main(String[] args) {
        Cat cat = new Cat();
        Dog dog = new Dog();

        Owner owner = new Owner();
        owner.register(cat);
        owner.register(dog);

        owner.noti();
    }
}
