package com.arlania.world.content;

import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.entity.impl.player.Player;

public class PersonalEvent {

    static public void activate(Player player) {

        switch(player.personalEventType) {

            case "Accuracy":
                if(player.accuracyEventTimer > 0) {
                    player.personalEvent = true;
                    player.accuracyEvent = true;
                    player.playerEventTimer = player.accuracyEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "Accelerate":
                if(player.accelerateEventTimer > 0) {
                    player.personalEvent = true;
                    player.accelerateEvent = true;
                    player.playerEventTimer = player.accelerateEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "Drop Rate":
                if(player.droprateEventTimer > 0) {
                    player.personalEvent = true;
                    player.droprateEvent = true;
                    player.playerEventTimer = player.droprateEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "2x Boss PP":
                if(player.doubleBossPointEventTimer > 0) {
                    player.personalEvent = true;
                    player.doubleBossPointEvent = true;
                    player.playerEventTimer = player.doubleBossPointEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "2x Skiller PP":
                if(player.doubleSkillerPointsEventTimer > 0) {
                    player.personalEvent = true;
                    player.doubleSkillerPointsEvent = true;
                    player.playerEventTimer = player.doubleSkillerPointsEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "2x Slayer PP":
                if(player.doubleSlayerPointsEventTimer > 0) {
                    player.personalEvent = true;
                    player.doubleSlayerPointsEvent = true;
                    player.playerEventTimer = player.doubleSlayerPointsEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "Loaded":
                if(player.loadedEventTimer > 0) {
                    player.personalEvent = true;
                    player.loadedEvent = true;
                    player.playerEventTimer = player.loadedEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "2x Loot":
                if(player.doubleLootTimer > 0) {
                    player.personalEvent = true;
                    player.doubleLoot = true;
                    player.playerEventTimer = player.doubleLootTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "2x Exp":
                if(player.doubleExpEventTimer > 0) {
                    player.personalEvent = true;
                    player.doubleExpEvent = true;
                    player.playerEventTimer = player.doubleExpEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "Event Box":
                if(player.universalDropEventTimer > 0) {
                    player.personalEvent = true;
                    player.eventBoxEvent = true;
                    player.playerEventTimer = player.universalDropEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "Boss Kills":
                if(player.bossKillsEventTimer > 0) {
                    player.personalEvent = true;
                    player.bossKillsEvent = true;
                    player.playerEventTimer = player.bossKillsEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;
            case "Max Hit":
                if(player.maxHitEventTimer > 0) {
                    player.personalEvent = true;
                    player.maxHitEvent = true;
                    player.playerEventTimer = player.maxHitEventTimer;
                    player.getPacketSender().sendMessage("You activate your personal event!");
                } else {
                    player.getPacketSender().sendMessage("You don't have enough time to activate that personal event!");
                }
                break;



        }
    }


    static public void process(Player player) {

        //Can't have Personal Max Hit + Global Berserker
        if (player.maxHitEvent && GlobalEventHandler.effectActive(GlobalEvent.Effect.BERSERKER))  {
            player.maxHitEvent = false;
            player.getPacketSender().sendMessage("@red@You can't have Max Hit active during a Berserker event!");
        }

        if (player.personalEvent) {

            //End personal event if no time left
            if (player.playerEventTimer < 1) {
                endPersonal(player);
                return;
            }


            if (player.accuracyEvent && player.accuracyEventTimer > 0)
                player.accuracyEventTimer--;
            else if (player.accuracyEvent) {
                player.accuracyEvent = false;
                player.personalEvent = false;
                player.accuracyEventTimer = 0;
            }

            if (player.accelerateEvent && player.accelerateEventTimer > 0)
                player.accelerateEventTimer--;
            else if (player.accelerateEvent) {
                player.accelerateEvent = false;
                player.personalEvent = false;
                player.accelerateEventTimer = 0;
            }

            if (player.droprateEvent && player.droprateEventTimer > 0)
                player.droprateEventTimer--;
            else if (player.droprateEvent) {
                player.droprateEvent = false;
                player.personalEvent = false;
                player.droprateEventTimer = 0;
            }

            if (player.doubleBossPointEvent && player.doubleBossPointEventTimer > 0)
                player.doubleBossPointEventTimer--;
            else if (player.doubleBossPointEvent) {
                player.doubleBossPointEvent = false;
                player.personalEvent = false;
                player.doubleBossPointEventTimer = 0;
            }

            if (player.doubleSkillerPointsEvent && player.doubleSkillerPointsEventTimer > 0)
                player.doubleSkillerPointsEventTimer--;
            else if (player.doubleSkillerPointsEvent) {
                player.doubleSkillerPointsEvent = false;
                player.personalEvent = false;
                player.doubleSkillerPointsEventTimer = 0;
            }

            if (player.doubleSlayerPointsEvent && player.doubleSlayerPointsEventTimer > 0)
                player.doubleSlayerPointsEventTimer--;
            else if (player.doubleSlayerPointsEvent) {
                player.doubleSlayerPointsEvent = false;
                player.personalEvent = false;
                player.doubleSlayerPointsEventTimer = 0;
            }

            if (player.loadedEvent && player.loadedEventTimer > 0)
                player.loadedEventTimer--;
            else if (player.loadedEvent) {
                player.loadedEvent = false;
                player.personalEvent = false;
                player.loadedEventTimer = 0;
            }

            if (player.doubleLoot && player.doubleLootTimer > 0)
                player.doubleLootTimer--;
            else if (player.doubleLoot) {
                player.doubleLoot = false;
                player.personalEvent = false;
                player.doubleLootTimer = 0;
            }

            if (player.doubleExpEvent && player.doubleExpEventTimer > 0)
                player.doubleExpEventTimer--;
            else if (player.doubleExpEvent) {
                player.doubleExpEvent = false;
                player.personalEvent = false;
                player.doubleExpEventTimer = 0;
            }

            if (player.eventBoxEvent && player.universalDropEventTimer > 0)
                player.universalDropEventTimer--;
            else if (player.eventBoxEvent) {
                player.eventBoxEvent = false;
                player.personalEvent = false;
                player.universalDropEventTimer = 0;
            }

            if (player.bossKillsEvent && player.bossKillsEventTimer > 0)
                player.bossKillsEventTimer--;
            else if (player.bossKillsEvent) {
                player.bossKillsEvent = false;
                player.personalEvent = false;
                player.bossKillsEventTimer = 0;
            }

            if (player.maxHitEvent && player.maxHitEventTimer > 0)
                player.maxHitEventTimer--;
            else if (player.maxHitEvent) {
                player.maxHitEvent = false;
                player.personalEvent = false;
                player.maxHitEventTimer = 0;
            }
        }
    }

    public static void endPersonal(Player player) {

        player.personalEvent = false;
        player.accelerateEvent = false;
        player.accuracyEvent = false;
        player.doubleExpEvent = false;
        player.droprateEvent = false;
        player.maxHitEvent = false;
        player.doubleBossPointEvent = false;
        player.doubleLoot = false;
        player.doubleSkillerPointsEvent = false;
        player.doubleSlayerPointsEvent = false;
        player.eventBoxEvent = false;
        player.loadedEvent = false;
        player.bossKillsEvent = false;
        player.getPacketSender().sendMessage("@red@Your personal event has ended.");

    }


}
