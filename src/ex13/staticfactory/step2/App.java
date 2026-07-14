package ex13.staticfactory.step2;

// 생성자를 감추고, 이름 붙인 정적 팩토리가 각 역할의 칸만 채운다. 어느 칸을 null로 둘지는 팩토리가 안다.
class User {
    private int id;
    private String username;
    private String subject;   // 담당과목 : 강사만
    private Integer grade;     // 담당학년 : 강사만
    private String course;    // 듣는수업 : 학생만

    // 생성자를 private으로 감춘다 → 밖에서 아무 칸이나 채운 User를 못 만든다.
    private User(int id, String username, String subject, Integer grade, String course) {
        this.id = id;
        this.username = username;
        this.subject = subject;
        this.grade = grade;
        this.course = course;
    }

    // 강사 : 과목/학년만 받는다. 수업 칸은 팩토리가 null로 채운다.
    static User 강사(int id, String username, String subject, int grade) {
        return new User(id, username, subject, grade, null);
    }

    // 학생 : 수업만 받는다. 과목/학년 칸은 팩토리가 null로 채운다.
    static User 학생(int id, String username, String course) {
        return new User(id, username, null, null, course);
    }

    public String info() {
        return "#" + id + " " + username +
               " | 과목=" + subject + " 학년=" + grade + " 수업=" + course;
    }
}

public class App {
    public static void main(String[] args) {
        // 이름이 곧 역할이다. 어느 칸을 채우고 어느 칸을 null로 둘지 외울 필요가 없다.
        User teacher = User.강사(1, "김강사", "자바", 2);
        User student = User.학생(2, "이학생", "자바");
        System.out.println(teacher.info());   // 수업=null 은 팩토리가 채웠다
        System.out.println(student.info());   // 과목=null 학년=null 은 팩토리가 채웠다

        System.out.println("----------------------------");

        // 강사인데 수업까지 넣는 짓이 불가능하다. 강사 팩토리엔 수업 칸 자체가 없다.
        // User.강사(3, "김섞임", "파이썬", 1, "자바");  // compile error : 인자 개수가 안 맞는다
        System.out.println("섞인 유저는 만들 방법이 없다");
    }
}
