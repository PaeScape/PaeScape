package com.arlania.world.content.skill.impl.woodcutting;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.EvilTrees;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.skill.impl.firemaking.Logdata;
import com.arlania.world.content.skill.impl.firemaking.Logdata.logData;
import com.arlania.world.content.skill.impl.woodcutting.WoodcuttingData.Hatchet;
import com.arlania.world.content.skill.impl.woodcutting.WoodcuttingData.Trees;
import com.arlania.world.entity.impl.player.Player;

public class Woodcutting {

    public static void cutWood(final Player player, final GameObject object, boolean restarting) {
        if (!restarting)
            player.getSkillManager().stopSkilling();
        if (player.getInventory().getFreeSlots() == 0) {
            player.getPacketSender().sendMessage("You don't have enough free inventory space.");
            return;
        }
        player.setPositionToFace(object.getPosition());
        final int objId = object.getId();
        final Hatchet h = Hatchet.forId(WoodcuttingData.getHatchet(player));
        if (h != null) {
            if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= h.getRequiredLevel()) {
                final WoodcuttingData.Trees t = WoodcuttingData.Trees.forId(objId);
                if (t != null) {
                    player.setEntityInteraction(object);
                    if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= t.getReq()) {
                        player.performAnimation(new Animation(h.getAnim()));
                        int delay = RandomUtility.inclusiveRandom(t.getTicks() - WoodcuttingData.getChopTimer(player, h)) + 1;
                        player.setCurrentTask(new Task(1, player, false) {
                            int cycle = 0;
                            final int reqCycle = delay >= 2 ? delay : RandomUtility.inclusiveRandom(1) + 1;

                            @Override
                            public void execute() {
                                if (player.getInventory().getFreeSlots() == 0) {
                                    player.performAnimation(new Animation(65535));
                                    player.getPacketSender().sendMessage("You don't have enough free inventory space.");
                                    this.stop();
                                    return;
                                }
                                if (cycle != reqCycle) {
                                    cycle++;
                                    player.performAnimation(new Animation(h.getAnim()));
                                } else if (cycle >= reqCycle) {

                                    double addxp = t.getXp();
                                    double bonusxp = 1;

                                    if (player.getEquipment().contains(210941))
                                        bonusxp += .05;
                                    if (player.getEquipment().contains(210939))
                                        bonusxp += .05;
                                    if (player.getEquipment().contains(210940))
                                        bonusxp += .05;
                                    if (player.getEquipment().contains(210933))
                                        bonusxp += .05;

                                    addxp *= bonusxp;

                                    int qty = player.acceleratedResources();


                                    if (t == Trees.NORMAL) {
                                        player.getAchievementTracker().progress(AchievementData.CHOP_10_LOGS, qty);
                                    } else if (t == Trees.OAK) {
                                        player.getAchievementTracker().progress(AchievementData.CHOP_25_OAKS, qty);
                                    } else if (t == Trees.WILLOW) {
                                        player.getAchievementTracker().progress(AchievementData.CHOP_50_WILLOWS, qty);
                                    } else if (t == Trees.MAPLE) {
                                        player.getAchievementTracker().progress(AchievementData.CHOP_100_MAPLES, qty);
                                    } else if (t == Trees.YEW) {
                                        player.getAchievementTracker().progress(AchievementData.CHOP_250_YEWS, qty);
                                    } else if (t == Trees.MAGIC) {
                                        player.getAchievementTracker().progress(AchievementData.CHOP_1000_MAGICS, qty);
                                    }


                                    if (player.getSkillManager().hasInfernalTools(Skill.WOODCUTTING, Skill.FIREMAKING) && t.getFmXp() > 0) {
                                        player.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));

                                        int addFMxp = t.getFmXp();
                                        double bonusFMxp = 1;

                                        if (player.getEquipment().contains(220708))
                                            bonusFMxp += .05;
                                        if (player.getEquipment().contains(220704))
                                            bonusFMxp += .05;
                                        if (player.getEquipment().contains(220706))
                                            bonusFMxp += .05;
                                        if (player.getEquipment().contains(220710))
                                            bonusFMxp += .05;
                                        if (player.getEquipment().contains(220712))
                                            bonusFMxp += .05;

                                        addFMxp *= bonusFMxp;
                                        logData fmLog = Logdata.getLogData(player, t.getReward());

                                        if ((player.getSummoning().getFamiliar() != null) && (player.getSummoning().getFamiliar().getSummonNpc().getId() == 21368)) {
                                            addFMxp *= 1.25;
                                        }

                                        if (player.personalFilterAdze) {
                                            player.getPacketSender().sendMessage("Your infernal tool burns the log, granting you Firemaking experience.");
                                        }
                                        if (fmLog == Logdata.logData.LOG) {
                                            player.getAchievementTracker().progress(AchievementData.BURN_10_LOGS, qty);
                                        } else if (fmLog == Logdata.logData.OAK) {
                                            player.getAchievementTracker().progress(AchievementData.BURN_25_OAKS, qty);
                                        } else if (fmLog == Logdata.logData.WILLOW) {
                                            player.getAchievementTracker().progress(AchievementData.BURN_50_WILLOWS, qty);
                                        } else if (fmLog == Logdata.logData.MAPLE) {
                                            player.getAchievementTracker().progress(AchievementData.BURN_100_MAPLES, qty);
                                        } else if (fmLog == Logdata.logData.YEW) {
                                            player.getAchievementTracker().progress(AchievementData.BURN_250_YEWS, qty);
                                        } else if (fmLog == Logdata.logData.MAGIC) {
                                            player.getAchievementTracker().progress(AchievementData.BURN_1000_MAGICS, qty);
                                        }

                                        player.getSkillManager().addExperience(Skill.WOODCUTTING, addxp * qty);
                                        player.getSkillManager().addExperience(Skill.FIREMAKING, addFMxp * qty / 2);

                                        if (t.getReward() == 14666)
                                            BirdNests.dropNest(player);

                                    } else {
                                        player.getSkillManager().addExperience(Skill.WOODCUTTING, addxp * qty);

                                        if (player.checkAchievementAbilities(player, "gatherer") && ItemDefinition.forId(t.getReward() + 1).isNoted()) {
                                            player.getInventory().add(t.getReward() + 1, qty);
                                        } else if (player.getGameMode() == GameMode.SEASONAL_IRONMAN && player.Harvester && ItemDefinition.forId(t.getReward() + 1).isNoted()) {
                                            player.getInventory().add(t.getReward() + 1, qty);
                                        } else {
                                            player.getInventory().add(t.getReward(), qty);
                                        }

                                        if (t.getReward() == 14666)
                                            BirdNests.dropNest(player);
                                    }


                                    for (int i = 0; i < player.getSkiller().getSkillerTask().getObjId().length; i++) {

                                        if (object.getId() == player.getSkiller().getSkillerTask().getObjId()[i]) {
                                            for (int k = 0; k < qty; k++) {
                                                player.getSkiller().handleSkillerTaskGather(true);
                                                player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                                            }
                                        }
                                    }
                                    cycle = 0;

                                    if (object.getId() == 336066) {
                                        if (RandomUtility.inclusiveRandom(10) == 1) {
                                            player.getInventory().add(223878, qty);
                                        }
                                    } else if (object.getId() != 329311) {
                                        BirdNests.dropNest(player);
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
                                            player.getInventory().add(219718, clueAmt);
                                        else if ((randClue > 1) && (randClue <= 4))
                                            player.getInventory().add(219716, clueAmt);
                                        else if ((randClue > 4) && (randClue <= 10))
                                            player.getInventory().add(219714, clueAmt);
                                        else
                                            player.getInventory().add(219712, clueAmt);
                                    }

                                    this.stop();
                                    if (object.getId() == 11434) {
                                        if (EvilTrees.SPAWNED_TREE == null || EvilTrees.SPAWNED_TREE.getTreeObject().getCutAmount() >= EvilTrees.MAX_CUT_AMOUNT) {
                                            player.getPacketSender().sendClientRightClickRemoval();
                                            player.getSkillManager().stopSkilling();
                                            return;
                                        } else {
                                            EvilTrees.SPAWNED_TREE.getTreeObject().incrementCutAmount();
                                        }
                                        //} else {
                                        //player.performAnimation(new Animation(65535));
                                    }
                                    if (!t.isMulti() || RandomUtility.inclusiveRandom(50) == 4) {
                                        //player.performAnimation(new Animation(65535));
                                        if (object.getId() == 11434 || object.getId() == 336066 || object.getId() == 329668 || object.getId() == 329670) {
                                            player.getPacketSender().sendMessage("@red@You take a break from cutting the tree.");
                                            return;
                                        }

                                        treeRespawn(player, object);
                                        if (player.getLocation() != Location.WINTERTODT)
                                            player.getPacketSender().sendMessage("You've chopped the tree down.");
                                    } else {
                                        cutWood(player, object, true);
                                        if (t == Trees.EVIL_TREE) {
                                            if (player.personalFilterGathering) {
                                                player.getPacketSender().sendMessage("You cut the Evil Tree...");
                                            }
                                        } else {
                                            if (player.personalFilterGathering) {
                                                player.getPacketSender().sendMessage("You get some logs..");
                                            }
                                        }
                                    }
                                    Sounds.sendSound(player, Sound.WOODCUT);

                                }
                            }
                        });
                        TaskManager.submit(player.getCurrentTask());
                    } else {
                        player.getPacketSender().sendMessage("You need a Woodcutting level of at least " + t.getReq() + " to cut this tree.");
                    }
                }
            } else {
                player.getPacketSender().sendMessage("You do not have a hatchet which you have the required Woodcutting level to use.");
            }
        } else {
            player.getPacketSender().sendMessage("You do not have a hatchet that you can use.");
        }
    }

