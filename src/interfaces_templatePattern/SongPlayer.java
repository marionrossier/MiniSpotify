package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SongPlayer extends AbstractMenuPage {

    public SongPlayer(SpotifyPlayer spotifyPlayer) {
        super(spotifyPlayer);
    }

    @Override
    void displayPage() {
        /*TODO*/
    }

    //Seulement fait pour la demo dans le main ! Modifie sans autre...
    @Override
    public void button0() {
        spotifyPlayer.selectPause();
    }
}
