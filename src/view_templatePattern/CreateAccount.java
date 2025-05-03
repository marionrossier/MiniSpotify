package view_templatePattern;

import data.entities.PlanEnum;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class CreateAccount extends _SimplePageTemplate {

    private String pseudonym;
    private String password;
    private String email;
    private PlanEnum planEnum;

    public CreateAccount(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Create Account Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "FREE " + icon.iconFree() + icon.lineBreak +
                icon.iconNbr(2) + "PREMIUM " + icon.iconPremium() ;
        Cookies_SingletonPattern.resetCookies();
    }

    @Override
    public void displayContent(String pageContent) {
        System.out.print("Enter your pseudonym : ");
        pseudonym = scanner.nextLine();
        System.out.print("Enter your password : ");
        password = scanner.nextLine();
        System.out.print("Enter your email : ");
        email = scanner.nextLine();
        //TODO : check if email is valid (dans UserService)
        System.out.println(icon.lineBreak + "Choose your plan : ");

        super.displayContent(pageContent);
    }

    @Override
    public void button1() {
        planEnum = PlanEnum.FREE;
        createAccount();
    }

    @Override
    public void button2() {
        planEnum = PlanEnum.PREMIUM;
        createAccount();
    }

    private void createAccount() {
        toolbox.getUserServ().addUser(pseudonym,email,password, planEnum);
        System.out.println("Account created successfully !");
        spotifyPageFactory.login.displayAllPage();
    }
}
