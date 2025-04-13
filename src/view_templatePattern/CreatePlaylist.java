package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class CreatePlaylist extends AbstractMenuPage{
    private String playlistName;

    public CreatePlaylist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Create Playlist Page");
        System.out.println("Enter the name of the playlist:");
        playlistName = in.nextLine();
        //search a song...

    }

}
