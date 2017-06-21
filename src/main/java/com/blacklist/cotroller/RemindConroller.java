package com.blacklist.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.domain.ProgramRemind;
import com.blacklist.service.RemindService;
import com.blacklist.utils.RequestValidator;


/**
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 *
 */
@RestController
@RequestMapping("/remind")
public class RemindConroller {
	@Autowired
	private RemindService remindService;
	
	@RequestMapping(value = "/add")
	public BaseResponse add(BaseRequest req, ProgramRemind remind) {
		if (RequestValidator.stringEmptyValidator(remind.getTitle(),
				remind.getRemindTime(), remind.getTime())) {
			return BaseResponse.forbidden(req.getTsno(), "参数不完整");
		}
		List<ProgramRemind> reminded = remindService.findByStatusAndOpenId(1, remind.getOpenId());
		if (reminded.size() >= 20) {
			return BaseResponse.forbidden(req.getTsno(), "每人最多设置20个提醒");
		}
		remind.setStatus(1);
		remindService.save(remind);
		return BaseResponse.success(req.getTsno());
	}
	
	@RequestMapping(value="/query")
	public BaseResponse get(BaseRequest req, String openId) {
		List<ProgramRemind> reminds = remindService.findByStatusAndOpenId(1, openId);
		return BaseResponse.success(req.getTsno()).setResponse(reminds);
	}
	
	@RequestMapping(value="/del")
	public BaseResponse delete(BaseRequest req, Long id, String openId) {
		if (RequestValidator.nullValueValidator(openId, id)) {
			return BaseResponse.fail(req.getTsno());
		}
		remindService.del(id, openId);
		return BaseResponse.success(req.getTsno());
	}
}
