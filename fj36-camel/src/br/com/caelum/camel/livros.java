package br.com.caelum.camel;

import java.util.Scanner;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import br.com.caelum.livraria.modelo.Livro;

public class livros {
	
	public static void main(String[] args) throws Exception {
		
		MysqlConnectionPoolDataSource	mysqlDs	=	new	MysqlConnectionPoolDataSource();
		mysqlDs.setDatabaseName("fj36_camel");
		mysqlDs.setServerName("localhost");
		mysqlDs.setPort(3306);
		mysqlDs.setUser("root");
		mysqlDs.setPassword("caelum");
		
		JndiContext	jndi	=	new	JndiContext();
		jndi.rebind("mysqlDataSource",	mysqlDs);
		
		CamelContext ctx = new DefaultCamelContext(jndi);
		ctx.addRoutes( new RotaPolling());
		ctx.addRoutes( new RotaSalvaBanco());

		
		ctx.start();
		new Scanner(System.in).nextLine();
		ctx.stop();
		
		
		
	}

}
