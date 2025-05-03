package view_templatePattern;

import services.Icon;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;
import services.Toolbox;

import java.util.Scanner;
import java.util.Stack;

public abstract class _SimplePageTemplate implements _MenuInterface {

    int index;
    public String pageTitle;
    public int pageId;
    public String pageContent;
    public IPlaylistPlayer spotifyPlayer;

    PageService pageService;
    Scanner scanner = new Scanner(System.in);

    protected Icon icon = new Icon();
    protected Toolbox toolbox = new Toolbox();

    public _SimplePageTemplate(PageService pageService, IPlaylistPlayer spotifyPlayer) {
        this.pageService = pageService;
        this.spotifyPlayer = spotifyPlayer;
    }

    public final void goBack() {
        int lastPageId;
        do {
            lastPageId = pageService.getMenuPages().pop();
        } while (lastPageId == getPageId() && !pageService.getMenuPages().isEmpty());

        pageService.getPageById(lastPageId).displayAllPage();
    }

    public void displayAllPage(){
        pageService.getMenuPages().push(getPageId());
        displayTitle(pageTitle);
        displayContent(pageContent);
        displaySpecificContent();
        displayInput();
        validateInput();
        switchPage();
    }

    public final int getPageId (){
        return pageId;
    }

    public final void displayTitle(String pageTitle){
        System.out.println();
        System.out.println(">>>>>> "+ pageTitle +" <<<<<<<");
    }

    public void displayContent(String pageContent){
        System.out.println(pageContent);
    }

    public String getAnInput (String input){
        if (input.equals("0")){
            goBack();
            return "";
        }
        return input;
    }

    public void displaySpecificContent(){}

    public final void displayInput (){
        System.out.print("Your input : ");
    }

    public void validateInput(){
        try{
            index = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
        }catch (Exception e){
            System.out.println("Invalid input, try again.");
            scanner.nextLine(); // Clear the invalid input
            displayInput();
            validateInput();
        }
    }

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
                displayContent(pageContent);
        }
    }

    public final void invalidChoice(){
        System.out.println(icon.iconWarning() + "Invalid choice, try again." + icon.iconWarning() + icon.lineBreak);
    }

    public void button0() {
        goBack();
    }
    public void button1(){
        invalidChoice();
        displayAllPage();
    }
    public void button2(){
        invalidChoice();
        displayAllPage();
    }
    public void button3(){
        invalidChoice();
        displayAllPage();
    }
    public void button4(){
        invalidChoice();
        displayAllPage();
    }
    public void button5(){
        invalidChoice();
        displayAllPage();
    }
    public void button6(){
        invalidChoice();
        displayAllPage();
    }
    public void button7(){
        invalidChoice();
        displayAllPage();
    }
}
