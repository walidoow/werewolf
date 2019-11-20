package com.walidoow.werewolf.game;

import com.walidoow.werewolf.Werewolf;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerStart extends BukkitRunnable {

    int seconds;

    public TimerStart(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        if (seconds <= 5 && seconds != 0) {
            //Send message
            Bukkit.broadcastMessage("§6[Loup-Garou] §eLa partie commence dans §b" + seconds + "§e" + (seconds == 1 ? " secondes." : " secondes."));
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                //Play note
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.BLOCK_NOTE_HARP, 5, 5);
                if (seconds <= 3)
                    //Send title
                    onlinePlayer.sendTitle("§c" + seconds, "§6Préparez-vous !");
            });
        }
        if (seconds == 0) {
            //Send message
            Bukkit.broadcastMessage("§6§l[Loup-Garou] §b§lLa partie commence!");
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                //Play note
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 5);
                //Send title
                onlinePlayer.sendTitle("§cLOUP-GAROU §6commence!", "§7Bon jeu!");
            });
            //Change game state to game
            Werewolf.get().getGameManager().setGameState(GameManager.GameState.GAME);
            //Cancel timer
            cancel();
            //Start the game
            Werewolf.get().getGameManager().startGame();
        }
        seconds--;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
