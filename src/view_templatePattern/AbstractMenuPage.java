package view_templatePattern;

import services.Icons;
import player_commandPattern.SpotifyPlayer;

import java.util.Scanner;

public abstract class AbstractMenuPage {
    int index;
    public String pageTitle;
    public String pageContent;
    public SpotifyPlayer spotifyPlayer;
    SpotifyPageFactory spotifyPageFactory;
    Scanner in = new Scanner(System.in);

    public Icons icons = new Icons();
    public String nb0 = icons.icon0To9(0);
    public String nb1 = icons.icon0To9(1);
    public String nb2 = icons.icon0To9(2);
    public String nb3 = icons.icon0To9(3);
    public String nb4 = icons.icon0To9(4);
    public String nb5 = icons.icon0To9(5);
    public String nb6 = icons.icon0To9(6);
    public String nb7 = icons.icon0To9(7);
    public String play = icons.iconPlay();
    public String playBack = icons.iconPlayBack();
    public String pause = icons.iconPause();
    public String next = icons.iconNext();
    public String previous = icons.iconPrevious();
    public String shuffle = icons.iconShuffle();
    public String sequential = icons.iconSequential();
    public String repeatOne = icons.iconRepeatOne();
    public String logoutLineWith0 = nb0 + icons.iconLogout();
    public String newIcon = icons.iconNew();
    public String up = icons.iconUp();
    public String down = icons.iconDown();
    public String ok = icons.iconOk();
    public String cross = icons.iconCross();
    public String warning = icons.iconWarning();
    public String lock = icons.iconLock();
    public String premium = icons.iconPremium();
    public String free = icons.iconFree();
    public String group = icons.iconGroup();
    public String earth = icons.iconEarth();
    public String lineBreak = "\n";
    public String backLineWith0 = nb0 + icons.iconBack();
    public String search = icons.iconSearch();

    public AbstractMenuPage(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        this.spotifyPageFactory = spotifyPageFactory;
        this.spotifyPlayer = spotifyPlayer;
    }

    public final void templateMethode(){
        displayTitle(pageTitle);
        displayContent(pageContent);
        displaySpecificContent();
        displayInput();
        validateInput();
        switchPage();
    }

    final void displayTitle(String pageTitle){
        System.out.println();
        System.out.println(">>>>>> "+ pageTitle +" <<<<<<<");
    }

    void displayContent(String pageContent){
        System.out.println(pageContent);
    }

    void displaySpecificContent(){}

    final void displayInput (){
        System.out.print("Your input : ");
    }

    void validateInput(){
        try{
            index = in.nextInt();
            in.nextLine(); // Clear the newline character
        }catch (Exception e){
            System.out.println("Invalid input, try again.");
            in.nextLine(); // Clear the invalid input
            displayInput();
            validateInput();
        }
    }

    void switchPage() {
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
                displayContent(pageContent);
        }
    }

    final void invalidChoice(){
        System.out.println(warning + "Invalid choice, try again." + warning + lineBreak);
    }

    void button0() {
        spotifyPageFactory.homePage.templateMethode();
    }
    void button1(){
        invalidChoice();
        templateMethode();
    }
    void button2(){
        invalidChoice();
        templateMethode();
    }
    void button3(){
        invalidChoice();
        templateMethode();
    }
    void button4(){
        invalidChoice();
        templateMethode();
    }
    void button5(){
        invalidChoice();
        templateMethode();
    }
    void button6(){
        invalidChoice();
        templateMethode();
    }
    void button7(){
        invalidChoice();
        templateMethode();
    }
}
