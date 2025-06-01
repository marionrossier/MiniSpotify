package clientSide.player.playlist_player;

import common.entities.Song;

import java.util.LinkedList;

class ShuffleState implements IState {
    private final PlaylistPlayer playlistPlayer;
    public final String stateName = "shuffle";

    public ShuffleState(PlaylistPlayer playlistPlayer) {
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

            int randomIndex = (int) (Math.random() * songsId.size());
            int randomSongId = songsId.get(randomIndex);

            playlistPlayer.songIdHistory.add(randomSongId);
            playlistPlayer.currentHistoryIndex++;

            return playlistPlayer.songService.getSongById(randomSongId);
        }
    }

    @Override
    public Song getPreviousSong() {
        if (playlistPlayer.currentHistoryIndex > 0) {
            //Historic already contains previous song
            playlistPlayer.currentHistoryIndex--;
            int previousSongId = playlistPlayer.songIdHistory.get(playlistPlayer.currentHistoryIndex);

            return playlistPlayer.songService.getSongById(previousSongId);

        } else {
            // Historic does not contain previous song, we need to get it from the playlist
            LinkedList<Integer> songsId = playlistPlayer.playlistServices
                    .getPlaylistById(playlistPlayer.getCurrentPlaylistId())
                    .getPlaylistSongsListWithId();

            int randomIndex = (int) (Math.random() * songsId.size());
            int randomSongId = songsId.get(randomIndex);

            playlistPlayer.songIdHistory.addFirst(randomSongId);

            return playlistPlayer.songService.getSongById(randomSongId);
        }
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
