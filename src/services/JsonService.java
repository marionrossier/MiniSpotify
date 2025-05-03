package services;

import data.entities.Artist;
import data.entities.MusicGender;
import data.entities.Playlist;
import data.entities.Song;
import data.jsons.ArtistRepository;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;

import java.util.Arrays;
import java.util.LinkedList;

public class JsonService {
    public static void main(String[] args) {

        addSong();
    }

    public static void addArtist() {
        ArtistRepository artistRepository = new ArtistRepository();

        artistRepository.addArtist(new Artist("Amy Winehouse"));
        artistRepository.addArtist(new Artist("Lady Gaga"));
        artistRepository.addArtist(new Artist("Boney M."));
        artistRepository.addArtist(new Artist("Macklemore & Ryan Lewis"));
        artistRepository.addArtist(new Artist("John Mayer"));
        artistRepository.addArtist(new Artist("Dany Brillant"));
        artistRepository.addArtist(new Artist("Rihanna"));
        artistRepository.addArtist(new Artist("Madonna"));
        artistRepository.addArtist(new Artist("Muse"));
        artistRepository.addArtist(new Artist("Olivier Dion"));
        artistRepository.addArtist(new Artist("Imagine Dragons"));
    }

    public static void addSong(){
        SongRepository repository = new SongRepository();

        repository.addSong(new Song(1108071776, "Rehab", "Amy Winehouse", "Back to Black", 214, MusicGender.SOUL_RNB, "Rehab - Amy Winehouse - Back to Black - 2006 - Soul _ R&B - 0334.mp3"));
        repository.addSong(new Song(1986076679, "Judas", "Lady Gaga", "Born This Way", 249, MusicGender.POP, "Judas - Lady Gaga - Born This Way - 2011 - Pop _ Electro-pop - 0409.mp3"));
        repository.addSong(new Song(2084461505, "Bloody Mary", "Lady Gaga", "Born This Way", 245, MusicGender.POP, "Bloody Mary - Lady Gaga - Born This Way - 2011 - Pop _ Dark pop - 0405.mp3"));
        repository.addSong(new Song(342105258, "You Know I'm No Good", "Amy Winehouse", "Back to Black", 257, MusicGender.SOUL_RNB, "You Know I'm No Good - Amy Winehouse - Back to Black - 2006 - Soul _ Jazz - 0417.mp3"));
        repository.addSong(new Song(625427469, "Me & Mr. Jones", "Amy Winehouse", "Back to Black", 153, MusicGender.SOUL_RNB, "Me & Mr. Jones - Amy Winehouse - Back to Black - 2006 - Soul _ Jazz - 0233.mp3"));
        repository.addSong(new Song(325561970, "Sunny", "Boney M.", "Sunny", 238, MusicGender.DISCO, "Sunny - Boney M. - Sunny (Official)... - 1976 - Disco - 0358.mp3"));
        repository.addSong(new Song(321324189, "Can't Hold Us", "Macklemore", "The Heist", 258, MusicGender.HIP_HOP, "Can't Hold Us - Macklemore & Ryan Lewis - The Heist - 2012 - Hip-hop - 0418.mp3"));
        repository.addSong(new Song(1280045910, "Come When I Call", "John Mayer", "Where the Light Is", 177, MusicGender.SOUL_RNB, "Come When I Call - John Mayer - Where the Light Is - 2008 - Blues Rock - 0257.mp3"));
        repository.addSong(new Song(354322599, "Viens à Saint Germain", "Dany Brillant", "Viens à Saint-Germain", 177, MusicGender.FRENCH_VARIETY, "Viens à Saint Germain - Dany Brillant - Viens à Saint-Germain - 1992 - Chanson française _ Swing - 0257.mp3"));
        repository.addSong(new Song(1925355941, "Suzette", "Dany Brillant", "C'est ça qui est bon", 177, MusicGender.FRENCH_VARIETY, "Suzette - Dany Brillant - C'est ça qui est bon - 1991 - Chanson française _ Swing - 0257.mp3"));
        repository.addSong(new Song(1951451340, "Diamonds", "Rihanna", "Unapologetic", 225, MusicGender.SOUL_RNB, "Diamonds - Rihanna - Unapologetic - 2012 - Pop _ R&B - 0345.mp3"));
        repository.addSong(new Song(243871940, "Free Fallin", "John Mayer", "Where the Light Is", 243, MusicGender.ROCK, "Free Fallin - John Mayer - Where the Light Is - 2008 - Soft Rock - 0403.mp3"));
        repository.addSong(new Song(1988790520, "Give It 2 Me", "Madonna", "Hard Candy", 287, MusicGender.POP, "Give It 2 Me - Madonna - Hard Candy - 2008 - Dance-pop - 0447.mp3"));
        repository.addSong(new Song(719812166, "I'm Gonna Find Another You", "John Mayer", "Where the Light Is", 283, MusicGender.SOUL_RNB, "I'm Gonna Find Another You (Live) - John Mayer - Where the Light Is - 2008 - Blues _ Soul - 0443.mp3"));
        repository.addSong(new Song(700468481, "La Isla Bonita", "Madonna", "True Blue", 241, MusicGender.POP, "La Isla Bonita - Madonna - True Blue - 1986 - Latin pop - 0401.mp3"));
        repository.addSong(new Song(469321884, "Man Down", "Rihanna", "Loud", 267, MusicGender.REGGAE, "Man Down - Rihanna - Loud - 2010 - Reggae fusion - 0427.mp3"));
        repository.addSong(new Song(1824616046, "Time is Running Out", "Muse", "Absolution", 236, MusicGender.ROCK, "Time is Running Out - Muse - Absolution - 2003 - Alternative Rock - 0356.mp3"));
        repository.addSong(new Song(998984026, "De mes propres ailes (Remix)", "Olivier Dion", "Les 3 Mousquetaires", 221, MusicGender.POP, "De mes propres ailes (Remix) - Olivier Dion - Les 3 Mousquetaires - 2016 - Pop française - 0341.mp3"));
        repository.addSong(new Song(661206135, "Fuck Me Pumps", "Amy Winehouse", "Frank", 198, MusicGender.SOUL_RNB, "Fuck Me Pumps - Amy Winehouse - Frank - 2003 - Soul _ Jazz - 0318.mp3"));
        repository.addSong(new Song(1290739974, "Poker Face", "Lady Gaga", "The Fame Monster", 237, MusicGender.POP, "Poker Face - Lady Gaga - The Fame Monster - 2008 - Electropop - 0357.mp3"));
        repository.addSong(new Song(1252829874, "Right Now", "Rihanna", "Unapologetic", 181, MusicGender.ELECTRO, "Right Now - Rihanna & David Guetta - Unapologetic - 2012 - EDM _ Dance-pop - 0301.mp3"));
        repository.addSong(new Song(521970022, "Thrift Shop", "Macklemore", "The Heist", 232, MusicGender.HIP_HOP, "Thrift Shop - Macklemore & Ryan Lewis - The Heist - 2012 - Hip-hop _ Comedy rap - 0352.mp3"));
        repository.addSong(new Song(1287974581, "Whatever It Takes", "Imagine Dragons", "Evolve", 219, MusicGender.ROCK, "Whatever It Takes - Imagine Dragons - Evolve - 2017 - Pop rock - 0339.mp3"));
        repository.addSong(new Song(614172035, "Believer", "Imagine Dragons", "Evolve", 204, MusicGender.ROCK, "Believer - Imagine Dragons - Evolve - 2017 - Pop rock _ Alt rock - 0324.mp3"));
        repository.addSong(new Song(494087492, "Demons", "Imagine Dragons", "Night Visions", 177, MusicGender.ROCK, "Demons - Imagine Dragons - Night Visions - 2012 - Pop rock _ Indie pop - 0257.mp3"));
        repository.addSong(new Song(515539482, "Not Today", "Imagine Dragons", "Me Before You", 238, MusicGender.ROCK, "Not Today - Imagine Dragons - Me Before You - 2016 - Pop rock - 0358.mp3"));

    }

