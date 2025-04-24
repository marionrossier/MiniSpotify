package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class PlaylistDisplay extends AbstractMenuPage{

    public PlaylistDisplay(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Playlist Page : ";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Play the playlist" + lineBreak +
                nb2 + "Rename Playlist" + lineBreak +
                nb3 + "Add song" + lineBreak +
                nb4 + "Remove song" + lineBreak +
                nb5 + "Reorder song" + lineBreak +
                nb6 + "Delete the playlist";

    }

    @Override
    void button0() {
        spotifyPageFactory.playlistChoseList.templateMethode();
    }

    @Override
    void button1() {
        spotifyPageFactory.songPlayer.templateMethode();
    }

    @Override
    void button2() {
        //TODO : rename playlist
    }

    @Override
    void button3() {
        //TODO : add song to playlist
    }

    @Override
    void button4() {
        //TODO : remove song from playlist
    }

    @Override
    void button5() {
        //TODO : reorder song in playlist
    }

    @Override
    void button6() {
        spotifyPageFactory.playlistDeletion.templateMethode();
    }
}
