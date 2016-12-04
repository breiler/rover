package se.cag.labs.ro.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import se.cag.labs.ro.eventbus.EventBusWebSocketHandler;

@Configuration
@Log4j
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    @Autowired
    private EventBusWebSocketHandler eventBusSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(eventBusSocketHandler, EventBusWebSocketHandler.CHANNEL_NAME)
                .setAllowedOrigins("*");

        registry.addHandler(eventBusSocketHandler, EventBusWebSocketHandler.CHANNEL_NAME_SOCKJS)
                .setAllowedOrigins("*")
                .withSockJS();
    }

}