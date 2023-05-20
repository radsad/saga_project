package tz.go.thesis.applications.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tz.go.thesis.applications.entity.Applications;




@Repository
public interface ApplicationRepository extends JpaRepository<Applications, Long>,JpaSpecificationExecutor<Applications> {
	
	@Query("SELECT ap FROM Applications ap WHERE status = ?1")
	List<Applications> findBySatus(int status);
	
	
	@Query("SELECT ap FROM Applications ap WHERE applicationsId = ?1")
	Optional<Applications> findById(Long id);
	

	@Query("SELECT ap FROM Applications ap WHERE applicationsId =:application")
	Applications findByapplicationId(Long application);
	
	@Query("select ap from Applications ap where ap.applicationsId in :applicationIds")
	List<Applications> findApplicationsByIds(List<Long> applicationIds);
		
}
