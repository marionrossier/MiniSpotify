package clientSide.player.playlist_player;

import common.entities.Song;

class RepeatState implements IState {
    private final PlaylistPlayer playlistPlayer;
    public final String stateName = "repeat";

    public RepeatState(PlaylistPlayer playlistPlayer) {
        this.playlistPlayer = playlistPlayer;
    }

    @Override
    public Song getNextSong() {
        return playlistPlayer.currentSong;
    }

    @Override
    public Song getPreviousSong() {
        return playlistPlayer.currentSong;
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
