package com.walidoow.werewolf.game;

import com.walidoow.werewolf.Werewolf;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerRound extends BukkitRunnable {

    int seconds;

    public TimerRound(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        if (seconds == 1) {
            cancel();
            Werewolf.get().getGameManager().getRoundManager().endActualRound();
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
