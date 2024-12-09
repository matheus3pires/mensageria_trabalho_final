package br.com.pires.mensageria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ManutencaoDroneDTO {

    private Integer idManutencao;
    private String modelo;
    private String fabricante;
    private String descricao;
    private LocalDate dataAgendada;
    private LocalDate dataRealizada;
    private String status;

    public ManutencaoDroneDTO(Integer idManutencao, String modelo, String fabricante, String descricao, LocalDate dataAgendada, LocalDate dataRealizada, String status) {
        this.idManutencao = idManutencao;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.descricao = descricao;
        this.dataAgendada = dataAgendada;
        this.dataRealizada = dataRealizada;
        this.status = status;
    }
}
