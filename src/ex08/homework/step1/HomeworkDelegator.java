package ex08.homework.step1;

import java.util.ArrayList;
import java.util.List;

public class HomeworkDelegator {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student){
        students.add(student);
    }

    public void 공부() {
        System.out.println("공부하다");
    }

    public void 복습() {
        System.out.println("복습하다");
    }

    public void 숙제(HomeworkType type) {
        students.stream()
                .filter(student -> student.isSameHomework(type))
                .forEach(student -> student.doHomework());
    }
}
