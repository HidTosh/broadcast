package com.boadcast.api.controller;

import com.boadcast.api.dto.MessageWsDto;
import com.boadcast.api.dto.UserInfosWsDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WsController {

    @MessageMapping("/broadcast.new")
    @SendTo("/topic/messages")
    public UserInfosWsDto newUser(
            @Payload UserInfosWsDto user,
            SimpMessageHeaderAccessor messageHeader
    ) {
        // Set user infos in web socket session
        messageHeader.getSessionAttributes().put("username", user.getUserName());
        messageHeader.getSessionAttributes().put("uuid", user.getUuid());
        messageHeader.getSessionAttributes().put("user_id", user.getUser_id());
        messageHeader.getSessionAttributes().put("type", user.getType());

        return user;
    }

    @MessageMapping("/broadcast.message")
    @SendTo("/topic/messages")
    public MessageWsDto send(MessageWsDto message) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return new MessageWsDto(
            message.getUserName(),
            message.getSender(),
            message.getReceiver(),
            message.getContent(),
            message.getType(),
            dtf.format(now)
        );
    }
}
