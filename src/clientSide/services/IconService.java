package clientSide.services;

public class IconService {

    public String nbr(int x){
        return (x+"\uFE0F⃣");
    }
    public String playPause(){
        return ("⏯️");
    }
    public String playBack(){
        return ("⏮️");
    }
    public String next(){
        return ("⏩");
    }
    public String previous(){
        return ("⏪");
    }
    public String shuffle(){
        return ("🔀");
    }
    public String sequential(){
        return ("🔁");
    }
    public String repeatOne(){
        return ("🔂");
    }

    public String ok(){
        return ("✅");
    }
    public String warning(){
        return ("⚠️");
    }
    public String lock(){
        return ("🔒");
    }
    public String premium(){
        return ("💎");
    }
    public String free(){
        return ("🆓");
    }
    public String group (){
        return ("👥");
    }
    public String house() {
        return ("\uD83C\uDFE0");
    }
    public String earth(){
        return ("🌍");
    }
    public String search(){
        return ("🔍");
    }

    public String lineBreak = "\n";

    public String eightMusicPlayer = nbr(8) + "Music player";
    public String nineHomepage = nbr(9) + "Home Page";
    public String zeroBack = nbr(0) + "Back ";

    public String backHomePageMusicPlayer = zeroBack + " |  " + nineHomepage +  " |  "  + eightMusicPlayer;

}