package ap.njit.chatapp;

import ap.njit.chatapp.controllers.MainController;
import ap.njit.chatapp.handlers.DisplayWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private CustomHttpSessionHandshakeInterceptor customHttpSessionHandshakeInterceptor;
    @Autowired
    private HttpSessionManager httpSessionManager;
    @Autowired
    private MainController mainController;

    @Autowired
    private DisplayWebSocketHandler displayWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(webSocketSessionManager, httpSessionManager, mainController), "/chat/{room}")
                .addInterceptors(customHttpSessionHandshakeInterceptor)
                .setAllowedOrigins("*");

        registry.addHandler(displayWebSocketHandler, "/display/{room}")
                .setAllowedOrigins("*");

    }
}

