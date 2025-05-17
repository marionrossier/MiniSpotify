package Utils;

import serverSide.socket.AudioSocketServer;
import serverSide.socket.SocketServer;

import java.util.Scanner;

import static clientSide.services.PrintHelper.*;

public class Main {
    public static void main(String[] args) {



        printLNWhite("Do you want to start the app with or without socket handeling ?");
        printLNWhite("1. With socket");
        printLNWhite("2. Without socket");

        printWhite("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {
            case 1:
                withSocketDivision();
                break;
            case 2:
                noSocketApp();
                break;
            default:
                printLNInfo("Invalid argument");
                break;
        }
    }

    public static void noSocketApp () {
        CompositionRootPatternNoSocket compositionRootPatternNoSocket = new CompositionRootPatternNoSocket();

        compositionRootPatternNoSocket.copySongs();
        compositionRootPatternNoSocket.copyJsons();
        compositionRootPatternNoSocket.startApp();
    }

    public static void withSocketDivision(){
        CompositionRootServerSide compositionRootServerSide = new CompositionRootServerSide();

        AudioSocketServer audioSocketServer = new AudioSocketServer(compositionRootServerSide.backAudioRepo);
        SocketServer socketServer = new SocketServer(
                compositionRootServerSide.backUserRepo,
                compositionRootServerSide.backPlaylistRepo,
                compositionRootServerSide.backSongRepo,
                compositionRootServerSide.backArtistRepo
        );

        // ✅ Lancer les serveurs AVANT
        new Thread(socketServer::main).start();
        new Thread(audioSocketServer::audioSocketMain).start();

        // ⏱ Petite pause pour éviter que le client tente une connexion trop tôt
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        compositionRootServerSide.copySongs();
        compositionRootServerSide.copyJsons();

        // ✅ Puis démarrer le client
        CompositionRootClientSide compositionRootClientSide = new CompositionRootClientSide();
        compositionRootClientSide.startApp();
    }

}