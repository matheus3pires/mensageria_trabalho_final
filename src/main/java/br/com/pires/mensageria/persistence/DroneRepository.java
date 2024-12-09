package br.com.pires.mensageria.persistence;

import br.com.pires.mensageria.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {

    List<Drone> findByStatus(String status);
}
