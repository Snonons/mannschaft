package com.acme.mannschaft.graphql;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

record Suchkriterien (
    String name,
    String gruendungsjahr
) {
    Map<String, List<String>> toMap() {
        final Map<String, List<String>> map = new HashMap<>(2, 1);
        if (name != null) {
            map.put("name", List.of(name));
        }
        if (gruendungsjahr != null) {
            map.put("gruendungsjahr", List.of(gruendungsjahr));
        }
        return map;
    }
}
