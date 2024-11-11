package br.com.pires.mensageria.mensageria;

import br.com.pires.mensageria.mensageria.controller.CadastroContatos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Trabalho04MensageriaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(Trabalho04MensageriaApplication.class).run(args);
		CadastroContatos.setSpringContext(context);
		CadastroContatos.launch(CadastroContatos.class, args);
	}

}
