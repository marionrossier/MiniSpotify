package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class SearchGender extends AbstractMenuPage{
    private String songGenderName;

    public SearchGender(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }
    @Override
    void displayPage() {
        System.out.println("Search Gender");
        System.out.println("Enter the name of the song gender: ");
        songGenderName = in.nextLine();
        //search a song...
//        0) Exit
//        Songs:
//        1) ...		--> Songplayer
        super.displayPage();
    }
}
