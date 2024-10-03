package com.arlania.world.content.minigames.impl.strongholdraids;

import com.arlania.model.Direction;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Position;
import com.arlania.util.JsonLoader;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class Mobs {

    public static void spawnMobsF1(Player player) {
        new JsonLoader() {
            @Override
            public void load(JsonObject reader, Gson builder) {

                int id = reader.get("npc-id").getAsInt();
                Position position = builder.fromJson(reader.get("position").getAsJsonObject(), Position.class);
                position.setZ(player.instanceHeight);
                NPCMovementCoordinator.Coordinator coordinator = builder.fromJson(reader.get("walking-policy").getAsJsonObject(), NPCMovementCoordinator.Coordinator.class);
                Direction direction = Direction.valueOf(reader.get("face").getAsString());
                NPC npc = new NPC(id, position);
                //npc.getMovementCoordinator().setCoordinator(coordinator);
                npc.getMovementCoordinator().setCoordinator(new NPCMovementCoordinator.Coordinator(true, 0));
                npc.setDirection(direction);
                World.register(npc);
            }

            @Override
            public String filePath() {
                return "./data/def/json/raids/shrMobs.json";
            }
        }.load();
    }


    public static void handleNPCDeath(final Player player, NPC n) {

        if (RandomUtility.inclusiveRandom(25) == 1)
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(15333), n.getPosition(), player.getUsername(), true, 150, true, 1000));

        if (RandomUtility.inclusiveRandom(25) == 1)
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(2434), n.getPosition(), player.getUsername(), true, 150, true, 1000));

        int randKey = RandomUtility.inclusiveRandom(3);
        int keyID = 0;

        //1:2 odds to get a key
        if (RandomUtility.inclusiveRandom(1) == 1) {

            player.getPacketSender().sendMessage("A dungeoneering key has dropped!");

            //floor 1 key
            if (n.getId() == bathusWarrior || n.getId() == wildercressMage || n.getId() == subleatherRanger) {
                keyID = 18218 + (randKey * 2);
            }

            if (n.getId() == zephyriumWarrior || n.getId() == rosebloodMage || n.getId() == spinoleatherRanger) {
                keyID = 18234 + (randKey * 2);
            }

            if (n.getId() == promethiumWarrior || n.getId() == spiritbloomMage || n.getId() == tyrannoleatherRanger) {
                keyID = 18250 + (randKey * 2);
            }

            if (n.getId() == primalWarrior || n.getId() == celestialMage || n.getId() == sagittarianRanger) {
                keyID = 18298 + (randKey * 2);
            }

            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(keyID, 1), n.getPosition(), player.getUsername(), true, 150, true, 1000));

        }

        int randomLoot = 995;

        switch (n.getId()) {

            case 9767:
                randomLoot = Equipment.mediumEquipment[RandomUtility.inclusiveRandom(Equipment.mediumEquipment.length - 1)];
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));

                for(Player member : player.getRaidsParty().getPlayers()) {
                    member.strongholdLootFloor = 1;
                    member.strongholdRaidFloor = 2;

                    if(member == player.getRaidsParty().getOwner())
                        RaidFloorTwo.assignBossKey(member);
                }

                if (RandomUtility.inclusiveRandom(9) == 1)
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4036, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                else
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4037, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                break;
            case 10126:
                randomLoot = Equipment.highEquipment[RandomUtility.inclusiveRandom(Equipment.highEquipment.length - 1)];
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));

                for(Player member : player.getRaidsParty().getPlayers()) {
                    member.strongholdLootFloor = 2;
                    member.strongholdRaidFloor = 3;

                    if(member == player.getRaidsParty().getOwner())
                        RaidFloorThree.assignBossKey(member);
                }

                if (RandomUtility.inclusiveRandom(9) == 1)
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4033, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                else
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4036, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                break;
            case 12751:
                randomLoot = Equipment.legendaryEquipment[RandomUtility.inclusiveRandom(Equipment.legendaryEquipment.length - 1)];
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));

                for(Player member : player.getRaidsParty().getPlayers()) {
                    member.strongholdLootFloor = 3;
                    member.strongholdRaidFloor = 4;

                    if(member == player.getRaidsParty().getOwner())
                        RaidFloorFour.assignBossKey(member);
                }

                if (RandomUtility.inclusiveRandom(9) == 1)
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4034, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                else
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4033, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                break;
            case 22097:
                randomLoot = Equipment.masterEquipment[RandomUtility.inclusiveRandom(Equipment.masterEquipment.length - 1)];
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));

                for(Player member : player.getRaidsParty().getPlayers()) {
                    member.strongholdLootFloor = 4;
                }

                if (RandomUtility.inclusiveRandom(9) == 1)
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4035, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                else
                    GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(4034, 1), n.getPosition(), player.getUsername(), false, 150, true, 200));
                break;


        }

    }

    public static int randomMob(Player player) {


        if(player.getRaidsParty() == null)
            return -1;

        int[] floor1Mobs = {bathusWarrior, wildercressMage, subleatherRanger};
        int[] floor2Mobs = {zephyriumWarrior, rosebloodMage, spinoleatherRanger};
        int[] floor3Mobs = {promethiumWarrior, spiritbloomMage, tyrannoleatherRanger};
        int[] floor4Mobs = {primalWarrior, celestialMage, sagittarianRanger};

        if(player.strongholdRaidFloor == 4) {
            return floor4Mobs[RandomUtility.exclusiveRandom(floor4Mobs.length)];
        }
        else if(player.strongholdRaidFloor == 3) {
            return floor3Mobs[RandomUtility.exclusiveRandom(floor3Mobs.length)];
        }
        else if(player.strongholdRaidFloor == 2) {
            return floor2Mobs[RandomUtility.exclusiveRandom(floor2Mobs.length)];
        }
        else if(player.strongholdRaidFloor == 1) {
            return floor1Mobs[RandomUtility.exclusiveRandom(floor1Mobs.length)];
        }

        return -1;
    }


    //SHR NPCs
    private static final int bathusWarrior = 10422; //Battle-axe (4 tick - max hit 8)
    private static final int zephyriumWarrior = 10443; //Battle-axe (4 tick - max hit 12)
    private static final int promethiumWarrior = 10511; //Battle-axe (4 tick - max hit 32)
    private static final int primalWarrior = 10547; //Maul (6 tick - max hit 55)

    private static final int wildercressMage = 10565; //Fire Bolt (5 tick - max hit 12)
    private static final int rosebloodMage = 10586; //Fire Blast (5 tick - max hit 18)
    private static final int spiritbloomMage = 10599; //Fire Surge (5 tick - max hit 30)
    private static final int celestialMage = 10603; //Fire Wave (5 tick - max hit 45)

    private static final int subleatherRanger = 10327; //Bronze arrows (3 tick - max hit 8)
    private static final int spinoleatherRanger = 10342; //Mithril arrows (3 tick - max hit 10)
    private static final int tyrannoleatherRanger = 10352; //Rune arrows (3 tick - max hit 24)
    private static final int sagittarianRanger = 10362; //Dragon arrows (3 tick - max hit 32)


}
