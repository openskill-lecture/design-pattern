package ex01.oop.p3_tellask.step1;

// 데이터를 묻지 말고 일을 시킨다.
class Wallet {
    int money = 10000;
}

public class App {
    public static void main(String[] args) {
        // 나쁨 : 지갑을 열어서 묻고, 밖에서 계산하고, 다시 넣는다.
        Wallet wallet = new Wallet();
        int price = 3000;

        if (wallet.money >= price) {              // 묻는다
            wallet.money = wallet.money - price;  // 밖에서 계산한다
            System.out.println(price + "원 지불. 잔액 : " + wallet.money);
        }
    }
}
