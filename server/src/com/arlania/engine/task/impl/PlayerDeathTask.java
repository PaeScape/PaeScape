package com.arlania.engine.task.impl;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.HighScores;
import com.arlania.engine.task.Task;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Represents a player's death task, through which the process of dying is handled,
 * the animation, dropping items, etc.
 *
 * @author relex lawl, redone by Gabbe.
 */

public class PlayerDeathTask extends Task {

    /**
     * The PlayerDeathTask constructor.
     *
     * @param player The player setting off the task.
     */
    public PlayerDeathTask(Player player) {
        super(1, player, false);
        this.player = player;
    }

    private Player player;
    private int ticks = 5;
    private final boolean dropItems = true;
    Position oldPosition;
    Location loc;
    ArrayList<Item> itemsToKeep = null;
    NPC death;

    @Override
    public void execute() {
        if (player == null) {
            stop();
            return;
        }
        try {
            switch (ticks) {
                case 5:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getMovementQueue().setLockMovement(true).reset();
                    break;
                case 3:
                    player.performAnimation(new Animation(0x900));
                    player.getPacketSender().sendMessage("Oh dear, you are dead!");
                    player.getPacketSender().sendMessage("You have 10 minutes to grab your stuff before other players can!");
                    this.death = getDeathNpc(player);
                    break;
                case 1:
                    this.oldPosition = player.getPosition().copy();
                    this.loc = player.getLocation();

                    if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
                        HighScores.toggleDeath(player);
                        World.sendMessage("status", "<img=34> @red@" + player.getUsername() + " has lost their Hardcore Ironman status!");
                        GameMode.set(player, GameMode.IRONMAN, false);
                        player.getPacketSender().sendIronmanMode(GameMode.IRONMAN.ordinal());
                    }

                    if (loc != Location.GWD_RAID && loc != Location.CHAOS_RAIDS && loc != Location.INSTANCEDBOSSES && loc != Location.TEKTON && loc != Location.SKELETAL_MYSTICS && loc != Location.OLM && loc != Location.PESTILENT_BLOAT && loc != Location.MAIDEN_SUGADINTI && loc != Location.VERZIK_VITUR && loc != Location.FREE_FOR_ALL_ARENA && loc != Location.FREE_FOR_ALL_WAIT && loc != Location.SOULWARS && loc != Location.FIGHT_PITS && loc != Location.FIGHT_PITS_WAIT_ROOM && loc != Location.FIGHT_CAVES && loc != Location.RECIPE_FOR_DISASTER && loc != Location.GRAVEYARD && loc != Location.NIGHTMARE && loc != Location.MAZE_RANDOM && loc != Location.ZULRAH && loc != Location.INSTANCED_SLAYER) {
                        Player killer = player.getCombatBuilder().getKiller(true);


                        if (killer != null) {

                            String exchangeLog = "[PVP] " + killer.getUsername() + " killed " + player.getUsername() + " at [" + player.getPosition().getX() + ", " + player.getPosition().getY() + ", " + player.getPosition().getZ() + "]";

                            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                                new MessageBuilder().append(exchangeLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.PLAYER_KILLS_CH).get());

                            killer.getPlayerKillingAttributes().add(player);
                            killer.getPlayerKillingAttributes().setPlayerKills(killer.getPlayerKillingAttributes().getPlayerKills() + 1);
                            killer.getPlayerKillingAttributes().setPlayerKillStreak(killer.getPlayerKillingAttributes().getPlayerKillStreak() + 1);
                            player.getPlayerKillingAttributes().setPlayerDeaths(player.getPlayerKillingAttributes().getPlayerDeaths() + 1);
                            player.getPlayerKillingAttributes().setPlayerKillStreak(0);
                            player.getPointsHandler().refreshPanel();

                            if(killer.getGameMode() == GameMode.NORMAL && player.canTransferWealth()) {
                                killer.setMoneyInPouch(killer.getMoneyInPouch() + player.wildRisk);
                                killer.getPacketSender().sendString(8135, "" + killer.getMoneyInPouch());
                                killer.getPacketSender().sendMessage("You received " + player.wildRisk + " coins for killing " + player.getUsername() + "!");
                            }
                            player.wildRisk = 0;
                            player.wildBoost = 0;
                        }

                        player.setAutocastSpell(null);

                    }

                    player.getPacketSender().sendInterfaceRemoval();
                    player.setEntityInteraction(null);
                    player.getMovementQueue().setFollowCharacter(null);
                    player.getCombatBuilder().cooldown(false);
                    player.setTeleporting(false);
                    player.setWalkToTask(null);
                    player.getSkillManager().stopSkilling();
                    break;
                case 0:
                    if (death != null) {
                        World.deregister(death);
                    }


                    player.moveTo(GameSettings.DEFAULT_POSITION);

                    player.restart();
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    loc.onDeath(player);
                    player.getInventory().refreshItems();
                    player = null;
                    oldPosition = null;
                    stop();
                    break;
            }
            ticks--;
        } catch (Exception e) {
            setEventRunning(false);
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            if (player != null) {
                player.moveTo(new Position(3172, 5727, 0));
                player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
            }
        }
    }

    public static NPC getDeathNpc(Player player) {
        NPC death = new NPC(2862, new Position(player.getPosition().getX() + 1, player.getPosition().getY() + 1));
        World.register(death);
        death.setEntityInteraction(player);
        death.performAnimation(new Animation(401));
        death.forceChat(randomDeath(player.getUsername()));
        return death;
    }


    public static String randomDeath(String name) {
        switch (RandomUtility.inclusiveRandom(8)) {
            case 0:
                return "There is no escape, " + Misc.formatText(name)
                        + "...";
            case 1:
                return "Muahahahaha!";
            case 2:
                return "You belong to me!";
            case 3:
                return "Beware mortals, " + Misc.formatText(name)
                        + " travels with me!";
            case 4:
                return "Your time here is over, " + Misc.formatText(name)
                        + "!";
            case 5:
                return "Now is the time you die, " + Misc.formatText(name)
                        + "!";
            case 6:
                return "I claim " + Misc.formatText(name) + " as my own!";
            case 7:
                return Misc.formatText(name) + " is mine!";
            case 8:
                return "Let me escort you back to Edgeville, "
                        + Misc.formatText(name) + "!";
            case 9:
                return "I have come for you, " + Misc.formatText(name)
                        + "!";
        }
        return "";
    }

}
