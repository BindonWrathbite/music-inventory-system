package com.ims.backend.service;

import com.ims.backend.dto.InstrumentDTO;
import com.ims.backend.entity.Instrument;
import com.ims.backend.entity.Location;
import com.ims.backend.entity.Student;
import com.ims.backend.mapper.InstrumentMapper;
import com.ims.backend.repository.InstrumentRepository;
import com.ims.backend.repository.LocationRepository;
import com.ims.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstrumentService {

  private final InstrumentRepository instrumentRepository;
  private final LocationRepository locationRepository;
  private final StudentRepository studentRepository;

  @Autowired
  public InstrumentService(InstrumentRepository instrumentRepository,
                           LocationRepository locationRepository,
                           StudentRepository studentRepository) {
    this.instrumentRepository = instrumentRepository;
    this.locationRepository = locationRepository;
    this.studentRepository = studentRepository;
  }

  public List<InstrumentDTO> getAllInstruments() {
    return instrumentRepository.findAll().stream()
            .map(InstrumentMapper::toDTO)
            .collect(Collectors.toList());
  }

  public Optional<InstrumentDTO> getInstrumentById(Long id) {
    return instrumentRepository.findById(id)
            .map(InstrumentMapper::toDTO);
  }

  public InstrumentDTO saveInstrument(InstrumentDTO dto) {
    Instrument instrument = InstrumentMapper.toEntity(dto, null, null);
    Instrument saved = instrumentRepository.save(instrument);
    return InstrumentMapper.toDTO(saved);
  }

  public void deleteInstrumentById(Long id) {
    instrumentRepository.deleteById(id);
  }
}
