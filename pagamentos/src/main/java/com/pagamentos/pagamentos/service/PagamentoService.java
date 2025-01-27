package com.pagamentos.pagamentos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pagamentos.pagamentos.dto.PagamentoDto;
import com.pagamentos.pagamentos.http.PedidoClient;
import com.pagamentos.pagamentos.model.Pagamento;
import com.pagamentos.pagamentos.model.Status;
import com.pagamentos.pagamentos.repository.PagamentoRepository;

@Service
public class PagamentoService {
	
	@Autowired
	private PagamentoRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PedidoClient pedido;
	
	public List<PagamentoDto> obterTodos(){
		return repository
					.findAll().stream()
					.map(p -> modelMapper.map(p, PagamentoDto.class))
					 .collect(Collectors.toList());
	}


	
	public PagamentoDto obterPorId(Long id){
		Pagamento pagamento = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		PagamentoDto dto = modelMapper.map(pagamento, PagamentoDto.class);
		dto.setItens(pedido.obterItensDoPedido(pagamento.getPedidoId()).getItens());
		return dto;		
	}
	
	public PagamentoDto criarPagamento(PagamentoDto dto) {
		Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
		pagamento.setStatus(Status.CRIADO);
		repository.save(pagamento);
		return modelMapper.map(pagamento,  PagamentoDto.class);
	}
	
	public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
		Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
		pagamento.setId(id);
		pagamento = repository.save(pagamento);
		return modelMapper.map(pagamento, PagamentoDto.class);
	}
	
	public void excluirPagamento(Long id) {
		repository.deleteById(id);
	}
	
	public void confirmarPagamento(Long id) {
		Optional<Pagamento> pagamento = repository.findById(id);
		
		if(!pagamento.isPresent()) {
			throw new EntityNotFoundException();
		}
		pagamento.get().setStatus(Status.CONFIRMADO);
		repository.save(pagamento.get());
		pedido.atualizarPagamento(pagamento.get().getPedidoId());
		
	}
	
	public void alterarStatus(Long id) {
		Optional<Pagamento> pagamento = repository.findById(id);
		
		if(!pagamento.isPresent()) {
			throw new EntityNotFoundException();
		}
		pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
		repository.save(pagamento.get());
	}
	

}
