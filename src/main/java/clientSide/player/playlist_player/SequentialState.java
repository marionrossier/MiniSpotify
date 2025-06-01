package clientSide.player.playlist_player;

import common.entities.Song;

import java.util.LinkedList;

class SequentialState implements IState {
    private final PlaylistPlayer playlistPlayer;
    public final String stateName = "sequential";

    public SequentialState(PlaylistPlayer playlistPlayer) {
        this.playlistPlayer = playlistPlayer;
    }

    @Override
    public Song getNextSong() {
        if (playlistPlayer.currentHistoryIndex + 1 < playlistPlayer.songIdHistory.size()) {
            // Historic already contains next song
            playlistPlayer.currentHistoryIndex++;
            int nextSongId = playlistPlayer.songIdHistory.get(playlistPlayer.currentHistoryIndex);

            return playlistPlayer.songService.getSongById(nextSongId);

        } else {
            // Historic does not contain next song, we need to get it from the playlist
            LinkedList<Integer> songsId = playlistPlayer.playlistServices
                    .getPlaylistById(playlistPlayer.getCurrentPlaylistId())
                    .getPlaylistSongsListWithId();

            int currentIndex = songsId.indexOf(playlistPlayer.currentSong.getSongId());
            int nextIndex = (currentIndex + 1) % songsId.size();
            int nextSongId = songsId.get(nextIndex);

            playlistPlayer.songIdHistory.add(nextSongId);
            playlistPlayer.currentHistoryIndex++;

            return playlistPlayer.songService.getSongById(nextSongId);
        }
    }

    @Override
    public Song getPreviousSong() {
        if (playlistPlayer.currentHistoryIndex > 0) {
            // Historic already contains previous song
            playlistPlayer.currentHistoryIndex--;
            int previousSongId = playlistPlayer.songIdHistory.get(playlistPlayer.currentHistoryIndex);

            return playlistPlayer.songService.getSongById(previousSongId);

        } else {
            // Historic does not contain previous song, we need to get it from the playlist
            LinkedList<Integer> songsId = playlistPlayer.playlistServices
                    .getPlaylistById(playlistPlayer.getCurrentPlaylistId())
                    .getPlaylistSongsListWithId();

            int currentIndex = songsId.indexOf(playlistPlayer.currentSong.getSongId());
            int previousIndex = (currentIndex - 1 + songsId.size()) % songsId.size();
            int previousSongId = songsId.get(previousIndex);

            playlistPlayer.songIdHistory.addFirst(previousSongId);

            return playlistPlayer.songService.getSongById(previousSongId);
        }
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
