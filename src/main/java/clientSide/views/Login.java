package clientSide.views;

import clientSide.services.ToolBoxView;
import common.entities.User;
import clientSide.services.Cookies;
import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import static clientSide.services.PrintHelper.*;

public class Login extends TemplateSimplePage {

    public Login(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Login Page";
        this.pageContent =
                icon.nbr0() + "End process" + icon.lineBreak +
                icon.nbr1()+ "Sign in"+icon.lineBreak +
                icon.nbr2()+ "Create an account";
        toolBoxView.getUserServ().resetCookie();
    }

    @Override
    public void button0 (){
        System.exit(0);
    }

    @Override
    public void button1() {
        printLN();
        printLNWhite("For going back, enter \"0\".");
        printWhite("Enter your pseudonym : ");
        String pseudonym = pageService.gotAnInputGoBackIf0(scanner.nextLine());

        printWhite("Enter your password : ");
        String password = pageService.gotAnInputGoBackIf0(scanner.nextLine());

        //Check the password...
        if (toolBoxView.getPasswordServ().passwordCheck(pseudonym, password)){
            User user = toolBoxView.getUserServ().getUserByPseudonym(pseudonym);
            Cookies.initializeInstance(user.getUserId(), user.getPseudonym(), user.getPassword());
            printLNGreen(icon.lineBreak + icon.ok() + "Login successful !");
            toolBoxView.getPlaylistServ().createAllSongPlaylist(user);
            pageService.homePage.displayAllPage();
        }
        else {
            printLNInfo("Login failed ! Please try again.");
            button1();
        }
    }

    @Override
    public void button2() {
        pageService.createAccount.displayAllPage();
    }
}
