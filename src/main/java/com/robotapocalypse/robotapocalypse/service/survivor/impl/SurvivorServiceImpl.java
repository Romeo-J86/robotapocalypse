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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Double.valueOf;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 12:44
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SurvivorServiceImpl implements SurvivorService {

    private final SurvivorRepository survivorRepository;
    private final ModelMapper modelMapper;
    @Override
    public SurvivorDto saveSurvivor(SurvivorSaveRequest survivorSaveRequest) {
        if(isNull(survivorSaveRequest)) {
            throw new InvalidRequestException("Survivor request cannot be null");
        }
        if (survivorSaveRequest.getAge() < 0){
            throw new InvalidRequestException(
                    String.format("Survivor age %s cannot be negative", survivorSaveRequest.getAge())
            );
        }
        log.info("Adding survivor, survivorSaveRequest = {}",survivorSaveRequest);
        Survivor survivor = Survivor.builder()
                .firstName(survivorSaveRequest.getFirstName())
                .lastName(survivorSaveRequest.getLastName())
                .gender(Gender.valueOf(survivorSaveRequest.getGender().toUpperCase()))
                .age(survivorSaveRequest.getAge())
                .location(survivorSaveRequest.getLocation())
                .infectionStatus(InfectionStatus.NON_INFECTED)
                .dateCreated(LocalDate.now())
                .infectionReportTracker(Integer.valueOf(0))
                .build();
        return convertSurvivorToDto(survivorRepository.save(survivor));
    }

    @Override
    public SurvivorDto updateLocation(Long survivorId,Location location) {
        Survivor survivor = survivorRepository.findById(survivorId)
                .orElseThrow(() -> new SurvivorNotFoundException(
                        String.format("Survivor with id %s was not found", survivorId)
                ));
        survivor.setLocation(location);
        Survivor savedSurvivor = survivorRepository.save(survivor);
        return convertSurvivorToDto(savedSurvivor);
    }

    @Override
    public List<SurvivorDto> getSurvivorsByInfectionStatus(InfectionStatus infectionStatus) {
        requireNonNull(infectionStatus, "InfectionStatus cannot be null");
        List<Survivor> survivors = survivorRepository
                .getSurvivorsByInfectionStatus(infectionStatus)
                .orElseThrow(
                        ()-> new SurvivorNotFoundException(
                                String.format("Survivors with Infection Status %s not found",infectionStatus.name())
                        )
                );

        return survivors.stream().map(survivor -> convertSurvivorToDto(survivor))
                .collect(Collectors.toList());
    }

    @Override
    public Double getInfectedOrNonInfectedPercentage(InfectionStatus infectedStatus) {
        requireNonNull(infectedStatus, "InfectionStatus cannot be null");
        Long countByInfectionStatus = survivorRepository.countByInfectionStatus(infectedStatus);

        Long allSurvivorsCount = survivorRepository.count();

        log.info("Survivors Count: {}",allSurvivorsCount);

        return  valueOf((countByInfectionStatus / allSurvivorsCount) * 100);

    }

    private SurvivorDto convertSurvivorToDto(Survivor survivor) {
        SurvivorDto survivorDto = modelMapper.map(survivor, SurvivorDto.class);
        return survivorDto;
    }
}
