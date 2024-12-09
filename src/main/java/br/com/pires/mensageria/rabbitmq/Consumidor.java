package br.com.pires.mensageria.rabbitmq;

import br.com.pires.mensageria.model.Drone;
import br.com.pires.mensageria.model.Manutencao;
import br.com.pires.mensageria.service.DroneService;
import br.com.pires.mensageria.service.ManutencaoService;
import com.rabbitmq.client.Channel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumidor {

    private final DroneService droneService;
    private final ManutencaoService manutencaoService;
    private final ObjectMapper objectMapper;

    public Consumidor(DroneService droneService, ManutencaoService manutencaoService, ObjectMapper objectMapper) {
        this.droneService = droneService;
        this.manutencaoService = manutencaoService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${queue.drones.name}", ackMode = "MANUAL")
    public void receberMensagemDrone(@Payload String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            Drone drone = objectMapper.readValue(message, Drone.class);
            droneService.salvar(drone);

            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "${queue.manutencoes.name}", ackMode = "MANUAL")
    public void receberMensagemManutencao(@Payload String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            if (message.startsWith("{")) {
                Manutencao manutencao = objectMapper.readValue(message, Manutencao.class);

                manutencaoService.salvar(manutencao);
                droneService.desativarDrone(manutencao.getDrone().getIdDrone());
            } else {
                Integer idManutencao = Integer.valueOf(message);

                Integer idDrone = manutencaoService.concluirManutencao(idManutencao);
                droneService.ativarDrone(idDrone);
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
            channel.basicNack(tag, false, true);
        }
    }


}

