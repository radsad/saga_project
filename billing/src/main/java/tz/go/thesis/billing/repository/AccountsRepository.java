package tz.go.thesis.billing.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tz.go.thesis.billing.entities.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
	
	@Query(value = "SELECT nextval('manyoni_seq')", nativeQuery = true)
    Long getNextSeriesId(); 
	
	@Query("SELECT acc FROM Accounts acc where accountNumber= :account")
	Accounts findAccountByAccountNo(@Param("account") String accountNumber);
	Optional<Accounts> findAccountByAccountNumber(String accountNumber);
	

	@Query("SELECT acc FROM Accounts acc where acc.applicationId = :applicationId")
	Accounts findAccountByApplicationId(Long applicationId);


	@Query("SELECT acc FROM Accounts acc where acc.accountId=:accountId")
	Accounts findAccountByAccountId(Long accountId);
	

}
