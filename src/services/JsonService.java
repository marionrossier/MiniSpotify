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
        addArtist();
        addSong();
        addPlaylist();
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

        repository.addSong(new Song("Rehab", "Amy Winehouse", "Back to Black",
                214, MusicGender.SOUL_RNB, "Rehab - Amy Winehouse - Back to Black - " +
                "2006 - Soul _ R&B - 0334.mp3"));
        repository.addSong(new Song("Judas", "Lady Gaga", "Born This Way",
                249, MusicGender.POP, "Judas - Lady Gaga - Born This Way - " +
                "2011 - Pop _ Electro-pop - 0409.mp3"));
        repository.addSong(new Song("Bloody Mary", "Lady Gaga", "Born This Way",
                245, MusicGender.POP, "Bloody Mary - Lady Gaga - Born This Way - " +
                "2011 - Pop _ Dark pop - 0405.mp3"));
        repository.addSong(new Song("You Know I'm No Good", "Amy Winehouse", "Back to Black",
                257, MusicGender.SOUL_RNB, "You Know I'm No Good - Amy Winehouse - Back to Black - " +
                "2006 - Soul _ Jazz - 0417.mp3"));
        repository.addSong(new Song("Me & Mr. Jones", "Amy Winehouse", "Back to Black",
                153, MusicGender.SOUL_RNB, "Me & Mr. Jones - Amy Winehouse - Back to Black - " +
                "2006 - Soul _ Jazz - 0233.mp3"));
        repository.addSong(new Song("Sunny", "Boney M.", "Sunny",
                238, MusicGender.DISCO, "Sunny - Boney M. - Sunny (Official)... - " +
                "1976 - Disco - 0358.mp3"));
        repository.addSong(new Song("Can't Hold Us", "Macklemore", "The Heist",
                258, MusicGender.HIP_HOP, "Can't Hold Us - Macklemore & Ryan Lewis - The Heist - " +
                "2012 - Hip-hop - 0418.mp3"));
        repository.addSong(new Song("Come When I Call", "John Mayer", "Where the Light Is",
                177, MusicGender.SOUL_RNB, "Come When I Call - John Mayer - Where the Light Is - " +
                "2008 - Blues Rock - 0257.mp3"));
        repository.addSong(new Song("Viens à Saint Germain", "Dany Brillant", "Viens à Saint-Germain",
                177, MusicGender.FRENCH_VARIETY, "Viens à Saint Germain - Dany Brillant - Viens à Saint-Germain - " +
                "1992 - Chanson française _ Swing - 0257.mp3"));
        repository.addSong(new Song("Suzette", "Dany Brillant", "C'est ça qui est bon",
                177, MusicGender.FRENCH_VARIETY, "Suzette - Dany Brillant - C'est ça qui est bon - " +
                "1991 - Chanson française _ Swing - 0257.mp3"));
        repository.addSong(new Song("Diamonds", "Rihanna", "Unapologetic",
                225, MusicGender.SOUL_RNB, "Diamonds - Rihanna - Unapologetic - " +
                "2012 - Pop _ R&B - 0345.mp3"));
        repository.addSong(new Song("Free Fallin", "John Mayer", "Where the Light Is",
                243, MusicGender.ROCK, "Free Fallin - John Mayer - Where the Light Is - " +
                "2008 - Soft Rock - 0403.mp3"));
        repository.addSong(new Song("Give It 2 Me", "Madonna", "Hard Candy",
                287, MusicGender.POP, "Give It 2 Me - Madonna - Hard Candy - " +
                "2008 - Dance-pop - 0447.mp3"));
        repository.addSong(new Song("I'm Gonna Find Another You", "John Mayer", "Where the Light Is",
                283, MusicGender.SOUL_RNB, "I'm Gonna Find Another You (Live) - John Mayer - Where the Light Is - " +
                "2008 - Blues _ Soul - 0443.mp3"));
        repository.addSong(new Song("La Isla Bonita", "Madonna", "True Blue",
                241, MusicGender.POP, "La Isla Bonita - Madonna - True Blue - " +
                "1986 - Latin pop - 0401.mp3"));
        repository.addSong(new Song("Man Down", "Rihanna", "Loud",
                267, MusicGender.REGGAE, "Man Down - Rihanna - Loud - " +
                "2010 - Reggae fusion - 0427.mp3"));
        repository.addSong(new Song("Time is Running Out", "Muse", "Absolution",
                236, MusicGender.ROCK, "Time is Running Out - Muse - Absolution - " +
                "2003 - Alternative Rock - 0356.mp3"));
        repository.addSong(new Song("De mes propres ailes (Remix)", "Olivier Dion", "Les 3 Mousquetaires",
                221, MusicGender.POP, "De mes propres ailes (Remix) - Olivier Dion - Les 3 Mousquetaires - " +
                "2016 - Pop française - 0341.mp3"));
        repository.addSong(new Song("Fuck Me Pumps", "Amy Winehouse", "Frank",
                198, MusicGender.SOUL_RNB, "Fuck Me Pumps - Amy Winehouse - Frank - " +
                "2003 - Soul _ Jazz - 0318.mp3"));
        repository.addSong(new Song("Poker Face", "Lady Gaga", "The Fame Monster",
                237, MusicGender.POP, "Poker Face - Lady Gaga - The Fame Monster - " +
                "2008 - Electropop - 0357.mp3"));
        repository.addSong(new Song("Right Now", "Rihanna", "Unapologetic",
                181, MusicGender.ELECTRO, "Right Now - Rihanna & David Guetta - Unapologetic - " +
                "2012 - EDM _ Dance-pop - 0301.mp3"));
        repository.addSong(new Song("Thrift Shop", "Macklemore", "The Heist",
                232, MusicGender.HIP_HOP, "Thrift Shop - Macklemore & Ryan Lewis - The Heist - " +
                "2012 - Hip-hop _ Comedy rap - 0352.mp3"));
        repository.addSong(new Song("Whatever It Takes", "Imagine Dragons", "Evolve",
                219, MusicGender.ROCK, "Whatever It Takes - Imagine Dragons - Evolve - " +
                "2017 - Pop rock - 0339.mp3"));
        repository.addSong(new Song("Believer", "Imagine Dragons", "Evolve",
                204, MusicGender.ROCK, "Believer - Imagine Dragons - Evolve - " +
                "2017 - Pop rock _ Alt rock - 0324.mp3"));
        repository.addSong(new Song("Demons", "Imagine Dragons", "Night Visions",
                177, MusicGender.ROCK, "Demons - Imagine Dragons - Night Visions - " +
                "2012 - Pop rock _ Indie pop - 0257.mp3"));
        repository.addSong(new Song("Not Today", "Imagine Dragons", "Me Before You",
                238, MusicGender.ROCK, "Not Today - Imagine Dragons - Me Before You - " +
                "2016 - Pop rock - 0358.mp3"));
    }

    public void addUser (){
        // TODO : Implementer la méthode pour ajouter des utilisateurs (Florent)
    }

    public static void addPlaylist() {

        PlaylistRepository playlistRepository = new PlaylistRepository();

        playlistRepository.addPlaylist(new Playlist("POP Vibes",
                new LinkedList<>(Arrays.asList(872272219, 1297812817, 1411387436, 474736140, 172586501,
                        1338197511)),
                1480, 6));

        playlistRepository.addPlaylist(new Playlist("Rock Legends",
                new LinkedList<>(Arrays.asList(424099184, 1302348970, 1674132511, 1123081604, 1281601206,
                        977169387)),
                1317, 6));

        playlistRepository.addPlaylist(new Playlist("Amy Forever",
                new LinkedList<>(Arrays.asList(155113268, 365058535, 836418353, 1690548446)),
                822, 4));

        playlistRepository.addPlaylist(new Playlist("Imagine This",
                new LinkedList<>(Arrays.asList(1674132511, 1123081604, 1281601206, 977169387)),
                838, 4));

        playlistRepository.addPlaylist(new Playlist("Quick Hits",
                new LinkedList<>(Arrays.asList(836418353, 182189416, 949217884, 1667426682, 1690548446,
                        1259359522, 1281601206)),
                1240, 7));

        playlistRepository.addPlaylist(new Playlist("Girls",
                new LinkedList<Integer>(Arrays.asList(
                        155113268, 365058535, 836418353, 1690548446, 872272219, 1297812817, 1338197511,
                        1581494300, 1846376291, 1259359522, 1411387436, 474736140)),
                2754, 12));

        playlistRepository.addPlaylist(new Playlist("Boys",
                new LinkedList<Integer>(Arrays.asList(
                        1668230988, 1524582978, 1140627190, 182189416, 1444691749, 424099184,
                        1302348970, 1674132511, 1123081604, 1281601206, 977169387, 172586501)),
                2726, 12));

        playlistRepository.addPlaylist(new Playlist("Soul & RnB Grooves",
                new LinkedList<>(Arrays.asList(155113268, 365058535, 836418353, 182189416, 1581494300,
                        1444691749, 1690548446)),
                1507, 7));

        playlistRepository.addPlaylist(new Playlist("Dance Floor",
                new LinkedList<>(Arrays.asList(1668230988, 1259359522,1524582978, 1140627190)),
                909, 4));


        playlistRepository.addPlaylist(new Playlist("Random Favorites",
                new LinkedList<>(Arrays.asList(182189416, 1581494300, 1259359522, 1444691749, 1140627190)),
                1114, 5));

        playlistRepository.addPlaylist(new Playlist("Before 2000",
                new LinkedList<Integer>(Arrays.asList(1668230988, 949217884, 1667426682, 474736140)),
                833, 4));

        playlistRepository.addPlaylist(new Playlist("AllSongs",
                new LinkedList<>(Arrays.asList(
                        155113268, 872272219, 1297812817, 365058535, 836418353, 1668230988, 1524582978,
                        182189416, 949217884, 1667426682, 1581494300, 424099184, 1411387436, 1444691749,
                        474736140, 1846376291, 1302348970, 172586501, 1690548446, 1338197511, 1259359522,
                        1140627190, 1674132511, 1123081604, 1281601206, 977169387)),
                6286, 26));
        }
    }
