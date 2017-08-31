package com.blacklist.cotroller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.domain.Water;
import com.blacklist.repo.WaterRepo;
import com.blacklist.service.WaterService;
import com.blacklist.utils.MD5Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author 
 *
 */
@RestController
@RequestMapping("/water")
public class WaterConroller {
//	@Autowired
//	WaterService waterService;
//	
//	@RequestMapping(value = "/pull")
//	public BaseResponse pull(BaseRequest req, Long max, Long min) {
//		min = null == min ? 0 : min;
//		max = null == max ? 0 : (max < min ? min : max);
//		return BaseResponse.success(req.getTsno()).setResponse(waterService.getList(max, min, 10));
//	}
//	
//	@RequestMapping(value = "/push")
//	public BaseResponse push(BaseRequest req, String waters, String auto) {
//		if(!MD5Util.bytesToMD5(req.getTsno().getBytes()).equals(auto)) {
//			return BaseResponse.forbidden(req.getTsno(), "无效请求");
//		}
//		Type type = new TypeToken<ArrayList<Water>>(){}.getType();
//		List<Water> list = new Gson().fromJson(waters, type);
//		waterService.save(list);
//		return BaseResponse.success(req.getTsno());
//	}
}
