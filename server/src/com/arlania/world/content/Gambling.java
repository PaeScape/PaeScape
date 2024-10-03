package com.arlania.world.content;

import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.model.Graphic;
import com.arlania.model.movement.MovementQueue;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Gambling {

    public static void rollDice(Player player) {
        if (player.getClanChatName() == null) {
            player.getPacketSender().sendMessage("You need to be in a clanchat channel to roll a dice.");
            return;
        } else if (player.getClanChatName().equalsIgnoreCase("help")) {
            player.getPacketSender().sendMessage("You can't roll a dice in this clanchat channel!");
            return;
        }
        if (!player.getClickDelay().elapsed(5000)) {
            player.getPacketSender().sendMessage("You must wait 5 seconds between each dice cast.");
            return;
        }
        player.getMovementQueue().reset();
        player.performAnimation(new Animation(11900));
        player.performGraphic(new Graphic(2075));
        ClanChatManager.sendMessage(player.getCurrentClanChat(), "@bla@[ClanChat] @whi@" + player.getUsername() + " just rolled @bla@" + RandomUtility.inclusiveRandom(100) + "@whi@ on the percentile dice.");
        player.getClickDelay().reset();
    }

    public static void plantSeed(Player player) {
        if (!player.getClickDelay().elapsed(2000))
            return;
        for (NPC npc : player.getLocalNpcs()) {
            if (npc != null && npc.getPosition().equals(player.getPosition())) {
                player.getPacketSender().sendMessage("You cannot plant a seed right here.");
                return;
            }
        }
        if (CustomObjects.objectExists(player.getPosition().copy())) {
            player.getPacketSender().sendMessage("You cannot plant a seed right here.");
            return;
        }
        FlowersData flowers = FlowersData.generate();
        final GameObject flower = new GameObject(flowers.objectId, player.getPosition().copy());
        player.getMovementQueue().reset();
        player.getInventory().delete(299, 1);
        player.performAnimation(new Animation(827));
        player.getPacketSender().sendMessage("You plant the seed..");
        player.getMovementQueue().reset();
        player.setDialogueActionId(42);
        player.setInteractingObject(flower);
        DialogueManager.start(player, 78);
        MovementQueue.stepAway(player);
        CustomObjects.globalObjectRemovalTask(flower, 90);
        player.setPositionToFace(flower.getPosition());
        player.getClickDelay().reset();
    }


    public enum FlowersData {
        PASTEL_FLOWERS(2980, 2460),
        RED_FLOWERS(2981, 2462),
        BLUE_FLOWERS(2982, 2464),
        YELLOW_FLOWERS(2983, 2466),
        PURPLE_FLOWERS(2984, 2468),
        ORANGE_FLOWERS(2985, 2470),
        RAINBOW_FLOWERS(2986, 2472),

        WHITE_FLOWERS(2987, 2474),
        BLACK_FLOWERS(2988, 2476);

        FlowersData(int objectId, int itemId) {
            this.objectId = objectId;
            this.itemId = itemId;
        }

        public int objectId;
        public int itemId;

        public static FlowersData forObject(int object) {
            for (FlowersData data : FlowersData.values()) {
                if (data.objectId == object)
                    return data;
            }
            return null;
        }

        public static FlowersData generate() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return values()[RandomUtility.inclusiveRandom(6)];//
            } else {
                return RandomUtility.inclusiveRandom(3) == 1 ? WHITE_FLOWERS : BLACK_FLOWERS;
            }
        }

        public static FlowersData generate2() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return BLUE_FLOWERS;
            } else {
                return BLUE_FLOWERS;
            }
        }

        public static FlowersData generate3() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return PURPLE_FLOWERS;
            } else {
                return PURPLE_FLOWERS;
            }
        }

        public static FlowersData generate4() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return RED_FLOWERS;
            } else {
                return RED_FLOWERS;
            }
        }

        public static FlowersData generate5() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return ORANGE_FLOWERS;
            } else {
                return ORANGE_FLOWERS;
            }
        }

        public static FlowersData generate6() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return RAINBOW_FLOWERS;
            } else {
                return RAINBOW_FLOWERS;
            }
        }

        public static FlowersData generate7() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return PASTEL_FLOWERS;
            } else {
                return PASTEL_FLOWERS;
            }
        }

        public static FlowersData generate8() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return BLACK_FLOWERS;
            } else {
                return BLACK_FLOWERS;
            }
        }

        public static FlowersData generate9() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return WHITE_FLOWERS;
            } else {
                return WHITE_FLOWERS;
            }
        }

        public static FlowersData generate10() {
            double RANDOM = (java.lang.Math.random() * 100);
            if (RANDOM >= 1) {
                return YELLOW_FLOWERS;
            } else {
                return YELLOW_FLOWERS;
            }
        }
    }
}
