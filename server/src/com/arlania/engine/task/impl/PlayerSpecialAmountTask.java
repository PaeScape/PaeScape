package com.arlania.engine.task.impl;

import com.arlania.engine.task.Task;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

public class PlayerSpecialAmountTask extends Task {

    public PlayerSpecialAmountTask(Player player) {
        super(20, player, false);
        this.player = player;
        player.setRecoveringSpecialAttack(true);
    }

    private final Player player;

    @Override
    public void execute() {
        if (player == null || !player.isRegistered() || player.getSpecialPercentage() >= 100 || !player.isRecoveringSpecialAttack()) {
            player.setRecoveringSpecialAttack(false);
            stop();
            return;
        }
        int amount = 5;

        if ((player.Survivalist >= 3) && PerkHandler.canUseNormalPerks(player))
            amount = player.getSpecialPercentage() + 15;
        else if ((player.Survivalist >= 1) && PerkHandler.canUseNormalPerks(player))
            amount = player.getSpecialPercentage() + 10;
        else
            amount = player.getSpecialPercentage() + 5;

        if (amount >= 100) {
            amount = 100;
            player.setRecoveringSpecialAttack(false);
            stop();
        }
        player.setSpecialPercentage(amount);
        CombatSpecial.updateBar(player);
        if (player.getSpecialPercentage() % 50 == 0)
            player.getPacketSender().sendMessage("Your special attack energy is now " + player.getSpecialPercentage() + "%.");
    }
}