package br.com.pires.mensageria.controller;

import br.com.pires.mensageria.dto.ManutencaoDroneDTO;
import br.com.pires.mensageria.model.Drone;
import br.com.pires.mensageria.model.Manutencao;
import br.com.pires.mensageria.rabbitmq.Produtor;
import br.com.pires.mensageria.service.DroneService;
import br.com.pires.mensageria.service.ManutencaoService;
import javafx.animation.PauseTransition;
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

import java.time.LocalDate;
import java.util.List;

@Component
public class DronesController {

    @Autowired
    private DroneService droneService;

    @Autowired
    private ManutencaoService manutencaoService;

    @Autowired
    private Produtor produtor;

    @FXML
    private Pane paneMensagem;

    @FXML
    private Button btnAgendarManutencao;

    @FXML
    private Button btnAtualizarDados;

    @FXML
    private Button btnCadastrarDrone;

    @FXML
    private Button btnConcluirManutencao;

    @FXML
    private TableColumn<ManutencaoDroneDTO, LocalDate> columnDataAgendada;

    @FXML
    private TableColumn<ManutencaoDroneDTO, LocalDate> columnDataRealizada;

    @FXML
    private TableColumn<ManutencaoDroneDTO, String> columnDescricao;

    @FXML
    private TableColumn<ManutencaoDroneDTO, String> columnFabricante;

    @FXML
    private TableColumn<ManutencaoDroneDTO, Integer> columnId;

    @FXML
    private TableColumn<ManutencaoDroneDTO, String> columnModelo;

    @FXML
    private TableColumn<ManutencaoDroneDTO, String> columnStatus;

    @FXML
    private ComboBox<Drone> comboDrones;

    @FXML
    private ComboBox<String> comboStatusDrone;

    @FXML
    private ComboBox<String> comboStatusManutencao;

    @FXML
    private DatePicker dataAgendada;

    @FXML
    private Label lblMensagem;

    @FXML
    private TableView<ManutencaoDroneDTO> tableHistorico;

    @FXML
    private TextField txtDescricaoManutencao;

    @FXML
    private TextField txtFabricanteDrone;

    @FXML
    private TextField txtModeloDrone;

    private ObservableList<ManutencaoDroneDTO> historicoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idManutencao"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        columnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columnDataAgendada.setCellValueFactory(new PropertyValueFactory<>("dataAgendada"));
        columnDataRealizada.setCellValueFactory(new PropertyValueFactory<>("dataRealizada"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarDrones();
        carregarHistorico();
    }

    @FXML
    void agendarManutencao(ActionEvent event) {
        String mensagem = "Preencha todos os campos!";

        if (comboDrones.getSelectionModel().getSelectedItem() != null && !txtDescricaoManutencao.getText().isEmpty()
                && dataAgendada.getValue() != null && comboStatusManutencao.getSelectionModel().getSelectedItem() != null) {
            Manutencao manutencao = Manutencao.builder()
                    .drone(comboDrones.getSelectionModel().getSelectedItem())
                    .descricao(txtDescricaoManutencao.getText())
                    .dataAgendada(dataAgendada.getValue())
                    .status(comboStatusManutencao.getSelectionModel().getSelectedItem())
                    .build();

            produtor.enviarMensagemManutencao(manutencao);

            comboDrones.getSelectionModel().clearSelection();
            txtDescricaoManutencao.setText("");
            dataAgendada.setValue(null);
            comboStatusManutencao.getSelectionModel().clearSelection();

            mensagem = "Manutenção salva com sucesso!";
        }
        mostrarMensagem(mensagem);
    }

    @FXML
    void atualizarDados(ActionEvent event) {
        carregarDrones();
        carregarHistorico();
    }

    @FXML
    void cadastrarDrone(ActionEvent event) {
        String mensagem = "Preencha todos os campos!";

        if (!txtModeloDrone.getText().isEmpty() && !txtFabricanteDrone.getText().isEmpty()
                && comboStatusDrone.getSelectionModel().getSelectedItem() != null) {
            Drone drone = Drone.builder()
                    .modelo(txtModeloDrone.getText())
                    .fabricante(txtFabricanteDrone.getText())
                    .status(comboStatusDrone.getSelectionModel().getSelectedItem().toString())
                    .build();

            produtor.enviarMensagemDrone(drone);

            txtModeloDrone.setText("");
            txtFabricanteDrone.setText("");
            comboStatusDrone.getSelectionModel().clearSelection();

            mensagem = "Drone salvo com sucesso!";
        }

        mostrarMensagem(mensagem);
    }

    @FXML
    void concluirManutencao(ActionEvent event) {
        ManutencaoDroneDTO manutencaoDroneSelecionado = tableHistorico.getSelectionModel().getSelectedItem();
        String mensagem = "Selecione alguma manutenção para concluir!";

        if (manutencaoDroneSelecionado != null) {
            if (manutencaoDroneSelecionado.getStatus().equals("Concluída")) {
                mensagem = "Selecione uma manutenção que ainda não foi concluída!";
            } else {
                produtor.enviarMensagemConcluirManutencao(manutencaoDroneSelecionado.getIdManutencao());

                mensagem = "Manutenção concluida com sucesso!";
            }
        }

        mostrarMensagem(mensagem);
    }

    private void carregarDrones() {
        List<Drone> drones = droneService.listarDronesAtivos();
        comboDrones.setItems(FXCollections.observableArrayList(drones));
    }

    private void carregarHistorico() {
        historicoList.clear();
        historicoList.addAll(manutencaoService.listarHistorico());
        tableHistorico.setItems(historicoList);
    }


    private void mostrarMensagem(String mensagem) {
        lblMensagem.setText(mensagem);
        paneMensagem.setVisible(true);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(e -> paneMensagem.setVisible(false));
        pauseTransition.play();
    }

}
