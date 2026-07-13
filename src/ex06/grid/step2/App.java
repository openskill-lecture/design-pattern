package ex06.grid.step2;

// setter로 하나씩 조립한다. 이름이 보여 읽기는 쉬워졌지만 —
// (1) 필수값을 빼먹어도 안 잡아주고, (2) 다 만든 뒤에도 열려 있어 값이 또 바뀐다.

class Grid {
    private int columns;    // 필수 : 0이면 안 된다
    private int gap;
    private int padding;
    private boolean header;
    private boolean footer;
    private String title;

    void setColumns(int columns) { this.columns = columns; }
    void setGap(int gap)         { this.gap = gap; }
    void setPadding(int padding) { this.padding = padding; }
    void setHeader(boolean header) { this.header = header; }
    void setFooter(boolean footer) { this.footer = footer; }
    void setTitle(String title)  { this.title = title; }

    void 그린다() {
        System.out.println("[Grid] " + title);
        System.out.println("  columns=" + columns + ", gap=" + gap + ", padding=" + padding);
        System.out.println("  header=" + header + ", footer=" + footer);
    }
}

public class App {
    public static void main(String[] args) {
        // 착한 길 : 이름이 보여 읽기 쉽다. 원하는 것만 세팅한다.
        Grid grid = new Grid();
        grid.setColumns(3);
        grid.setGap(10);
        grid.setPadding(20);
        grid.setHeader(true);
        grid.setTitle("대시보드");
        grid.그린다();

        System.out.println("----------------------------");

        // 뚫리는 지점 (1) : columns를 깜빡했다. 아무도 안 잡아준다 → columns=0 인 깨진 Grid.
        Grid broken = new Grid();
        broken.setTitle("설정");
        broken.그린다();   // columns=0 — 열이 0인 그리드가 그대로 그려진다!

        // 뚫리는 지점 (2) : 다 만든 뒤에도 setter가 열려 있어 누군가 값을 또 바꾼다.
        grid.setColumns(1);
        grid.그린다();     // 대시보드가 갑자기 1열로... '완성됐다'고 믿을 수가 없다. (step3)
    }
}
