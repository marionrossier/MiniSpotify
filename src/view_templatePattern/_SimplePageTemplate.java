package view_templatePattern;

import data.entities.PlanEnum;
import data.entities.User;
import services.Icon;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;
import services.Toolbox;

import java.util.Scanner;

public abstract class _SimplePageTemplate implements _MenuInterface {

    int index;
    public String pageTitle;
    public int pageId;
    public String pageContent;
    public IPlaylistPlayer spotifyPlayer;
    PageService pageService;
    Scanner scanner = new Scanner(System.in);
    boolean isFree = true;

    protected Icon icon = new Icon();
    protected final Toolbox toolbox;

    public _SimplePageTemplate(PageService pageService, IPlaylistPlayer spotifyPlayer) {
        this.pageService = pageService;
        this.spotifyPlayer = spotifyPlayer;
        this.toolbox = new Toolbox();
    }

    public void displayAllPage(){
        pageIsPremium(isFree);
        addToStack();
        displayTitle(pageTitle);
        displayContent(pageContent);
        displaySpecificContent();
        displayInput();
        validateInput();
        switchPage();
    }

    public void pageIsPremium (boolean isFree){
        if (!isFree){
            int userId = toolbox.getUserServ().getCurrentUserId();
            User user = toolbox.getUserServ().getUserById(userId);

            if (user.getPlanEnum().equals(PlanEnum.FREE)) {

                System.out.println("Premium Client option only. \nUpgrade to Premium plan now ? YES or NO");
                String input = scanner.nextLine();
                int lastPageId = pageService.getMenuPages().pop();
                input = input.toLowerCase();

                switch (input) {
                    case "yes":
                        user.setPlanEnum(PlanEnum.PREMIUM);
                        toolbox.getUserServ().saveUser(user);
                        break;
                    case "no":
                        pageService.getPageById(lastPageId).displayAllPage();
                        break;
                    default:
                        System.err.println("Invalid input.");
                        pageService.getPageById(lastPageId).displayAllPage();
                }

            }
        }
    }

    private void addToStack() {
        //TODO : compléter la liste des pages sur lesquelles on devrait pas pouvoir faire retour.
        int actionFoundedSong = pageService.actionFoundedSongs.pageId;
        int playlistCreation = pageService.playlistCreation.pageId;
        int searchGender = pageService.searchGender.pageId;
        int search = pageService.search.pageId;
        int playlistDeletion = pageService.playlistDeletion.pageId;
        int [] pageIdNotToAdd = new int[] {actionFoundedSong,playlistCreation, search, searchGender, playlistDeletion};

        for (int id : pageIdNotToAdd) {
            if (id == getPageId()) {
                return;
            }
        }
        pageService.getMenuPages().push(getPageId());
    }

    public final int getPageId (){
        return pageId;
    }

    public final void displayTitle(String pageTitle){
        System.out.println();
        System.out.println();
        System.out.println(">>>>>> "+ pageTitle +" <<<<<<<");
        System.out.println();
    }

    public void displayContent(String pageContent){
        System.out.println(pageContent);
    }

    public void displaySpecificContent(){}

    public final void displayInput (){
        System.out.println();
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
            case 8 :
                button8();
            case 9 :
                button9();
            default:
                System.out.println("Option non available, try again.");
                displayContent(pageContent);
        }
    }

    private void invalidChoice(){
        System.out.println(icon.iconWarning() + "Invalid choice, try again." + icon.iconWarning() + icon.lineBreak);
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
