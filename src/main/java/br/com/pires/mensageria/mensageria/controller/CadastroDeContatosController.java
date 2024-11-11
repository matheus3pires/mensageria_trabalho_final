package br.com.pires.mensageria.mensageria.controller;

import br.com.pires.mensageria.mensageria.model.Contato;
import br.com.pires.mensageria.mensageria.service.ContatoService;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastroDeContatosController {

    @Autowired
    private ContatoService contatoService;

    @FXML
    private Button btnApagar;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnSair1;

    @FXML
    private Button btnSalvar;

    @FXML
    private TableColumn<Contato, String> colEmail;

    @FXML
    private TableColumn<Contato, Long> colId;

    @FXML
    private TableColumn<Contato, String> colNome;

    @FXML
    private TableColumn<Contato, String> colTelefone;

    @FXML
    private Label lblMensagem;

    @FXML
    private Pane paneMensagem;

    @FXML
    private TableView<Contato> tableContatos;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    private ObservableList<Contato> contatosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        carregarContatos();
    }

    @FXML
    void salvarContatos(ActionEvent event) {
        String mensagem = "";

        if (txtNome.getText().isEmpty()){
            mensagem =  "O campo Nome é obrigatório.";
        } else if (txtEmail.getText().isEmpty()) {
            mensagem =  "O campo E-mail é obrigatório.";
        } else if (txtTelefone.getText().isEmpty()) {
            mensagem =  "O campo Telefone é obrigatório.";
        } else {
            Contato contato = Contato.builder()
                    .nome(txtNome.getText())
                    .email(txtEmail.getText())
                    .telefone(txtTelefone.getText())
                    .build();

            contatoService.salvarContato(contato);
            mensagem =  "Contato salvo com sucesso!";
            carregarContatos();
        }
        mostrarMensagem(mensagem);
    }

    @FXML
    void apagarContatos(ActionEvent event) {
        Contato contatoSelecionado = tableContatos.getSelectionModel().getSelectedItem();
        String mensagem = "";

        if (contatoSelecionado != null) {
            contatoService.deletarContato(contatoSelecionado.getId());
            mensagem = ("Contato " + contatoSelecionado.getNome() + " removido com sucesso!");
            mostrarMensagem(mensagem);
            carregarContatos();
        } else {
            mensagem = "Nenhum contato selecionado para excluir.";
            mostrarMensagem(mensagem);
        }

    }

    @FXML
    void limparCampos(ActionEvent event) {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
    }

    @FXML
    void sair(ActionEvent event) {
        Platform.exit();
    }

    private void carregarContatos() {
        contatosList.clear();
        contatosList.addAll(contatoService.listarContatos());
        tableContatos.setItems(contatosList);
    }

    private void mostrarMensagem(String mensagem) {
        lblMensagem.setText(mensagem);
        paneMensagem.setVisible(true);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(e -> paneMensagem.setVisible(false));
        pauseTransition.play();
    }
}