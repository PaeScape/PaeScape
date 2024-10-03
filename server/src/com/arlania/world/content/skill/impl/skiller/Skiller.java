package com.arlania.world.content.skill.impl.skiller;

import com.arlania.model.Skill;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.PetAbilities;
import com.arlania.world.content.PlayerPanel;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.entity.impl.player.Player;

public class Skiller {

    private final Player player;

    public Skiller(Player p) {
        this.player = p;
    }

    private SkillerTasks skillerTask = SkillerTasks.NO_TASK, lastTask = SkillerTasks.NO_TASK;
    private int amountToSkill, taskStreak;

    public void assignTask() {
		/*boolean hasTask = getSkillerTask() != SkillerTasks.NO_TASK && player.getSkiller().getLastTask() != getSkillerTask();
		if(hasTask) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}*/
        int[] taskData = SkillerTasks.getNewTaskData(player, false);
        int skillerTaskId = taskData[0], skillerTaskAmount = taskData[1];

        SkillerTasks taskToSet = SkillerTasks.forId(skillerTaskId);

        //check level requirements for task
        if (taskToSet.getreqLvl() > player.getSkillManager().getCurrentLevel(taskToSet.getSkill()) ||
                taskToSet.getSkill() != player.getSkillerSkill()) {
            assignTask();
            return;
        }

        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSkill = skillerTaskAmount;
        this.skillerTask = taskToSet;
        player.getPacketSender().sendMessage("Your new Skilling tasks is  @blu@" + Misc.ucFirst(SkillerTasks.forId(taskData[0]).name().toLowerCase().replaceAll("_", " ") + " @red@" + taskData[1]));

        PlayerPanel.refreshPanel(player);
    }

    public void assignWildernessTask() {
		/*boolean hasTask = getSkillerTask() != SkillerTasks.NO_TASK && player.getSkiller().getLastTask() != getSkillerTask();
		if(hasTask) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}*/

        if (player.getSkillManager().getCurrentLevel(Skill.COOKING) < 98 || player.getSkillManager().getCurrentLevel(Skill.FISHING) < 95 ||
                player.getSkillManager().getCurrentLevel(Skill.RUNECRAFTING) < 90 || player.getSkillManager().getCurrentLevel(Skill.MINING) < 90) {
            player.getPacketSender().sendMessage("You must have 98 Cooking, 95 Fishing, 90 RC, and 90 Mining to get a Wilderness Task.");
            return;
        }

        int[] taskData = SkillerTasks.getNewTaskData(player, true);
        int skillerTaskId = taskData[0], skillerTaskAmount = taskData[1];

        SkillerTasks taskToSet = SkillerTasks.forId(skillerTaskId);


        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSkill = skillerTaskAmount;
        this.skillerTask = taskToSet;
        player.getPacketSender().sendMessage("Your new Wild Skilling tasks is  @blu@" + Misc.ucFirst(SkillerTasks.forId(taskData[0]).name().toLowerCase().replaceAll("_", " ") + " @red@" + taskData[1]));

        PlayerPanel.refreshPanel(player);
    }

    public void assignRepeatTask(Player player) {


        SkillerTasks taskToSet = player.getSkiller().getLastTask();
        int skillerTaskAmount = 100;

        taskToSet = player.getSkiller().getLastTask();

        int taskQuantity = taskToSet.getTaskAmount();

        taskQuantity = (taskQuantity) * (1 + (player.Accelerate / 2));

        if (player.artisanQty == "max")
            skillerTaskAmount = taskQuantity;
        else if (player.artisanQty == "min")
            skillerTaskAmount = taskQuantity / 2;
        else
            skillerTaskAmount = RandomUtility.inclusiveRandom(taskQuantity / 2, taskQuantity);


        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSkill = skillerTaskAmount;
        this.skillerTask = taskToSet;

        PlayerPanel.refreshPanel(player);
    }

    public void assignArtisanTask(Player player, int skillerTaskId) {

        SkillerTasks taskToSet = SkillerTasks.forId(skillerTaskId);

        int[] taskData = SkillerTasks.getNewArtisanTaskData(skillerTaskId, player);

        int skillerTaskAmount = taskData[1];
        taskToSet = SkillerTasks.forId(skillerTaskId);

        int taskQuantity = SkillerTasks.forId(skillerTaskId).getTaskAmount();

        taskQuantity = (taskQuantity) * (1 + (player.Accelerate / 2));

        if (player.artisanQty == "max")
            skillerTaskAmount = taskQuantity;
        else if (player.artisanQty == "min")
            skillerTaskAmount = taskQuantity / 2;
        else
            skillerTaskAmount = RandomUtility.inclusiveRandom(taskQuantity / 2, taskQuantity);

        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSkill = skillerTaskAmount;
        this.skillerTask = taskToSet;

        PlayerPanel.refreshPanel(player);
    }

