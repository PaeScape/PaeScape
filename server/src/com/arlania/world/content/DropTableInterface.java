package com.arlania.world.content;

import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.model.input.impl.EnterItemDropSearch;
import com.arlania.model.input.impl.EnterNpcDropSearch;
import com.arlania.net.packet.PacketSender;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

public class DropTableInterface {
    //show interface - 24000
    //npc search button 24007
    //item search button 19687
    //results list ids clickable text - 24010 - 24109
    //item sprites - 24115-24194
    //item names - 24195-24174
    //item amounts - 24275-24354
    //item avg kills - 24355-24434

    private final Player player;
    private List<NpcDefinition> npcResults = new ArrayList<>();
    private List<ItemDefinition> itemResults = new ArrayList<>();
    private boolean searchingNpcs;
    private static final int ROWS = 96;

    public DropTableInterface(Player player) {
        this.player = player;
    }

    public void open(boolean showBlanks) {
        if (showBlanks) {
            sendBlanks();
        }
        player.getPacketSender().sendInterface(24000);
    }

    public void open(boolean showBlanks, boolean showAllDrops) {
        if (showBlanks) {
            sendBlanks();
        }
        searchNpc("");
        player.getPacketSender().sendInterface(24000);
    }

    public void sendBlanks() {
        PacketSender ps = player.getPacketSender();
        for (int i = 0, j = 24115; i < ROWS; i++) {
            ps.sendItemOnInterface(j++, -1, 0);
            ps.sendString(j++, "");
            ps.sendString(j++, "");
            ps.sendString(j++, "");
        }
        for (int i = 0; i < 100; i++) {
            ps.sendString(24010 + i, "");
        }
        this.itemResults = new ArrayList<>();
        this.npcResults = new ArrayList<>();
    }

    private static boolean listContains(List<String> list, String search) {
        for (String name : list) {
            if (name.equals(search))
                return true;
        }
        return false;
    }

    public void searchItem(String itemName) {
        searchingNpcs = false;
        itemResults = new ArrayList<>(128);
        Set<Integer> items = new HashSet<>(128);
        NPCDrops.dropDump.forEach(dropEntry -> {
            NPCDrops.NpcDropItem[] drops = dropEntry.getDropList();
            for (int i = 0; i < drops.length; i++) {
                NPCDrops.NpcDropItem drop = drops[i];
                ItemDefinition itemDef = ItemDefinition.forId(drop.getId());
                if (StringUtils.containsIgnoreCase(itemDef.getName(), itemName) && !items.contains(itemDef.getId())) {
                    itemResults.add(itemDef);
                    items.add(itemDef.getId());
                    items.add(itemDef.isNoted() ? Item.getUnNoted(itemDef.getId()) : Item.getNoted(itemDef.getId()));
                }
            }
        });

        player.getPacketSender().setScrollBar(24009, 0);
        player.getPacketSender().sendScrollMaxChange(24009, 6 + Math.min(itemResults.size(), 100) * 14);
        int i = 24010;
        for (ItemDefinition def : itemResults) {
            player.getPacketSender().sendString(i, def.getName());
            i++;
            if (i >= 24109) {
                player.sendMessage("Narrow your search to see all the items.");
                break;
            }
        }
        for (; i < 24109; i++) {
            player.getPacketSender().sendString(i, "");
        }
        if (itemResults.size() == 1) {
            showDropsForItem(24010);
        }
    }

    public void searchNpc(String npcName) {
        searchingNpcs = true;
        npcResults = new ArrayList<>(100);
        NPCDrops.dropDump.forEach(npcDrop -> {
            int[] npcIds = npcDrop.getNpcIds();
            List<String> names = new LinkedList<>();
            for (int i = 0; i < npcIds.length; i++) {
                NpcDefinition def = NpcDefinition.forId(npcIds[i]);
                if (def == null)
                    continue;
                if (StringUtils.containsIgnoreCase(def.getName(), npcName) && !listContains(names, def.getName())) {
                    npcResults.add(def);
                    names.add(def.getName());
                }
            }
        });

        player.getPacketSender().setScrollBar(24009, 0);
        player.getPacketSender().sendScrollMaxChange(24009, 6 + Math.min(npcResults.size(), 100) * 14);
        int i = 24010;
        for (NpcDefinition def : npcResults) {
            player.getPacketSender().sendString(i, def.getName());
            i++;
            if (i >= 24109) {
                player.sendMessage("Narrow your search to see all the npcs.");
                break;
            }
        }
        for (; i < 24109; i++) {
            player.getPacketSender().sendString(i, "");
        }
        if (npcResults.size() == 1) {
            showDropsForNpc(24010);
        }
    }

    public void showDropsForItem(int clickedId) {
        if (itemResults == null)
            return;
        int index = clickedId - 24010;
        if (index >= itemResults.size())
            return;

        player.getPacketSender().sendString(24006, "Npcs that drop " + itemResults.get(index).getName());
        sendNpcs(itemResults.get(index));
    }

    public void showDropsForNpc(int clickedId) {
        if (npcResults == null)
            return;
        int index = clickedId - 24010;
        if (index >= npcResults.size())
            return;
        NPCDrops drops = NPCDrops.forId(npcResults.get(index).getId());
        player.getPacketSender().sendString(24006, "Loot from " + npcResults.get(index).getName());
        sendDrops(drops);
    }

    public void showDropsForNpcId(int npcId) {
        NPCDrops drops = NPCDrops.forId(npcId);
        if (drops != null) {
            String name = NpcDefinition.forId(npcId).getName();
            if (name != null)
                player.getPacketSender().sendString(24006, "Loot from " + name);
            player.getDropTableInterface().open(true);
            sendDrops(drops);
        } else {
            player.sendMessage("No drops found for that npc.");
        }
    }

