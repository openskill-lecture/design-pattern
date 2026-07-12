package ex13.db.step2;

// 생성을 팩토리 한 곳으로 모은다. 클라이언트는 무엇을 만들지만 고르고, 어떻게 만드는지는 모른다.

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

// 어떤 DB를 만들지 고르는 선택지. 종류가 제약적이라 열거형으로 고정한다.
enum Driver { MARIA, ORACLE }

// Simple Factory : new를 대신 해준다.
// 종류가 소수·고정이라 '무엇을 만들까' 결정을 if로 이 한 곳에 모아둔다.
class DBFactory {
    public DB createDB(Driver driver) {
        if (driver == Driver.MARIA) {
            MariaDB db = new MariaDB();
            db.setUrl("jdbc:mariadb://127.0.0.1:3306");   // 생성 절차도 팩토리가 안다
            return db;
        } else if (driver == Driver.ORACLE) {
            OracleDB db = new OracleDB();
            db.setUrl("jdbc:oracle:thin://127.0.0.1:8080");
            return db;
        }
        throw new IllegalArgumentException("지원하지 않는 driver : " + driver);
    }
}

public class App {
    public static void main(String[] args) {
        DBFactory factory = new DBFactory();

        // 클라이언트는 MariaDB라는 구체 타입을 모른다. Driver만 넘기고 DB로 받는다.
        DB db = factory.createDB(Driver.MARIA);
        db.execute("select");

        System.out.println("----------------------------");

        // DB를 바꿔도 클라이언트는 인자 하나만 바꾼다. new도 url도 팩토리 안에 있다.
        DB db2 = factory.createDB(Driver.ORACLE);
        db2.execute("select");
    }
}
