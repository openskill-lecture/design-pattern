package ex15.step8;

import java.util.HashMap;
import java.util.Map;

// STEP 8. 캐시 프록시가 실제 원장 조회 횟수를 줄인다.
interface TransactionSource {
    String 거래내역조회(String accountNo, String period);
}

class CoreBankingTransactionSource implements TransactionSource {
    @Override
    public String 거래내역조회(String accountNo, String period) {
        System.out.println("[원장 시스템] " + accountNo + " / " + period + " 거래내역 조회");
        return "입금 100,000원 / 출금 30,000원";
    }
}

class CachedTransactionSourceProxy implements TransactionSource {
    private final TransactionSource target;
    private final Map<String, String> cache = new HashMap<>();

    CachedTransactionSourceProxy(TransactionSource target) {
        this.target = target;
    }

    @Override
    public String 거래내역조회(String accountNo, String period) {
        String key = accountNo + ":" + period;

        if (cache.containsKey(key)) {
            System.out.println("[캐시 HIT] 저장된 거래내역을 반환합니다.");
            return cache.get(key);
        }

        System.out.println("[캐시 MISS] 실제 원장 시스템을 조회합니다.");
        String transactions = target.거래내역조회(accountNo, period);
        cache.put(key, transactions);
        return transactions;
    }
}

public class App {
    public static void main(String[] args) {
        TransactionSource realSource = new CoreBankingTransactionSource();
        TransactionSource source = new CachedTransactionSourceProxy(realSource);

        source.거래내역조회("110-123-456789", "2026-08");
        System.out.println();
        source.거래내역조회("110-123-456789", "2026-08");
    }
}
