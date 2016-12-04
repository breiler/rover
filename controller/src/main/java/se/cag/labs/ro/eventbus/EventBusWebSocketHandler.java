package se.cag.labs.ro.eventbus;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import se.cag.labs.ro.MotorService;
import se.cag.labs.ro.bean.Movement;

@Log4j
@Component
public class EventBusWebSocketHandler extends TextWebSocketHandler {

    public static final String CHANNEL_NAME = "/api/eventbus";
    public static final String CHANNEL_NAME_SOCKJS = "/api/eventbus/sockjs";
    private Gson gson = new Gson();

    @Autowired
    private MotorService motorService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            Movement movement = gson.fromJson(message.getPayload(), Movement.class);
            if (movement.isFire()) {
                motorService.stopMotors();
                motorService.setLaser(true);
            } else {
                motorService.setLaser(false);
                motorService.setLeftSpeed((int) Math.round(255 * movement.getLeft()));
                motorService.setRightSpeed((int) Math.round(255 * movement.getRight()));
            }
        } catch (Throwable e) {
            log.error(e);
            throw e;
        }
    }
}