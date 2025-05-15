package Utils;

import serverSide.socket.AudioSocketServer;
import serverSide.socket.SocketServer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Do you want to start the app with or without socket handeling ?");
        System.out.println("1. With socket");
        System.out.println("2. Without socket");

        System.out.print("Enter your choice: ");
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
                System.out.println("Invalid argument");
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
        new Thread(audioSocketServer::main).start();

        // ⏱ Petite pause pour éviter que le client tente une connexion trop tôt
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // ✅ Puis démarrer le client
        CompositionRootClientSide compositionRootClientSide = new CompositionRootClientSide();
        compositionRootClientSide.startApp();
    }

}