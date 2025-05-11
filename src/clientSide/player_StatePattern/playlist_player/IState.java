package clientSide.player_StatePattern.playlist_player;

import serverSide.entities.Song;

interface IState {
    Song getNextSong();

    abstract Song getPreviousSong();
}
