package com.claim.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.claim.entity.PersonTreeHouse;

@Repository
public interface PersonTreeHouseRepository extends JpaRepository<PersonTreeHouse, Integer> {
	
	@Query("SELECT tree_houseid FROM person_tree_house WHERE person_email=:userEmail")
	public List<Integer> getTreeIDs(@Param("userEmail") String userEmail);
	
	@Query("SELECT person_email, generationid FROM person_tree_house WHERE tree_houseid=:treeID")
	public List<PersonTreeHouse> getEmailsAndIDs(@Param("treeID") int treeID);
	
}
