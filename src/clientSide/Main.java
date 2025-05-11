package clientSide;

public class Main {
    public static void main(String[] args) {
        CompositionRootPattern compositionRootPattern = new CompositionRootPattern();

        compositionRootPattern.copySongs();
        compositionRootPattern.copyJsons();
        compositionRootPattern.startApp();
    }
}