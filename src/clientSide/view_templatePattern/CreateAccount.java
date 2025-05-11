package clientSide.view_templatePattern;

import clientSide.services.ViewToolBox;
import serverSide.entities.PlanEnum;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class CreateAccount extends _InversedPageTemplate {

    private String pseudonym;
    private String password;
    private String email;
    private PlanEnum planEnum;

    public CreateAccount(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Create Account Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                icon.nbr(1) + "FREE " + icon.free() + icon.lineBreak +
                icon.nbr(2) + "PREMIUM " + icon.premium();
        viewToolBox.getUserServ().resetCookie();
    }

    public void displaySpecificContent () {
        System.out.print("Enter your pseudonym : ");
        pseudonym = pageService.gotAnInput(scanner.nextLine());
        System.out.print("Enter your password : ");
        password = pageService.gotAnInput(scanner.nextLine());
        System.out.print("Enter your email : ");
        email = pageService.gotAnInput(scanner.nextLine());
        while (!viewToolBox.getUserServ().emailValidation(email)){
            System.err.print("Enter a valid email address, like name@email.com :");
            email = pageService.gotAnInput(scanner.nextLine());
        }
        System.out.println(icon.lineBreak + "Choose your plan : ");
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
        viewToolBox.getUserServ().addUser(pseudonym,email,password, planEnum);
        System.out.println("Account created successfully !");
        pageService.login.displayAllPage();
    }
}
