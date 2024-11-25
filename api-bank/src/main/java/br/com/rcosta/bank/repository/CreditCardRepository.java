package br.com.rcosta.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rcosta.bank.model.CreditCardModel;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardModel, Long> {

}
