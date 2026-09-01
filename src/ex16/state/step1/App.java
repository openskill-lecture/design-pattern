package ex16.state.step1;

class Order {
    private String state = "결제대기";

    void 결제한다() {
        if (state.equals("결제대기")) {
            System.out.println("결제 완료");
            state = "결제완료";
        } else if (state.equals("결제완료")) {
            System.out.println("이미 결제했습니다.");
        } else if (state.equals("배송중")) {
            System.out.println("이미 배송중입니다.");
        } else if (state.equals("배송완료")) {
            System.out.println("이미 배송완료되었습니다.");
        }
    }

    void 배송한다() {
        if (state.equals("결제대기")) {
            System.out.println("결제부터 해야 합니다.");
        } else if (state.equals("결제완료")) {
            System.out.println("배송을 시작합니다.");
            state = "배송중";
        } else if (state.equals("배송중")) {
            System.out.println("이미 배송중입니다.");
        } else if (state.equals("배송완료")) {
            System.out.println("이미 배송완료되었습니다.");
        }
    }

    void 배송완료한다() {
        if (state.equals("결제대기")) {
            System.out.println("배송을 시작하지 않았습니다.");
        } else if (state.equals("결제완료")) {
            System.out.println("아직 배송중이 아닙니다.");
        } else if (state.equals("배송중")) {
            System.out.println("배송 완료");
            state = "배송완료";
        } else if (state.equals("배송완료")) {
            System.out.println("이미 배송완료되었습니다.");
        }
    }
}

public class App {
    public static void main(String[] args) {
        Order order = new Order();
        order.결제한다();
        order.배송한다();
        order.배송완료한다();
    }
}
