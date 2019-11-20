package com.walidoow.werewolf.player;

public enum Role {

    WEREWOLF("Loup-garou"),
    VILLAGER("Villageois"),
    WITCH("Sorcière"),
    SEER("Voyante"),
    HUNTER("Chasseur"),
    GUARIDAN("Garde"),
    CUPID("Cupidon");

    String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

