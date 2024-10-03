package com.arlania.engine.task.impl;


import com.arlania.engine.task.Task;
import com.arlania.model.CombatIcon;
import com.arlania.model.Graphic;
import com.arlania.model.Hit;
import com.arlania.model.Hitmask;
import com.arlania.model.Locations.Location;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

/**
 * Barrows
 *
 * @author Gabriel Hannason
 */
public class CeilingCollapseTask extends Task {

    public CeilingCollapseTask(Player player) {
        super(9, player, false);
        this.player = player;
    }

    private final Player player;

    @Override
    public void execute() {
        if (player == null || !player.isRegistered() || player.getLocation() != Location.BARROWS || player.getLocation() == Location.BARROWS && player.getPosition().getY() < 8000) {
            player.getPacketSender().sendCameraNeutrality();
            stop();
            return;
        }
        player.performGraphic(new Graphic(60));
        player.getPacketSender().sendMessage("Some rocks fall from the ceiling and hit you.");
        player.forceChat("Ouch!");
        player.dealDamage(new Hit(30 + RandomUtility.inclusiveRandom(20), Hitmask.RED, CombatIcon.BLOCK));
    }
}
