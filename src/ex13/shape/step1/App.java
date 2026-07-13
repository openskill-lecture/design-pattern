package ex13.shape.step1;

// simple factory 변형: 생성을 누가 하나? 아직은 클라이언트가 직접 new 한다.

// 도형마다 구현이 다르다. 공통 통로는 Shape 인터페이스.
interface Shape {
    void 그린다();
}

class Circle implements Shape {
    private String name;
    public void setName(String name) { this.name = name; }
    public void 그린다() {
        System.out.println(name + " 원을 그린다");
    }
}

class Rectangle implements Shape {
    private String name;
    public void setName(String name) { this.name = name; }
    public void 그린다() {
        System.out.println(name + " 사각형을 그린다");
    }
}

// 클라이언트가 구체 타입을 직접 알고, new + name 세팅까지 떠안는다.
public class App {
    public static void main(String[] args) {
        // 원을 그리려면 : 구체 클래스 이름도, 생성 절차도 클라이언트 몫
        Circle circle = new Circle();
        circle.setName("빨간색");   // 생성 절차가 여기 흩어진다
        circle.그린다();

        System.out.println("----------------------------");

        // 사각형으로 바꾸려면 : 클라이언트를 또 고친다. new도 세팅도 똑같이 여기서 반복
        Rectangle rect = new Rectangle();
        rect.setName("파란색");
        rect.그린다();
    }
}
