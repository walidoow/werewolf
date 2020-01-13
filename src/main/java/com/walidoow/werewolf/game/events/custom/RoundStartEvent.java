package com.walidoow.werewolf.game.events.custom;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.RoundManager;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.player.Role;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundStartEvent extends RoundEvent {

    public RoundStartEvent(RoundManager.GameRound gameRound) {

        if (gameRound == RoundManager.GameRound.DAY) {
            Bukkit.broadcastMessage("§e➤ §aVous avez §62 minutes 30 secondes §apour faire connaissance !");
            Bukkit.getWorld(getGameManager().getGameProperties().getWorldName()).setTime(0L);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendTitle("§6Crépuscule", "§eIl est temps d'aller voter");
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1000, 20);
            }
        }

        if (gameRound == RoundManager.GameRound.CUPID) {
            if (!getGameManager().getAmountOfRoles().containsKey(Role.CUPID))
                //Cancel Cupid round if no player is a cupid
                getRoundManager().endActualRound();
            else
                Bukkit.broadcastMessage("§e➤ §aCupidon va choisir deux amoureux..");
        }

        if (gameRound == RoundManager.GameRound.WEREWOLF) {
            Bukkit.broadcastMessage("§e➤ §cLa nuit tombe, les loups-garous choisissent leur victime..");
            new BukkitRunnable() {
                @Override
                public void run() {
                    long time = Bukkit.getWorld(getGameManager().getGameProperties().getWorldName()).getTime();
                    if (time >= 15000L) {

                        cancel();
                    }
                    Bukkit.getWorld(getGameManager().getGameProperties().getWorldName()).setTime(time + 200);
                }
            }.runTaskTimer(Werewolf.get(), 0L, 1L);
            //Give blindness effect to players
            PlayerManager.getWolfPlayers().stream().filter(vampPlayer -> vampPlayer.getOfflinePlayer().isOnline()).forEach(vampPlayer -> vampPlayer.getOfflinePlayer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 4, 3)));
        }
    }
}
