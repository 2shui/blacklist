package com.blacklist.utils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.blacklist.config.AliMQConfig;

public class SMSProducer {
	private Logger log = LoggerFactory.getLogger(SMSProducer.class);
	private static Producer producer;

	private static class SMSProducerHolder {
		private static SMSProducer producerHolder = new SMSProducer();
	}

	public static SMSProducer getInstance() {
		return SMSProducerHolder.producerHolder;
	}

	private SMSProducer() {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ProducerId, AliMQConfig.producerId);
		properties.put(PropertyKeyConst.AccessKey, AliMQConfig.accessKey);
		properties.put(PropertyKeyConst.SecretKey, AliMQConfig.secretKey);
		properties.put(PropertyKeyConst.SendMsgTimeoutMillis,
				AliMQConfig.timeoutMillis);
		properties.put(PropertyKeyConst.ONSAddr, AliMQConfig.ONSAddr);
		producer = ONSFactory.createProducer(properties);
		producer.start();
	}

	public <T extends Serializable> void send(T... obj) {
		Arrays.asList(obj)
				.stream()
				.forEach(
						o -> {
							byte[] body = buildSerializable(o);
							if (null != body) {
								Message msg = new Message(AliMQConfig.topic,
										"", buildSerializable(o));
								SendResult sendResult = producer.send(msg);
								log.info("SMSProducer send result:{}",
										sendResult);
							}
						});

	}

	private byte[] buildSerializable(Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ObjectOutputStream objOutStream = new ObjectOutputStream(outStream);
			objOutStream.writeObject(obj);
			objOutStream.flush();
			objOutStream.close();
			bytes = outStream.toByteArray();
		} catch (Exception e) {
			log.error("SMSProducer buildSerializable error:{}", e);
		}
		return bytes;

	}
}