    private void sendNpcs(ItemDefinition itemDef) {
        int otherId = itemDef.isNoted() ? Item.getUnNoted(itemDef.getId()) : Item.getNoted(itemDef.getId());
        List<DropRow> dropRows = new ArrayList<>(ROWS);
        for (NPCDrops npcDrops : NPCDrops.dropDump) {
            NPCDrops.NpcDropItem[] drops = npcDrops.getDropList();
            for (NPCDrops.NpcDropItem drop : drops) {
                if (drop.getId() == itemDef.getId() || drop.getId() == otherId) {
                    double avgKills = drop.getChance().getRandom();
                    Set<String> names = new HashSet<>((int) (npcDrops.getNpcIds().length * 1.25));
                    for (int npcId : npcDrops.getNpcIds()) {
                        NpcDefinition def = NpcDefinition.forId(npcId);
                        if (def == null || def.getName() == null) {
                            System.err.println(npcId + " is a drop npc but is null or has null name.");
                            continue;
                        }
                        String thisName = def.getName() + def.getCombatLevel();
                        if (names.contains(thisName))
                            continue;
                        else
                            names.add(thisName);
                        String color = Misc.combatDiffColor(player.getSkillManager().getCombatLevel(), def.getCombatLevel());
                        DropRow dropRow = new DropRow(drop.getId(), itemDef.isStackable() ? drop.getCount()[0] : 1, def.getName() + color + " (Lvl: " + def.getCombatLevel() + ")", drop.getCount()[0], avgKills);
                        dropRows.add(dropRow);
                    }
                }
            }
        }

        DecimalFormat format = new DecimalFormat("#.##");
        Collections.sort(dropRows, Comparator.comparingDouble(row -> row.avgKillsPerItem));
        PacketSender packetSender = player.getPacketSender();
        packetSender.setScrollBar(24008, 0);
        packetSender.sendScrollMaxChange(24008, Math.max(dropRows.size() * 32, 258));
        int j = 24115;
        for (int i = 0, n = Math.min(dropRows.size(), ROWS); i < n; i++) {
            DropRow row = dropRows.get(i);
            packetSender.sendItemOnInterface(j++, row.itemId, row.spriteAmount);
            packetSender.sendString(j++, row.nameText);
            packetSender.sendString(j++, Misc.insertCommasToNumber("" + row.amount));
            packetSender.sendString(j++, format.format(row.avgKills));
        }
        for (int i = dropRows.size(); i < ROWS; i++) {
            packetSender.sendItemOnInterface(j++, -1, 0);
            packetSender.sendString(j++, "");
            packetSender.sendString(j++, "");
            packetSender.sendString(j++, "");
        }
    }

    private class DropRow {

        private final int itemId;
        private final int spriteAmount;
        private final int amount;
        private final double avgKills;
        private final String nameText;
        private final double avgKillsPerItem;

        public DropRow(int itemId, int spriteAmount, String nameText, int amount, double avgKills) {
            this.itemId = itemId;
            this.spriteAmount = spriteAmount;
            this.nameText = nameText;
            this.amount = amount;
            this.avgKills = avgKills;
            this.avgKillsPerItem = avgKills / amount;
        }
    }

    private void sendDrops(NPCDrops drops) {
        List<NPCDrops.NpcDropItem> items = new ArrayList<>(Arrays.asList(drops.getDropList()));
        items.sort(Comparator.comparingInt(o -> -o.getChance().getRandom()));
        DecimalFormat format = new DecimalFormat("#.##");
        PacketSender packetSender = player.getPacketSender();
        int j = 24115;
        int i = 0;
        int n = Math.min(ROWS, items.size());
        packetSender.setScrollBar(24008, 0);
        packetSender.sendScrollMaxChange(24008, Math.max(n * 32, 258));
        for (NPCDrops.NpcDropItem item : items) {
            if (i >= n) {
                break;
            }
            packetSender.sendItemOnInterface(j++, item.getId(), ItemDefinition.forId(item.getId()).isStackable() ? item.getCount()[0] : 1);
            packetSender.sendString(j++, ItemDefinition.forId(item.getId()).getName());
            packetSender.sendString(j++, Misc.insertCommasToNumber("" + item.getCount()[0]));
            double avgKills = item.getChance().getRandom();
            packetSender.sendString(j++, avgKills == 1 ? "Always" : (format.format(avgKills)));
            i++;
        }

        //fill rest with blanks
        for (; i < ROWS; i++) {
            packetSender.sendItemOnInterface(j++, -1, 0);
            packetSender.sendString(j++, "");
            packetSender.sendString(j++, "");
            packetSender.sendString(j++, "");
        }
    }

    public boolean handleButtonClick(int id) {
        if (id >= 24010 && id <= 24109) {
            if (searchingNpcs)
                showDropsForNpc(id);
            else
                showDropsForItem(id);
            return true;
        }
        if (id == 24002) {
            player.getPacketSender().sendInterfaceRemoval();
            player.getPacketSender().sendString(24006, "Loot Viewer");
            player.setDropTableInterface(null); //garbage collection
            return true;
        }
        if (id == 24007) {
            player.setInputHandling(new EnterNpcDropSearch());
            player.getPacketSender().sendEnterInputPrompt("What npc would you like to search for?");
            return true;
        }
        if (id == 19687) {
            player.setInputHandling(new EnterItemDropSearch());
            player.getPacketSender().sendEnterInputPrompt("What item would you like to search for?");
            return true;
        }
        return false;
    }
}
