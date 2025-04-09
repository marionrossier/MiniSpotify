package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SongPlayer extends AbstractMenuPage {

    public SongPlayer(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Song Player");
        System.out.print("0) Exit\n" +
                "1) Shuffle\n" +
                "2) Previous\n" +
                "3) Play\n" +
                "4) Pause\n" +
                "5) Next\n" +
                "6) Repeat\n" +
                "7) Play back  >> State pattern\n");
        super.displayPage();
    }


    @Override
    void button1() {
        spotifyPlayer.selectShuffle();
    }

    @Override
    void button2() {
        spotifyPlayer.selectPrevious();
    }

    @Override
    void button3() {
//        spotifyPlayer.selectPlay(spotifyPlayer.getCurrentSong());
    }

    @Override
    void button4() {
        spotifyPlayer.selectPause();
    }

    @Override
    void button5() {
        spotifyPlayer.selectNext();
    }

    @Override
    void button6() {
        spotifyPlayer.selectRepeat();
    }

    @Override
    void button7() {
        spotifyPlayer.selectPlayback();
    }
}
