package com.acme.mannschaft.graphql;

import com.acme.mannschaft.entity.Mannschaft;
import com.acme.mannschaft.entity.Spieler;
import com.acme.mannschaft.service.MannschaftReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static java.util.Collections.emptyMap;

@Controller
@RequiredArgsConstructor
@Slf4j
class MannschaftQueryController {
    private final MannschaftReadService service;

    @QueryMapping("mannschaft")
    Mannschaft findById(@Argument final UUID id) {
        log.debug("findById: id={}", id);
        final var mannschaft = service.findById(id);
        log.debug("findById: mannschaft={}", mannschaft);
        return mannschaft;
    }

    @QueryMapping("mannschaften")
    Collection<Mannschaft> find(@Argument final Optional<Suchkriterien> input) {
        log.debug("find: suchkriterien={}", input);
        final var suchkriterien = input.map(Suchkriterien::toMap).orElse(emptyMap());
        final var mannschaften = service.find(suchkriterien);
        log.debug("find: mannschaften={}", mannschaften);
        return mannschaften;
    }
}
