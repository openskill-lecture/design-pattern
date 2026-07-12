package ex01.oop.p3_tellask.step3;

class Wallet {
    private int money = 10000; // 캡슐화 시킨다.

    public boolean 지불한다(int price) {
        if (money < price) {
            return false;
        }
        money -= price;
        return true;
    }
}

public class App {
    public static void main(String[] args) {
        Wallet wallet = new Wallet();

        // 좋음 : 묻지 않고 그냥 시킨다. 계산은 지갑 안에서만 일어난다.
        if (wallet.지불한다(3000)) {
            System.out.println("3000원 지불 완료");
        } else {
            System.out.println("돈이 부족해서 지불 실패");
        }

        if (wallet.지불한다(20000)) {
            System.out.println("20000원 지불 완료");
        } else {
            System.out.println("돈이 부족해서 지불 실패");
        }
    }
}
