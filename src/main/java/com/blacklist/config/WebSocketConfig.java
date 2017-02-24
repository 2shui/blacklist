package com.blacklist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry reg) {
		reg.addEndpoint("/tweet").withSockJS();//.setHandshakeHandler(new MyHandshakeHandler())
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry reg) {
		reg.enableSimpleBroker("/topic");
		reg.setApplicationDestinationPrefixes("/app");
	}
}
