package view_templatePattern;

import services.Icon;
import player_StatePattern.playlist_player.IPlaylistPlayer;

import java.util.Scanner;

//TODO : modifier celle-ci en interface et faire ensuite 2 abstracts qui extends cette interface
// dont une pour les menus simples et l'autre pour les menus avec impression d'information
public abstract class AbstractMenuPage {

    //TODO : créer une classe repositoryService qui crée tous les répositories, afin qu'ils soient tous
    // atteignable dans chaque classe
    int index;
    public String pageTitle;
    public String pageContent;
    public IPlaylistPlayer spotifyPlayer;
    SpotifyPageFactory spotifyPageFactory;
    Scanner in = new Scanner(System.in);

    //TODO : remove icons form here and use the Icons Class
    //For more visibility
    public Icon icon = new Icon();
    public String nb0 = icon.iconNbr(0);
    public String nb1 = icon.iconNbr(1);
    public String nb2 = icon.iconNbr(2);
    public String nb3 = icon.iconNbr(3);
    public String nb4 = icon.iconNbr(4);
    public String nb5 = icon.iconNbr(5);
    public String nb6 = icon.iconNbr(6);
    public String nb7 = icon.iconNbr(7);
    public String play = icon.iconPlay();
    public String playPause = icon.iconPlayPause();
    public String playBack = icon.iconPlayBack();
    public String pause = icon.iconPause();
    public String next = icon.iconNext();
    public String previous = icon.iconPrevious();
    public String shuffle = icon.iconShuffle();
    public String sequential = icon.iconSequential();
    public String repeatOne = icon.iconRepeatOne();
    public String logoutLineWith0 = nb0 + icon.iconLogout();
    public String newIcon = icon.iconNew();
    public String up = icon.iconUp();
    public String down = icon.iconDown();
    public String ok = icon.iconOk();
    public String cross = icon.iconCross();
    public String warning = icon.iconWarning();
    public String lock = icon.iconLock();
    public String premium = icon.iconPremium();
    public String free = icon.iconFree();
    public String group = icon.iconGroup();
    public String earth = icon.iconEarth();
    public String lineBreak = "\n";
    public String backLineWith0 = nb0 + icon.iconBack();
    public String search = icon.iconSearch();

    public AbstractMenuPage(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
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
