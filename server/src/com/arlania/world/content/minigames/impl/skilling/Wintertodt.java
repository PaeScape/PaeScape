package com.arlania.world.content.minigames.impl.skilling;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.clog.CollectionLog;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.content.skill.impl.woodcutting.WoodcuttingData;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;

public class Wintertodt {

    public static int WintertodtHealth = 0;
    public static int burnTimer = 0;
    public static boolean WintertodtBoss = false;

    public static int[] uniques = {220704, 220706, 220708, 220710, 220712};

    public static void enter(Player player) {

        if (player.getLocation() == Location.WINTERTODT) {
            player.getPacketSender().sendInterfaceRemoval();
            player.getMovementQueue().reset();
            player.getClickDelay().reset();
            player.inWintertodt = true;
        }


        player.getPacketSender().sendMessage("@red@Welcome to the Wintertodt!");


        player.getPacketSender().sendInterfaceRemoval();

        //if (!player.getInventory().contains(1351) && !player.getEquipment().contains(1351))
        //player.getInventory().add(1351, 1); //Bronze hatchet

        if (WoodcuttingData.getHatchet(player) == -1)
            player.getInventory().add(1351, 1); //Bronze hatchet

        if (!player.getInventory().contains(590) && !player.getInventory().contains(2946))
            player.getInventory().add(590, 1); //tinderbox

        if (!player.getInventory().contains(946) && !player.getEquipment().contains(946))
            player.getInventory().add(946, 1); //knife

        if (!player.getInventory().contains(2347) && !player.getEquipment().contains(2347))
            player.getInventory().add(2347, 1); //hammer

        if (!WintertodtBoss) {
            WintertodtBoss = true;
            WintertodtHealth = 10000;
        }
    }


    public static void leave(Player player) {
        Location.WINTERTODT.leave(player);
        player.inWintertodt = false;

    }

    public final static void bossFight(Player player) {


    }

    public static void feedBrazier(Player player, final GameObject litBrazier) {

        int kindling = 220696;


        if (player.getInventory().contains(220696) && !player.addingToBrazier) {
            player.addingToBrazier = true;
            player.setCurrentTask(new Task(2, player, true) {
                @Override
                public void execute() {
                    player.getPacketSender().sendInterfaceRemoval();

                    if (burnTimer < 1) {
                        player.getPacketSender().sendMessage("You stop adding kindling to the fire!");
                        stop();
                        return;
                    }

                    if (player.getInventory().contains(2946) && RandomUtility.inclusiveRandom(2) == 1) {
                        player.getPacketSender().sendMessage("Your tinderbox's magic saves your materials!");
                    } else {
                        player.getInventory().delete(220696, 1);
                    }

                    player.performAnimation(new Animation(827));
                    player.getPacketSender().sendMessage("You add some kindling to the fire..");

                    Sounds.sendSound(player, Sound.LIGHT_FIRE);

                    double addxp = player.getSkillManager().getCurrentLevel(Skill.FIREMAKING) * 10;
                    double bonusxp = 1;

                    if (player.getEquipment().contains(220708))
                        bonusxp += .05;
                    if (player.getEquipment().contains(220704))
                        bonusxp += .05;
                    if (player.getEquipment().contains(220706))
                        bonusxp += .05;
                    if (player.getEquipment().contains(220710))
                        bonusxp += .05;
                    if (player.getEquipment().contains(220712))
                        bonusxp += .05;

                    addxp *= bonusxp;


                    player.WintertodtPoints += 25;
                    Wintertodt.WintertodtHealth -= 250;
                    player.getSkillManager().addExperience(Skill.FIREMAKING, addxp);
                    if (!player.getInventory().contains(kindling)) {
                        stop();
                    }

                }

                @Override
                public void stop() {
                    player.addingToBrazier = false;
                    setEventRunning(false);
                    player.performAnimation(new Animation(65535));
                    player.getMovementQueue().setLockMovement(false);
                    //MovementQueue.stepAway(player);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setEntityInteraction(null);
                }
            });
            TaskManager.submit(player.getCurrentTask());


        }

    }

