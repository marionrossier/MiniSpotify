package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class DeletePlaylist extends AbstractMenuPage {

    public DeletePlaylist(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Delete Playlist Page";
        this.pageContent = backLineWith0 + lineBreak +
                "Are you sure you want to delete this playlist?" + lineBreak +
                nb1 + "YES" + lineBreak +
                nb2 + "NO";
    }

    @Override
    void button0() {
        spotifyPageFactory.onPlaylist.templateMethode();
    }

    @Override
    void button1() {
        System.out.println("Playlist deleted !");
        spotifyPageFactory.onPlaylist.templateMethode();
    }

    @Override
    void button2() {
        System.out.println("Playlist not deleted !");
        spotifyPageFactory.onPlaylist.templateMethode();
    }
}
