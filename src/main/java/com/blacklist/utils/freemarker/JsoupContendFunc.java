package com.blacklist.utils.freemarker;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class JsoupContendFunc implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arg0) throws TemplateModelException {
		String str = "";
		Document doc = Jsoup.parse(arg0.get(0).toString());
		Elements eles = doc.getAllElements();
		eles = eles.select("body *").size() > 0 ? eles.select("body *") : eles.select("body");
		for (Element e : eles) {
			str += e.text();
		}
		return str;
	}
}
