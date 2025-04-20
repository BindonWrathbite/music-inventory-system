package com.ims.backend.controller;

import com.ims.backend.dto.InstrumentDTO;
import com.ims.backend.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Anyone logged in can view instruments
  public ResponseEntity<List<InstrumentDTO>> getAllInstruments() {
    return ResponseEntity.ok(instrumentService.getAllInstruments());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // View individual instrument
  public ResponseEntity<InstrumentDTO> getInstrumentById(@PathVariable("id") Long id) {
    return instrumentService.getInstrumentById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Add instrument
  public ResponseEntity<InstrumentDTO> createInstrument(@RequestBody InstrumentDTO instrumentDTO) {
    InstrumentDTO saved = instrumentService.saveInstrument(instrumentDTO);
    return ResponseEntity.ok(saved);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Edit instrument
  public ResponseEntity<InstrumentDTO> updateInstrument(
          @PathVariable Long id,
          @RequestBody InstrumentDTO updatedDto
  ) {
    return instrumentService.updateInstrument(id, updatedDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Delete instrument
  public ResponseEntity<Void> deleteInstrument(@PathVariable("id") Long id) {
    instrumentService.deleteInstrumentById(id);
    return ResponseEntity.noContent().build();
  }
}
