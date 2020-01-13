package com.walidoow.werewolf;

import com.walidoow.werewolf.game.GameManager;
import com.walidoow.werewolf.game.events.*;
import com.walidoow.werewolf.game.player.PlayerManager;
import com.walidoow.werewolf.game.scoreboard.Board;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Werewolf extends JavaPlugin {

    private static Werewolf instance;

    private PlayerManager playerManager;
    private GameManager gameManager;
    private Board board;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new PlayerManager();
        gameManager = new GameManager();
        board = new Board();

        loadEvents();
        //Clear the world's weather
        getServer().getWorld(gameManager.getGameProperties().getWorldName()).setStorm(false);

        /*
         * Update scoreboard
         */
        getServer().getScheduler().runTaskTimer(this, () -> {
            if (!board.getBoards().isEmpty())
                for (UUID uuid : board.getBoards().keySet()) {
                    board.updateBoard(board.getBoards().get(uuid), uuid);
                }
        }, 0, 20);
    }

    @Override
    public void onDisable() {

    }

    private void loadEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new PlayerInteract(), this);
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new CreatureSpawn(), this);
        pm.registerEvents(new FoodLevelChange(), this);
        pm.registerEvents(new WeatherChange(), this);
        pm.registerEvents(new AsyncPlayerChat(), this);
        pm.registerEvents(new InventoryClick(), this);
    }

    public static Werewolf get() {
        return instance;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
