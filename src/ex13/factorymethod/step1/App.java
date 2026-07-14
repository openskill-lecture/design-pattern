package ex13.factorymethod.step1;

import java.util.List;

// 팩토리 메서드가 없다. 포맷마다 '내보내는 흐름'을 통째로 복사해서 만든다.

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

public class App {

    // PDF로 내보내는 전체 흐름 : 문서 만들고 → 행 채우고 → 저장
    static String exportPdf(List<String> rows) {
        PdfDocument doc = new PdfDocument("sales");
        for (String r : rows) doc.행추가(r);
        return doc.저장한다();
    }

    // Excel로 내보내는 전체 흐름 : 위와 판박이인데 new 하는 제품만 다르다
    static String exportExcel(List<String> rows) {
        ExcelDocument doc = new ExcelDocument("sales");
        for (String r : rows) doc.행추가(r);
        return doc.저장한다();
    }

    public static void main(String[] args) {
        List<String> rows = List.of("1월,커피,4500", "2월,케이크,6000", "3월,라떼,5000");

        System.out.println(exportPdf(rows));

        System.out.println("----------------------------");

        // 포맷이 늘 때마다 '흐름 복사'가 늘어난다. new만 다른 코드가 계속 복제된다.
        System.out.println(exportExcel(rows));

        // CSV를 추가하려면? exportCsv를 위 흐름 통째로 또 복사해야 한다.
    }
}
