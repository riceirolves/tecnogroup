package com.tecnogroup;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

	List<Pedido> findByNome(String nome);
    
	List<Pedido> findBycontrole(int controle);
	
	List<Pedido> findBydataConvertida(LocalDate dataConvertida);
    
    
}
