package com.pagamentos.pagamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pagamentos.pagamentos.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

}
