package com.arlania.world.content;

import com.arlania.GameServer;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.world.entity.impl.player.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;

public class DropLog {

    public static void submit(Player player, DropLogEntry[] drops) {
        for (DropLogEntry drop : drops) {
            if (drop != null)
                submit(player, drop);
        }
    }

    public static void submit(Player player, DropLogEntry drop) {
        int index = getIndex(player, drop);
        if (index >= 0) {
            player.getDropLog().get(index).amount += drop.amount;
        } else {
            if (addItem(player, drop)) {
                player.getDropLog().add(drop);
            }
        }
    }

    public static void openRare(Player player) {
        try {
            /* RESORT THE KILLS */
            Collections.sort(player.getDropLog(), new Comparator<DropLogEntry>() {
                @Override
                public int compare(DropLogEntry drop1, DropLogEntry drop2) {
                    ItemDefinition def1 = ItemDefinition.forId(drop1.item);
                    ItemDefinition def2 = ItemDefinition.forId(drop2.item);
                    int value1 = def1.getValue() * drop1.amount;
                    int value2 = def2.getValue() * drop2.amount;
                    if (value1 > value2) {
                        return -1;
                    } else if (value2 > value1) {
                        return 1;
                    } else {
                        if (def2.getName().compareTo(def2.getName()) > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            });
            /* SHOW THE INTERFACE */
            resetInterface(player);
            player.getPacketSender().sendInterface(25250);
            int index = -1;
            index++;
            for (DropLogEntry entry : player.getDropLog()) {
                if (entry.rareDrop) {

                    player.getPacketSender().sendString(25261 + index, "@or1@ " + ItemDefinition.forId(entry.item).getName() + ": @gre@x" + entry.amount);
                    index++;
                }
            }
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static void open(Player player) {
        try {
            /* RESORT THE KILLS */
            Collections.sort(player.getDropLog(), new Comparator<DropLogEntry>() {
                @Override
                public int compare(DropLogEntry drop1, DropLogEntry drop2) {
                    ItemDefinition def1 = ItemDefinition.forId(drop1.item);
                    ItemDefinition def2 = ItemDefinition.forId(drop2.item);
                    int value1 = def1.getValue() * drop1.amount;
                    int value2 = def2.getValue() * drop2.amount;
                    if (value1 > value2) {
                        return -1;
                    } else if (value2 > value1) {
                        return 1;
                    } else {
                        if (def2.getName().compareTo(def2.getName()) > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            });
            /* SHOW THE INTERFACE */
            resetInterface(player);
            player.getPacketSender().sendInterface(65250);
            int index = -1;
            index++;
            for (DropLogEntry entry : player.getDropLog()) {
                player.getPacketSender().sendString(65261 + index, "@or1@ " + ItemDefinition.forId(entry.item).getName() + ":@gre@ x" + entry.amount);
                index++;
            }
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static void resetInterface(Player player) {
        for (int i = 8145; i < 8196; i++)
            player.getPacketSender().sendString(i, "");
        for (int i = 12174; i < 12224; i++)
            player.getPacketSender().sendString(i, "");
        player.getPacketSender().sendString(8136, "Close window");
    }

    public static boolean addItem(Player player, DropLogEntry drop) {
        if (player.getDropLog().size() >= 98) {
            DropLogEntry drop2 = player.getDropLog().get(player.getDropLog().size() - 1);
            int value1 = ItemDefinition.forId(drop.item).getValue() * drop.amount;
            int value2 = ItemDefinition.forId(drop2.item).getValue() * drop2.amount;
            if (value1 > value2) {
                player.getDropLog().remove(drop2);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static int getIndex(Player player, DropLogEntry drop) {
        for (int i = 0; i < player.getDropLog().size(); i++) {
            if (player.getDropLog().get(i).item == drop.item) {
                return i;
            }
        }
        return -1;
    }

    public static class DropLogEntry {

        public DropLogEntry(int item, int amount) {
            this.item = item;
            this.amount = amount;
            this.rareDrop = ItemDefinition.forId(item).getValue() > 200000 || NPCDrops.ItemDropAnnouncer.announce(item);
        }

        public int item;
        public int amount;
        public boolean rareDrop;
    }

}
