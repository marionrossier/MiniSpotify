package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class Login extends AbstractMenuPage {
    private String pseudonym;
    private String password;


    public Login(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Login Page");
        System.out.println("Enter your pseudonym:");
        pseudonym = in.nextLine();
        System.out.println("Enter your password:");
        password = in.nextLine();
        //Check to password...
        if(true){
            System.out.println("Login successful");
            pageFactory.homePage.displayPage();
        }else{
            System.out.println("Login failed");
            System.out.println("Please try again");
            displayPage();
        }
    }

    @Override
    void button1() {

    }

    @Override
    void button2() {

    }

    @Override
    void button3() {

    }

    @Override
    void button4() {

    }

    @Override
    void button5() {

    }

    @Override
    void button6() {

    }

    @Override
    void button7() {

    }
}
