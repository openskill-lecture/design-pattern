package ex13.db.step1;

// 생성을 누가 하나? 아직은 클라이언트가 직접 new 한다.

// DB마다 구현이 다르다. 공통 통로는 DB 인터페이스.
interface DB {
    void setUrl(String url);
    int execute(String sql);
}

class MariaDB implements DB {
    private String url;
    public void setUrl(String url) { this.url = url; }
    public int execute(String sql) {
        System.out.println("maria query : " + url + "/" + sql);
        return 1;
    }
}

class OracleDB implements DB {
    private String url;
    public void setUrl(String url) { this.url = url; }
    public int execute(String sql) {
        System.out.println("oracle query : " + url + "/" + sql);
        return 1;
    }
}

// 클라이언트가 구체 타입을 직접 알고, new + url 세팅까지 떠안는다.
public class App {
    public static void main(String[] args) {
        // maria를 쓰려면 : 구체 클래스 이름도, 생성 절차도 클라이언트 몫
        MariaDB db = new MariaDB();
        db.setUrl("jdbc:mariadb://127.0.0.1:3306");  // 생성 절차가 여기 흩어진다
        db.execute("select");

        System.out.println("----------------------------");

        // oracle로 바꾸려면 : 클라이언트를 또 고친다. new도 url도 똑같이 여기서 반복
        OracleDB db2 = new OracleDB();
        db2.setUrl("jdbc:oracle:thin://127.0.0.1:8080");
        db2.execute("select");
    }
}
