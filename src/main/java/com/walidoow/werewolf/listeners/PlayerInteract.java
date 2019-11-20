package com.walidoow.werewolf.listeners;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.RoundManager;
import com.walidoow.werewolf.inventory.SkullInventory;
import com.walidoow.werewolf.player.VampPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

    @EventHandler
    public static void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getHand().equals(EquipmentSlot.OFF_HAND))
            return;
        Player player = event.getPlayer();
        RoundManager.GameRound round = Werewolf.get().getGameManager().getRoundManager().getGameRound();
        VampPlayer vampPlayer = Werewolf.get().getPlayerManager().isVamPlayer(player) ? Werewolf.get().getPlayerManager().getVampPlayer(player) : null;
        ItemStack itemStack = event.hasItem() ? event.getItem() : null;

        event.setCancelled(true);

        if (itemStack.getType().equals(Material.SKULL_ITEM)) {
            new SkullInventory(player).open();
        }
    }
}
