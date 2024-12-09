package br.com.pires.mensageria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "manutencoes")
@Builder(toBuilder = true)
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idManutencao;

    @ManyToOne
    @JoinColumn(name = "id_drone", nullable = false)
    private Drone drone;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataAgendada;

    @Column
    private LocalDate dataRealizada;

    @Column(nullable = false, length = 10)
    private String status;
}
