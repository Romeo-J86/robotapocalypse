package com.robotapocalypse.robotapocalypse.service.survivor.impl;

import com.robotapocalypse.robotapocalypse.domain.Survivor;
import com.robotapocalypse.robotapocalypse.domain.embeddables.Location;
import com.robotapocalypse.robotapocalypse.errohandling.InvalidRequestException;
import com.robotapocalypse.robotapocalypse.errohandling.SurvivorNotFoundException;
import com.robotapocalypse.robotapocalypse.persistence.SurvivorRepository;
import com.robotapocalypse.robotapocalypse.service.survivor.SurvivorDto;
import com.robotapocalypse.robotapocalypse.service.survivor.SurvivorSaveRequest;
import com.robotapocalypse.robotapocalypse.service.survivor.SurvivorService;
import com.robotapocalypse.robotapocalypse.util.enums.Gender;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.robotapocalypse.robotapocalypse.util.enums.Gender.FEMALE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Romeo Jerenyama
 * @created 16/12/2022 - 15:00
 */
@ExtendWith(MockitoExtension.class)
class SurvivorServiceImplTest {

    @Mock
    private SurvivorRepository survivorRepository;
    @Mock
    private ModelMapper modelMapper;
    private SurvivorService underTest;
    private Survivor survivor;


    @BeforeEach
    void setUp() {
        survivor = Survivor.builder()
                .id(1L)
                .firstName("Albert")
                .lastName("Einstein")
                .location(new Location())
                .build();
        underTest = new SurvivorServiceImpl(survivorRepository, modelMapper);
    }


    @Test
    void canSaveSurvivor() {
        //GIVEN
        SurvivorSaveRequest survivorSaveRequest = new SurvivorSaveRequest();
        survivorSaveRequest.setAge(18);
        survivorSaveRequest.setGender(FEMALE.name());
        survivorSaveRequest.setFirstName("Lissa");
        survivorSaveRequest.setLastName("Nadim");
        survivorSaveRequest.setLocation(new Location());

        //WHEN
        underTest.saveSurvivor(survivorSaveRequest);

        //THEN
        Survivor expectedSurvivor = Survivor.builder()
                .firstName(survivorSaveRequest.getFirstName())
                .lastName(survivorSaveRequest.getLastName())
                .gender(Gender.valueOf(survivorSaveRequest.getGender().toUpperCase()))
                .age(survivorSaveRequest.getAge())
                .location(survivorSaveRequest.getLocation())
                .infectionStatus(InfectionStatus.NON_INFECTED)
                .dateCreated(LocalDate.now())
                .infectionReportTracker(Integer.valueOf(0))
                .build();

        ArgumentCaptor<Survivor> survivorArgumentCaptor =
                ArgumentCaptor.forClass(Survivor.class);

        verify(survivorRepository).save(survivorArgumentCaptor.capture());

        Survivor capturedSurvivor = survivorArgumentCaptor.getValue();

        assertThat(capturedSurvivor).isEqualTo(expectedSurvivor);
    }
    @Test
    @DisplayName("given a negative value for age InvalidRequestException is thrown")
    void saveSurvivorThrowsInvalidRequestExceptionWhenGivenNegativeValueForAge(){
        int age = -23;

        SurvivorSaveRequest survivorSaveRequest = new SurvivorSaveRequest();
        survivorSaveRequest.setAge(age);
        survivorSaveRequest.setGender(Gender.FEMALE.name());
        survivorSaveRequest.setFirstName("Lissa");
        survivorSaveRequest.setLastName("Nadim");
        survivorSaveRequest.setLocation(new Location());

        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.saveSurvivor(survivorSaveRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Survivor age %s cannot be negative", survivorSaveRequest.getAge());

    }

    @Test
    @DisplayName("Save Survivor Method Throws Invalid Request Exception When Passed Null Request")
    void saveSurvivorMethodThrowsInvalidRequestExceptionWhenPassedNullRequest() {
        //GIVEN
        SurvivorSaveRequest survivorSaveRequest = null;
        //WHEN


        //THEN
        assertThatThrownBy(() -> underTest.saveSurvivor(survivorSaveRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Survivor request cannot be null");
    }

    @Test
    @DisplayName("can update Location method update survivor location")
    void canUpdateLocation() {
        //GIVEN

        Location location = new Location();
        location.setLatitude(-17.840050);
        location.setLatitude(30.983880);

        given(survivorRepository.findById(1L))
                .willReturn(Optional.ofNullable(survivor));


        //WHEN
        underTest.updateLocation(1L, location);

        //THEN
        Survivor expectedSurvivor = Survivor.builder()
                .id(1L)
                .firstName("Albert")
                .lastName("Einstein")
                .location(location)
                .build();

        ArgumentCaptor<Survivor> survivorArgumentCaptor =
                ArgumentCaptor.forClass(Survivor.class);

        verify(survivorRepository).save(survivorArgumentCaptor.capture());

        Survivor capturedSurvivor = survivorArgumentCaptor.getValue();

        assertThat(capturedSurvivor).isEqualTo(expectedSurvivor);
    }

    @Test
    @DisplayName("Update Location Throws Survivor Not FoundException")
    void updateLocation() {
        //GIVEN
        Location location = new Location();
        location.setLatitude(-17.840050);
        location.setLatitude(30.983880);

        long survivorId = 1L;

        given(survivorRepository.findById(survivorId))
                .willReturn(Optional.ofNullable(null));


        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.updateLocation(survivorId, location))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining(String.format("Survivor with id %s was not found", survivorId));
    }

    @Test
    @DisplayName("Get Survivors By InfectionStatus method Throws InvalidParameterException When Passed Invalid InfectionStatus")
    void getSurvivorsByInfectionStatusThrowsInvalidParameterExceptionWhenPassedInvalidInfectionStatus() {
        //GIVEN
        InfectionStatus infectionStatus = InfectionStatus.INFECTED;



        given(survivorRepository.getSurvivorsByInfectionStatus(infectionStatus))
                .willReturn(Optional.ofNullable(null));

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.getSurvivorsByInfectionStatus(infectionStatus))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining("Survivors with Infection Status %s not found",infectionStatus.name());
    }

    @Test
    @DisplayName("Can Get Survivors By Infection Status")
    void canGetSurvivorsByInfectionStatus() {
        //GIVEN
        InfectionStatus infectionStatus = InfectionStatus.INFECTED;
        List<Survivor> survivors = new ArrayList<>();

        Survivor survivor2 = Survivor.builder()
                .id(1L)
                .firstName("Lionel")
                .lastName("Samvura")
                .location(new Location())
                .infectionStatus(InfectionStatus.INFECTED)
                .build();
        survivors.add(survivor);
        survivors.add(survivor2);

        given(survivorRepository.getSurvivorsByInfectionStatus(infectionStatus))
                .willReturn(Optional.ofNullable(survivors));

        //WHEN
        List<SurvivorDto> result = underTest.getSurvivorsByInfectionStatus(infectionStatus);

        //THEN
        assertThat(result.size() == 2);
    }

    @Test
    @DisplayName("Can Get Percentage Of Infection Status")
    void getInfectedOrNonInfectedPercentage() {
        //GIVEN
        InfectionStatus infectionStatus = InfectionStatus.INFECTED;

        given(survivorRepository.countByInfectionStatus(infectionStatus))
                .willReturn(50L);

        given(survivorRepository.count())
                .willReturn(100L);

        //WHEN
        Double result = underTest.getInfectedOrNonInfectedPercentage(infectionStatus);

        //THEN
        Double expectedResult = Double.valueOf(50 / 100 * 100);
        assertThat(result == expectedResult);
    }
}