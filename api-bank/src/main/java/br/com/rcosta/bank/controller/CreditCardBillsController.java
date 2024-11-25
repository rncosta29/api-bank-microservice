package br.com.rcosta.bank.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.rcosta.bank.dto.CreditCardBillsDto;
import br.com.rcosta.bank.services.CreditCardBillsService;

@RestController
@RequestMapping("/api/v1/bills")
public class CreditCardBillsController {

	@Autowired
	private CreditCardBillsService creditCardBillsService;
	
	@GetMapping()
	public ResponseEntity<List<CreditCardBillsDto>> findAllBills() {
		return ResponseEntity.ok(creditCardBillsService.allBills());
	}
	
	@PostMapping("/insert")
	public ResponseEntity<CreditCardBillsDto> createBills(@RequestBody CreditCardBillsDto dto, UriComponentsBuilder uriBuilder) {
		CreditCardBillsDto model = creditCardBillsService.addBills(dto);
		URI address = uriBuilder.path("/api/v1/bills/{id}").buildAndExpand(model.getId()).toUri();
		
		return ResponseEntity.created(address).body(model);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CreditCardBillsDto> findCreditCardById(@PathVariable Long id) {
		CreditCardBillsDto dto = creditCardBillsService.getBillsById(id);
		
		return ResponseEntity.ok(dto);
	}
}