    public void addUser (){
        // TODO : Implementer la méthode pour ajouter des utilisateurs (Florent)
    }

    public static void addPlaylist() {

        PlaylistRepository playlistRepository = new PlaylistRepository();

        playlistRepository.savePlaylist(new Playlist("POP Vibes",
                new LinkedList<>(Arrays.asList(1986076679, 2084461505, 1988790520, 700468481, 998984026, 1290739974)),
                1480, 6));

        playlistRepository.savePlaylist(new Playlist("Rock Legends",
                new LinkedList<>(Arrays.asList(243871940, 1824616046, 1287974581, 614172035, 494087492, 515539482)),
                1317, 6));

        playlistRepository.savePlaylist(new Playlist("Amy Forever",
                new LinkedList<>(Arrays.asList(1108071776, 342105258, 625427469, 661206135)),
                822, 4));

        playlistRepository.savePlaylist(new Playlist("Imagine This",
                new LinkedList<>(Arrays.asList(1287974581, 614172035, 494087492, 515539482)),
                838, 4));

        playlistRepository.savePlaylist(new Playlist("Quick Hits",
                new LinkedList<>(Arrays.asList(625427469, 1280045910, 354322599, 1925355941, 661206135, 1252829874, 494087492)),
                1240, 7));

        playlistRepository.savePlaylist(new Playlist("Girls",
                new LinkedList<>(Arrays.asList(1108071776, 342105258, 625427469, 661206135, 1986076679, 2084461505, 1290739974, 1951451340, 469321884, 1252829874, 1988790520, 700468481)),
                2754, 12));

        playlistRepository.savePlaylist(new Playlist("Boys",
                new LinkedList<>(Arrays.asList(325561970, 321324189, 521970022, 1280045910, 719812166, 243871940, 1824616046, 1287974581, 614172035, 494087492, 515539482, 998984026)),
                2726, 12));

        playlistRepository.savePlaylist(new Playlist("Soul & RnB Grooves",
                new LinkedList<>(Arrays.asList(1108071776, 342105258, 625427469, 1280045910, 1951451340, 719812166, 661206135)),
                1507, 7));

        playlistRepository.savePlaylist(new Playlist("Dance Floor",
                new LinkedList<>(Arrays.asList(325561970, 1252829874, 321324189, 521970022)),
                909, 4));

        playlistRepository.savePlaylist(new Playlist("Random Favorites",
                new LinkedList<>(Arrays.asList(1280045910, 1951451340, 1252829874, 719812166, 521970022)),
                1114, 5));

        playlistRepository.savePlaylist(new Playlist("Before 2000",
                new LinkedList<>(Arrays.asList(325561970, 354322599, 1925355941, 700468481)),
                833, 4));

        playlistRepository.savePlaylist(new Playlist("AllSongs",
                new LinkedList<>(Arrays.asList(
                        1108071776, 1986076679, 2084461505, 342105258, 625427469, 325561970, 321324189, 1280045910,
                        354322599, 1925355941, 1951451340, 243871940, 1988790520, 719812166, 700468481, 469321884,
                        1824616046, 998984026, 661206135, 1290739974, 1252829874, 521970022, 1287974581, 614172035,
                        494087492, 515539482)),
                6286, 26));
        }
    }