    public static void lightBrazier(Player player, final GameObject unlitBrazier) {

        int litBrazier = 329314;
        int cycles = 20 + RandomUtility.inclusiveRandom(20);

        burnTimer += cycles;

        player.performAnimation(new Animation(733));
        player.getPacketSender().sendMessage("You light the Brazier!");
        player.WintertodtPoints += 25;
        player.getSkillManager().addExperience(Skill.FIREMAKING, 500);

        CustomObjects.deleteGlobalObject(player.getInteractingObject());
        CustomObjects.globalObjectRespawnTask(new GameObject(litBrazier, unlitBrazier.getPosition().copy(), 10, 0), unlitBrazier, cycles);
    }

    public static void brokenBrazier() {

        int litBrazier = 329314;
        int brokenBrazier = 329313;

        GameObject brokenSWbrazier = new GameObject(brokenBrazier, new Position(1620, 3997, 0));
        GameObject brokenNWbrazier = new GameObject(brokenBrazier, new Position(1620, 4015, 0));
        GameObject brokenSEbrazier = new GameObject(brokenBrazier, new Position(1638, 3997, 0));
        GameObject brokenNEbrazier = new GameObject(brokenBrazier, new Position(1638, 4015, 0));

        GameObject litSWbrazier = new GameObject(litBrazier, new Position(1620, 3997, 0));
        GameObject litNWbrazier = new GameObject(litBrazier, new Position(1620, 4015, 0));
        GameObject litSEbrazier = new GameObject(litBrazier, new Position(1638, 3997, 0));
        GameObject litNEbrazier = new GameObject(litBrazier, new Position(1638, 4015, 0));


        GameObject[] brokenBraziers = {brokenSWbrazier, brokenNWbrazier, brokenSEbrazier, brokenNEbrazier};
        GameObject[] litBraziers = {litSWbrazier, litNWbrazier, litSEbrazier, litNEbrazier};

        int random = RandomUtility.inclusiveRandom(brokenBraziers.length - 1);

        CustomObjects.deleteGlobalObject(litBraziers[random]);
        CustomObjects.spawnGlobalObject(brokenBraziers[random]);
    }

    public static void fixBrazier(Player player, final GameObject brokenBrazier) {

        int fixedBrazier = 329312;

        player.performAnimation(new Animation(898));
        player.getPacketSender().sendMessage("You fix the Brazier!");
        player.WintertodtPoints += 25;
        player.getSkillManager().addExperience(Skill.FIREMAKING, player.getSkillManager().getCurrentLevel(Skill.SMITHING) * 10);

        CustomObjects.deleteGlobalObject(player.getInteractingObject());
        CustomObjects.spawnGlobalObject(new GameObject(fixedBrazier, brokenBrazier.getPosition().copy()));
    }

    public static void makeKindling(Player player) {

        int log = 220695;
        int kindling = 220696;

        if (player.Accelerate == 0) {
            player.setCurrentTask(new Task(2, player, true) {
                @Override
                public void execute() {

                    if (!player.getInventory().contains(946)) {
                        player.getPacketSender().sendMessage("You need a Knife to fletch this log.");
                        player.performAnimation(new Animation(65535));
                        stop();
                        return;
                    }
                    if (!player.getInventory().contains(220695)) {
                        player.getPacketSender().sendMessage("You have no more logs.");
                        player.performAnimation(new Animation(65535));
                        stop();
                        return;
                    }
                    player.performAnimation(new Animation(1248));
                    player.getInventory().delete(log, 1);
                    player.getInventory().add(kindling, 1);
                    player.getSkillManager().addExperience(Skill.FLETCHING, player.getSkillManager().getCurrentLevel(Skill.FLETCHING) * 5);
                    Sounds.sendSound(player, Sound.FLETCH_ITEM);
                }
            });
            TaskManager.submit(player.getCurrentTask());
        } else {
            int amountToMake = player.getInventory().getAmount(220695);

            for (int i = 0; i < amountToMake; i++) {
                if (!player.getInventory().contains(946)) {
                    player.getPacketSender().sendMessage("You need a Knife to fletch this log.");
                    player.performAnimation(new Animation(65535));
                    return;
                }
                if (!player.getInventory().contains(220695)) {
                    player.getPacketSender().sendMessage("You have no more logs.");
                    player.performAnimation(new Animation(65535));
                    return;
                }
                player.performAnimation(new Animation(1248));
                player.getInventory().delete(log, 1);
                player.getInventory().add(kindling, 1);
                player.getSkillManager().addExperience(Skill.FLETCHING, player.getSkillManager().getCurrentLevel(Skill.FLETCHING) * 5);
                Sounds.sendSound(player, Sound.FLETCH_ITEM);

            }


        }


    }

