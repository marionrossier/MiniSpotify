package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class SongPlayer extends AbstractMenuPage {

    public SongPlayer(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Song Player Page";
        this.pageContent = backLineWith0 + lineBreak+
                "Your song player ! " +lineBreak+
                nb1 + ":"+ shuffle + " |" +
                nb2 + ":"+ previous + " |" +
                nb3 + ":"+ playPause +" |" +
                nb4 + ":"+ playBack + " |" +
                nb5 + ":"+ next + " |" +
                nb6 + ":"+ repeatOne + " |";
    }

    @Override
    void button1() {
        spotifyPlayer.setShuffleMode();
    }

    @Override
    void button2() {
        spotifyPlayer.previous();
    }

    @Override
    void button3() {
        spotifyPlayer.resume();
    }

    @Override
    void button4() {
        spotifyPlayer.playback();
    }

    @Override
    void button5() {
        spotifyPlayer.next();
    }

    @Override
    void button6() {
        spotifyPlayer.setRepeatMode();
    }
}
