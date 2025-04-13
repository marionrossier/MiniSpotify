package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsCommunePlaylists extends AbstractMenuPage{
    public FriendsCommunePlaylists(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Commune Playlists");
        System.out.print("0) Exit\n" +
                "Tip the playlist's number to play it\n");
    }
}
