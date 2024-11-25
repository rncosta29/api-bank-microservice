package br.com.rcosta.bank.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rcosta.bank.dto.CreditCardBillsDto;
import br.com.rcosta.bank.dto.CreditCardDto;
import br.com.rcosta.bank.model.CreditCardBillsModel;
import br.com.rcosta.bank.model.CreditCardModel;
import br.com.rcosta.bank.repository.CreditCardBillsRepository;
import br.com.rcosta.bank.repository.CreditCardRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CreditCardBillsService {

	@Autowired
	private CreditCardBillsRepository creditCardBillsRepository;
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<CreditCardBillsDto> allBills() {
		return creditCardRepository.findAll().stream().map(c -> modelMapper.map(c, CreditCardBillsDto.class))
				.collect(Collectors.toList());
	}
	
	public CreditCardBillsDto addBills(CreditCardBillsDto dto) {
		CreditCardModel creditCardModel = creditCardRepository.findById(dto.getCreditCardId())
				.orElseThrow(() -> new EntityNotFoundException());
		
		dto.setCreditCardDto(modelMapper.map(creditCardModel, CreditCardDto.class));
		CreditCardBillsModel model = modelMapper.map(dto, CreditCardBillsModel.class);
		
		model.setCreditCard(creditCardModel);
		creditCardBillsRepository.save(model);
		
		return modelMapper.map(model, CreditCardBillsDto.class);
	}
	
	public CreditCardBillsDto getBillsById(Long id) {
		CreditCardBillsModel model =  creditCardBillsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(model, CreditCardBillsDto.class);
	}
}
