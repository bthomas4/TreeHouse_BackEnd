package com.claim.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.claim.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Query(value="SELECT * FROM message WHERE receiver=?1 ORDER BY messageid DESC", nativeQuery=true)
	public List<Message> getMessages(String userEmail);
}