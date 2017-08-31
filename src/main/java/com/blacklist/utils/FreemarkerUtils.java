package com.blacklist.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacklist.config.FreemarkerConfig;
import com.blacklist.utils.freemarker.DateFormatFunc;
import com.blacklist.utils.freemarker.JsoupContendFunc;
import com.blacklist.utils.freemarker.SubStringFunc;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemarker生成html
 * */
public class FreemarkerUtils {
	private static Logger log = LoggerFactory.getLogger(FreemarkerUtils.class);
	private static Configuration config;
	private static Template template;

	private FreemarkerUtils() {
	}

	public static void init(File file) {
		if (null == config) {
			config = new Configuration();
			try {
				config.setDirectoryForTemplateLoading(file);
				config.setObjectWrapper(new DefaultObjectWrapper());
				config.setSharedVariable("subStr", new SubStringFunc());
				config.setSharedVariable("jsoup", new JsoupContendFunc());
				config.setSharedVariable("dateFormat", new DateFormatFunc());
			} catch (IOException e) {
				log.error("freemarker init config error:" + e);
			}
		}
	}

	public static void initTemplate(String fileName, String codeType) {
		codeType = null == codeType ? "UTF-8" : codeType;
		if (null == config) {
			init(new File(FreemarkerConfig.ftlPath));
		}
		try {
			template = config.getTemplate(fileName, codeType);
		} catch (IOException e) {
			log.error("freemarker initTemplate template error:" + e);
		}
	}

	public static void analysisTemplate(String targetPath, String targetFile,
			Map<?, ?> data, String templateFile, String codeType,
			Boolean absolutePath) {
		templateFile = null == templateFile ? "blog.ftl" : templateFile;
		initTemplate(templateFile, codeType);
		codeType = null == codeType ? "UTF-8" : codeType;
		try {
			String filePath = absolutePath ? targetPath : (null == targetPath ? FreemarkerConfig.htmlPath : 
										FreemarkerConfig.htmlPath + "/" + targetPath);
			File f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			
			FileOutputStream os = new FileOutputStream(f.getPath() + "/" + targetFile);
			Writer out = new OutputStreamWriter(os, codeType);
			template.process(data, out);
			out.flush();
			out.close();
		} catch (TemplateException | IOException e) {
			log.error("create html file error:" + e);
		}
	}
	
	/**
	 * 默认blog.ftl模板
	 * */
	public static void analysisTemplate(String targetPath, String targetFile, Map<?, ?> data,String templateFile, String codeType) {
		analysisTemplate(targetPath, targetFile, data, templateFile, codeType, false);
	}
	
}
