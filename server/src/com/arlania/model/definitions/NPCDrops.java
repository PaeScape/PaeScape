package com.arlania.model.definitions;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.util.JsonLoader;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.ClueScrolls;
import com.arlania.world.content.DropLog;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.MoneyPouch;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.clog.CollectionLog;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.holiday.HolidayHandler;
import com.arlania.world.content.skill.impl.slayer.SlayerMobs;
import com.arlania.world.content.skill.impl.slayer.SuperiorSlayer;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Controls the npc drops
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>, Gabbe &
 * Samy
 */
public class NPCDrops {


    static private final Set<Integer> BOSSES = new HashSet<>(Arrays.asList(2882, 2881, 2883, 50, 1999, 6203, 6222, 6247, 6260,
            8133, 13447, 5886, 8349, 7286, 2042, 2043, 2044, 1158,
            1159, 4540, 1580, 3334, 3340, 499, 7287, 2745, 21554,
            22359, 22360, 22388, 22340, 22374, 23425,
            2000, 2001, 2006, 2009, 3200, 3334, 8349, 22061));

    static private final int[] herbs = {199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 2485, 2486,
            3049, 3050, 3051, 3052};

    /**
     * The map containing all the npc drops.
     */
    private static final Map<Integer, NPCDrops> dropControllers = new HashMap<Integer, NPCDrops>();
    public static ArrayList<NPCDrops> dropDump = new ArrayList<>();
    /**
     * The id's of the NPC's that "owns" this class.
     */
    private int[] npcIds;
    /**
     * All the drops that belongs to this class.
     */
    private NpcDropItem[] drops;

    public static JsonLoader parseDrops() {
        dropDump.clear();
        dropControllers.clear();

        ItemDropAnnouncer.init();

        return new JsonLoader() {

            @Override
            public void load(JsonObject reader, Gson builder) {
                int[] npcIds = builder.fromJson(reader.get("npcIds"),
                        int[].class);
                NpcDropItem[] drops = builder.fromJson(reader.get("drops"),
                        NpcDropItem[].class);

                NPCDrops d = new NPCDrops();
                d.npcIds = npcIds;
                d.drops = drops;
                for (int id : npcIds) {
                    dropControllers.put(id, d);
//					System.out.println("put: "+id+" "+d);
                }
                dropDump.add(d);
            }

            @Override
            public String filePath() {
                return "./data/def/json/drops.json";
            }
        };
    }

    /**
     * Gets the NPC drop controller by an id.
     *
     * @return The NPC drops associated with this id.
     */
    public static NPCDrops forId(int id) {
        return dropControllers.get(id);
    }

    public static Map<Integer, NPCDrops> getDrops() {
        return dropControllers;
    }

    public static boolean CollectionDropChance(DropChance chance) {
        return chance == DropChance.RARE || chance == DropChance.VERY_RARE || chance == DropChance.SUPER_RARE || chance == DropChance.ULTRA_RARE || chance == DropChance.LEGENDARY || chance == DropChance.LEGENDARY2 || chance == DropChance.LEGENDARY3;
    }

    /**
     * Drops items for a player after killing an npc. A player can max receive
     * one item per drop chance.
     *
     * @param p   Player to receive drop.
     * @param npc NPC to receive drop FROM.
     */


