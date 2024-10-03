package com.arlania.world.content.clog;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.world.World;
import com.arlania.world.content.Bingo;
import com.arlania.world.content.ClueScrolls;
import com.arlania.world.content.MonsterDrops;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.minigames.impl.barrows.Rewards;
import com.arlania.world.content.minigames.impl.chaosraids.ChaosRewards;
import com.arlania.world.content.minigames.impl.gwdraids.GwdrRewards;
import com.arlania.world.content.minigames.impl.skilling.BlastMine;
import com.arlania.world.content.minigames.impl.skilling.Wintertodt;
import com.arlania.world.content.seasonal.SeasonalHandler;
import com.arlania.world.content.skill.SkillManager;
import com.arlania.world.content.skill.impl.slayer.SlayerMobs;
import com.arlania.world.content.skill.impl.slayer.SuperiorSlayer;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;


/**
 * @author Era || Feb 25, 2021
 */
public class CollectionLog {


    private static final int INTERFACE_ID = 29000;
    /* Variables */
    private static HashMap<CollectionTabType, ArrayList<Integer>> collectionNPCS;
    public CollectionLog.CollectionTabType currentCollectionLogTab;
    public int previousSelectedCell;
    public int previousSelectedTab;

    public HashMap<Integer, Integer> killsTracker = new HashMap<>();
    Player player;
    private HashMap<String, ArrayList<Item>> collections;

    public CollectionLog(Player player) {
        this.player = player;
        this.collections = new HashMap<>();
        prepareDrops();
    }

    public static String getDataDirectory() {
        return "data/collectionlog/cfg/collection_npcs.json";
    }

