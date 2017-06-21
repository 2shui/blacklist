package com.blacklist.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blacklist.domain.ProgramRemind;
import com.blacklist.repo.RemindRepo;
import com.blacklist.service.RemindService;

@Service("remindService")
public class RemindServiceImpl implements RemindService {
	private Logger log = LoggerFactory.getLogger(RemindServiceImpl.class);

	@Autowired
	private RemindRepo remindRepo;
	
	@Override
	public ProgramRemind save(ProgramRemind remind) {
		remindRepo.saveAndFlush(remind);
		return remind;
	}

	@Override
	public List<ProgramRemind> getAll(int status) {
		return remindRepo.findByStatus(status);
	}

	@Override
	@Transactional
	public void del(Long id, String openId) {
		remindRepo.updateForDel(id, openId);
	}

	@Override
	public List<ProgramRemind> findByOpenId(String openId) {
		return remindRepo.findByOpenId(openId);
	}

	@Override
	public List<ProgramRemind> findByStatusAndOpenId(int status, String openId) {
		return remindRepo.findByStatusAndOpenId(status, openId);
	}
	
}
