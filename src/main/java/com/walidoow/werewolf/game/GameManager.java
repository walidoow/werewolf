package com.walidoow.werewolf.game;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.Role;
import com.walidoow.werewolf.game.player.WolfPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {

    private GameState gameState = GameState.WAITING_FOR_PLAYERS;
    private GameProperties gameProperties;
    private RoundManager roundManager = new RoundManager();
    private TimerStart timerStart;
    private int round = 1;
    private Map<Role, Integer> amountOfRoles = new HashMap<>();
    private UUID[] cupids = new UUID[2];

    public GameManager() {
        gameProperties = new GameProperties();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameProperties getGameProperties() {
        return gameProperties;
    }

    public void setGameProperties(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    public void setRoundManager(RoundManager roundManager) {
        this.roundManager = roundManager;
    }

    public TimerStart getTimerStart() {
        return timerStart;
    }

    public void setTimerStart(TimerStart timerStart) {
        this.timerStart = timerStart;
    }

    public boolean hasTimerStart() {
        if (timerStart == null)
            return false;
        return true;
    }

    public boolean isTimerStarted() {
        if (timerStart == null)
            return false;
        return true;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void addRound(int round) {
        this.round += round;
    }

    public Map<Role, Integer> getAmountOfRoles() {
        return amountOfRoles;
    }

    public void setAmountOfRoles(Map<Role, Integer> amountOfRoles) {
        this.amountOfRoles = amountOfRoles;
    }

    public void addAmountOfRoles(Role role, int amount) {
        if (amountOfRoles.containsKey(role))
            amountOfRoles.put(role, amountOfRoles.get(role) + amount);
        else amountOfRoles.put(role, amount);
    }

    public UUID[] getCupids() {
        return cupids;
    }

    public void setCupids(UUID[] cupids) {
        this.cupids = cupids;
    }

    public void setCupid(int index, UUID uuid) {
        this.cupids[index] = uuid;
    }

    public boolean hasCupid() {
        if (this.cupids[0] != null & this.cupids[1] != null)
            return true;
        return false;
    }

    public void startGame() {
        Werewolf werewolf = Werewolf.get();
        PlayerManager playerManager = werewolf.getPlayerManager();
        int numberOfPlayers = playerManager.getWolfPlayers().size();
        int numberOfWerewolves = Math.round(((3F / 11F) * numberOfPlayers));
        int numberOfSimpleVillagers = (int) Math.ceil(((2F / 11F) * numberOfPlayers));
        Collections.shuffle(PlayerManager.getWolfPlayers());

        /*
         * Set the roles of the players
         */
        //Set the werevolves
        for (int i = 0; i < numberOfWerewolves; i++) {
            setRole(i, Role.WEREWOLF);
            addAmountOfRoles(Role.WEREWOLF, 1);
        }
        int l = numberOfWerewolves + numberOfSimpleVillagers;
        //Set the simple villagers
        for (int i = numberOfWerewolves; i < l; i++) {
            setRole(i, Role.VILLAGER);
            addAmountOfRoles(Role.VILLAGER, 1);
        }
        //Set the other roles in order of priority
        if (numberOfPlayers > l) {
            setRole(l, Role.SEER);
            addAmountOfRoles(Role.SEER, 1);
        }
        if (numberOfPlayers > l + 1) {
            setRole(l + 1, Role.WITCH);
            addAmountOfRoles(Role.WITCH, 1);
        }
        if (numberOfPlayers > l + 2) {
            setRole(l + 2, Role.HUNTER);
            addAmountOfRoles(Role.HUNTER, 1);
        }
        if (numberOfPlayers > l + 3) {
            setRole(l + 3, Role.GUARIDAN);
            addAmountOfRoles(Role.GUARIDAN, 1);
        }
        if (numberOfPlayers > l + 4) {
            setRole(l + 4, Role.CUPID);
            addAmountOfRoles(Role.CUPID, 1);
        }
        roundManager.setGameRound(RoundManager.GameRound.CUPID);
        revealRoleDescription();
    }

    private void revealRoleDescription() {
        for (WolfPlayer wolfPlayer : PlayerManager.getWolfPlayers())
            if (wolfPlayer.getOfflinePlayer().isOnline())
                Role.sendDescription(wolfPlayer.getOfflinePlayer().getPlayer(), wolfPlayer.getRole());
    }

    private void setRole(int index, Role role) {
        PlayerManager.getWolfPlayers().get(index).setRole(role);
    }

    public enum GameState {
        WAITING_FOR_PLAYERS, GAME, END_GAME;
    }
}



