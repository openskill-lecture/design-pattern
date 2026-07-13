package ex13.money.step1;

// 정적 팩토리 변형 예제 (User → Money). 생성자 하나로 다 받는다. 무엇을 만드는지는 인자(문자열)에 숨는다.
class Money {
    private int amount;
    private String currency;

    // 생성자가 열려 있어 아무 문자열이나 들어온다.
    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}

// 부르는 쪽이 매직 스트링을 외우고, if로 골라서 힘들게 넣는다.
public class App {
    public static void main(String[] args) {
        // 원화와 달러를 동시에 만들고 싶다 → 종류를 if로 분기해 문자열을 넣는다
        Money won = makeMoney("won", 1000);
        Money dollar = makeMoney("dollar", 10);
        System.out.println("won    : " + won.getAmount() + " " + won.getCurrency());
        System.out.println("dollar : " + dollar.getAmount() + " " + dollar.getCurrency());

        System.out.println("----------------------------");

        // 오타 "KWR"도 생성자가 그대로 받아 만들어 버린다. 컴파일도, 실행도 통과 → 버그
        Money wrong = new Money(1000, "KWR");
        System.out.println("wrong  : " + wrong.getAmount() + " " + wrong.getCurrency());  // KWR. 잘못된 돈이 태어났다
    }

    // 종류를 고르려면 이렇게 if로 분기해서 매직 스트링을 대입해야 한다
    static Money makeMoney(String type, int amount) {
        if (type.equals("won")) {
            return new Money(amount, "KRW");
        } else if (type.equals("dollar")) {
            return new Money(amount, "USD");
        } else {
            throw new IllegalArgumentException("알 수 없는 type : " + type);
        }
    }
}
