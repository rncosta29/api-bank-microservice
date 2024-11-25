package br.com.rcosta.bank.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rcosta.bank.dto.CreditCardDto;
import br.com.rcosta.bank.model.CreditCardModel;
import br.com.rcosta.bank.repository.CreditCardRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<CreditCardDto> allCreditsCard() {
		return creditCardRepository.findAll().stream().map(c -> modelMapper.map(c, CreditCardDto.class))
				.collect(Collectors.toList());
	}
	
	public CreditCardDto addCreditCard(CreditCardDto dto) {
		CreditCardModel model = modelMapper.map(dto, CreditCardModel.class);
		creditCardRepository.save(model);
		
		return modelMapper.map(model, CreditCardDto.class);
	}
	
	public CreditCardDto getCreditsCardById(Long id) {
		CreditCardModel model =  creditCardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(model, CreditCardDto.class);
	}
}
