package com.acme.mannschaft.rest;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("RecordComponentNumber")
record MannschaftDTO(
    String name,
    LocalDate gruendungsjahr,
    List<SpielerDTO> spielerList,
    TrainerDTO trainer
) {
}
