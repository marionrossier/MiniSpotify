package view_templatePattern;

import data.jsons.SongRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class SongPlayer extends AbstractMenuPage {

    SongRepository songRepository = new SongRepository();

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
        loop();
    }

    @Override
    void button2() {
        spotifyPlayer.previous();
        loop();
    }

    @Override
    void button3() {
        spotifyPlayer.playOrPause(Cookies_SingletonPattern.getInstance().getCurrentSongId());
        loop();
    }

    @Override
    void button4() {
        spotifyPlayer.playback();
        loop();
    }

    @Override
    void button5() {
        spotifyPlayer.next();
        loop();
    }

    @Override
    void button6() {
        spotifyPlayer.setRepeatMode();
        loop();
    }

    void loop(){
        while (spotifyPlayer.getMusicPlayer().isPlaying() || spotifyPlayer.getMusicPlayer().isPaused()) {
            System.out.println(this.songRepository.getSongById(spotifyPlayer.getRunningSongId()).getSongName());
            displayInput();
            validateInput();
            switchPage();
        }
    }
}
