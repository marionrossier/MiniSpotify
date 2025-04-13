package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class SearchArtist extends AbstractMenuPage {
    private String artistName;

    public SearchArtist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }
    @Override
    void displayPage() {
        System.out.println("Search Artist Page");
        System.out.println("Enter the name of the artist:");
        artistName = in.nextLine();
        //search a song...
//        0) Exit
//        Songs:
//        1) ... --> Songplayer
        super.displayPage();
    }
}
