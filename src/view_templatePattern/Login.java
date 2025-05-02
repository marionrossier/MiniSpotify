package view_templatePattern;

import data.entities.User;
import services.Cookies_SingletonPattern;
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
                icon.iconNbr(0) + "End process" + icon.lineBreak +
                icon.iconNbr(1)+ "Sign in"+icon.lineBreak +
                icon.iconNbr(2)+ "Create an account";
        Cookies_SingletonPattern.resetCookies();
    }

    //TODO : rajouter option pour revenir en arrière sur le bouton  2!
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

        //TODO : mettre cette logique dans passWordService
        //Check the password...
        if(userService.verifyUserAuthentification(pseudonym, password)) {
            User user = userRepository.getUserByPseudonym(pseudonym);
            Cookies_SingletonPattern.setUser(user.getUserId());
            System.out.println(icon.lineBreak + icon.iconOk() + "Login successful !");
            spotifyPageFactory.homePage.templateMethode();
        }else{
            System.out.println(icon.iconWarning() + "Login failed ! Please try again.");
            button1();
        }
    }

    @Override
    void button2() {
        spotifyPageFactory.createAccount.templateMethode();
    }
}
