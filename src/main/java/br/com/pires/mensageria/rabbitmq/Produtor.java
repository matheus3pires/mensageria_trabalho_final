package br.com.pires.mensageria.rabbitmq;

import br.com.pires.mensageria.model.Drone;
import br.com.pires.mensageria.model.Manutencao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Produtor {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${queue.drones.name}")
    private String queueDrone;

    @Value("${queue.manutencoes.name}")
    private String queueManutencao;

    public Produtor(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarMensagemDrone(Drone drone) {
        try {
            String droneJson = objectMapper.writeValueAsString(drone);

            rabbitTemplate.convertAndSend(queueDrone, droneJson);
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    public void enviarMensagemManutencao(Manutencao manutencao) {
        try {
            String manutencaoJson = objectMapper.writeValueAsString(manutencao);

            rabbitTemplate.convertAndSend(queueManutencao, manutencaoJson);
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    public void enviarMensagemConcluirManutencao(Integer idManutencao) {
        try {
            rabbitTemplate.convertAndSend(queueManutencao, idManutencao.toString());
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem para concluir manutenção: " + e.getMessage());
        }
    }


}