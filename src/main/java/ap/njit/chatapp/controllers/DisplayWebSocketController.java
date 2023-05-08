//package ap.njit.chatapp.controllers;
//
//import ap.njit.chatapp.activities.Flip;
//import ap.njit.chatapp.handlers.DisplayWebSocketHandler;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class DisplayWebSocketController {
//
//    @Autowired
//    private DisplayWebSocketHandler displayWebSocketHandler;
//
//    @MessageMapping("/display/{room}")
//    public String handleChatWebSocketMessage(@DestinationVariable String room, String message, WebSocketSession session) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> serialized = mapper.readValue(message, new TypeReference<Map<String, Object>>() {
//        });
//
//        Flip coin = new Flip((String) serialized.get("message"));
//        String result = coin.flip();
//        serialized.put("flip", result);
//
//        String finalMessage = mapper.writeValueAsString(serialized);
//
//        List<WebSocketSession> connectedSessions = this.displayWebSocketHandler.getConnectedSessions();
//
////        for (WebSocketSession s: connectedSessions) {
////            if (s.isOpen()) {
////                s.sendMessage(new WebSocketMessage<String>() {
////                });
////            }
////        }
//
//        return finalMessage;
//
//    }
//}
//
