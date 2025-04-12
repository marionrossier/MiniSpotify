package view_templatePattern;


import datas.entities.PlanEnum;
import datas.entities.User;
import ressources.Cookies;
import services.login.CreateUser;
import services.login.VerifyUser;
import services.player_commandPattern.SpotifyPlayer;
import services.user.SearchUser;

import static services.login.CreateUser.addUser;

public class Login extends AbstractMenuPage {
    private String pseudonym;
    private String password;
    private String email;
    private PlanEnum planEnum;
    public Cookies cookies;


    public Login(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        cookies = null;
        System.out.println("Login Page");
        System.out.print("0) Exit\n" +
                "1) Sign in\n" +
                "2) Create an account\n");
        super.displayPage();
    }

    void button0() {
        System.out.println("Goodbye");
    }

    @Override
    void button1() {
        System.out.println("Enter your pseudonym:");
        pseudonym = in.nextLine();
        System.out.println("Enter your password:");
        password = in.nextLine();
        //Check to password...
        if(VerifyUser.verifyUser(pseudonym, password)) {
            User user = SearchUser.searchUserByPseudo(pseudonym);
            cookies = new Cookies(user.getUserGuId(), user.getPlanEnum(), user.getPlaylists());
            System.out.println("Login successful");
            pageFactory.homePage.displayPage();
        }else{
            System.out.println("Login failed");
            System.out.println("Please try again");
            displayPage();
        }
    }

    @Override
    void button2() {
        System.out.println("Enter your pseudonym:");
        pseudonym = in.nextLine();
        System.out.println("Enter your password:");
        password = in.nextLine();
        System.out.println("Enter your email:");
        email = in.nextLine();

        //Plan
        while(true) {
            System.out.print("Choose your plan:\n" +
                    "1) FREE\n" +
                    "2) PREMIUM\n");
            ;
            try {
                int plan = in.nextInt();

                if (plan == 1) {
                    planEnum = PlanEnum.FREE;
                    break;
                } else if (plan == 2) {
                    planEnum = PlanEnum.PREMIUM;
                    break;
                } else
                    System.out.println("Invalid plan, choose 1 or 2");
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
            }
        }
        CreateUser.addUser(pseudonym,email,password, planEnum);
        displayPage();
    }
}
