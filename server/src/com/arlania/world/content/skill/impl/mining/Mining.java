package com.arlania.world.content.skill.impl.mining;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GameMode;
import com.arlania.model.GameObject;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.ShootingStar;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.skill.impl.mining.MiningData.Ores;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class Mining {

    public static void startMining(final Player player, final GameObject oreObject) {
        player.getPacketSender().sendInterfaceRemoval();
		/*if(!Locations.goodDistance(player.getPosition().copy(), oreObject.getPosition(), 1) && oreObject.getId() != 24444  && oreObject.getId() != 24445 && oreObject.getId() != 38660)
			return;*/
        if (player.busy() || player.getCombatBuilder().isBeingAttacked() || player.getCombatBuilder().isAttacking()) {
            player.getPacketSender().sendMessage("You cannot do that right now.");
            return;
        }
        if (player.getInventory().getFreeSlots() == 0) {
            player.getPacketSender().sendMessage("You do not have any free inventory space left.");
            return;
        }

        player.setInteractingObject(oreObject);
        player.setPositionToFace(oreObject.getPosition());
        final Ores o = MiningData.forRock(oreObject.getId());

        boolean givesGem = false;

        if (o.getGemOdds() != -1)
            if (RandomUtility.inclusiveRandom(1, o.getGemOdds()) == 1)
                givesGem = true;

        final boolean giveGem = givesGem;

        final int reqCycle = o == Ores.Runite ? 6 + RandomUtility.inclusiveRandom(2) : RandomUtility.inclusiveRandom(o.getTicks() - 1);
        if (o != null) {
            final int pickaxe = MiningData.getPickaxe(player);
            int miningLevel = player.getSkillManager().getCurrentLevel(Skill.MINING);

            if (player.Prodigy > 0)
                miningLevel = player.getSkillManager().getMaxLevel(Skill.MINING) + 6 + (3 * player.Prodigy);

            if (pickaxe > 0) {
                if (miningLevel >= o.getLevelReq()) {
                    final MiningData.Pickaxe p = MiningData.forPick(pickaxe);
                    if (miningLevel >= p.getReq()) {
                        player.performAnimation(new Animation(p.getAnim()));
                        final int delay = o.getTicks() - MiningData.getReducedTimer(player, p);
                        player.setCurrentTask(new Task(delay >= 2 ? delay : 1, player, false) {
                            int cycle = 0;
                            int actions = 0;

                            @Override
                            public void execute() {
                                if (player.getInteractingObject() == null || player.getInteractingObject().getId() != oreObject.getId()) {
                                    player.getSkillManager().stopSkilling();
                                    player.performAnimation(new Animation(65535));
                                    stop();
                                    return;
                                }

                                if (player.getInventory().getFreeSlots() == 0) {
                                    player.performAnimation(new Animation(65535));
                                    stop();
                                    player.getPacketSender().sendMessage("You do not have any free inventory space left.");
                                    return;
                                }

                                int qty = player.acceleratedResources();

                                if (cycle != reqCycle) {
                                    cycle++;
                                    player.performAnimation(new Animation(p.getAnim()));
                                }

                                if (giveGem && o != Ores.Rune_essence && o != Ores.Pure_essence && o != Ores.Dark_essence) {

                                    //zenyte
                                    if (o == Ores.CRASHED_STAR && RandomUtility.inclusiveRandom(500) == 1) {
                                        player.getInventory().add(20566, 1);
                                        player.getSkillManager().addExperience(Skill.MINING, RandomUtility.inclusiveRandom(player.getSkillManager().getCurrentLevel(Skill.MINING)) * 50);
                                        player.getPacketSender().sendMessage("You've found a Zenyte Shard!");
                                        World.sendMessage("drops", "<img=10><col=009966> " + player.getUsername() + " has just received a Zenyte Shard from the Crashed Star!");

                                        String discordMessage = player.getUsername() + " received a Zenyte Shard from the Crashed Star!";

                                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
                                    }

                                    //Onyx
                                    if ((o == Ores.CRASHED_STAR || o == Ores.Runite) && RandomUtility.inclusiveRandom(250) == 1) {
                                        player.getInventory().add(6571, 1);
                                        player.getSkillManager().addExperience(Skill.MINING, RandomUtility.inclusiveRandom(player.getSkillManager().getCurrentLevel(Skill.MINING)) * 25);
                                        player.getPacketSender().sendMessage("You've found an Uncut Onyx!");

                                        String rockType = o == Ores.CRASHED_STAR ? "Crashed Star" : "Runite Ore";

                                        World.sendMessage("drops", "<img=10><col=009966> " + player.getUsername() + " has just received an Uncut Onyx from a " + rockType + "!");

                                        String discordMessage = player.getUsername() + " received an Uncut Onyx from a " + rockType + "!";

                                        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                                            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.ANNOUNCE_CH).get());
                                    }

                                    int gemId = MiningData.RANDOM_GEMS[(int) (MiningData.RANDOM_GEMS.length * Math.random())];

                                    player.getSkillManager().addExperience(Skill.MINING, RandomUtility.inclusiveRandom(player.getSkillManager().getCurrentLevel(Skill.MINING)) * 10);

                                    if (player.getInventory().contains(18338)) {
                                        switch (gemId) {
                                            //1623, 1621, 1619, 1617, 1631
                                            case 1623:
                                                player.setBagSapphire(player.getBagSapphire() + 1);
                                                if (player.personalFilterGathering)
                                                    player.getPacketSender().sendMessage("Your gem bag collects a Sapphire!");
                                                break;
                                            case 1621:
                                                player.setBagEmerald(player.getBagEmerald() + 1);
                                                if (player.personalFilterGathering)
                                                    player.getPacketSender().sendMessage("Your gem bag collects a Emerald!");
                                                break;
                                            case 1619:
                                                player.setBagRuby(player.getBagRuby() + 1);
                                                if (player.personalFilterGathering)
                                                    player.getPacketSender().sendMessage("Your gem bag collects a Ruby!");
                                                break;
                                            case 1617:
                                                player.setBagDiamond(player.getBagDiamond() + 1);
                                                if (player.personalFilterGathering)
                                                    player.getPacketSender().sendMessage("Your gem bag collects a Diamond!");
                                                break;
                                            case 1631:
                                                player.setBagDragonstone(player.getBagDragonstone() + 1);
                                                if (player.personalFilterGathering)
                                                    player.getPacketSender().sendMessage("Your gem bag collects a Dragonstone!");
                                                break;
                                        }

                                    } else {
                                        player.getInventory().add(gemId, 1);
                                        player.getPacketSender().sendMessage("You've found a gem!");
                                    }

                                }

                                int clueChance = 400;
                                int clueAmt = 1;

                                if (player.Detective == 1)
                                    clueChance = (clueChance * (100 - (player.completedLogs / 4))) / 100;

                                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_CLUES))
                                    clueAmt = 2;

                                if (RandomUtility.inclusiveRandom(clueChance) == 1) {
                                    int randClue = RandomUtility.inclusiveRandom(20);

                                    if (randClue == 1)
                                        player.getInventory().add(220364, clueAmt);
                                    else if ((randClue > 1) && (randClue <= 4))
                                        player.getInventory().add(220362, clueAmt);
                                    else if ((randClue > 4) && (randClue <= 10))
                                        player.getInventory().add(220360, clueAmt);
                                    else
                                        player.getInventory().add(220358, clueAmt);
                                }

                                if (o == Ores.Crystal) {
                                    if (RandomUtility.inclusiveRandom(10) == 1) {
                                        player.getInventory().add(223877, qty);
                                    }
                                }


                                if (o == Ores.Gem) {
                                    if ((oreObject.getId() == 2111 || oreObject.getId() == 17004 || oreObject.getId() == 311380 || oreObject.getId() == 311381)
                                            && player.getSkillManager().getCurrentLevel(Skill.MINING) >= 40) {
                                        int[] gems = {1625, 1627, 1629, 1623, 1621, 1619, 1617, 1631};
                                        int gemId = gems[RandomUtility.inclusiveRandom(gems.length - 1)];
                                        player.getInventory().add(gemId, qty);
                                    }
                                }

                                int addxp = o.getXpAmount();

                                double bonusxp = 1;

                                if (player.getEquipment().contains(212013))
                                    bonusxp += .05;
                                if (player.getEquipment().contains(212014))
                                    bonusxp += .05;
                                if (player.getEquipment().contains(212015))
                                    bonusxp += .05;
                                if (player.getEquipment().contains(212016))
                                    bonusxp += .05;
                                if (player.getEquipment().contains(221345))
                                    bonusxp += .05;

                                addxp *= bonusxp;

                                if (cycle == reqCycle) {

                                    if (o.getItemId() != -1) {
                                        //else
                                        //player.getInventory().add(o.getItemId(), qty);

                                        player.getSkillManager().addExperience(Skill.MINING, addxp * qty);
                                    }

                                    if ((oreObject.getId() == 2111 || oreObject.getId() == 17004 || oreObject.getId() == 311380 || oreObject.getId() == 311381)
                                            && player.getSkillManager().getCurrentLevel(Skill.MINING) >= 40) {
                                        int[] gems = {1623, 1621, 1619, 1617, 1631};
                                        int gemId = gems[RandomUtility.inclusiveRandom(gems.length - 1)];
                                        player.getInventory().add(gemId, 1);
                                    }

                                }

                                if (player.getSkillManager().hasInfernalTools(Skill.MINING, Skill.SMITHING) && o.getProductId() != -1)  //verifies there is a product to make
                                {
                                    if (player.checkAchievementAbilities(player, "gatherer")) {
                                        player.getInventory().add(o.getProductId() + 1, qty);
                                    } else if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester) {
                                        player.getInventory().add(o.getProductId() + 1, qty);
                                    } else {
                                        player.getInventory().add(o.getProductId(), qty);
                                    }

                                    player.getSkillManager().addExperience(Skill.MINING, addxp * qty);
                                    player.getSkillManager().addExperience(Skill.SMITHING, o.getSmithingXp() * qty / 2);

                                    if (player.personalFilterAdze) {
                                        player.getPacketSender().sendMessage("Your infernal tool smelts the ore, granting you Smithing experience.");
                                    }

                                    if(o.getProductId() == 2351)
                                        player.getAchievementTracker().progress(AchievementData.SMELT_25_IRON_BARS, qty);
                                    if(o.getProductId() == 2353)
                                        player.getAchievementTracker().progress(AchievementData.SMELT_100_STEEL_BARS, qty);
                                    if(o.getProductId() == 2359)
                                        player.getAchievementTracker().progress(AchievementData.SMELT_250_MITHRIL_BARS, qty);
                                    if(o.getProductId() == 2361)
                                        player.getAchievementTracker().progress(AchievementData.SMELT_500_ADDY_BARS, qty);
                                    if(o.getProductId() == 2363)
                                        player.getAchievementTracker().progress(AchievementData.SMELT_1000_RUNE_BARS, qty);


                                } else {

                                    //collecting rocks

                                    if (oreObject.getId() == 334773 && player.getSkillManager().getCurrentLevel(Skill.MINING) > 84)
                                        player.getInventory().add(7938, qty);
                                    else if (oreObject.getId() == 334773 && player.getSkillManager().getCurrentLevel(Skill.MINING) > 29)
                                        player.getInventory().add(7936, qty);
                                    else if ((oreObject.getId() == 326679 || oreObject.getId() == 326680) && player.getSkillManager().getCurrentLevel(Skill.MINING) > 29)
                                        player.getInventory().add(212011, qty);
                                    else if ((oreObject.getId() == 315250 && player.getSkillManager().getCurrentLevel(Skill.MINING) >= 99)) {

                                        if (RandomUtility.inclusiveRandom(1, 40) == 3) {
                                            player.getPacketSender().sendMessage("@red@You stop mining Amethyst.");
                                            player.getSkillManager().stopSkilling();
                                            return;
                                        }

                                        if(player.checkAchievementAbilities(player, "gatherer")) {
                                            player.getInventory().add(221348, qty);
                                        } else if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester) {
                                            player.getInventory().add(221348, qty);
                                        } else {
                                            player.getInventory().add(221347, qty);
                                        }
                                    }else {
                                        if (player.checkAchievementAbilities(player, "gatherer") && !ItemDefinition.forId(o.getItemId()).isStackable()) {
                                            player.getInventory().add(o.getItemId() + 1, qty);
                                        } else if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester) {
                                            player.getInventory().add(o.getItemId() + 1, qty);
                                        } else {
                                            player.getInventory().add(o.getItemId(), qty);
                                        }
                                    }

                                    player.getSkillManager().addExperience(Skill.MINING, addxp * qty);

                                    //tutorial
                                    if ((o.getItemId() == 436 || o.getItemId() == 438) && (player.tutorialIsland == 4)) {
                                        player.tutorialIsland = 5;
                                        DialogueManager.start(player, 1010);
                                    }

                                }

                                if (o.getItemId() == Ores.Copper.getItemId()) {
                                    player.getAchievementTracker().progress(AchievementData.MINE_10_COPPER, qty);
                                } else if (o == Ores.Iron) {
                                    player.getAchievementTracker().progress(AchievementData.MINE_25_IRON, qty);
                                } else if (o == Ores.Coal) {
                                    player.getAchievementTracker().progress(AchievementData.MINE_100_COAL, qty);
                                } else if (o == Ores.Mithril) {
                                    player.getAchievementTracker().progress(AchievementData.MINE_250_MITHRIL, qty);
                                } else if (o == Ores.Adamantite) {
                                    player.getAchievementTracker().progress(AchievementData.MINE_500_ADAMANTITE, qty);
                                } else if (o.getItemId() == Ores.Runite.getItemId()) {
                                    player.getAchievementTracker().progress(AchievementData.MINE_1000_RUNITE, qty);
                                }

                                if (o == Ores.CRASHED_STAR) {
                                    if (player.personalFilterGathering) {
                                        player.getPacketSender().sendMessage("You mine the crashed star..");
                                    }
                                } else {
                                    if (player.personalFilterGathering) {
                                        player.getPacketSender().sendMessage("You mine some ore.");
                                    }
                                }
                                Sounds.sendSound(player, Sound.MINE_ITEM);

                                for (int i = 0; i < player.getSkiller().getSkillerTask().getObjId().length; i++) {
                                    if (oreObject.getId() == player.getSkiller().getSkillerTask().getObjId()[i]) {
                                        for (int k = 0; k < qty; k++) {
                                            player.getSkiller().handleSkillerTaskGather(true);
                                            player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                                        }
                                    }
                                }

                                cycle = 0;
                                this.stop();
                                if (o.getRespawn() > 0) {
                                    player.performAnimation(new Animation(65535));
                                    oreRespawn(player, oreObject, o);
                                } else {
                                    if (oreObject.getId() == 38660) {
                                        if (ShootingStar.CRASHED_STAR == null || ShootingStar.CRASHED_STAR.getStarObject().getPickAmount() >= ShootingStar.MAXIMUM_MINING_AMOUNT) {
                                            player.getPacketSender().sendClientRightClickRemoval();
                                            player.getSkillManager().stopSkilling();
                                            return;
                                        } else {
                                            ShootingStar.CRASHED_STAR.getStarObject().incrementPickAmount();
                                        }
                                    } else {
                                        player.performAnimation(new Animation(65535));
                                    }
                                    startMining(player, oreObject);
                                }
                            }
                        });
                        TaskManager.submit(player.getCurrentTask());
                    } else {
                        player.getPacketSender().sendMessage("You need a Mining level of at least " + p.getReq() + " to use this pickaxe.");
                    }
                } else {
                    player.getPacketSender().sendMessage("You need a Mining level of at least " + o.getLevelReq() + " to mine this rock.");
                }
            } else {
                player.getPacketSender().sendMessage("You don't have a pickaxe to mine this rock with.");
            }
        }
    }

    public static void oreRespawn(final Player player, final GameObject oldOre, Ores o) {
        if (oldOre == null || oldOre.getPickAmount() >= 1)
            return;
        oldOre.setPickAmount(1);
        for (Player players : player.getLocalPlayers()) {
            if (players == null)
                continue;
            if (players.getInteractingObject() != null && players.getInteractingObject().getPosition().equals(player.getInteractingObject().getPosition().copy())) {
                players.getPacketSender().sendClientRightClickRemoval();
                players.getSkillManager().stopSkilling();
            }
        }
        player.getPacketSender().sendClientRightClickRemoval();
        player.getSkillManager().stopSkilling();
        CustomObjects.globalObjectRespawnTask(new GameObject(452, oldOre.getPosition().copy(), 10, 0), oldOre, o.getRespawn());
    }
}
