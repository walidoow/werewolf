package com.walidoow.werewolf.game.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {

    @EventHandler
    public static void onWeatherChangeEvent(WeatherChangeEvent event) {
        if (event.getWorld().hasStorm())
            event.getWorld().setStorm(false);
    }
}
