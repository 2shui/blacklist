package com.blacklist.utils.freemarker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import freemarker.template.SimpleDate;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class DateFormatFunc implements TemplateMethodModelEx {
	static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		Date date = ((SimpleDate) arguments.get(0)).getAsDate();
		return df.format(date);
	}

}
