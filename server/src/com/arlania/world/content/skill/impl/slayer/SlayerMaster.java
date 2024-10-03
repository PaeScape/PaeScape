package com.arlania.world.content.skill.impl.slayer;

import com.arlania.model.Position;
import com.arlania.model.Skill;
import com.arlania.util.Misc;
import com.arlania.world.content.PlayerPanel;
import com.arlania.world.entity.impl.player.Player;

public enum SlayerMaster {

    VANNAKA(1, 1597, new Position(3145, 9912)),
    DAVE(1, 2909, new Position(3078, 3493)),
    DURADEL(40, 8275, new Position(2826, 2958)),
    KURADEL(60, 9085, new Position(1748, 5326)),
    SUMONA(80, 7780, new Position(1795, 3781)),
    NIEVE(99, 20797, new Position(2431, 3426));

    SlayerMaster(int slayerReq, int npcId, Position telePosition) {
        this.slayerReq = slayerReq;
        this.npcId = npcId;
        this.position = telePosition;
    }

    private final int slayerReq;
    private final int npcId;
    private final Position position;

    public int getSlayerReq() {
        return this.slayerReq;
    }

    public int getNpcId() {
        return this.npcId;
    }

    public Position getPosition() {
        return this.position;
    }

    public static SlayerMaster forId(int id) {
        for (SlayerMaster master : SlayerMaster.values()) {
            if (master.ordinal() == id) {
                return master;
            }
        }
        return null;
    }

    public static void changeSlayerMaster(Player player, SlayerMaster master) {
        player.getPacketSender().sendInterfaceRemoval();
        if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < master.getSlayerReq()) {
            player.getPacketSender().sendMessage("This Slayer master does not teach noobies. You need a Slayer level of at least " + master.getSlayerReq() + ".");
            return;
        }
        if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
            player.getPacketSender().sendMessage("Please finish your current task before changing Slayer master.");
            return;
        }
        if (player.getSlayer().getSlayerMaster() == master) {
            player.getPacketSender().sendMessage(Misc.formatText(master.toString().toLowerCase()) + " is already your Slayer master.");
            return;
        }
        player.getSlayer().setSlayerMaster(master);
        PlayerPanel.refreshPanel(player);
        player.getPacketSender().sendMessage("You've sucessfully changed Slayer master.");
    }

    @Override
    public String toString() {
        return Misc.ucFirst(name().toLowerCase().replaceAll("_", " "));
    }
}
