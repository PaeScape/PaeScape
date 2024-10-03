package com.arlania.world.content;

import com.arlania.GameSettings;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;

public class WellOfEvents {


    public static void lookDownWell(Player player) {
        if (GlobalEventHandler.globalEventTimeRemaining > 0) {
            return;
        }
        DialogueManager.start(player, new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"It looks like the well could use an Event token."};
            }

            @Override
            public int npcId() {
                return 802;
            }

            @Override
            public Dialogue nextDialogue() {
                return DialogueManager.getDialogues().get(75);
            }

        });
    }

    public static boolean checkFull(Player player) {
        if (GlobalEventHandler.globalEventTimeRemaining > 0) {
            DialogueManager.start(player, new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.NPC_STATEMENT;
                }

                @Override
                public DialogueExpression animation() {
                    return DialogueExpression.NORMAL;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"A global event is running!"};
                }

                @Override
                public int npcId() {
                    return 802;
                }

                @Override
                public Dialogue nextDialogue() {
                    return DialogueManager.getDialogues().get(75);
                }

            });
            return true;
        }
        return false;
    }

    public static void donate(Player player, int itemID) {

        /*if (GlobalEventHandler.globalEventTime > 0) {
            DialogueManager.sendStatement(player, "You can't donate while an event is running.");
            return;
        }
        if (GameSettings.wellOfEventsCooldown < 30000 && NobilitySystem.getRank(player) != -1) {
            int eventTime = GameSettings.wellOfEventsCooldown / 100;
            int timeLeft = 300 - eventTime;
            player.getPacketSender().sendMessage("The Well of Events has a cooldown timer with " + timeLeft + " minutes remaining.");
            return;
        }*/

        int numberOfEventsToTrigger = 0;

        if (Arrays.stream(GameSettings.soloGlobalEvents).anyMatch(soloGlobalEvent -> soloGlobalEvent == itemID)) {
            numberOfEventsToTrigger = 1;
        }
        if (Arrays.stream(GameSettings.duoGlobalEvents).anyMatch(duoGlobalEvent -> duoGlobalEvent == itemID)) {
            numberOfEventsToTrigger = 2;
        }

        if (numberOfEventsToTrigger == 0) {
            player.getPacketSender().sendMessage("@red@You must use an event token on the Event Well.");
            return;
        }


        //DialogueManager.sendStatement(player, "Thank you for your donation.");

        //activate random event(s)
        GlobalEventHandler.useTokenOnWell(player, numberOfEventsToTrigger, itemID);

    }

}
