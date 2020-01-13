package com.walidoow.werewolf.game.votes;

import com.walidoow.werewolf.game.inventory.VoteInventory;
import org.bukkit.Bukkit;

import java.util.*;

public class VoteManager {

    private static List<Vote> votes = new ArrayList<>();
    private static VoteInventory voteInventory;

    public static List<Vote> getVotes() {
        return votes;
    }

    public static void setVotes(List<Vote> votes) {
        VoteManager.votes = votes;
    }

    public static void addVote(Vote vote) {
        VoteManager.votes.add(vote);
    }

    public static List<UUID> getVoters(UUID uuid) {
        List<UUID> voters = new ArrayList<>();
        for (Vote vote : votes)
            if (vote.getVoted().equals(uuid)) voters.add(vote.getVoter());
        return voters;
    }

    public static UUID getVoted(UUID uuid) {
        for (Vote vote : votes)
            if (vote.getVoter().equals(uuid))
                return vote.getVoted();
        return null;

    }

    public static int getAmmountOfVotes(UUID uuid) {
        int i = 0;
        for (Vote vote : votes)
            if (vote.getVoted().equals(uuid)) i++;
        return i;
    }

    public static boolean containsVoter(UUID uuid) {
        for (Vote vote : votes)
            if (vote.getVoter().equals(uuid))
                return true;
        return false;
    }

    public static boolean containsVoted(UUID uuid) {
        for (Vote vote : votes)
            if (vote.getVoted().equals(uuid))
                return true;
        return false;
    }

    public static void removeVote(UUID uuid) {
        Vote v = null;
        for (Vote vote : votes)
            if (vote.getVoter().equals(uuid))
                v = vote;
        if (v != null)
            votes.remove(v);
    }

    public static List<UUID> getTopVoted() {
        Map<UUID, Integer> totalVotes = new HashMap<>();
        List<UUID> topList = new ArrayList<>();

        for (Vote vote : votes) {
            UUID voted = vote.getVoted();
            if (totalVotes.containsKey(voted))
                totalVotes.put(voted, totalVotes.get(voted) + 1);
            else
                totalVotes.put(voted, 1);
        }

        for (UUID uuid : totalVotes.keySet()) {

            //Set the first voted
            if (topList.isEmpty())
                topList.add(uuid);

                //Comparison of voted players
            else {
                int votesTop = totalVotes.get(topList.get(0));
                int votesUUID = totalVotes.get(uuid);

                if (votesTop == votesUUID)
                    topList.add(uuid);
                else if (votesUUID > votesTop) {
                    topList = new ArrayList<>();
                    topList.add(uuid);
                }
            }
        }
        for (UUID uuid : topList) {
            Bukkit.broadcastMessage(Bukkit.getPlayer(uuid).getDisplayName());
        }
        return topList;
    }

    public static void clearVotes() {
        votes = new ArrayList<>();
    }
}
