package com.blacklist.cotroller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.domain.TopicReply;
import com.blacklist.domain.enums.TopicReplyEnum;
import com.blacklist.service.TopicReplyService;
import com.blacklist.service.TopicService;
import com.blacklist.utils.RequestValidator;

/**
 * 描述: 回复Controller
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016年9月24日16:24:28
 */
@RestController
@RequestMapping("/reply")
public class ReplyConroller {
	@Autowired
	TopicReplyService topicReplyService;
	@Autowired
	TopicService topicService;
	
	@RequestMapping("/reply")
	public BaseResponse reply(BaseRequest req, TopicReply reply) {
		if (RequestValidator.nullValueValidator(reply.getTopicId())
				|| RequestValidator.stringEmptyValidator(reply.getIp(),
						reply.getCitySN(), reply.getIntro())) {
			return BaseResponse.forbidden(req.getTsno(), "403");
		}
		if (null == topicService.get(reply.getTopicId())) {
			return BaseResponse.forbidden(req.getTsno(), "bad topic");
		}
		reply.setStatus(TopicReplyEnum.Status.NORMAL.getValue());
		reply.setTime(new Date());
		reply.setUpNum(0);
		reply.setDownNum(0);
		return BaseResponse.success(req.getTsno()).setResponse(topicReplyService.addReply(reply));
	}
	
	@RequestMapping("/comments")
	public BaseResponse comments(BaseRequest req, Long id) {
		if (RequestValidator.nullValueValidator(id)) {
			return BaseResponse.forbidden(req.getTsno(), "403");
		}
		return BaseResponse.success(req.getTsno()).setResponse(topicReplyService.getReplys(id));
	}
	
	/**
	 * @param req
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/up")
	public BaseResponse up(BaseRequest req, Long id) {
		if(RequestValidator.nullValueValidator(id)) {
			return BaseResponse.forbidden(req.getTsno(), "参数不完整");
		}
		if(null==topicReplyService.get(id)) {
			return BaseResponse.fail(req.getTsno());
		}
		topicReplyService.up(id);
		return BaseResponse.success(req.getTsno());
	}
	
	@RequestMapping("/down")
	public BaseResponse down(BaseRequest req, Long id) {
		if(RequestValidator.nullValueValidator(id)) {
			return BaseResponse.forbidden(req.getTsno(), "参数不完整");
		}
		if(null==topicReplyService.get(id)) {
			return BaseResponse.fail(req.getTsno());
		}
		topicReplyService.down(id);
		return BaseResponse.success(req.getTsno());
	}
}
