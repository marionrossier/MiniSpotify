package serverSide.socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import serverSide.repoBack.*;

import static clientSide.services.PrintHelper.*;


public class SocketServer {
    private int Port = 45000;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BackPlaylistRepo backPlaylistRepo;
    private final BackUserRepo backUserRepo;
    private final BackSongRepo backSongRepo;
    private final BackArtistRepo backArtistRepo;

    public void setPort(int port) {
        this.Port = port;
    }

    public void socketServerMain() {
        try (ServerSocket serverSocket = new ServerSocket(this.Port)) {
            printLNSystem("🎵 MiniSpotify Server is listening on port " + Port);

            while (true) {
                Socket socket = serverSocket.accept();
//                printLNSystem("📡 Client connected");

                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketServer(BackUserRepo backUserRepo, BackPlaylistRepo backPlaylistRepo,
                        BackSongRepo backSongRepo, BackArtistRepo backArtistRepo) {
        this.backPlaylistRepo = backPlaylistRepo;
        this.backUserRepo = backUserRepo;
        this.backSongRepo = backSongRepo;
        this.backArtistRepo = backArtistRepo;
    }

    private void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String jsonRequest = in.readLine();
            Map request = mapper.readValue(jsonRequest, Map.class);
            String command = (String) request.get("command");

            @SuppressWarnings("unchecked")
            String responseJson = switch (command) {
                // Playlist
                case "getPlaylistById", "getPlaylistByName", "getAllPlaylists",
                     "deletePlaylistById", "savePlaylist", "getPlaylistStatus", "getTemporaryPlaylistOfCurrentUser"
                        -> backPlaylistRepo.handleRequest(request);

                // Artist
                case "getAllArtists", "getArtistById", "getArtistByName",
                     "getArtistBySongId", "addArtist", "saveArtist"
                        -> backArtistRepo.handleRequest(request);

                // Song 🎶
                case "getAllSongs", "getSongById", "getSongsByTitle",
                     "getSongsByArtist", "getSongsByGender", "addSong"
                        -> backSongRepo.handleRequest(request);

                // 👤 User
                case "getAllUsers", "getUserById", "getUserByPseudonymLogin", "getUserByPseudonym", "saveUser",
                     "addPlaylistToUser", "addFriendToUser", "deleteFriendFromUser"
                        -> backUserRepo.handleRequest(request);

                default -> "{\"status\": \"ERROR\", \"message\": \"Unknown command at server switch\"}";
            };


            out.write(responseJson);
            out.newLine();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
