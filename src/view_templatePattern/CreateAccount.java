package view_templatePattern;

import data.entities.PlanEnum;
import player_commandPattern.SpotifyPlayer;
import services.UserService;

public class CreateAccount extends AbstractMenuPage {

    private String pseudonym;
    private String password;
    private String email;
    private PlanEnum planEnum;
    public UserService userService = new UserService();

    public CreateAccount(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Create Account Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "FREE " + free + lineBreak +
                nb2 + "PREMIUM " + premium ;
        cookies = null;
    }

    @Override
    void displayContent(String pageContent) {
        System.out.print("Enter your pseudonym : ");
        pseudonym = in.nextLine();
        System.out.print("Enter your password : ");
        password = in.nextLine();
        System.out.print("Enter your email : ");
        email = in.nextLine();
        //TODO : check if email is valid
        System.out.println(lineBreak + "Choose your plan : ");

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
        userService.addUser(pseudonym,email,password, planEnum);
        System.out.println("Account created successfully !");
        spotifyPageFactory.login.templateMethode();
    }
}
