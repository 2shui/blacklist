package com.blacklist.cotroller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.blacklist.bean.BaseRequest;
import com.blacklist.bean.BaseResponse;
import com.blacklist.config.FreemarkerConfig;
import com.blacklist.domain.BlogArticle;
import com.blacklist.service.BlogArticleService;
import com.blacklist.utils.FreemarkerUtils;
import com.blacklist.utils.MD5Util;
import com.blacklist.utils.MapperBuilder;
import com.blacklist.utils.RequestValidator;

/**
 * 描述: blogController
 *
 * @author <a href="mailto:admin@itblacklist.com">admin</a>
 * @createDate 2016-11-15 17:21:15
 */
@SpringBootApplication
@RequestMapping("/blog")
public class BlogConroller {
	@Autowired
	BlogArticleService blogArticleService;
	
	@RequestMapping("ln")
	@ResponseBody
	public BaseResponse listNum(BaseRequest req) {
		List<BlogArticle> articles = blogArticleService.findAll(new Sort(
				Direction.DESC, "id"));
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		articles.forEach(article -> res.add(MapperBuilder.buildMap(article, "id", "accessNum")));
		return BaseResponse.success(req.getTsno()).setResponse(res);
	}
	
	@RequestMapping("/uploadImg")
	@ResponseBody
	public String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		String fileName = file.getOriginalFilename();
		String type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf("."), fileName.length()):null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String now = df.format(new Date());
		String targetName = new Date().getTime() + type;
		File f = new File(FreemarkerConfig.imgPath + "/" + now, targetName);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(file.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return now + "/" + targetName;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public BaseResponse add(BaseRequest req, BlogArticle article) {
		if(RequestValidator.stringEmptyValidator(article.getContent(), article.getAuthor())) {
			return BaseResponse.forbidden(req.getTsno(), "参数不完整");
		}
		article.setAccessNum(1);
		article.setCreateTime(new Date());
		blogArticleService.save(article);
		return BaseResponse.success(req.getTsno());
	}
	
	@RequestMapping("/a")
	@ResponseBody
	public String a(HttpServletRequest request) {
		String res = MD5Util.bytesToMD5(("" + new Date().getTime()).getBytes());
		request.getSession().setAttribute(res, true);
		return res;
	}

	@RequestMapping("/b/{pwd}")
	@ResponseBody
	public ModelAndView b(@PathVariable("pwd") String pwd, Map<String, Object> model, HttpServletRequest request) {
		if (null != request.getSession().getAttribute(pwd)) {
			model.put("imgSite", FreemarkerConfig.imgSite);
			return new ModelAndView("addBlog");
		}
		return new ModelAndView();
	}

}
