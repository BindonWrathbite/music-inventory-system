package com.ims.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "instrument", schema = "public")
@Getter
@Setter
public class Instrument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "brand")
  private String brand;

  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "inventory_number", nullable = false)
  private String inventoryNumber;

  @Column(name = "repairs")
  private String repairs;

  @Column(name = "condition", nullable = false)
  private String condition;

  @Column(name = "purchase_date")
  private LocalDate purchaseDate;

  @Column(name = "purchase_price")
  private BigDecimal purchasePrice;

  @Column(name = "notes")
  private String notes;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student assignedStudent;
}
