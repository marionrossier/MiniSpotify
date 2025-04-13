package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class EditPlaylist extends AbstractMenuPage {

    public EditPlaylist(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Edit Playlist Page";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Add song" + lineBreak +
                nb2 + "Remove song" + lineBreak +
                nb3 + "Reorder song";
    }

    @Override
    void button0() {
        spotifyPageFactory.onPlaylist.templateMethode();
    }

    @Override
    void button1() {
        spotifyPageFactory.search.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.deletePlaylist.templateMethode();
    }

    @Override
    void button3() {
        //TODO : implement logic to move a song to a certain place
    }

}
