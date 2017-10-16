package com.blacklist.service;

import java.util.List;

import com.blacklist.domain.Water;

public interface WaterService {
	void save(Water water);
	void save(List<Water> list);
	@Deprecated
	List<Water> getList(Long max, Long min, int num);
}
