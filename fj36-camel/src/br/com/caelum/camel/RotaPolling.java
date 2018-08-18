package br.com.caelum.camel;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import br.com.caelum.livraria.modelo.Livro;

public class RotaPolling extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub

		
		from("http://localhost:8088/fj36-livraria/loja/livros/mais-vendidos").delay(1000).unmarshal().json()
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) {
				List<?> listaDeLivros = (List<?>) exchange.getIn().getBody();
				ArrayList<Livro> livros = (ArrayList<Livro>) listaDeLivros.get(0);
				Message message = exchange.getIn();
				message.setBody(livros);
			}
		})

		.log("${body}").to("direct:livros")
		;
	}

}
