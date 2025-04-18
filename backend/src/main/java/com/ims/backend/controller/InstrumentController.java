package com.ims.backend.controller;

import com.ims.backend.dto.InstrumentDTO;
import com.ims.backend.entity.Instrument;
import com.ims.backend.service.InstrumentService;
import com.ims.backend.repository.InstrumentRepository;
import com.ims.backend.mapper.InstrumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/instruments")
public class InstrumentController {

  private final InstrumentService instrumentService;

  @Autowired
  public InstrumentController(InstrumentService instrumentService) {
    this.instrumentService = instrumentService;
  }

  @GetMapping
  public ResponseEntity<List<InstrumentDTO>> getAllInstruments() {
    return ResponseEntity.ok(instrumentService.getAllInstruments());
  }

  @GetMapping("/{id}")
  public ResponseEntity<InstrumentDTO> getInstrumentById(@PathVariable("id") Long id) {
    return instrumentService.getInstrumentById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<InstrumentDTO> createInstrument(@RequestBody InstrumentDTO instrumentDTO) {
    InstrumentDTO saved = instrumentService.saveInstrument(instrumentDTO);
    return ResponseEntity.ok(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InstrumentDTO> updateInstrument(
          @PathVariable Long id,
          @RequestBody InstrumentDTO updatedDto
  ) {
    return instrumentService.updateInstrument(id, updatedDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInstrument(@PathVariable("id") Long id) {
    instrumentService.deleteInstrumentById(id);
    return ResponseEntity.noContent().build();
  }
}
