package com.walidoow.werewolf.game;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.events.custom.RoundEndEvent;
import com.walidoow.werewolf.game.events.custom.RoundStartEvent;
import org.bukkit.Bukkit;

public class RoundManager {
    private GameRound gameRound;
    private TimerRound timerRound;

    public GameRound getGameRound() {
        return gameRound;
    }

    public void setGameRound(GameRound gameRound) {
        this.gameRound = gameRound;
        //Cancel previous timer if not already
        if (timerRound != null) timerRound.cancel();
        timerRound = new TimerRound(gameRound.time);
        timerRound.runTaskTimer(Werewolf.get(), 20L, 20L);
        startRound(gameRound);
    }

    public void startRound(GameRound gameRound) {
        //Call the event when the round starts
        Bukkit.getPluginManager().callEvent(new RoundStartEvent(gameRound));
    }

    public void endActualRound() {
        //Call the event when the round ends
        Bukkit.getPluginManager().callEvent(new RoundEndEvent(gameRound));

        //Start the new round
        setGameRound(GameRound.getRoundByPriority(gameRound.priority >= GameRound.values().length - 1 ? 2 : gameRound.priority + 1));
    }

    public TimerRound getTimerRound() {
        return timerRound;
    }

    public void setTimerRound(TimerRound timerRound) {
        this.timerRound = timerRound;
    }


    public enum GameRound {
        CUPID("Choix de Cupidon", 1, 0),//60
        MEETING("Présentations", 1, 1),//60
        SEER("Voyance", 15, 2),//15
        GUARDIAN("Choix du garde", 1, 3),//15
        WEREWOLF("Minuit", 20, 4),//90
        WITCH("Sorcière", 1, 5),//15
        DAY("Jour", 20, 6);//150

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
