package ex02.solid.p3_srp.step1;

// 주문·제조·계산·영수증이 한 메서드에 뒤엉켜 있다. 책임 구분이 없다.
class Barista {
    void 서빙한다(String drink, int amount) {
        System.out.println("주문 접수 : " + drink);
        System.out.println(drink + " 제조 완료");
        System.out.println(amount + "원 결제 완료");
        System.out.println("[영수증] " + drink + " " + amount + "원");
    }
}

public class App {
    public static void main(String[] args) {
        Barista barista = new Barista();
        barista.서빙한다("아메리카노", 4000);

        // 접수·제조·결제·영수증이 통째로 붙어있다.
        // 무엇 하나 바꾸려면 이 덩어리를 헤집어야 한다. ← SRP 위반
        // [실습] 제조/계산/영수증을 각각 다른 클래스로 쪼개보자 → step2
    }
}
