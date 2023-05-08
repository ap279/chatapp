package ap.njit.chatapp;


import ap.njit.chatapp.controllers.MainController;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;
    private final HttpSessionManager httpSessionManager;

    private final MainController mainController;

    @Autowired
    public ChatWebSocketHandler(WebSocketSessionManager sessionManager, HttpSessionManager httpSessionManager, MainController mainController) {
        this.sessionManager = sessionManager;
        this.httpSessionManager = httpSessionManager;
        this.mainController = mainController;
    }


    public List<WebSocketSession> getConnectedSessions() {
        return connectedSessions;
    }

    private final List<WebSocketSession> connectedSessions = new ArrayList<>();

    public List<HttpSession> getConnectedHttpSessions() {
        return connectedHttpSessions;
    }

    private final List<HttpSession> connectedHttpSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
        sessionManager.addSession(session);
        // Sending message to all other clients by adding them to this list
        connectedSessions.add(session);
        httpSessionManager.addSession(httpSession);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Send to a new endpoint specifically for display

        // when message is received, send it to all connected sessions
        for (WebSocketSession s: connectedSessions) {
            if (s.isOpen()) {
                s.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove session from connected session list once closed
        // Maybe do some other session handling stuff here!
        sessionManager.removeSession(session);
        connectedSessions.remove(session);
    }

    public WebSocketSessionManager getSessionManager() {
        return sessionManager;
    }
}
