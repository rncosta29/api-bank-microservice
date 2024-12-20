package br.com.rcosta.bank.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.rcosta.bank.dto.CreditCardBillsDto;
import br.com.rcosta.bank.services.CreditCardBillsService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/bills")
@CrossOrigin(origins = "*")
public class CreditCardBillsController {

	@Autowired
	private CreditCardBillsService creditCardBillsService;
	
	@GetMapping()
	public ResponseEntity<List<CreditCardBillsDto>> findAllBills() {
		return ResponseEntity.ok(creditCardBillsService.allBills());
	}
	
	@PostMapping("/insert/{quantity}")
	public ResponseEntity<List<CreditCardBillsDto>> createBills(
	        @RequestBody CreditCardBillsDto dto, 
	        @PathVariable("quantity") Integer quantity,  // Correção: Use @PathVariable para obter o parâmetro da URL
	        UriComponentsBuilder uriBuilder) {

	    try {
	        // Chama o serviço para criar as faturas (parceladas ou não)
	        List<CreditCardBillsDto> list = creditCardBillsService.addNewBills(dto, quantity);

	        // Cria a URI para o primeiro item da lista de faturas (você pode modificar para outro item, conforme necessário)
	        URI address = uriBuilder.path("/api/v1/bills/{id}").buildAndExpand(list.get(0).getId()).toUri();

	        // Retorna a resposta com status 201 (Created) e o corpo com a lista de faturas criadas
	        return ResponseEntity.created(address).body(list);
	    } catch (Exception e) {
	        // Log da exceção para depuração
	        e.printStackTrace();

	        // Retorna resposta de erro com código 500 e mensagem amigável
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<CreditCardBillsDto> findBillsById(@PathVariable Long id) {
		CreditCardBillsDto dto = creditCardBillsService.getBillsById(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/creditCardId/{id}")
	public ResponseEntity<List<CreditCardBillsDto>> findByCreditCardId(@PathVariable Long id) {
		List<CreditCardBillsDto> list = creditCardBillsService.getBillsByCreditCardId(id);
		
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
	    try {
	        // Chama o serviço para excluir a fatura pelo ID
	        creditCardBillsService.deleteBillById(id);

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
