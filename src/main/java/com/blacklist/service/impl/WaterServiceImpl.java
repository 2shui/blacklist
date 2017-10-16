package com.blacklist.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.blacklist.domain.Water;
import com.blacklist.repo.WaterRepo;
import com.blacklist.service.WaterService;
import com.blacklist.utils.FreemarkerUtils;

@Service("waterService")
public class WaterServiceImpl implements WaterService {
	private Logger log = LoggerFactory.getLogger(WaterServiceImpl.class);
	@Autowired
	WaterRepo waterRepo;
	@Override
	public void save(Water water) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		water.setCreateTime(now);
		water.setAccessNum(1);
		water = waterRepo.saveAndFlush(water);
		String targetPath = sdf.format(now);
		String targetFile = water.getId() + ".html";
		water.setUrl("/" + targetPath + "/" + targetFile);
		Map<String, Water> data = new HashMap<>();
		data.put("water", water);
		FreemarkerUtils.analysisTemplate(targetPath, targetFile, data, "water.ftl", null);
	}
	
	@Override
	public void save(List<Water> list) {
		Date now = new Date();
		list.forEach(w-> {
			w.setAccessNum(1);
			w.setCreateTime(now);
		});
		waterRepo.save(list);
	}

	@Override
	public List<Water> getList(Long max, Long min, int num) {
		Sort sort =  new Sort(Direction.DESC, "id");
		Pageable page = new PageRequest(0, num, sort);
		//return waterRepo.findByIdNotBetween(min, max, page);
		return null;
	}
	
}
