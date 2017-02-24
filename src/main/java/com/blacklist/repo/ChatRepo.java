package com.blacklist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.Chat;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {

}
