package ex15.step6;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// STEP 6. 어댑터로 기존 팩스 시스템을 공통 발송 규격에 연결한다.
enum DeliveryChannel {
    EMAIL, FAX
}

interface StatementSender {
    void 발송한다(String receiver, String document);
}

class EmailStatementSender implements StatementSender {
    @Override
    public void 발송한다(String receiver, String document) {
        System.out.println("[이메일] " + receiver + "에게 발송: " + document);
    }
}

// 이미 운영 중인 외부 시스템이라고 가정한다. 이 코드는 수정할 수 없다.
class LegacyFaxClient {
    void transmit(String phoneNumber, String payload) {
        System.out.println("[기존 팩스] " + phoneNumber + "로 전송: " + payload);
    }
}

class FaxStatementSenderAdapter implements StatementSender {
    private final LegacyFaxClient faxClient;

    FaxStatementSenderAdapter(LegacyFaxClient faxClient) {
        this.faxClient = faxClient;
    }

    @Override
    public void 발송한다(String receiver, String document) {
        faxClient.transmit(receiver, document);
    }
}

class SenderFactory {
    private final Map<DeliveryChannel, Supplier<StatementSender>> creators = new HashMap<>();

    void 등록한다(DeliveryChannel channel, Supplier<StatementSender> creator) {
        creators.put(channel, creator);
    }

    StatementSender 생성한다(DeliveryChannel channel) {
        Supplier<StatementSender> creator = creators.get(channel);
        if (creator == null) {
            throw new IllegalArgumentException("지원하지 않는 발송 채널: " + channel);
        }
        return creator.get();
    }
}

public class App {
    public static void main(String[] args) {
        LegacyFaxClient legacyFaxClient = new LegacyFaxClient();

        SenderFactory senderFactory = new SenderFactory();
        senderFactory.등록한다(DeliveryChannel.EMAIL, EmailStatementSender::new);
        senderFactory.등록한다(
                DeliveryChannel.FAX,
                () -> new FaxStatementSenderAdapter(legacyFaxClient)
        );

        StatementSender sender = senderFactory.생성한다(DeliveryChannel.FAX);
        sender.발송한다("051-000-0000", "PDF 거래명세서");
    }
}
