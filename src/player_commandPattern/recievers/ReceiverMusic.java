package player_commandPattern.recievers;

public class ReceiverMusic implements IReceiver {

    @Override
    public void Play() {
        /*TODO*/
    }

    @Override
    public void Pause() {
        /*TODO:Direct implem*/
    }

    @Override
    public void Next() {
        /*TODO:Delegate to Context
        *Context.next*/

    }

    @Override
    public void Previous() {
        /*TODO : Context.previous*/
    }

    @Override
    public void Shuffle() {
        /*
        TODO : Context.setcurrentstate(context.getshuffle())
        if (Context.getCurrentState() == Context.getShuffle)
            set sequential
        else
            set shuffle
        */
    }

    @Override
    public void Repeat() {
        /*TODO : Context.setcurrentstate(context.getRepeat())*/
    }

    @Override
    public void Playback() {
        /*TODO : recommencer la chanson*/
    }
}
