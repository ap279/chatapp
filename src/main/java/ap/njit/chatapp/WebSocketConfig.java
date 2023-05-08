package ap.njit.chatapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private CustomHttpSessionHandshakeInterceptor customHttpSessionHandshakeInterceptor;
    @Autowired
    private HttpSessionManager httpSessionManager;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(webSocketSessionManager, httpSessionManager), "/chat")
                .addInterceptors(customHttpSessionHandshakeInterceptor);
    }
}

