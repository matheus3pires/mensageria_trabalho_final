package br.com.pires.mensageria;

import br.com.pires.mensageria.controller.Mensageria;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MensageriaTrabalhoFinalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MensageriaTrabalhoFinalApplication.class).run(args);
		Mensageria.setSpringContext(context);
		Mensageria.launch(Mensageria.class, args);
	}

}
