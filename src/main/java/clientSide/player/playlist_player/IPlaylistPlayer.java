package clientSide.player.playlist_player;

import common.entities.Song;

public interface IPlaylistPlayer {

    void playOrPause(int songId);

    void play(int playlistId, int songId);

    void playback();

    void next();

    void previous();

    void setShuffleMode();

    void setRepeatMode();
    void setSequentialMode();

    int getCurrentPlaylistId();

    int getCurrentSongId();

    void stop();

    boolean isPlaying();

    boolean isPaused();

    String getCurrentState();

    void setCurrentSong(Song currentSong);
    }
