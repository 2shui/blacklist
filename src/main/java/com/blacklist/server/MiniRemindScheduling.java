package com.blacklist.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;




import com.blacklist.config.MiniProgramConfig;
import com.blacklist.domain.ProgramRemind;
import com.blacklist.service.RemindService;
import com.blacklist.utils.HttpsClient;
import com.blacklist.utils.Lunar;import com.blacklist.utils.MailSender;


@Configuration
@EnableScheduling
public class MiniRemindScheduling {
	private static Logger log = LoggerFactory.getLogger(MiniRemindScheduling.class);
	@Autowired
	RemindService remindService;
	@Autowired
	WechatService wechatService;
	
	@Scheduled(cron = "0 39 7 * * ?")
	public void scheduler() {
		GregorianCalendar gregorian = new GregorianCalendar();
		Date now = new Date();
		gregorian.setTime(now);
		gregorian.add(Calendar.DAY_OF_YEAR, 7);
		Date week = gregorian.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String solar = sdf.format(now);
		String solarWeek = sdf.format(week);
		
		List<ProgramRemind> needReminds = new ArrayList<ProgramRemind> ();
		try {
			String lunar = Lunar.solarToLunar(now, false);
			String lunarWeek = Lunar.solarToLunar(week, false);
			List<ProgramRemind> list = remindService.getAll(1);
			needReminds = list.stream().filter(r -> {return check(r, lunar, solar, lunarWeek, solarWeek);})
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("to lunar error:{}", e);
		}
		needReminds.forEach(remind -> {
			String lunar = remind.getIsLunar()?"农历":"阳历";
			String time = remind.getTime();
			String content = "hi,<br/>今天是"+lunar+time+"，您在小程序中设置了提醒：<b>"
					+remind.getTitle()+"</b>。<br/>不要忘记这个重要的日子哦~";
			MailSender.send126(remind.getEmail(), "日程提醒-【日历小二】", content);
		});
		
	}
	
	/*
	private void doTemplate(ProgramRemind remind) {
		try {
			String token = wechatService.getToken(MiniProgramConfig.appid, MiniProgramConfig.appSecret);
			String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+token;
			HttpsClient client = new HttpsClient();
			Map<String, String> param = new HashMap<String,String> ();
			param.put("touser", remind.getOpenId());
			param.put("template_id", MiniProgramConfig.templateId);
			param.put("form_id", "");
			param.put("data", "{'keyword1':'"+remind.getTitle()+"','keyword2':'"+remind.getTime()+"'}");
			client.doPost(url, null, "utf-8");
		} catch (IOException e) {
			log.error("get wechat token error:{}", e);
		}
	}
	*/
	
	/**
	 * @param remind
	 * @param lunarNow 农历年月日 yyyy-MM-dd
	 * @param romeNow 公历年月日 yyyy-MM-dd
	 * @param lunarWeek 农历一周后
	 * @param romeNow 公历一周后
	 * @return
	 */
	private static boolean check(ProgramRemind remind, String lunarNow, String romeNow, String lunarWeek,
			String romeWeek) {
		boolean lunar = remind.getIsLunar();
		boolean sday = remind.getRemindTime().contains("week");
		boolean nday = remind.getRemindTime().contains("day");
		String now = lunar?lunarNow:romeNow;
		String week = lunar?lunarWeek:romeWeek;
		String remindDay = remind.getTime();
		String subDay = remindDay.substring(5, 10);
		String subNow = now.substring(5, 10);
		String subWeek = week.substring(5, 10);
		
		return (sday && ((remind.getIsEveryYear() && subDay.equals(subWeek)) || 
							(!remind.getIsEveryYear() && remindDay.equals(week))))
				|| 
				(nday && ((remind.getIsEveryYear() && subDay.equals(subNow)) || 
							(!remind.getIsEveryYear() && remindDay.equals(now))));
	}
	/*
	public static void main(String[] args) {
		ProgramRemind remind = new ProgramRemind();
		remind.setIsLunar(true);
		remind.setRemindTime("week,day");
		remind.setTime("2017-12-12");
		remind.setIsEveryYear(false);
		System.out.println(check(remind, "2018-12-12", "2019-01-23", "2018-12-19", "2019-01-30"));
		
	}
	*/
}
