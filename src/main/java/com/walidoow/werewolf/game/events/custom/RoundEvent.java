package com.walidoow.werewolf.game.events.custom;

import com.walidoow.werewolf.Werewolf;
import com.walidoow.werewolf.game.GameManager;
import com.walidoow.werewolf.game.RoundManager;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class RoundEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public RoundManager getRoundManager() {
        return Werewolf.get().getGameManager().getRoundManager();
    }

    public GameManager getGameManager() {
        return Werewolf.get().getGameManager();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
