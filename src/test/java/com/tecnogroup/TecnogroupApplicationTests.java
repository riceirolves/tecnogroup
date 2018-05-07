package com.tecnogroup;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TecnogroupApplicationTests {

	@Autowired
	PedidoController pedidoController = new PedidoController();

	@Test
	public void successfulXMLTest() {
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(new Pedido(1, "29/03/1989", "Ricardo", 30.00, 92, 32));

		assertThat(pedidoController.processXML(pedidos).contains("cadastrado com sucesso."));

	}

	@Test
	public void wrongDataFormatTest() {
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(new Pedido(1, "29/03/12989", "Ricardo", 30.00, 92, 32));

		assertThat(pedidoController.processXML(pedidos).contains("Por favor informe a data e hora neste formato:"));

	}

	@Test
	public void successfulJsonTest() {
		List<Pedido> pedidos = new ArrayList<>();
		String pedido = "[{\"controle\": \"1\",\"data\": \"29/03/1989\",\"nome\": \"Ricardo\",\"valor\":\"30\",\"quantidade\":\"6\",\"codigo\":\"111\"}]";

		try {
			assertThat(pedidoController.processJson(pedido).contains("cadastrado com sucesso."));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void exceedXMLLimitTest() {
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());
		pedidos.add(new Pedido());

		assertThat(pedidoController.processXML(pedidos).contains("Você excedeu em"));

	}

}
