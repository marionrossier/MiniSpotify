package serverSide.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import serverSide.repoBack.BackPlaylistRepo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class SocketServer {
    private static final int PORT = 45000;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🎵 MiniSpotify Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("📡 Client connected");

                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String jsonRequest = in.readLine();
            Map<String, Object> request = mapper.readValue(jsonRequest, Map.class);

            String responseJson = BackPlaylistRepo.handleRequest(request);
            out.write(responseJson);
            out.newLine();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
