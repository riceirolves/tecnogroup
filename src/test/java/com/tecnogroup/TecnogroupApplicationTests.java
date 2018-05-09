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
		pedidos.add(new Pedido(27, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(28, "29/03/1989", "Ricardo", 30.00, 92, 32));

		assertThat(pedidoController.processXML(pedidos)).contains("cadastrado com sucesso.");

	}

	@Test
	public void wrongDataFormatTest() {
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(new Pedido(2799, "29/03/19589", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(2899, "29/03/19889", "Ricardo", 30.00, 92, 32));

		assertThat(pedidoController.processXML(pedidos)).contains("Por favor informe a data e hora neste formato:");

	}

	@Test
	public void successfulJsonTest() {
		List<Pedido> pedidos = new ArrayList<>();
		String pedido = "[{\"controle\": \"99\",\"data\": \"29/03/1989\",\"nome\": \"Ricardo\",\"valor\":\"30\",\"quantidade\":\"6\",\"codigo\":\"111\"}]";

		try {
			assertThat(pedidoController.processJson(pedido)).contains("cadastrado com sucesso.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void exceedXMLLimitTest() {
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(new Pedido(27, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(28, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(29, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(21, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(22, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(23, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(24, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(25, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(26, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(30, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(31, "29/03/1989", "Ricardo", 30.00, 92, 32));
		pedidos.add(new Pedido(32, "29/03/1989", "Ricardo", 30.00, 92, 32));

		assertThat(pedidoController.processXML(pedidos)).contains("Você excedeu em");

	}

}
