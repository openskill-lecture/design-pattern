package ex13.staticfactory.step2;

// 생성자를 감추고, 이름 붙인 정적 팩토리로만 만든다. 메서드 이름이 곧 무엇을 만드는지다.
class User {
    private String role;

    // 생성자를 private으로 감춘다 → 밖에서 new User("아무거나") 못 한다.
    private User(String role) {
        this.role = role;
    }

    // 이름이 의도를 말한다. 매직 스트링도, if 분기도 부르는 쪽에서 사라진다.
    static User student() {
        return new User("STUDENT");
    }

    static User staff() {
        return new User("STAFF");
    }

    public String getRole() {
        return role;
    }
}

public class App {
    public static void main(String[] args) {
        // 학생과 직원을 동시에, 이름만 보고 만든다. 문자열을 외울 필요가 없다.
        User student = User.student();
        User staff = User.staff();
        System.out.println("student : " + student.getRole());
        System.out.println("staff   : " + staff.getRole());

        System.out.println("----------------------------");

        // 오타는 아예 컴파일이 안 된다. step1의 new User("STUFF") 같은 버그가 원천 차단.
        // User.stuff();   // compile error : cannot find symbol
        System.out.println("잘못된 유저는 만들 방법이 없다");
    }
}
