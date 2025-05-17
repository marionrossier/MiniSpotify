package clientSide.player_StatePattern.playlist_player;

import serverSide.entities.*;

import java.util.*;

class SequentialState implements IState{
    private final PlaylistPlayer playlistPlayer;
    public final String stateName = "sequential";

    public SequentialState(PlaylistPlayer spotifyService) {
        this.playlistPlayer = spotifyService;
    }

    @Override
    public Song getNextSong() {
        LinkedList<Integer> songsId = playlistPlayer.playlistServices
                .getPlaylistById(playlistPlayer.getCurrentPlaylistId())
                .getPlaylistSongsListWithId();
        int currentIndex = songsId.indexOf(playlistPlayer.currentSong.getSongId());
        int nextIndex = (currentIndex + 1) % songsId.size();
        int nextSongId = songsId.get(nextIndex);
        return playlistPlayer.songService.getSongById(nextSongId);
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
