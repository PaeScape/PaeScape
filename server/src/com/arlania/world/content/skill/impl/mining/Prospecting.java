package com.arlania.world.content.skill.impl.mining;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.world.entity.impl.player.Player;

public class Prospecting {

    public static boolean prospectOre(final Player plr, int objectId) {
        final MiningData.Ores oreData = MiningData.forRock(objectId);
        if (oreData != null) {
            if (!plr.getClickDelay().elapsed(2800))
                return true;
            plr.getSkillManager().stopSkilling();
            plr.getPacketSender().sendMessage("You examine the ore...");
            TaskManager.submit(new Task(2, plr, false) {
                @Override
                public void execute() {
                    plr.getPacketSender().sendMessage("..the rock contains " + oreData.toString().toLowerCase() + " ore.");
                    this.stop();
                }
            });
            plr.getClickDelay().reset();
            return true;
        }
        return false;
    }
}
