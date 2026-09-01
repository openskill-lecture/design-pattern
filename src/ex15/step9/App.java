package ex15.step9;

import java.util.ArrayList;
import java.util.List;

// STEP 9. 옵저버로 발송 완료 뒤의 후속 작업을 분리한다.
class StatementSentEvent {
    String userId;
    String receiver;
    String document;

    StatementSentEvent(String userId, String receiver, String document) {
        this.userId = userId;
        this.receiver = receiver;
        this.document = document;
    }
}

interface StatementSentObserver {
    void 발송완료(StatementSentEvent event);
}

class AuditLogObserver implements StatementSentObserver {
    @Override
    public void 발송완료(StatementSentEvent event) {
        System.out.println("[감사] " + event.userId + "의 발송 이력을 기록합니다.");
    }
}

class CustomerNotificationObserver implements StatementSentObserver {
    @Override
    public void 발송완료(StatementSentEvent event) {
        System.out.println("[알림] " + event.receiver + "에게 발송 완료를 안내합니다.");
    }
}

class DeliveryStatisticsObserver implements StatementSentObserver {
    @Override
    public void 발송완료(StatementSentEvent event) {
        System.out.println("[통계] 거래명세서 발송 건수를 1 증가시킵니다.");
    }
}

class StatementEventPublisher {
    private final List<StatementSentObserver> observers = new ArrayList<>();

    void 구독한다(StatementSentObserver observer) {
        observers.add(observer);
    }

    void 발행한다(StatementSentEvent event) {
        for (StatementSentObserver observer : observers) {
            observer.발송완료(event);
        }
    }
}

class StatementService {
    private final StatementEventPublisher publisher;

    StatementService(StatementEventPublisher publisher) {
        this.publisher = publisher;
    }

    void 명세서를발송한다(String userId, String receiver, String document) {
        System.out.println("[발송] " + receiver + "에게 발송: " + document);
        publisher.발행한다(new StatementSentEvent(userId, receiver, document));
    }
}

public class App {
    public static void main(String[] args) {
        StatementEventPublisher publisher = new StatementEventPublisher();
        publisher.구독한다(new AuditLogObserver());
        publisher.구독한다(new CustomerNotificationObserver());
        publisher.구독한다(new DeliveryStatisticsObserver());

        StatementService service = new StatementService(publisher);
        service.명세서를발송한다("staff01", "customer@bank.com", "PDF 거래명세서");
    }
}
