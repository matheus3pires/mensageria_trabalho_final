package br.com.pires.mensageria.service;

import br.com.pires.mensageria.model.Drone;
import br.com.pires.mensageria.persistence.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    public void salvar(Drone drone){
        droneRepository.save(drone);
    }

    public List<Drone> listarDronesAtivos() {
        return droneRepository.findByStatus("Ativo");
    }

    public void ativarDrone(Integer idDrone){
        Drone drone = droneRepository.findById(idDrone).orElse(null);

        if(drone != null){
            drone.setStatus("Ativo");
            droneRepository.save(drone);
        }
    }

    public void desativarDrone(Integer idDrone){
        Drone drone = droneRepository.findById(idDrone).orElse(null);

        if(drone != null){
            drone.setStatus("Inativo");
            droneRepository.save(drone);
        }
    }
}
