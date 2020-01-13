package com.walidoow.werewolf.game.inventory;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.RoundManager;
import com.walidoow.werewolf.game.player.Role;
import org.bukkit.entity.Player;

public class InventoryManager {

    public static void createInventory(Player player, RoundManager.GameRound gameRound) {
        if (gameRound == RoundManager.GameRound.CUPID && Werewolf.get().getPlayerManager().getVampPlayer(player).getRole() == Role.CUPID)
            new CupidInventory(player).open();
        if (gameRound == RoundManager.GameRound.DAY)
            new VoteInventory(player).open();
        if (gameRound == RoundManager.GameRound.WEREWOLF && Werewolf.get().getPlayerManager().getVampPlayer(player).getRole() == Role.WEREWOLF)
            new VoteInventory(player).open();
        if (gameRound == RoundManager.GameRound.SEER && Werewolf.get().getPlayerManager().getVampPlayer(player).getRole() == Role.SEER)
            new SeerInventory(player).open();
    }
}
