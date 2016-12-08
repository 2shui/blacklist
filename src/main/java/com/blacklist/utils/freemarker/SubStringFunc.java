package com.blacklist.utils.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class SubStringFunc implements TemplateMethodModel {

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		Integer begin, end;
		String str = arg0.get(0).toString();
		if (arg0.size() > 2) {
			begin = Integer.parseInt(arg0.get(1).toString());
			end = Integer.parseInt(arg0.get(2).toString());
			end = end > str.length() ? str.length() : end;
			return arg0.get(0).toString().subSequence(begin, end);
		} else if (arg0.size() > 1) {
			begin = 0;
			end = Integer.parseInt(arg0.get(1).toString());
			end = end > str.length() ? str.length() : end;
			return arg0.get(0).toString().subSequence(begin, end);
		}
		return null;
	}

}
