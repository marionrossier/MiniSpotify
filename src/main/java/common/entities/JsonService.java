package common.entities;

import clientSide.services.*;
import common.*;
import common.services.StockageService;
import serverSide.repoLocal.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JsonService {
    public static void main(String[] args) throws IOException {

        Files.deleteIfExists(Path.of("src/main/resources/jsons/user.json"));
        Files.deleteIfExists(Path.of("src/main/resources/jsons/song.json"));
        Files.deleteIfExists(Path.of("src/main/resources/jsons/artist.json"));
        Files.deleteIfExists(Path.of("src/main/resources/jsons/playlist.json"));

        File userJsonFile = Files.createFile(Path.of("src/main/resources/jsons/user.json")).toFile();
        File songJsonFile = Files.createFile(Path.of("src/main/resources/jsons/song.json")).toFile();
        File artistJsonFile = Files.createFile(Path.of("src/main/resources/jsons/artist.json")).toFile();
        File playlistJsonFile = Files.createFile(Path.of("src/main/resources/jsons/playlist.json")).toFile();

        StockageService stockageService = new StockageService();

        ArtistLocalRepository artistLocalRepository = new ArtistLocalRepository(artistJsonFile.getAbsolutePath());
        UserLocalRepository userLocalRepository = new UserLocalRepository(userJsonFile.getAbsolutePath());
        PlaylistLocalRepository playlistLocalRepository = new PlaylistLocalRepository(playlistJsonFile.getAbsolutePath());
        ISongRepository songLocalRepository = new SongLocalRepository(songJsonFile.getAbsolutePath(),
                stockageService, artistLocalRepository);

        AudioLocalRepository audioLocalRepository = new AudioLocalRepository();
        PasswordService passwordService = new PasswordService(userLocalRepository);

        ToolBoxService toolBoxService = new ToolBoxService(playlistLocalRepository, userLocalRepository,
                songLocalRepository, artistLocalRepository, audioLocalRepository);

        UserService userService = new UserService(toolBoxService, passwordService);

        JsonService.addUser(userService);
        addArtist(artistLocalRepository);
        addSong(songLocalRepository, artistLocalRepository);
        addPlaylist(playlistLocalRepository);
        addPlaylistToUser(playlistLocalRepository, userLocalRepository);
    }

    private static void addPlaylistToUser(PlaylistLocalRepository playlistLocalRepository, UserLocalRepository userLocalRepository) {

        int allSongID = playlistLocalRepository.getPlaylistByName("AllSongs").getPlaylistId();
        int danceFloorId = playlistLocalRepository.getPlaylistByName("Dance Floor").getPlaylistId();
        int before2000Id = playlistLocalRepository.getPlaylistByName("Before 2000").getPlaylistId();
        int randomFavoriteId = playlistLocalRepository.getPlaylistByName("Random Favorites").getPlaylistId();
        int imagineThisId = playlistLocalRepository.getPlaylistByName("Imagine This").getPlaylistId();
        int girlsId = playlistLocalRepository.getPlaylistByName("Girls").getPlaylistId();
        int boysId = playlistLocalRepository.getPlaylistByName("Boys").getPlaylistId();
        int popVibesId = playlistLocalRepository.getPlaylistByName("POP Vibes").getPlaylistId();
        int rockLegendsId = playlistLocalRepository.getPlaylistByName("Rock Legends").getPlaylistId();
        int amyForeverId = playlistLocalRepository.getPlaylistByName("Amy Forever").getPlaylistId();
        int quickHitsId = playlistLocalRepository.getPlaylistByName("Quick Hits").getPlaylistId();
        int soulRnBId = playlistLocalRepository.getPlaylistByName("Soul & RnB Grooves").getPlaylistId();


        User marion = userLocalRepository.getUserById(232928320);
        List<Integer> marionPlaylists = new ArrayList<>();
        marionPlaylists.add(allSongID);
        marionPlaylists.add(danceFloorId);
        marionPlaylists.add(girlsId);
        marionPlaylists.add(boysId);
        marion.setPlaylists(marionPlaylists);
        userLocalRepository.saveUser(marion);

        User florent = userLocalRepository.getUserById(1726370281);
        List<Integer> florentPlaylists = new ArrayList<>();
        florentPlaylists.add(allSongID);
        florentPlaylists.add(before2000Id);
        florentPlaylists.add(randomFavoriteId);
        florentPlaylists.add(imagineThisId);
        florent.setPlaylists(florentPlaylists);
        userLocalRepository.saveUser(florent);


        User admin = userLocalRepository.getUserById(1);
        List<Integer> adminPlaylists = new ArrayList<>();
        adminPlaylists.add(popVibesId);
        adminPlaylists.add(rockLegendsId);
        adminPlaylists.add(amyForeverId);
        adminPlaylists.add(quickHitsId);
        adminPlaylists.add(soulRnBId);
        admin.setPlaylists(adminPlaylists);
        userLocalRepository.saveUser(admin);

    }

    public static void addArtist(ArtistLocalRepository artistLocalRepository) {

        LinkedList<Integer> amy = new LinkedList<>(Arrays.asList(1108071776,342105258,625427469,661206135));
        LinkedList<Integer> ladyGaga = new LinkedList<>(Arrays.asList(1986076679,2084461505,1290739974));
        LinkedList<Integer> boney = new LinkedList<>(Arrays.asList(325561970));
        LinkedList<Integer> macklemore = new LinkedList<>(Arrays.asList(321324189, 521970022));
        LinkedList<Integer> johnMayer = new LinkedList<>(Arrays.asList(1280045910, 243871940, 719812166));
        LinkedList<Integer> dany = new LinkedList<>(Arrays.asList(354322599, 1925355941));
        LinkedList<Integer> riri = new LinkedList<>(Arrays.asList(1951451340,469321884,1252829874));
        LinkedList<Integer> madonna = new LinkedList<>(Arrays.asList(1988790520, 700468481));
        LinkedList<Integer> muse = new LinkedList<>(Arrays.asList(1824616046));
        LinkedList<Integer> oliDi = new LinkedList<>(Arrays.asList(998984026));
        LinkedList<Integer> dragons = new LinkedList<>(Arrays.asList(1287974581, 614172035,494087492, 515539482));

        artistLocalRepository.addArtist(new Artist(960571432, "Amy Winehouse",amy));
        artistLocalRepository.addArtist(new Artist(887780660, "Lady Gaga", ladyGaga ));
        artistLocalRepository.addArtist(new Artist(1297291487,"Boney M.", boney ));
        artistLocalRepository.addArtist(new Artist(1088398922, "Macklemore", macklemore ));
        artistLocalRepository.addArtist(new Artist(375990935, "John Mayer", johnMayer));
        artistLocalRepository.addArtist(new Artist(375032367,"Danny Brillant", dany));
        artistLocalRepository.addArtist(new Artist(1916983454, "Rihanna", riri));
        artistLocalRepository.addArtist(new Artist(393467085,"Madonna", madonna));
        artistLocalRepository.addArtist(new Artist(202791500, "Muse", muse));
        artistLocalRepository.addArtist(new Artist(177334402, "Olivier Dion", oliDi));
        artistLocalRepository.addArtist(new Artist(609219333, "Imagine Dragons", dragons));
    }

    public static void addSong(ISongRepository songLocalRepository, ArtistLocalRepository artistLocalRepository){

        songLocalRepository.addSong(new Song(1108071776, "Rehab",
                artistLocalRepository.getArtistByName("Amy Winehouse").getArtistId() ,
                214, MusicGender.SOUL_RNB, "Rehab - Amy Winehouse - Back to Black - 2006 - Soul _ R&B - 0334.mp3"));
        songLocalRepository.addSong(new Song(1986076679, "Judas",
                artistLocalRepository.getArtistByName("Lady Gaga").getArtistId(),
                249, MusicGender.POP, "Judas - Lady Gaga - Born This Way - 2011 - Pop _ Electro-pop - 0409.mp3"));
        songLocalRepository.addSong(new Song(2084461505, "Bloody Mary",
                artistLocalRepository.getArtistByName("Lady Gaga").getArtistId(),
                245, MusicGender.POP, "Bloody Mary - Lady Gaga - Born This Way - 2011 - Pop _ Dark pop - 0405.mp3"));
        songLocalRepository.addSong(new Song(342105258, "You Know I'm No Good",
                artistLocalRepository.getArtistByName("Amy Winehouse").getArtistId(),
                257, MusicGender.SOUL_RNB, "You Know I'm No Good - Amy Winehouse - Back to Black - 2006 - Soul _ Jazz - 0417.mp3"));
        songLocalRepository.addSong(new Song(625427469, "Me & Mr. Jones",
                artistLocalRepository.getArtistByName("Amy Winehouse").getArtistId(),
                153, MusicGender.SOUL_RNB, "Me & Mr. Jones - Amy Winehouse - Back to Black - 2006 - Soul _ Jazz - 0233.mp3"));
        songLocalRepository.addSong(new Song(325561970, "Sunny",
                artistLocalRepository.getArtistByName("Boney M.").getArtistId(),
                238, MusicGender.DISCO, "Sunny - Boney M. - Sunny (Official)... - 1976 - Disco - 0358.mp3"));
        songLocalRepository.addSong(new Song(321324189, "Can't Hold Us",
                artistLocalRepository.getArtistByName("Macklemore").getArtistId(),
                258, MusicGender.HIP_HOP, "Can't Hold Us - Macklemore & Ryan Lewis - The Heist - 2012 - Hip-hop - 0418.mp3"));
        songLocalRepository.addSong(new Song(1280045910, "Come When I Call",
                artistLocalRepository.getArtistByName("John Mayer").getArtistId(),
                177, MusicGender.SOUL_RNB, "Come When I Call - John Mayer - Where the Light Is - 2008 - Blues Rock - 0257.mp3"));
        songLocalRepository.addSong(new Song(354322599, "Viens à Saint Germain",
                artistLocalRepository.getArtistByName("Danny Brillant").getArtistId(),
                177, MusicGender.FRENCH_VARIETY, "Viens à Saint Germain - Dany Brillant - Viens à Saint-Germain - 1992 - Chanson française _ Swing - 0257.mp3"));
        songLocalRepository.addSong(new Song(1925355941, "Suzette",
                artistLocalRepository.getArtistByName("Danny Brillant").getArtistId(),
                177, MusicGender.FRENCH_VARIETY, "Suzette - Dany Brillant - C'est ça qui est bon - 1991 - Chanson française _ Swing - 0257.mp3"));
        songLocalRepository.addSong(new Song(1951451340, "Diamonds",
                artistLocalRepository.getArtistByName("Rihanna").getArtistId(),
                225, MusicGender.SOUL_RNB, "Diamonds - Rihanna - Unapologetic - 2012 - Pop _ R&B - 0345.mp3"));
        songLocalRepository.addSong(new Song(243871940, "Free Fallin",
                artistLocalRepository.getArtistByName("John Mayer").getArtistId(),
                243, MusicGender.ROCK, "Free Fallin - John Mayer - Where the Light Is - 2008 - Soft Rock - 0403.mp3"));
        songLocalRepository.addSong(new Song(1988790520, "Give It 2 Me",
                artistLocalRepository.getArtistByName("Madonna").getArtistId(),
                287, MusicGender.POP, "Give It 2 Me - Madonna - Hard Candy - 2008 - Dance-pop - 0447.mp3"));
        songLocalRepository.addSong(new Song(719812166, "I'm Gonna Find Another You",
                artistLocalRepository.getArtistByName("John Mayer").getArtistId(),
                283, MusicGender.SOUL_RNB, "I'm Gonna Find Another You (Live) - John Mayer - Where the Light Is - 2008 - Blues _ Soul - 0443.mp3"));
        songLocalRepository.addSong(new Song(700468481, "La Isla Bonita",
                artistLocalRepository.getArtistByName("Madonna").getArtistId(),
                241, MusicGender.POP, "La Isla Bonita - Madonna - True Blue - 1986 - Latin pop - 0401.mp3"));
        songLocalRepository.addSong(new Song(469321884, "Man Down",
                artistLocalRepository.getArtistByName("Rihanna").getArtistId(),
                267, MusicGender.REGGAE, "Man Down - Rihanna - Loud - 2010 - Reggae fusion - 0427.mp3"));
        songLocalRepository.addSong(new Song(1824616046, "Time is Running Out",
                artistLocalRepository.getArtistByName("Muse").getArtistId(),
                236, MusicGender.ROCK, "Time is Running Out - Muse - Absolution - 2003 - Alternative Rock - 0356.mp3"));
        songLocalRepository.addSong(new Song(998984026, "De mes propres ailes (Remix)",
                artistLocalRepository.getArtistByName("Olivier Dion").getArtistId(),
                221, MusicGender.POP, "De mes propres ailes (Remix) - Olivier Dion - Les 3 Mousquetaires - 2016 - Pop française - 0341.mp3"));
        songLocalRepository.addSong(new Song(661206135, "Fuck Me Pumps",
                artistLocalRepository.getArtistByName("Amy Winehouse").getArtistId(),
                198, MusicGender.SOUL_RNB, "Fuck Me Pumps - Amy Winehouse - Frank - 2003 - Soul _ Jazz - 0318.mp3"));
        songLocalRepository.addSong(new Song(1290739974, "Poker Face",
                artistLocalRepository.getArtistByName("Lady Gaga").getArtistId(),
                237, MusicGender.POP, "Poker Face - Lady Gaga - The Fame Monster - 2008 - Electropop - 0357.mp3"));
        songLocalRepository.addSong(new Song(1252829874, "Right Now",
                artistLocalRepository.getArtistByName("Rihanna").getArtistId(),
                181, MusicGender.ELECTRO, "Right Now - Rihanna & David Guetta - Unapologetic - 2012 - EDM _ Dance-pop - 0301.mp3"));
        songLocalRepository.addSong(new Song(521970022, "Thrift Shop",
                artistLocalRepository.getArtistByName("Macklemore").getArtistId(),
                232, MusicGender.HIP_HOP, "Thrift Shop - Macklemore & Ryan Lewis - The Heist - 2012 - Hip-hop _ Comedy rap - 0352.mp3"));
        songLocalRepository.addSong(new Song(1287974581, "Whatever It Takes",
                artistLocalRepository.getArtistByName("Imagine Dragons").getArtistId(),
                219, MusicGender.ROCK, "Whatever It Takes - Imagine Dragons - Evolve - 2017 - Pop rock - 0339.mp3"));
        songLocalRepository.addSong(new Song(614172035, "Believer",
                artistLocalRepository.getArtistByName("Imagine Dragons").getArtistId(),
                204, MusicGender.ROCK, "Believer - Imagine Dragons - Evolve - 2017 - Pop rock _ Alt rock - 0324.mp3"));
        songLocalRepository.addSong(new Song(494087492, "Demons",
                artistLocalRepository.getArtistByName("Imagine Dragons").getArtistId(),
                177, MusicGender.ROCK, "Demons - Imagine Dragons - Night Visions - 2012 - Pop rock _ Indie pop - 0257.mp3"));
        songLocalRepository.addSong(new Song(515539482, "Not Today",
                artistLocalRepository.getArtistByName("Imagine Dragons").getArtistId()
                , 238, MusicGender.ROCK, "Not Today - Imagine Dragons - Me Before You - 2016 - Pop rock - 0358.mp3"));

    }

    public static void addUser (UserService userService ){

        userService.addUser(232928320,"marion", "marion","marion", PlanEnum.FREE);
        userService.addUser(1726370281, "florent", "florent","florent",PlanEnum.PREMIUM);
        userService.addUser(1,"admin", "admin", "admin", PlanEnum.PREMIUM);

    }

    public static void addPlaylist(PlaylistLocalRepository playlistLocalRepository) {


        playlistLocalRepository.savePlaylist(new Playlist("POP Vibes",
                new LinkedList<>(Arrays.asList(1986076679, 2084461505, 1988790520, 700468481, 998984026, 1290739974)),
                1, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Rock Legends",
                new LinkedList<>(Arrays.asList(243871940, 1824616046, 1287974581, 614172035, 494087492, 515539482)),
                1, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Amy Forever",
                new LinkedList<>(Arrays.asList(1108071776, 342105258, 625427469, 661206135)),
                1, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Imagine This",
                new LinkedList<>(Arrays.asList(1287974581, 614172035, 494087492, 515539482)),
                1726370281, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Quick Hits",
                new LinkedList<>(Arrays.asList(625427469, 1280045910, 354322599, 1925355941, 661206135, 1252829874, 494087492)),
                1, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Girls",
                new LinkedList<>(Arrays.asList(1108071776, 342105258, 625427469, 661206135, 1986076679, 2084461505, 1290739974, 1951451340, 469321884, 1252829874, 1988790520, 700468481)),
                232928320, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Boys",
                new LinkedList<>(Arrays.asList(325561970, 321324189, 521970022, 1280045910, 719812166, 243871940, 1824616046, 1287974581, 614172035, 494087492, 515539482, 998984026)),
                232928320, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Soul & RnB Grooves",
                new LinkedList<>(Arrays.asList(1108071776, 342105258, 625427469, 1280045910, 1951451340, 719812166, 661206135)),
                1, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Dance Floor",
                new LinkedList<>(Arrays.asList(325561970, 1252829874, 321324189, 521970022)),
                232928320, PlaylistEnum.PRIVATE));

        playlistLocalRepository.savePlaylist(new Playlist("Random Favorites",
                new LinkedList<>(Arrays.asList(1280045910, 1951451340, 1252829874, 719812166, 521970022)),
                1726370281, PlaylistEnum.PUBLIC));

        playlistLocalRepository.savePlaylist(new Playlist("Before 2000",
                new LinkedList<>(Arrays.asList(325561970, 354322599, 1925355941, 700468481)),
                1726370281, PlaylistEnum.PRIVATE));

        playlistLocalRepository.savePlaylist(new Playlist("AllSongs", 914903479,
                new LinkedList<>(Arrays.asList(
                        1108071776, 1986076679, 2084461505, 342105258, 625427469, 325561970, 321324189, 1280045910,
                        354322599, 1925355941, 1951451340, 243871940, 1988790520, 719812166, 700468481, 469321884,
                        1824616046, 998984026, 661206135, 1290739974, 1252829874, 521970022, 1287974581, 614172035,
                        494087492, 515539482)),
                1, PlaylistEnum.PUBLIC));
        }
    }
