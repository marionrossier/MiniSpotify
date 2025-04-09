package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

import javax.sound.midi.Soundbank;

public class SearchGender extends AbstractMenuPage{
    private String songGenderName;

    public SearchGender(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }
    @Override
    void displayPage() {
        System.out.println("Search Gender");
        System.out.println("Enter the name of the song gender: ");
        songGenderName = in.nextLine();
        //search a song...
//        0) Exit
//        Songs:
//        1) ...		--> Songplayer
        super.displayPage();
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
