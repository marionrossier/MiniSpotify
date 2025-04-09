package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class DeletePlaylist extends AbstractMenuPage {

    public DeletePlaylist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }
    @Override
    void displayPage() {
        System.out.println("Delete Playlist");
        System.out.print("0) Exit\n" +
                "1) Are you sure?\n");
        super.displayPage();
    }

    @Override
    void button0() {
        pageFactory.onPlaylist.displayPage();
    }

    @Override
    void button1() {
        System.out.println("Are you sure you want to delete this playlist? (y/n)");
        String input = in.nextLine();
        if (input.equals("y")) {
            System.out.println("Playlist deleted");
            pageFactory.onPlaylist.displayPage();
        } else if (input.equals("n")) {
            System.out.println("Playlist not deleted");
            pageFactory.onPlaylist.displayPage();
        } else {
            System.out.println("Invalid input");
            displayPage();
        }

    }

    @Override
    void button2() {

    }

    @Override
    void button3() {

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
