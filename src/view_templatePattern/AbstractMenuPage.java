package view_templatePattern;

import services.Icon;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Toolbox;

import java.util.Scanner;

//TODO : modifier celle-ci en interface et faire ensuite 2 abstracts qui extends cette interface
// dont une pour les menus simples et l'autre pour les menus avec impression d'information
public abstract class AbstractMenuPage {


    //TODO : revoir logique des views et faire en sorte qu'on puisse revenir en arrière
    int index;
    public String pageTitle;
    public String pageContent;
    public IPlaylistPlayer spotifyPlayer;

    SpotifyPageFactory spotifyPageFactory;
    Scanner in = new Scanner(System.in);
    protected Icon icon = new Icon();

    protected Toolbox toolbox = new Toolbox();

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
        System.out.println(icon.iconWarning() + "Invalid choice, try again." + icon.iconWarning() + icon.lineBreak);
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
