package com.robotapocalypse.robotapocalypse.service.inventory;

import com.robotapocalypse.robotapocalypse.util.enums.InventoryType;
import lombok.Data;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 17:36
 */
@Data
public class InventoryRequest {
    private Long survivorId;
    private InventoryType inventoryType;
    private long quantity;
    private boolean activeStatus;
}
