package com.ims.backend.service;

import com.ims.backend.entity.Instrument;
import com.ims.backend.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentService {

  private final InstrumentRepository instrumentRepository;

  @Autowired
  public InstrumentService(InstrumentRepository instrumentRepository) {
    this.instrumentRepository = instrumentRepository;
  }

  public List<Instrument> getAllInstruments() {
    return instrumentRepository.findAll();
  }

  public Optional<Instrument> getInstrumentById(Long id) {
    return instrumentRepository.findById(id);
  }

  public Instrument saveInstrument(Instrument instrument) {
    return instrumentRepository.save(instrument);
  }

  public void deleteInstrumentById(Long id) {
    instrumentRepository.deleteById(id);
  }
}
