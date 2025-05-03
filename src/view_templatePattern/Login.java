package view_templatePattern;

import data.entities.User;
import services.Cookies_SingletonPattern;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;


public class Login extends _SimplePageTemplate {

    public Login(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Login Page";
        this.pageContent =
                icon.iconNbr(0) + "End process" + icon.lineBreak +
                icon.iconNbr(1)+ "Sign in"+icon.lineBreak +
                icon.iconNbr(2)+ "Create an account";
        Cookies_SingletonPattern.resetCookies();
    }

    //TODO : rajouter option pour revenir en arrière sur le bouton  2!

    @Override
    public void button1() {
        System.out.println();
        System.out.print("Enter your pseudonym : ");
        String pseudonym = scanner.nextLine();

        System.out.print("Enter your password : ");
        String password = scanner.nextLine();

        //TODO : mettre cette logique dans passWordService
        //Check the password...
        if(toolbox.getUserServ().verifyUserAuthentification(pseudonym, password)) {
            User user = toolbox.getUserRepo().getUserByPseudonym(pseudonym);
            Cookies_SingletonPattern.setUser(user.getUserId());
            System.out.println(icon.lineBreak + icon.iconOk() + "Login successful !");
            pageService.homePage.displayAllPage();
        }else{
            System.out.println(icon.iconWarning() + "Login failed ! Please try again.");
            button1();
        }
    }

    @Override
    public void button2() {
        pageService.createAccount.displayAllPage();
    }
}
