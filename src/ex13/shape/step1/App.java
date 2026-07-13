package ex13.shape.step1;

// simple factory 변형: 생성을 누가 하나? 아직은 클라이언트가 직접 new 한다.

// 도형마다 넓이 계산법이 다르다. 공통 통로는 Shape 인터페이스.
interface Shape {
    void 그린다();
    int 넓이();
}

class Circle implements Shape {
    private int radius;
    public void setRadius(int radius) { this.radius = radius; }
    public int 넓이() {
        return radius * radius * 3;   // 원주율은 3으로 단순화
    }
    public void 그린다() {
        System.out.println("반지름 " + radius + " 원 (넓이 " + 넓이() + ")");
    }
}

class Rectangle implements Shape {
    private int width;
    private int height;
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int 넓이() {
        return width * height;
    }
    public void 그린다() {
        System.out.println("가로 " + width + " 세로 " + height + " 사각형 (넓이 " + 넓이() + ")");
    }
}

// 클라이언트가 구체 타입을 직접 알고, new + 치수 세팅까지 떠안는다.
public class App {
    public static void main(String[] args) {
        // 원을 그리려면 : 구체 클래스 이름도, 치수 세팅도 클라이언트 몫
        Circle circle = new Circle();
        circle.setRadius(5);   // 생성 절차가 여기 흩어진다
        circle.그린다();

        System.out.println("----------------------------");

        // 사각형으로 바꾸려면 : 클라이언트를 또 고친다. new도 치수 세팅도 똑같이 여기서 반복
        Rectangle rect = new Rectangle();
        rect.setWidth(4);
        rect.setHeight(6);
        rect.그린다();
    }
}
