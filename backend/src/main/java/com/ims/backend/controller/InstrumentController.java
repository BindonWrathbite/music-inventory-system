package com.ims.backend.controller;

import com.ims.backend.entity.Instrument;
import com.ims.backend.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instruments")
public class InstrumentController {

  private final InstrumentService instrumentService;

  @Autowired
  public InstrumentController(InstrumentService instrumentService) {
    this.instrumentService = instrumentService;
  }

  @GetMapping
  public ResponseEntity<List<Instrument>> getAllInstruments() {
    return ResponseEntity.ok(instrumentService.getAllInstruments());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Instrument> getInstrumentById(@PathVariable("id") Long id) {
    return instrumentService.getInstrumentById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Instrument> createInstrument(@RequestBody Instrument instrument) {
    return ResponseEntity.ok(instrumentService.saveInstrument(instrument));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInstrument(@PathVariable("id") Long id) {
    instrumentService.deleteInstrumentById(id);
    return ResponseEntity.noContent().build();
  }
}
