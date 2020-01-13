package com.walidoow.werewolf.game.events;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.GameManager;
import com.walidoow.werewolf.game.GameProperties;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.utils.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public static void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Werewolf werewolf = Werewolf.get();
        PlayerManager playerManager = werewolf.getPlayerManager();
        GameManager gameManager = werewolf.getGameManager();
        GameProperties gameProperties = gameManager.getGameProperties();

        event.setQuitMessage("");
        //Delete scoreboard
        FastBoard board = Werewolf.get().getBoard().getBoards().remove(player.getUniqueId());
        if (board != null)
            board.delete();

        /*
         * Quit when the server is waiting for players
         */
        if (Werewolf.get().getGameManager().getGameState().equals(GameManager.GameState.WAITING_FOR_PLAYERS)) {
            playerManager.removeVamPlayer(player);

            //Send join message
            Bukkit.broadcastMessage("§6[Loup-Garou]§b " + player.getDisplayName() + " §ca quitté la partie. §a(" +
                    playerManager.getWolfPlayers().size() + "/" + gameProperties.getMaxPlayers() + ")");

            //Cancel timer when not enough players
            if (playerManager.getWolfPlayers().size() < gameProperties.getMinPlayers()) {
                if (!gameManager.hasTimerStart())
                    return;
                gameManager.getTimerStart().cancel();
                gameManager.setTimerStart(null);
                Bukkit.broadcastMessage("§c§l[Loup-Garou]§r§c Par manque de joueur le partie ne peut être lancée.");
            }

        }
    }
}
