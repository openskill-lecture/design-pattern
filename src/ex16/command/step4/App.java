package ex16.command.step4;

interface Command {
    void execute();
}

class EmailService {
    private int callCount = 0;

    void send() {
        callCount++;

        if (callCount == 1) {
            throw new RuntimeException("일시 오류");
        }

        System.out.println("이메일 전송");
    }
}

class RetryableEmailCommand implements Command {
    private final EmailService emailService;
    private int attemptCount = 0;
    private boolean completed = false;

    RetryableEmailCommand(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void execute() {
        attemptCount++;

        try {
            emailService.send();
            completed = true;
        } catch (RuntimeException e) {
            completed = false;
        }
    }

    boolean canRetry() {
        return !completed && attemptCount < 3;
    }

    boolean isCompleted() {
        return completed;
    }

    int getAttemptCount() {
        return attemptCount;
    }
}

public class App {
    public static void main(String[] args) {
        RetryableEmailCommand command =
                new RetryableEmailCommand(new EmailService());

        do {
            command.execute();
        } while (command.canRetry());

        System.out.println("시도 횟수: " + command.getAttemptCount());
        System.out.println("성공 여부: " + command.isCompleted());
    }
}
