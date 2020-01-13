package com.walidoow.werewolf.game.inventory;

import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.WolfPlayer;
import com.walidoow.werewolf.game.votes.VoteManager;
import com.walidoow.werewolf.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoteInventory implements WerewolfInventory {

    private Inventory inventory;
    private OfflinePlayer player;
    private UUID uuid;

    public VoteInventory(OfflinePlayer player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        List<WolfPlayer> wolfPlayers = PlayerManager.getWolfPlayers();

        int height = wolfPlayers.size() % 9 == 0 ? (wolfPlayers.size() / 9) + 1 : (wolfPlayers.size() / 9) + 2;
        //Create the inventory
        Inventory inv = Bukkit.createInventory(null, 9 * height, "Choisissez un joueur:");

        //Add the heads of all players
        for (int i = 0; i < PlayerManager.getWolfPlayers().size(); i++)
            inv.setItem(i, getHead(wolfPlayers.get(i).getOfflinePlayer(), uuid));

        //Add item to cancel a vote
        inv.setItem(inv.getSize() - 1, new ItemBuilder(new ItemStack(Material.BARRIER)).setDisplayName("§cAnnuler son vote").toItemStack());
        this.inventory = inv;
    }

    @Override
    public void open() {
        if (player.isOnline())
            player.getPlayer().openInventory(inventory);
    }


    private ItemStack getHead(OfflinePlayer offlinePlayer, UUID voter) {
        List<String> lore = new ArrayList<>();
        lore.add("§aSélectionné");

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(offlinePlayer.getName());
        skullMeta.setDisplayName("§b" + offlinePlayer.getName());
        if (VoteManager.containsVoter(voter))
            if (VoteManager.getVoted(voter).equals(offlinePlayer.getUniqueId()))
                skullMeta.setLore(lore);
        skull.setItemMeta(skullMeta);
        skull.setAmount(VoteManager.getAmmountOfVotes(offlinePlayer.getUniqueId()));
        return skull;
    }

    private static ItemStack getHead(UUID uuid, int amount) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(offlinePlayer.getName());
        skullMeta.setDisplayName("§b" + offlinePlayer.getName());
        skull.setItemMeta(skullMeta);
        skull.setAmount(amount);
        return skull;
    }

    public static void addHead(UUID voter, int rawSlot) {
        //Get the inventories of all online players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Inventory inventory = onlinePlayer.getOpenInventory().getTopInventory();

            if (inventory.getType() != InventoryType.CRAFTING)
                //Add one head to the inventory
                inventory.getItem(rawSlot).setAmount(inventory.getItem(rawSlot).getAmount() + 1);
        }
    }

    public static void removeHead(UUID voter) {
        UUID voted = VoteManager.getVoted(voter);

        //Get the inventories of all online players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Inventory inventory = onlinePlayer.getOpenInventory().getTopInventory();
            if (inventory.getType() != InventoryType.CRAFTING)

                for (int i = 0; i < inventory.getContents().length; i++) {
                    ItemStack content = inventory.getItem(i);
                    SkullMeta skullMeta = (SkullMeta) content.getItemMeta();

                    if (Bukkit.getPlayer(skullMeta.getOwner()).getUniqueId().equals(voted)) {
                        //Remove one head from the inventory
                        inventory.setItem(i, getHead(voted, VoteManager.getAmmountOfVotes(voted) - 1));
                        break;
                    }
                }
        }

    }


}
