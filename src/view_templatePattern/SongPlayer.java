package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class SongPlayer extends AbstractMenuPage {

    public SongPlayer(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Song Player Page";
        this.pageContent = backLineWith0 + lineBreak+
                "Your song player ! " +lineBreak+
                nb1 + "|"+ nb2 + "|"+ nb3 + "|"+ nb4 + "|"+ nb5 + "|"+ nb6 + "|"+ nb7 + "|"+ lineBreak +
                shuffle+ "|" + previous + "|" + play + "|" + pause + "|" + next + "|" + repeatOne + "|" + playBack + lineBreak;
        //TODO : changer pour , shuffle, previous, playback, play/pause, next, repeat
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
        spotifyPlayer.selectPlay(1);
        //TODO : a changer une fois l'interface SearchSong terminée
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
