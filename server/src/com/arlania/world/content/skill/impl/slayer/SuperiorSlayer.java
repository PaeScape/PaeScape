package com.arlania.world.content.skill.impl.slayer;

import com.arlania.model.Locations;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 */

public enum SuperiorSlayer {

    CRAWLING_HAND(new int[]{1653, 1654, 1655, 1656, 1657}, new int[]{21388}, 203230),
    CAVE_CRAWLER(new int[]{1600}, new int[]{21389}, 203231),
    BANSHEE(new int[]{1612}, new int[]{21390}, 203232),
    COCKATRICE(new int[]{1621}, new int[]{21393}, 203233),
    PYREFIEND(new int[]{6216}, new int[]{21394}, 203234),
    BASILISK(new int[]{1616}, new int[]{21395}, 203235),
    INFERNAL_MAGE(new int[]{1643}, new int[]{21396}, 203236),
    BLOODVELD(new int[]{1618}, new int[]{21397}, 203237),
    TUROTH(new int[]{1626}, new int[]{24397}, 203239),
    ABERRANT_SPECTRE(new int[]{1604}, new int[]{21402}, 203241),
    KURASK(new int[]{1608, 14410}, new int[]{21405}, 203243),
    GARGOYLE(new int[]{1610}, new int[]{21407}, 203244),
    NECHRYAEL(new int[]{1613}, new int[]{21411}, 203245),
    ABYSSAL_DEMON(new int[]{1615}, new int[]{21410}, 203246),
    DARK_BEAST(new int[]{2783}, new int[]{21409}, 203247);


    SuperiorSlayer(int[] npcId, int[] superiorId, int keyId) {
        this.npcId = npcId;
        this.superiorId = superiorId;
        this.keyId = keyId;
    }

    private final int[] npcId;
    private final int[] superiorId;
    private final int keyId;


    public int[] getNpcId() {
        return this.npcId;
    }

    public int[] getSuperiorId() {
        return this.superiorId;
    }

    public int getKeyId() {
        return this.keyId;
    }

    public static SuperiorSlayer forId(int id) {
        for (SuperiorSlayer mobs : SuperiorSlayer.values()) {
            if (mobs.ordinal() == id) {
                return mobs;
            }
        }
        return null;
    }

    public static int superiorForKeyId(int id) {
        for (SuperiorSlayer mobs : SuperiorSlayer.values()) {
            if (mobs.keyId == id) {
                return mobs.getSuperiorId()[0];
            }
        }
        return -1;
    }

    public static int mobForKeyId(int id) {
        for (SuperiorSlayer mobs : SuperiorSlayer.values()) {
            if (mobs.keyId == id) {
                return mobs.getNpcId()[0];
            }
        }
        return -1;
    }

    public static boolean isSlayerKey(int keyId) {

        boolean isKey = false;

        switch (keyId) {
            case 203230:
            case 203231:
            case 203232:
            case 203233:
            case 203234:
            case 203235:
            case 203236:
            case 203237:
            case 203238:
            case 203239:
            case 203240:
            case 203241:
            case 203242:
            case 203243:
            case 203244:
            case 203245:
            case 203246:
            case 203247:
                return true;

        }

        return isKey;
    }

    public static void createInstance(Player player) {
        if (player.getRegionInstance() == null) {
            // take the key
            player.getInventory().delete(player.slayerInstanceKey, 1);

            // move player to instance
            final int height = player.getIndex() * 4;
            player.instanceHeight = height;
            player.moveTo(new Position(1695, 4575, height));
            player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCED_SLAYER));

            // ensure instance is empty
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Locations.Location.INSTANCED_SLAYER, player.getPosition().getZ()));

            // spawn the first wave
            player.slayerInstanceWave = 1;
            player.slayerWaveKills = 0;
            spawn(player);
        } else {
            // player already in superior slayer instance
            if (player.getRegionInstance().getType() != RegionInstance.RegionInstanceType.INSTANCED_SLAYER) {
                player.getPacketSender().sendMessage("Superior Slayer bug. Screenshot and create a ticket." + player.getRegionInstance().getType());
                return;
            }

            // take the key
            player.getInventory().delete(player.slayerInstanceKey, 1);

            // ensure instance is empty
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Locations.Location.INSTANCED_SLAYER, player.getPosition().getZ()));

            // spawn the first wave
            player.slayerInstanceWave = 1;
            player.slayerWaveKills = 0;
            spawn(player);
        }
    }

    public static void handleNPCDeath(Player player, NPC npc) {
        NPCDrops.dropItems(player, npc, npc.getPosition(), false);

        if(player.difficulty >= RandomUtility.inclusiveRandom(1, 10)) {
            NPCDrops.dropItems(player, npc, npc.getPosition(), false);
        }

        //Nobility System (additional loot roll)
        if(NobilitySystem.getNobilityBoost(player) > RandomUtility.RANDOM.nextDouble()) {
            NPCDrops.dropItems(player, npc, npc.getPosition(), false);
            player.getPacketSender().sendMessage("You've received an additional drop with your Nobility Rank!");
        }

        //Pet Hatius (additional loot roll)
        if(player.getHatiusLootRoll()) {
            if (RandomUtility.inclusiveRandom(0, 100) < 5) {
                NPCDrops.dropItems(player, npc, npc.getPosition(), false);
                player.getPacketSender().sendMessage("You've received an additional drop with your Pet Hatius!");
            }
        }


        player.slayerWaveKills++;

        if (player.slayerWaveKills >= player.slayerWaveQty) {
            player.slayerInstanceWave++;
            player.slayerWaveKills = 0;
            spawn(player);
        }

    }

    public static void spawn(Player player) {
        if (player.slayerInstanceWave < 25) {
            //spawn normal
            spawnWave(player, mobForKeyId(player.slayerInstanceKey));
        } else if (player.slayerInstanceWave == 25) {
            //spawn Superior
            spawnWave(player, superiorForKeyId(player.slayerInstanceKey));
        } else {
            // spawn sumona
            player.getPacketSender().sendMessage("Superior instance completed! Sumona has appeared.");

            NPC slayerMaster = new NPC(7780, new Position(1700, 4571, player.instanceHeight));
            World.register(slayerMaster);
            player.getRegionInstance().getNpcsList().add(slayerMaster);
        }
    }

    private static void spawnWave(Player player, int superiorId) {
        final Position[] spawnLocations = {
                new Position(1692, 4580, player.instanceHeight),
                new Position(1701, 4578, player.instanceHeight),
                new Position(1700, 4571, player.instanceHeight)
        };

        player.getPacketSender().sendMessage("Spawning Wave: " + player.slayerInstanceWave);

        for (int i = 0; i < player.slayerWaveQty; i++) {
            NPC superior = new NPC(superiorId, spawnLocations[i]);

            if (player.difficulty > 0)
                superior.difficultyPerks(superior, player.difficulty);

            World.register(superior);
            player.getRegionInstance().getNpcsList().add(superior);
            superior.getCombatBuilder().attack(player);
        }
    }


}
