package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class EditPlaylist extends AbstractMenuPage {

    public EditPlaylist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Edit Playlist");
        System.out.print("0) Exit\n" +
                "1) Add song\n" +
                "2) Remove song\n" +
                "3) Reorder song\n");
        super.displayPage();
    }

    @Override
    void button1() {
        pageFactory.searchSong.displayPage();
    }

    @Override
    void button2() {
        pageFactory.deletePlaylist.displayPage();
    }

    @Override
    void button3() {
        //implement logic to move a song to a certain place
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
