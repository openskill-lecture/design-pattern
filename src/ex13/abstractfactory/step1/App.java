package ex13.abstractfactory.step1;

// 추상 팩토리: 제품 하나가 아니라 '서로 어울리는 제품 한 가족'을 통째로 만든다.
// shape 예제(simple factory)는 도형 하나를 만들지만, 여기선 버튼+체크박스를 한 세트로 함께 만든다.

// 제품군 1 : 버튼
interface Button {
    void 그린다();
}
 
// 제품군 2 : 체크박스
interface CheckBox {
    void 그린다();
}

// --- Windows 가족 (각진 회색) ---
class WindowsButton implements Button {
    public void 그린다() {
        System.out.println("Windows Button  : 각진 회색 버튼");
    }
}

class WindowsCheckBox implements CheckBox {
    public void 그린다() {
        System.out.println("Windows CheckBox: 각진 회색 체크박스");
    }
}

// --- Mac 가족 (둥근 파랑) ---
class MacButton implements Button {
    public void 그린다() {
        System.out.println("Mac Button  : 둥근 파랑 버튼");
    }
}

class MacCheckBox implements CheckBox {
    public void 그린다() {
        System.out.println("Mac CheckBox: 둥근 파랑 체크박스");
    }
}

// 추상 팩토리 : 한 가족을 이루는 제품들의 생성 통로. '무엇을 만들지'만 약속한다.
interface UIFactory {
    Button createButton();
    CheckBox createCheckBox();
}

// 각 팩토리는 같은 테마의 제품만 만든다 → 버튼과 체크박스 가족이 절대 섞이지 않는다.
class WindowsFactory implements UIFactory {
    public Button createButton() {
        return new WindowsButton();
    }

    public CheckBox createCheckBox() {
        return new WindowsCheckBox();
    }
}

class MacFactory implements UIFactory {
    public Button createButton() {
        return new MacButton();
    }

    public CheckBox createCheckBox() {
        return new MacCheckBox();
    }
}

public class App {
    public static void main(String[] args) {
        // 팩토리 한 줄이 화면 전체의 가족을 결정한다. 버튼도 체크박스도 통째로 Windows 가족.
        UIFactory factory = new WindowsFactory();
        Button wBtn = factory.createButton();
        CheckBox wBox = factory.createCheckBox();
        wBtn.그린다();
        wBox.그린다();

        System.out.println("----------------------------");

        // 팩토리 한 줄만 Mac으로 바꾸면 버튼·체크박스가 통째로 교체된다. 아래 renderDialog는 그대로.
        factory = new MacFactory();
        Button mBtn = factory.createButton();
        CheckBox mBox = factory.createCheckBox();
        mBtn.그린다();
        mBox.그린다();
    }

}
