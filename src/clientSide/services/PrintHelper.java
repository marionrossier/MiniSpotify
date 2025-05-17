package clientSide.services;

public class PrintHelper {

    static final String PRINT_RESET = "\u001B[0m";

    static final String PRINT_BLACK = "\u001B[30m";
    static final String PRINT_RED = "\u001B[31m";
    static final String PRINT_GREEN = "\u001B[32m";
    static final String PRINT_YELLOW = "\u001B[33m";
    static final String PRINT_BLUE = "\u001B[34m";
    static final String PRINT_WHITE = "\u001B[28m";

    static final String PRINT_BGWHITE = "\u001B[47m";
    static final String PRINT_BOLD = "\u001B[1m";

    public static void printLN (){
        System.out.println();
    }

    public static void printLNBgWhite(String message) {
        System.out.println(PRINT_BGWHITE + PRINT_BLACK + PRINT_BOLD + message + PRINT_RESET);
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

    public static void printLNInfo(String message) {
        System.out.println(PRINT_YELLOW + message + PRINT_RESET);
    }

    //TODO : vérifier les impressions d'erreur
    public static void printLNError(String message) {
        System.out.println(PRINT_RED + message + PRINT_RESET);
    }

    public static void printLNSystem(String message){
        System.out.println(PRINT_BLACK + message + PRINT_RESET);
    }



    public static void printWhite(String message) {
        System.out.print(PRINT_WHITE + message + PRINT_RESET);
    }

    public static void printInfo(String message) {
        System.out.print(PRINT_YELLOW + message + PRINT_RESET);
    }
}
