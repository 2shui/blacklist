package com.blacklist.utils.freemarker;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class JsoupContendFunc implements TemplateMethodModel {

	@Override
	public Object exec(List arg0) throws TemplateModelException {
		String str = "";
		Document doc = Jsoup.parse(arg0.get(0).toString());
		List<Element> eles = doc.getAllElements();
		for (Element e : eles) {
			str += e.text();
		}
		return str;
	}

}
