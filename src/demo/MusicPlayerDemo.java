package demo;

import player_StatePattern.file_player.MusicPlayer;
import player_StatePattern.file_player.IMusicPlayer;

public class MusicPlayerDemo {
    public static void main(String[] args) {
        IMusicPlayer player = new MusicPlayer();

        String firstSong = "resources/songsfiles/Rehab - Amy Winehouse - Back to Black - 2006 - Soul _ R&B - 0334.mp3";
        String nextSong = "resources/songsfiles/Judas - Lady Gaga - Born This Way - 2011 - Pop _ Electro-pop - 0409.mp3";

        player.setOnSongEndAction(() -> {
            System.out.println("Première chanson terminée, lancement de la suivante !");
            player.play(nextSong);
        });

        player.play(firstSong);

        try {
            Thread.sleep(1000 * 5);
            player.pause();
            Thread.sleep(1000 * 5);
            player.resume();
            Thread.sleep(1000 * 60 * 10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lecture terminée. Fin du programme.");
    }
}
