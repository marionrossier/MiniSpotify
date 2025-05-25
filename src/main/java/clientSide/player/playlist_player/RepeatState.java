package clientSide.player.playlist_player;

import common.entities.Song;

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
