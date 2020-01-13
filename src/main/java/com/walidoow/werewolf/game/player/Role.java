package com.walidoow.werewolf.game.player;

import org.bukkit.entity.Player;

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

    public static void sendDescription(Player p, Role role) {

        switch (role) {
            case WEREWOLF: {
                p.sendMessage("§a§l∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎");
                p.sendMessage(" ");
                p.sendMessage("\uD83D\uDCDC §aMon rôle: §6§lLOUP-GAROU");
                p.sendMessage(" ");
                p.sendMessage("§fVous devez éliminer tous les villageois !");
                p.sendMessage("§fDurant la nuit, les loups-garous se réunissent");
                p.sendMessage("§fpour voter qui va être éliminer.");
                p.sendMessage(" ");
                break;
            }
            case VILLAGER: {
                p.sendMessage("§a§l∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎");
                p.sendMessage(" ");
                p.sendMessage("\uD83D\uDCDC §aMon rôle: §6§lVILLAGEOIS");
                p.sendMessage(" ");
                p.sendMessage("§fVous devez éliminer tous les loups-garous !");
                p.sendMessage("§fVotre parole est votre seul pouvoir de persuasion");
                p.sendMessage("§fpour les éliminer. Restez à l’affût d’indice et\n");
                p.sendMessage("§fidentifier les coupables !");
                break;
            }
        }
    }
}

