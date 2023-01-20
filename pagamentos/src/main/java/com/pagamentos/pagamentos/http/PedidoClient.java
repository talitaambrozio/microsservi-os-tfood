package com.pagamentos.pagamentos.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.pagamentos.pagamentos.model.Pedido;

@FeignClient("pedidos-ms")
public interface PedidoClient {
	
	@PutMapping("/pedidos/{id}/pago")
	void atualizarPagamento(@PathVariable Long id) ;
	
	@GetMapping("/pedidos/{id}")
	Pedido obterItensDoPedido(@PathVariable Long id);
}