    public static void dropItems(Player p, NPC npc, Position pos, boolean adventurerBoost) {

        int loot = 995; //default is a coin

        final boolean goGlobal = p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4;
        Position npcPOS = npc.getPosition().copy();
        Position playerPOS = p.getPosition().copy();

        int npcLevel = npc.getDefinition().getCombatLevel();

        //Coin drop (before loot bonuses are added)

        if (p.Wealthy == 1 && (RandomUtility.inclusiveRandom(100) < p.completedLogs * 2)) {
            p.getInventory().add(995, npcLevel);

            if (p.getLocation() != Location.WILDERNESS)
                MoneyPouch.depositMoney(p, npcLevel);

            p.getPacketSender().sendString(8135, "" + p.getMoneyInPouch());
        }

        //Holiday Event Handler
        HolidayHandler.handleHolidayLoot(p, npc);

        //Subscriptions
        if (p.getSubscription().getSubscriptionLevel() > 0 && BOSSES.contains(npc.getId())) {

            int rand = 400;

            if (p.getGameMode() == GameMode.SEASONAL_IRONMAN)
                rand *= (1 - p.getWealthBonus(p, npc.getId(), false, 0));

            if (RandomUtility.inclusiveRandom(rand) == 1) {

                if (p.getInventory().contains(p.getSubscription().getBoxType()))
                    p.getInventory().add(p.getSubscription().getBoxType(), 1);
                else if (p.getInventory().getFreeSlots() > 0)
                    p.getInventory().add(p.getSubscription().getBoxType(), 1);
                else
                    GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(p.getSubscription().getBoxType(), 1), p.getPosition(), p.getUsername(), false, 150, false, 200));

                String message = "@blu@[SUBSCRIPTION BOX]";
                p.sendMessage(message);

                String npcName = npc.getDefinition().getName();
                String message2 = "@blu@[SUB BOX] " + p.getUsername() + "@bla@ has just received a @blu@Subscription Box@bla@ from " + npcName;

                if (p.getGameMode() != GameMode.SEASONAL_IRONMAN) {
                    World.sendMessage("drops", message2);
                }

                String discordMessage = "[" + p.getSubscription() + "]" + p.getUsername()
                        + " just received a Subscription Box from " + npc.getDefinition().getName() + " at " + p.getCollectionLog().getKills(npc.getId()) + " KC!";

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.SUBSCRIPTION_BOX_DROPS_CH).get());

            }

        }

        //Event Boxes
        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.EVENT_BOX) || (p.eventBoxEvent && p.personalEvent)) {
            if (RandomUtility.inclusiveRandom(125) == 1) {
                String npcName = npc.getDefinition().getName();
                String message = "@blu@[EVENT] " + p.getUsername() + " has just received an @red@EVENT BOX@blu@ from " + npcName;
                World.sendMessage("drops", message);

                if (p.getInventory().contains(221140) || p.getEquipment().contains(221140) || (p.getEquipment().contains(21077) && p.lightbearerOptions[4]))
                    p.getInventory().add(2685, 1);
                else
                    GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(2685), pos, p.getUsername(), false, 150, true, 200));
            }
        }

        //Tertiary Drops
        checkTertiaryDrops(p, npc);

        //imbued heart
        for (int i = 0; i < p.getSlayer().getSlayerTask().getNpcId().length; i++) {
            if (npc.getId() == p.getSlayer().getSlayerTask().getNpcId()[i]) {
                if (RandomUtility.inclusiveRandom(5000) == 1) {
                    String npcName = npc.getDefinition().getName();
                    String message = "@blu@[RARE DROP] " + p.getUsername() + " has just received an @red@Imbued Heart@blu@ from " + npcName;
                    World.sendMessage("drops", message);
                    PlayerLogs.log(p.getUsername(), p.getUsername() + " received an Imbued Heart from " + npcName);
                    DropLog.submit(p, new DropLogEntry(20560, 1));

                    if (p.getEquipment().contains(221140) || (p.getEquipment().contains(21077) && p.lightbearerOptions[4])) {
                        p.getInventory().add(20560, 1);
                    } else {
                        drop(p, new Item(20560, 1), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, false, true);
                    }
                }

            }
        }

        int npcId = npc.getId();

        //Superior Slayer
        for (SuperiorSlayer ss : SuperiorSlayer.values()) {
            for (int superiorId : ss.getSuperiorId()) {
                if (superiorId == npcId) {
                    npcId = 22;
                    break;
                }
            }
        }

        NPCDrops drops = NPCDrops.forId(npcId);

        if (p.checkAchievementAbilities(p, "sweeten") && RandomUtility.inclusiveRandom(1000) == 1 && BOSSES.contains(npcId)) {
            drop(p, new Item(4562, 1), npc, npcPOS, goGlobal, false, true);
            String message = "@blu@[Rare Candy Drop]";
            p.sendMessage(message);
        }
        if (p.checkAchievementAbilities(p, "entertainer") && RandomUtility.inclusiveRandom(5000) == 1 && BOSSES.contains(npcId)) {
            drop(p, new Item(6769, 1), npc, npcPOS, goGlobal, false, true);
            String message = "@blu@[Event Pass Drop]";
            p.sendMessage(message);
        }
        if (p.checkAchievementAbilities(p, "combatant") && RandomUtility.inclusiveRandom(5000) == 1 && BOSSES.contains(npcId)) {
            drop(p, new Item(6758, 1), npc, npcPOS, goGlobal, false, true);
            String message = "@blu@[Battle Pass Drop]";
            p.sendMessage(message);
        }

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.SWEET_TOOTH) && RandomUtility.inclusiveRandom(50) == 1 && BOSSES.contains(npcId))
            drop(p, new Item(4562, 1), npc, npcPOS, goGlobal, false, true);

        if (drops == null)
            return;

        if (npc.getId() == 2044 || npc.getId() == 2043 || npc.getId() == 2042) {
            npcPOS = p.getPosition().copy();
        }

        boolean[] dropsReceived = new boolean[16];


        for (int i = 0; i < drops.getDropList().length; i++) {
            if (drops.getDropList()[i].getItem().getId() <= 0 || drops.getDropList()[i].getItem().getId() > ItemDefinition.getMaxAmountOfItems() || drops.getDropList()[i].getItem().getAmount() <= 0) {
                continue;
            } else if (p.getLocation() == Location.INSTANCED_SLAYER && SuperiorSlayer.isSlayerKey(drops.getDropList()[i].getItem().getId())) {
                continue;
            }

            DropChance dropChance = drops.getDropList()[i].getChance();

            boolean isClue = drops.getDropList()[i].getItem().getName().contains("lue scroll");

            if (dropChance == DropChance.ALWAYS) {
                drop(p, drops.getDropList()[i].getItem(), npc, npcPOS, goGlobal, false, false);
            } else {
                if (shouldDrop(dropsReceived, dropChance, isClue, npc.getId(), p, adventurerBoost)) {
                    drop(p, drops.getDropList()[i].getItem(), npc, npcPOS, goGlobal, false, true);
                    dropsReceived[dropChance.ordinal()] = true;
                    if (CollectionDropChance(dropChance))
                        p.getCollectionLog().handleDrop(npcId, drops.getDropList()[i].getItem().getId(), drops.getDropList()[i].getItem().getAmount());
                }
            }
        }
    }

    public static boolean shouldDrop(boolean[] b, DropChance chance, boolean isClue, int npcId, Player player, boolean adventurerBoost) {
        int random = chance.getRandom();//this means the drop rate which is here


        if (isClue && player.Detective == 1)
            random = (random * (100 - (player.completedLogs / 4))) / 100;

        for (int i = 0; i < player.getSlayer().getSlayerTask().getNpcId().length; i++) {
            if (npcId == player.getSlayer().getSlayerTask().getNpcId()[i])
                if (player.Unnatural > 1) {
                    random -= (random / 20);

                    final boolean goGlobal = player.getPosition().getZ() >= 0 && player.getPosition().getZ() < 4;
                    int itemId = -1;

                    if (RandomUtility.inclusiveRandom(500) == 1)
                        itemId = ClueScrolls.hardClue;
                    if (RandomUtility.inclusiveRandom(250) == 1)
                        itemId = ClueScrolls.mediumClue;
                    if (RandomUtility.inclusiveRandom(100) == 1)
                        itemId = ClueScrolls.easyClue;

                    if (itemId != -1)
                        drop(player, new Item(itemId, 1), new NPC(npcId, player.getPosition().copy()), player.getPosition().copy(), goGlobal, false, true);
                }
        }

        final double boost = player.getWealthBonus(player, npcId, adventurerBoost, 0);

        random -= random * boost;

        return !b[chance.ordinal()] && RandomUtility.inclusiveRandom(random) == 0;
    }

    public static boolean shouldRecieveDrop(boolean[] b, DropChance chance, boolean isClue) {
        int random = chance.getRandom();

        return !b[chance.ordinal()] && RandomUtility.inclusiveRandom(random) == 1;
    }

    public static void drop(Player player, Item item, NPC npc, Position pos, boolean goGlobal, boolean alchable, boolean rof) {

        //SEASONAL TODO - Make sure seasonal drops can only be picked up by that seasonal account. They do not need to appear globally

        int amount = item.getAmount();

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_LOOT) || (player.doubleLoot && player.personalEvent))
            amount *= 2;

        //sub boxes
        if (player.getSubscription().getSubscriptionLevel() > 0 && item.getDefinition().getName().toLowerCase().contains("box")) {
            player.getInventory().add(item.getId(), amount);
            return;
        }

        if (player.getStaffRights().getStaffRank() > 2 || player.getInventory().contains(221140) || player.getEquipment().contains(221140)) {


            for (int i = 0; i < GameSettings.ALCHABLES.length - 1; i++) {
                if (item.getId() == GameSettings.ALCHABLES[i]) {
                    player.checkLooterSettings(player, item);
                    return;
                }
            }

            for (int i = 0; i < GameSettings.AUTO_ALCHS.length - 1; i++) {
                if (item.getId() == GameSettings.AUTO_ALCHS[i]) {
                    player.checkLooterSettings(player, item);
                    return;
                }
            }

            //autosupply
            if (item.getName().toLowerCase().contains("leather") ||
                    item.getName().toLowerCase().contains("bar") ||
                    item.getName().toLowerCase().contains("grimy") ||
                    item.getName().toLowerCase().contains("log") ||
                    item.getName().toLowerCase().contains("ore") ||
                    item.getName().toLowerCase().contains("essence") ||
                    item.getName().toLowerCase().contains("uncut") ||
                    item.getName().toLowerCase().contains("raw") ||
                    item.getName().toLowerCase().contains("runes") ||
                    item.getName().toLowerCase().contains("arrow") ||
                    item.getName().toLowerCase().contains("charm") ||
                    item.getName().toLowerCase().contains("bone") ||
                    item.getName().toLowerCase().contains("key")) {
                player.checkLooterSettings(player, item);
                return;
            }

        } else if (item.getName().toLowerCase().contains("grimy") && (player.getInventory().contains(19675) || player.getInventory().contains(19675))) {
            player.checkLooterSettings(player, item);
            return;
        } else if (item.getName().toLowerCase().contains("bone") && (player.getInventory().contains(18337) || player.getInventory().contains(18337))) {
            player.checkLooterSettings(player, item);
            return;
        } else if (item.getName().toLowerCase().contains("charm") && (player.getInventory().contains(6500) || player.getInventory().contains(6500))) {
            player.checkLooterSettings(player, item);
            return;
        }


        if (item.getDefinition().getName().contains("lue scrol") && GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_CLUES))
            amount++;

        int itemId = item.getId();


        //SLayer C-Log

        for (int i = 0; i < SlayerMobs.slayerUniques.length; i++)
            if (SlayerMobs.slayerUniques[i] == itemId)
                player.getCollectionLog().handleDrop(CollectionLog.CustomCollection.Slayer.getId(), itemId, amount);


        Player toGive = player;

        boolean ccAnnounce = false;
        if (Location.inMulti(player)) {
            if (player.getCurrentClanChat() != null && player.getCurrentClanChat().getLootShare()) {
                CopyOnWriteArrayList<Player> playerList = new CopyOnWriteArrayList<Player>();
                for (Player member : player.getCurrentClanChat().getMembers()) {
                    if (member != null) {
                        if (member.getPosition().isWithinDistance(player.getPosition())) {
                            playerList.add(member);
                        }
                    }
                }
                if (playerList.size() > 0) {
                    toGive = playerList.get(RandomUtility.inclusiveRandom(playerList.size() - 1));
                    if (toGive == null || toGive.getCurrentClanChat() == null || toGive.getCurrentClanChat() != player.getCurrentClanChat()) {
                        toGive = player;
                    }
                    ccAnnounce = true;
                }
            }
        }

        if (ItemDropAnnouncer.announce(itemId)) {
            String itemName = item.getDefinition().getName();
            String itemMessage = Misc.anOrA(itemName) + " " + itemName;
            String npcName = Misc.formatText(npc.getDefinition().getName());


            switch (itemId) {
                case 14484:
                    itemMessage = "a pair of Dragon Claws";
                    break;
                case 20000:
                case 20001:
                case 20002:
                    itemMessage = itemName;
                    break;
            }
            switch (npc.getId()) {
                case 81:
                    npcName = "a Cow";
                    break;
                case 50:
                case 3200:
                case 8133:
                case 4540:
                case 1160:
                case 8549:
                    npcName = "The " + npcName;
                    break;
                case 51:
                case 54:
                case 5363:
                case 8349:
                case 1592:
                case 1591:
                case 1590:
                case 1615:
                case 9463:
                case 9465:
                case 9467:
                case 1382:
                case 13659:
                case 11235:
                    npcName = Misc.anOrA(npcName) + " " + npcName;
                    break;
            }

            int kills = player.getCollectionLog().getKills(npc.getId());

            if (player.getLocation() == Location.WILDERNESS) {

                String discordMessage = "[DROP] " + toGive.getUsername()
                        + " just received " + itemMessage + " from a Wilderness Boss at " + kills + " KC!";

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());

            } else {

                String discordMessage = "[DROP] " + toGive.getUsername()
                        + " just received " + itemMessage + " from " + npcName + " at " + kills + " KC!";

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
            }

            if (ccAnnounce) {
                ClanChatManager.sendMessage(player.getCurrentClanChat(), "<col=16777215>[<col=255>Lootshare<col=16777215>]<col=3300CC> " + toGive.getUsername() + " received " + itemMessage + " from " + npcName + "!");
            }

            PlayerLogs.log(toGive.getUsername(), toGive.getUsername() + " received " + itemMessage + " from " + npcName);
        }

        Item newItem = new Item(item.getId(), amount);

        if ((player.getEquipment().contains(221140) || (player.getEquipment().contains(21077) && player.lightbearerOptions[4])) && rof) {
            player.getInventory().add(itemId, newItem.getAmount());
        } else {
            for (int i = 0; i < amount; i++) {
                if (!player.canTransferWealth()) {
                    GroundItemManager.spawnGroundItem(toGive, new GroundItem(new Item(itemId, 1), pos, toGive.getUsername(), false, 150, false, 200));
                } else {
                    GroundItemManager.spawnGroundItem(toGive, new GroundItem(new Item(itemId, 1), pos, toGive.getUsername(), false, 150, goGlobal, 200));
                }
            }
        }
        DropLog.submit(toGive, new DropLogEntry(itemId, item.getAmount()));
    }

    public static void checkTertiaryDrops(Player p, NPC npc) {
        int combatsquared = npc.getDefinition().getCombatLevel() * npc.getDefinition().getCombatLevel();
        int qty = 1;
        int loot = 995;
        int effigy = RandomUtility.inclusiveRandom(100000000);
        int alchable = RandomUtility.inclusiveRandom(1000000);
        int CrystalKey = 1024;
        int Larran = 25000;
        boolean goGlobal = true;

        Position pos = npc.getPosition();

        //effigy
        if (combatsquared >= effigy) {
            int effigyQty = 1;

            if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_LOOT) || (p.doubleLoot && p.personalEvent))
                effigyQty *= 2;

            if ((p.getEquipment().contains(221140) || (p.getEquipment().contains(21077) && p.lightbearerOptions[4])) && p.getInventory().getFreeSlots() >= effigyQty) {
                p.getInventory().add(18778, effigyQty);
            } else {
                for (int i = 0; i < effigyQty; i++) {
                    GroundItemManager.spawnGroundItem(p, new GroundItem(new Item(18778, 1), pos, p.getUsername(), false, 150, false, 200));
                }
            }
            String message = "@blu@[EFFIGY DROP]";
            p.sendMessage(message);
        }

        //charms
        int[] randomcharm = {12158, 12159, 12160, 12163};
        int randomcharmPos = RandomUtility.inclusiveRandom(randomcharm.length - 1);

        int itemId = randomcharm[randomcharmPos];

        drop(p, new Item(itemId, 1), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, false, false);

        //alchables
        if (combatsquared >= alchable) {
            int itemid = GameSettings.ALCHABLES[RandomUtility.inclusiveRandom(GameSettings.ALCHABLES.length - 1)];
            drop(p, new Item(itemid), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, true, false);
        }

        //supply drops
        if (combatsquared <= 3600) {

            //herbs 200, 202, 204, 206, 208, 210, 212, 214, 216, 218, 220, 2486, 3050, 3052
            //fish 318, 322, 332, 336, 360, 372, 378, 384, 390, 396
            //bars 2350, 2352, 2354, 2360, 2362, 2364
            //gems 1618, 1620, 1622, 1624, 1632
            //logs 1512, 1514, 1516, 1518, 1520, 1522

            int[] loottable = {199, 201, 203, 205, 317, 321, 331, 2349, 2351, 1625, 1627, 1629, 1511, 1521};
            loot = loottable[RandomUtility.inclusiveRandom(loottable.length - 1)];

            if (p.checkAchievementAbilities(p, "gatherer")) {
                loot += 1;
            }

            qty = RandomUtility.inclusiveRandom(2, 4);

            drop(p, new Item(loot, qty), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, false, false);

        } else if ((combatsquared <= 14400) && (combatsquared > 3600)) {
            int[] loottable = {207, 209, 211, 213, 215, 335, 359, 371, 377, 2353, 2359, 1619, 1621, 1623, 1519, 1517};
            loot = loottable[RandomUtility.inclusiveRandom(loottable.length - 1)];

            if (p.checkAchievementAbilities(p, "gatherer")) {
                loot += 1;
            }

            qty = RandomUtility.inclusiveRandom(3, 6);

            drop(p, new Item(loot, qty), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, false, false);

        } else {
            int[] loottable = {217, 219, 2485, 3049, 3051, 383, 389, 395, 2361, 2363, 1617, 1631, 1513, 1515};
            loot = loottable[RandomUtility.inclusiveRandom(loottable.length - 1)];

            if (p.checkAchievementAbilities(p, "gatherer")) {
                loot += 1;
            }

            qty = RandomUtility.inclusiveRandom(3, 6);

            drop(p, new Item(loot, qty), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, false, false);
        }

        //crystal key/lock picks
        if ((0 < npc.getDefinition().getCombatLevel()) && (npc.getDefinition().getCombatLevel() < 40)) {
            CrystalKey = 1024;
            Larran = 5000;
        } else if ((40 <= npc.getDefinition().getCombatLevel()) && (npc.getDefinition().getCombatLevel() < 80)) {
            CrystalKey = 512;
            Larran = 2500;
        } else if ((80 <= npc.getDefinition().getCombatLevel()) && (npc.getDefinition().getCombatLevel() < 120)) {
            CrystalKey = 256;
            Larran = 1000;
        } else if ((120 <= npc.getDefinition().getCombatLevel()) && (npc.getDefinition().getCombatLevel() < 160)) {
            CrystalKey = 128;
            Larran = 500;
        } else if ((160 <= npc.getDefinition().getCombatLevel()) && (npc.getDefinition().getCombatLevel() < 200)) {
            CrystalKey = 64;
            Larran = 250;
        } else if (240 <= npc.getDefinition().getCombatLevel()) {
            CrystalKey = 32;
            Larran = 100;
        }

        if (p.wildTainted > 0) {
            Larran = Larran / 2;
        }

        if (npc.getLocation() == Location.WILDERNESS)
            CrystalKey = CrystalKey * 3 / 4;

        if (p.Lucky > 0 && PerkHandler.canUseNormalPerks(p))
            CrystalKey /= 2;

        if (p.wildLooter > 0 && PerkHandler.canUseWildernessPerks(p))
            CrystalKey /= 2;

        int keyChance = RandomUtility.inclusiveRandom(CrystalKey + 1);
        int larranChance = RandomUtility.inclusiveRandom(Larran + 1);

        if (keyChance == CrystalKey) {
            int[] keyhalf = {985, 987, 985, 987, 989};
            int keyhalfRand = keyhalf[RandomUtility.inclusiveRandom(keyhalf.length - 1)];

            drop(p, new Item(keyhalfRand, 1), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, false, true);

            String message = "@blu@[CRYSTAL KEY DROP]";
            p.sendMessage(message);

        }

        if (larranChance == Larran && npc.getLocation() == Location.WILDERNESS) {
            drop(p, new Item(223490, 1), new NPC(npc.getId(), p.getPosition().copy()), p.getPosition().copy(), goGlobal, false, true);

            String message = "@blu@[LARRAN'S KEY]";
            p.sendMessage(message);
        }

    }

    /**
     * Gets the drop list
     *
     * @return the list
     */
    public NpcDropItem[] getDropList() {
        return drops;
    }

    /**
     * Gets the npcIds
     *
     * @return the npcIds
     */
    public int[] getNpcIds() {
        return npcIds;
    }


    public enum DropChance {
        ALWAYS(2), ALMOST_ALWAYS(8), VERY_COMMON(15), COMMON(50), LESS_COMMON(80), UNCOMMON(120), RARE(240), VERY_RARE(400), SUPER_RARE(600), ULTRA_RARE(800), LEGENDARY(1000), LEGENDARY2(1200), LEGENDARY3(1500);


        private final int random;

        DropChance(int randomModifier) {
            this.random = randomModifier;
        }

        public int getRandom() {
            return this.random;
        }
    }

    /**
     * Crystal Keys a npc drop item
     */
    public static class NpcDropItem {

        /**
         * The id.
         */
        private final int id;

        /**
         * Array holding all the amounts of this item.
         */
        private final int[] count;

        /**
         * The chance of getting this item.
         */
        private final int chance;

        /**
         * New npc drop item
         *
         * @param id     the item
         * @param count  the count
         * @param chance the chance
         */
        public NpcDropItem(int id, int[] count, int chance) {
            this.id = id;
            this.count = count;
            this.chance = chance;
        }

        /**
         * Gets the item id.
         *
         * @return The item id.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the chance.
         *
         * @return The chance.
         */
        public int[] getCount() {
            return count;
        }

        /**
         * Gets the chance.
         *
         * @return The chance.
         */
        public DropChance getChance() {
            switch (chance) {
                case 1:
                    return DropChance.ALMOST_ALWAYS;
                case 2:
                    return DropChance.VERY_COMMON;
                case 3:
                    return DropChance.COMMON;
                case 4:
                    return DropChance.LESS_COMMON;
                case 5:
                    return DropChance.UNCOMMON;
                case 6:
                    return DropChance.RARE;
                case 7:
                    return DropChance.VERY_RARE;
                case 8:
                    return DropChance.SUPER_RARE;
                case 9:
                    return DropChance.ULTRA_RARE;
                case 10:
                    return DropChance.LEGENDARY;
                case 11:
                    return DropChance.LEGENDARY2;
                case 12:
                    return DropChance.LEGENDARY3;
                default:
                    return DropChance.ALWAYS;
            }
        }


        /**
         * Gets the item
         *
         * @return the item
         */
        public Item getItem() {
            int amount = 0;
            for (int i = 0; i < count.length; i++)
                amount += count[i];
            if (amount > count[0])
                amount = count[0] + RandomUtility.inclusiveRandom(count[1]);
            return new Item(id, amount);
        }
    }

    public static class ItemDropAnnouncer {

        private static final int[] TO_ANNOUNCE = new int[]{


                //Pets
                11971, 11972, 11978, 11979, 11981, 11982, 11983, 11984, 11985, 11986, 11987, 11988, 11989, 11990, 11991,
                11992, 11993, 11994, 11995, 11996, 11997, 12001, 12002, 12003, 12004, 12005, 12006,
                14914, 14916, 20079, 20080, 20081, 20082, 20083, 20085, 20086, 20087, 20088, 20089, 20090, 20103, 20104,
                221509, 220659, 220661, 220663, 220665, 220693, 221187, 213322, 211320, 220851, 11157, 212399,
                223398, 222336, 212646, 222473, 224491, 221907, 221992,

                //Master Uniques
                21003, 21004, 21005, 21006, 13740, 13742, 20998, 4450, 4454, 14008, 14009,
                14010, 14011, 14012, 14013, 14014, 14015, 14016, 20569, 20570, 20571,

                //Legendary Uniques
                14484, 18888, 18889, 18890, 21000, 21001, 21002, 219493, 219496, 13239, 13235, 12708, 18844,
                12926, 12284, 13738, 13744, 11694, 11696, 11698, 11700, 224419, 224420, 224421, 224417, 18347, 222981, 4448,

                //High Uniques
                11724, 11726, 11722, 11720, 13045, 13051, 4452, 20555, 12282, 20559, 11283, 212006, 11716,
                11702, 11704, 11706, 11708, 18843, 20568, 13736, 20564, 12061,

                //Medium Uniques
                4453, 11730, 11728, 11718, 4151, 13047, 11235, 6737, 6735, 6733, 6731, 18897,
                11924, 11926, 6739, 15259, 18845, 11690, 12603, 12605, 224780, 20565, 15241, 19780, 14057, 213233, 221028,

                //Low Uniques
                4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738,
                4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759, 6920, 11732, 6585, 6914, 6889, 2577, 14497, 14499, 14501, 18846,
                6724, 15403, 211905, 4153,

                //Wild Uniques
                19868, 4671, 17275,

                //Slayer Heads
                //221907, 223077, 7980, 7981, 224466, 221275, 7979,

                //Dragon equip
                11613,

                //Other
                6199, 15501, 20566, 219835, 13227, 13229, 13231, 2957, 6640, 6642, 6645,
                13734, 13746, 13748, 13750, 13752, 13754, 20572, 20573, 20574, 14472, 6571, 220517, 220520, 220595, 11286,
                20558, 601, 602, 603, 2957, 6571,

                //Rev Uniques
                13899, 13905, 13902, 13867, 13867, 13879, 13858, 13870, 13884, 13887, 13861, 13873, 13890,
                13893, 13864, 13876, 13896,

                //Subscription Boxes
                906, 907, 908, 909, 910,

                //Superior Slayer
                19669, 212849, 219677, 2380, 2381, 2382, 2383, 219835, 2707, 2708,

                //Slayer
                15488, 15490, 6746, 8921, 222002


        };
        private static List<Integer> ITEM_LIST;

        private static void init() {
            ITEM_LIST = new ArrayList<Integer>();
            for (int i : TO_ANNOUNCE) {
                ITEM_LIST.add(i);
            }
        }

        public static boolean announce(int item) {
            return ITEM_LIST.contains(item);
        }
    }
}