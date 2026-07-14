package ex08.homework;

public class HistoryStudent implements Student{
    @Override
    public void doHomework() {
        System.out.println("역사 숙제를 합니다");
    }

    @Override
    public boolean isSameHomework(HomeworkType homeworkType) {
        return HomeworkType.HISTORY.equals(homeworkType);
    }
}
