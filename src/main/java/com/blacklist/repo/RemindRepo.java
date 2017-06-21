package com.blacklist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.ProgramRemind;

@Repository
public interface RemindRepo extends JpaRepository<ProgramRemind, Long> {
	public List<ProgramRemind> findByStatus(Integer status);
	public List<ProgramRemind> findByOpenId(String openId);
	public List<ProgramRemind> findByStatusAndOpenId(int status, String openId);
	@Modifying
	@Query(value = "update mini_program_remind t set t.status = 2 where t.id=:id and t.open_id=:openId", nativeQuery = true)
	public void updateForDel(@Param("id") Long id, @Param("openId") String openId);
}
