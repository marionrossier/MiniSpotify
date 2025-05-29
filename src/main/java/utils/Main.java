package utils;

import clientSide.CompositionRootClientSide;
import serverSide.CompositionRootServerSide;
import serverSide.socket.AudioSocketServer;
import serverSide.socket.SocketServer;

import java.util.Scanner;

import static clientSide.services.PrintHelper.*;

public class Main {
    public static void main(String[] args) {

        printLNWhite("Do you want to start the app with or without socket handling ?");
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
                printInvalidInput();
                break;
        }
    }

    public static void noSocketApp () {
        CompositionRootPatternNoSocket compositionRootPatternNoSocket = new CompositionRootPatternNoSocket();
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

        compositionRootServerSide.startApp(socketServer, audioSocketServer);


        CompositionRootClientSide compositionRootClientSide = new CompositionRootClientSide();
        compositionRootClientSide.startApp();
    }

}