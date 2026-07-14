package ex13.factorymethod.step2;

import java.util.List;

// 팩토리 메서드 : 부모는 '내보내는 흐름'만 쥐고, '무엇을 new할지'는 자식에게 뚫어 맡긴다.

// 제품 : 내보낼 문서. 포맷마다 크기 계산이 다르다(값 흐름).
interface Document {
    void 행추가(String row);
    String 저장한다();   // 파일명 | N행 | bytes
}

class PdfDocument implements Document {
    private String name;
    private int rowCount = 0;
    private int bytes = 200;   // PDF는 헤더가 무겁다
    PdfDocument(String name) { this.name = name; }
    public void 행추가(String row) { rowCount++; bytes += 40; }   // 행마다 40 bytes
    public String 저장한다() { return name + ".pdf | " + rowCount + "행 | " + bytes + " bytes"; }
}

class ExcelDocument implements Document {
    private String name;
    private int rowCount = 0;
    private int bytes = 120;   // 엑셀은 그보다 가볍다
    ExcelDocument(String name) { this.name = name; }
    public void 행추가(String row) { rowCount++; bytes += 30; }
    public String 저장한다() { return name + ".xlsx | " + rowCount + "행 | " + bytes + " bytes"; }
}

// 부모 : 내보내는 고정 흐름은 여기 딱 한 번. 문서생성()만 구멍으로 비워둔다.
abstract class ReportExporter {
    protected abstract Document 문서생성();          // ← 변하는 칸 (자식이 new해서 채움)

    public String 내보낸다(List<String> rows) {       // 고정 흐름 : 생성 → 채움 → 저장
        Document doc = 문서생성();
        for (String r : rows) doc.행추가(r);          // 부모가 제품을 실제로 굴린다
        return doc.저장한다();
    }
}

class PdfExporter extends ReportExporter {
    protected Document 문서생성() { return new PdfDocument("sales"); }
}

class ExcelExporter extends ReportExporter {
    protected Document 문서생성() { return new ExcelDocument("sales"); }
}

// 새 포맷 CSV 추가 : 부모 ReportExporter는 열어보지도 않았다. 클래스 둘만 늘렸다.
class CsvDocument implements Document {
    private String name;
    private int rowCount = 0;
    private int bytes = 0;     // CSV는 헤더가 없어 가장 가볍다
    CsvDocument(String name) { this.name = name; }
    public void 행추가(String row) { rowCount++; bytes += 12; }
    public String 저장한다() { return name + ".csv | " + rowCount + "행 | " + bytes + " bytes"; }
}

class CsvExporter extends ReportExporter {
    protected Document 문서생성() { return new CsvDocument("sales"); }
}

public class App {
    public static void main(String[] args) {
        List<String> rows = List.of("1월,커피,4500", "2월,케이크,6000", "3월,라떼,5000");

        // 부모 흐름은 그대로 두고, 어느 자식을 세우냐로 제품이 결정된다.
        ReportExporter pdf = new PdfExporter();
        System.out.println(pdf.내보낸다(rows));

        System.out.println("----------------------------");

        ReportExporter excel = new ExcelExporter();
        System.out.println(excel.내보낸다(rows));

        // CSV를 새로 붙였지만 부모(ReportExporter)도, 내보내는 흐름도 0줄 수정. 자식만 추가했다.
        ReportExporter csv = new CsvExporter();
        System.out.println(csv.내보낸다(rows));
    }
}
