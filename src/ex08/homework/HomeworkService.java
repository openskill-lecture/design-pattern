package ex08.homework;

public class HomeworkService {
    private HomeworkDelegator delegator;

    public HomeworkService(HomeworkDelegator delegator){
        this.delegator = delegator;
    }
    
    public void 시작(HomeworkType type){
        delegator.공부();
        delegator.숙제(type);
        delegator.복습();
    }
} 
