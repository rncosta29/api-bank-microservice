package br.com.rcosta.account.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.rcosta.account.dto.PersonalAccountDto;
import br.com.rcosta.account.services.PersonalAccountService;

@RestController
@RequestMapping("/api/v1/personal-account")
@CrossOrigin(origins = "*")
public class PersonalAccountController {

	private PersonalAccountService personalAccountService;
	
	public PersonalAccountController(PersonalAccountService personalAccountService) {
		this.personalAccountService = personalAccountService;
	}
	
	@PostMapping("/insert")
	public ResponseEntity<PersonalAccountDto> createCreditCard(@RequestBody PersonalAccountDto dto, UriComponentsBuilder uriBuilder) {
		PersonalAccountDto model = personalAccountService.addPersonalAccount(dto);
		URI address = uriBuilder.path("/api/v1/credit-card/{id}").buildAndExpand(model.getId()).toUri();
		
		return ResponseEntity.created(address).body(model);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonalAccountDto> findCreditCardById(@PathVariable Long id) {
		PersonalAccountDto dto = personalAccountService.getPersonalAccountById(id);
		
		return ResponseEntity.ok(dto);
	}
}
