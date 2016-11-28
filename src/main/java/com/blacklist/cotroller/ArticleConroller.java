package com.blacklist.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.service.BlogArticleService;
import com.blacklist.utils.RequestValidator;

/**
 * 描述: blogController
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016-11-15 17:21:15
 */
@RestController
@RequestMapping("/article")
public class ArticleConroller {
	@Autowired
	BlogArticleService blogArticleService;
	
	@RequestMapping("/view")
	public BaseResponse view(BaseRequest req, Long id) {
		if (RequestValidator.nullValueValidator(id)) {
			return BaseResponse.forbidden(req.getTsno(), "403");
		}
		blogArticleService.view(id);
		return BaseResponse.success(req.getTsno());
	}

}
