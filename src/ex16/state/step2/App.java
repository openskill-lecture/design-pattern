package ex16.state.step2;

interface OrderState {
    OrderState 결제한다();
    OrderState 배송한다();
    OrderState 배송완료한다();
}

class PaymentWaitingState implements OrderState {
    @Override
    public OrderState 결제한다() {
        System.out.println("결제 완료");
        return new PaidState();
    }

    @Override
    public OrderState 배송한다() {
        System.out.println("결제부터 해야 합니다.");
        return this;
    }

    @Override
    public OrderState 배송완료한다() {
        System.out.println("배송을 시작하지 않았습니다.");
        return this;
    }
}

class PaidState implements OrderState {
    @Override
    public OrderState 결제한다() {
        System.out.println("이미 결제했습니다.");
        return this;
    }

    @Override
    public OrderState 배송한다() {
        System.out.println("배송을 시작합니다.");
        return new ShippingState();
    }

    @Override
    public OrderState 배송완료한다() {
        System.out.println("아직 배송중이 아닙니다.");
        return this;
    }
}

class ShippingState implements OrderState {
    @Override
    public OrderState 결제한다() {
        System.out.println("이미 결제했습니다.");
        return this;
    }

    @Override
    public OrderState 배송한다() {
        System.out.println("이미 배송중입니다.");
        return this;
    }

    @Override
    public OrderState 배송완료한다() {
        System.out.println("배송 완료");
        return new DeliveredState();
    }
}

class DeliveredState implements OrderState {
    @Override
    public OrderState 결제한다() {
        System.out.println("이미 결제했습니다.");
        return this;
    }

    @Override
    public OrderState 배송한다() {
        System.out.println("이미 배송완료되었습니다.");
        return this;
    }

    @Override
    public OrderState 배송완료한다() {
        System.out.println("이미 배송완료되었습니다.");
        return this;
    }
}

class Order {
    private OrderState state;

    Order(OrderState state) {
        this.state = state;
    }

    void 결제한다() {
        state = state.결제한다();
    }

    void 배송한다() {
        state = state.배송한다();
    }

    void 배송완료한다() {
        state = state.배송완료한다();
    }
}

public class App {
    public static void main(String[] args) {
        Order order = new Order(new PaymentWaitingState());
        order.결제한다();
        order.배송한다();
        order.배송완료한다();
    }
}
