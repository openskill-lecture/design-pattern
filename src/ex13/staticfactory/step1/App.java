package ex13.staticfactory.step1;

// 생성자 하나로 다 받는다. 무엇을 만드는지는 인자(문자열)에 숨는다.
class User {
    private String role;

    // 생성자가 열려 있어 아무 문자열이나 들어온다.
    public User(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

// 부르는 쪽이 매직 스트링을 외우고, if로 골라서 힘들게 넣는다.
public class App {
    public static void main(String[] args) {
        // 학생과 직원을 동시에 만들고 싶다 → 종류를 if로 분기해 문자열을 넣는다
        User student = makeUser("student");
        User staff = makeUser("staff");
        System.out.println("student : " + student.getRole());
        System.out.println("staff   : " + staff.getRole());

        System.out.println("----------------------------");

        // 오타 "stuff"도 생성자가 그대로 받아 만들어 버린다. 컴파일도, 실행도 통과 → 버그
        User wrong = new User("STUFF");
        System.out.println("wrong   : " + wrong.getRole());  // STUFF. 잘못된 유저가 태어났다
    }

    // 종류를 고르려면 이렇게 if로 분기해서 매직 스트링을 대입해야 한다
    static User makeUser(String type) {
        if (type.equals("student")) {
            return new User("STUDENT");
        } else if (type.equals("staff")) {
            return new User("STAFF");
        } else {
            throw new IllegalArgumentException("알 수 없는 type : " + type);
        }
    }
}
