package clientSide.player_StatePattern.playlist_player;

import serverSide.entities.*;

class RepeatState implements IState{
    private final PlaylistPlayer context;
    public final String stateName = "repeat";

    public RepeatState(PlaylistPlayer spotifyService) {
        this.context = spotifyService;
    }

    @Override
    public Song getNextSong() {
        return context.currentSong;
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