    public void resetSkillerTask() {
        if (player.getSkillerSkill() == Skill.WOODCUTTING)
            player.getSkiller().setSkillerTask(SkillerTasks.NO_TASK);
        if (player.getSkillerSkill() == Skill.MINING)
            player.getSkiller().setSkillerTask(SkillerTasks.NO_TASK_MINING);
        if (player.getSkillerSkill() == Skill.FISHING)
            player.getSkiller().setSkillerTask(SkillerTasks.NO_TASK_FISHING);
        if (player.getSkillerSkill() == Skill.THIEVING)
            player.getSkiller().setSkillerTask(SkillerTasks.NO_TASK_THIEVING);
        if (player.getSkillerSkill() == Skill.HUNTER)
            player.getSkiller().setSkillerTask(SkillerTasks.NO_TASK_HUNTER);
        if (player.getSkillerSkill() == Skill.DUNGEONEERING)
            player.getSkiller().setSkillerTask(SkillerTasks.NO_TASK_DG);

        this.amountToSkill = 0;
        assignTask();
        PlayerPanel.refreshPanel(player);
    }

    public void resetSkillerSkill() {

        Skill[] Skills = {Skill.WOODCUTTING, Skill.MINING, Skill.FISHING, Skill.THIEVING, Skill.HUNTER, Skill.DUNGEONEERING};

        if (player.getSkillerSkill() == Skills[0])
            player.setSkillerSkill(Skills[1]);
        else if (player.getSkillerSkill() == Skills[1])
            player.setSkillerSkill(Skills[2]);
        else if (player.getSkillerSkill() == Skills[2])
            player.setSkillerSkill(Skills[3]);
        else if (player.getSkillerSkill() == Skills[3])
            player.setSkillerSkill(Skills[4]);
        else if (player.getSkillerSkill() == Skills[4])
            player.setSkillerSkill(Skills[5]);
        else if (player.getSkillerSkill() == Skills[5])
            player.setSkillerSkill(Skills[0]);


        assignTask();
        PlayerPanel.refreshPanel(player);
    }

    public void gatheredSkill(Object obj) {
        if (skillerTask != SkillerTasks.NO_TASK) {
            for (int i = 0; i < skillerTask.getObjId().length; i++) {
                if (skillerTask.getObjId()[i] == player.getInteractingObject().getId()) {
                    handleSkillerTaskGather(true);
                }
            }
        }
    }

    public void handleSkillerTaskGather(boolean giveXp) {
        int xp = (skillerTask.getXP() / 2) + RandomUtility.inclusiveRandom(skillerTask.getXP());

        if (giveXp) {
            player.getSkillManager().addExperience(skillerTask.getSkill(), xp);
        }

        if (amountToSkill == 0)
            return;
        if (amountToSkill > 1) {
            amountToSkill--;
        } else {
            player.getPacketSender().sendMessage("").sendMessage("You've completed your Skiller task! Return to Max for another task.");
            taskStreak++;

            player.totalSkillerTasks++;

            player.getAchievementTracker().progress(AchievementData.COMPLETE_10_SKILLER_TASKS, 1);
            player.getAchievementTracker().progress(AchievementData.COMPLETE_100_SKILLER_TASKS, 1);
            player.getAchievementTracker().progress(AchievementData.COMPLETE_1000_SKILLER_TASKS, 1);

            boolean wildyTask = "wild" == skillerTask.getType();

            givePoints(skillerTask, wildyTask);

            if (PetAbilities.checkPetAbilities(player, "tryout") && RandomUtility.inclusiveRandom(1, 100) > 50) {
                player.getPacketSender().sendMessage("You've completed your Skiller task!");
                player.getPacketSender().sendMessage("Your Pet Tryout automatically repeats your last task!");
                lastTask = skillerTask;
                assignRepeatTask(player);
            } else {
                player.getPacketSender().sendMessage("You've completed your Skiller task! Return to Max for another one.");
                lastTask = skillerTask;
                skillerTask = SkillerTasks.NO_TASK;
                amountToSkill = 0;
            }


            if (player.getSkillerSkill() == Skill.WOODCUTTING)
                skillerTask = SkillerTasks.NO_TASK;
            else if (player.getSkillerSkill() == Skill.MINING)
                skillerTask = SkillerTasks.NO_TASK_MINING;
            else if (player.getSkillerSkill() == Skill.FISHING)
                skillerTask = SkillerTasks.NO_TASK_FISHING;
            else if (player.getSkillerSkill() == Skill.DUNGEONEERING)
                skillerTask = SkillerTasks.NO_TASK_DG;
            else if (player.getSkillerSkill() == Skill.HUNTER)
                skillerTask = SkillerTasks.NO_TASK_HUNTER;
            else if (player.getSkillerSkill() == Skill.THIEVING)
                skillerTask = SkillerTasks.NO_TASK_THIEVING;

        }

        PlayerPanel.refreshPanel(player);
    }

