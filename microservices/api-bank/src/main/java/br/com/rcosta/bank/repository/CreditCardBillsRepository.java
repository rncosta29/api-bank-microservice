package br.com.rcosta.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rcosta.bank.model.CreditCardBillsModel;

@Repository
public interface CreditCardBillsRepository extends JpaRepository<CreditCardBillsModel, Long> {
	@Query("SELECT c FROM CreditCardBillsModel c WHERE c.creditCard.id = :creditCardId")
	List<CreditCardBillsModel> findByCreditCardId(@Param("creditCardId") Long creditCardId);
}
