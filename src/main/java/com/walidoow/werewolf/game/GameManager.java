package com.walidoow.werewolf.game;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.player.PlayerManager;
import com.walidoow.werewolf.player.Role;
import com.walidoow.werewolf.player.VampPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {

    private GameState gameState = GameState.WAITING_FOR_PLAYERS;
    private GameProperties gameProperties;
    private RoundManager roundManager = new RoundManager();
    private TimerStart timerStart;
    private int round = 1;
    private Map<Role, Integer> amountOfRoles = new HashMap<>();

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

    public void startGame() {
        Werewolf werewolf = Werewolf.get();
        PlayerManager playerManager = werewolf.getPlayerManager();
        int numberOfPlayers = playerManager.getVampPlayers().size();
        int numberOfWerewolves = Math.round(((3F / 11F) * numberOfPlayers));
        int numberOfSimpleVillagers = (int) Math.ceil(((2F / 11F) * numberOfPlayers));
        Collections.shuffle(PlayerManager.getVampPlayers());

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

        for (VampPlayer vampPlayer : PlayerManager.getVampPlayers()) {
            if (vampPlayer.getOfflinePlayer().isOnline()) {
                Player p = vampPlayer.getOfflinePlayer().getPlayer();

                switch (vampPlayer.getRole()) {
                    case WEREWOLF: {
                        p.sendMessage("§a§l∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎");
                        p.sendMessage(" ");
                        p.sendMessage("\uD83D\uDCDC §aMon rôle: §6§lLOUP-GAROU");
                        p.sendMessage(" ");
                        p.sendMessage("§fVous devez éliminer tous les villageois !");
                        p.sendMessage("§fDurant la nuit, les loups-garous se réunissent");
                        p.sendMessage("§fpour voter qui va être éliminer.");
                        p.sendMessage(" ");
                        break;
                    }
                    case VILLAGER: {
                        p.sendMessage("§a§l∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎");
                        p.sendMessage(" ");
                        p.sendMessage("\uD83D\uDCDC §aMon rôle: §6§lVILLAGEOIS");
                        p.sendMessage(" ");
                        p.sendMessage("§fVous devez éliminer tous les loups-garous !");
                        p.sendMessage("§fVotre parole est votre seul pouvoir de persuasion");
                        p.sendMessage("§fpour les éliminer. Restez à l’affût d’indice et\n");
                        p.sendMessage("§fidentifier les coupables !");
                        break;
                    }
                }
            }
        }

    }

    private void setRole(int index, Role role) {
        PlayerManager.getVampPlayers().get(index).setRole(role);
    }

    public enum GameState {
        WAITING_FOR_PLAYERS, GAME, END_GAME;
    }
}



