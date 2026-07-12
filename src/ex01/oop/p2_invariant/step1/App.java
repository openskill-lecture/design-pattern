package ex01.oop.p2_invariant.step1;

// 객체는 자신의 불변식을 스스로 지킨다.
class Account {
    int balance = 10000;
}

public class App {
    public static void main(String[] args) {
        // 나쁨 : 잔액을 아무나 직접 바꾼다. 규칙(잔액 >= 0)을 지킬 방법이 없다.
        Account account = new Account();

        account.balance -= 50000;
        System.out.println("잔액 : " + account.balance); // -40000. 은행이 무너졌다.
    }
}
