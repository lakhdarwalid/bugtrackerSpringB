package com.bugtracker.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{
	@Query("select r from Role r where r.role = ?1")
    public Role findByName(String name);
	
}
