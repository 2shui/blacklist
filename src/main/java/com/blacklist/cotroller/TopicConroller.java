package com.blacklist.cotroller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.domain.Topic;
import com.blacklist.domain.enums.TopicEnum;
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
	@Autowired TopicService topicService;
	
	/**
	 * @param req
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/add")
	public BaseResponse checkExpert(BaseRequest req, Topic topic) {
		if(!RequestValidator.nullValueValidator(topic.getId())) {
			return BaseResponse.forbidden(req.getTsno(), "非法请求");
		}
		if(RequestValidator.nullValueValidator(topic.getCity(), topic.getCompany(), topic.getSketch(), topic.getIntro(), topic.getContact())){
			BaseResponse.forbidden(req.getTsno(), "必填参数不完整");
		}
		topic.setCreateTime(new Date());
		topic.setStatus(TopicEnum.Status.NORMAL.getValue());
		topicService.add(topic);
		
		return BaseResponse.success(req.getTsno());
	}
	
	
}
