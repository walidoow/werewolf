package com.walidoow.werewolf.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SkullInventory {

    Inventory inventory;
    Player player;

    public SkullInventory(Player player) {
        this.player = player;
        inventory = Bukkit.createInventory(player, 9 * 6, "Menu principal");

        inventory.setItem(16, new ItemStack(Material.RED_ROSE));
    }

    public void open() {
        player.openInventory(inventory);
    }
}
