package com.bugtracker.bugs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BugRepo extends JpaRepository<Bug, Long>  {
	
	@Query("select b from Bug b where b.title like %?1%")
	public List<Bug> SearchBugByTitle(String title);

}
