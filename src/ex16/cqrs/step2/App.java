package ex16.cqrs.step2;

import java.util.ArrayList;
import java.util.List;

public class App {
    static List<String> writeDB = new ArrayList<>();
    static List<String> readDB = new ArrayList<>();
    static int state = 0;

    static void save(String name) {
        writeDB.add(name);
        System.out.println("[WRITE] " + name + " 저장");
    }

    static void sync() {
        while (writeDB.size() > state) {
            readDB.add(writeDB.get(state));
            state++;
        }

        System.out.println("[SYNC] 동기화 완료");
    }

    static void findAll() {
        System.out.println("[READ] " + readDB);
    }

    public static void main(String[] args) {
        save("홍길동");
        findAll();
        sync();
        findAll();
    }
}
