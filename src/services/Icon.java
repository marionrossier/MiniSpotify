package services;

public class Icon {

    public String iconNbr(int x){
        return (x+"\uFE0F⃣");
    }

    public String iconPlay (){
        return ("▶\uFE0F");
    }
    public String iconPlayPause (){
        return ("⏯️");
    }
    public String iconPlayBack (){
        return ("⏮️");
    }
    public String iconPause (){
        return ("⏸️");
    }
    public String iconNext(){
        return ("⏩");
    }
    public String iconPrevious(){
        return ("⏪");
    }
    public String iconShuffle (){
        return ("🔀");
    }
    public String iconSequential (){
        return ("🔁");
    }
    public String iconRepeatOne (){
        return ("🔂");
    }
    public String iconBack (){
        return ("Back ");
    }
    public String iconNew (){
        return ("🆕");
    }

    public String iconUp (){
        return ("🔼");
    }
    public String iconDown (){
        return ("🔽");
    }
    public String iconOk (){
        return ("✅");
    }
    public String iconCross (){
        return ("❌");
    }
    public String iconWarning (){
        return ("⚠️");
    }
    public String iconLock (){
        return ("🔒");
    }
    public String iconPremium (){
        return ("💎");
    }
    public String iconFree (){
        return ("🆓");
    }
    public String iconGroup (){
        return ("👥");
    }
    public String iconHouse () {return ("\uD83C\uDFE0");}
    public String iconEarth (){
        return ("🌍");
    }
    public String iconSearch (){
        return ("🔍");
    }

    public String lineBreak = "\n";

    public String goToMusicPlayer = iconNbr(8) + "Music player";
    public String goToHomepage = iconNbr(9) + "Home Page";
    public String goBack = iconNbr(0) + iconBack();

    public String backHomePageMusicPlayer = goBack + " |  " + goToHomepage +  " |  "  + goToMusicPlayer;

}