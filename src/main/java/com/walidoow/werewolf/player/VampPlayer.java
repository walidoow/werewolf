package com.walidoow.werewolf.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VampPlayer {

    private OfflinePlayer offlinePlayer;
    private UUID uuid;
    private Role role;
    private boolean eliminated;
    private boolean spectator;

    public VampPlayer(Player player) {
        this.offlinePlayer = player;
        this.uuid = player.getUniqueId();
    }

    public VampPlayer(Player player, boolean spectator) {
        this.offlinePlayer = player;
        this.uuid = player.getUniqueId();
        this.spectator = spectator;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean hasRole() {
        if (role != null)
            return true;
        return false;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public boolean isSpectator() {
        return spectator;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }
}
