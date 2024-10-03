package com.arlania.world.content.minigames.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.model.container.impl.Shop;
import com.arlania.model.input.impl.EnterAmountToBuyFromShop;
import com.arlania.model.input.impl.EnterAmountToSellToShop;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class RecipeForDisaster {

    public static void enter(Player player) {
        player.moveTo(new Position(1900, 5346, player.getIndex() * 4 + 2));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.RECIPE_FOR_DISASTER));
        spawnWave(player);

        if (!player.inDaily) {
            CurseHandler.deactivateAll(player);
            PrayerHandler.deactivateAll(player);
        }
    }

    public static void leave(Player player) {
        Location.RECIPE_FOR_DISASTER.leave(player);
        if (player.getRegionInstance() != null)
            player.getRegionInstance().destruct();
        player.barrowsRaid = false;
        player.inDaily = false;
        player.inSaradomin = false;
        player.inZamorak = false;
        player.inArmadyl = false;
        player.inBandos = false;
        player.inCerberus = false;
        player.inSire = false;
        player.inThermo = false;
        player.inCorporeal = false;
        player.inKBD = false;

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

                if (p.inDaily) {

                    int[] npc = {8349};
                    if (p.DailyKills > 0) {
                        NPC n = new NPC(npc[0], new Position(spawnPos.getX(), spawnPos.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        World.register(n);
                        p.getRegionInstance().getNpcsList().add(n);
                        n.getCombatBuilder().attack(p);
                        p.DailyKills--;
                    } else {
                        p.getPacketSender().sendMessage("@red@You have no more daily kills left! Leave with the portal!");
                    }
                } else if (p.instancedBosses) {
                    int length = 0;

                    if (p.inSaradomin)
                        length++;
                    if (p.inZamorak)
                        length++;
                    if (p.inArmadyl)
                        length++;
                    if (p.inBandos)
                        length++;
                    if (p.inCerberus)
                        length++;
                    if (p.inSire)
                        length++;
                    if (p.inThermo)
                        length++;
                    if (p.inCorporeal)
                        length++;
                    if (p.inKBD)
                        length++;

                    int[] npc = new int[length];
                    int i = 0;
                    if (p.inSaradomin) {
                        npc[i] = 6247;
                        i++;
                    }
                    if (p.inZamorak) {
                        npc[i] = 6203;
                        i++;
                    }
                    if (p.inArmadyl) {
                        npc[i] = 6222;
                        i++;
                    }
                    if (p.inBandos) {
                        npc[i] = 6260;
                        i++;
                    }
                    if (p.inCerberus) {
                        npc[i] = 1999;
                        i++;
                    }
                    if (p.inSire) {
                        npc[i] = 5886;
                        i++;
                    }
                    if (p.inThermo) {
                        npc[i] = 499;
                        i++;
                    }
                    if (p.inCorporeal) {
                        npc[i] = 8133;
                        i++;
                    }
                    if (p.inKBD) {
                        npc[i] = 50;
                        i++;
                    }


                    NPC n = new NPC(npc[RandomUtility.inclusiveRandom(length - 1)], new Position(spawnPos.getX(), spawnPos.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                    World.register(n);
                    p.getRegionInstance().getNpcsList().add(n);
                    //n.getCombatBuilder().attack(p);

                }

                stop();
            }
        });
    }

    public static void handleNPCDeath(final Player player, NPC n) {
        if (player.getRegionInstance() == null)
            return;
        player.getRegionInstance().getNpcsList().remove(n);

        if (player.getLocation() != Location.RECIPE_FOR_DISASTER)
            return;

        if (player.instancedBosses) {
            TaskManager.submit(new Task(3, player, false) {
                @Override
                public void execute() {
                    if (player.getLocation() != Location.RECIPE_FOR_DISASTER)
                        return;
                    spawnWave(player);
                    stop();
                }
            });
        } else if (player.inDaily) {
            TaskManager.submit(new Task(3, player, false) {
                @Override
                public void execute() {
                    if (player.getLocation() != Location.RECIPE_FOR_DISASTER)
                        return;
                    spawnWave(player);
                    stop();
                }
            });
        }
    }

    public static void openRFDShop(final Player player) {
        int[] stock = new int[33];
        int[] stockAmount = new int[33];
        for (int i = 0; i < stock.length; i++) {
            stock[i] = -1;
            stockAmount[i] = 1000;
        }
        for (int i = 0; i <= 6; i++) {
            switch (i) {
                case 1:
                    stock[0] = 7453;
                    break;
                case 2:
                    stock[1] = 7454;
                    stock[2] = 7455;
                    break;
                case 3:
                    stock[3] = 7456;
                    stock[4] = 7457;
                    break;
                case 4:
                    stock[5] = 7458;
                    break;
                case 5:
                    stock[6] = 7459;
                    stock[7] = 7460;
                    break;
                case 6:
                    stock[8] = 7461;
                    stock[9] = 7462;
                    stock[10] = 11967;
                    stock[11] = 10499;
                    stock[12] = 2412;
                    stock[13] = 2413;
                    stock[14] = 2414;
                    stock[15] = 3839;
                    stock[16] = 3841;
                    stock[17] = 3843;
                    stock[18] = 19612;
                    stock[19] = 19614;
                    stock[20] = 19616;
                    stock[21] = 10887;
                    stock[22] = 6106;
                    stock[23] = 6107;
                    stock[24] = 6108;
                    stock[25] = 6109;
                    stock[26] = 6110;
                    stock[27] = 6111;
                    stock[28] = 14641;
                    stock[29] = 14642;
                    stock[30] = 14017;
                    stock[31] = 10828;
                    stock[32] = 3105;
                    break;
            }
        }
        Item[] stockItems = new Item[stock.length];
        for (int i = 0; i < stock.length; i++)
            stockItems[i] = new Item(stock[i], stockAmount[i]);
        Shop shop = new Shop(player, Shop.RECIPE_FOR_DISASTER_STORE, "Culinaromancer's chest", new Item(995), stockItems);
        stock = stockAmount = null;
        stockItems = null;
        shop.setPlayer(player);
        player.getPacketSender().sendItemContainer(player.getInventory(), Shop.INVENTORY_INTERFACE_ID);
        player.getPacketSender().sendItemContainer(shop, Shop.ITEM_CHILD_ID);
        player.getPacketSender().sendString(Shop.NAME_INTERFACE_CHILD_ID, "Culinaromancer's chest");
        if (player.getInputHandling() == null || !(player.getInputHandling() instanceof EnterAmountToSellToShop || player.getInputHandling() instanceof EnterAmountToBuyFromShop))
            player.getPacketSender().sendInterfaceSet(Shop.INTERFACE_ID, Shop.INVENTORY_INTERFACE_ID - 1);
        player.setShop(shop).setInterfaceId(Shop.INTERFACE_ID).setShopping(true);
    }


    private static final Position spawnPos = new Position(1900, 5354);
    private static final Position minionSpawnPos = new Position(1895, 5349);

}
