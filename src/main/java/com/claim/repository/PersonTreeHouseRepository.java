package com.claim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.claim.entity.PersonTreeHouse;

@Repository
public interface PersonTreeHouseRepository extends JpaRepository<PersonTreeHouse, Integer> {
	
	//Check this ?1
	@Query("SELECT tree_houseid FROM person_tree_house WHERE person_email IN (?1)")
	List<Integer> getTreeIDs(String userEmail);
	
	//Check this too
	@Query("SELECT person_email, generationid FROM person_tree_house WHERE tree_houseid IN (?1)")
	List<PersonTreeHouse> getEmailsAndIDs(int treeID);

	
}
