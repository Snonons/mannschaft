package com.acme.mannschaft.graphql;

import com.acme.mannschaft.entity.Mannschaft;
import com.acme.mannschaft.entity.Spieler;
import com.acme.mannschaft.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
interface MannschaftInputMapper {
    @Mapping(target = "id", ignore = true)
    Mannschaft toMannschaft(MannschaftInput input);

    Spieler toSpieler(SpielerInput input);

    Trainer toTrainer(TrainerInput input);
}
