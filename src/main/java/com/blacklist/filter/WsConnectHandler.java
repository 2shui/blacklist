package com.blacklist.filter;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.blacklist.cotroller.help.WsAccountNumHelp;

@Component
public class WsConnectHandler implements ApplicationListener<SessionConnectEvent> {

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		WsAccountNumHelp.add();
	}

}
