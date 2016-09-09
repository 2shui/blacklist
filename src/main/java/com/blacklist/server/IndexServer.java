package com.blacklist.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.stereotype.Service;

import com.blacklist.config.WebConfig;
import com.blacklist.domain.Topic;

@Service
public class IndexServer {

	public List<Document> build(List<Topic> topics) {
		List<Document> list = new ArrayList<Document>();
		topics.forEach(topic -> {
			Document doc = new Document();
			Field companyField = new Field(WebConfig.company, topic
					.getCompany(), TextField.TYPE_STORED);
			companyField.setBoost(WebConfig.companyPoint);
			doc.add(companyField);
			Field cityField = new Field(WebConfig.city, topic.getCity(),
					TextField.TYPE_STORED);
			cityField.setBoost(WebConfig.cityPoint);
			doc.add(cityField);
			Field sketchField = new Field(WebConfig.sketch, topic.getSketch(), TextField.TYPE_STORED);
			sketchField.setBoost(WebConfig.sketchPoint);
			doc.add(sketchField);
			Field idField = new Field(WebConfig.id, String.valueOf(topic.getId()), TextField.TYPE_STORED);
			doc.add(idField);
			list.add(doc);
		});
		return list;
	}
}
