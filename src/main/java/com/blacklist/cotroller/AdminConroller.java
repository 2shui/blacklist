package com.blacklist.cotroller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.server.IndexServer;
import com.blacklist.service.TopicService;
import com.blacklist.utils.LuceneIKUtil;

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
	
	/**
	 * @param req
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/fullIndex")
	public BaseResponse fullIndex(BaseRequest req, HttpServletRequest request) {
		String clientIp = getIpAddr(request);
		if("127.0.0.1".equals(clientIp) || "localhost".equals(clientIp) || "0:0:0:0:0:0:0:1".equals(clientIp)) {
			try {
				LuceneIKUtil.getInstance().createIndex(indexServer.build(topicService.getAll()), true);
			} catch (Exception e) {
				log.error("rebuild index error:{}", e);
			}
		}
		return BaseResponse.success(getIpAddr(request));
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
