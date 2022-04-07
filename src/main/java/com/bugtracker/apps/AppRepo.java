package com.bugtracker.apps;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AppRepo extends JpaRepository<App, Long>{
	@Query("select a from App a where a.name = ?1")
    public App findByName(String name);
	
	@Query("select a from App a where a.name like %?1%")
    public List<App> SearchByName(String name);
	
}
