package clientSide.views;

import clientSide.services.IconService;
import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

import java.util.Scanner;

import static clientSide.services.PrintHelper.*;

public abstract class TemplateSimplePage implements InterfaceMenu {

    int index;
    public String pageTitle;
    public int pageId;
    public String pageContent;
    public IPlaylistPlayer spotifyPlayer;
    PageService pageService;
    Scanner scanner = new Scanner(System.in);
    boolean isFree = true;
    public ToolBoxView toolBoxView;

    protected IconService icon = new IconService();

    public TemplateSimplePage(PageService pageService, IPlaylistPlayer spotifyPlayer) {
        this.pageService = pageService;
        this.spotifyPlayer = spotifyPlayer;
    }

    public void displayAllPage(){
        pageService.pageIsPremium(isFree);
        pageService.addToStack(getPageId());
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
        printLN();
        printLN();
        printLNBgWhite(">>>>>> "+ pageTitle +" <<<<<<<");
        printLN();
    }

    public void displayContent(String pageContent){
        printLNWhite(pageContent);
    }

    public void displaySpecificContent(){}

    public final void displayInput (){
        printLN();
        printWhite("Your input : ");
    }

    public void validateInput(){
        try{
            index = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
        }catch (Exception e){
            printInfo("Invalid input, try again.");
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
            case 8 :
                button8();
            case 9 :
                button9();
            default:
                printLNInfo("Option non available, try again.");
                displayContent(pageContent);
        }
    }

    private void invalidChoice(){
        printLNInfo(icon.warning() + "Invalid choice, try again." + icon.warning() + icon.lineBreak);
    }

    public void button0() {
        pageService.goBack(getPageId());
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
    public void button8(){
        pageService.songPlayer.displayAllPage();

    }
    public void button9(){
        pageService.homePage.displayAllPage();
    }
}
