package clientSide.views;

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
        printYourInput();
        validateInput();
        switchPage();
    }

    public final int getPageId (){
        return pageId;
    }

    public final void displayTitle(String pageTitle){
        printLN();
        printLN();
        printLN();
        printLN();
        printLNBgWhite(">>>>>> "+ pageTitle +" <<<<<<<");
        printLN();
    }

    public void displayContent(String pageContent){
        printLNWhite(pageContent);
        printLN();
    }

    public void displaySpecificContent(){}

    public void validateInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                index = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                printInvalidInputTryAgain();
                printYourInput();
            }
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
                printInvalidInputTryAgain();
                handelInvalidIndex();
        }
    }

    public void handelInvalidIndex() {
        printYourInput();
        validateInput();
        switchPage();
    }

    public void button0() {
        pageService.goBack(getPageId());
    }
    public void button1(){
        printInvalidInputTryAgain();
        handelInvalidIndex();
    }
    public void button2(){
        printInvalidInputTryAgain();
        handelInvalidIndex();
    }
    public void button3(){
        printInvalidInputTryAgain();
        handelInvalidIndex();
    }
    public void button4(){
        printInvalidInputTryAgain();
        handelInvalidIndex();
    }
    public void button5(){
        printInvalidInputTryAgain();
        handelInvalidIndex();
    }
    public void button6(){
        printInvalidInputTryAgain();
        handelInvalidIndex();
    }
    public void button7(){
        printInvalidInputTryAgain();
        handelInvalidIndex();
    }
    public void button8(){
        pageService.songPlayer.displayAllPage();
    }
    public void button9(){
        pageService.homePage.displayAllPage();
    }
}
