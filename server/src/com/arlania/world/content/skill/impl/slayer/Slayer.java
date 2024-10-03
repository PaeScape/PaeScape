package com.arlania.world.content.skill.impl.slayer;

import com.arlania.model.*;
import com.arlania.model.container.impl.Shop.ShopManager;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.Emotes.Skillcape_Data;
import com.arlania.world.content.PetAbilities;
import com.arlania.world.content.PlayerPanel;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Slayer {

    private final Player player;

    public Slayer(Player p) {
        this.player = p;
    }

    private SlayerTasks slayerTask = SlayerTasks.NO_TASK, lastTask = SlayerTasks.NO_TASK;
    private SlayerMaster slayerMaster = SlayerMaster.VANNAKA;
    private int amountToSlay, taskStreak;
    private String duoPartner, duoInvitation;

    public void assignTask() {
        boolean hasTask = getSlayerTask() != SlayerTasks.NO_TASK && player.getSlayer().getLastTask() != getSlayerTask();
        boolean duoSlayer = duoPartner != null;
        if (duoSlayer && !player.getSlayer().assignDuoSlayerTask())
            return;
        if (hasTask) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        int[] taskData = SlayerTasks.getNewTaskData(slayerMaster, player);
        int slayerTaskId = taskData[0], slayerTaskAmount = taskData[1];
        while (SlayerTasks.forId(slayerTaskId).getreqLvl() > player.getSkillManager().getCurrentLevel(Skill.SLAYER) ||
                SlayerTasks.forId(slayerTaskId).getXP() == -1) {
            taskData = SlayerTasks.getNewTaskData(slayerMaster, player);
            slayerTaskId = taskData[0];
            slayerTaskAmount = taskData[1];
        }
        SlayerTasks taskToSet = SlayerTasks.forId(slayerTaskId);



        if (NpcDefinition.forId(taskToSet.getNpcId()[0]).getSlayerLevel() > player.getSkillManager().getMaxLevel(Skill.SLAYER)) {

            if (taskToSet == player.getSlayer().getLastTask() && player.Unnatural < 1)
                return;

            assignTask();
            return;
        }
        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSlay = slayerTaskAmount;
        this.slayerTask = taskToSet;
        DialogueManager.start(player, SlayerDialogues.receivedTask(player, getSlayerMaster(), getSlayerTask()));
        PlayerPanel.refreshPanel(player);
        if (duoSlayer) {
            Player duo = World.getPlayerByName(duoPartner);
            duo.getSlayer().setSlayerTask(taskToSet);
            duo.getSlayer().setAmountToSlay(slayerTaskAmount);
            duo.getPacketSender().sendInterfaceRemoval();
            DialogueManager.start(duo, SlayerDialogues.receivedTask(duo, slayerMaster, taskToSet));
            PlayerPanel.refreshPanel(duo);
        }
    }

    public void assignRepeatTask(Player player) {

        boolean duoSlayer = duoPartner != null;
        if (duoSlayer && !player.getSlayer().assignDuoSlayerTask())
            return;

        if (player.getSlayer().getLastTask().getXP() == -1) {
            player.getPacketSender().sendMessage("You have no task to repeat");
            return;
        }

        SlayerMaster master = player.getSlayer().getLastTask().getTaskMaster();
        SlayerTasks taskToSet = player.getSlayer().getLastTask();

        player.getSlayer().setSlayerMaster(master);
        int slayerTaskAmount = SlayerTasks.getUnnaturalTaskQuantity(player);


        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSlay = slayerTaskAmount;
        this.slayerTask = taskToSet;

        PlayerPanel.refreshPanel(player);
        if (duoSlayer) {
            Player duo = World.getPlayerByName(duoPartner);
            duo.getSlayer().setSlayerTask(taskToSet);
            duo.getSlayer().setAmountToSlay(slayerTaskAmount);
            duo.getPacketSender().sendInterfaceRemoval();
            DialogueManager.start(duo, SlayerDialogues.receivedTask(duo, slayerMaster, taskToSet));
            PlayerPanel.refreshPanel(duo);
        }
    }

    public void assignUnnaturalTask(int slayerTaskId) {

        boolean duoSlayer = duoPartner != null;
        if (duoSlayer && !player.getSlayer().assignDuoSlayerTask())
            return;
        SlayerTasks taskToSet = SlayerTasks.forId(slayerTaskId);

        int slayerTaskAmount = SlayerTasks.getUnnaturalTaskQuantity(player);
        taskToSet = SlayerTasks.forId(slayerTaskId);


        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSlay = slayerTaskAmount;
        this.slayerTask = taskToSet;

        PlayerPanel.refreshPanel(player);
        if (duoSlayer) {
            Player duo = World.getPlayerByName(duoPartner);
            duo.getSlayer().setSlayerTask(taskToSet);
            duo.getSlayer().setAmountToSlay(slayerTaskAmount);
            duo.getPacketSender().sendInterfaceRemoval();
            DialogueManager.start(duo, SlayerDialogues.receivedTask(duo, slayerMaster, taskToSet));
            PlayerPanel.refreshPanel(duo);
        }
    }

    public void resetSlayerTask() {
        SlayerTasks task = getSlayerTask();
        if (task == SlayerTasks.NO_TASK)
            return;
        this.slayerTask = SlayerTasks.NO_TASK;
        this.amountToSlay = 0;

        if(player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
            if (player.Unnatural == 0) {
                player.setPaePoints(player.getPaePoints() - 5);
                this.taskStreak = 0;
            }
        }

        PlayerPanel.refreshPanel(player);
        Player duo = duoPartner == null ? null : World.getPlayerByName(duoPartner);
        if (duo != null) {

            if (duo.Unnatural == 0)
                duo.getSlayer().setSlayerTask(SlayerTasks.NO_TASK).setAmountToSlay(0).setTaskStreak(0);
            else
                duo.getSlayer().setSlayerTask(SlayerTasks.NO_TASK).setAmountToSlay(0);

            if (player.Unnatural == 0)
                duo.getPacketSender().sendMessage("Your partner exchanged 5 PaePoints to reset your team's Slayer task.");
            PlayerPanel.refreshPanel(duo);
            player.getPacketSender().sendMessage("You've successfully reset your team's Slayer task.");
        } else {
            player.getPacketSender().sendMessage("Your Slayer task has been reset.");
        }
    }

    public void killedNpc(NPC npc) {
        if (player.getSlayer().slayerTask == SlayerTasks.NO_TASK)
            return;

        if (player.getSlayer().getSlayerMaster() == SlayerMaster.DAVE && player.getLocation() != Locations.Location.WILDERNESS)
            return;

        for (int i = 0; i < slayerTask.getNpcId().length; i++) {
            if (slayerTask.getNpcId()[i] == npc.getId()) {

                player.getRespawnTime(true);

                if (RandomUtility.inclusiveRandom(3) == 0 && (player.getInventory().contains(221183) || player.getEquipment().contains(221183)) && player.getSlayer().getDuoPartner() == null) {
                    player.getPacketSender().sendMessage("Your bracelet prevents the kill from counting towards your task!");
                    return;
                }

                handleSlayerTaskDeath(true);

                if (RandomUtility.inclusiveRandom(3) == 0 && (player.getInventory().contains(221177) || player.getEquipment().contains(221177))) {
                    if (player.getSlayer().amountToSlay > 0 && player.getSlayer().slayerTask != SlayerTasks.NO_TASK && player.getSlayer().getDuoPartner() == null) {
                        player.getPacketSender().sendMessage("Your bracelet counts an additional kill towards your slayer task");
                        handleSlayerTaskDeath(true);
                    } else
                        player.getPacketSender().sendMessage("Your bracelet would have counted an additional kill.");
                }


                if (duoPartner != null) {
                    Player duo = World.getPlayerByName(duoPartner);
                    if (duo != null) {
                        if (checkDuoSlayer(player, false)) {
                            duo.getSlayer().handleSlayerTaskDeath(Locations.goodDistance(player.getPosition(), duo.getPosition(), 20));
                        } else {
                            resetDuo(player, duo);
                        }
                    }
                }
            } else {
                player.getRespawnTime(false);
            }
        }
    }


    public void handleSlayerTaskDeath(boolean giveXp) {
        int xp = slayerTask.getXP();

        if (amountToSlay > 1) {
            amountToSlay--;
        } else {
            taskStreak++;
            player.totalSlayerTasks++;

            player.getAchievementTracker().progress(AchievementData.COMPLETE_10_SLAYER_TASKS, 1);
            player.getAchievementTracker().progress(AchievementData.COMPLETE_100_SLAYER_TASKS, 1);
            player.getAchievementTracker().progress(AchievementData.COMPLETE_1000_SLAYER_TASKS, 1);

            givePoints(slayerMaster);

            if (PetAbilities.checkPetAbilities(player, "duradead") && RandomUtility.inclusiveRandom(1, 100) > 50) {
                player.getPacketSender().sendMessage("You've completed your Slayer task!");
                player.getPacketSender().sendMessage("Your Pet Duradead automatically repeats your last task!");
                lastTask = slayerTask;
                assignRepeatTask(player);
            } else {
                player.getPacketSender().sendMessage("You've completed your Slayer task! Return to a Slayer master for another one.");
                lastTask = slayerTask;
                slayerTask = SlayerTasks.NO_TASK;
                amountToSlay = 0;
            }

        }

        if (giveXp) {
            player.getSkillManager().addExperience(Skill.SLAYER, doubleSlayerXP ? xp * 2 : xp);
        }

        PlayerPanel.refreshPanel(player);
    }

    @SuppressWarnings("incomplete-switch")
    public void givePoints(SlayerMaster master) {
        int pointsReceived = 5;


        switch (master) {
            case VANNAKA:
                pointsReceived = 5;
                player.getSkillManager().addExperience(Skill.SLAYER, 1000);
                break;
            case DURADEL:
                pointsReceived = 10;
                player.getSkillManager().addExperience(Skill.SLAYER, 2000);
                break;
            case KURADEL:
                pointsReceived = 15;
                player.getSkillManager().addExperience(Skill.SLAYER, 4000);
                break;
            case SUMONA:
                pointsReceived = 20;
                player.getSkillManager().addExperience(Skill.SLAYER, 7500);
                break;
            case NIEVE:
                pointsReceived = 25;
                player.getSkillManager().addExperience(Skill.SLAYER, 10000);
                break;
            case DAVE:
                pointsReceived = 30;
                player.getSkillManager().addExperience(Skill.SLAYER, 15000);
                break;
        }
        if (Skillcape_Data.SLAYER.isWearingCape(player)) {
            pointsReceived += 2;
        }

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_SLAYER_PP) || (player.doubleSlayerPointsEvent && player.personalEvent))
            pointsReceived *= 2;

        double donatorbonus = 1;

        if (player.getStaffRights().getStaffRank() > 5)
            donatorbonus = 3;
        else if (player.getStaffRights().getStaffRank() > 3)
            donatorbonus = 2;
        else if (player.getStaffRights().getStaffRank() == 2 || player.getStaffRights().getStaffRank() == 3)
            donatorbonus = 1.5;

        pointsReceived *= donatorbonus;


        //Unnatural double points

        if (player.Unnatural >= 4) {
            if (RandomUtility.inclusiveRandom(1, 100) <= 15) {
                pointsReceived *= 2;
            }
        } else if (player.Unnatural >= 2) {
            if (RandomUtility.inclusiveRandom(1, 100) <= 10) {
                pointsReceived *= 2;
            }
        }

        if(player.Bloodthirsty) {
            pointsReceived *= 2;
        }

        int per5 = pointsReceived * 2;
        int per10 = pointsReceived * 5;
        int per25 = pointsReceived * 10;

        if (player.Unnatural >= 3) {
            per5 *= 2;
            per10 *= 2;
            per25 *= 2;
        }
        if (master == SlayerMaster.DAVE) {

            player.getInventory().add(223490, RandomUtility.inclusiveRandom(1, 3));
            player.getPacketSender().sendMessage("You receive a Larran's Key for completing your Slayer Task!");


            if (player.getSlayer().getTaskStreak() == 25) {
                player.setPaePoints(player.getPaePoints() + per25 / 2);
                player.WildyPoints += per25 / 2;
                player.getPacketSender().sendMessage("You received " + per25 / 2 + " Wildy Points.");
                player.getPacketSender().sendMessage("You received " + per25 / 2 + " PaePoints and your Task Streak has been reset");
                player.getSlayer().setTaskStreak(0);


            } else if (player.getSlayer().getTaskStreak() == 20) {
                player.setPaePoints(player.getPaePoints() + per10 / 2);
                player.WildyPoints += per10 / 2;
                player.getPacketSender().sendMessage("You received " + per10 / 2 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() == 15) {
                player.setPaePoints(player.getPaePoints() + per5 / 2);
                player.WildyPoints += per5 / 2;
                player.getPacketSender().sendMessage("You received " + per5 / 2 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() == 10) {
                player.setPaePoints(player.getPaePoints() + per10 / 2);
                player.WildyPoints += per10 / 2;
                player.getPacketSender().sendMessage("You received " + per10 / 2 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() == 5) {
                player.setPaePoints(player.getPaePoints() + per5 / 2);
                player.WildyPoints += per5 / 2;
                player.getPacketSender().sendMessage("You received " + per5 / 2 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() != 0) {
                player.setPaePoints(player.getPaePoints() + pointsReceived / 2);
                player.WildyPoints += pointsReceived / 2;
                player.getPacketSender().sendMessage("You received " + pointsReceived / 2 + " PaePoints.");
            }

        } else {
            if (player.getSlayer().getTaskStreak() == 25) {
                player.setPaePoints(player.getPaePoints() + per25);
                player.getPacketSender().sendMessage("You received " + per25 + " PaePoints and your Task Streak has been reset");
                player.getSlayer().setTaskStreak(0);
            } else if (player.getSlayer().getTaskStreak() == 20) {
                player.setPaePoints(player.getPaePoints() + per10);
                player.getPacketSender().sendMessage("You received " + per10 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() == 15) {
                player.setPaePoints(player.getPaePoints() + per5);
                player.getPacketSender().sendMessage("You received " + per5 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() == 10) {
                player.setPaePoints(player.getPaePoints() + per10);
                player.getPacketSender().sendMessage("You received " + per10 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() == 5) {
                player.setPaePoints(player.getPaePoints() + per5);
                player.getPacketSender().sendMessage("You received " + per5 + " PaePoints.");
            } else if (player.getSlayer().getTaskStreak() != 0) {
                player.setPaePoints(player.getPaePoints() + pointsReceived);
                player.getPacketSender().sendMessage("You received " + pointsReceived + " PaePoints.");
            }
        }
        player.getPointsHandler().refreshPanel();
    }

    public boolean assignDuoSlayerTask() {
        player.getPacketSender().sendInterfaceRemoval();
        if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
            player.getPacketSender().sendMessage("You already have a Slayer task.");
            return false;
        }
        Player partner = World.getPlayerByName(duoPartner);
        if (partner == null) {
            player.getPacketSender().sendMessage("");
            player.getPacketSender().sendMessage("You can only get a new Slayer task when your duo partner is online.");
            return false;
        }
        if (partner.getSlayer().getDuoPartner() == null || !partner.getSlayer().getDuoPartner().equals(player.getUsername())) {
            resetDuo(player, null);
            return false;
        }
        if (partner.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
            player.getPacketSender().sendMessage("Your partner already has a Slayer task.");
            return false;
        }
        if (partner.getSlayer().getSlayerMaster() != player.getSlayer().getSlayerMaster()) {
            player.getPacketSender().sendMessage("You and your partner need to have the same Slayer master.");
            return false;
        }
        if (partner.getInterfaceId() > 0) {
            player.getPacketSender().sendMessage("Your partner must close all their open interfaces.");
            return false;
        }
        return true;
    }

    public static boolean checkDuoSlayer(Player p, boolean login) {
        if (p.getSlayer().getDuoPartner() == null) {
            return false;
        }
        Player partner = World.getPlayerByName(p.getSlayer().getDuoPartner());
        if (partner == null) {
            return false;
        }
        if (partner.getSlayer().getDuoPartner() == null || !partner.getSlayer().getDuoPartner().equals(p.getUsername())) {
            resetDuo(p, null);
            return false;
        }
        if (partner.getSlayer().getSlayerMaster() != p.getSlayer().getSlayerMaster()) {
            resetDuo(p, partner);
            return false;
        }
        if (partner.getLocation() != p.getLocation()) {
            return false;
        }
        if (login) {
            p.getSlayer().setSlayerTask(partner.getSlayer().getSlayerTask());
            p.getSlayer().setAmountToSlay(partner.getSlayer().getAmountToSlay());
        }

        return true;
    }

    public static void resetDuo(Player player, Player partner) {
        if (partner != null) {
            if (partner.getSlayer().getDuoPartner() != null && partner.getSlayer().getDuoPartner().equals(player.getUsername())) {
                partner.getSlayer().setDuoPartner(null);
                partner.getPacketSender().sendMessage("Your Slayer duo team has been disbanded.");
                PlayerPanel.refreshPanel(partner);
            }
        }
        player.getSlayer().setDuoPartner(null);
        player.getPacketSender().sendMessage("Your Slayer duo team has been disbanded.");
        PlayerPanel.refreshPanel(player);
    }

    public void handleInvitation(boolean accept) {
        if (duoInvitation != null) {
            Player inviteOwner = World.getPlayerByName(duoInvitation);
            if (inviteOwner != null) {
                if (accept) {
                    if (duoPartner != null) {
                        player.getPacketSender().sendMessage("You already have a Slayer duo partner.");
                        inviteOwner.getPacketSender().sendMessage(player.getUsername() + " already has a Slayer duo partner.");
                        return;
                    }
                    inviteOwner.getPacketSender().sendMessage(player.getUsername() + " has joined your duo Slayer team.").sendMessage("Seek respective Slayer master for a task.");
                    inviteOwner.getSlayer().setDuoPartner(player.getUsername());
                    PlayerPanel.refreshPanel(inviteOwner);
                    player.getPacketSender().sendMessage("You have joined " + inviteOwner.getUsername() + "'s duo Slayer team.");
                    player.getSlayer().setDuoPartner(inviteOwner.getUsername());
                    PlayerPanel.refreshPanel(player);
                } else {
                    player.getPacketSender().sendMessage("You've declined the invitation.");
                    inviteOwner.getPacketSender().sendMessage(player.getUsername() + " has declined your invitation.");
                }
            } else
                player.getPacketSender().sendMessage("Failed to handle the invitation.");
        }
    }

    public void handleSlayerRingTP(int itemId) {
        if (!player.getClickDelay().elapsed(4500))
            return;
        if (player.getMovementQueue().isLockMovement())
            return;
        SlayerTasks task = getSlayerTask();
        if (task == SlayerTasks.NO_TASK)
            return;
        Position slayerTaskPos = new Position(task.getTaskPosition().getX(), task.getTaskPosition().getY(), task.getTaskPosition().getZ());
        if (!TeleportHandler.checkReqs(player, slayerTaskPos))
            return;
        TeleportHandler.teleportPlayer(player, slayerTaskPos, player.getSpellbook().getTeleportType());
        Item slayerRing = new Item(itemId);
        player.getInventory().delete(slayerRing);
        if (slayerRing.getId() < 13288)
            player.getInventory().add(slayerRing.getId() + 1, 1);
        else
            player.getPacketSender().sendMessage("Your Ring of Slaying crumbles to dust.");
    }

    public int getAmountToSlay() {
        return this.amountToSlay;
    }

    public Slayer setAmountToSlay(int amountToSlay) {
        this.amountToSlay = amountToSlay;
        return this;
    }

    public int getTaskStreak() {
        return this.taskStreak;
    }

    public Slayer setTaskStreak(int taskStreak) {
        this.taskStreak = taskStreak;
        return this;
    }

    public SlayerTasks getLastTask() {
        return this.lastTask;
    }

    public void setLastTask(SlayerTasks lastTask) {
        this.lastTask = lastTask;
    }

    public boolean doubleSlayerXP = false;

    public Slayer setDuoPartner(String duoPartner) {
        this.duoPartner = duoPartner;
        return this;
    }

    public String getDuoPartner() {
        return duoPartner;
    }

    public SlayerTasks getSlayerTask() {
        return slayerTask;
    }

    public Slayer setSlayerTask(SlayerTasks slayerTask) {
        this.slayerTask = slayerTask;
        return this;
    }

    public SlayerMaster getSlayerMaster() {
        return slayerMaster;
    }

    public void setSlayerMaster(SlayerMaster master) {
        this.slayerMaster = master;
    }

    public void setDuoInvitation(String player) {
        this.duoInvitation = player;
    }

    public static boolean handleRewardsInterface(Player player, int button) {
        if (player.getInterfaceId() == 36000) {
            switch (button) {
                case 36002:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 36014:
				/*if(player.getPaePoints() < 10) {
					player.getPacketSender().sendMessage("You do not have 10 PaePoints.");
					return true;
				}
				player.getPointsHandler().refreshPanel();
				player.setPaePoints(player.getPaePoints() - 10);
				player.getSkillManager().addExperience(Skill.SLAYER, 10000);
				player.getPacketSender().sendMessage("You've bought 10000 Slayer XP for 10 PaePoints.");*/
                    player.getPacketSender().sendMessage("@red@This feature has been removed, as voted by the Community!");
                    break;
                case 36017:
                    if (player.getPaePoints() < 300) {
                        player.getPacketSender().sendMessage("You do not have 300 PaePoints.");
                        return true;
                    }
                    if (player.getSlayer().doubleSlayerXP) {
                        player.getPacketSender().sendMessage("You already have this buff.");
                        return true;
                    }
                    player.setPaePoints(player.getPaePoints() - 300);
                    player.getSlayer().doubleSlayerXP = true;
                    player.getPointsHandler().refreshPanel();
                    player.getPacketSender().sendMessage("You will now permanently receive double Slayer experience.");
                    break;
                case 36005:
                    ShopManager.getShops().get(92).open(player);
                    break;
            }
            player.getPacketSender().sendString(36030, "Current Points:   " + player.getPaePoints());
            return true;
        }
        return false;
    }
}
