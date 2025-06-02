package clientSide.services;

public class MenuService {

    public String nbr0() { return "0."; }
    public String nbr1() { return "1."; }
    public String nbr2() { return "2."; }
    public String nbr3() { return "3."; }
    public String nbr4() { return "4."; }
    public String nbr5() { return "5."; }
    public String nbr6() { return "6."; }
    public String nbr7() { return "7."; }
    public String nbr8() { return "8."; }
    public String nbr9() { return "9."; }

    public String playPause()   { return "[PLAY/PAUSE]"; }
    public String playBack()    { return "[|<<]"; }
    public String next()        { return "[>>]"; }
    public String previous()    { return "[<<]"; }
    public String shuffle()     { return "[SHUFFLE]"; }
    public String sequential()  { return "[LOOP]"; }
    public String repeatOne()   { return "[REPEAT]"; }

    public String ok()          { return "[OK]"; }
    public String warning()     { return "[!!!]"; }
    public String lock()        { return "[Owned]"; }
    public String premium()     { return "[PREMIUM]"; }
    public String free()        { return "[FREE]"; }
    public String group()       { return "[FRIENDS]"; }
    public String house()       { return "[SAVED]"; }
    public String earth()       { return "[OPEN]"; }
    public String search()      { return "[SEARCH]"; }

    public String lineBreak = "\n";

    public String eightMusicPlayer = nbr8() + "[PLAYER]";
    public String nineHomepage     = nbr9() + "[HOME]";
    public String zeroBack         = nbr0() + "[BACK]";

    public String separator = "--------------------------------------------";
    public String backHomePageMusicPlayer = zeroBack + " | " + nineHomepage + " | " + eightMusicPlayer;
}