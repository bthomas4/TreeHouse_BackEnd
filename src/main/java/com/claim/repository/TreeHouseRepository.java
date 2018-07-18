package com.claim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claim.entity.TreeHouse;

@Repository
public interface TreeHouseRepository extends JpaRepository<TreeHouse, Integer> {

	//SQL queries go here if not yet defined

}
