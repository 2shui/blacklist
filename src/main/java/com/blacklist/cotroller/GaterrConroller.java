package com.blacklist.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseResponse;
import com.blacklist.server.GaterrServer;

/**
 * 描述: gaterrController
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2017年5月10日17:21:03
 */
@RestController
@RequestMapping("/gaterr")
public class GaterrConroller {
	@Autowired
	GaterrServer gaterrServer;

	/**
	 * @param type
	 * @return
	 */
	@RequestMapping("/{type}")
	public BaseResponse index(@PathVariable("type") String type) {
		return BaseResponse.success("").setResponse(gaterrServer.getRandom(type));
	}
}
