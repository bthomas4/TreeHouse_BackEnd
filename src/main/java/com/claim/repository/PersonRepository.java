package com.claim.repository;

import java.util.List;

import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.claim.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
	
	//SQL queries go here if not yet defined
//	@Query("SELECT p FROM person p WHERE email=:email AND password=:password")
//	public Person login(@Param("email") String email, @Param("password")String password);

}
