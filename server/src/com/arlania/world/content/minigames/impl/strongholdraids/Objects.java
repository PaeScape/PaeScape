package com.arlania.world.content.minigames.impl.strongholdraids;


import com.arlania.model.*;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.skill.impl.fishing.Fishing;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator;
import com.arlania.world.entity.impl.player.Player;

import static com.arlania.world.content.minigames.impl.strongholdraids.Raid.leave;

public class Objects {


    public static void objectHandler(Player player) {

        GameObject gameObject = player.getInteractingObject();

        if (player.getRaidsParty() == null) {
            Locations.Location.SHR.leave(player);
            player.getPacketSender().sendMessage("You were kicked for not being in a Raid Party.");
        }

        switch (gameObject.getId()) {

            case 75:
                player.getPacketSender().sendMessage("Use a key on this chest.");
                break;
            case 320784: //Floor 1 Ladder EXIT
                leave(player);
                break;
            case 323694: //yellow
            case 323695: //green
            case 323696: //blue
            case 323697: //red
                gatherResource(player, gameObject.getId());
                break;
            case 323955:
                spawnMob(player);
                break;
            case 8702:
                Fishing.setupFishing(player, Fishing.Spot.SHR);
                break;
            case 323677:
                leftClickErector(player);
                break;
            case 336076:
                if (player.strongholdLootFloor == 4) {

                    if (player.getRaidsParty() != null && player.getRaidsParty().getOwner() == player) {

                        RaidsParty party = player.getRaidsParty();

                        for (Player members : party.getPlayers()) {
                            endRaid(members, party);
                        }
                    }
                }

                break;
            default:
                player.getPacketSender().sendMessage("This object doesn't do anything.");
                break;
        }
    }

    public static void endRaid(Player player, RaidsParty party) {

        if(player.getMailBox().isFull()) {
            player.getPacketSender().sendMessage("You need empty space in your Mailbox to store SHR keys.");
            com.arlania.world.content.minigames.impl.strongholdraids.Raid.leave(player);
            return;
        }

        player.getPacketSender().sendMessage("Your loot was sent to your Mailbox.");

        //send earned SHR keys and sub boxes to player's mailbox
        com.arlania.world.content.minigames.impl.strongholdraids.Rewards.grantMailboxLoot(party, player);

        player.getPacketSender().sendMessage("Your Raid has reset.");

        //reset raid settings
        if(player == party.getOwner()) {
            com.arlania.world.content.minigames.impl.strongholdraids.Raid.start(party);
        }

    }

    public static void spawnMob(Player player) {

        if (player.getRaidsParty() == null)
            return;

        int y = 0;

        if (player.getPosition().getY() < 9639)
            y = 9634;

        else if (player.getPosition().getY() >= 9639)
            y = 9646;

        int id = Mobs.randomMob(player);

        if (id == -1)
            return;

        if (player.getInventory().contains(player.getRaidsParty().shrItem)) {

            player.getInventory().delete(player.getRaidsParty().shrItem, 1);

            Position position = new Position(3363, y, player.instanceHeight);
            NPC npc = new NPC(id, position);
            npc.getMovementCoordinator().setCoordinator(new NPCMovementCoordinator.Coordinator(true, 2));
            World.register(npc);
            player.getRaidsParty().getOwner().getRegionInstance().getNpcsList().add(npc);
            npc.getCombatBuilder().attack(player);
        } else {
            player.getPacketSender().sendMessage("You don't have the proper item.");
        }


    }


    public static void gatherResource(Player player, int objectId) {

        switch (objectId) {

            case 323694: //yellow
                if(!player.getInventory().contains(6899)) {
                    player.getInventory().add(6899, 25);
                } else {
                    player.getInventory().add(6899, player.acceleratedResources());
                }
                break;
            case 323695: //green
                if(!player.getInventory().contains(6898)) {
                    player.getInventory().add(6898, 25);
                } else {
                    player.getInventory().add(6898, player.acceleratedResources());
                }
                break;
            case 323696: //blue
                if(!player.getInventory().contains(6900)) {
                    player.getInventory().add(6900, 25);
                } else {
                    player.getInventory().add(6900, player.acceleratedResources());
                }
                break;
            case 323697: //red
                if(!player.getInventory().contains(6901)) {
                    player.getInventory().add(6901, 25);
                } else {
                    player.getInventory().add(6901, player.acceleratedResources());
                }
                break;


        }


    }

    public static void leftClickErector(Player player) {

        if (player.getRaidsParty() == null) {
            Locations.Location.SHR.leave(player);
            player.getPacketSender().sendMessage("You were kicked for not being in a Raid Party.");
        }

        int itemId = -1;

        for (Item item : player.getInventory().getItems()) {
            if (item != null && item.getDefinition().getName().toLowerCase().contains("key")) {
                itemId = item.getId();
                break;
            }
        }

        if (itemId < 0) {
            player.getPacketSender().sendMessage("You have no keys in your inventory.");
            return;
        }

        GameObject gameObject = player.getInteractingObject();
        ItemDefinition defs = ItemDefinition.forId(itemId);

        //Floor one door chests
        if (defs.getName().toLowerCase().contains("silver") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 1);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }

