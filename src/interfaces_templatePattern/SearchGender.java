package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SearchGender extends AbstractMenuPage{
    private String songGenderName;

    public SearchGender(SpotifyPlayer spotifyPlayer){
        super(spotifyPlayer);
    }

    @Override
    void displayPage() {

    }
}
