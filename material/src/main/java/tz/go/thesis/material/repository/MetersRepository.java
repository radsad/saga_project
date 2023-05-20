package tz.go.thesis.material.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tz.go.thesis.material.entity.Meter;


@Repository
public interface MetersRepository extends JpaRepository<Meter, Long>{

	public Meter findByMeterNumber(String meterNumber);

	@Query("SELECT m.meterNumber FROM Meter m")
	public List<String> findExistingMeters();
	
	@Query("SELECT m FROM Meter m WHERE m.id=:meterId")
	public Meter findByMeterId(Long meterId);
	


}
