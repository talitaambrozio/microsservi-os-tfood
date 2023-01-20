package com.pagamentos.pagamentos.dto;

import java.math.BigDecimal;
import java.util.List;

import com.pagamentos.pagamentos.model.ItemDoPedido;
import com.pagamentos.pagamentos.model.Status;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PagamentoDto {
	
	private Long id;
	private BigDecimal valor;
	private String nome;
	private String numero;
	private String expiracao;
	private String codigo;
	private Status status;
	private Long pedidoId;
	private Long formaDePagamentoId;
	private List<ItemDoPedido> itens;

}
