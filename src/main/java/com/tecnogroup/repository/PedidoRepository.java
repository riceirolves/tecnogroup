package com.tecnogroup.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecnogroup.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

	List<Pedido> findByNome(String nome);
    
	List<Pedido> findBycontrole(int controle);
	
	List<Pedido> findBydataConvertida(LocalDate dataConvertida);
    
    
}
