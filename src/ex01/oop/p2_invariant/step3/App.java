package ex01.oop.p2_invariant.step3;

class Account {
    private int balance; // 캡슐화 시킨다.

    public Account(int balance) {
        if (balance < 0) { // 태어날 때부터 규칙을 지킨다
            throw new IllegalArgumentException("잔액은 음수가 될 수 없다");
        }
        this.balance = balance;
    }

    // 출금은 흐름을 조율한다 : 스스로 검사하고, 통과할 때만 뺀다.
    public void 출금(int amount) {
        if (!잔액검사(amount)) {
            System.out.println("잔액 부족! 출금 거부. 잔액 : " + balance);
            return;
        }
        balance -= amount;
        System.out.println(amount + "원 출금. 잔액 : " + balance);
    }

    // 검사는 오직 검사만 (하나의 책임). private 이라 호출자가 우회할 수 없다.
    private boolean 잔액검사(int amount) {
        return amount <= balance;
    }

    public int getBalance() {
        return balance;
    }
}

public class App {
    public static void main(String[] args) {
        Account account = new Account(10000);

        // 좋음 : 호출자는 검사를 신경 쓸 필요가 없다. 출금()이 스스로 지킨다.
        account.출금(3000);  // 7000
        account.출금(50000); // 거부!

        System.out.println("잔액 : " + account.getBalance());
    }
}
