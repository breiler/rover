package se.cag.labs.ro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import se.cag.labs.ro.eventbus.EventChannelSocketHandler;

@Configuration
public class WebsocketConfig implements WebSocketConfigurer {
    @Autowired
    private EventChannelSocketHandler eventBus;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(eventBus, EventChannelSocketHandler.CHANNEL_NAME)
                .setAllowedOrigins("*")
                .withSockJS();
    }

}