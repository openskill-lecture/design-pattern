package ex15.step3;

// STEP 3. 전략과 생성자 주입으로 실행 방법을 교체한다.
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

class StatementService {
    private final StatementGenerator generator;
    private final StatementSender sender;

    StatementService(StatementGenerator generator, StatementSender sender) {
        this.generator = generator;
        this.sender = sender;
    }

    void 명세서를발송한다(String receiver, String transactions) {
        String document = generator.생성한다(transactions);
        sender.발송한다(receiver, document);
    }
}

public class App {
    public static void main(String[] args) {
        String transactions = "입금 100,000원 / 출금 30,000원";

        StatementService emailPdfService = new StatementService(
                new PdfStatementGenerator(),
                new EmailStatementSender()
        );
        emailPdfService.명세서를발송한다("customer@bank.com", transactions);

        System.out.println();

        StatementService smsCsvService = new StatementService(
                new CsvStatementGenerator(),
                new SmsStatementSender()
        );
        smsCsvService.명세서를발송한다("010-0000-0000", transactions);
    }
}
