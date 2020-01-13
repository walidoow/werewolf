package com.walidoow.werewolf.game.events;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.GameManager;
import com.walidoow.werewolf.game.GameProperties;
import com.walidoow.werewolf.game.TimerStart;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.WolfPlayer;
import com.walidoow.werewolf.utils.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public static void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Werewolf werewolf = Werewolf.get();
        PlayerManager playerManager = werewolf.getPlayerManager();
        GameManager gameManager = werewolf.getGameManager();
        GameProperties gameProperties = gameManager.getGameProperties();
        FastBoard board = new FastBoard(player);

        event.setJoinMessage("");
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20D);
        player.setFoodLevel(20);
        //Set the board
        board.updateTitle("\uD83D\uDC3A §6§lLOUP-GAROU \uD83D\uDC3A");
        Werewolf.get().getBoard().getBoards().put(player.getUniqueId(), board);

        /*
         * Join when the server is waiting for players
         */
        if (gameManager.getGameState().equals(GameManager.GameState.WAITING_FOR_PLAYERS)) {
            playerManager.addVamPlayer(new WolfPlayer(player));

            //Send join message
            Bukkit.broadcastMessage("§6[Loup-Garou]§b " + player.getDisplayName() + " §7a rejoint la partie. §a(" +
                    playerManager.getWolfPlayers().size() + "/" + gameProperties.getMaxPlayers() + ")");

            //Launch timer if enough players
            if (playerManager.getWolfPlayers().size() == gameProperties.getMinPlayers()) {
                gameManager.setTimerStart(new TimerStart(5));
                gameManager.getTimerStart().runTaskTimer(werewolf, 0L, 20L);
            }

            /*
             * Join when the server is running
             */
        } else if (gameManager.getGameState().equals(GameManager.GameState.GAME)) {

            //Check if the player is part of the game
            if (playerManager.isVamPlayer(player)) {

            } else {
                //Player's first connection
                player.setGameMode(GameMode.SPECTATOR);
            }
        }

    }
}
