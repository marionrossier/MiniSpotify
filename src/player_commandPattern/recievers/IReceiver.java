package player_commandPattern.recievers;

public interface IReceiver {
    void Play();
    void Pause();
    void Next();
    void Previous();
    void Shuffle();
    void Repeat();
    void Playback();
}
