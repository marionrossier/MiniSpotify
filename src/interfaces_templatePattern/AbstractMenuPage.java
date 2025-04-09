package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

import java.util.Scanner;

public abstract class AbstractMenuPage {
    int index;
    public SpotifyPlayer spotifyPlayer;
    PageFactory pageFactory;
    Scanner in = new Scanner(System.in);

//    public AbstractMenuPage(PageFactory pageFactory) {
//        this.pageFactory = pageFactory;
//    }

    public AbstractMenuPage(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        this.pageFactory = pageFactory;
        this.spotifyPlayer = spotifyPlayer;
    }

    final void templateMethode (){
        displayPage();
        switchPage();
    }

    void displayPage(){
        index = in.nextInt();
        switchPage();
    };

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

    void button0() {
        pageFactory.homePage.displayPage();
    }
    abstract void button1();
    abstract void button2();
    abstract void button3();
    abstract void button4();
    abstract void button5();
    abstract void button6();
    abstract void button7();

}
