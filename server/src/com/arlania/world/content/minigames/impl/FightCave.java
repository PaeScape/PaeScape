package com.arlania.world.content.minigames.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Locations;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class FightCave {

    public static final int JAD_NPC_ID = 2745;

    public static void enterCave(Player player) {
        player.moveTo(new Position(2413, 5117, player.getIndex() * 4));
        DialogueManager.start(player, 36);
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.FIGHT_CAVE));
        spawnJad(player);
    }

    public static void leaveCave(Player player) {
        Locations.Location.FIGHT_CAVES.leave(player);

    }

    public static void spawnJad(final Player player) {
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Location.FIGHT_CAVES) {
                    stop();
                    return;
                }
                NPC n = new NPC(JAD_NPC_ID, new Position(2399, 5083, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(n);
                player.getRegionInstance().getNpcsList().add(n);
                n.getCombatBuilder().attack(player);
                stop();
            }
        });
    }

    public static void handleJadDeath(final Player player, NPC n) {
        if (n.getId() == JAD_NPC_ID) {
            if (player.getRegionInstance() != null)
                player.getRegionInstance().getNpcsList().remove(n);
            //player.getSlayer().killedNpc(n);
            leaveCave(player);
            DialogueManager.start(player, 37);
            player.getInventory().add(6570, 1).add(6529, 1000 + RandomUtility.inclusiveRandom(500));

        }
    }

}
