package ex13.staticfactory.step1;

// 한 테이블(User)에 강사/학생이 섞여 산다. 생성자 하나로 모든 칸을 받는다.
class User {
    private int id;
    private String username;
    private String subject;   // 담당과목 : 강사만
    private Integer grade;     // 담당학년 : 강사만 (null이 찍히도록 int 아닌 Integer)
    private String course;    // 듣는수업 : 학생만

    // 모든 칸이 열려 있다. 강사인지 학생인지, 어느 칸을 null로 둘지 부르는 쪽이 알아서 맞춰야 한다.
    public User(int id, String username, String subject, Integer grade, String course) {
        this.id = id;
        this.username = username;
        this.subject = subject;
        this.grade = grade;
        this.course = course;
    }

    public String info() {
        return "#" + id + " " + username +
               " | 과목=" + subject + " 학년=" + grade + " 수업=" + course;
    }
}

public class App {
    public static void main(String[] args) {
        // 강사 : 과목/학년만 채우고 수업은 null. 부르는 쪽이 null 자리를 외워 넣어야 한다.
        User teacher = new User(1, "김강사", "자바", 2, null);
        // 학생 : 수업만 채우고 과목/학년은 null.
        User student = new User(2, "이학생", null, null, "자바");
        System.out.println(teacher.info());
        System.out.println(student.info());

        System.out.println("----------------------------");

        // 함정 : 강사칸(과목·학년)과 학생칸(수업)이 동시에 찬, 말이 안 되는 유저도 그대로 태어난다.
        User frankenstein = new User(3, "김섞임", "파이썬", 1, "자바");
        System.out.println(frankenstein.info());  // 과목=파이썬 학년=1 수업=자바. 강사냐 학생이냐?
    }
}
