package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.*;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class RaidChest {


    public static void raidsLobbyChest(final Player p, final GameObject chest) {

        if (p.getInventory().contains(211942))
            gwdRaidChest(p, chest);
        else
            chaosRaidChest(p, chest);


    }


    public static void coxChest(final Player p, final GameObject chest) {

        int raidKey = 202399;
        String raidType = "CoX";


        //Check inventory for raid keys and compare it to current location of the raid chest

        if (!p.getInventory().contains(raidKey)) {
            p.getPacketSender().sendMessage("This chest can only be opened with a " + raidType + " Raid Key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 3) {
            p.getPacketSender().sendMessage("You need at least 3 empty inventory spaces to use this chest.");
            return;
        }

        p.getInventory().delete(raidKey, 1);


        p.getPacketSender().sendMessage("You open the chest...");


        int roll = RandomUtility.inclusiveRandom(510);


        //celebration
        if (roll > 0 && roll < 18) {
            p.performAnimation(new Animation(862));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1229, 3557, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1230, 3557, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1229, 3559, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1230, 3559, 0));
        }

        //low supply
        int lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
        int lootOneQty = 100 + RandomUtility.inclusiveRandom(250);

        //high supply
        int lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
        int lootTwoQty = 10 + RandomUtility.inclusiveRandom(40);


        p.getInventory().add(lootOne, lootOneQty);
        p.getInventory().add(lootTwo, lootTwoQty);

        if (!p.chatFilter && p.personalFilterKeyLoot) {
            Item itemOne = new Item(lootOne, lootOneQty);
            Item itemTwo = new Item(lootTwo, lootTwoQty);
            p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
            p.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
        }

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootTwo)), new Item(lootTwo, lootTwoQty), p.getInventory().getSlot(lootTwo), false, true);
        }


        if (roll == 1) //Twisted Bow
        {
            p.getInventory().add(20998, 1);
            announceCOX(p, new Item(20998));
        } else if (roll == 2) //Elder Maul
        {
            p.getInventory().add(4450, 1);
            announceCOX(p, new Item(4450));
        } else if ((roll == 3))//Kodai Wand
        {
            p.getInventory().add(4454, 1);
            announceCOX(p, new Item(4454));
        } else if ((roll == 4) || (roll == 5))//Dragon Claws
        {
            p.getInventory().add(14484, 1);
            announceCOX(p, new Item(14484));
        } else if ((roll == 6) || (roll == 7))//Ancestral Hat
        {
            p.getInventory().add(18888, 1);
            announceCOX(p, new Item(18888));
        } else if ((roll == 8) || (roll == 9))//Ancestral Robe Top
        {
            p.getInventory().add(18889, 1);
            announceCOX(p, new Item(18889));
        } else if ((roll == 10) || (roll == 11))//Ancestral Robe Bottoms
        {
            p.getInventory().add(18890, 1);
            announceCOX(p, new Item(18890));
        } else if ((roll == 12) || (roll == 13) || (roll == 14))//DHCB
        {
            p.getInventory().add(18844, 1);
            announceCOX(p, new Item(18844));
        } else if ((roll == 15) || (roll == 16) || (roll == 17))//Twisted Buckler
        {
            p.getInventory().add(4452, 1);
            announceCOX(p, new Item(4452));
        }

    }


    public static void tobChest(final Player p, final GameObject chest) {

        int raidKey = 220526;
        String raidType = "ToB";


        //Check inventory for raid keys and compare it to current location of the raid chest

        if (!p.getInventory().contains(raidKey)) {
            p.getPacketSender().sendMessage("This chest can only be opened with a " + raidType + " Raid Key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 3) {
            p.getPacketSender().sendMessage("You need at least 3 empty inventory spaces to use this chest.");
            return;
        }


        p.getInventory().delete(raidKey, 1);


        p.getPacketSender().sendMessage("You open the chest...");


        int roll = RandomUtility.inclusiveRandom(450);


        //celebration
        if (roll > 0 && roll < 16) {
            p.performAnimation(new Animation(862));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3662, 3218, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3663, 3218, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3662, 3220, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3663, 3220, 0));
        }

        //low supply
        int lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
        int lootOneQty = 100 + RandomUtility.inclusiveRandom(250);

        //high supply
        int lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
        int lootTwoQty = 10 + RandomUtility.inclusiveRandom(40);

        p.getInventory().add(4604, RandomUtility.inclusiveRandom(4) + 1);
        p.getInventory().add(lootOne, lootOneQty);
        p.getInventory().add(lootTwo, lootTwoQty);

        if (!p.chatFilter && p.personalFilterKeyLoot) {
            Item itemOne = new Item(lootOne, lootOneQty);
            Item itemTwo = new Item(lootTwo, lootTwoQty);
            p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
            p.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
        }

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootTwo)), new Item(lootTwo, lootTwoQty), p.getInventory().getSlot(lootTwo), false, true);
        }


        if (roll == 1) //Scythe of Vitur
        {
            p.getInventory().add(21006, 1);
            announceTOB(p, new Item(21006));
        } else if (roll == 2) //Sanguinesti Staff
        {
            p.getInventory().add(21005, 1);
            announceTOB(p, new Item(21005));
        } else if ((roll == 3) || (roll == 4))//Avernic Defender
        {
            p.getInventory().add(21004, 1);
            announceTOB(p, new Item(21004));
        } else if ((roll == 5) || (roll == 6))//Ghrazi Rapier
        {
            p.getInventory().add(21003, 1);
            announceTOB(p, new Item(21003));
        } else if ((roll == 7) || (roll == 8) || (roll == 9))//Justiciar Faceguard
        {
            p.getInventory().add(21000, 1);
            announceTOB(p, new Item(21000));
        } else if ((roll == 10) || (roll == 11) || (roll == 12))//Justiciar Chestguard
        {
            p.getInventory().add(21001, 1);
            announceTOB(p, new Item(21001));
        } else if ((roll == 13) || (roll == 14) || (roll == 15))//Justiciar Legguards
        {
            p.getInventory().add(21002, 1);
            announceTOB(p, new Item(21002));
        }

    }


    public static void gwdRaidChest(final Player p, final GameObject chest) {

        int raidKey = 211942;
        String raidType = "GWD";


        //Check inventory for raid keys and compare it to current location of the raid chest

        if (!p.getInventory().contains(raidKey)) {
            p.getPacketSender().sendMessage("This chest can only be opened with a " + raidType + " Raid Key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 3) {
            p.getPacketSender().sendMessage("You need at least 3 empty inventory spaces to use this chest.");
            return;
        }

        p.getInventory().delete(raidKey, 1);


        p.getPacketSender().sendMessage("You open the chest...");


        int roll = RandomUtility.inclusiveRandom(300);

        //celebration
        if (roll > 0 && roll < 11) {
            p.performAnimation(new Animation(862));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3662, 3218, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3663, 3218, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3662, 3220, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3663, 3220, 0));
        }

        //low supply
        int lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
        int lootOneQty = 100 + RandomUtility.inclusiveRandom(250);

        //high supply
        int lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
        int lootTwoQty = 10 + RandomUtility.inclusiveRandom(40);

        p.getInventory().add(lootOne, lootOneQty);
        p.getInventory().add(lootTwo, lootTwoQty);

        if (!p.chatFilter && p.personalFilterKeyLoot) {
            Item itemOne = new Item(lootOne, lootOneQty);
            Item itemTwo = new Item(lootTwo, lootTwoQty);
            p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
            p.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
        }

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootTwo)), new Item(lootTwo, lootTwoQty), p.getInventory().getSlot(lootTwo), false, true);
        }


        if (roll == 1) {
            p.getInventory().add(14008, 1);
            announceGWD(p, new Item(14008));
        }
        if (roll == 2) {
            p.getInventory().add(14009, 1);
            announceGWD(p, new Item(14009));
        }
        if (roll == 3) {
            p.getInventory().add(14010, 1);
            announceGWD(p, new Item(14010));
        }
        if (roll == 4) {
            p.getInventory().add(14011, 1);
            announceGWD(p, new Item(14011));
        }
        if (roll == 5) {
            p.getInventory().add(14012, 1);
            announceGWD(p, new Item(14012));
        }
        if (roll == 6) {
            p.getInventory().add(14013, 1);
            announceGWD(p, new Item(14013));
        }
        if (roll == 7) {
            p.getInventory().add(14014, 1);
            announceGWD(p, new Item(14014));
        }
        if (roll == 8) {
            p.getInventory().add(14015, 1);
            announceGWD(p, new Item(14015));
        }
        if (roll == 9) {
            p.getInventory().add(14016, 1);
            announceGWD(p, new Item(14016));
        }
        if (roll == 10) //Zaros Hilt
        {
            p.getInventory().add(224003, 1);
            announceGWD(p, new Item(224003));
        }

    }


    public static void chaosRaidChest(final Player p, final GameObject chest) {

        int keyFive = 201543;
        int keySix = 201544;
        int keySeven = 201545;
        int keyEight = 201546;
        int keyNine = 201547;
        int keyTen = 206754;
        String raidType = "Chaos";

        int usedKey = 0;

        //Check inventory for raid keys and compare it to current location of the raid chest

        if (!p.getInventory().contains(keyFive) && !p.getInventory().contains(keySix) &&
                !p.getInventory().contains(keySeven) && !p.getInventory().contains(keyEight) &&
                !p.getInventory().contains(keyNine) && !p.getInventory().contains(keyTen)) {
            p.getPacketSender().sendMessage("This chest can only be opened with a " + raidType + " Raid Key or GWD Raids Key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 3) {
            p.getPacketSender().sendMessage("You need at least 3 empty inventory spaces to use this chest.");
            return;
        }

        int maxRoll = 0;
        int uniqueRolls = 24;

        if (p.getInventory().contains(keyTen)) {
            usedKey = keyTen;
            maxRoll = uniqueRolls * 10;
        } else if (p.getInventory().contains(keyNine)) {
            usedKey = keyNine;
            maxRoll = uniqueRolls * 13;
        } else if (p.getInventory().contains(keyEight)) {
            usedKey = keyEight;
            maxRoll = uniqueRolls * 17;
        } else if (p.getInventory().contains(keySeven)) {
            usedKey = keySeven;
            maxRoll = uniqueRolls * 23;
        } else if (p.getInventory().contains(keySix)) {
            usedKey = keySix;
            maxRoll = uniqueRolls * 32;
        } else if (p.getInventory().contains(keyFive)) {
            usedKey = keyFive;
            maxRoll = uniqueRolls * 50;
        }

        p.getInventory().delete(usedKey, 1);


        p.getPacketSender().sendMessage("You open the chest...");


        int roll = RandomUtility.inclusiveRandom(maxRoll);


        //celebration
        if (roll > 0 && roll < 25) {
            p.performAnimation(new Animation(862));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(2443, 3094, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(2444, 3094, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(2443, 3092, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(2444, 3092, 0));
        }

        //low supply
        int lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
        int lootOneQty = 100 + RandomUtility.inclusiveRandom(250);

        //high supply
        int lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
        int lootTwoQty = 10 + RandomUtility.inclusiveRandom(40);

        p.getInventory().add(lootOne, lootOneQty);
        p.getInventory().add(lootTwo, lootTwoQty);

        if (!p.chatFilter && p.personalFilterKeyLoot) {
            Item itemOne = new Item(lootOne, lootOneQty);
            Item itemTwo = new Item(lootTwo, lootTwoQty);
            p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
            p.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
        }

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootTwo)), new Item(lootTwo, lootTwoQty), p.getInventory().getSlot(lootTwo), false, true);
        }


        if (roll == 1) //Gilded Crystal
        {
            p.getInventory().add(21033, 1);
            announceCHAOS(p, new Item(21033));
        } else if (roll == 2) //Soul Crystal
        {
            p.getInventory().add(21031, 1);
            announceCHAOS(p, new Item(21031));
        } else if ((roll == 3))//Blood Crystal
        {
            p.getInventory().add(21032, 1);
            announceCHAOS(p, new Item(21032));
        } else if ((roll == 4) || (roll == 5))//Vanguard helm
        {
            p.getInventory().add(20013, 1);
            announceCHAOS(p, new Item(20013));
        } else if ((roll == 6) || (roll == 7))//Vanguard Top
        {
            p.getInventory().add(20014, 1);
            announceCHAOS(p, new Item(20014));
        } else if ((roll == 8) || (roll == 9))//Vanguard bottoms
        {
            p.getInventory().add(14062, 1);
            announceCHAOS(p, new Item(14062));
        } else if ((roll == 10) || (roll == 11))//Battlemage helm
        {
            p.getInventory().add(20016, 1);
            announceCHAOS(p, new Item(20016));
        } else if ((roll == 12) || (roll == 13))//Battlemage top
        {
            p.getInventory().add(20017, 1);
            announceCHAOS(p, new Item(20017));
        } else if ((roll == 14) || (roll == 15))//Battlemage bottoms
        {
            p.getInventory().add(20018, 1);
            announceCHAOS(p, new Item(20018));
        } else if ((roll == 16) || (roll == 17))//Trickster Helm
        {
            p.getInventory().add(20010, 1);
            announceCHAOS(p, new Item(20010));
        } else if ((roll == 18) || (roll == 19))//Trickster Body
        {
            p.getInventory().add(20011, 1);
            announceCHAOS(p, new Item(20011));
        } else if ((roll == 20) || (roll == 21))//Trickster Legs
        {
            p.getInventory().add(20012, 1);
            announceCHAOS(p, new Item(20012));
        } else if ((roll == 22) || (roll == 23) || (roll == 24))//Brimstone Boots
        {
            p.getInventory().add(222951, 1);
            announceCHAOS(p, new Item(222951));
        }

    }
    public static void strongholdRaidChest(final Player p, final GameObject chest) {

        int key = 223502;

        String raidType = "SHR";

        //Check inventory for raid keys and compare it to current location of the raid chest

        if (!p.getInventory().contains(key)) {
            p.getPacketSender().sendMessage("This chest can only be opened with a " + raidType + " Key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 3) {
            p.getPacketSender().sendMessage("You need at least 3 empty inventory spaces to use this chest.");
            return;
        }

        int maxRoll = 0;
        int uniqueRolls = 27;

        if (p.getInventory().contains(key)) {
            maxRoll = uniqueRolls * 45;
        }

        p.getInventory().delete(key, 1);


        p.getPacketSender().sendMessage("You open the chest...");


        int roll = RandomUtility.inclusiveRandom(maxRoll);

        int[] uniques = {21061, 21063, 21065, 21068, 21069, 21070, 21071, 21072, 21076, 21077, 21078, 21079, 21081, 21083, 763, 764, 765};

        //celebration
        if (roll > 0 && roll < 28) {
            p.performAnimation(new Animation(862));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3082, 3412, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3082, 3412, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3082, 3412, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(3082, 3412, 0));
        }

        //low supply
        int lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
        int lootOneQty = 100 + RandomUtility.inclusiveRandom(250);

        //high supply
        int lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
        int lootTwoQty = 10 + RandomUtility.inclusiveRandom(40);

        p.getInventory().add(lootOne, lootOneQty);
        p.getInventory().add(lootTwo, lootTwoQty);

        if (!p.chatFilter && p.personalFilterKeyLoot) {
            Item itemOne = new Item(lootOne, lootOneQty);
            Item itemTwo = new Item(lootTwo, lootTwoQty);
            p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
            p.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
        }

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootTwo)), new Item(lootTwo, lootTwoQty), p.getInventory().getSlot(lootTwo), false, true);
        }


        if (roll == 1) //Tumeken's Shadow
        {
            p.getInventory().add(21061, 1);
            announceSHR(p, new Item(21061));
        } else if (roll == 2) //Cursed Phalanx
        {
            p.getInventory().add(21065, 1);
            announceSHR(p, new Item(21065));
        } else if ((roll == 3)) //Lightbearer
        {
            p.getInventory().add(21077, 1);
            announceSHR(p, new Item(21077));
        } else if ((roll == 4) || (roll == 5)) //Masori Mask
        {
            p.getInventory().add(21070, 1);
            announceSHR(p, new Item(21070));
        } else if ((roll == 6) || (roll == 7)) //Masori Body
        {
            p.getInventory().add(21071, 1);
            announceSHR(p, new Item(21071));
        } else if ((roll == 8) || (roll == 9)) //Masori Chaps
        {
            p.getInventory().add(21072, 1);
            announceSHR(p, new Item(21072));
        } else if ((roll == 10) || (roll == 11) || (roll == 12)) //Keris Partisan
        {
            p.getInventory().add(21078, 1);
            announceSHR(p, new Item(21078));
        } else if ((roll == 13) || (roll == 14))//Elidinis' Ward
        {
            p.getInventory().add(765, 1);
            announceSHR(p, new Item(765));
        } else if ((roll == 15) || (roll == 16))//Menaphite Ornament Kit
        {
            p.getInventory().add(764, 1);
            announceSHR(p, new Item(764));
        }  else if ((roll == 17) || (roll == 18) || (roll == 27))//Masori Crafting Kit
        {
            p.getInventory().add(21068, 1);
            announceSHR(p, new Item(21068));
        } else if ((roll == 19) || (roll == 20))//Osmumten's Fang
        {
            p.getInventory().add(21063, 1);
            announceSHR(p, new Item(21063));
        } else if ((roll == 21) || (roll == 22))//Eye of the Corrupter
        {
            p.getInventory().add(21081, 1);
            announceSHR(p, new Item(21081));
        } else if ((roll == 23) || (roll == 24))//Breach of the Scarab
        {
            p.getInventory().add(21079, 1);
            announceSHR(p, new Item(21079));
        } else if ((roll == 25) || (roll == 26))//Jewel of the Sun
        {
            p.getInventory().add(21083, 1);
            announceSHR(p, new Item(21083));
        }

    }

    public static void gauntletChest(final Player p, final GameObject chest) {

        int raidKey = 223951;
        String raidType = "Gauntlet";


        //Check inventory for raid keys and compare it to current location of the raid chest

        if (!p.getInventory().contains(raidKey)) {
            p.getPacketSender().sendMessage("This chest can only be opened with a " + raidType + " Raid Key.");
            return;
        }

        if (p.getInventory().getFreeSlots() < 3)
            return;

        p.getInventory().delete(raidKey, 1);


        p.getPacketSender().sendMessage("You open the chest...");


        int roll = RandomUtility.inclusiveRandom(15);


        //celebration
        if (roll > 0 && roll < 2) {
            p.performAnimation(new Animation(862));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1630, 3939, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1630, 3940, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1632, 3939, 0));
            p.getPacketSender().sendGlobalGraphic(new Graphic(190, GraphicHeight.HIGH), new Position(1632, 3940, 0));
        }

        //low supply
        int lootOne = GameSettings.LOWSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.LOWSUPPLYDROPS.length - 1)];
        int lootOneQty = 100 + RandomUtility.inclusiveRandom(250);

        //high supply
        int lootTwo = GameSettings.HIGHSUPPLYDROPS[RandomUtility.inclusiveRandom(GameSettings.HIGHSUPPLYDROPS.length - 1)];
        int lootTwoQty = 10 + RandomUtility.inclusiveRandom(40);

        //crystal shards
        int crystalShards = 223962;
        int crystalQty = 50;

        p.getInventory().add(lootOne, lootOneQty);
        p.getInventory().add(lootTwo, lootTwoQty);
        p.getInventory().add(crystalShards, crystalQty);

        if (!p.chatFilter && p.personalFilterKeyLoot) {
            Item itemOne = new Item(lootOne, lootOneQty);
            Item itemTwo = new Item(lootTwo, lootTwoQty);
            Item itemThree = new Item(crystalShards, crystalQty);
            p.getPacketSender().sendMessage("Your first supply loot is: " + lootOneQty + " " + itemOne.getName());
            p.getPacketSender().sendMessage("Your second supply loot is: " + lootTwoQty + " " + itemTwo.getName());
            p.getPacketSender().sendMessage("You got " + crystalShards + " " + itemThree.getName());
        }

        if (p.looterBanking && p.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootOne)), new Item(lootOne, lootOneQty), p.getInventory().getSlot(lootOne), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, lootTwo)), new Item(lootTwo, lootTwoQty), p.getInventory().getSlot(lootTwo), false, true);
            p.getInventory().switchItem(p.getBank(Bank.getTabForItem(p, crystalShards)), new Item(crystalShards, crystalQty), p.getInventory().getSlot(crystalShards), false, true);
        }


        if (roll == 1) {
            p.getInventory().add(223956, 1);
            announceGauntlet(p, new Item(223956));
        }

    }


    public static void announceCOX(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from Great Olm at " + player.getCoxKC() + " CoX KC!";
        World.sendMessage("drops", message);
        player.getCollectionLog().handleDrop(CustomCollection.COX.getId(), item.getId(), 1);
        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from Great Olm at " + player.getCoxKC() + " CoX KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from Great Olm.");

        if (RandomUtility.inclusiveRandom(50) == 1) {
            int petId = 220851;
            Item pet = new Item(petId);
            String petName = pet.getDefinition().getName();
            message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName;
            player.getPacketSender().sendMessage("You feel something special at your feet...");
            World.sendMessage("drops", message);
            DropLog.submit(player, new DropLogEntry(petId, 1));
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
            player.getCollectionLog().handleDrop(CustomCollection.COX.getId(), 220851, 1);
        }


    }


    public static void announceTOB(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from Verzik Vitur at " + player.getTobKC() + " ToB KC!";
        World.sendMessage("drops", message);

        player.getCollectionLog().handleDrop(CustomCollection.TOB.getId(), item.getId(), 1);

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from Verzik Vitur at " + player.getTobKC() + " ToB KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());

        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from Verzik Vitur.");

        if (RandomUtility.inclusiveRandom(50) == 1) {
            int petId = 222473;
            Item pet = new Item(petId);
            String petName = pet.getDefinition().getName();
            message = "@blu@[PET DROP] " + player.getUsername() + " has just received a @red@" + petName;
            player.getPacketSender().sendMessage("You feel something special at your feet...");
            World.sendMessage("drops", message);
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(message).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            DropLog.submit(player, new DropLogEntry(petId, 1));
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(petId), player.getPosition(), player.getUsername(), false, 150, true, 200));
            player.getCollectionLog().handleDrop(CustomCollection.TOB.getId(), 222473, 1);
        }
    }

    public static void announceGWD(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from GWD Raids at " + player.getGwdRaidKC() + " KC!";
        World.sendMessage("drops", message);

        player.getCollectionLog().handleDrop(CustomCollection.GWD.getId(), item.getId(), 1);

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from GWD Raids at " + player.getGwdRaidKC() + " KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from GWD Raids.");

        if (RandomUtility.inclusiveRandom(50) == 1) {
            player.performAnimation(new Animation(827));
            player.getInventory().add(11987, 1);
            itemName = new Item(11987).getDefinition().getName();
            itemMessage = Misc.anOrA(itemName) + " " + itemName;
            message = "@blu@[RARE DROP] " + player.getUsername()
                    + " has just received @red@" + itemMessage + "@blu@ at " + player.getGwdRaidKC() + " KC at GWD Raids!";
            World.sendMessage("drops", message);
            DropLog.submit(player, new DropLogEntry(11987, 1));
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from the GWD Raids Chest!");
            player.getCollectionLog().handleDrop(CustomCollection.GWD.getId(), 11987, 1);

            discordMessage = player.getUsername() + " has just received " + itemMessage + " from GWD Raids at " + player.getGwdRaidKC() + " KC!";
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        }
    }


    public static void announceCHAOS(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from Chaos Raids at " + player.getchaosKC() + " KC!";
        World.sendMessage("drops", message);

        player.getCollectionLog().handleDrop(CustomCollection.CHAOS.getId(), item.getId(), 1);

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from Chaos Raids at " + player.getchaosKC() + " KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from Chaos Raids.");

        if (RandomUtility.inclusiveRandom(50) == 1) {
            player.performAnimation(new Animation(827));
            player.getInventory().add(207582, 1);
            itemName = new Item(207582).getDefinition().getName();
            itemMessage = Misc.anOrA(itemName) + " " + itemName;
            message = "@blu@[RARE DROP] " + player.getUsername()
                    + " has just received @red@" + itemMessage + "@blu@ at " + player.getchaosKC() + " KC at Chaos Raids!";
            World.sendMessage("drops", message);
            DropLog.submit(player, new DropLogEntry(207582, 1));
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from Chaos Raids!");
            player.getCollectionLog().handleDrop(CustomCollection.CHAOS.getId(), 207582, 1);

            discordMessage = player.getUsername() + " has just received " + itemMessage + " from Chaos Raids at " + player.getchaosKC() + " KC!";
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        }
    }


    public static void announceSHR(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from SHR at " + player.getshrKC() + " KC!";
        World.sendMessage("drops", message);

        player.getCollectionLog().handleDrop(CustomCollection.SHR.getId(), item.getId(), 1);

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from SHR at " + player.getshrKC() + " KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from SHR.");

        /*if (RandomUtility.inclusiveRandom(50) == 1) {
            player.performAnimation(new Animation(827));
            player.getInventory().add(207582, 1);
            itemName = new Item(207582).getDefinition().getName();
            itemMessage = Misc.anOrA(itemName) + " " + itemName;
            message = "@blu@[RARE DROP] " + player.getUsername()
                    + " has just received @red@" + itemMessage + "@blu@ at " + player.getshrKC() + " KC at SHR!";
            World.sendMessage("drops", message);
            DropLog.submit(player, new DropLogEntry(207582, 1));
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from SHR!");
            player.getCollectionLog().handleDrop(CustomCollection.SHR.getId(), 207582, 1);

            discordMessage = player.getUsername() + " has just received " + itemMessage + " from SHR at " + player.getshrKC() + " KC!";
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        }*/
    }

    public static void announceGauntlet(Player player, Item item) {
        String itemName = item.getDefinition().getName();
        String itemMessage = Misc.anOrA(itemName) + " " + itemName;
        String message = player.getUsername() + " has just received <col=ad66ff>" + itemMessage
                + "</col> from the Gauntlet at " + player.GauntletKC + " KC!";
        World.sendMessage("drops", message);

        player.getCollectionLog().handleDrop(CustomCollection.SHR.getId(), item.getId(), 1);

        String discordMessage = player.getUsername() + " has just received " + itemMessage + " from the Gauntlet at " + player.GauntletKC + " KC!";
        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from the Gauntlet.");

        if (RandomUtility.inclusiveRandom(50) == 1) {
            player.performAnimation(new Animation(827));
            player.getInventory().add(223757, 1);
            itemName = new Item(223757).getDefinition().getName();
            itemMessage = Misc.anOrA(itemName) + " " + itemName;
            message = "@blu@[RARE DROP] " + player.getUsername()
                    + " has just received @red@" + itemMessage + "@blu@ at " + player.GauntletKC + " KC at the Gauntlet!";
            World.sendMessage("drops", message);
            DropLog.submit(player, new DropLogEntry(223757, 1));
            PlayerLogs.log(player.getUsername(), player.getUsername() + " received " + itemMessage + " from the Gauntlet chest");
            player.getCollectionLog().handleDrop(CustomCollection.SHR.getId(), 223757, 1);

            discordMessage = player.getUsername() + " has just received " + itemMessage + " from the Gauntlet at " + player.GauntletKC + " KC!";
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
        }
    }


}
