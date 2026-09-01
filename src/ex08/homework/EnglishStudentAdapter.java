package ex08.homework;

public class EnglishStudentAdapter implements Student{

    private OuterEnglishStudent student;
    
    public EnglishStudentAdapter(OuterEnglishStudent student) {
        this.student = student;
    }

    @Override
    public void doHomework() {
        student.solve();
    }

    @Override
    public boolean isSameHomework(HomeworkType homeworkType) {
        if(HomeworkType.ENGLISH.equals(homeworkType)){
            return student.isSame("ENGLISH");
        }else{
            return false;
        }
    }
    
}
