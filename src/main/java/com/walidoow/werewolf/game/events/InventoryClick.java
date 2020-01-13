package com.walidoow.werewolf.game.events;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.GameManager;
import com.walidoow.werewolf.game.RoundManager;
import com.walidoow.werewolf.game.inventory.VoteInventory;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.Role;
import com.walidoow.werewolf.game.votes.Vote;
import com.walidoow.werewolf.game.votes.VoteManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryClick implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return; //Check to remove null pointer exceptions

        Player player = (Player) event.getWhoClicked();
        RoundManager.GameRound gameRound = Werewolf.get().getGameManager().getRoundManager().getGameRound();
        GameManager gameManager = Werewolf.get().getGameManager();
        GameManager.GameState gameState = gameManager.getGameState();

        ItemStack itemStack = event.getCurrentItem();
        Inventory inventory = event.getClickedInventory();
        Material material = itemStack.getType();
        int rawSlot = event.getRawSlot();

        List<String> lore = new ArrayList<>();
        lore.add("§aSélectionné");

        event.setCancelled(true);


        /*
         * Game has started
         */
        if (gameState.equals(GameManager.GameState.GAME)) {

            /*
             * Player is choosing a player
             */
            if (material == Material.SKULL_ITEM) {

                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                itemStack.setItemMeta(skullMeta);
                if (!skullMeta.hasOwner()) return; //Check if player is not clicking on a simple head

                /*
                 * Players are voting
                 */
                if (gameRound == RoundManager.GameRound.DAY |
                        (gameRound == RoundManager.GameRound.WEREWOLF && Werewolf.get().getPlayerManager().getVampPlayer(player).getRole() == Role.WEREWOLF)) {
                    UUID voter = player.getUniqueId();
                    UUID voted = Bukkit.getOfflinePlayer(skullMeta.getOwner()).getUniqueId();

                    /*
                     * Player has already voted
                     */
                    if (VoteManager.containsVoter(voter)) {
                        //Remove one head from the inventory
                        VoteInventory.removeHead(voter);
                        //Remove voter's last vote
                        VoteManager.removeVote(voter);
                    }

                    //Add new vote from player
                    VoteManager.addVote(new Vote(voter, voted));
                    //Add one head to the inventory
                    VoteInventory.addHead(voter, rawSlot);

                    skullMeta.setLore(lore);
                    itemStack.setItemMeta(skullMeta);
                    inventory.setItem(rawSlot, itemStack);

                    /*
                     * Cupid is voting
                     */
                } else if (gameRound == RoundManager.GameRound.CUPID) {
                    skullMeta.setLore(lore);
                    itemStack.setItemMeta(skullMeta);

                    //Check if the cupid selected its first lover
                    if (gameManager.getCupids()[0] == null) {

                        //Assign the first lover
                        Werewolf.get().getGameManager().getCupids()[0] = Bukkit.getOfflinePlayer(skullMeta.getOwner()).getUniqueId();
                        inventory.setItem(rawSlot, itemStack);

                        //Check if the cupid selected its second lover
                    } else if (gameManager.getCupids()[1] == null) {

                        //Assign the second lover
                        Werewolf.get().getGameManager().getCupids()[1] = Bukkit.getOfflinePlayer(skullMeta.getOwner()).getUniqueId();
                        inventory.setItem(rawSlot, itemStack);
                        player.closeInventory();
                        Werewolf.get().getGameManager().getRoundManager().endActualRound();
                    }
                }

                /*
                 * Seer is looking for a player's role
                 */
                if (gameRound == RoundManager.GameRound.SEER) {

                    UUID target = Bukkit.getOfflinePlayer(skullMeta.getOwner()).getUniqueId();
                    String roleName = Werewolf.get().getPlayerManager().getVampPlayer(target).getRole().getName();

                    //TODO: Reveal the selected player's role
                    player.closeInventory();

                    //End round when a player discovers a role
                    Werewolf.get().getGameManager().getRoundManager().endActualRound();
                }

                /*
                 * Player is canceling something
                 */
            } else if (material == Material.BARRIER) {
                UUID voter = player.getUniqueId();

                /*
                 * Player has already voted
                 */
                if (VoteManager.containsVoter(voter)) {
                    //Cancel vote
                    //Remove one head from the inventory
                    VoteInventory.removeHead(voter);
                    //Remove voter's last vote
                    VoteManager.removeVote(voter);
                }
            }

        }
    }
}
