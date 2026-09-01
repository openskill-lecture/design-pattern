package ex16.cqrs.step1;

import java.util.ArrayList;
import java.util.List;

class MemberService {
    private final List<String> members = new ArrayList<>();

    void save(String name) {
        members.add(name);
        System.out.println("[SAVE] " + name);
    }

    void findAll() {
        System.out.println("[READ] " + members);
    }
}

public class App {
    public static void main(String[] args) {
        MemberService service = new MemberService();
        service.save("홍길동");
        service.findAll();
    }
}
