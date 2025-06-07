package serverSide.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.entities.User;
import serverSide.repoBack.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class SocketServer {
    private int port = 45000;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BackPlaylistRepo backPlaylistRepo;
    private final BackUserRepo backUserRepo;
    private final BackSongRepo backSongRepo;
    private final BackArtistRepo backArtistRepo;

    public SocketServer(BackUserRepo backUserRepo, BackPlaylistRepo backPlaylistRepo,
                        BackSongRepo backSongRepo, BackArtistRepo backArtistRepo) {
        this.backPlaylistRepo = backPlaylistRepo;
        this.backUserRepo = backUserRepo;
        this.backSongRepo = backSongRepo;
        this.backArtistRepo = backArtistRepo;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void socketServerMain() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("MiniSpotify Server listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        String pseudonym = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            boolean authenticated = false;
            String jsonRequest;

            while ((jsonRequest = in.readLine()) != null) {
                Map<String, Object> request = mapper.readValue(jsonRequest, Map.class);
                String command = (String) request.get("command");

                if (!authenticated) {
                    if ("login".equalsIgnoreCase(command)) {
                        pseudonym = (String) request.get("userPseudonym");
                        String password = (String) request.get("password");

                        boolean success = backUserRepo.authenticate(pseudonym, password);
                        if (success) {
                            authenticated = true;
                            out.write("{\"status\": \"OK\", \"message\": \"Login successful\"}");
                            System.out.println("User logged in: " + pseudonym);
                        } else {
                            out.write("{\"status\": \"ERROR\", \"message\": \"Invalid credentials\"}");
                            System.err.println("Login failed for pseudonym: " + pseudonym);
                        }
                        out.newLine();
                        out.flush();
                        continue;
                    } else if ("updateOrInsertUser".equalsIgnoreCase(command)) {
                        // Autoriser l'inscription même avant authentification
                        String responseJson = backUserRepo.handleRequest(request);
                        out.write(responseJson);
                        out.newLine();
                        out.flush();
                        continue;
                    } else {
                        out.write("{\"status\": \"ERROR\", \"message\": \"Please login first\"}");
                        out.newLine();
                        out.flush();
                        continue;
                    }
                }

                if ("disconnect".equalsIgnoreCase(command)) {
                    out.write("{\"status\": \"OK\", \"message\": \"Disconnected\"}");
                    out.newLine();
                    out.flush();
                    break;
                }

                String responseJson = switch (command) {
                    case "getPlaylistById", "getPlaylistByName", "getAllPlaylists",
                         "deletePlaylistById", "updateOrInsertPlaylist", "getPlaylistStatus", "getTemporaryPlaylistOfCurrentUser"
                            -> backPlaylistRepo.handleRequest(request);

                    case "getAllArtists", "getArtistById", "getArtistByName",
                         "getArtistBySongId", "addArtist", "updateOrInsertArtist"
                            -> backArtistRepo.handleRequest(request);

                    case "getAllSongs", "getSongById", "getSongsByTitle",
                         "getSongsByArtist", "getSongsByGender", "addSong"
                            -> backSongRepo.handleRequest(request);

                    case "getAllUsers", "getUserById", "getUserByPseudonym", "updateOrInsertUser"
                            -> backUserRepo.handleRequest(request);

                    default -> "{\"status\": \"ERROR\", \"message\": \"Unknown command\"}";
                };


                out.write(responseJson);
                out.newLine();
                out.flush();
            }

        } catch (SocketException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client disconnected: " + (pseudonym != null ? pseudonym : "unknown"));
        }
    }
}
