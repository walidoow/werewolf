package com.walidoow.werewolf.listeners;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public static void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        GameManager gm = Werewolf.get().getGameManager();

        event.setCancelled(true);
        if (gm.getGameState().equals(GameManager.GameState.WAITING_FOR_PLAYERS)) {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage("§7" + event.getPlayer().getName() + "§f: " + event.getMessage()));
        }
    }
}
