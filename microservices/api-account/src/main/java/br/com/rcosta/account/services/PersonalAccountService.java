package br.com.rcosta.account.services;

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
public class PersonalAccountService {

	private PersonalAccountRepository personalAccountRepository;
	private DebitAccountRepository debitAccountRepository;
	private ModelMapper modelMapper;
	
	public PersonalAccountService(PersonalAccountRepository personalAccountRepository, DebitAccountRepository debitAccountRepository, ModelMapper modelMapper) {
		this.personalAccountRepository = personalAccountRepository;
		this.debitAccountRepository = debitAccountRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<PersonalAccountDto> allPersonalAccount() {
		List<PersonalAccountDto> listModel = personalAccountRepository.findAll().stream().map(c -> modelMapper.map(c, PersonalAccountDto.class))
				.collect(Collectors.toList());
		
		for (PersonalAccountDto personalAccountDto : listModel) {
			List<DebitAccountModel> list = debitAccountRepository.findByPersonalAccountId(personalAccountDto.getId());
			personalAccountDto.setDebitAccountModel(list.stream().map(dto -> modelMapper.map(dto, DebitAccountDto.class)).collect(Collectors.toList()));
		}
		
		return listModel;
	}
	
	public PersonalAccountDto addPersonalAccount(PersonalAccountDto dto) {
		PersonalAccountModel model = modelMapper.map(dto, PersonalAccountModel.class);
		personalAccountRepository.save(model);
		
		return modelMapper.map(model, PersonalAccountDto.class);
	}
	
	public PersonalAccountDto getPersonalAccountById(Long id) {
		PersonalAccountModel model =  personalAccountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		List<DebitAccountModel> list = debitAccountRepository.findByPersonalAccountId(id);
		PersonalAccountDto newModel = modelMapper.map(model, PersonalAccountDto.class);
		
		newModel.setDebitAccountModel(list.stream().map(dto -> modelMapper.map(dto, DebitAccountDto.class)).collect(Collectors.toList()));
		
		return newModel;
	}
}
