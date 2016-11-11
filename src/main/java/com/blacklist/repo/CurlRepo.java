package com.blacklist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.Curl;

@Repository
public interface CurlRepo extends JpaRepository<Curl, Long> {

}
