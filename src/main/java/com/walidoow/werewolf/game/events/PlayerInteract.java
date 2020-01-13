package com.walidoow.werewolf.game.events;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.RoundManager;
import com.walidoow.werewolf.game.inventory.InventoryManager;
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
        if (event.getHand().equals(EquipmentSlot.OFF_HAND) | event.getItem() == null) return;

        Player player = event.getPlayer();
        RoundManager.GameRound round = Werewolf.get().getGameManager().getRoundManager().getGameRound();
        ItemStack itemStack = event.getItem();

        event.setCancelled(true);

        if (itemStack.getType().equals(Material.SKULL_ITEM))
            InventoryManager.createInventory(player, round);

    }
}
