package ex07.cook.step2;

// 흐름을 부모로 끌어올렸다. 그런데 이 골격, 정말 고정일까?
// 조리하기()가 final 이 아니고, 스텝들이 public 이라 문이 열려 있다.

class Ramen {
    // 조리의 골격(템플릿). 하지만 final 이 아니라 자식이 갈아엎을 수 있다.
    public void 조리하기() {
        물끓이기();
        면넣기();
        비법();
        그릇담기();
    }

    // 고정이어야 할 스텝들인데 public 이라 자식이 마음대로 바꿀 수 있다.
    public void 물끓이기() { System.out.println("물끓이기"); }
    public void 면넣기()   { System.out.println("면넣기"); }
    public void 비법()     { System.out.println("비법"); }
    public void 그릇담기() { System.out.println("그릇담기"); }
}

// 착한 자식 : 비법()만 바꿔 골격을 그대로 따른다.
class HonganeRamen extends Ramen {
    @Override
    public void 비법() { System.out.println("홍가네 비법소스 넣기"); }
}

// 뚫는 자식 (a) : 조리하기() 자체를 오버라이드해 골격을 갈아엎는다.
class CheatRamen extends Ramen {
    @Override
    public void 조리하기() {        // 골격을 통째로 다시 쓴다!
        면넣기();                   // 물보다 면을 먼저 넣는다. 순서가 깨졌다.
        물끓이기();
        비법();
        그릇담기();
    }
    @Override
    public void 비법() { System.out.println("대충 아무 소스나 넣기"); }
}

// 뚫는 자식 (b) : 열린 public 스텝을 빈 껍데기로 오버라이드한다.
class LazyRamen extends Ramen {
    @Override
    public void 면넣기() { /* 아무것도 안 함 */ }   // 골격은 그대로인데 면이 사라졌다.
    @Override
    public void 비법() { System.out.println("건성 비법 넣기"); }
}

public class App {
    public static void main(String[] args) {
        new HonganeRamen().조리하기();     // 착한 길 : 물끓이기→면넣기→홍가네 비법→그릇담기

        System.out.println("----------------------------");

        new CheatRamen().조리하기();       // 골격이 뚫렸다 : 순서가 엉켜 면이 물보다 먼저 들어간다

        System.out.println("----------------------------");

        new LazyRamen().조리하기();        // 골격은 도는데 면이 소리 없이 사라졌다

        // 템플릿은 만들었지만 final 도, 캡슐화도 없어 자식이 골격을 뚫는다.
        // → 골격은 잠그고(final), 변하는 스텝만 열어야 한다.
    }
}
