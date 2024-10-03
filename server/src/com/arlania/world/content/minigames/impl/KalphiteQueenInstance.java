package com.arlania.world.content.minigames.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class KalphiteQueenInstance {

    public static void enter(Player player) {
        final int height = player.getIndex() * 4;

        player.moveTo(new Position(3485, 9509, height), true);
        player.getPacketSender().sendMessage("You walk down the staircase.");

        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.KALPHITE_QUEEN));
        spawnWave(player, 1160);

    }

    public static void leave(Player player) {
        Location.KALPHITE_QUEEN.leave(player);
        if (player.getRegionInstance() != null)
            player.getRegionInstance().destruct();
    }

    public static void spawnWave(final Player p, final int id) {
        if (p.getRegionInstance() == null)
            return;

        int tempId = -1;

        if (id == 1158) {
            tempId = 1160;
        } else if (id == 1160) {
            tempId = 1158;
        } else {
            return;
        }

        final int spawnId = tempId;

        TaskManager.submit(new Task(2, p, false) {
            @Override
            public void execute() {
                if (p.getRegionInstance() == null) {
                    stop();
                    return;
                }

                NPC n = new NPC(spawnId, new Position(spawnPos.getX(), spawnPos.getY(), p.getPosition().getZ())).setSpawnedFor(p);

                if (p.difficulty > 0)
                    n.difficultyPerks(n, p.difficulty);

                World.register(n);

                if (p.getAfkAttack().elapsed(900000)) //15 minutes
                    p.getRegionInstance().getNpcsList().add(n);
                else {
                    p.getRegionInstance().getNpcsList().add(n);
                    n.getCombatBuilder().attack(p);
                }
                stop();
            }
        });
    }

    public static void handleNPCDeath(final Player player, NPC n) {
        if (player.getRegionInstance() == null)
            return;
        player.getRegionInstance().getNpcsList().remove(n);

        if (player.getLocation() != Location.KALPHITE_QUEEN)
            return;

        boolean adventurerBoost = player.getCollectionLog().getKills(n.getId()) >= 250;

        NPCDrops.dropItems(player, n, player.getPosition(), adventurerBoost);

        if (player.difficulty >= RandomUtility.inclusiveRandom(1, 10)) {
            NPCDrops.dropItems(player, n, player.getPosition(), false);
        }

        //Nobility System (additional loot roll)
        if (NobilitySystem.getNobilityBoost(player) > RandomUtility.RANDOM.nextDouble()) {
            NPCDrops.dropItems(player, n, player.getPosition(), adventurerBoost);
            player.getPacketSender().sendMessage("You've received an additional drop with your Nobility Rank");
        }

        TaskManager.submit(new Task(3, player, false) {
            @Override
            public void execute() {
                if (player.getLocation() != Location.KALPHITE_QUEEN)
                    return;

                spawnWave(player, n.getId());
                stop();
            }
        });
    }


    private static final Position spawnPos = new Position(3485, 9509);
}
