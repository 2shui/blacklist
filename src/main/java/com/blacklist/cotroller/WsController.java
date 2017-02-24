package com.blacklist.cotroller;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.alibaba.druid.util.StringUtils;
import com.blacklist.bean.WSRequestMessage;
import com.blacklist.bean.WSResponseMessage;
import com.blacklist.cotroller.help.WsAccountNumHelp;
import com.blacklist.domain.Chat;
import com.blacklist.repo.ChatRepo;
import com.eastrobot.ask.sdk.AskRequest;
import com.eastrobot.ask.sdk.AskResponse;
import com.eastrobot.ask.sdk.AskService;
import com.eastrobot.ask.sdk.CloudNotInitializedException;
import com.eastrobot.ask.sdk.CloudServiceFactory;
import com.eastrobot.ask.utils.Constant;

@Controller
public class WsController {
	private static Logger log = LoggerFactory.getLogger(WsController.class);
	@Autowired
	private ChatRepo chatRepo;
	
	//http://blog.csdn.net/yingxiake/article/details/51224569  , Principal principal
	//http://blog.csdn.net/u012702547/article/details/53835453  for Security
	/**
	 * 用户发消息，广播所有用户
	 * */
	@MessageMapping("/say")
	@SendTo("/topic/resay")
	public WSResponseMessage say(WSRequestMessage req) {
		Chat chat = new Chat();
		chat.setMsg(req.getMsg());
		chat.setNickName(req.getUid());
		chat.setCreateTime(new Date());
		chatRepo.saveAndFlush(chat);
		return new WSResponseMessage(req.getMsg(), req.getUid());
	}
	
	/**
	 * 用户发消息，机器人自动回复，单播
	 * */
	@MessageMapping("/get")
	@SendToUser("/topic/getting")
	public WSResponseMessage get(WSRequestMessage req) {
		boolean noneUser = WsAccountNumHelp.get() < 3;
		boolean shortUser = WsAccountNumHelp.get() < 8 && new Random().nextBoolean();
		boolean isEmpty = StringUtils.isEmpty(req.getMsg());
		if((noneUser || shortUser) && !isEmpty) {
			String appKey = "UQRBtq0bABWE";
			String appSecret = "C1OwfTVWafqVZ1vbZUWc";
			
			AskRequest askRequest = new AskRequest(appKey, appSecret, req.getMsg(),
					Constant.SENIOR_TYPE, null, Constant.WEIXIN_PLATFORM);
			AskService askService = CloudServiceFactory.getInstance()
					.createAskService();
			askService.init(null);
			AskResponse askResponse = null;
			try {
				askResponse = askService.ask(askRequest);
				Chat answer = new Chat();
				answer.setMsg(askResponse.getContent());
				answer.setNickName("机智的小黑");
				answer.setFromUser(req.getUid());
				answer.setCreateTime(new Date());
				chatRepo.saveAndFlush(answer);
				
				return new WSResponseMessage(askResponse.getContent(), "机智的小黑");
			} catch (CloudNotInitializedException e) {
				log.error("xiaoi error:{}", e);
			}
		}
		return new WSResponseMessage("", "");
	}
}