    @SuppressWarnings("incomplete-switch")
    public void givePoints(SkillerTasks skillerTask, boolean wildyTask) {

        int pointsReceived = 1;

        if (player.getSkiller().getSkillerTask().getreqLvl() >= 90) {
            pointsReceived = 25;
            player.getSkillManager().addExperience(Skill.SKILLER, 7500);
        } else if (player.getSkiller().getSkillerTask().getreqLvl() >= 75) {
            pointsReceived = 20;
            player.getSkillManager().addExperience(Skill.SKILLER, 5000);
        } else if (player.getSkiller().getSkillerTask().getreqLvl() >= 50) {
            pointsReceived = 15;
            player.getSkillManager().addExperience(Skill.SKILLER, 3000);

        } else if (player.getSkiller().getSkillerTask().getreqLvl() >= 25) {
            pointsReceived = 10;
            player.getSkillManager().addExperience(Skill.SKILLER, 2000);

        } else if (player.getSkiller().getSkillerTask().getreqLvl() >= 1) {
            pointsReceived = 5;
            player.getSkillManager().addExperience(Skill.SKILLER, 1000);
        }

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_SKILLER_PP) || (player.doubleSkillerPointsEvent && player.personalEvent))
            pointsReceived *= 2;

        double donatorbonus = 1;

        if (player.getStaffRights().getStaffRank() > 5)
            donatorbonus = 3;
        else if (player.getStaffRights().getStaffRank() > 3)
            donatorbonus = 2;
        else if (player.getStaffRights().getStaffRank() == 2 || player.getStaffRights().getStaffRank() == 3)
            donatorbonus = 1.5;

        pointsReceived = (int) (pointsReceived * donatorbonus);

        if(player.Infernal) {
            pointsReceived *= 2;
        }

        //Artisan double points

        if (player.Artisan >= 4) {
            if (RandomUtility.inclusiveRandom(1, 100) <= 15) {
                pointsReceived *= 2;
            }
        } else if (player.Artisan >= 2) {
            if (RandomUtility.inclusiveRandom(1, 100) <= 10) {
                pointsReceived *= 2;
            }
        }

        int per5 = pointsReceived * 2;
        int per10 = pointsReceived * 3;
        int per25 = pointsReceived * 5;

        if (player.Artisan >= 3) {
            per5 *= 2;
            per10 *= 2;
            per25 *= 2;
        }

        if (player.getSkiller().getTaskStreak() == 25) {
            player.setPaePoints(player.getPaePoints() + per25);
            player.getPacketSender().sendMessage("You received " + per25 + " PaePoints and your Task Streak has been reset");
            player.getSkiller().setTaskStreak(0);
        } else if (player.getSkiller().getTaskStreak() == 20) {
            player.setPaePoints(player.getPaePoints() + per10);
            player.getPacketSender().sendMessage("You received " + per10 + " PaePoints.");
        } else if (player.getSkiller().getTaskStreak() == 15) {
            player.setPaePoints(player.getPaePoints() + per5);
            player.getPacketSender().sendMessage("You received " + per5 + " PaePoints.");
        } else if (player.getSkiller().getTaskStreak() == 10) {
            player.setPaePoints(player.getPaePoints() + per10);
            player.getPacketSender().sendMessage("You received " + per10 + " PaePoints.");
        } else if (player.getSkiller().getTaskStreak() == 5) {
            player.setPaePoints(player.getPaePoints() + per5);
            player.getPacketSender().sendMessage("You received " + per5 + " PaePoints.");
        } else if (player.getSkiller().getTaskStreak() != 0) {
            player.setPaePoints(player.getPaePoints() + pointsReceived);
            player.getPacketSender().sendMessage("You received " + pointsReceived + " PaePoints.");
        }

        int randLoot = 25;

        if (player.Artisan >= 1)
            randLoot = 20;

        if (RandomUtility.exclusiveRandom(randLoot) == 1)
            player.getInventory().add(18782, 1);
        else
            player.getInventory().add(11137, 1);

        if(wildyTask) {
            player.getInventory().add(223490, RandomUtility.inclusiveRandom(1, 3));
            player.getPacketSender().sendMessage("You receive a Larran's Key for completing your Skiller Task!");
        }


        player.getPointsHandler().refreshPanel();
    }

    public int getAmountToSkill() {
        return this.amountToSkill;
    }

    public Skiller setAmountToSkill(int amountToSkill) {
        this.amountToSkill = amountToSkill;
        return this;
    }

    public int getTaskStreak() {
        return this.taskStreak;
    }

    public Skiller setTaskStreak(int taskStreak) {
        this.taskStreak = taskStreak;
        return this;
    }

    public SkillerTasks getLastTask() {
        return this.lastTask;
    }

    public void setLastTask(SkillerTasks lastTask) {
        this.lastTask = lastTask;
    }

    public SkillerTasks getSkillerTask() {
        return skillerTask;
    }

    public Skiller setSkillerTask(SkillerTasks skillerTask) {
        this.skillerTask = skillerTask;
        return this;
    }


}
