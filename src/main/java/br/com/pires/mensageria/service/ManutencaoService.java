package br.com.pires.mensageria.service;

import br.com.pires.mensageria.dto.ManutencaoDroneDTO;
import br.com.pires.mensageria.model.Manutencao;
import br.com.pires.mensageria.persistence.ManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public void salvar(Manutencao manutencao){
        manutencaoRepository.save(manutencao);
    }

    public List<ManutencaoDroneDTO> listarHistorico() {
       return manutencaoRepository.listarHistorico();
    }

    public Integer concluirManutencao(Integer idManutencao) {
        Manutencao manutencao = manutencaoRepository.findById(idManutencao).orElse(null);

        if(manutencao != null){
            manutencao.setDataRealizada(LocalDate.now());
            manutencao.setStatus("Concluída");
            manutencaoRepository.save(manutencao);

            return manutencao.getDrone().getIdDrone();
        }

        return null;
    }
}
