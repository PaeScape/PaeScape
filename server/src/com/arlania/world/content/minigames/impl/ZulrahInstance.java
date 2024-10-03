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
public class ZulrahInstance {

    public static void enter(Player player) {
        final int height = player.getIndex() * 4;
        player.moveTo(new Position(2265, 3069, height));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.ZULRAH));
        spawnWave(player);

    }

    public static void leave(Player player) {
        Location.ZULRAH.leave(player);
        if (player.getRegionInstance() != null)
            player.getRegionInstance().destruct();
    }

    public static void spawnWave(final Player p) {
        if (p.getRegionInstance() == null)
            return;
        TaskManager.submit(new Task(2, p, false) {
            @Override
            public void execute() {
                if (p.getRegionInstance() == null) {
                    stop();
                    return;
                }
                NPC n = null;

                switch (RandomUtility.inclusiveRandom(3)) {

                    case 1:
                        n = new NPC(2042, new Position(spawnPos.getX(), spawnPos.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        break;
                    case 2:
                        n = new NPC(2043, new Position(spawnPos.getX(), spawnPos.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        break;
                    case 3:
                        n = new NPC(2044, new Position(spawnPos.getX(), spawnPos.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        break;
                    default:
                        n = new NPC(2042, new Position(spawnPos.getX(), spawnPos.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        break;

                }

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

        if (player.getLocation() != Location.ZULRAH)
            return;

        boolean adventurerBoost = player.getCollectionLog().getKills(n.getId()) >= 250;

        NPCDrops.dropItems(player, n, player.getPosition(), adventurerBoost);

        if(player.difficulty >= RandomUtility.inclusiveRandom(1, 10)) {
            NPCDrops.dropItems(player, n, player.getPosition(), false);
        }

        //Nobility System (additional loot roll)
        if(NobilitySystem.getNobilityBoost(player) > RandomUtility.RANDOM.nextDouble()) {
            NPCDrops.dropItems(player, n, player.getPosition(), adventurerBoost);
            player.getPacketSender().sendMessage("You've received an additional drop with your Nobility Rank");
        }

        TaskManager.submit(new Task(3, player, false) {
            @Override
            public void execute() {
                if (player.getLocation() != Location.ZULRAH)
                    return;

                spawnWave(player);
                stop();
            }
        });
    }


    private static final Position spawnPos = new Position(2271, 3069);

}