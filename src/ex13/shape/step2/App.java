package ex13.shape.step2;

// simple factory 변형: 생성을 팩토리 한 곳으로 모은다. 클라이언트는 무엇을 만들지만 고르고, 어떻게 만드는지는 모른다.

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

// 어떤 도형을 만들지 고르는 선택지. 종류가 제약적이라 열거형으로 고정한다.
enum ShapeType { CIRCLE, RECTANGLE }

// Simple Factory : new를 대신 해준다.
// 종류가 소수·고정이라 '무엇을 만들까' 결정을 if로 이 한 곳에 모아둔다.
class ShapeFactory {
    public Shape create(ShapeType type) {
        if (type == ShapeType.CIRCLE) {
            Circle shape = new Circle();
            shape.setRadius(5);    // 생성 절차(치수 세팅)도 팩토리가 안다
            return shape;
        } else if (type == ShapeType.RECTANGLE) {
            Rectangle shape = new Rectangle();
            shape.setWidth(4);
            shape.setHeight(6);
            return shape;
        }
        throw new IllegalArgumentException("지원하지 않는 type : " + type);
    }
}

public class App {
    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();

        // 클라이언트는 Circle이라는 구체 타입을 모른다. ShapeType만 넘기고 Shape로 받는다.
        Shape shape = factory.create(ShapeType.CIRCLE);
        shape.그린다();

        System.out.println("----------------------------");

        // 도형을 바꿔도 클라이언트는 인자 하나만 바꾼다. new도 치수 세팅도 팩토리 안에 있다.
        Shape shape2 = factory.create(ShapeType.RECTANGLE);
        shape2.그린다();
    }
}
