package com.boadcast.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SpringWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enable a simple message broker prefixes to filter destinations targeting the broker
        registry.enableSimpleBroker("/topic");
        // Configure one or more prefixes to filter destinations targeting application
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/ws-api") // Register a STOMP over WebSocket endpoint at the given mapping path.
            .setAllowedOrigins("http://127.0.0.1:8089/") // specifying the origins for which cross-origin requests are allowed from a browser
            .withSockJS(); // Enable SockJS fallback options
    }
}
