package clientSide.services;

import clientSide.socket.SocketClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthentificationService {
    private final SocketClient socketClient;

    public AuthentificationService(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public boolean login(String pseudonym, String password) {
        try {
            socketClient.close();    // <-- ferme ancienne socket
            socketClient.connect();  // <-- ouvre une nouvelle socket
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to reconnect to server", e);
        }

        Map<String, Object> request = new HashMap<>();
        request.put("command", "login"); // Commande spéciale pour le serveur
        request.put("userPseudonym", pseudonym);
        request.put("password", password);

        Map<String, Object> response = socketClient.sendRequest(request);
        return "OK".equals(response.get("status"));
    }

    public void logout() {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("command", "disconnect");

            socketClient.sendRequest(request);
            socketClient.close();
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to logout", e);
        }
    }

}
