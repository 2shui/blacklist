package com.blacklist.cotroller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.config.GaterrConfig;


/**
 * @author 
 *
 */
@RestController
@RequestMapping("/gaterr")
public class WaterConroller {
	@RequestMapping(value = "/search")
	public BaseResponse search(BaseRequest req, String key, Integer page) {
		ResultBean result = new ResultBean();
		try {
			String uri = GaterrConfig.getGurl()+"&q="+key+"&start="+((null==page||page<1)?0:10*page);
			HttpResponse resp = HttpClients.createDefault().execute(new HttpGet(uri));
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				JSONObject obj = JSONObject.parseObject(EntityUtils.toString(resp.getEntity()));
				Integer count = Integer.parseInt(obj.getJSONObject("cursor").getString("estimatedResultCount"));
				Integer pageNum = count>100?10:(count%10==0?count/10:count/10 +1);
				result.pageNum = pageNum;
				JSONArray results = obj.getJSONArray("results");
				List<Movie> list = new ArrayList<>();
				JSONObject tmp;
				for(int i=0;i<results.size();i++) {
					Movie movie = new Movie();
					tmp = results.getJSONObject(i);
					movie.content = tmp.getString("content")+"gaterr.com";
					movie.url = tmp.getString("url");
					list.add(movie);
				}
				result.list = list;
			}
		} catch (Exception e) {
			result = null;
		}
		return BaseResponse.success(req.getTsno()).setResponse(result);
	}
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
class ResultBean {
	int pageNum;
	List<Movie> list;
}
class Movie {
	String content;
	String url;
}