    public static void loot(Player player) {

        player.setPaePoints(player.getPaePoints() + 5);
        player.getPacketSender().sendMessage("You've gained 5 Paepoints for completing the Wintertodt!");
        player.getPacketSender().sendMessage("You now have " + player.getPaePoints() + " PaePoints!");
        player.WintertodtKC++;
        player.getPacketSender().sendMessage("You now have " + player.WintertodtKC + " Wintertodt completions!");
        player.getSkillManager().addExperience(Skill.DUNGEONEERING, 1000);

        player.getAchievementTracker().progress(AchievementData.COMPLETE_25_WINTERTODT, 1);
        player.getAchievementTracker().progress(AchievementData.COMPLETE_100_WINTERTODT, 1);
        player.getAchievementTracker().progress(AchievementData.COMPLETE_250_WINTERTODT, 1);

        if (player.WintertodtKC >= 250)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_250_WINTERTODT, 250);
        if (player.WintertodtKC >= 100)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_100_WINTERTODT, 100);
        if (player.WintertodtKC >= 25)
            player.getAchievementTracker().progress(AchievementData.COMPLETE_25_WINTERTODT, 25);

        player.getCollectionLog().addKill(CollectionLog.CustomCollection.Wintertodt.getId());


        double addxp = player.getSkillManager().getCurrentLevel(Skill.FIREMAKING) * 10;
        double bonusxp = 1;

        if (player.getEquipment().contains(220708))
            bonusxp += .05;
        if (player.getEquipment().contains(220704))
            bonusxp += .05;
        if (player.getEquipment().contains(220706))
            bonusxp += .05;
        if (player.getEquipment().contains(220710))
            bonusxp += .05;
        if (player.getEquipment().contains(220712))
            bonusxp += .05;

        addxp *= bonusxp;


        player.getSkillManager().addExperience(Skill.FIREMAKING, addxp);

        int qty = 0;

        qty = player.WintertodtPoints / 500;

        if (qty > 5)
            qty = 5;

        player.getInventory().add(220703, qty);
        player.WintertodtPoints = 0;


        if (RandomUtility.inclusiveRandom(25) == 1) {
            int uniqueLoot = uniques[RandomUtility.inclusiveRandom(uniques.length - 1)];
            player.getCollectionLog().handleDrop(CustomCollection.Wintertodt.getId(), uniqueLoot, 1);
            player.getInventory().add(uniqueLoot, 1);
        }


    }

    public static void dropSnow(Player player) {

        Position pos = randomLocation();
        NPC spawn = NPC.of(5090, pos);
        World.register(spawn);
        NPC spawn1 = NPC.of(5090, new Position(pos.getX(), pos.getY() - 1, 0));
        World.register(spawn1);
        new Projectile(spawn1, spawn, 369, 140, 1, 220, 0, 90).sendProjectile();

        TaskManager.submit(new Task(4) {

            @Override
            public void execute() {
                player.getPacketSender().sendGlobalGraphic(new Graphic(311), pos);


                int maxHit = player.getSkillManager().getMaxLevel(Skill.CONSTITUTION) / 100;

                if (player.getPosition().sameAs(pos)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(maxHit * 6 / 10, maxHit), Hitmask.RED, CombatIcon.NONE));
                } else if (Locations.goodDistance(player.getPosition(), pos, 1)) {
                    player.dealDamage(new Hit(RandomUtility.inclusiveRandom(maxHit * 4 / 10, maxHit * 8 / 10), Hitmask.RED, CombatIcon.NONE));
                }

                World.deregister(spawn);
                World.deregister(spawn1);
                stop();
            }
        });
    }


    public static Position randomLocation() {
        ArrayList<Position> positions = new ArrayList<>();

        for (int x = 1619; x < 1642; x++) {
            for (int y = 3988; y < 4000; y++) {
                if ((x == 1619 || x == 1620 || x == 1621 || x == 1622 || x == 1638 || x == 1639 || x == 1640 || x == 1641) &&
                        (y >= 3988 && y <= 3999)) {
                    positions.add(new Position(x, y, 0));
                }
            }
        }

        return positions.get(RandomUtility.inclusiveRandom(positions.size() - 1));
    }


}
