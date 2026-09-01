package ex16.command.step1;

import java.util.LinkedList;
import java.util.Queue;

class Task {
    String type;

    Task(String type) {
        this.type = type;
    }
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

public class App {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        EmailService emailService = new EmailService();
        PrintService printService = new PrintService();
        Queue<Task> queue = new LinkedList<>();

        queue.add(new Task("SAVE"));
        queue.add(new Task("EMAIL"));
        queue.add(new Task("PRINT"));

        while (!queue.isEmpty()) {
            Task task = queue.poll();

            if (task.type.equals("SAVE")) {
                fileService.save();
            } else if (task.type.equals("EMAIL")) {
                emailService.send();
            } else if (task.type.equals("PRINT")) {
                printService.print();
            }
        }
    }
}
