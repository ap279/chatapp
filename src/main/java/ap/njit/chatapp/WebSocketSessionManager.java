package ap.njit.chatapp;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    public final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session) {
        // Use a ConcurrentWebSocketSessionDecorator to make the session thread-safe
        WebSocketSession safeSession = new ConcurrentWebSocketSessionDecorator(session, 10000, 1024 * 1024);
        sessions.put(getUserIdentifier(session), safeSession);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(getUserIdentifier(session));
    }

    public WebSocketSession getSession(String userIdentifier) {
        return sessions.get(userIdentifier);
    }

    private String getUserIdentifier(WebSocketSession session) {
        // Replace this with your own logic for identifying the user
        // Here, we simply use the session ID
        return session.getId();
    }
}