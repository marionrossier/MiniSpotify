package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SongPlayer extends AbstractMenuPage {

    public SongPlayer(PageFactory pageFactory) {
        super(pageFactory);
    }
    @Override
    void displayPage() {
        System.out.println("Song Player");
        System.out.print("0) Exit\n" +
                "1) Shuffle\n" +
                "2) Previous\n" +
                "3) Play\n" +
                "4) Next\n" +
                "5) Repeat\n" +
                "6) Play back  >> State pattern\n");
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
