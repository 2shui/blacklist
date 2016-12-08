package com.blacklist.cotroller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.config.FreemarkerConfig;
import com.blacklist.domain.BlogArticle;
import com.blacklist.server.IndexServer;
import com.blacklist.service.BlogArticleService;
import com.blacklist.service.TopicService;
import com.blacklist.utils.FreemarkerUtils;
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
	@Autowired
	BlogArticleService blogArticleService;
	
	@RequestMapping("test")
	@ResponseBody
	public String test(BaseRequest req, HttpServletRequest request) {
		String clientIp = getIpAddr(request);
		if ("127.0.0.1".equals(clientIp) || "localhost".equals(clientIp)
				|| "0:0:0:0:0:0:0:1".equals(clientIp) || "120.24.186.80".equals(clientIp)) {
			List<BlogArticle> articles = blogArticleService.findAll(new Sort(Direction.DESC, "id"));
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			articles.forEach(article->{
				article.setSource(df.format(article.getCreateTime()));
			});
			Map<String, Object> ftlMap = new HashMap<String, Object>();
			ftlMap.put("articles", articles);
			ftlMap.put("site", FreemarkerConfig.site);
			FreemarkerUtils.analysisTemplate(null, "index.html", ftlMap, "blogList.ftl", null);
		}
		return "ok";
	}
	
	/**
	 * @param req
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/fullIndex")
	public BaseResponse fullIndex(BaseRequest req, HttpServletRequest request) {
		String clientIp = getIpAddr(request);
		if ("127.0.0.1".equals(clientIp) || "localhost".equals(clientIp)
				|| "0:0:0:0:0:0:0:1".equals(clientIp) || "120.24.186.80".equals(clientIp)) {
			try {
				LuceneIKUtil.getInstance().createIndex(
						indexServer.build(topicService.findByStatus(1)), true);
				log.info("fullIndex success...");
			} catch (Exception e) {
				log.error("rebuild index error:{}", e);
			}
		}
		return BaseResponse.success(clientIp);
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
