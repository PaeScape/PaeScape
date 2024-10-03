package com.arlania.engine.task.impl;


import com.arlania.engine.task.Task;
import com.arlania.world.entity.impl.player.Player;

/**
 * Staff of light special attack
 *
 * @author Gabriel Hannason
 */
public class StaffOfLightSpecialAttackTask extends Task {

    public StaffOfLightSpecialAttackTask(Player player) {
        super(2, player, false);
        this.player = player;
    }

    private final Player player;

    @Override
    public void execute() {
        if (player == null || !player.isRegistered()) {
            stop();
            return;
        }

        player.decrementStaffOfLightEffect();

        if (!player.hasStaffOfLightEffect()) {
            player.getPacketSender().sendMessage("Your Staff of Light's effect has faded away!");
            player.setStaffOfLightEffect(-1);
            stop();
        }
    }
}
