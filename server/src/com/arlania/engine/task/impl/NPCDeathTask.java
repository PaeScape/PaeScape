package com.arlania.engine.task.impl;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.Skill;
import com.arlania.model.definitions.NPCDrops;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.combat.strategy.impl.KalphiteQueen;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an npc's death task, which handles everything
 * an npc does before and after their death animation (including it),
 * such as dropping their drop table items.
 *
 * @author relex lawl
 */

public class NPCDeathTask extends Task {
    /*
     * The array which handles what bosses will give a player points
     * after death
     */
    private final Set<Integer> BOSSES = new HashSet<>(Arrays.asList(2882, 2881, 2883, 50, 1999, 6203, 6222, 6247, 6260,
            8133, 13447, 5886, 8349, 7286, 2042, 2043, 2044, 1158,
            1159, 4540, 1580, 3334, 3340, 499, 7287, 2745, 21554,
            22359, 22360, 22388, 22340, 22374, 23425,
            2000, 2001, 2006, 2009, 3200, 3334, 8349, 22061));

    private final Set<Integer> WILDY_BOSSES = new HashSet<>(Arrays.asList(2000, 2001, 2006, 2009, 3200, 3334, 8349, 22061));

    private final Set<Integer> REVENANTS = new HashSet<>(Arrays.asList(6715, 6716, 6701, 6725, 6691));

    public boolean adventurerBoost = false;

    //use this array of npcs to change the npcs you want to give PaePoints

    /**
     * The NPCDeathTask constructor.
     *
     * @param npc The npc being killed.
     */
    public NPCDeathTask(NPC npc) {
        super(2);
        this.npc = npc;
        this.ticks = 2;
    }

    /**
     * The npc setting off the death task.
     */
    private final NPC npc;

    /**
     * The amount of ticks on the task.
     */
    private int ticks = 2;

    /**
     * The player who killed the NPC
     */
    private Player killer = null;


