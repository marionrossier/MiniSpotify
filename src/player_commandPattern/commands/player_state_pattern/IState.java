package player_commandPattern.commands.player_state_pattern;

public interface IState {

    void next();
    void previous();
    void playback();
}
