package com.blacklist.service;

import java.util.List;

import com.blacklist.domain.ProgramRemind;

public interface RemindService {
	ProgramRemind save(ProgramRemind remind);
	List<ProgramRemind> findByStatusAndOpenId(int status, String openId);
	List<ProgramRemind> getAll(int status);
	List<ProgramRemind> findByOpenId(String openId);
	void del(Long id, String openId);
}
