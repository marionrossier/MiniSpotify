package view_templatePattern;

import data.entities.User;
import services.Cookies;
import data.jsons.UserRepository;
import player_commandPattern.SpotifyPlayer;
import services.UserService;


public class Login extends AbstractMenuPage {
    public Cookies cookies;
    public UserRepository userRepository = new UserRepository();
    public UserService userService = new UserService();

    public Login(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Login Page";
        this.pageContent =
                nb0 + "End process" + lineBreak +
                nb1+ "Sign in"+lineBreak +
                nb2+ "Create an account";
        cookies = null;
    }

    @Override
    void button0() {
        System.out.println("Goodbye");
    }

    @Override
    void button1() {
        System.out.println();
        System.out.print("Enter your pseudonym : ");
        String pseudonym = in.nextLine();

        System.out.print("Enter your password : ");
        String password = in.nextLine();
        //Check to password...

        if(userService.verifyUserAuthentification(pseudonym, password)) {
            User user = userRepository.findUserByPseudonym(pseudonym);
            cookies = new Cookies(user.getUserId(), user.getPlanEnum(), user.getPlaylists());
            System.out.println(lineBreak + ok + "Login successful !");
            spotifyPageFactory.homePage.templateMethode();
        }else{
            System.out.println(warning + "Login failed ! Please try again.");
            button1();
        }
    }

    @Override
    void button2() {
        spotifyPageFactory.createAccount.templateMethode();
    }
}
