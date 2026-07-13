package ex06.grid.step1;

// Grid를 만들려면 생성자에 값을 몰아넣는다. 인자가 뭐가 뭔지, 순서가 헷갈린다.

class Grid {
    private int columns;
    private int gap;
    private int padding;
    private boolean header;
    private boolean footer;
    private String title;

    Grid(int columns, int gap, int padding, boolean header, boolean footer, String title) {
        this.columns = columns;
        this.gap = gap;
        this.padding = padding;
        this.header = header;
        this.footer = footer;
        this.title = title;
    }

    void 그린다() {
        System.out.println("[Grid] " + title);
        System.out.println("  columns=" + columns + ", gap=" + gap + ", padding=" + padding);
        System.out.println("  header=" + header + ", footer=" + footer);
    }
}

public class App {
    public static void main(String[] args) {
        // 인자가 전부 숫자·불리언이라 무엇이 무엇인지 안 보인다. 순서도 외워야 한다.
        Grid grid = new Grid(3, 10, 20, true, false, "대시보드");
        grid.그린다();

        System.out.println("----------------------------");

        // gap 없이 padding만 주고 싶어도, 생성자가 다 받으니 0이라도 억지로 채워야 한다.
        Grid grid2 = new Grid(2, 0, 16, false, false, "설정");
        grid2.그린다();

        // 인자가 늘수록 new Grid(3,10,20,true,false,...) 는 점점 읽기 어려워진다. (step2)
    }
}
