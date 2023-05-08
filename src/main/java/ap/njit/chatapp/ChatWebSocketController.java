package ap.njit.chatapp;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatWebSocketController {

    @MessageMapping("/chat/{room}")
    public String handleChatWebSocketMessage(@DestinationVariable String room, String message) throws Exception {
        // process message here
        return message;
    }
}
