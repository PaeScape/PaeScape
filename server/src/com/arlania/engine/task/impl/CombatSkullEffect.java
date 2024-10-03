package com.arlania.engine.task.impl;

import com.arlania.engine.task.Task;
import com.arlania.model.Flag;
import com.arlania.world.entity.impl.player.Player;

/**
 * A {@link Task} implementation that will remove the white skull from above
 * player's head.
 *
 * @author lare96
 */
public class CombatSkullEffect extends Task {

    /**
     * The player attached to this task.
     */
    private final Player player;

    /**
     * Create a new {@link CombatSkullEffect}.
     *
     * @param player the player attached to this task.
     */
    public CombatSkullEffect(Player player) {
        super(50, player, false);
        this.player = player;
    }

    @Override
    public void execute() {
        if (player == null || !player.isRegistered()) {
            stop();
            return;
        }
        // Timer is at or below 0 so we can remove the skull.
        if (player.getSkullTimer() <= 0) {
            player.setSkullIcon(0);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            this.stop();
            return;
        }

        // Otherwise we just decrement the timer.
        player.decrementSkullTimer();
    }
}
