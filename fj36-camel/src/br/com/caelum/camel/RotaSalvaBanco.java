package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;

import br.com.caelum.livraria.modelo.Livro;

public class RotaSalvaBanco extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		from("direct:livros")
		.split(body())
		.process(new	Processor()	{
			@Override
			public	void	process(Exchange	exchange)	throws	Exception	{
							Message	inbound	=	exchange.getIn();
							Livro	livro	=	(Livro)	inbound.getBody();
							String	nomeAutor	=	livro.getNomeAutor();
							inbound.setHeader("nomeAutor",	nomeAutor);
			}	
        })
		.setBody(
				simple("insert	into	Livros	(nomeAutor)	values	(:?nomeAutor)")
								)
		.to("jdbc:mysqlDataSource?useHeadersAsParameters=true");
		
	}

	

}
