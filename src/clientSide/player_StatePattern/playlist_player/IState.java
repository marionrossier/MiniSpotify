package clientSide.player_StatePattern.playlist_player;

import clientSide.entities.Song;

interface IState {
    Song getNextSong();

    abstract Song getPreviousSong();
}
