package com.acme.mannschaft.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
public class Mannschaft {
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    private String name;

    @Past
    private LocalDate gruendungsjahr;

    @Valid
    @NotNull
    private List<Spieler> spielerList;

    @NotNull
    private Trainer trainer;
}
