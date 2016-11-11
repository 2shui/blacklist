package com.blacklist.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseResponse;
import com.blacklist.domain.Curl;
import com.blacklist.repo.CurlRepo;

/**
 * 描述: urlController
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016年11月11日14:23:24
 */
//@RestController
@SpringBootApplication
@RequestMapping("/curl")
public class CurlConroller {
	@Autowired
	CurlRepo curlRepo;

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping("/{id}")
	public String index(@PathVariable("id") Long id) {
		Curl url = curlRepo.findOne(id);
		return "redirect:" + url.getUrlValue();
	}

}
