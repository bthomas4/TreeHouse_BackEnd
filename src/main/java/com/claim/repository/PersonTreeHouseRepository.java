package com.claim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.claim.entity.PersonTreeHouse;

@Repository
public interface PersonTreeHouseRepository extends JpaRepository<PersonTreeHouse, Integer> {

}
