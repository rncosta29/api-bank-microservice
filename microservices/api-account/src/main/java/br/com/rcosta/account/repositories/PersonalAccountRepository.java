package br.com.rcosta.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rcosta.account.model.PersonalAccountModel;

@Repository
public interface PersonalAccountRepository extends JpaRepository<PersonalAccountModel, Long> {

}
