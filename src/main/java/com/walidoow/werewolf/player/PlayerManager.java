package com.walidoow.werewolf.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private static List<VampPlayer> vampPlayers = new ArrayList<>();

    public static List<VampPlayer> getVampPlayers() {
        return vampPlayers;
    }

    public VampPlayer getVampPlayer(Player player) {
        for (VampPlayer vampPlayer : vampPlayers) {
            if (vampPlayer.getUuid().equals(player.getUniqueId()))
                return vampPlayer;
        }
        return null;
    }

    public VampPlayer getVampPlayer(OfflinePlayer player) {
        for (VampPlayer vampPlayer : vampPlayers) {
            if (vampPlayer.getUuid().equals(player.getUniqueId()))
                return vampPlayer;
        }
        return null;
    }

    public VampPlayer getVampPlayer(UUID uuid) {
        for (VampPlayer vampPlayer : vampPlayers) {
            if (vampPlayer.getUuid().equals(uuid))
                return vampPlayer;
        }
        return null;
    }


    public void setVampPlayers(List<VampPlayer> vampPlayers) {
        this.vampPlayers = vampPlayers;
    }

    public void addVamPlayer(VampPlayer vampPlayer) {
        vampPlayers.add(vampPlayer);
    }

    public boolean isVamPlayer(Player player) {
        if (vampPlayers.isEmpty())
            return false;
        for (VampPlayer vampPlayer : vampPlayers) {
            if (vampPlayer.getUuid().equals(player.getUniqueId()))
                return true;
        }
        return false;
    }

    public boolean isVamPlayer(OfflinePlayer player) {
        if (vampPlayers.isEmpty())
            return false;
        for (VampPlayer vampPlayer : vampPlayers) {
            if (vampPlayer.getUuid().equals(player.getUniqueId()))
                return true;
        }
        return false;
    }

    public boolean isVamPlayer(UUID uuid) {
        if (vampPlayers.isEmpty())
            return false;
        for (VampPlayer vampPlayer : vampPlayers) {
            if (vampPlayer.getUuid().equals(uuid))
                return true;
        }
        return false;
    }

    public void removeVamPlayer(Player player) {
        for (VampPlayer vampPlayer : vampPlayers)
            if (vampPlayer.getUuid().equals(player.getUniqueId())) {
                vampPlayers.remove(vampPlayer);
                return;
            }
    }

    public void removeVamPlayer(OfflinePlayer player) {
        for (VampPlayer vampPlayer : vampPlayers)
            if (vampPlayer.getUuid().equals(player.getUniqueId())) {
                vampPlayers.remove(vampPlayer);
                return;
            }
    }

    public void removeVamPlayer(UUID uuid) {
        for (VampPlayer vampPlayer : vampPlayers)
            if (vampPlayer.getUuid().equals(uuid)) {
                vampPlayers.remove(vampPlayer);
                return;
            }
    }
}
