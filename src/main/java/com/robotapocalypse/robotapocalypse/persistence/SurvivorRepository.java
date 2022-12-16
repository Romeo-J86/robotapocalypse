package com.robotapocalypse.robotapocalypse.persistence;

import com.robotapocalypse.robotapocalypse.domain.Survivor;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:34
 */
@Repository
public interface SurvivorRepository extends JpaRepository<Survivor,Long> {
    Optional<List<Survivor>> getSurvivorsByInfectionStatus(InfectionStatus infectionStatus);

    Integer countAllByInfectionStatus(InfectionStatus infectionStatus);

    Long countByInfectionStatus(InfectionStatus infectionStatus);
}
