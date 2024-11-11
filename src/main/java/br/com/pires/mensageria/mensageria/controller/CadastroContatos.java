package br.com.pires.mensageria.mensageria.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class CadastroContatos extends Application {

    @Setter
    private static ApplicationContext springContext;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CadastroContatos.class.getResource("/cadastrocontatos/cadastro-de-contatos.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cadastro de Contatos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
