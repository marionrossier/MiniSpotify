package clientSide.socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SocketClient {
    private final String SERVER_ADDRESS = "127.0.0.1";
    private int serverPort = 45000;

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void connect() throws IOException {
        this.socket = new Socket(SERVER_ADDRESS, serverPort);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Map<String, Object> sendRequest(Map<String, Object> requestData) {
        try {
            String json = mapper.writeValueAsString(requestData);
            out.write(json);
            out.newLine();
            out.flush();

            String responseJson = in.readLine();
            return mapper.readValue(responseJson, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to communicate with server", e);
        }
    }

    public void close() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
