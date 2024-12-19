package br.com.rcosta.account.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.rcosta.account.dto.DebitAccountDto;
import br.com.rcosta.account.services.DebitAccountService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/debit")
public class DebitAccountController {

	private DebitAccountService debitAccountService;
	
	@GetMapping()
	public ResponseEntity<List<DebitAccountDto>> findAllDebits() {
		return ResponseEntity.ok(debitAccountService.allDebits());
	}
	
	@PostMapping("/insert")
	public ResponseEntity<DebitAccountDto> createBills( @RequestBody DebitAccountDto dto, UriComponentsBuilder uriBuilder) {

	    try {
	        // Chama o serviço para criar as faturas (parceladas ou não)
	        DebitAccountDto model = debitAccountService.addDebit(dto);
	        
	        URI address = uriBuilder.path("/api/v1/personal-account/{id}").buildAndExpand(model.getId()).toUri();

	        // Retorna a resposta com status 201 (Created) e o corpo com a lista de faturas criadas
	        return ResponseEntity.created(address).body(model);
	    } catch (Exception e) {
	        // Log da exceção para depuração
	        e.printStackTrace();

	        // Retorna resposta de erro com código 500 e mensagem amigável
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<DebitAccountDto> findDebitById(@PathVariable Long id) {
		DebitAccountDto dto = debitAccountService.getDebitById(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/personal-account-id/{id}")
	public ResponseEntity<List<DebitAccountDto>> findByPersonalAccountId(@PathVariable Long id) {
		List<DebitAccountDto> list = debitAccountService.getDebitByPersonalAccountId(id);
		
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDebit(@PathVariable Long id) {
	    try {
	        // Chama o serviço para excluir a fatura pelo ID
	    	debitAccountService.deleteDebitById(id);

	        // Retorna o status 204 (No Content) caso a exclusão seja bem-sucedida
	        return ResponseEntity.noContent().build();
	    } catch (EntityNotFoundException e) {
	        // Retorna o status 404 (Not Found) caso a fatura não seja encontrada
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    } catch (Exception e) {
	        // Log da exceção para depuração
	        e.printStackTrace();

	        // Retorna o status 500 (Internal Server Error) em caso de erro inesperado
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
}
