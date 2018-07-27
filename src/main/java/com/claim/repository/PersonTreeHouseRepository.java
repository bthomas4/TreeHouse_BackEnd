package com.claim.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.claim.entity.PersonTreeHouse;

@Repository
@Transactional
public interface PersonTreeHouseRepository extends JpaRepository<PersonTreeHouse, Long> {
	
	@Query(value="SELECT tree_houseid FROM person_tree_house WHERE person_email=?1", nativeQuery=true)
	public List<Integer> getTreeIDs(String userEmail);
	
	@Query(value="SELECT * FROM person_tree_house WHERE tree_houseid=?1", nativeQuery=true)
	public List<PersonTreeHouse> getEmailsAndIDs(long treeID);
	
	@Query(value="SELECT COUNT(*) FROM person_tree_house WHERE person_email=?1 AND tree_houseid=?2", nativeQuery=true)
	public int checkForDuplicate(String personEmail, int treeID);
	
	@Query(value="SELECT generationid FROM person_tree_house WHERE tree_houseid=?1 AND person_email=?2", nativeQuery=true)
	public int getGenID(int treeID, String userEmail);
	
	@Modifying
	@Query(value="UPDATE person_tree_house SET generationid=?1 WHERE tree_houseid=?2 AND person_email=?3", nativeQuery=true)
	public void updateGenID(int value, int treeID, String userEmail);
}