    /**
     * Initializes the default npcs to be collecting for
     */
    public static void init() {
        try {
            Path path = Paths.get(getDataDirectory());
            File file = path.toFile();

            JsonParser parser = new JsonParser();
            if (!file.exists()) {
                System.out.println(file.getName() + " did not exist");
                return;
            }
            Object obj = parser.parse(new FileReader(file));
            JsonObject jsonUpdates = (JsonObject) obj;

            Type listType = new TypeToken<HashMap<CollectionTabType, ArrayList<Integer>>>() {
            }.getType();

            collectionNPCS = new Gson().fromJson(jsonUpdates, listType);
            Bingo.init(collectionNPCS);
            System.out.println("collectionNPCS Loaded succesfully");//wasn't read or something
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "No default NPCs found!", e);
            collectionNPCS = new HashMap<>();
        }
    }

    public static List<Item> getRareDropList(Integer npcId) {
        List<Item> itemList = new ArrayList<Item>();
        //mystery box collection log
        if (npcId == CustomCollection.MysteryBox.getId()) {
            for (int itemId : GameSettings.LEGENDARYUNIQUESfromBOX) {//rares only
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        //clue collection log
        if (npcId == CustomCollection.EasyClues.getId()) {
            for (int itemId : ClueScrolls.EASY_UNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.MediumClues.getId()) {
            for (int itemId : ClueScrolls.MEDIUM_UNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.HardClues.getId()) {
            for (int itemId : ClueScrolls.HARD_UNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.EliteClues.getId()) {
            for (int itemId : ClueScrolls.ELITE_UNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.MasterClues.getId()) {
            for (int itemId : ClueScrolls.MASTER_UNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.GenericClues.getId()) {
            for (int itemId : ClueScrolls.GENERIC_UNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.Wintertodt.getId()) {
            for (int itemId : Wintertodt.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.BlastMine.getId()) {
            for (int itemId : BlastMine.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.Slayer.getId()) {
            for (int itemId : SlayerMobs.slayerUniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.SkillingPets.getId()) {
            for (int itemId : SkillManager.SkillingPets) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.COX.getId()) {
            for (int itemId : com.arlania.world.content.minigames.impl.chambersofxeric.CoxRewards.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.TOB.getId()) {
            for (int itemId : com.arlania.world.content.minigames.impl.theatreofblood.TobRewards.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.CHAOS.getId()) {
            for (int itemId : ChaosRewards.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.SHR.getId()) {
            for (int itemId : com.arlania.world.content.minigames.impl.strongholdraids.Rewards.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }

		/*if(npcId == CustomCollection.GAUNTLET.getId()) {
			for(int itemId : GauntletRewards.uniques) {
				itemList.add(new Item(itemId,0));
			}
			return itemList;
		}*/
        if (npcId == CustomCollection.BARROWS.getId()) {
            for (int itemId : Rewards.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.GWD.getId()) {
            for (int itemId : GwdrRewards.uniques) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.LOW_UNIQUES.getId()) {
            for (int itemId : GameSettings.LOWUNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.MEDIUM_UNIQUES.getId()) {
            for (int itemId : GameSettings.MEDIUMUNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.HIGH_UNIQUES.getId()) {
            for (int itemId : GameSettings.HIGHUNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.LEGENDARY_UNIQUES.getId()) {
            for (int itemId : GameSettings.LEGENDARYUNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.MASTER_UNIQUES.getId()) {
            for (int itemId : GameSettings.MASTERUNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.CUSTOM_UNIQUES.getId()) {
            for (int itemId : GameSettings.CUSTOMUNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.UNTRADEABLES.getId()) {
            for (int itemId : GameSettings.UPGRADEABLE_UNTRADEABLES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.WILDY_UNIQUES.getId()) {
            for (int itemId : GameSettings.WILDYUNIQUES) {
                itemList.add(new Item(itemId, 0));
            }
            return itemList;
        }
        if (npcId == CustomCollection.SUPERIOR_SLAYER.getId()) {
            NPCDrops drops = NPCDrops.getDrops().get(22);
            for (NPCDrops.NpcDropItem drop : drops.getDropList()) {
                if (NPCDrops.CollectionDropChance(drop.getChance())) {
                    itemList.add(new Item(drop.getId(), 0));
                }
            }
            return itemList;
        }

        NPCDrops drops = MonsterDrops.npcDrops.get(getCollectionName(npcId).toLowerCase());

        if (drops != null) {
            for (int i = 0; i < drops.getDropList().length; i++) {
                if (drops.getDropList()[i].getItem().getId() <= 0 || drops.getDropList()[i].getItem().getId() > ItemDefinition.getMaxAmountOfItems() || drops.getDropList()[i].getItem().getAmount() <= 0) {
                    continue;
                }
                if (NPCDrops.CollectionDropChance(drops.getDropList()[i].getChance()))
                    itemList.add(new Item(drops.getDropList()[i].getItem().getId(), 0));
            }
            return itemList;
        }
        return itemList;
    }

    public static String getCollectionName(int id) {
        String npcName = "none";
        if (id < 0) {
            for (CustomCollection cc : CustomCollection.values()) {
                if (id == cc.getId()) {
                    npcName = cc.getName();
                    break;
                }
            }
        } else {
            NpcDefinition npcDef = NpcDefinition.forId(id);
            if (npcDef != null) {
                npcName = npcDef.getName();
            }
        }
        return npcName;
    }

    public static int normalizeNpcId(int id) {
        //Zulrah
        if (id == 2042 || id == 2043 || id == 2044)
            return 2044;

        //Kalphite Queen
        if (id == 1158 || id == 1160)
            return 1159;

        if (id == 22)
            return -22;

        //Superior Slayer
        for (SuperiorSlayer ss : SuperiorSlayer.values()) {
            for (int superiorId : ss.getSuperiorId()) {
                if (superiorId == id) {
                    return -22;
                }
            }
        }
        return id;
    }

    public HashMap<String, ArrayList<Item>> getCollections() {
        return collections;
    }

    /**
     * Opens the interface for a player
     */
    public void openInterface() {
        resetInterface();
        selectTab(CollectionTabType.BOSSES);
    }

    /**
     * Clears the interface
     */
    public void resetInterface() {
        for (int i = 0; i < 50; i++) {
            player.getPA().sendFrame126("", 29013 + (i * 2));
            player.getPA().sendConfig(520 + i, 0);
        }
        player.getPA().sendConfig(519, 0);
        for (int i = 0; i < 3; i++) {
            player.getPA().sendConfig(571 + i, 0);
        }
    }

    /**
     * Selects a tab within the interface
     *
     * @param type
     */
    public void selectTab(CollectionTabType type) {
        if (collectionNPCS == null || collectionNPCS.isEmpty()) {
            return;
        }

        ArrayList<Integer> npcs = collectionNPCS.get(type);
        if (npcs != null) {
            resetInterface();
            currentCollectionLogTab = type;
            previousSelectedCell = 0;
            player.getPA().sendConfig(previousSelectedTab == 0 ? 519 : 570 + previousSelectedTab, 0);
            previousSelectedTab = type.ordinal();
            player.getPA().sendConfig(type.ordinal() == 0 ? 519 : 570 + type.ordinal(), 1);

            for (int i = 0; i < npcs.size(); i++) {
                if (getCollections().containsKey(npcs.get(i) + "")) {
                    ArrayList<Item> itemsObtained = getCollections().get(npcs.get(i) + "");
                    if (itemsObtained != null) {
                        String name = getCollectionName(npcs.get(i));
                        for (CustomCollection cc : CustomCollection.values()) {
                            if (npcs.get(i) == cc.getId()) {
                                name = cc.getName();
                                break;
                            }
                        }
                        int obtainedItems = 0;
                        for (Item item : itemsObtained) {
                            if (item.getAmount() > 0)
                                obtainedItems++;
                        }
                        player.getPA().sendFrame126((obtainedItems == itemsObtained.size() ? "@gre@" : obtainedItems > 0 ? "@yel@" : "@red@") + name, 29013 + (i * 2));
                    }
                }
            }
            selectCell(0, type);
        } else {
            player.sendMessage("There are no collection logs for this type yet.");
        }
    }

    /**
     * Selects a cell from a tab type
     *
     * @param index
     * @param type
     */
    public void selectCell(int index, CollectionTabType type) {
        if (collectionNPCS == null || collectionNPCS.isEmpty()) {
            return;
        }

        ArrayList<Integer> npcs = collectionNPCS.get(type);
        if (npcs != null) {
            if (index >= npcs.size()) {
                return;
            }

            player.getPA().sendConfig(520 + previousSelectedCell, 0);
            previousSelectedCell = index;
            player.getPA().sendConfig(520 + index, 1);

            populateInterface(npcs.get(index));
        }
    }

    /**
     * Populates the interface with data
     *
     * @param npcId
     */
    public void populateInterface(int npcId) {
        String npcName = getCollectionName(npcId);
        for (CustomCollection cc : CustomCollection.values()) {
            if (npcId == cc.getId()) {
                npcName = cc.getName();
                break;
            }
        }

        player.getPA().sendFrame126(player.getUsername() + "'s Collection Log - " + player.completedLogs + "/" + collections.size(), 29002);
        player.getPA().sendFrame126(npcName, 29008);

        player.getPA().sendFrame126(npcName + ": @whi@" + (getKills(npcId)), 29010);

        ArrayList<Item> items = getCollections().get(npcId + "");
        List<Item> dropItems = getRareDropList(npcId);

        int foundCount = 0;
        //Clear items
        int frameId = 29121;
        for (int i = 0; i < 198; i++) {
            player.getPA().sendItemOnInterface(frameId, -1, 0);
        }

        for (int i = 0; i < dropItems.size(); i++) {
            for (Item item : items) {
                if (item.getId() == dropItems.get(i).getId()) {
                    player.getPA().sendItemOnInterface(frameId, item.getId(), i, item.getAmount());
                    if (item.getAmount() > 0) {
                        foundCount++;
                    }
                }
            }
        }
        player.getPA().sendFrame126("Obtained: " + (foundCount == dropItems.size() ? "@gre@" : "@red@") + foundCount + "/" + dropItems.size(), 29009);
        player.getPA().sendInterface(INTERFACE_ID);
    }

    public void handleDrop(int npcId, int dropId, int dropAmount) {
        handleDrop(npcId, dropId, dropAmount, true);
    }

    /**
     * Handles and NPC dropping an item
     *
     * @param npcId
     * @param dropId
     * @param dropAmount
     */
    public void handleDrop(int npcId, int dropId, int dropAmount, boolean message) {
        npcId = normalizeNpcId(npcId);
        player.getBingo().handleDrop(dropId, npcId);
        String npcName = getCollectionName(npcId);

        int points = 0;

        if (!isCollectionNPC(npcId)) {
            return;
        }

        boolean complete = true;
        ArrayList<Item> currentItems = getCollections().get("" + npcId);
        if (currentItems == null) {
            currentItems = new ArrayList<>();
            Item item = new Item(dropId, dropAmount);
            currentItems.add(item);
            if (message)
                player.sendMessage("You have unlocked another item in your collection log! @red@(" + item.getName() + ")");
        } else {
            for (Item currentItem : currentItems) {

                if (currentItem.getAmount() == 0) //check to see if there was a hole in the C-Log
                    complete = false;

                if (currentItem.getId() == dropId) {
                    if (message) {
                        if (currentItem.getAmount() <= 0 && npcId > 0) {
                            player.sendMessage("You have unlocked another item in your collection log! @red@(" + currentItem.getName() + ")");

                            String messageB = "@blu@[DROP] " + player.getUsername() + " just received @red@" + currentItem.getName() + "@blu@ from " + npcName + "!";
                            World.sendMessage("drops", messageB);

                        } else
                            player.sendMessage("An item has been added to your collection log! @red@(" + currentItem.getName() + ")");
                    }

                    if(currentItem.getAmount() + dropAmount >= 0) {
                        currentItem.setAmount(currentItem.getAmount() + dropAmount);
                    }
                }
            }
        }

        if (!complete) {
            for (Item currentItem : currentItems) {
                if (currentItem.getAmount() == 0) //check to see if there was a hole in the C-Log
                {
                    return;
                } else if (currentItem.getAmount() > 0) //check to see if there was a hole in the C-Log
                {
                    complete = true;
                }
            }
            if (complete) {
                player.completedLogs++;


                if (player.completedLogs >= 50)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_50_CLOGS, 50);
                if (player.completedLogs >= 45)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_45_CLOGS, 45);
                if (player.completedLogs >= 40)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_40_CLOGS, 40);
                if (player.completedLogs >= 35)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_35_CLOGS, 35);
                if (player.completedLogs >= 30)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_30_CLOGS, 30);
                if (player.completedLogs >= 25)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_25_CLOGS, 25);
                if (player.completedLogs >= 20)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_20_CLOGS, 20);
                if (player.completedLogs >= 15)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_15_CLOGS, 15);
                if (player.completedLogs >= 10)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_10_CLOGS, 10);
                if (player.completedLogs >= 5)
                    player.getAchievementTracker().progress(AchievementData.COMPLETE_5_CLOGS, 5);

                if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    points = 6;

                    if (npcId >= -6 && npcId <= -1)
                        points = 12;

                    SeasonalHandler.addPoints(player, points);

                    String Points = "points";

                    String time = player.ticksToMinutes(player.getTotalPlayTime() + player.getRecordedLogin().elapsed());
                    String messageA = player.getUsername() + " has completed " + npcName + "'s collection log and has " + player.seasonPoints + " " + Points + "! (" + time + ")";
                    String discordMessage = GameSettings.SUCCESSKID + " " + messageA;

                    World.sendMessage("seasonal", "<img=35> @blu@[C-LOG] " + player.getUsername() + " has completed a collection log and has " + player.seasonPoints + " " + Points + "!");

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_SEASONAL_POINTS_CH).get());
                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());

                } else {
                    World.sendMessage("drops", "@blu@[C-LOG] " + player.getUsername() + " has completed " + npcName + "'s collection log");

                    String discordMessage = "[C-LOG] " + player.getUsername() + " has completed " + npcName + "'s collection log!";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());


                }
            }
        }
    }

    /**
     * Checks if an NPC is in fact a collection NPC
     *
     * @param npcId
     * @return
     */
    public boolean isCollectionNPC(int npcId) {
        for (Entry<CollectionTabType, ArrayList<Integer>> entry : collectionNPCS.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i) == npcId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Handles all buttons on the interface
     *
     * @param buttonId
     * @return
     */
    public boolean handleActionButtons(int buttonId) {
        if (buttonId >= 29012 && buttonId <= 29110) {
            int index = (buttonId - 29012) / 2;
            selectCell(index, currentCollectionLogTab);
            return true;
        }
        switch (buttonId) {
            case 29006:
                selectTab(CollectionTabType.BOSSES);
                return true;
            case 29112:
                selectTab(CollectionTabType.WILDERNESS);
                return true;
            case 29114:
                selectTab(CollectionTabType.RAIDS);
                return true;
            case 29116:
                selectTab(CollectionTabType.OTHER);
                return true;
            case 29118:
                selectTab(CollectionTabType.UPGRADES);
                return true;
            case 29003:
                player.getPA().closeAllWindows();
                return true;
        }
        return false;
    }

    private void prepareDrops() {
        collectionNPCS.forEach((category, npcIds) -> {
                    for (int npcId : npcIds) {
                        ArrayList<Item> newItems = (ArrayList<Item>) getRareDropList(npcId);
                        if (!collections.containsKey(npcId + ""))
                            collections.put(npcId + "", newItems);
                        ArrayList<Item> oldItems = collections.get(npcId + "");
                        for (int i = 0; i < newItems.size(); i++) {
                            Item item = newItems.get(i);
                            for (Item old : oldItems) {
                                if (item.getId() == old.getId()) {
                                    newItems.set(i, new Item(old.getId(), old.getAmount()));
                                }
                            }
                        }
                        collections.put(npcId + "", newItems);
                    }
                }
        );
    }

    /**
     * Loads a users collection data
     */
    public void loadCollections(JsonElement clogs) {
        try {
            Type listType = new TypeToken<HashMap<String, ArrayList<Item>>>() {
            }.getType();

            collections = new Gson().fromJson(clogs, listType);
            prepareDrops();
            loadUpgrades();
            checkCompletedLogs();
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "No collections found!", e);
            collections = new HashMap<>();
        }
    }

    public HashMap<Integer, Integer> getKillsTracker() {
        return killsTracker;
    }

    public int getKills(int id) {
        if (id == 2043 || id == 2042)
            id = 2044;
        Integer kills = killsTracker.get(id);
        if (kills == null) {
            killsTracker.put(id, 0);
            return 0;
        } else {
            return kills;
        }
    }

    public int addKill(int id) {
        id = normalizeNpcId(id);

        int kills = getKills(id) + 1;
        killsTracker.put(id, kills);
        return kills;
    }

    public void loadKillsTracker(JsonElement kills) {
        try {
            Type listType = new TypeToken<HashMap<Integer, Integer>>() {
            }.getType();
            killsTracker = new Gson().fromJson(kills, listType);
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "No trackers found!", e);
            killsTracker = new HashMap<>();
        }
    }

    public void loadUpgrades() {
        try {

            ArrayList<Integer> upgrades = collectionNPCS.get(CollectionLog.CollectionTabType.UPGRADES);


            if (player.getEquipment().get(Equipment.HEAD_SLOT).getId() != player.idHelmUpgrade) {
                player.idHelmUpgrade = 0;
                player.qtyHelmUpgrade = 0;
            }
            if (player.getEquipment().get(Equipment.BODY_SLOT).getId() != player.idBodyUpgrade) {
                player.idBodyUpgrade = 0;
                player.qtyBodyUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.LEG_SLOT).getId() != player.idLegsUpgrade) {
                player.idLegsUpgrade = 0;
                player.qtyLegsUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.WEAPON_SLOT).getId() != player.idWeaponUpgrade) {
                player.idWeaponUpgrade = 0;
                player.qtyWeaponUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.SHIELD_SLOT).getId() != player.idShieldUpgrade) {
                player.idShieldUpgrade = 0;
                player.qtyShieldUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.FEET_SLOT).getId() != player.idBootsUpgrade) {
                player.idBootsUpgrade = 0;
                player.qtyBootsUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.RING_SLOT).getId() != player.idRingUpgrade) {
                player.idRingUpgrade = 0;
                player.qtyRingUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.CAPE_SLOT).getId() != player.idCapeUpgrade) {
                player.idCapeUpgrade = 0;
                player.qtyCapeUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.HANDS_SLOT).getId() != player.idGlovesUpgrade) {
                player.idGlovesUpgrade = 0;
                player.qtyGlovesUpgrade = 0;
            }

            if (player.getEquipment().get(Equipment.AMULET_SLOT).getId() != player.idAmuletUpgrade) {
                player.idAmuletUpgrade = 0;
                player.qtyAmuletUpgrade = 0;
            }

            for (Integer upgrade : upgrades) {
                if (getCollections().containsKey(upgrade + "")) {
                    ArrayList<Item> itemsObtained = getCollections().get(upgrade + "");
                    if (itemsObtained != null) {
                        for (Item item : itemsObtained) {
                            if (item.getAmount() > 0) {
                                if (item.getId() == 11718 && player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getName().contains("Armadyl hel")) {
                                    player.idHelmUpgrade = player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getId();
                                    player.qtyHelmUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11720 && player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getName().contains("Armadyl chest")) {
                                    player.idBodyUpgrade = player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getId();
                                    player.qtyBodyUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11722 && player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getName().contains("Armadyl chain")) {
                                    player.idLegsUpgrade = player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getId();
                                    player.qtyLegsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11724 && player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getName().contains("Bandos chest")) {
                                    player.idBodyUpgrade = player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getId();
                                    player.qtyBodyUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11726 && player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getName().contains("Bandos tass")) {
                                    player.idLegsUpgrade = player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getId();
                                    player.qtyLegsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11728 && player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getName().contains("Bandos boo")) {
                                    player.idBootsUpgrade = player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getId();
                                    player.qtyBootsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 18888 && player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getName().contains("Ancestral ha")) {
                                    player.idHelmUpgrade = player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getId();
                                    player.qtyHelmUpgrade = item.getAmount();
                                }
                                if (item.getId() == 18889 && player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getName().contains("Ancestral robe to")) {
                                    player.idBodyUpgrade = player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getId();
                                    player.qtyBodyUpgrade = item.getAmount();
                                }
                                if (item.getId() == 18890 && player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getName().contains("Ancestral robe bot")) {
                                    player.idLegsUpgrade = player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getId();
                                    player.qtyLegsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 8839 && player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getName().contains("void knight")) {
                                    player.idBodyUpgrade = player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getId();
                                    player.qtyBodyUpgrade = item.getAmount();
                                }
                                if (item.getId() == 8840 && player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getName().contains("void knight")) {
                                    player.idLegsUpgrade = player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getId();
                                    player.qtyLegsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 219496 && player.getEquipment().get(Equipment.AMULET_SLOT).getDefinition().getName().contains("Amulet of tort")) {
                                    player.idAmuletUpgrade = player.getEquipment().get(Equipment.AMULET_SLOT).getDefinition().getId();
                                    player.qtyAmuletUpgrade = item.getAmount();
                                }
                                if (item.getId() == 219496 && player.getEquipment().get(Equipment.AMULET_SLOT).getDefinition().getName().contains("Necklace of angui")) {
                                    player.idAmuletUpgrade = player.getEquipment().get(Equipment.AMULET_SLOT).getDefinition().getId();
                                    player.qtyAmuletUpgrade = item.getAmount();
                                }
                                if (item.getId() == 219496 && player.getEquipment().get(Equipment.HANDS_SLOT).getDefinition().getName().contains("Tormented brace")) {
                                    player.idGlovesUpgrade = player.getEquipment().get(Equipment.HANDS_SLOT).getDefinition().getId();
                                    player.qtyGlovesUpgrade = item.getAmount();
                                }
                                if (item.getId() == 13239 && player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getName().contains("Steadfast")) {
                                    player.idBootsUpgrade = player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getId();
                                    player.qtyBootsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 12708 && player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getName().contains("Glavien")) {
                                    player.idBootsUpgrade = player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getId();
                                    player.qtyBootsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 13235 && player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getName().contains("Ragefire")) {
                                    player.idBootsUpgrade = player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getId();
                                    player.qtyBootsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 219496 && player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getName().contains("Ring of suffer")) {
                                    player.idRingUpgrade = player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getId();
                                    player.qtyRingUpgrade = item.getAmount();
                                }
                                if (item.getId() == 222951 && player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getName().contains("oots of brimston")) {
                                    player.idBootsUpgrade = player.getEquipment().get(Equipment.FEET_SLOT).getDefinition().getId();
                                    player.qtyBootsUpgrade = item.getAmount();
                                }
                                if (item.getId() == 6737 && player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getName().contains("Berserker")) {
                                    player.idRingUpgrade = player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getId();
                                    player.qtyRingUpgrade = item.getAmount();
                                }
                                if (item.getId() == 6735 && player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getName().contains("Warrior")) {
                                    player.idRingUpgrade = player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getId();
                                    player.qtyRingUpgrade = item.getAmount();
                                }
                                if (item.getId() == 6733 && player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getName().contains("Archer")) {
                                    player.idRingUpgrade = player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getId();
                                    player.qtyRingUpgrade = item.getAmount();
                                }
                                if (item.getId() == 6731 && player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getName().contains("Seer")) {
                                    player.idRingUpgrade = player.getEquipment().get(Equipment.RING_SLOT).getDefinition().getId();
                                    player.qtyRingUpgrade = item.getAmount();
                                }
                                if (item.getId() == 2412 && player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getName().toLowerCase().contains("saradomin")) {
                                    player.idCapeUpgrade = player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getId();
                                    player.qtyCapeUpgrade = item.getAmount();
                                }
                                if (item.getId() == 2413 && player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getName().toLowerCase().contains("guthix")) {
                                    player.idCapeUpgrade = player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getId();
                                    player.qtyCapeUpgrade = item.getAmount();
                                }
                                if (item.getId() == 2414 && player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getName().toLowerCase().contains("zamorak")) {
                                    player.idCapeUpgrade = player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getId();
                                    player.qtyCapeUpgrade = item.getAmount();
                                }
                                if (item.getId() == 6570 && player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getName().toLowerCase().contains("infernal")) {
                                    player.idCapeUpgrade = player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getId();
                                    player.qtyCapeUpgrade = item.getAmount();
                                }
                                if (item.getId() == 10499 && player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getName().toLowerCase().contains("assembler")) {
                                    player.idCapeUpgrade = player.getEquipment().get(Equipment.CAPE_SLOT).getDefinition().getId();
                                    player.qtyCapeUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11694 && player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().toLowerCase().contains("armadyl Gods")) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11696 && player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().toLowerCase().contains("bandos Gods")) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11698 && player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().toLowerCase().contains("saradomin gods")) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11700 && player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().toLowerCase().contains("zamorak Gods")) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 7462 && player.getEquipment().get(Equipment.HANDS_SLOT).getDefinition().getName().toLowerCase().contains("barrows")) {
                                    player.idGlovesUpgrade = player.getEquipment().get(Equipment.HANDS_SLOT).getDefinition().getId();
                                    player.qtyGlovesUpgrade = item.getAmount();
                                }
                                if (item.getId() == 18897 && player.getEquipment().get(Equipment.AMULET_SLOT).getDefinition().getName().toLowerCase().contains("occult")) {
                                    player.idAmuletUpgrade = player.getEquipment().get(Equipment.AMULET_SLOT).getDefinition().getId();
                                    player.qtyAmuletUpgrade = item.getAmount();
                                }
                                if (item.getId() == 4151 && player.getEquipment().hasWhip()) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 11235 && player.getEquipment().hasDarkBow()) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 15486 && player.getEquipment().hasStaffOfLight()) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 21063 && player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().contains("Osmumten")) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 21078 && player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().contains("Keris partisan")) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 765 && player.getEquipment().get(Equipment.SHIELD_SLOT).getDefinition().getName().contains("Elidinis")) {
                                    player.idShieldUpgrade = player.getEquipment().get(Equipment.SHIELD_SLOT).getDefinition().getId();
                                    player.qtyShieldUpgrade = item.getAmount();
                                }
                                if (item.getId() == 224271 && player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getName().contains("eitiznot")) {
                                    player.idHelmUpgrade = player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getId();
                                    player.qtyHelmUpgrade = item.getAmount();
                                }
                                if (item.getId() == 6746 && player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().contains("Arclight")) {
                                    player.idWeaponUpgrade = player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getId();
                                    player.qtyWeaponUpgrade = item.getAmount();
                                }
                                if (item.getId() == 21070 && player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getName().contains("Masori mask (f)")) {
                                    player.idHelmUpgrade = player.getEquipment().get(Equipment.HEAD_SLOT).getDefinition().getId();
                                    player.qtyHelmUpgrade = item.getAmount();
                                }
                                if (item.getId() == 21071 && player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getName().contains("Masori body (f)")) {
                                    player.idBodyUpgrade = player.getEquipment().get(Equipment.BODY_SLOT).getDefinition().getId();
                                    player.qtyBodyUpgrade = item.getAmount();
                                }
                                if (item.getId() == 21072 && player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getName().contains("Masori chaps (f)")) {
                                    player.idLegsUpgrade = player.getEquipment().get(Equipment.LEG_SLOT).getDefinition().getId();
                                    player.qtyLegsUpgrade = item.getAmount();
                                }

                                if (player.getEquipment().contains(item.getId())) {
                                    if (player.getEquipment().getSlot(item.getId()) == 0) {
                                        player.idHelmUpgrade = item.getId();
                                        player.qtyHelmUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 2) {
                                        player.idAmuletUpgrade = item.getId();
                                        player.qtyAmuletUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 3) {
                                        player.idWeaponUpgrade = item.getId();
                                        player.qtyWeaponUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 4) {
                                        player.idBodyUpgrade = item.getId();
                                        player.qtyBodyUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 5) {
                                        player.idShieldUpgrade = item.getId();
                                        player.qtyShieldUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 7) {
                                        player.idLegsUpgrade = item.getId();
                                        player.qtyLegsUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 9) {
                                        player.idGlovesUpgrade = item.getId();
                                        player.qtyGlovesUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 10) {
                                        player.idBootsUpgrade = item.getId();
                                        player.qtyBootsUpgrade = item.getAmount();
                                    } else if (player.getEquipment().getSlot(item.getId()) == 12) {
                                        player.idRingUpgrade = item.getId();
                                        player.qtyRingUpgrade = item.getAmount();
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "No collections found!", e);
            collections = new HashMap<>();
        }
    }

    public int getItemUpgradeTier(int itemToUpgrade) {
        try {
            ArrayList<Integer> upgrades = collectionNPCS.get(CollectionLog.CollectionTabType.UPGRADES);

            int itemToUpgradeTier = -1;

            for (Integer upgrade : upgrades) {
                if (getCollections().containsKey(upgrade + "")) {
                    ArrayList<Item> itemsObtained = getCollections().get(upgrade + "");
                    if (itemsObtained != null) {
                        for (Item item : itemsObtained) {
                            if (item.getId() == itemToUpgrade) {
                                itemToUpgradeTier = item.getAmount();
                                break;
                            }
                        }
                    }
                }
                if (itemToUpgradeTier != -1)
                    break;
            }
            return itemToUpgradeTier;
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "No collections found!", e);
            collections = new HashMap<>();
            return 0;
        }
    }

    public void checkCompletedLogs() {
        if (collectionNPCS == null || collectionNPCS.isEmpty()) {
            return;
        }

        int completed = 0;
        for (CollectionTabType type : CollectionTabType.values())
            completed += getCompletedForType(type);

        player.completedLogs = completed;
    }

    private int getCompletedForType(CollectionTabType type) {
        ArrayList<Integer> npcs = collectionNPCS.get(type);
        if (npcs == null) {
            return 0;
        }

        int completed = 0;
        for (Integer npc : npcs) {
            if (getCollections().containsKey(npc + "")) {
                ArrayList<Item> itemsObtained = getCollections().get(npc + "");
                if (itemsObtained != null) {
                    int obtainedItems = 0;
                    for (Item item : itemsObtained) {
                        if (item.getAmount() > 0)
                            obtainedItems++;
                    }
                    if (obtainedItems == itemsObtained.size())
                        completed++;
                }
            }
        }
        return completed;
    }


    public enum CustomCollection {
        SkillingPets(-7, "Skilling Pets"),
        MysteryBox(-8, "Mystery Box"),
        EasyClues(-9, "Easy Clues"),
        MediumClues(-10, "Medium Clues"),
        HardClues(-11, "Hard Clues"),
        EliteClues(-12, "Elite Clues"),
        MasterClues(-13, "Master Clues"),
        GenericClues(-14, "Generic Clues"),
        Wintertodt(-15, "Wintertodt"),
        BlastMine(-16, "Blast Mine"),
        Slayer(-20, "Slayer"),
        SUPERIOR_SLAYER(-22, "Superior Slayer"),
        COX(-1, "Chambers of Xeric"),
        TOB(-2, "Theatre of Blood"),
        BARROWS(-3, "Barrows"),
        GWD(-4, "GWD Raid"),
        CHAOS(-5, "Chaos Raid"),
        SHR(-6, "Stronghold Raids"),
        LOW_UNIQUES(-101, "Low Uniques"),
        MEDIUM_UNIQUES(-102, "Medium Uniques"),
        HIGH_UNIQUES(-103, "High Uniques"),
        LEGENDARY_UNIQUES(-104, "Legendary Uniques"),
        MASTER_UNIQUES(-105, "Master Uniques"),
        CUSTOM_UNIQUES(-106, "Custom Uniques"),
        UNTRADEABLES(-107, "Untradeables"),
        WILDY_UNIQUES(-108, "Wildy Uniques");

        private int id;
        private String name;

        CustomCollection(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    /**
     * Different tabs within interface
     */
    public enum CollectionTabType {
        BOSSES, WILDERNESS, RAIDS, OTHER, UPGRADES
    }

}

