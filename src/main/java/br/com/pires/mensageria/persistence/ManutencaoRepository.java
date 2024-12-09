package br.com.pires.mensageria.persistence;

import br.com.pires.mensageria.dto.ManutencaoDroneDTO;
import br.com.pires.mensageria.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Integer> {

    @Query("""
        SELECT new br.com.pires.mensageria.dto.ManutencaoDroneDTO(
               m.idManutencao,
               d.modelo,
               d.fabricante,
               m.descricao,
               m.dataAgendada,
               m.dataRealizada,
               m.status)
        FROM Manutencao m
        JOIN m.drone d
        ORDER BY m.idManutencao ASC
    """)
    List<ManutencaoDroneDTO> listarHistorico();
}
