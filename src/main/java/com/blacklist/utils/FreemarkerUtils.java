package com.blacklist.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacklist.config.FreemarkerConfig;

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

	/**
	 * 默认blog.ftl模板
	 * */
	public static void analysisTemplate(String targetPath, String file, Map<?, ?> data,String templateFile, String codeType) {
		templateFile = null == templateFile ? "blog.ftl" : templateFile;
		initTemplate(templateFile, codeType);
		codeType = null == codeType ? "UTF-8" : codeType;
		try {
			File f = new File(FreemarkerConfig.htmlPath + "\\" + targetPath);
			if(!f.exists()) {
				f.mkdirs();
			}
			
			FileOutputStream os = new FileOutputStream(f.getPath() + "\\" + file);
			Writer out = new OutputStreamWriter(os, codeType);
			template.process(data, out);
			out.flush();
			out.close();
		} catch (TemplateException | IOException e) {
			log.error("create html file error:" + e);
		}
	}
	
}
