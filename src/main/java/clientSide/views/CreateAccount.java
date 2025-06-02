package clientSide.views;

import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import common.entities.PlanEnum;
import clientSide.player.playlist_player.IPlaylistPlayer;
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
        this.pageContent = PrintHelper.zeroBack + PrintHelper.lineBreak + PrintHelper.separator + PrintHelper.lineBreak +
                PrintHelper.nbr1 + "FREE " + PrintHelper.free + PrintHelper.lineBreak +
                PrintHelper.nbr2 + "PREMIUM " + PrintHelper.premium;
        toolBoxView.getUserServ().resetCookie();
    }

    public void displaySpecificContent () {
        printLNWhite("For going back, enter \"0\".");
        printWhite("Enter your pseudonym : ");
        pseudonym = pageService.gotAnInputGoBackIf0(scanner.nextLine());
        printWhite("Enter your password : ");
        password = pageService.gotAnInputGoBackIf0(scanner.nextLine());
        printWhite("Enter your email : ");
        email = pageService.gotAnInputGoBackIf0(scanner.nextLine());
        while (!toolBoxView.getUserServ().emailValidation(email)){
            printInfo("Enter a valid email address, like name@email.com :");
            email = pageService.gotAnInputGoBackIf0(scanner.nextLine());
        }
        printLNWhite(PrintHelper.lineBreak + "Choose your plan : ");
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
        pageService.login.displayAllPage();
    }
}
