package com.blacklist.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.Water;

@Repository
public interface WaterRepo extends JpaRepository<Water, Long> {
	List<Water> findByUrlNotNull();
	List<Water> findByUrlNull();
	
	//List<Water> findByIdNotBetween(Long mix, Long min, Pageable page);
}
