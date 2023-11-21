package com.acme.mannschaft.rest;

import com.acme.mannschaft.entity.Mannschaft;
import com.acme.mannschaft.service.MannschaftReadService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.UUID;
import static com.acme.mannschaft.rest.MannschaftGetController.REST_PATH;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(REST_PATH)
@OpenAPIDefinition(info = @Info(title = "Mannschaft API", version = "v1"))
@RequiredArgsConstructor
@Slf4j
public class MannschaftGetController {
    public static final String REST_PATH = "/rest";
    public static final String ID_PATTERN = "[\\da-f]{8}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{12}";

    private final MannschaftReadService service;

    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = APPLICATION_JSON_VALUE)
    Mannschaft getById(@PathVariable final UUID id) {
        log.debug("getById: id={}, Thread={}", id, Thread.currentThread().getName());

        // Geschaeftslogik bzw. Anwendungskern
        final var mannschaft = service.findById(id);

        log.debug("getById: {}", mannschaft);
        return mannschaft;
    }

    @GetMapping(produces = HAL_JSON_VALUE)
    @Operation(summary = "Suche mit Suchkriterien", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "Collection mit den Mannschaften")
    @ApiResponse(responseCode = "404", description = "Keine Mannschaften gefunden")
    Collection<Mannschaft> get(
        @RequestParam @NonNull final MultiValueMap<String, String> suchkriterien
    ) {
        log.debug("get: suchkriterien={}", suchkriterien);
        return service.find(suchkriterien);
    }
}
