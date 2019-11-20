package com.walidoow.werewolf.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public static void onEntityDamageEvent(EntityDamageEvent event){
        event.setCancelled(true);
    }
}
