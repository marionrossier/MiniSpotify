package view_templatePattern;

import data.entities.PlanEnum;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class CreateAccount extends AbstractMenuPage {

    private String pseudonym;
    private String password;
    private String email;
    private PlanEnum planEnum;

    public CreateAccount(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Create Account Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "FREE " + icon.iconFree() + icon.lineBreak +
                icon.iconNbr(2) + "PREMIUM " + icon.iconPremium() ;
        Cookies_SingletonPattern.resetCookies();
    }

    @Override
    void displayContent(String pageContent) {
        System.out.print("Enter your pseudonym : ");
        pseudonym = in.nextLine();
        System.out.print("Enter your password : ");
        password = in.nextLine();
        System.out.print("Enter your email : ");
        email = in.nextLine();
        //TODO : check if email is valid (dans UserService)
        System.out.println(icon.lineBreak + "Choose your plan : ");

        super.displayContent(pageContent);
    }

    @Override
    void button0() {
        spotifyPageFactory.login.templateMethode();
    }

    @Override
    void button1() {
        planEnum = PlanEnum.FREE;
        createAccount();
    }

    @Override
    void button2() {
        planEnum = PlanEnum.PREMIUM;
        createAccount();
    }

    private void createAccount() {
        toolbox.getUserServ().addUser(pseudonym,email,password, planEnum);
        System.out.println("Account created successfully !");
        spotifyPageFactory.login.templateMethode();
    }
}
