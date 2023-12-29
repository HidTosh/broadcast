package com.boadcast.api.handler;

import com.boadcast.api.dto.UserInfosWsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
public class WebSocketChatEventListener {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("New web socket connection received");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> userInfos = headerAccessor.getSessionAttributes();
        String username = (String) userInfos.get("username");

        if(username != null) {
            UserInfosWsDto user = new UserInfosWsDto(
                (Integer) userInfos.get("user_id"),
                (String) userInfos.get("uuid"),
                username,
                (String) userInfos.get("type"),
                false
            );
            messagingTemplate.convertAndSend("/topic/messages", user);
        }
    }
}
