package com.robotapocalypse.robotapocalypse.persistence;

import com.robotapocalypse.robotapocalypse.domain.InfectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 21:03
 */
@Repository
public interface InfectionRecordRepository extends JpaRepository<InfectionRecord, Long> {
}
