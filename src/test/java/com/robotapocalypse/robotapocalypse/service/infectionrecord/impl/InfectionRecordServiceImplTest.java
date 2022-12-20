package com.robotapocalypse.robotapocalypse.service.infectionrecord.impl;

import com.robotapocalypse.robotapocalypse.domain.Inventory;
import com.robotapocalypse.robotapocalypse.domain.Survivor;
import com.robotapocalypse.robotapocalypse.domain.embeddables.Location;
import com.robotapocalypse.robotapocalypse.errohandling.InvalidRequestException;
import com.robotapocalypse.robotapocalypse.errohandling.InventoryNotFoundException;
import com.robotapocalypse.robotapocalypse.errohandling.SurvivorNotFoundException;
import com.robotapocalypse.robotapocalypse.persistence.InventoryRepository;
import com.robotapocalypse.robotapocalypse.persistence.SurvivorRepository;
import com.robotapocalypse.robotapocalypse.service.inventory.InventoryRequest;
import com.robotapocalypse.robotapocalypse.service.inventory.InventoryService;
import com.robotapocalypse.robotapocalypse.service.inventory.impl.InventoryServiceImpl;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;
import com.robotapocalypse.robotapocalypse.util.enums.InventoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


/**
 * @author Romeo Jerenyama
 * @created 16/12/2022 - 19:59
 */
@ExtendWith(MockitoExtension.class)
class InfectionRecordServiceImplTest {
    @Mock
    private  ModelMapper modelMapper;
    @Mock
    private  InventoryRepository inventoryRepository;
    @Mock
    private  SurvivorRepository survivorRepository;

    private Survivor survivor;
    private InventoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new InventoryServiceImpl(modelMapper, inventoryRepository,survivorRepository);
        Location location = new Location();
        location.setLatitude(211.012d);
        location.setLongitude(574.214d);
        survivor = Survivor.builder()
                .id(1l)
                .firstName("Romeo")
                .lastName("Jerenyama")
                .location(location)
                .infectionStatus(InfectionStatus.INFECTED)
                .build();;
    }

    @Test
    void canGetInventoryBySurvivor() {
        //GIVEN

        given(survivorRepository.findById(survivor.getId()))
                .willReturn(Optional.ofNullable(survivor));
        //WHEN
        underTest.getInventoryBySurvivor(survivor.getId());
        //THEN
        verify(inventoryRepository).getInventoryBySurvivor(survivor);
    }

    @Test
    @DisplayName("getInventoryBySurvivor method Throws SurvivorNotFoundException When Survivor Not Found")
    void canGetInventoryBySurvivorThrowsSurvivorNotFoundExceptionWhenSurvivorNotFound() {
        //GIVEN
        long survivorId = survivor.getId();

        given(survivorRepository.findById(survivorId))
                .willReturn(Optional.ofNullable(null));
        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.getInventoryBySurvivor(survivorId))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining( String.format("Survivor with id %s was not found", survivorId));
    }

    @Test
    void canSaveInventory() {
        //GIVEN

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(survivor.getId());
        inventoryRequest.setActiveStatus(true);

        given(survivorRepository.findById(inventoryRequest.getSurvivorId()))
                .willReturn(Optional.ofNullable(survivor));

        //WHEN
        underTest.saveInventory(inventoryRequest);

        //THEN
        Inventory expectedInventory = Inventory.builder()
                .inventoryType(inventoryRequest.getInventoryType())
                .quantity(inventoryRequest.getQuantity())
                .survivor(survivor)
                .activeStatus(true)
                .build();

        ArgumentCaptor<Inventory> inventoryArgumentCaptor =
                ArgumentCaptor.forClass(Inventory.class);

        verify(inventoryRepository).save(inventoryArgumentCaptor.capture());

        Inventory capturedInventory = inventoryArgumentCaptor.getValue();

        assertThat(capturedInventory).isEqualTo(expectedInventory);
    }

    @Test
    @DisplayName("canSaveInventory method Throws Survivor Not Found Exception When Survivor Not Found")
    void canSaveInventoryThrowsSurvivorNotFoundExceptionWhenSurvivorNotFound() {
        //GIVEN
        long survivorId = 1L;

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(survivorId);
        inventoryRequest.setActiveStatus(true);

        given(survivorRepository.findById(inventoryRequest.getSurvivorId()))
                .willReturn(Optional.ofNullable(null));

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.saveInventory(inventoryRequest))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining(String.format("Survivor with id %s was not found", inventoryRequest.getSurvivorId()));
    }

    @Test@DisplayName("given negative value for quantity InvalidRequestException is thrown")
    void saveInventoryThrowsInvalidRequestExceptionWhenGivenNegativeValueForQuantity(){

        given(survivorRepository.findById(survivor.getId()))
                .willReturn(Optional.ofNullable(survivor));

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(-3);
        inventoryRequest.setSurvivorId(survivor.getId());

        assertThatThrownBy(() -> underTest.saveInventory(inventoryRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining(String.format("Quantity, %s, cannot be negative", inventoryRequest.getQuantity()));

    }

    @Test
    void canUpdateInventory() {
        //GIVEN
        long inventoryId = survivor.getId();

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(survivor.getId());
        inventoryRequest.setActiveStatus(true);

        Inventory expectedInventory = Inventory.builder()
                .inventoryType(inventoryRequest.getInventoryType())
                .quantity(inventoryRequest.getQuantity())
                .survivor(survivor)
                .activeStatus(true)
                .build();

        given( inventoryRepository.findById(inventoryId))
                .willReturn(Optional.ofNullable( expectedInventory));

        //WHEN
        underTest.updateInventory(inventoryId,inventoryRequest);

        //THEN

        ArgumentCaptor<Inventory> inventoryArgumentCaptor =
                ArgumentCaptor.forClass(Inventory.class);

        verify(inventoryRepository).save(inventoryArgumentCaptor.capture());

        Inventory capturedInventory = inventoryArgumentCaptor.getValue();

        assertThat(capturedInventory).isEqualTo(expectedInventory);
    }
    @Test
    void canUpdateInventoryThrowsInventoryNotFoundExceptionWhenInventoryNotFound() {
        //GIVEN
        long inventoryId=1L;
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(1L);
        inventoryRequest.setActiveStatus(true);

        given( inventoryRepository.findById(inventoryId))
                .willReturn(Optional.ofNullable( null));

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.updateInventory(inventoryId,inventoryRequest))
                .isInstanceOf(InventoryNotFoundException.class)
                .hasMessageContaining( String.format("Survivor with id %s was not found", inventoryId));
    }
}