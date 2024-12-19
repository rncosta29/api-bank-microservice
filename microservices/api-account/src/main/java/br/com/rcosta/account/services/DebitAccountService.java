package br.com.rcosta.account.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.rcosta.account.dto.DebitAccountDto;
import br.com.rcosta.account.dto.PersonalAccountDto;
import br.com.rcosta.account.model.DebitAccountModel;
import br.com.rcosta.account.model.PersonalAccountModel;
import br.com.rcosta.account.repositories.DebitAccountRepository;
import br.com.rcosta.account.repositories.PersonalAccountRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DebitAccountService {

	private DebitAccountRepository debitAccountRepository;
	private PersonalAccountRepository personalAccountRepository;
	private ModelMapper modelMapper;
	
	public DebitAccountService(DebitAccountRepository debitAccountRepository, PersonalAccountRepository personalAccountRepository, ModelMapper modelMapper) {
		this.debitAccountRepository = debitAccountRepository;
		this.personalAccountRepository = personalAccountRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<DebitAccountDto> allDebits() {
		return debitAccountRepository.findAll().stream().map(c -> modelMapper.map(c, DebitAccountDto.class))
				.collect(Collectors.toList());
	}
	
	public DebitAccountDto addDebit(DebitAccountDto dto) {
		PersonalAccountModel personalAccountModel = personalAccountRepository.findById(dto.getPersonalAccountId())
				.orElseThrow(() -> new EntityNotFoundException());
		
		dto.setPersonalAccountDto(modelMapper.map(personalAccountModel, PersonalAccountDto.class));
		DebitAccountModel model = modelMapper.map(dto, DebitAccountModel.class);
		
		model.setPersonalAccountModel(personalAccountModel);
		debitAccountRepository.save(model);
		
		return modelMapper.map(model, DebitAccountDto.class);
	}
	
	public DebitAccountDto getDebitById(Long id) {
		DebitAccountModel model =  debitAccountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(model, DebitAccountDto.class);
	}
	
	public List<DebitAccountDto> getDebitByPersonalAccountId(Long personalAccountId) {
	    return debitAccountRepository.findByPersonalAccountId(personalAccountId).stream()
	            .sorted(Comparator.comparing(DebitAccountModel::getDate))  // Ordena pelo paymentYear em ordem crescente
	            .map(c -> modelMapper.map(c, DebitAccountDto.class))
	            .collect(Collectors.toList());
	}
	
	public void deleteDebitById(Long id) {
	    // Verifica se a fatura existe antes de deletar
	    DebitAccountModel model = debitAccountRepository.findById(id)
	        .orElseThrow(() -> new EntityNotFoundException("Fatura com id " + id + " não encontrada."));

	    // Realiza a exclusão
	    debitAccountRepository.delete(model);
	}
}
