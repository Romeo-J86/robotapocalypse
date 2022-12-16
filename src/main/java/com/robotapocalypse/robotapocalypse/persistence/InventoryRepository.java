package com.robotapocalypse.robotapocalypse.persistence;

import com.robotapocalypse.robotapocalypse.domain.Inventory;
import com.robotapocalypse.robotapocalypse.domain.Survivor;
import com.robotapocalypse.robotapocalypse.service.inventory.InventoryDto;
import com.robotapocalypse.robotapocalypse.service.inventory.InventoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 17:27
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Page<Inventory> getInventoryBySurvivor(Survivor survivor, Pageable pageable);

    List<Inventory> getInventoryBySurvivor(Survivor survivor);
}
