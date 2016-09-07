package com.blacklist.utils;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.blacklist.config.AliMQConfig;
import com.blacklist.domain.Topic;
import com.blacklist.server.IndexServer;

@Component
@Order(value=1)
public class SMSConsumer implements CommandLineRunner {
	private Logger log = LoggerFactory.getLogger(SMSConsumer.class);
	@Autowired
	private IndexServer indexServer;

	@Override
	public void run(String... arg0) throws Exception {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ConsumerId, AliMQConfig.consumerId);
		properties.put(PropertyKeyConst.AccessKey, AliMQConfig.accessKey);
		properties.put(PropertyKeyConst.SecretKey, AliMQConfig.secretKey);
		properties.put(PropertyKeyConst.ONSAddr, AliMQConfig.ONSAddr);
		Consumer consumer = ONSFactory.createConsumer(properties);
		consumer.subscribe(AliMQConfig.topic, "*", new MessageListener() {
			@Override
			public Action consume(Message message, ConsumeContext context) {
				Topic topic = buildObject(message.getBody());
				indexed(topic);
				return Action.CommitMessage;
			}
		});
		consumer.start();
	}
	
	private void indexed(Topic topic) {
		if (null != topic) {
			try {
				LuceneIKUtil.getInstance().createIndex(
						indexServer.build(Arrays.asList(topic)), false);
			} catch (Exception e) {
				log.error("Add indexed error:{}", e);
			}
		}
	}

	private <T extends Serializable> T buildObject(byte[] bytes) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objInputStream = new ObjectInputStream(
					inputStream);
			return (T) objInputStream.readObject();
		} catch (Exception e) {
			log.error("SMSConsumer buildObject error:{}", e);
		}
		return null;
	}


}
