package ap.njit.chatapp.handlers;

import ap.njit.chatapp.ChatWebSocketHandler;
import ap.njit.chatapp.HttpSessionManager;
import ap.njit.chatapp.WebSocketSessionManager;
import ap.njit.chatapp.activities.Flip;
import ap.njit.chatapp.activities.Roll;
import ap.njit.chatapp.controllers.MainController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DisplayWebSocketHandler extends TextWebSocketHandler {

//    @Autowired
//    private ChatWebSocketHandler chatWebSocketHandler;

    public List<WebSocketSession> getConnectedSessions() {
        return connectedSessions;
    }

    private final List<WebSocketSession> connectedSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Sending message to all other clients by adding them to this list
        connectedSessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> serialized = mapper.readValue(payload, new TypeReference<Map<String, Object>>() {
        });

        String serializedMessage = (String) serialized.get("message");

//        chatWebSocketHandler.handleTextMessage(session, message);

        Flip coin = new Flip(serializedMessage);
        String flipResult = coin.flip();

        // Handle roll message here
        String rolled = "";
        if (flipResult == "null") {
            // Then you can roll because it won't update the display twice in one go
            Roll die = new Roll(serializedMessage);
            String rollResult = die.roll();
            rolled = rollResult;
        }

        Map<String, Object> messageMap = Map.of(
                "flip_result", flipResult, "roll_result", rolled
        );

        String messageJson = mapper.writeValueAsString(messageMap);

        TextMessage messageText = new TextMessage(messageJson);
        // Send to a new endpoint specifically for display
        // when message is received, send it to all connected sessions
        for (WebSocketSession s : connectedSessions) {
            if (s.isOpen()) {
                s.sendMessage(messageText);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove session from connected session list once closed
        // Maybe do some other session handling stuff here!
        connectedSessions.remove(session);
    }

}
