package ex15.step5;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// STEP 5. 팩토리에 구현체 생성을 등록해 선택 분기를 서비스 밖으로 옮긴다.
enum StatementFormat {
    PDF, CSV
}

enum DeliveryChannel {
    EMAIL, SMS
}

interface StatementGenerator {
    String 생성한다(String transactions);
}

class PdfStatementGenerator implements StatementGenerator {
    @Override
    public String 생성한다(String transactions) {
        System.out.println("[PDF] 거래명세서 생성: " + transactions);
        return "PDF 거래명세서";
    }
}

class CsvStatementGenerator implements StatementGenerator {
    @Override
    public String 생성한다(String transactions) {
        System.out.println("[CSV] 거래명세서 생성: " + transactions);
        return "CSV 거래명세서";
    }
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

class SmsStatementSender implements StatementSender {
    @Override
    public void 발송한다(String receiver, String document) {
        System.out.println("[문자] " + receiver + "에게 다운로드 링크 발송: " + document);
    }
}

class GeneratorFactory {
    private final Map<StatementFormat, Supplier<StatementGenerator>> creators = new HashMap<>();

    void 등록한다(StatementFormat format, Supplier<StatementGenerator> creator) {
        creators.put(format, creator);
    }

    StatementGenerator 생성한다(StatementFormat format) {
        Supplier<StatementGenerator> creator = creators.get(format);
        if (creator == null) {
            throw new IllegalArgumentException("지원하지 않는 문서 형식: " + format);
        }
        return creator.get();
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

class StatementService {
    private final GeneratorFactory generatorFactory;
    private final SenderFactory senderFactory;

    StatementService(GeneratorFactory generatorFactory, SenderFactory senderFactory) {
        this.generatorFactory = generatorFactory;
        this.senderFactory = senderFactory;
    }

    void 명세서를발송한다(
            StatementFormat format,
            DeliveryChannel channel,
            String receiver,
            String transactions
    ) {
        StatementGenerator generator = generatorFactory.생성한다(format);
        StatementSender sender = senderFactory.생성한다(channel);

        String document = generator.생성한다(transactions);
        sender.발송한다(receiver, document);
    }
}

public class App {
    public static void main(String[] args) {
        GeneratorFactory generatorFactory = new GeneratorFactory();
        generatorFactory.등록한다(StatementFormat.PDF, PdfStatementGenerator::new);
        generatorFactory.등록한다(StatementFormat.CSV, CsvStatementGenerator::new);

        SenderFactory senderFactory = new SenderFactory();
        senderFactory.등록한다(DeliveryChannel.EMAIL, EmailStatementSender::new);
        senderFactory.등록한다(DeliveryChannel.SMS, SmsStatementSender::new);

        StatementService service = new StatementService(generatorFactory, senderFactory);
        service.명세서를발송한다(
                StatementFormat.PDF,
                DeliveryChannel.EMAIL,
                "customer@bank.com",
                "입금 100,000원 / 출금 30,000원"
        );
    }
}
