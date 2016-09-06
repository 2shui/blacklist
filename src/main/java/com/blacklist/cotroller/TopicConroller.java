package com.blacklist.cotroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;

/**
 * 描述: 主题Controller
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016年7月19日 上午9:43:32
 */
@RestController
@RequestMapping("/topic")
public class TopicConroller {
	/**
	 * @param req
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/add")
	public BaseResponse checkExpert(BaseRequest req) {
		return BaseResponse.success(req.getTsno());
	}
}
