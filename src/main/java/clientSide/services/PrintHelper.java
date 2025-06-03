package clientSide.services;

public class PrintHelper {

    public static final String nbr0 = "0.";
    public static final String nbr1 = "1.";
    public static final String nbr2 = "2.";
    public static final String nbr3 = "3.";
    public static final String nbr4 = "4.";
    public static final String nbr5 = "5.";
    public static final String nbr6 = "6.";
    public static final String nbr7 = "7.";
    public static final String nbr8 = "8.";
    public static final String nbr9 = "9.";

    public static final String playPause = "[PLAY/PAUSE]";
    public static final String playBack = "[|<<]";
    public static final String next = "[>>]";
    public static final String previous = "[<<]";
    public static final String shuffle = "[SHUFFLE]";
    public static final String sequential = "[LOOP]";
    public static final String repeatOne = "[REPEAT]";

    public static final String lock = "[Owned]";
    public static final String premium = "[PREMIUM]";
    public static final String free = "[FREE]";
    public static final String group = "[FRIENDS]";
    public static final String house = "[SAVED]";
    public static final String earth = "[OPEN]";
    public static final String search = "[SEARCH]";

    public static final String lineBreak = "\n";

    public static final String eightMusicPlayer = nbr8 + "[PLAYER]";
    public static final String nineHomepage     = nbr9 + "[HOME]";
    public static final String zeroBack         = nbr0 + "[BACK]";

    public static final String separator = "--------------------------------------------";
    public static final String backHomePageMusicPlayer = zeroBack + " | " + eightMusicPlayer + " | " + nineHomepage;




    static final String PRINT_RESET = "\u001B[0m";

    static final String PRINT_BLACK = "\u001B[30m";
    static final String PRINT_RED = "\u001B[31m";
    static final String PRINT_GREEN = "\u001B[32m";
    static final String PRINT_YELLOW = "\u001B[33m";
    static final String PRINT_BLUE = "\u001B[34m";
    static final String PRINT_WHITE = "\u001B[28m";
    static final String PRINT_GREY = "\u001B[90m";

    static final String PRINT_BG_WHITE = "\u001B[47m";
    static final String PRINT_BOLD = "\u001B[1m";

    public static void printLN (){
        System.out.println();
    }

    public static void printLNBgWhite(String message) {
        System.out.println(PRINT_BG_WHITE + PRINT_BLACK + PRINT_BOLD + message + PRINT_RESET);
    }

    public static void printLNWhite(String message) {
        System.out.println(PRINT_WHITE + message + PRINT_RESET);
    }

    public static void printLNBlue(String message) {
        System.out.println(PRINT_BLUE + message + PRINT_RESET);
    }

    public static void printLNGreen(String message) {
        System.out.println(PRINT_GREEN + message + PRINT_RESET);
    }

    public static void printLNGrey(String message) {
        System.out.println(PRINT_GREY + message + PRINT_RESET);
    }

    public static void printLNInfo(String message) {
        System.out.println(PRINT_YELLOW + message + PRINT_RESET);
    }

    public static void printInvalidInputTryAgain(){
        System.out.println(PRINT_YELLOW + "Invalid input, try again." + PRINT_RESET);
    }

    public static void printYourInput(){
        printWhite("Your input : ");
    }

    public static void printInvalidInputTryAgainOrBack(){
        System.out.println(PRINT_YELLOW + "Invalid input, try again or press \"0\" to go back." + PRINT_RESET);
    }

    public static void printInvalidInput(){
        System.out.println(PRINT_YELLOW + "Invalid input." + PRINT_RESET);
    }

    public static void printLNError(String message) {
        System.out.println(PRINT_RED + message + PRINT_RESET);
    }


    public static void printWhite(String message) {
        System.out.print(PRINT_WHITE + message + PRINT_RESET);
    }

    public static void printInfo(String message) {
        System.out.print(PRINT_YELLOW + message + PRINT_RESET);
    }
}
