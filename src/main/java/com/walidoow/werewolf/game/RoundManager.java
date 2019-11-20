package com.walidoow.werewolf.game;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.player.Role;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundManager {

    private GameRound gameRound;
    private TimerRound timerRound;

    public GameRound getGameRound() {
        return gameRound;
    }

    public void setGameRound(GameRound gameRound) {
        this.gameRound = gameRound;
        timerRound = new TimerRound(gameRound.time);
        timerRound.runTaskTimer(Werewolf.get(), 20L, 20L);
        startRound(gameRound);
    }

    public void startRound(GameRound gameRound) {

        if (gameRound == GameRound.DAY) {
            Bukkit.broadcastMessage("§e➤ §aVous avez §61 minute §apour faire connaissance !");
            Bukkit.getWorld(Werewolf.get().getGameManager().getGameProperties().getWorldName()).setTime(0L);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendTitle("§6Crépuscule", "§eIl est temps d'aller voter");
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1000, 20);
            }

        } else if (gameRound == GameRound.CUPID) {
            if (Werewolf.get().getGameManager().getAmountOfRoles().containsKey(Role.CUPID))
                //Cancel Cupid round if no player is a cupid
                endActualRound();
            Bukkit.broadcastMessage("§e➤ §aCupidon va choisir deux amoureux..");

        } else if (gameRound == GameRound.WEREWOLF) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    long time = Bukkit.getWorld(Werewolf.get().getGameManager().getGameProperties().getWorldName()).getTime();
                    if (time >= 15000L) {
                        Bukkit.broadcastMessage("§e➤ §cLa nuit tombe, les loups-garous choisissent leur victime..");
                        cancel();
                    }
                    Bukkit.getWorld(Werewolf.get().getGameManager().getGameProperties().getWorldName()).setTime(time + 200);
                }
            }.runTaskTimer(Werewolf.get(), 0L, 1L);
        }
    }

    public void endActualRound() {
        if (gameRound == GameRound.WITCH)
            //Add one day to number of passed days
            Werewolf.get().getGameManager().addRound(1);
        //Start the a new round
        setGameRound(GameRound.getRoundByPriority(gameRound.priority >= GameRound.values().length - 1 ? 2 : gameRound.priority + 1));
    }

    public TimerRound getTimerRound() {
        return timerRound;
    }

    public void setTimerRound(TimerRound timerRound) {
        this.timerRound = timerRound;
    }


    public enum GameRound {
        CUPID("Choix de Cupidon", 5, 0),//15
        MEETING("Présentations", 5, 1),
        SEER("Voyance", 5, 2),//15
        GUARDIAN("Choix du garde", 5, 3),//15
        WEREWOLF("Minuit", 5, 4),//20
        WITCH("Sorcière", 5, 5),//15
        DAY("Jour", 5, 6);//120
        //Cupidon Voyante Garde Loup Sorcière

        private String name;
        private int time;
        private int priority;

        GameRound(String name, int time, int priority) {
            this.name = name;
            this.time = time;
            this.priority = priority;
        }

        public String getName() {
            return name;
        }

        public int getTime() {
            return time;
        }

        public int getPriority() {
            return priority;
        }

        public static GameRound getRoundByPriority(int priority) {
            for (GameRound value : values())
                if (value.priority == priority)
                    return value;
            return null;
        }
    }
}
