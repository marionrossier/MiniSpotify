package view_templatePattern;

import data.entities.User;
import services.Cookies_SingeltonPattern;
import data.jsons.UserRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.UserService;


public class Login extends AbstractMenuPage {
    public UserRepository userRepository = new UserRepository();
    public UserService userService = new UserService(new UserRepository());

    public Login(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Login Page";
        this.pageContent =
                nb0 + "End process" + lineBreak +
                nb1+ "Sign in"+lineBreak +
                nb2+ "Create an account";
        Cookies_SingeltonPattern.resetCookies();
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

        //Check the password...
        if(userService.verifyUserAuthentification(pseudonym, password)) {
            User user = userRepository.getUserByPseudonym(pseudonym);
            Cookies_SingeltonPattern.setUser(user.getUserId());
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
