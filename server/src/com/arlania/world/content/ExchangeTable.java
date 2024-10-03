package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Animation;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.input.impl.EnterAmountToExchange;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.File;

public class ExchangeTable {

    public static final File DIRECTORY = new File(System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Pets");

    public static void tryExchange(final Player p, final Item item) {

        if (p.getInventory().contains(item.getId()) && p.getInventory().getAmount(item.getId()) == 1) {
            p.setSelectedSkillingItem(item.getId());
            handleExchange(p, 1);
        } else if (p.getInventory().contains(item.getId()) && p.getInventory().getAmount(item.getId()) > 1) {
            p.setSelectedSkillingItem(item.getId());
            p.setInputHandling(new EnterAmountToExchange());
            p.getPacketSender().sendString(2799, item.getName()).sendInterfaceModel(1746, item.getId(), 150).sendChatboxInterface(4429);
            p.getPacketSender().sendString(2800, "How many would you like to exchange?");
        } else {
            p.getPacketSender().sendMessage("@red@Something went wrong with this exchange. Let Pat know.");
        }

    }


    public static void handleExchange(final Player p, int amount) {

        p.getSkillManager().stopSkilling();
        p.getPacketSender().sendInterfaceRemoval();

        int itemid = p.getSelectedSkillingItem();
        int noteditemid = -1;

        Item item = new Item(itemid, amount);

        //Pet Beaver fix
        if (itemid == 16714)
            itemid = 213322;

        int[] MasterUniques = GameSettings.MASTERUNIQUES;

        int[] LegendaryUniques = GameSettings.LEGENDARYUNIQUES;

        int[] HighUniques = GameSettings.HIGHUNIQUES;

        int[] MediumUniques = GameSettings.MEDIUMUNIQUES;

        int[] LowUniques = GameSettings.LOWUNIQUES;

        int[] TzhaarDrops = GameSettings.TZHAARDROPS;

        int[] firecape = {6570, 221295};

        int[] Untradeables = GameSettings.UNTRADEABLE_ITEMS;

        int[] pets = GameSettings.PETS;

        int[] SoulEquip = GameSettings.SOUL_EQUIP;
        int[] BloodEquip = GameSettings.BLOOD_EQUIP;
        int[] GildedEquip = GameSettings.GILDED_EQUIP;
        int[] PinkEquip = GameSettings.PINK_EQUIP;
        int[] TaintedEquip = GameSettings.TAINTED_EQUIP;


        if (!p.getClickDelay().elapsed(2000))
            return;


        if (item.getDefinition().isNoted()) {
            noteditemid = itemid;
            itemid -= 1;
        }

        if (!p.getInventory().contains(itemid)) {
            p.getPacketSender().sendMessage("@red@Your inventory does not contain this item.");
            return;
        }

        if (p.getInventory().getAmount(itemid) < amount) {
            amount = p.getInventory().getAmount(itemid);
        }

        switch (itemid) {
            case 212013:
            case 212014:
            case 212015:
            case 212016:
            case 221345:
            case 219988:
            case 220704:
            case 220706:
            case 220708:
            case 220710:
            case 220712:
                p.getInventory().delete(itemid, 1);
                p.getPacketSender().sendMessage("You receive 50 PaePoints for your item!");
                p.setPaePoints(p.getPaePoints() + 50);
                break;
        }

        if(item.getName().toLowerCase().contains("effigy")) {

            int effigyQty = p.getInventory().getAmount(18778) + p.getInventory().getAmount(18779) + p.getInventory().getAmount(18780) + p.getInventory().getAmount(18781);

            p.getInventory().delete(18778, p.getInventory().getAmount(18778));
            p.getInventory().delete(18779, p.getInventory().getAmount(18779));
            p.getInventory().delete(18780, p.getInventory().getAmount(18780));
            p.getInventory().delete(18781, p.getInventory().getAmount(18781));

            p.getInventory().add(18782, effigyQty);

            String eventLog = p.getUsername() + " exchanged " + effigyQty + " Ancient Effigies for Dragonkin Lamps!";

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(eventLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MISC_LOGS_CH).get());


        }

        //Equipment Upgrade token swap

        //4035 master
        //4034 legendary
        //4033 high
        //4036 medium
        //4037 low

        if (itemid == 4035) {
            p.getInventory().delete(itemid, 1);
            p.getInventory().add(4034, 2);
        }
        if (itemid == 4034) {
            p.getInventory().delete(itemid, 1);
            p.getInventory().add(4033, 2);
        }
        if (itemid == 4033) {
            p.getInventory().delete(itemid, 1);
            p.getInventory().add(4036, 2);
        }
        if (itemid == 4036) {
            p.getInventory().delete(itemid, 1);
            p.getInventory().add(4037, 2);
        }
        if (itemid == 4037) {
            p.getInventory().delete(itemid, 1);
            p.getInventory().add(2677, 1);
        }


        for (int i = 0; i < SoulEquip.length; i++) {
            if (SoulEquip[i] == itemid) {
                p.performAnimation(new Animation(827));
                p.getInventory().delete(itemid, 1);
                p.getInventory().add(21031, 1);
            }
        }

        for (int i = 0; i < BloodEquip.length; i++) {
            if (BloodEquip[i] == itemid) {
                p.performAnimation(new Animation(827));
                p.getInventory().delete(itemid, 1);
                p.getInventory().add(21032, 1);
            }
        }

        for (int i = 0; i < GildedEquip.length; i++) {
            if (GildedEquip[i] == itemid) {
                p.performAnimation(new Animation(827));
                p.getInventory().delete(itemid, 1);
                p.getInventory().add(21033, 1);
            }
        }

        for (int i = 0; i < PinkEquip.length; i++) {
            if (PinkEquip[i] == itemid) {
                p.performAnimation(new Animation(827));
                p.getInventory().delete(itemid, 1);
                p.getInventory().add(21034, 1);
            }
        }

        for (int i = 0; i < TaintedEquip.length; i++) {
            if (TaintedEquip[i] == itemid) {
                p.performAnimation(new Animation(827));
                p.getInventory().delete(itemid, 1);
                p.getInventory().add(2050, 1);
            }
        }


        for (int i = 0; i < pets.length; i++) {
            if (pets[i] == itemid) {
                p.performAnimation(new Animation(827));
                PetStorage.savePets(p, itemid);
            }
        }


        for (int i = 0; i < firecape.length; i++) {
            if (firecape[i] == itemid) {

                for (int e = 0; e < amount; e++) {
                    p.performAnimation(new Animation(827));
                    p.getInventory().delete(itemid, 1);

                    int loot = 500;

                    if (itemid == 6570)
                        loot = RandomUtility.inclusiveRandom(500);
                    else if (itemid == 221295)
                        loot = RandomUtility.inclusiveRandom(100);
                    else
                        break;

                    if (loot == 1) //jad pet
                    {
                        p.getInventory().add(11978, 1);
                        Item loots = new Item(11978, 1);
                        String itemName = loots.getDefinition().getName();
                        String message = "@or2@[PET] " + p.getUsername() + " has just received @red@" + itemName + "@or2@ from the Exchange Table!";
                        World.sendMessage("drops", message);
                    } else if (loot == 302 || loot == 303 || loot == 304 || loot == 305) //Infernal cape
                    {
                        p.getInventory().add(221295, 1);
                        Item loots = new Item(221295, 1);
                        String itemName = loots.getDefinition().getName();
                        String message = "@or2@[INFERNAL] " + p.getUsername() + " has just received @red@" + itemName + "@or2@ from the Exchange Table!";
                        World.sendMessage("drops", message);
                    } else //tokkul
                    {
                        p.getInventory().add(6529, 2000 + loot);
                    }

                }
            }

            if(p.boxScape && isUnique(itemid)) {
                boxScape(p, itemid);
                return;
            } else if(!p.boxScape && isUnique(itemid)) {
                p.getPacketSender().sendMessage("You don't have BoxScape activated. You can only activate this at account creation.");
                return;
            }
        }

        if (itemid == 11137) {
            if (p.getInventory().getAmount(11137) >= 30) {
                p.getInventory().delete(11137, 30);
                p.getInventory().add(18782, 1);
                p.getPacketSender().sendMessage("@blu@You exchange 30 normal lamps for 1 Dragonkin lamp.");
            }
        }

        for (int i = 0; i < Untradeables.length; i++) {

            if (Untradeables[i] == itemid) {

                if(p.getGameMode() == GameMode.SEASONAL_IRONMAN && p.Shoplifter) {
                    p.getPacketSender().sendMessage("You can't exchange untradeables with Shoplifter.");
                    return;
                }

                int itemValue = 0;
                Object[] obj = ShopManager.getCustomShopData(92, itemid);
                if (obj == null)
                    return;
                itemValue = (int) obj[0];
                if (itemValue <= 0)
                    return;
                itemValue = (int) (itemValue * 0.5);

                p.performAnimation(new Animation(827));
                p.getInventory().delete(itemid, 1);
                p.setPaePoints(p.getPaePoints() + itemValue);
                p.getPacketSender().sendMessage("You've received " + itemValue + " PaePoints from the exchange table.");
                PlayerPanel.refreshPanel(p);
                break;
            }
        }

        for (int i = 0; i < TzhaarDrops.length; i++) {
            if (TzhaarDrops[i] == itemid) {
                int itemValue = 0;
                Object[] obj = ShopManager.getCustomShopData(43, itemid);

                if (itemid == 6571 || itemid == 6572) //can't sell onyx for tokkul
                    return;
                if (obj == null)
                    return;
                itemValue = (int) obj[0];
                if (itemValue <= 0)
                    return;
                itemValue = (int) (itemValue * 0.5);

                p.performAnimation(new Animation(827));
                p.getInventory().delete(itemid, 1);
                p.getInventory().add(6529, itemValue);
                p.getPacketSender().sendMessage("You've received " + itemValue + " Tokkul from the exchange table.");
                PlayerPanel.refreshPanel(p);
                break;
            }
        }

        if (itemid == 223804) {
            p.performAnimation(new Animation(827));
            p.getInventory().delete(itemid, 1);
            p.getInventory().add(4562, 1);
        }

        Item exchangedItem = new Item(itemid);

        String exchangeLog = "[" + Misc.getCurrentServerTime() + "] " + p.getUsername() + " Exchanged: " + exchangedItem.getDefinition().getName();
        //PlayerLogs.log("Exchange", exchangeLog);

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(exchangeLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_EXCHANGE_CH).get());

    }


    public static boolean isExchangeable(int itemId) {
        Item item = new Item(itemId);

        if (item.getDefinition().isNoted())
            itemId -= 1;


        int[] MasterUniques = GameSettings.MASTERUNIQUES;

        int[] LegendaryUniques = GameSettings.LEGENDARYUNIQUES;

        int[] HighUniques = GameSettings.HIGHUNIQUES;

        int[] MediumUniques = GameSettings.MEDIUMUNIQUES;

        int[] LowUniques = GameSettings.LOWUNIQUES;

        int[] TwoMysteryBoxes = GameSettings.MEDIUM_MYSTERY_EXCHANGE;

        int[] OneMysteryBox = GameSettings.SMALL_MYSTERY_EXCHANGE;

        int[] TwoCrystalKeys = GameSettings.TWO_KEY_UNIQUES;

        int[] FourRareCandy = GameSettings.FOUR_RARE_CANDY;

        int[] SixRareCandy = GameSettings.SIX_RARE_CANDY;


        for (int i = 0; i < SixRareCandy.length; i++) {
            if (SixRareCandy[i] == itemId)
                return true;
        }
        for (int i = 0; i < FourRareCandy.length; i++) {
            if (FourRareCandy[i] == itemId)
                return true;
        }
        for (int i = 0; i < TwoCrystalKeys.length; i++) {
            if (TwoCrystalKeys[i] == itemId)
                return true;
        }
        for (int i = 0; i < TwoMysteryBoxes.length; i++) {
            if (TwoMysteryBoxes[i] == itemId)
                return true;
        }
        for (int i = 0; i < OneMysteryBox.length; i++) {
            if (OneMysteryBox[i] == itemId)
                return true;
        }
        for (int i = 0; i < LowUniques.length; i++) {
            if (LowUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < MediumUniques.length; i++) {
            if (MediumUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < HighUniques.length; i++) {
            if (HighUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < LegendaryUniques.length; i++) {
            if (LegendaryUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < MasterUniques.length; i++) {
            if (MasterUniques[i] == itemId)
                return true;
        }

        return false;
    }

    public static boolean isUnique(int itemId) {
        Item item = new Item(itemId);

        if (item.getDefinition().isNoted())
            itemId -= 1;


        int[] CustomUniques = GameSettings.CUSTOMUNIQUES;

        int[] MasterUniques = GameSettings.MASTERUNIQUES;

        int[] LegendaryUniques = GameSettings.LEGENDARYUNIQUES;

        int[] HighUniques = GameSettings.HIGHUNIQUES;

        int[] MediumUniques = GameSettings.MEDIUMUNIQUES;

        int[] LowUniques = GameSettings.LOWUNIQUES;


        for (int i = 0; i < LowUniques.length; i++) {
            if (LowUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < MediumUniques.length; i++) {
            if (MediumUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < HighUniques.length; i++) {
            if (HighUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < LegendaryUniques.length; i++) {
            if (LegendaryUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < MasterUniques.length; i++) {
            if (MasterUniques[i] == itemId)
                return true;
        }
        for (int i = 0; i < CustomUniques.length; i++) {
            if (CustomUniques[i] == itemId)
                return true;
        }

        return false;
    }

    public static void boxScape(Player player, int itemId) {

        int value = 0;

        int[] CustomUniques = GameSettings.CUSTOMUNIQUES;

        int[] MasterUniques = GameSettings.MASTERUNIQUES;

        int[] LegendaryUniques = GameSettings.LEGENDARYUNIQUES;

        int[] HighUniques = GameSettings.HIGHUNIQUES;

        int[] MediumUniques = GameSettings.MEDIUMUNIQUES;

        int[] LowUniques = GameSettings.LOWUNIQUES;

        for (int i = 0; i < LowUniques.length; i++) {
            if (LowUniques[i] == itemId)
                value = Marketplace.getCurrentPrice(itemId, Marketplace.ItemType.LOW_UNIQUES);
        }
        for (int i = 0; i < MediumUniques.length; i++) {
            if (MediumUniques[i] == itemId)
                value = Marketplace.getCurrentPrice(itemId, Marketplace.ItemType.MEDIUM_UNIQUES);
        }
        for (int i = 0; i < HighUniques.length; i++) {
            if (HighUniques[i] == itemId)
                value = Marketplace.getCurrentPrice(itemId, Marketplace.ItemType.HIGH_UNIQUES);
        }
        for (int i = 0; i < LegendaryUniques.length; i++) {
            if (LegendaryUniques[i] == itemId)
                value = Marketplace.getCurrentPrice(itemId, Marketplace.ItemType.LEGENDARY_UNIQUES);
        }
        for (int i = 0; i < MasterUniques.length; i++) {
            if (MasterUniques[i] == itemId)
                value = Marketplace.getCurrentPrice(itemId, Marketplace.ItemType.MASTER_UNIQUES);
        }
        for (int i = 0; i < CustomUniques.length; i++) {
            if (CustomUniques[i] == itemId)
                value = Marketplace.getCurrentPrice(itemId, Marketplace.ItemType.MASTER_UNIQUES);
        }

        if (value < 1) {
            player.getPacketSender().sendMessage("This item can't be exchanged.");
            return;
        }

        int sBoxValue = 250000000 * 4;
        int eBoxValue = 40000000 * 4;
        int mBoxValue = 5000000 * 4;

        player.getInventory().delete(itemId, 1);
        player.getPacketSender().sendMessage("You attempt to sacrifice a unique on the Exchange table....");

        //Supreme Mbox chance
        if (RandomUtility.inclusiveRandom(0, sBoxValue) < value) {
            player.getInventory().add(603, 1);
            player.getPacketSender().sendMessage("You receive a Supreme Mystery Box!!");
        }

        //Elite Mbox chance
        else if (RandomUtility.inclusiveRandom(0, eBoxValue) < value) {
            player.getInventory().add(15501, 1);
            player.getPacketSender().sendMessage("You receive a Elite Mystery Box!!");
        }

        //Regular Mbox chance
        else if (RandomUtility.inclusiveRandom(0, mBoxValue) < value) {
            player.getInventory().add(6199, 1);
            player.getPacketSender().sendMessage("You receive a Mystery Box!!");
        }

        else {
            player.getInventory().add(995, value/10);
            player.getPacketSender().sendMessage("You receive some coins...");
        }

    }

}
