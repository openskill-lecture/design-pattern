package ex08.homework.step3;

public class HomeworkProxy extends HomeworkService{

    public HomeworkProxy(HomeworkDelegator delegator) {
        super(delegator);
    }

    public void 시작(HomeworkType type) {
        System.out.println("학생증을 체크합니다.");
        super.시작(type);
    }
}
 