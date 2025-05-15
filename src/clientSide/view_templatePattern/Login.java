package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import serverSide.entities.User;
import clientSide.services.Cookies_SingletonPattern;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class Login extends _SimplePageTemplate {

    public Login(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Login Page";
        this.pageContent =
                icon.nbr(0) + "End process" + icon.lineBreak +
                icon.nbr(1)+ "Sign in"+icon.lineBreak +
                icon.nbr(2)+ "Create an account";
        toolBoxView.getUserServ().resetCookie();
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
        if (toolBoxView.getPasswordServ().passwordCheck(pseudonym, password)){
            User user = toolBoxView.getUserServ().getUserByPseudonymLogin(pseudonym);
            Cookies_SingletonPattern.setUser(user.getUserId(), user.getPseudonym(), user.getPassword());
            System.out.println(icon.lineBreak + icon.ok() + "Login successful !");
            toolBoxView.getPlaylistServ().createAllSongPlaylist(user);
            pageService.homePage.displayAllPage();
        }
        else {
            System.out.println(icon.warning() + "Login failed ! Please try again.");
            button1();
        }
    }

    @Override
    public void button2() {
        pageService.createAccount.displayAllPage();
    }
}