    @SuppressWarnings("incomplete-switch")
    @Override
    public void execute() {
        try {
            npc.setEntityInteraction(null);
            switch (ticks) {
                case 2:
                    npc.getMovementQueue().setLockMovement(true).reset();
                    killer = npc.getCombatBuilder().getKiller(npc.getId() != 3334);
                    if (!(npc.getId() >= 6142 && npc.getId() <= 6145) && !(npc.getId() > 5070 && npc.getId() < 5081))
                        npc.performAnimation(new Animation(npc.getDefinition().getDeathAnimation()));

                    /** CUSTOM NPC DEATHS **/


                    break;
                case 0:
                    if (killer != null) {

                        if (killer.wildSavior > 0 && killer.getLocation() == Location.WILDERNESS) {
                            if (killer.wildSavior == 3) {
                                killer.heal(30);
                                killer.getSkillManager().setCurrentLevel(Skill.PRAYER, killer.getSkillManager().getCurrentLevel(Skill.PRAYER) + 30);
                            } else if (killer.wildSavior == 2) {
                                killer.heal(20);
                                killer.getSkillManager().setCurrentLevel(Skill.PRAYER, killer.getSkillManager().getCurrentLevel(Skill.PRAYER) + 20);
                            } else if (killer.wildSavior == 1) {
                                killer.heal(10);
                                killer.getSkillManager().setCurrentLevel(Skill.PRAYER, killer.getSkillManager().getCurrentLevel(Skill.PRAYER) + 10);
                            }
                            if (killer.getSkillManager().getCurrentLevel(Skill.PRAYER) > killer.getSkillManager().getMaxLevel(Skill.PRAYER))
                                killer.getSkillManager().setCurrentLevel(Skill.PRAYER, killer.getSkillManager().getMaxLevel(Skill.PRAYER));
                        }

                        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.GLOBAL_BOSS_KILLS)) {
                            GlobalEventHandler.totalGlobalBossKills += 1;
                        }

                        if (killer.bossKillsEvent && killer.personalEvent) {
                            killer.globalBossKills += 1;
                            killer.totalGlobalBossKills += 1;
                        }

                        int donatorbonus = 0;

                        if (killer.getStaffRights().getStaffRank() > 4)
                            donatorbonus = 2;
                        else if (killer.getStaffRights().getStaffRank() == 3 || killer.getStaffRights().getStaffRank() == 4)
                            donatorbonus = 1;

                        //donatorbonus to add additional PaePoints per kill later

                        if(npc.savagery == 10) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_M7_BOSSES, 1);
                        } if(npc.savagery == 9) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_M6_BOSSES, 1);
                         }if(npc.savagery == 8) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_M5_BOSSES, 1);
                        } if(npc.savagery == 7) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_M4_BOSSES, 1);
                        } if(npc.savagery == 6) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_M3_BOSSES, 1);
                        } if(npc.savagery == 5) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_M2_BOSSES, 1);
                        } if(npc.savagery == 4) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_M1_BOSSES, 1);
                        } if(npc.savagery == 3) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_EXPERT_BOSSES, 1);
                        } if(npc.savagery == 2) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_HARD_BOSSES, 1);
                        } if(npc.savagery == 1) {
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_MEDIUM_BOSSES, 1);
                        }


                        if (npc.getId() == 1552) {
                            killer.sendMessage("NO DROPS UNTIL DECEMBER 19th?!");
                        }

                        int pointsToAdd = 1 + donatorbonus;


                        if (npc.getId() == 22061) {
                            pointsToAdd += 3;
                        }

                        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_BOSS_PP) || (killer.doubleBossPointEvent && killer.personalEvent))
                            pointsToAdd *= 2;

                        if (BOSSES.contains(npc.getId())) {
                            killer.setPaePoints(killer.getPaePoints() + pointsToAdd);
                            if (!killer.chatFilter && killer.personalFilterPaepoints)
                                killer.sendMessage("<img=0>You now have @red@" + killer.getPaePoints() + " PaePoints!");
                        }

                        if (WILDY_BOSSES.contains(npc.getId()) && npc.getLocation() == Location.WILDERNESS) {
                            killer.WildyPoints = killer.WildyPoints + pointsToAdd + 2;
                            if (killer.wildEnraged > 0) {
                                killer.wildEnragedKills++;
                            }
                            if (!killer.chatFilter && killer.personalFilterWildypoints)
                                killer.sendMessage("<img=0>You now have @red@" + killer.WildyPoints + " Wildy Points!");
                        } else if (WILDY_BOSSES.contains(npc.getId()) && npc.getLocation() != Location.WILDERNESS) {
                            killer.setPaePoints(killer.getPaePoints() + pointsToAdd);
                            if (!killer.chatFilter && killer.personalFilterPaepoints)
                                killer.sendMessage("<img=0>You now have @red@" + killer.getPaePoints() + " PaePoints!");
                        }

                        if (REVENANTS.contains(npc.getId()) && npc.getLocation() == Location.WILDERNESS) {
                            killer.WildyPoints = killer.WildyPoints + 1;
                            if (killer.wildEnraged > 0) {
                                killer.wildEnragedKills++;
                            }
                        }

                        if (BOSSES.contains(npc.getId()) && killer.battlePass) {
                            killer.bpBossKills++;
                        }
                        if (BOSSES.contains(npc.getId()) && killer.eventPass) {
                            killer.epBossKills++;
                        }
                        if (BOSSES.contains(npc.getId()) && killer.mysteryPass) {
                            killer.mpBossKills++;
                        }

                        if (BOSSES.contains(npc.getId())) {
                            killer.getPointsHandler().settotalbosskills(1, true);


                            killer.getAchievementTracker().progress(AchievementData.KILL_100000_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_50000_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_25000_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_10000_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_5000_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_2500_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_1000_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_500_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_250_BOSSES, 1);
                            killer.getAchievementTracker().progress(AchievementData.KILL_100_BOSSES, 1);


                            if (killer.getPointsHandler().gettotalbosskills() >= 100000)
                                killer.getAchievementTracker().progress(AchievementData.KILL_100000_BOSSES, 100000);

                            if (killer.getPointsHandler().gettotalbosskills() >= 50000)
                                killer.getAchievementTracker().progress(AchievementData.KILL_50000_BOSSES, 50000);

                            if (killer.getPointsHandler().gettotalbosskills() >= 25000)
                                killer.getAchievementTracker().progress(AchievementData.KILL_25000_BOSSES, 25000);

                            if (killer.getPointsHandler().gettotalbosskills() >= 10000)
                                killer.getAchievementTracker().progress(AchievementData.KILL_10000_BOSSES, 10000);

                            if (killer.getPointsHandler().gettotalbosskills() >= 5000)
                                killer.getAchievementTracker().progress(AchievementData.KILL_5000_BOSSES, 5000);

                            if (killer.getPointsHandler().gettotalbosskills() >= 2500)
                                killer.getAchievementTracker().progress(AchievementData.KILL_2500_BOSSES, 2500);

                            if (killer.getPointsHandler().gettotalbosskills() >= 1000)
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_BOSSES, 1000);

                            if (killer.getPointsHandler().gettotalbosskills() >= 500)
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_BOSSES, 500);

                            if (killer.getPointsHandler().gettotalbosskills() >= 250)
                                killer.getAchievementTracker().progress(AchievementData.KILL_250_BOSSES, 250);

                            if (killer.getPointsHandler().gettotalbosskills() >= 100)
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_BOSSES, 100);


                        }

                        boolean boss = BOSSES.contains(npc.getId());
                        String qty = String.valueOf(killer.getCollectionLog().addKill(npc.getId()));

                        if (killer.getCollectionLog().getKills(npc.getId()) >= 250)
                            adventurerBoost = true;

                        if (!killer.chatFilter && boss && killer.personalFilterBossKills)
                            killer.sendMessage("<img=0>You have now killed @red@" + qty + " @bla@" + npc.getDefinition().getName() + " and @red@" + killer.getPointsHandler().gettotalbosskills() + " @bla@Bosses!");


                        switch (npc.getId()) {

                            case 50:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_KBD, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_KBD, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_KBD, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_KBD, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_KBD, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_KBD, 1000);
                                }
                                break;
                            case 23425:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_NIGHTMARE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_NIGHTMARE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_NIGHTMARE, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_NIGHTMARE, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_NIGHTMARE, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_NIGHTMARE, 1000);
                                }
                                break;
                            case 8349:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_TORM_DEMONS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_TORM_DEMONS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_TORM_DEMONS, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_TORM_DEMONS, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_TORM_DEMONS, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_TORM_DEMONS, 1000);
                                }
                                break;
                            case 6260:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_GRAARDOR, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_GRAARDOR, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_GRAARDOR, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_GRAARDOR, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_GRAARDOR, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_GRAARDOR, 1000);
                                }
                                break;
                            case 6222:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_KREE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_KREE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_KREE, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_KREE, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_KREE, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_KREE, 1000);
                                }
                                break;
                            case 6247:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_ZILYANA, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_ZILYANA, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_ZILYANA, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_ZILYANA, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_ZILYANA, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_ZILYANA, 1000);
                                }
                                break;
                            case 6203:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_KRIL, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_KRIL, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_KRIL, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_KRIL, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_KRIL, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_KRIL, 1000);
                                }
                                break;
                            case 8133:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_CORP_BEAST, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_CORP_BEAST, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_CORP_BEAST, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_CORP_BEAST, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_CORP_BEAST, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_CORP_BEAST, 1000);
                                }
                                break;
                            case 4540:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_GLACIO, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_GLACIO, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_GLACIO, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_GLACIO, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_GLACIO, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_GLACIO, 1000);
                                }
                                break;
                            case 3200:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_CHAOS_ELEMENTAL, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_CHAOS_ELEMENTAL, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_CHAOS_ELEMENTAL, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_CHAOS_ELEMENTAL, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_CHAOS_ELEMENTAL, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_CHAOS_ELEMENTAL, 1000);
                                }
                                break;
                            case 2009:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_CALLISTO, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_CALLISTO, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_CALLISTO, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_CALLISTO, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_CALLISTO, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_CALLISTO, 1000);
                                }
                                break;
                            case 2006:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_VETION, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_VETION, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_VETION, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_VETION, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_VETION, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_VETION, 1000);
                                }
                                break;
                            case 2001:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_SCORPIA, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_SCORPIA, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_SCORPIA, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_SCORPIA, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_SCORPIA, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_SCORPIA, 1000);
                                }
                                break;
                            case 2000:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_VENENATIS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_VENENATIS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_VENENATIS, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_VENENATIS, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_VENENATIS, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_VENENATIS, 1000);
                                }
                                break;
                            case 1999:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_CERBERUS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_CERBERUS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_CERBERUS, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_CERBERUS, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_CERBERUS, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_CERBERUS, 1000);
                                }
                                break;
                            case 5886:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_SIRE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_SIRE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_SIRE, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_SIRE, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_SIRE, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_SIRE, 1000);
                                }
                                break;
                            case 2881:
                            case 2882:
                            case 2883:
                                int killsRex = killer.getCollectionLog().getKills(2881);
                                int killsSupreme = killer.getCollectionLog().getKills(2882);
                                int killsPrime = killer.getCollectionLog().getKills(2883);
                                int totalDKS = killsRex + killsPrime + killsSupreme;

                                killer.getAchievementTracker().progress(AchievementData.KILL_100_DKS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_DKS, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_DKS, 1);

                                if (totalDKS >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_DKS, 100);
                                }
                                if (totalDKS >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_DKS, 500);
                                }
                                if (totalDKS >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_DKS, 1000);
                                }
                                break;
                            case 2042:
                            case 2043:
                            case 2044:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_ZULRAH, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_ZULRAH, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_ZULRAH, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_ZULRAH, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_ZULRAH, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_ZULRAH, 1000);
                                }
                                break;
                            case 499:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_THERMO, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_THERMO, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_THERMO, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_THERMO, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_THERMO, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_THERMO, 1000);
                                }
                                break;
                            case 3340:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_MOLE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_MOLE, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_MOLE, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_MOLE, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_MOLE, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_MOLE, 1000);
                                }
                                break;
                            case 3334:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_WILDYWYRM, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_WILDYWYRM, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_WILDYWYRM, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_WILDYWYRM, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_WILDYWYRM, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_WILDYWYRM, 1000);
                                }
                                break;
                            case 1158:
                            case 1160:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_KALPH_QUEEN, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_KALPH_QUEEN, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_KALPH_QUEEN, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_KALPH_QUEEN, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_KALPH_QUEEN, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_KALPH_QUEEN, 1000);
                                }
                                break;
                            case 1648:
                            case 1649:
                            case 1650:
                            case 1651:
                            case 1652:
                            case 1653:
                            case 1654:
                            case 1655:
                            case 1656:
                            case 1657:
                                killer.getAchievementTracker().progress(AchievementData.KILL_10_CRAWLING_HAND, 1);
                                break;
                            case 1612:
                                killer.getAchievementTracker().progress(AchievementData.KILL_25_BANSHEE, 1);
                                break;
                            case 6216:
                                killer.getAchievementTracker().progress(AchievementData.KILL_50_PYREFIEND, 1);
                                break;
                            case 1626:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_TUROTH, 1);
                                break;
                            case 1610:
                            case 1611:
                                killer.getAchievementTracker().progress(AchievementData.KILL_250_GARGOYLE, 1);
                                break;
                            case 6221:
                            case 6231:
                            case 6257:
                            case 6278:
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_SPIRITUAL_MAGE, 1);
                                break;
                            case 1615:
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_ABYSSAL_DEMON, 1);
                                break;
                            case 22061:
                                killer.getAchievementTracker().progress(AchievementData.KILL_100_VORKATH, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_500_VORKATH, 1);
                                killer.getAchievementTracker().progress(AchievementData.KILL_1000_VORKATH, 1);

                                if (killer.getCollectionLog().getKills(npc.getId()) >= 100) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_100_VORKATH, 100);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 500) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_500_VORKATH, 500);
                                }
                                if (killer.getCollectionLog().getKills(npc.getId()) >= 1000) {
                                    killer.getAchievementTracker().progress(AchievementData.KILL_1000_VORKATH, 1000);
                                }
                                break;

                        }

                        /** SLAYER **/
                        killer.getSlayer().killedNpc(npc);

                        /** LOCATION KILLS **/
                        if (npc.getLocation().handleKilledNPC(killer, npc) && npc.getLocation() != Location.INSTANCEDBOSSES && npc.getLocation() != Location.RECIPE_FOR_DISASTER) {
                            stop();
                            return;
                        }

                        if ((npc.getId() == 1158 || npc.getId() == 1160) && npc.getLocation() != Location.INSTANCEDBOSSES)
                            KalphiteQueen.death(npc.getId(), npc.getPosition(), killer);

                        //Barrows Raid
                        if (npc.getLocation() == Location.GRAVEYARD) {
                            break;
                        }


                        Position pos = npc.getPosition().copy();


                        /** PARSE DROPS **/
                        NPCDrops.dropItems(killer, npc, pos, adventurerBoost);

                        //Difficulty Settings (additional loot roll)
                        if(killer.getLocation() == Location.INSTANCEDBOSSES && killer.difficulty > 0) {
                            if(killer.difficulty >= RandomUtility.inclusiveRandom(1, 10)) {
                                NPCDrops.dropItems(killer, npc, pos, adventurerBoost);
                                killer.getPacketSender().sendMessage("You've received an additional drop with your Difficulty Settings!");
                            }
                        }

                        //Nobility System (additional loot roll)
                        if(NobilitySystem.getNobilityBoost(killer) > RandomUtility.RANDOM.nextDouble()) {
                            NPCDrops.dropItems(killer, npc, pos, adventurerBoost);
                            killer.getPacketSender().sendMessage("You've received an additional drop with your Nobility Rank!");
                        }

                        //Pet Hatius (additional loot roll)
                        if(killer.getHatiusLootRoll()) {
                            if (RandomUtility.inclusiveRandom(0, 100) < 5) {
                                NPCDrops.dropItems(killer, npc, pos, adventurerBoost);
                                killer.getPacketSender().sendMessage("You've received an additional drop with your Pet Hatius!");
                            }
                        }


                        if (npc.getId() == 2043 || npc.getId() == 2042 || npc.getId() == 2044) {
                            pos = killer.getPosition().copy();
                        }


                        if (killer.Unnatural > 0) {
                            if (npc.getId() == 1652) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 10);
                            }
                            if (npc.getId() == 1600) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 20);
                            }
                            if (npc.getId() == 1612) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 30);
                            }
                            if (npc.getId() == 1621) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 40);
                            }
                            if (npc.getId() == 6216) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 50);
                            }
                            if (npc.getId() == 1616) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 60);
                            }
                            if (npc.getId() == 1643) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 70);
                            }
                            if (npc.getId() == 1618) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 80);
                            }
                            if (npc.getId() == 1640) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 90);
                            }
                            if (npc.getId() == 1626) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 100);
                            }
                            if (npc.getId() == 1604) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 110);
                            }
                            if (npc.getId() == 1624) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 120);
                            }
                            if (npc.getId() == 1608) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 130);
                            }
                            if (npc.getId() == 3071) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 140);
                            }
                            if (npc.getId() == 1610) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 150);
                            }
                            if (npc.getId() == 1613) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 160);
                            }
                            if (npc.getId() == 6221) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 165);
                            }
                            if (npc.getId() == 6231) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 165);
                            }
                            if (npc.getId() == 6257) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 165);
                            }
                            if (npc.getId() == 6278) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 165);
                            }
                            if (npc.getId() == 1615) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 175);
                            }
                            if (npc.getId() == 2783) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 200);
                            }
                            if (npc.getId() == 9465) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 250);
                            }
                            if (npc.getId() == 9467) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 250);
                            }
                            if (npc.getId() == 9463) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 250);
                            }
                            if (npc.getId() == 5886) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 500);
                            }
                            if (npc.getId() == 1999) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 500);
                            }
                            if (npc.getId() == 499) {
                                killer.getSkillManager().addExperience(Skill.SLAYER, 500);
                            }
                        }

                        //Tutorial
                        if (npc.getId() == 86 && killer.tutorialIsland == 6) {
                            killer.tutorialIsland = 7;
                            DialogueManager.start(killer, 1014);
                        }
                        if (npc.getId() == 86 && killer.tutorialIsland == 8) {
                            killer.tutorialIsland = 9;
                            DialogueManager.start(killer, 1017);
                        }


                    }
                    stop();
                    break;
            }
            ticks--;
        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    }

    @Override
    public void stop() {
        setEventRunning(false);

        npc.setDying(false);

        //if (npc.getDefinition().getName().contains("Revenant"))
        //RevenantArena.spawn();

        if(killer == null) {

            String npcName = npc.getDefinition().getName();
            String location = npc.getLocation().toString();

            String log = npcName + " died at location: " + location + " and was assigned a null killer";

            if (GameSettings.DISCORD) {
                World.getPlayers().stream().filter(p -> p != null && (p.getLocation() == npc.getLocation())).forEach(p -> new MessageBuilder().append(p.getUsername() + " is in Location: " + npc.getLocation()).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_NULL_KILLER_CH).get()));
                new MessageBuilder().append(log).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_NULL_KILLER_CH).get());
            }

        }


        //respawn
        if (npc.getDefinition().getRespawnTime() > 0 && npc.shouldRespawn(npc)) {

            if (killer != null && killer.gwdRaid && npc.getLocation() == Location.GWD_RAID) {
                World.deregister(npc);
                return;
            }

            int respawn = npc.getDefinition().getRespawnTime();

            if (npc.getDefinition().getRespawnTime() > 0 && killer != null)
                respawn = killer.respawnTime;


            if (npc.getDefinition().getId() == 5080) {
            } else if (killer != null && killer.getStaffRights().getStaffRank() > 1)
                respawn /= 2;

            /*if (killer.Flash > 0) {
                respawn -= (respawn / 4 * killer.Flash);
            }*/

            if (killer != null && killer.flashbackTime > 0)
                respawn = 1;

            if (npc.getLocation() == Location.RECIPE_FOR_DISASTER || npc.getLocation() == Location.INSTANCEDBOSSES ||
                    npc.getLocation() == Location.GRAVEYARD || npc.getLocation() == Location.CHAOS_RAIDS) {
            } else {
                TaskManager.submit(new NPCRespawnTask(npc, respawn));
            }

        }

        World.deregister(npc);

    }
}
