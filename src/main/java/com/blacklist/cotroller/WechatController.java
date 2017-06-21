package com.blacklist.cotroller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blacklist.bean.BaseResponse;
import com.blacklist.config.MiniProgramConfig;
import com.blacklist.enums.WechatType;
import com.blacklist.server.IndexServer;
import com.blacklist.server.WechatService;
import com.blacklist.service.TopicService;
import com.blacklist.utils.HttpClient;
import com.blacklist.utils.HttpsClient;

@RestController
@SpringBootApplication
@RequestMapping("/wechat")
@Scope("prototype")
public class WechatController {
	@Autowired
	private WechatService wechatService;
	@Autowired
	IndexServer indexServer;
	@Autowired
	TopicService topicService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void index(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if (isGet) {
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			if (wechatService.validate(signature, timestamp, nonce)) {
				response.getWriter().write(echostr);
			}
		} else {
			Map<String, String> reqMap = wechatService.parseReq(request);
			if ("text".equalsIgnoreCase(reqMap.get("MsgType"))) {
				String input = wechatService.buildResponse(reqMap, WechatType.TEXT);
				response.getWriter().write(input);
			} else if("event".equalsIgnoreCase(reqMap.get("MsgType"))
					&& "subscribe".equalsIgnoreCase(reqMap.get("Event"))){
				String input = wechatService.buildResponse(reqMap, WechatType.EVENT);
				response.getWriter().write(input);
			}else {
				String input = wechatService.buildResponse(reqMap, WechatType.IMAGE);
				response.getWriter().write(input);
			}
		}
	}
	
	@RequestMapping("/miniProgram/onlogin")
	public BaseResponse miniProgram(String code) {
		Map<String, String> param = new HashMap<String, String> ();
		param.put("appid", MiniProgramConfig.appid);
		param.put("secret", MiniProgramConfig.appSecret);
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");
		HttpsClient client = new HttpsClient();
		String res = client.doPost("https://api.weixin.qq.com/sns/jscode2session", param, "utf-8");
		// {"session_key":"lOZrl0l9xXQRybCYMor+0w==","expires_in":7200,"openid":"xxx"}
		JSONObject jsonObject = JSON.parseObject(res);
		return BaseResponse.success(""+new Date().getTime()).setResponse(jsonObject.get("openid"));
	}

}
