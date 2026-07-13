package ex06.grid.step3;

// 빌더 패턴 — 생성을 Builder에게 위임한다.
// 체이닝이 하나의 '식'으로 읽히고, build()에서 한 번에 완성·검증한다. 완성된 Grid는 불변.

class Grid {
    private final int columns;
    private final int gap;
    private final int padding;
    private final boolean header;
    private final boolean footer;
    private final String title;

    // 생성자를 private으로 감춘다. 오직 Builder를 통해서만 만든다.
    private Grid(Builder b) {
        this.columns = b.columns;
        this.gap = b.gap;
        this.padding = b.padding;
        this.header = b.header;
        this.footer = b.footer;
        this.title = b.title;
    }

    void 그린다() {
        System.out.println("[Grid] " + title);
        System.out.println("  columns=" + columns + ", gap=" + gap + ", padding=" + padding);
        System.out.println("  header=" + header + ", footer=" + footer);
    }

    // 조립을 맡는 Builder. 각 메서드가 자기 자신(this)을 돌려줘 체이닝(식)이 된다.
    static class Builder {
        private int columns;
        private int gap;
        private int padding;
        private boolean header;
        private boolean footer;
        private String title = "제목없음";

        Builder columns(int v)  { this.columns = v; return this; }
        Builder gap(int v)      { this.gap = v; return this; }
        Builder padding(int v)  { this.padding = v; return this; }
        Builder header()        { this.header = true; return this; }
        Builder footer()        { this.footer = true; return this; }
        Builder title(String v) { this.title = v; return this; }

        Grid build() {
            // 완성 시점에 딱 한 번 검증. 필수값이 없으면 아예 못 만든다.
            if (columns <= 0) throw new IllegalStateException("columns는 1 이상이어야 한다");
            return new Grid(this);   // 여기서만 Grid가 태어난다
        }
    }
}

public class App {
    public static void main(String[] args) {
        // 체이닝 한 줄 = 하나의 '식'. 이름이 다 보이고, 원하는 것만 지정한다.
        Grid grid = new Grid.Builder()
                .columns(3)
                .gap(10)
                .padding(20)
                .header()
                .title("대시보드")
                .build();
        grid.그린다();

        System.out.println("----------------------------");

        // 필수값(columns)을 빼먹으면 build()에서 바로 막는다. 깨진 Grid가 태어날 수 없다.
        try {
            Grid broken = new Grid.Builder().title("설정").build();
            broken.그린다();
        } catch (IllegalStateException e) {
            System.out.println("만들다 막힘 → " + e.getMessage());
        }

        // grid.setColumns(1);  // ← 그런 메서드가 없다. 완성된 Grid는 불변이라 못 바꾼다.
    }
}
