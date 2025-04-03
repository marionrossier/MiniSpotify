package interfaces_templatePattern;

abstract class AbstractMenuPage {
    private int index;

    final void templateMethode (){
        displayPage();
        switchPage();
    }

    abstract void displayPage();

    public final void switchPage() {
        switch (index){
            case 0:
                button0();
                break;
            case 1:
                button1();
                break;
            case 2:
                button2();
                break;
            case 3 :
                button3();
                break;
            case 4:
                button4();
                break;
            case 5:
                button5();
                break;
            case 6:
                button6();
                break;
            case 7:
                button7();
                break;
            default:
                System.out.println("Option non available, try again.");
                displayPage();
        }
    }

    public void button0() {/*TODO*/}
    public void button1() {/*TODO*/}
    public void button2() {/*TODO*/}
    public void button3() {/*TODO*/}
    public void button4() {/*TODO*/}
    public void button5() {/*TODO*/}
    public void button6() {/*TODO*/}
    public void button7() {/*TODO*/}
}
