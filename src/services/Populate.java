package services;

import data.entities.Artist;
import data.entities.MusicGender;
import data.entities.Song;
import data.storage.ArtistRepository;
import data.storage.SongRepository;

public class Populate {
    public static void main(String[] args) {
        ArtistPopulate();
        SongPopulate();
    }

    public static void ArtistPopulate() {
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

    public static void SongPopulate(){
        SongRepository repository = new SongRepository();

        repository.addSong(new Song("Rehab", "Amy Winehouse", "Back to Black", 3.34, MusicGender.SOUL_RNB, "Rehab - Amy Winehouse - Back to Black - 2006 - Soul _ R&B - 0334.mp3"));
        repository.addSong(new Song("Judas", "Lady Gaga", "Born This Way", 4.09, MusicGender.POP, "Judas - Lady Gaga - Born This Way - 2011 - Pop _ Electro-pop - 0409.mp3"));
        repository.addSong(new Song("Bloody Mary", "Lady Gaga", "Born This Way", 4.05, MusicGender.POP, "Bloody Mary - Lady Gaga - Born This Way - 2011 - Pop _ Dark pop - 0405.mp3"));
        repository.addSong(new Song("You Know I'm No Good", "Amy Winehouse", "Back to Black", 4.17, MusicGender.SOUL_RNB, "You Know I'm No Good - Amy Winehouse - Back to Black - 2006 - Soul _ Jazz - 0417.mp3"));
        repository.addSong(new Song("Me & Mr. Jones", "Amy Winehouse", "Back to Black", 2.33, MusicGender.SOUL_RNB, "Me & Mr. Jones - Amy Winehouse - Back to Black - 2006 - Soul _ Jazz - 0233.mp3"));
        repository.addSong(new Song("Sunny", "Boney M.", "Sunny", 3.58, MusicGender.DISCO, "Sunny - Boney M. - Sunny (Official)... - 1976 - Disco - 0358.mp3"));
        repository.addSong(new Song("Can't Hold Us", "Macklemore", "The Heist", 4.18, MusicGender.HIP_HOP, "Can't Hold Us - Macklemore & Ryan Lewis - The Heist - 2012 - Hip-hop - 0418.mp3"));
        repository.addSong(new Song("Come When I Call", "John Mayer", "Where the Light Is", 2.57, MusicGender.SOUL_RNB, "Come When I Call - John Mayer - Where the Light Is - 2008 - Blues Rock - 0257.mp3"));
        repository.addSong(new Song("Viens à Saint Germain", "Dany Brillant", "Viens à Saint-Germain", 2.57, MusicGender.FRENCH_VARIETY, "Viens à Saint Germain - Dany Brillant - Viens à Saint-Germain - 1992 - Chanson française _ Swing - 0257.mp3"));
        repository.addSong(new Song("Suzette", "Dany Brillant", "C'est ça qui est bon", 2.57, MusicGender.FRENCH_VARIETY, "Suzette - Dany Brillant - C'est ça qui est bon - 1991 - Chanson française _ Swing - 0257.mp3"));
        repository.addSong(new Song("Diamonds", "Rihanna", "Unapologetic", 3.45, MusicGender.SOUL_RNB, "Diamonds - Rihanna - Unapologetic - 2012 - Pop _ R&B - 0345.mp3"));
        repository.addSong(new Song("Free Fallin", "John Mayer", "Where the Light Is", 4.03, MusicGender.ROCK, "Free Fallin - John Mayer - Where the Light Is - 2008 - Soft Rock - 0403.mp3"));
        repository.addSong(new Song("Give It 2 Me", "Madonna", "Hard Candy", 4.47, MusicGender.POP, "Give It 2 Me - Madonna - Hard Candy - 2008 - Dance-pop - 0447.mp3"));
        repository.addSong(new Song("I'm Gonna Find Another You", "John Mayer", "Where the Light Is", 4.43, MusicGender.SOUL_RNB, "I'm Gonna Find Another You (Live) - John Mayer - Where the Light Is - 2008 - Blues _ Soul - 0443.mp3"));
        repository.addSong(new Song("La Isla Bonita", "Madonna", "True Blue", 4.01, MusicGender.POP, "La Isla Bonita - Madonna - True Blue - 1986 - Latin pop - 0401.mp3"));
        repository.addSong(new Song("Man Down", "Rihanna", "Loud", 4.27, MusicGender.REGGAE, "Man Down - Rihanna - Loud - 2010 - Reggae fusion - 0427.mp3"));
        repository.addSong(new Song("Time is Running Out", "Muse", "Absolution", 3.56, MusicGender.ROCK, "Time is Running Out - Muse - Absolution - 2003 - Alternative Rock - 0356.mp3"));
        repository.addSong(new Song("De mes propres ailes (Remix)", "Olivier Dion", "Les 3 Mousquetaires", 3.41, MusicGender.POP, "De mes propres ailes (Remix) - Olivier Dion - Les 3 Mousquetaires - 2016 - Pop française - 0341.mp3"));
        repository.addSong(new Song("Fuck Me Pumps", "Amy Winehouse", "Frank", 3.18, MusicGender.SOUL_RNB, "Fuck Me Pumps - Amy Winehouse - Frank - 2003 - Soul _ Jazz - 0318.mp3"));
        repository.addSong(new Song("Poker Face", "Lady Gaga", "The Fame Monster", 3.57, MusicGender.POP, "Poker Face - Lady Gaga - The Fame Monster - 2008 - Electropop - 0357.mp3"));
        repository.addSong(new Song("Right Now", "Rihanna", "Unapologetic", 3.01, MusicGender.ELECTRO, "Right Now - Rihanna & David Guetta - Unapologetic - 2012 - EDM _ Dance-pop - 0301.mp3"));
        repository.addSong(new Song("Thrift Shop", "Macklemore", "The Heist", 3.52, MusicGender.HIP_HOP, "Thrift Shop - Macklemore & Ryan Lewis - The Heist - 2012 - Hip-hop _ Comedy rap - 0352.mp3"));
        repository.addSong(new Song("Whatever It Takes", "Imagine Dragons", "Evolve", 3.39, MusicGender.ROCK, "Whatever It Takes - Imagine Dragons - Evolve - 2017 - Pop rock - 0339.mp3"));
        repository.addSong(new Song("Believer", "Imagine Dragons", "Evolve", 3.24, MusicGender.ROCK, "Believer - Imagine Dragons - Evolve - 2017 - Pop rock _ Alt rock - 0324.mp3"));
        repository.addSong(new Song("Demons", "Imagine Dragons", "Night Visions", 2.57, MusicGender.ROCK, "Demons - Imagine Dragons - Night Visions - 2012 - Pop rock _ Indie pop - 0257.mp3"));
        repository.addSong(new Song("Not Today", "Imagine Dragons", "Me Before You", 3.58, MusicGender.ROCK, "Not Today - Imagine Dragons - Me Before You - 2016 - Pop rock - 0358.mp3"));
    }
}
