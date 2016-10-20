package com.blacklist.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.service.TopicReplyService;
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
