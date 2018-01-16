package com.blacklist.cotroller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.config.FreemarkerConfig;
import com.blacklist.config.WebConfig;
import com.blacklist.domain.Topic;
import com.blacklist.domain.enums.TopicEnum;
import com.blacklist.server.TopicSortServer;
import com.blacklist.service.TopicReplyService;
import com.blacklist.service.TopicService;
import com.blacklist.utils.RequestValidator;

/**
 * 描述: 主题Controller
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016年7月19日 上午9:43:32
 */
@RestController
@RequestMapping("/topic")
public class TopicConroller {
	@Autowired
	TopicService topicService;
	@Autowired
	TopicReplyService topicReplyService;
	@Autowired
	TopicSortServer topicSortServer;
	
	/**
	 * @param req
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/add")
	public BaseResponse add(BaseRequest req, Topic topic) {
		if(!RequestValidator.nullValueValidator(topic.getId())) {
			return BaseResponse.forbidden(req.getTsno(), "非法请求");
		}
		if(RequestValidator.stringEmptyValidator(topic.getCity(), topic.getCompany(), topic.getSketch(), topic.getIntro())){
			return BaseResponse.forbidden(req.getTsno(), "必填参数不完整");
		}
		topic.setCreateTime(new Date());
		topic.setStatus(TopicEnum.Status.NORMAL.getValue());
		topic.setAccessNum(0);
		topic.setEulogyNum(0);
		topic.setRefuteNum(0);
		topic = topicService.add(topic);
		
		return BaseResponse.success(req.getTsno()).setResponse(topic);
	}
	
	@RequestMapping("/s")
	public BaseResponse search(BaseRequest req, String key) {
		if(RequestValidator.stringEmptyValidator(key)) {
			return BaseResponse.forbidden(req.getTsno(), "查询数据为空");
		}
		List<Topic> list = topicService.search(new String[]{WebConfig.id, WebConfig.city, WebConfig.company}, key, 10);
		if(list.size()<1) {
			//list = topicService.findByStatus(1);
		}
		return BaseResponse.success(req.getTsno()).setResponse(list);
	}
	
	@RequestMapping("/details")
	public BaseResponse details(BaseRequest req, Long id) {
		if (RequestValidator.nullValueValidator(id)) {
			return BaseResponse.forbidden(req.getTsno(), "无效的页面");
		}
		Topic topic = topicService.get(id);
		return null == topic ? BaseResponse.empty(req.getTsno(), "无此数据")
				: BaseResponse.success(req.getTsno()).setResponse(topic);
	}
	
	@RequestMapping("/view")
	public BaseResponse view(BaseRequest req, Long id) {
		if (RequestValidator.nullValueValidator(id)) {
			return BaseResponse.forbidden(req.getTsno(), "403");
		}
		topicService.viewPage(id);
		return BaseResponse.success(req.getTsno());
	}
	
	/**
	 * 获取热门爆料
	 * @param req
	 * @return
	 */
	@RequestMapping("/hot")
	public BaseResponse hot(BaseRequest req) {
		List<Topic> list = topicService.getLimit(50, new Sort(Direction.DESC, "id"));
		if (!list.isEmpty())
			list = topicSortServer.sort(list);
		if(list.size() > 5) {
			list = list.subList(0, 5);
		}
		return BaseResponse.success(req.getTsno()).setResponse(list);
	}
	
	/**
	 * 获取相关城市爆料
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping("/associated")
	public BaseResponse associated(BaseRequest req, Long id) {
		Topic topic = topicService.get(id);
		List<Topic> list = null;
		if(null!=topic){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String tempPath = "/detail/##/=.html";
			list = topicService.random(topic.getCity(), 5);
			list.stream().forEach(t->{
				t.setShortPath(tempPath.replace("##", sdf.format(t.getCreateTime())).replace("=", t.getId().toString()));
			});
		}
		return BaseResponse.success(req.getTsno()).setResponse(list);
	}
}
