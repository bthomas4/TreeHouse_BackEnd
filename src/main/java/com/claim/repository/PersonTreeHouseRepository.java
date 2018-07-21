package com.claim.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.claim.entity.PersonTreeHouse;


@Repository
public interface PersonTreeHouseRepository extends JpaRepository<PersonTreeHouse, Long> {
	
	@Query(value="SELECT tree_houseid FROM person_tree_house WHERE person_email=?1", nativeQuery=true)
	public List<Integer> getTreeIDs(String userEmail);
	
	@Query(value="SELECT person_email, generationid FROM person_tree_house WHERE tree_houseid=?1", nativeQuery=true)
	public List<PersonTreeHouse> getEmailsAndIDs(long treeID);
	
}
