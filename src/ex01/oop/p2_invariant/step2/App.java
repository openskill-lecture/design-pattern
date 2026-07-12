package ex01.oop.p2_invariant.step2;

// 검사와 출금이 따로 논다. 검사는 "호출자 책임"이 되어버렸다.
class Account {
    int balance = 10000;

    // 잔액 검사만 하는 메서드 (호출자가 불러줘야 한다)
    boolean 잔액충분(int amount) {
        return amount <= balance;
    }

    // 출금은 검사를 안 한다. 누군가 미리 검사했겠거니 믿는다.
    void 출금(int amount) {
        balance -= amount;
        System.out.println(amount + "원 출금. 잔액 : " + balance);
    }
}

public class App {
    public static void main(String[] args) {
        Account account = new Account();

        // 착한 호출자 : 검사하고 출금한다
        if (account.잔액충분(3000)) {
            account.출금(3000); // 7000
        }

        System.out.println("----------------------------");

        // 깜빡한 호출자 : 검사를 빼먹고 바로 출금한다
        account.출금(50000); // 검사 없이 그대로 빠져나간다 → 음수!

        // 미친 호출자 : 그냥 상태를 바꾼다.
        account.balance = -5000;
    }
}
