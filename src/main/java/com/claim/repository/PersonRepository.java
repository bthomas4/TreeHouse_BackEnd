package com.claim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.claim.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

	@Query(value="SELECT * FROM person WHERE mother=?1", nativeQuery=true)
	public List<Person> findChildrenByMother(String motherEmail);
	
	@Query(value="SELECT * FROM person WHERE father=?1", nativeQuery=true)
	public List<Person> findChildrenByFather(String fatherEmail);
}
