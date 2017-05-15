package com.blacklist.cotroller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.domain.enums.TopicEnum;
import com.blacklist.repo.BlogArticleRepo;
import com.blacklist.repo.TopicRepo;
import com.blacklist.server.IndexServer;
import com.blacklist.service.BlogArticleService;
import com.blacklist.service.TopicService;
import com.blacklist.utils.LuceneIKUtil;
import com.eastrobot.ask.sdk.AskRequest;
import com.eastrobot.ask.sdk.AskResponse;
import com.eastrobot.ask.sdk.AskService;
import com.eastrobot.ask.sdk.CloudNotInitializedException;
import com.eastrobot.ask.sdk.CloudServiceFactory;
import com.eastrobot.ask.utils.Constant;

/**
 * 描述: 主题Controller
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016年7月19日 上午9:43:32
 */
@RestController
@RequestMapping("/admin")
public class AdminConroller {
	private Logger log = LoggerFactory.getLogger(AdminConroller.class);
	@Autowired
	IndexServer indexServer;
	@Autowired
	TopicService topicService;
	@Autowired
	BlogArticleService blogArticleService;
	@Autowired
	BlogArticleRepo articleRepo;
	@Autowired
	TopicRepo topicRepo;
	
	@RequestMapping("test")
	@ResponseBody
	public String test(BaseRequest req,String question, HttpServletRequest request) {
		String clientIp = getIpAddr(request);
		if ("127.0.0.1".equals(clientIp) || "localhost".equals(clientIp)
				|| "0:0:0:0:0:0:0:1".equals(clientIp) || "120.24.186.80".equals(clientIp)) {
			
			try {
				LuceneIKUtil.getInstance().createIndex(
						indexServer.build(topicRepo.findByStatus(TopicEnum.Status.NORMAL.getValue())), true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
//			String appKey = "UQRBtq0bABWE";
//			String appSecret = "C1OwfTVWafqVZ1vbZUWc";
//			//String question = "笑话";
//			String exampleFile = "";
//			
//			AskRequest askRequest = new AskRequest(appKey, appSecret, question,
//					Constant.SENIOR_TYPE, null, Constant.WEIXIN_PLATFORM);
//			AskService askService = CloudServiceFactory.getInstance()
//					.createAskService();
//			askService.init(null);
//			AskResponse askResponse = null;
//			try {
//				askResponse = askService.ask(askRequest);
//				return askResponse.getContent();
//			} catch (CloudNotInitializedException e) {
//				e.printStackTrace();
//			}
			
		}
		return "ok";
	}
	
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if(!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if(!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(',');
			if(-1 != index) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}else {
			return request.getRemoteAddr();
		}
	}
}
