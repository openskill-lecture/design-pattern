package ex13.staticfactory.step2;

// 생성자를 감추고, 이름 붙인 정적 팩토리가 각 역할의 칸만 채운다. 어느 칸을 null로 둘지는 팩토리가 안다.
class User {
    private int id;
    private String username;
    private Integer salary;    // 월급 : 강사만 받는다
    private Integer career;    // 경력(년차) : 강사만
    private Integer tuition;   // 등록금 : 학생만 낸다
    private Double gpa;        // 학점 : 학생만

    // 생성자를 private으로 감춘다 → 밖에서 아무 칸이나 채운 User를 못 만든다.
    private User(int id, String username, Integer salary, Integer career, Integer tuition, Double gpa) {
        this.id = id;
        this.username = username;
        this.salary = salary;
        this.career = career;
        this.tuition = tuition;
        this.gpa = gpa;
    }

    // 강사 : 월급/경력만 받는다. 등록금·학점 칸은 팩토리가 null로 채운다.
    static User 강사(int id, String username, int salary, int career) {
        return new User(id, username, salary, career, null, null);
    }

    // 학생 : 등록금/학점만 받는다. 월급·경력 칸은 팩토리가 null로 채운다.
    static User 학생(int id, String username, int tuition, double gpa) {
        return new User(id, username, null, null, tuition, gpa);
    }

    public String info() {
        return "#" + id + " " + username +
               " | 월급=" + salary + " 경력=" + career + " 등록금=" + tuition + " 학점=" + gpa;
    }
}

public class App {
    public static void main(String[] args) {
        // 이름이 곧 역할이다. 어느 칸을 채우고 어느 칸을 null로 둘지 외울 필요가 없다.
        User teacher = User.강사(1, "김강사", 3200000, 5);
        User student = User.학생(2, "이학생", 800000, 4.2);
        System.out.println(teacher.info());   // 등록금=null 학점=null 은 팩토리가 채웠다
        System.out.println(student.info());   // 월급=null 경력=null 은 팩토리가 채웠다

        System.out.println("----------------------------");

        // 강사인데 등록금까지 넣는 짓이 불가능하다. 강사 팩토리엔 등록금·학점 칸 자체가 없다.
        // User.강사(3, "김섞임", 3200000, 5, 800000, 4.2);  // compile error : 인자 개수가 안 맞는다
        System.out.println("섞인 유저는 만들 방법이 없다");
    }
}
