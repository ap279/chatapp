//package ap.njit.chatapp;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class ChatWebSocketController {
//
//    @MessageMapping("/test")
//    @SendTo("/websocket")
//    public String handleChatWebSocketMessage(String message) throws Exception {
//        // process message here
//        return message;
//    }
//}
