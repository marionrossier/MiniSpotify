import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlaymusicTest {
    private static Player player;
    private static Thread playerThread;
    private static boolean isPlaying = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Entrez une commande (start, stop, exit) :");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "start":
                    if (!isPlaying) {
                        startMusic();
                    } else {
                        System.out.println("La musique est déjà en cours de lecture.");
                    }
                    break;

                case "stop":
                    if (isPlaying) {
                        stopMusic();
                    } else {
                        System.out.println("Aucune musique n'est en cours de lecture.");
                    }
                    break;

                case "exit":
                    stopMusic();
                    System.out.println("Fermeture du programme.");
                    return;

                default:
                    System.out.println("Commande inconnue.");
            }
        }
    }

    private static void startMusic() {
        try {
         FileInputStream audioFile = new FileInputStream("src/data/songsfiles/boneyM_Sunny.mp3");
            player = new Player(audioFile);

            playerThread = new Thread(() -> {
                try {
                    isPlaying = true;
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                } finally {
                    isPlaying = false;
                }
            });

            playerThread.start();
            System.out.println("Lecture démarrée.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    private static void stopMusic() {
        if (player != null) {
            player.close();
            playerThread.interrupt();
            isPlaying = false;
            System.out.println("Lecture arrêtée.");
        }
    }
}