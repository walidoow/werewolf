package com.walidoow.werewolf.game.events.custom;

import com.walidoow.werewolf.game.RoundManager;
import com.walidoow.werewolf.game.player.Role;
import com.walidoow.werewolf.game.votes.VoteManager;
import org.bukkit.Bukkit;

import java.util.UUID;

public class RoundEndEvent extends RoundEvent {

    public RoundEndEvent(RoundManager.GameRound gameRound) {

        if (gameRound == RoundManager.GameRound.CUPID) {
            if (getGameManager().hasCupid()) {
                for (UUID cupid : getGameManager().getCupids())
                    Bukkit.getPlayer(cupid).sendTitle("\uD83D\uDC9E", "§c" + Bukkit.getPlayer(getGameManager().getCupids()[0]).getName() + " §f+ §c" + Bukkit.getPlayer(getGameManager().getCupids()[1]).getName());
                Bukkit.broadcastMessage("§e➤ §aCupidon a décidé de choisir deux amoureux ! ");
            } else if (getGameManager().getAmountOfRoles().containsKey(Role.CUPID))
                Bukkit.broadcastMessage("§e➤ §cCupidon a décidé de ne pas choisir deux amoureux !");
        }

        if (gameRound == RoundManager.GameRound.DAY) {
            VoteManager.getTopVoted();
            VoteManager.clearVotes();
        }

        if (gameRound == RoundManager.GameRound.WEREWOLF) {
            VoteManager.getTopVoted();
            VoteManager.clearVotes();
        }

        if (gameRound == RoundManager.GameRound.WITCH)
            //Add one day to number of passed days
            getGameManager().addRound(1);
    }

}
