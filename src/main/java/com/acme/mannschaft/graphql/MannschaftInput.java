package com.acme.mannschaft.graphql;

import java.util.List;

record MannschaftInput (
    String name,
    String gruendungsjahr,
    List<SpielerInput> spielerList,
    TrainerInput trainer
){
}
