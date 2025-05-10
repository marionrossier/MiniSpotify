package view_templatePattern;

import data.entities.User;
import services.Cookies_SingletonPattern;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

public class LoginOK extends _SimplePageTemplate {

    public LoginOK(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Login Page";
        this.pageContent =
                icon.iconNbr(0) + "End process" + icon.lineBreak +
                icon.iconNbr(1)+ "Sign in"+icon.lineBreak +
                icon.iconNbr(2)+ "Create an account";
        toolbox.getUserServ().resetCookie();
    }

    @Override
    public void button0 (){
        System.exit(0);
    }

    @Override
    public void button1() {
        System.out.println();
        System.out.println("For going back, enter \"0\".");
        System.out.print("Enter your pseudonym : ");
        String pseudonym = pageService.gotAnInput(scanner.nextLine());

        System.out.print("Enter your password : ");
        String password = pageService.gotAnInput(scanner.nextLine());

        //Check the password...
        if (toolbox.getPasswordServ().passwordCheck(pseudonym, password)){
            User user = toolbox.getUserServ().getUserByPseudonym(pseudonym);
            Cookies_SingletonPattern.setUser(user.getUserId());
            System.out.println(icon.lineBreak + icon.iconOk() + "Login successful !");
            toolbox.getPlaylistServ().createAllSongPlaylist(user);
            pageService.homePage.displayAllPage();
        }
        else {
            System.out.println(icon.iconWarning() + "Login failed ! Please try again.");
            button1();
        }
    }

    @Override
    public void button2() {
        pageService.createAccount.displayAllPage();
    }
}
