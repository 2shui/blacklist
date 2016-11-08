package com.blacklist.cotroller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.domain.Feedback;
import com.blacklist.service.FeedbackService;
import com.blacklist.utils.RequestValidator;

/**
 * 描述: 反馈Controller
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016年11月4日16:03:07
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackConroller {
	@Autowired
	FeedbackService feedbackService;
	
	/**
	 * @param req
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/add")
	public BaseResponse up(BaseRequest req, String content, String contact) {
		if (RequestValidator.stringEmptyValidator(content)) {
			return BaseResponse.forbidden(req.getTsno(), "建议内容不能为空");
		}
		Feedback feedback = new Feedback();
		feedback.setContent(content);
		feedback.setContact(contact);
		feedback.setStatus(1);
		feedback.setCreateTime(new Date());
		feedbackService.save(feedback);
		return BaseResponse.success(req.getTsno());
	}
	
}
