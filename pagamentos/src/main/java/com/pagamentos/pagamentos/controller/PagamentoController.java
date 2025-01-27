package com.pagamentos.pagamentos.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pagamentos.pagamentos.dto.PagamentoDto;
import com.pagamentos.pagamentos.service.PagamentoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

	@Autowired
	private PagamentoService service;
	
	@GetMapping
	public List<PagamentoDto> listar(){
		return service.obterTodos();
	}
	
	@GetMapping("/id")
	public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id){
		PagamentoDto dto =  service.obterPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder){
		PagamentoDto pagamento = service.criarPagamento(dto);
		URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();
		return ResponseEntity.created(endereco).body(pagamento);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto dto){
		PagamentoDto atualizado = service.atualizarPagamento(id, dto);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id){
		service.excluirPagamento(id);
		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/{id}/confirmar")
	@CircuitBreaker(name = "atualizarPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
	public void confirmarPagamento(@PathVariable @NotNull Long id) {
		service.confirmarPagamento(id);
	}
	public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e) {
		service.alterarStatus(id);
	}
}
