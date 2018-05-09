package com.tecnogroup;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

@RestController
public class PedidoController {

	@Autowired
	PedidoRepository repository;

	@GetMapping("/pedido")
	public String getPedidos(@RequestParam(value = "numeroPedido", defaultValue = "") String numeroPedido,
			@RequestParam(value = "dataCadastro", defaultValue = "") String dataCadastro) {
		String response = "";

		if (numeroPedido.equals("") && dataCadastro.equals("")) {
			for (Pedido pedido : repository.findAll()) {
				response += "Controle = " + pedido.getControle();
				response += ", Data = " + pedido.getDataConvertida();
				response += ", Nome = " + pedido.getNome();
				response += ", Valor = " + pedido.getValor();
				response += ", Quantidade = " + pedido.getQuantidade();
				response += ", Código = " + pedido.getCodigo();
				response += "<br />";

			}
		} else if (!numeroPedido.equals("") && dataCadastro.equals("")) {
			for (Pedido pedido : repository.findBycontrole(Integer.valueOf(numeroPedido))) {
				response += "Controle = " + pedido.getControle();
				response += ", Data = " + pedido.getDataConvertida();
				response += ", Nome = " + pedido.getNome();
				response += ", Valor = " + pedido.getValor();
				response += ", Quantidade = " + pedido.getQuantidade();
				response += ", Código = " + pedido.getCodigo();
				response += "<br />";

			}
		} else if (numeroPedido.equals("") && !dataCadastro.equals("")) {
			try {
				for (Pedido pedido : repository.findBydataConvertida(
						LocalDate.parse(dataCadastro, DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
					response += "Controle = " + pedido.getControle();
					response += ", Data = " + pedido.getDataConvertida();
					response += ", Nome = " + pedido.getNome();
					response += ", Valor = " + pedido.getValor();
					response += ", Quantidade = " + pedido.getQuantidade();
					response += ", Código = " + pedido.getCodigo();
					response += "<br />";
				}
			} catch (Exception e) {
				return "Por favor informe a data e hora neste formato: "
						+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";
			}

		}

		return response;
	}

	@RequestMapping(value = "/pedido", method = RequestMethod.POST, consumes = "application/xml")
	public String processXML(@RequestBody List<Pedido> pedidos) {
		pedidos.remove(0);
		return processPedido(pedidos);
	}

	@RequestMapping(value = "/pedido", method = RequestMethod.POST, consumes = "application/json")
	public String processJson(@RequestBody String payload) throws Exception {
		List<Pedido> pedidos = new ObjectMapper().readValue(payload,
				new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Pedido.class));

		return processPedido(pedidos);
	}

	private String processPedido(List<Pedido> pedidos) {
		String response = "";
		List<String> jaCadastrados = new ArrayList<>();

		if (pedidos.size() > 10)
			return "Você excedeu em " + (pedidos.size() - 10) + " a quantidade máxima(10) de pedidos";

		for (int i = 0; i < pedidos.size(); i++) {

			if (repository.findBycontrole(pedidos.get(i).getControle()).size() > 0) {
				jaCadastrados.add(String.valueOf(pedidos.get(i).getControle()));
			} else {
				if (pedidos.get(i).getQuantidade() == 0)
					pedidos.get(i).setQuantidade(1);
				if (pedidos.get(i).getQuantidade() > 5 && pedidos.get(i).getQuantidade() < 10)
					pedidos.get(i).setValor(pedidos.get(i).getValor() * 0.95);
				if (pedidos.get(i).getQuantidade() > 10)
					pedidos.get(i).setValor(pedidos.get(i).getValor() * 0.9);

				if (pedidos.get(i).getData() == null) {
					pedidos.get(i).setDataConvertida(LocalDate.now());
				} else {
					try {
						pedidos.get(i).setDataConvertida(
								LocalDate.parse(pedidos.get(i).getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					} catch (Exception e) {
						return "Por favor informe a data e hora neste formato: "
								+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";
					}
				}

				repository.save(pedidos.get(i));
				response += "Pedido número " + pedidos.get(i).getControle() + " cadastrado com sucesso. <br />";
			}

		}

		if (jaCadastrados.size() == 1) {
			response += "O número de controle " + jaCadastrados.get(0)
					+ " já esta cadastrado então o mesmo não foi inserido no banco.";
		}

		if (jaCadastrados.size() > 1) {

			response += "Segue os controles que por já estarem no banco de dados não foram inseridos novamente: ";
			for (int i = 0; i < jaCadastrados.size(); i++) {
				response += jaCadastrados.get(i) + " ";
			}
		}
		

		return response;
	}
}
