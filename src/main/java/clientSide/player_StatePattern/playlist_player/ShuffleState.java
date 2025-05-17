package clientSide.player_StatePattern.playlist_player;

import serverSide.entities.*;
import java.util.*;

class ShuffleState implements IState {
    private final PlaylistPlayer playlistPlayer;
    public final String stateName = "shuffle";

    public ShuffleState(PlaylistPlayer spotifyService) {
        this.playlistPlayer = spotifyService;
    }

    @Override
    public Song getNextSong() {
        LinkedList<Integer> songsId = playlistPlayer.playlistServices
                .getPlaylistById(playlistPlayer.getCurrentPlaylistId())
                .getPlaylistSongsListWithId();
        int nextIndex = (int) (Math.random() * songsId.size());
        int nextSongId = songsId.get(nextIndex);

        return playlistPlayer.songService.getSongById(nextSongId);
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
