package player_commandPattern.commands.player_state_pattern;

import player_commandPattern.commands.player_state_pattern.states.IState;
import player_commandPattern.commands.player_state_pattern.states.Repeat;
import player_commandPattern.commands.player_state_pattern.states.Sequential;
import player_commandPattern.commands.player_state_pattern.states.Shuffle;

public class Context {
    private IState currentState;
    private IState sequential;
    private IState shuffle;
    private IState repeat;

    public Context() {
        this.currentState = sequential;
        this.sequential = new Sequential(this);
        this.shuffle = new Shuffle(this);
        this.repeat = new Repeat(this);
    }

    public void stateInitiation(Context context) {
        context.setCurrentState(context.getSequential());
    }

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public IState getSequential() {
        return sequential;
    }

    public IState getCurrentState() {
        return currentState;
    }

    public IState getShuffle() {
        return shuffle;

    }public IState getRepeat() {
        return repeat;
    }

    public void playback() {
        currentState.playback();
    }

    public void next() {
        currentState.next();
    }
    public void previous(){
        currentState.previous();
    }
}
