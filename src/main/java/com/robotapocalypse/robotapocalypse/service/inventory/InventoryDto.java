package com.robotapocalypse.robotapocalypse.service.inventory;

import com.robotapocalypse.robotapocalypse.util.enums.InventoryType;
import com.robotapocalypse.robotapocalypse.util.enums.UnitOfMeasure;
import lombok.Data;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 17:28
 */
@Data
public class InventoryDto {

    private Long id;
    private Long survivorId;
    private UnitOfMeasure unitOfMeasure;
    private InventoryType inventoryType;
    private long quantity;
    private boolean activeStatus;
}
