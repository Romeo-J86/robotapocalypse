package com.robotapocalypse.robotapocalypse.service.infectionrecord.impl;

import com.robotapocalypse.robotapocalypse.domain.InfectionRecord;
import com.robotapocalypse.robotapocalypse.domain.Survivor;
import com.robotapocalypse.robotapocalypse.errohandling.SurvivorNotFoundException;
import com.robotapocalypse.robotapocalypse.persistence.InfectionRecordRepository;
import com.robotapocalypse.robotapocalypse.persistence.SurvivorRepository;
import com.robotapocalypse.robotapocalypse.service.infectionrecord.InfectionRecordDto;
import com.robotapocalypse.robotapocalypse.service.infectionrecord.InfectionRecordRequest;
import com.robotapocalypse.robotapocalypse.service.infectionrecord.InfectionRecordService;
import com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus.INFECTED;
import static com.robotapocalypse.robotapocalypse.util.enums.InfectionStatus.NON_INFECTED;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 21:07
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InfectionRecordServiceImpl implements InfectionRecordService {

    private final InfectionRecordRepository infectionRecordRepository;
    private final SurvivorRepository survivorRepository;
    private final ModelMapper modelMapper;
    @Override
    public InfectionRecordDto reportInfection(InfectionRecordRequest infectionRecordRequest) {
        requireNonNull(infectionRecordRequest,"InfectionRecordRequest cannot be null");
        log.info("Reporting survivor infection , infectionRecordRequest = {}", infectionRecordRequest);
        Survivor survivorReported = survivorRepository.findById(infectionRecordRequest.getSurvivorReportedId())
                 .orElseThrow(
                         () -> new SurvivorNotFoundException(
                                 format("Survivor being reported with id %s not found",
                                         infectionRecordRequest.getSurvivorReportedId())
                         )
                 );

        Survivor reportedBy = survivorRepository.findById(infectionRecordRequest.getReportedById())
                .orElseThrow(
                        () -> new SurvivorNotFoundException(
                                format("Survivor Reporting Infection with id %s not found",
                                        infectionRecordRequest.getReportedById())
                        )
                );

        InfectionRecord infectionRecord = InfectionRecord.builder()
                .reportedById(reportedBy.getId())
                .reportedBy(reportedBy.getFirstName())
                .survivorReportedId(survivorReported.getId())
                .survivorReported(survivorReported.getFirstName())
                .dateReported(LocalDateTime.now())
                .build();
        if (Objects.equals(survivorReported.getInfectionStatus(), NON_INFECTED)){
            survivorReported.setInfectionReportTracker(survivorReported.getInfectionReportTracker() + 1);
        }

        if (survivorReported.getInfectionReportTracker() == 3) {
            survivorReported.setInfectionStatus(INFECTED);
        }

        survivorRepository.save(survivorReported);

        return convertInfectionRecordToDto(infectionRecordRepository.save(infectionRecord));
    }
    private InfectionRecordDto convertInfectionRecordToDto(InfectionRecord infectionRecord) {
        InfectionRecordDto infectionRecordDto = modelMapper.map(infectionRecord, InfectionRecordDto.class);
        return infectionRecordDto;
    }
}
