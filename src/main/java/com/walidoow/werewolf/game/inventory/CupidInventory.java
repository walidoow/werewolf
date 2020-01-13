package com.walidoow.werewolf.game.inventory;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.Role;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CupidInventory implements WerewolfInventory {

    private Inventory inventory;
    private Player player;

    public CupidInventory(Player player) {
        this.player = player;

        //Create the inventory
        inventory = Bukkit.createInventory(player, 9 * 6, "Choisissez deux amoureux:");

        //Add the heads of all players
        for (int i = 0; i < PlayerManager.getWolfPlayers().size(); i++) {
            OfflinePlayer offlinePlayer = PlayerManager.getWolfPlayers().get(i).getOfflinePlayer();
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwner(offlinePlayer.getName());
            skullMeta.setDisplayName("§b" + offlinePlayer.getName());
            skull.setItemMeta(skullMeta);
            inventory.setItem(i, skull);
        }
    }

    @Override
    public void open() {
        if (Werewolf.get().getPlayerManager().getVampPlayer(player).getRole().equals(Role.CUPID))
            player.openInventory(inventory);
    }
}
