package ap.njit.chatapp;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HttpSessionManager {

    public final Map<String, HttpSession> sessions = new HashMap<>();

    public void addSession(HttpSession session) {
        // Use a ConcurrentWebSocketSessionDecorator to make the session thread-safe
        sessions.put(getUserIdentifier(session), session);
    }

    public void removeSession(HttpSession session) {
        sessions.remove(getUserIdentifier(session));
    }

    public HttpSession getSession(String userIdentifier) {
        return sessions.get(userIdentifier);
    }

    private String getUserIdentifier(HttpSession session) {
        // Replace this with your own logic for identifying the user
        // Here, we simply use the session ID
        return session.getId();
    }
}
