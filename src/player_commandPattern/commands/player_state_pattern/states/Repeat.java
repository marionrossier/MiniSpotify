package player_commandPattern.commands.player_state_pattern.states;

import player_commandPattern.commands.player_state_pattern.Context;

public class Repeat implements IState{
    private Context context;

    public Repeat(Context context) {
        this.context = context;
    }

    public void replay(){
        /*TODO : replay the same music*/
    }
    @Override
    public void next() {
        /*TODO : replay()*/
    }

    @Override
    public void previous() {
        /*TODO : replay()*/
    }

    @Override
    public void playback() {
        /*TODO : replay()*/
    }
}
