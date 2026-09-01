package ex15.step4;

// STEP 4. 템플릿 메서드로 문서 생성 순서는 고정하고 세부 표현만 바꾼다.
abstract class StatementGenerator {

    public final String 생성한다(String transactions) {
        준비한다();
        머리말을작성한다();
        거래내역을작성한다(transactions);
        꼬리말을작성한다();
        return 완성된문서();
    }

    private void 준비한다() {
        System.out.println("[공통] 거래명세서 생성을 준비합니다.");
    }

    protected abstract void 머리말을작성한다();

    protected abstract void 거래내역을작성한다(String transactions);

    protected abstract void 꼬리말을작성한다();

    protected abstract String 완성된문서();
}

class PdfStatementGenerator extends StatementGenerator {
    @Override
    protected void 머리말을작성한다() {
        System.out.println("[PDF] 은행 로고와 제목을 작성합니다.");
    }

    @Override
    protected void 거래내역을작성한다(String transactions) {
        System.out.println("[PDF] 표로 작성: " + transactions);
    }

    @Override
    protected void 꼬리말을작성한다() {
        System.out.println("[PDF] 안내 문구를 작성합니다.");
    }

    @Override
    protected String 완성된문서() {
        return "PDF 거래명세서";
    }
}

class CsvStatementGenerator extends StatementGenerator {
    @Override
    protected void 머리말을작성한다() {
        System.out.println("[CSV] 열 이름을 작성합니다.");
    }

    @Override
    protected void 거래내역을작성한다(String transactions) {
        System.out.println("[CSV] 행으로 작성: " + transactions);
    }

    @Override
    protected void 꼬리말을작성한다() {
        System.out.println("[CSV] 마지막 줄을 확인합니다.");
    }

    @Override
    protected String 완성된문서() {
        return "CSV 거래명세서";
    }
}

public class App {
    public static void main(String[] args) {
        String transactions = "입금 100,000원 / 출금 30,000원";

        StatementGenerator pdfGenerator = new PdfStatementGenerator();
        System.out.println("완성: " + pdfGenerator.생성한다(transactions));

        System.out.println();

        StatementGenerator csvGenerator = new CsvStatementGenerator();
        System.out.println("완성: " + csvGenerator.생성한다(transactions));
    }
}