//	public static boolean lumberJack(Player player) {
//		return player.getEquipment().get(Equipment.HEAD_SLOT).getId() == 10941 && player.getEquipment().get(Equipment.BODY_SLOT).getId() == 10939 && player.getEquipment().get(Equipment.LEG_SLOT).getId() == 10940 && player.getEquipment().get(Equipment.FEET_SLOT).getId() == 10933;
//	}


    public static void treeRespawn(final Player player, final GameObject oldTree) {
        if (oldTree == null || oldTree.getPickAmount() >= 1)
            return;
        oldTree.setPickAmount(1);
        for (Player players : player.getLocalPlayers()) {
            if (players == null)
                continue;
            if (players.getInteractingObject() != null && players.getInteractingObject().getPosition().equals(player.getInteractingObject().getPosition().copy())) {
                players.getSkillManager().stopSkilling();
                players.getPacketSender().sendClientRightClickRemoval();
            }
        }
        player.getPacketSender().sendClientRightClickRemoval();
        player.getSkillManager().stopSkilling();
        if (oldTree.getId() != 329311)
            CustomObjects.globalObjectRespawnTask(new GameObject(1343, oldTree.getPosition().copy(), 10, 0), oldTree, 20 + RandomUtility.inclusiveRandom(10));
    }

}
