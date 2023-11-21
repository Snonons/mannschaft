package com.acme.mannschaft.graphql;

import com.acme.mannschaft.entity.Mannschaft;
import com.acme.mannschaft.service.ConstraintViolationsException;
import com.acme.mannschaft.service.MannschaftWriteService;
import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

@Controller
@RequiredArgsConstructor
@Slf4j
class MannschaftMutationController {
    private final MannschaftWriteService service;
    private final MannschaftInputMapper mapper;

    @MutationMapping
    CreatePayload create(@Argument final MannschaftInput input) {
        log.debug("create: input={}", input);
        final var mannschaftNew = mapper.toMannschaft(input);
        final var id = service.create(mannschaftNew).getId();
        log.debug("create: id={}", id);
        return new CreatePayload(id);
    }

    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    GraphQLError onDateTimeParseException(final DateTimeParseException ex) {
        final List<Object> path = List.of("input", "geburtsdatum");
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(STR."Das Datum \{ex.getParsedString()} ist nicht korrekt.")
            .path(path)
            .build();
    }

    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    Collection<GraphQLError> onConstraintViolations(final ConstraintViolationsException ex) {
        return ex.getViolations()
            .stream()
            .map(this::violationToGraphQLError)
            .collect(Collectors.toList());
    }

    private GraphQLError violationToGraphQLError(final ConstraintViolation<Mannschaft> violation) {
        final List<Object> path = new ArrayList<>(5);
        path.add("input");
        for (final Path.Node node: violation.getPropertyPath()) {
            path.add(node.toString());
        }
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(violation.getMessage())
            .path(path)
            .build();
    }
}
