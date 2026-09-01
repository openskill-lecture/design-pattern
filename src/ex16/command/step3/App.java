package ex16.command.step3;

import java.util.LinkedList;
import java.util.Queue;

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
        Queue<Runnable> queue = new LinkedList<>();

        queue.add(fileService::save);
        queue.add(emailService::send);
        queue.add(printService::print);

        while (!queue.isEmpty()) {
            queue.poll().run();
        }
    }
}
