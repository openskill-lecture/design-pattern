package ex16.command.step2;

import java.util.LinkedList;
import java.util.Queue;

interface Command {
    void execute();
}

class FileService {
    void save() {
        System.out.println("파일 저장");
    }
}

class EmailService {
    void send() {
        System.out.println("이메일 전송");
    }
}

class PrintService {
    void print() {
        System.out.println("문서 출력");
    }
}

class SaveCommand implements Command {
    private final FileService fileService;

    SaveCommand(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void execute() {
        fileService.save();
    }
}

class EmailCommand implements Command {
    private final EmailService emailService;

    EmailCommand(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void execute() {
        emailService.send();
    }
}

class PrintCommand implements Command {
    private final PrintService printService;

    PrintCommand(PrintService printService) {
        this.printService = printService;
    }

    @Override
    public void execute() {
        printService.print();
    }
}

public class App {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        EmailService emailService = new EmailService();
        PrintService printService = new PrintService();
        Queue<Command> queue = new LinkedList<>();

        queue.add(new SaveCommand(fileService));
        queue.add(new EmailCommand(emailService));
        queue.add(new PrintCommand(printService));

        while (!queue.isEmpty()) {
            queue.poll().execute();
        }
    }
}
