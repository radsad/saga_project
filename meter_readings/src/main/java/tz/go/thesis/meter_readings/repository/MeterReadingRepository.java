package tz.go.thesis.meter_readings.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tz.go.thesis.meter_readings.entities.MeterReading;


@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

	Optional<MeterReading> findById(Long id);
	MeterReading findByAccountNumber(String accountNumber);
	
	@Query("SELECT mr FROM  MeterReading mr WHERE mr.accountNumber =:accountNumber")
    MeterReading findMeterReadingByAccountNumberAndMeterNumber(String accountNumber);
	
}