        //Floor two door chests
        if (defs.getName().toLowerCase().contains("yellow") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 2);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }

        //Floor three door chests
        if (defs.getName().toLowerCase().contains("green") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 3);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }

        //Floor four door chests
        if (defs.getName().toLowerCase().contains("crimson") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 4);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }


    }

    public static void handleErector(Player player, int itemId) {

        if (player.getRaidsParty() == null) {
            Locations.Location.SHR.leave(player);
            player.getPacketSender().sendMessage("You were kicked for not being in a Raid Party.");
        }

        GameObject gameObject = player.getInteractingObject();
        ItemDefinition defs = ItemDefinition.forId(itemId);

        //Floor one door chests
        if (defs.getName().toLowerCase().contains("silver") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 1);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }

        //Floor two door chests
        if (defs.getName().toLowerCase().contains("yellow") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 2);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }

        //Floor three door chests
        if (defs.getName().toLowerCase().contains("green") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 3);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }

        //Floor four door chests
        if (defs.getName().toLowerCase().contains("crimson") && defs.getName().toLowerCase().contains("key")) {
            gameObject.performGraphic(new Graphic(542, GraphicHeight.LOW));
            player.getInventory().delete(itemId, 1);
            chestLoot(player, gameObject, 4);
            player.getPacketSender().sendMessage("You add your key to the Erector leaving some loot on the ground!");
        }


    }

    public static void handleBossChest(Player player, int itemId) {

        if (player.getRaidsParty() == null) {
            Locations.Location.SHR.leave(player);
            player.getPacketSender().sendMessage("You were kicked for not being in a Raid Party.");
        }

        RaidsParty party = player.getRaidsParty();

        Item bossKey = new Item(itemId);

        //Floor one boss chest
        if (itemId == party.shrFloorOneBossKey && player.getInventory().contains(party.shrFloorOneBossKey) && !party.shrFloorOneBossSpawned) {
            player.getInventory().delete(itemId, 1);
            party.shrFloorOneBossSpawned = true;
            RaidFloorOne.spawnBoss(party, player.instanceHeight);
        }
        //Floor two boss chest
        else if (itemId == party.shrFloorTwoBossKey && player.getInventory().contains(party.shrFloorTwoBossKey) && !party.shrFloorTwoBossSpawned) {
            player.getInventory().delete(itemId, 1);
            party.shrFloorTwoBossSpawned = true;
            RaidFloorTwo.spawnBoss(party, player.instanceHeight);
        }
        //Floor three boss chest
        else if (itemId == party.shrFloorThreeBossKey && player.getInventory().contains(party.shrFloorThreeBossKey) && !party.shrFloorThreeBossSpawned) {
            player.getInventory().delete(itemId, 1);
            party.shrFloorThreeBossSpawned = true;
            RaidFloorThree.spawnBoss(party, player.instanceHeight);
        }
        //Floor four boss chest
        else if (itemId == party.shrFloorFourBossKey && player.getInventory().contains(party.shrFloorFourBossKey) && !party.shrFloorFourBossSpawned) {
            player.getInventory().delete(itemId, 1);
            party.shrFloorFourBossSpawned = true;
            RaidFloorFour.spawnBoss(party, player.instanceHeight);
        } else
            player.getPacketSender().sendMessage("Use a " + bossKey.getName() + " on the Lectern to spawn this floor's boss.");
    }

    public static void chestLoot(Player player, GameObject gameObject, int type) {

        int x = 0;

        if (gameObject.getPosition().getX() > 3368)
            x = 3373;
        else if (gameObject.getPosition().getX() <= 3368)
            x = 3354;

        Position spawnPos = new Position(x, 9640, player.instanceHeight);

        if (type == 1) {
            int randomLoot = Equipment.lowEquipment[RandomUtility.inclusiveRandom(Equipment.lowEquipment.length - 1)];
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), spawnPos, player.getUsername(), false, 150, true, 200));
        } else if (type == 2) {
            int randomLoot = Equipment.mediumEquipment[RandomUtility.inclusiveRandom(Equipment.mediumEquipment.length - 1)];
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), spawnPos, player.getUsername(), false, 150, true, 200));
        } else if (type == 3) {
            int randomLoot = Equipment.highEquipment[RandomUtility.inclusiveRandom(Equipment.highEquipment.length - 1)];
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), spawnPos, player.getUsername(), false, 150, true, 200));
        } else if (type == 4) {
            int randomLoot = Equipment.legendaryEquipment[RandomUtility.inclusiveRandom(Equipment.legendaryEquipment.length - 1)];
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(randomLoot, 1), spawnPos, player.getUsername(), false, 150, true, 200));
        }
    }


    private static final int[][] FLOOR_ONE = {
            {75, 2677, 5061, 0, 2}, //face south
            {75, 2678, 5061, 0, 2}, //face south
            {75, 2679, 5061, 0, 2}, //face south
            {75, 2667, 5066, 0, 2}, //face south
            {75, 2651, 5062, 0, 3}, //facing west
            {75, 2651, 5063, 0, 3}, //facing west
            {75, 2677, 5074, 0, 1}, //facing east
            {75, 2676, 5088, 0, 2}, //face south
            {75, 2681, 5093, 0, 2}, //face south
            {75, 2678, 5106, 0, 2}, //face south
            {75, 2679, 5106, 0, 2}, //face south
            {75, 2656, 5110, 0, 2}, //face south
            {75, 2657, 5110, 0, 2}, //face south ----- end of west objects
            {75, 2688, 5110, 0, 3}, //facing west
            {75, 2688, 5111, 0, 3}, //facing west
            {75, 2706, 5113, 0, 3}, //facing west
            {75, 2706, 5114, 0, 3}, //facing west
            {75, 2695, 5092, 0, 0}, //face north
            {75, 2702, 5087, 0, 0}, //face north
            {75, 2695, 5074, 0, 3}, //facing west
            {75, 2695, 5075, 0, 3}, //facing west
            {75, 2695, 5068, 0, 3}, //facing west
            {75, 2695, 5067, 0, 3}, //facing west
            {75, 2707, 5065, 0, 3}, //facing west
            {75, 2721, 5064, 0, 2}, //face south
            {75, 2722, 5064, 0, 2}, //face south
            {75, 2727, 5072, 0, 3}, //facing west

    };
}
