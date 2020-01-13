package com.walidoow.werewolf.game.votes;

import java.util.UUID;

public class Vote {

    private UUID voter;
    private UUID voted;

    public Vote(UUID voter, UUID voted) {
        this.voter = voter;
        this.voted = voted;
    }

    public UUID getVoter() {
        return voter;
    }

    public void setVoter(UUID voter) {
        this.voter = voter;
    }

    public UUID getVoted() {
        return voted;
    }

    public void setVoted(UUID voted) {
        this.voted = voted;
    }
}
