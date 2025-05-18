package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import common.entities.PlanEnum;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import static clientSide.services.PrintHelper.*;

public class CreateAccount extends TemplateInversePage {

    private String pseudonym;
    private String password;
    private String email;
    private PlanEnum planEnum;

    public CreateAccount(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Create Account Page";
        this.pageContent = icon.zeroBack + icon.lineBreak + icon.separator + icon.lineBreak +
                icon.nbr1() + "FREE " + icon.free() + icon.lineBreak +
                icon.nbr2() + "PREMIUM " + icon.premium();
        toolBoxView.getUserServ().resetCookie();
    }

    public void displaySpecificContent () {
        printLNWhite("For going back, enter \"0\".");
        printWhite("Enter your pseudonym : ");
        pseudonym = pageService.gotAnInput(scanner.nextLine());
        printWhite("Enter your password : ");
        password = pageService.gotAnInput(scanner.nextLine());
        printWhite("Enter your email : ");
        email = pageService.gotAnInput(scanner.nextLine());
        while (!toolBoxView.getUserServ().emailValidation(email)){
            printInfo("Enter a valid email address, like name@email.com :");
            email = pageService.gotAnInput(scanner.nextLine());
        }
        printLNWhite(icon.lineBreak + "Choose your plan : ");
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
        toolBoxView.getUserServ().addUser(pseudonym,email,password, planEnum);
        printLNGreen("Account created successfully !");
        pageService.login.displayAllPage();
    }
}
