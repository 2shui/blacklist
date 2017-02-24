package com.blacklist.filter;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.blacklist.cotroller.help.WsAccountNumHelp;

@Component
public class WsDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		WsAccountNumHelp.subtract();
	}

}
