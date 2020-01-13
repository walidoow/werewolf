package com.walidoow.werewolf.game.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private static List<WolfPlayer> wolfPlayers = new ArrayList<>();

    public static List<WolfPlayer> getWolfPlayers() {
        return wolfPlayers;
    }

    public WolfPlayer getVampPlayer(Player player) {
        for (WolfPlayer wolfPlayer : wolfPlayers) {
            if (wolfPlayer.getUuid().equals(player.getUniqueId()))
                return wolfPlayer;
        }
        return null;
    }

    public WolfPlayer getVampPlayer(OfflinePlayer player) {
        for (WolfPlayer wolfPlayer : wolfPlayers) {
            if (wolfPlayer.getUuid().equals(player.getUniqueId()))
                return wolfPlayer;
        }
        return null;
    }

    public WolfPlayer getVampPlayer(UUID uuid) {
        for (WolfPlayer wolfPlayer : wolfPlayers) {
            if (wolfPlayer.getUuid().equals(uuid))
                return wolfPlayer;
        }
        return null;
    }


    public void setVampPlayers(List<WolfPlayer> wolfPlayers) {
        this.wolfPlayers = wolfPlayers;
    }

    public void addVamPlayer(WolfPlayer wolfPlayer) {
        wolfPlayers.add(wolfPlayer);
    }

    public boolean isVamPlayer(Player player) {
        if (wolfPlayers.isEmpty())
            return false;
        for (WolfPlayer wolfPlayer : wolfPlayers) {
            if (wolfPlayer.getUuid().equals(player.getUniqueId()))
                return true;
        }
        return false;
    }

    public boolean isVamPlayer(OfflinePlayer player) {
        if (wolfPlayers.isEmpty())
            return false;
        for (WolfPlayer wolfPlayer : wolfPlayers) {
            if (wolfPlayer.getUuid().equals(player.getUniqueId()))
                return true;
        }
        return false;
    }

    public boolean isVamPlayer(UUID uuid) {
        if (wolfPlayers.isEmpty())
            return false;
        for (WolfPlayer wolfPlayer : wolfPlayers) {
            if (wolfPlayer.getUuid().equals(uuid))
                return true;
        }
        return false;
    }

    public void removeVamPlayer(Player player) {
        for (WolfPlayer wolfPlayer : wolfPlayers)
            if (wolfPlayer.getUuid().equals(player.getUniqueId())) {
                wolfPlayers.remove(wolfPlayer);
                return;
            }
    }

    public void removeVamPlayer(OfflinePlayer player) {
        for (WolfPlayer wolfPlayer : wolfPlayers)
            if (wolfPlayer.getUuid().equals(player.getUniqueId())) {
                wolfPlayers.remove(wolfPlayer);
                return;
            }
    }

    public void removeVamPlayer(UUID uuid) {
        for (WolfPlayer wolfPlayer : wolfPlayers)
            if (wolfPlayer.getUuid().equals(uuid)) {
                wolfPlayers.remove(wolfPlayer);
                return;
            }
    }
}
