package ex13.staticfactory.step1;

// 한 테이블(User)에 강사/학생이 섞여 산다. 생성자 하나로 모든 칸을 받는다.
class User {
    private int id;
    private String username;
    private Integer salary;    // 월급 : 강사만 받는다
    private Integer career;    // 경력(년차) : 강사만 (null이 찍히도록 int 아닌 Integer)
    private Integer tuition;   // 등록금 : 학생만 낸다
    private Double gpa;        // 학점 : 학생만 (null이 찍히도록 double 아닌 Double)

    // 모든 칸이 열려 있다. 강사인지 학생인지, 어느 칸을 null로 둘지 부르는 쪽이 알아서 맞춰야 한다.
    public User(int id, String username, Integer salary, Integer career, Integer tuition, Double gpa) {
        this.id = id;
        this.username = username;
        this.salary = salary;
        this.career = career;
        this.tuition = tuition;
        this.gpa = gpa;
    }

    public String info() {
        return "#" + id + " " + username +
               " | 월급=" + salary + " 경력=" + career + " 등록금=" + tuition + " 학점=" + gpa;
    }
}

public class App {
    public static void main(String[] args) {
        // 강사 : 월급/경력만 채우고 등록금·학점은 null. 부르는 쪽이 null 자리를 외워 넣어야 한다.
        User teacher = new User(1, "김강사", 3200000, 5, null, null);
        // 학생 : 등록금/학점만 채우고 월급·경력은 null.
        User student = new User(2, "이학생", null, null, 800000, 4.2);
        System.out.println(teacher.info());
        System.out.println(student.info());

        System.out.println("----------------------------");

        // 함정 : 강사칸(월급·경력)과 학생칸(등록금·학점)이 동시에 찬, 말이 안 되는 유저도 그대로 태어난다.
        User frankenstein = new User(3, "김섞임", 3200000, 5, 800000, 4.2);
        System.out.println(frankenstein.info());  // 월급도 받고 등록금도 낸다. 강사냐 학생이냐?
    }
}
