package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class PlaylistHomePage extends AbstractMenuPage {

    public PlaylistHomePage(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Choose your playlist" + icon.lineBreak +
                icon.iconNbr(2) + "Create a playlist";
    }

    @Override
    void button1() {
        spotifyPageFactory.playlistChoseList.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.playlistCreation.templateMethode();
    }

}
