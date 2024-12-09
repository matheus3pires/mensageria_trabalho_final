package br.com.pires.mensageria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drones")
@Builder(toBuilder = true)
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDrone;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(nullable = false, length = 100)
    private String fabricante;

    @Column(nullable = false, length = 10)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Manutencao> manutencoes;

    @Override
    public String toString() {
        return modelo + " - " + fabricante ;
    }
}
