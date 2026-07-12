package ex01.oop.p3_tellask.step2;

// 지불한다()로 계산을 지갑에게 시킬까?
class Wallet {
    public int money = 10000;

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

        if (wallet.지불한다(3000)) { // 시킨다 (착한 길)
            System.out.println("3000원 지불 완료. 잔액 : " + wallet.money);
        }

        System.out.println("----------------------------");

        // 하지만 필드가 열려있어 옛날 버릇이 여전히 가능하다
        if (wallet.money >= 5000) {              // 또 묻고
            wallet.money = wallet.money - 5000;  // 또 밖에서 계산한다
            System.out.println("몰래 5000원 꺼냄. 잔액 : " + wallet.money);
        }
    }
}
