package clientSide.socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SocketClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 45000;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> sendRequest(Map<String, Object> requestData) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String json = mapper.writeValueAsString(requestData);
            out.write(json);
            out.newLine();
            out.flush();

            String responseJson = in.readLine();
            return mapper.readValue(responseJson, Map.class);

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to connect to server", e);
        }
    }
}
