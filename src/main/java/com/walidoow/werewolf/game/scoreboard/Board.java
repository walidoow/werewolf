package com.walidoow.werewolf.game.scoreboard;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.GameManager;
import com.walidoow.werewolf.game.GameProperties;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.WolfPlayer;
import com.walidoow.werewolf.utils.FastBoard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Board {

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }

    public void updateBoard(FastBoard board, UUID uuid) {
        GameManager gm = Werewolf.get().getGameManager();
        PlayerManager pm = Werewolf.get().getPlayerManager();
        GameProperties gp = gm.getGameProperties();
        int connectedPlayers = pm.getWolfPlayers().size();
        WolfPlayer wolfPlayer = pm.getVampPlayer(uuid);

        if (Werewolf.get().getGameManager().getGameState() == GameManager.GameState.WAITING_FOR_PLAYERS) {
            board.updateLines(
                    "",
                    "§eEn attente de joueurs...",
                    "",
                    "Joueurs: §a" + connectedPlayers + "/" + gm.getGameProperties().getMaxPlayers()
            );

        } else if (Werewolf.get().getGameManager().getGameState() == GameManager.GameState.GAME) {
            board.updateLines(
                    "\uD83D\uDCC6 §eJour n°§b§l" + gm.getRound(),
                    "",
                    "\uD83D\uDEE1 §ePhase: §b" + gm.getRoundManager().getGameRound().getName(),
                    "🕑 §eChrono: §c" + gm.getRoundManager().getTimerRound().getSeconds(),
                    "",
                    "\uD83D\uDCDC §aMon rôle: §6" + wolfPlayer.getRole().getName(),
                    "",
                    "§fJoueurs: §a" + connectedPlayers + "/" + gm.getGameProperties().getMaxPlayers()
            );
        }
    }
}
