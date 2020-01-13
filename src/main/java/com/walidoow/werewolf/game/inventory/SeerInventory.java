package com.walidoow.werewolf.game.inventory;

import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.WolfPlayer;
import com.walidoow.werewolf.game.votes.VoteManager;
import com.walidoow.werewolf.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SeerInventory implements WerewolfInventory {

    private Inventory inventory;
    private OfflinePlayer player;
    private UUID uuid;

    public SeerInventory(OfflinePlayer player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        List<WolfPlayer> wolfPlayers = PlayerManager.getWolfPlayers();

        int height = wolfPlayers.size() % 9 == 0 ? wolfPlayers.size() / 9 : (wolfPlayers.size() / 9) + 1;
        //Create the inventory
        Inventory inv = Bukkit.createInventory(null, 9 * height, "Choisissez un joueur:");

        //Add the heads of all players
        for (int i = 0; i < PlayerManager.getWolfPlayers().size(); i++)
            inv.setItem(i, getHead(wolfPlayers.get(i).getOfflinePlayer()));

        this.inventory = inv;
    }

    @Override
    public void open() {
        if (player.isOnline())
            player.getPlayer().openInventory(inventory);
    }

    private ItemStack getHead(OfflinePlayer offlinePlayer) {
        
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(offlinePlayer.getName());
        skullMeta.setDisplayName("§b" + offlinePlayer.getName());
        skull.setItemMeta(skullMeta);
        skull.setAmount(VoteManager.getAmmountOfVotes(offlinePlayer.getUniqueId()));
        return skull;
    }
}
