package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

import java.util.Scanner;

public abstract class AbstractMenuPage {
    int index;
    public SpotifyPlayer spotifyPlayer;
    PageFactory pageFactory;
    Scanner in = new Scanner(System.in);

    public AbstractMenuPage(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        this.pageFactory = pageFactory;
        this.spotifyPlayer = spotifyPlayer;
    }

    final void templateMethode (){
        displayPage();
        switchPage();
    }

    void displayPage(){
        try{
            index = in.nextInt();
            in.nextLine(); // Clear the newline character
        }catch (Exception e){
            System.out.println("Invalid input, try again.");
            in.nextLine(); // Clear the invalid input
            displayPage();
        }
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
    void button1(){
        System.out.println("Choice invalid");
        displayPage();
    };
    void button2(){
        System.out.println("Choice invalid");
        displayPage();
    };
    void button3(){
        System.out.println("Choice invalid");
        displayPage();
    };
    void button4(){
        System.out.println("Choice invalid");
        displayPage();
    };
    void button5(){
        System.out.println("Choice invalid");
        displayPage();
    };
    void button6(){
        System.out.println("Choice invalid");
        displayPage();
    };
    void button7(){
        System.out.println("Choice invalid");
        displayPage();
    };

}
