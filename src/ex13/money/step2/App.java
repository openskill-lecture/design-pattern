package ex13.money.step2;

// 정적 팩토리 변형 예제 (User → Money). 생성자를 감추고, 이름 붙인 정적 팩토리로만 만든다. 메서드 이름이 곧 무엇을 만드는지다.
class Money {
    private int amount;
    private String currency;

    // 생성자를 private으로 감춘다 → 밖에서 new Money(1000, "아무거나") 못 한다.
    private Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    // 이름이 의도를 말한다. 매직 스트링도, if 분기도 부르는 쪽에서 사라진다.
    static Money won(int amount) {
        return new Money(amount, "KRW");
    }

    static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}

public class App {
    public static void main(String[] args) {
        // 원화와 달러를 동시에, 이름만 보고 만든다. 문자열을 외울 필요가 없다.
        Money won = Money.won(1000);
        Money dollar = Money.dollar(10);
        System.out.println("won    : " + won.getAmount() + " " + won.getCurrency());
        System.out.println("dollar : " + dollar.getAmount() + " " + dollar.getCurrency());

        System.out.println("----------------------------");

        // 오타는 아예 컴파일이 안 된다. step1의 new Money(1000, "KWR") 같은 버그가 원천 차단.
        // Money.wones(1000);   // compile error : cannot find symbol
        System.out.println("잘못된 돈은 만들 방법이 없다");
    }
}
