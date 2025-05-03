package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

public class PlaylistHomePageTemplate extends _SimplePageTemplate {

    public PlaylistHomePageTemplate(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Home Page Playlist";
        this.pageContent =
                icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Choose your playlist" + icon.lineBreak +
                icon.iconNbr(2) + "Create a playlist";
    }

    @Override
    public void button1() {
        spotifyPageFactory.playlistChoseList.displayAllPage();
    }

    @Override
    public void button2() {
        spotifyPageFactory.playlistCreation.displayAllPage();
    }

}
