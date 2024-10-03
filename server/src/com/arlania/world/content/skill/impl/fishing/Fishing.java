package com.arlania.world.content.skill.impl.fishing;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.entity.impl.player.Player;

public class Fishing {

    public enum Spot {

        SMALLNET(316, new int[]{317, 321}, new int[]{315, 319}, 303, -1, new int[]{1, 15}, new int[]{50, 60}, 621, false),

        BAIT(316, new int[]{327, 345}, new int[]{325, 347}, 307, 313, new int[]{5, 10}, new int[]{80, 100}, 623, true),

        BIGNET(313, new int[]{353, 341, 363}, new int[]{355, 339, 365}, 305, -1, new int[]{16, 23, 46}, new int[]{100, 150, 260}, 620, false),

        HARPOON2(313, new int[]{383}, new int[]{385}, 311, -1, new int[]{76}, new int[]{400}, 618, true),

        MONK_FISH(318, new int[]{7944, 389}, new int[]{7946, 391}, 305, -1, new int[]{62, 81}, new int[]{340, 500}, 621, false),

        LURE(318, new int[]{335, 331}, new int[]{333, 329}, 309, 314, new int[]{20, 30}, new int[]{210, 270}, 623, true),

        HARPOON(312, new int[]{359, 371}, new int[]{361, 373}, 311, -1, new int[]{35, 50}, new int[]{300, 450}, 618, true),

        CAGE(312, new int[]{377}, new int[]{379}, 301, -1, new int[]{40}, new int[]{240}, 619, false),

        ANGLERFISH(309, new int[]{213439}, new int[]{213441}, 311, -1, new int[]{95}, new int[]{700}, 618, false),

        ROCKTAIL(10091, new int[]{15270}, new int[]{15272}, 309, 15263, new int[]{91}, new int[]{600}, 623, false),

        SHR(-1, new int[]{-1}, new int[]{-1}, -1, -1, new int[]{-1}, new int[]{-1}, 618, false);

        int npcId, equipment, bait, anim;
        int[] rawFish, cookedFish, fishingReqs, xp;
        boolean second;

        Spot(int npcId, int[] rawFish, int[] cookedFish, int equipment, int bait, int[] fishingReqs, int[] xp, int anim, boolean second) {
            this.npcId = npcId;
            this.rawFish = rawFish;
            this.cookedFish = cookedFish;
            this.equipment = equipment;
            this.bait = bait;
            this.fishingReqs = fishingReqs;
            this.xp = xp;
            this.anim = anim;
            this.second = second;
        }

        public int getNPCId() {
            return npcId;
        }

        public int[] getRawFish() {
            return rawFish;
        }

        public int[] getCookedFish() {
            return cookedFish;
        }

        public int getEquipment() {
            return equipment;
        }

        public int getBait() {
            return bait;
        }

        public int[] getLevelReq() {
            return fishingReqs;
        }

        public boolean getSecond() {
            return second;
        }

        public int[] getXp() {
            return xp;
        }

        public int getAnim() {
            return anim;
        }
    }

    public static Spot forSpot(int npcId, boolean secondClick) {
        for (Spot s : Spot.values()) {
            if (secondClick) {
                if (s.getSecond()) {
                    if (s.getNPCId() == npcId) {
                        if (s != null) {
                            return s;
                        }
                    }
                }
            } else {
                if (s.getNPCId() == npcId && !s.getSecond()) {
                    if (s != null) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

    public static void setupFishing(Player p, Spot s) {
        if (s == null)
            return;
        if (p.getInventory().getFreeSlots() <= 0) {
            p.getPacketSender().sendMessage("You do not have any free inventory space.");
            p.getSkillManager().stopSkilling();
            return;
        }

        if (s == Spot.SHR) {

            //95
            if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.ANGLERFISH.getLevelReq()[0])
                startFishing(p, Spot.ANGLERFISH);
                //91
            else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.ROCKTAIL.getLevelReq()[0])
                startFishing(p, Spot.ROCKTAIL);
                //76
            else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.HARPOON2.getLevelReq()[0])
                startFishing(p, Spot.HARPOON2);
                //62
            else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.MONK_FISH.getLevelReq()[0])
                startFishing(p, Spot.MONK_FISH);
                //40
            else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.CAGE.getLevelReq()[0])
                startFishing(p, Spot.CAGE);
                //35
            else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.HARPOON.getLevelReq()[0])
                startFishing(p, Spot.HARPOON);
                //20
            else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.LURE.getLevelReq()[0])
                startFishing(p, Spot.LURE);
                //1
            else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= Spot.SMALLNET.getLevelReq()[0])
                startFishing(p, Spot.SMALLNET);

            return;
        }


        if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= s.getLevelReq()[0]) {
            if (p.getInventory().contains(s.getEquipment()) ||
                    ((p.getInventory().contains(221028) || p.getEquipment().contains(221028)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 61) ||
                    ((p.getInventory().contains(221031) || p.getEquipment().contains(221031)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 75) ||
                    ((p.getInventory().contains(223762) || p.getEquipment().contains(223762)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 70) ||
                    ((p.getInventory().contains(13661) || p.getEquipment().contains(13661)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 75)) {

                if (s.getBait() == 314) {
                    if (p.getInventory().contains(314) || p.getInventory().contains(2950))
                        startFishing(p, s);

                } else if (s.getBait() != -1) {
                    if (p.getInventory().contains(s.getBait())) {
                        startFishing(p, s);
                    } else {
                        String baitName = ItemDefinition.forId(s.getBait()).getName();
                        if (baitName.contains("Feather") || baitName.contains("worm"))
                            baitName += "s";
                        p.getPacketSender().sendMessage("You need some " + baitName + " to fish here.");
                        p.performAnimation(new Animation(65535));
                    }
                } else {
                    startFishing(p, s);
                }
            } else {
                if (s == Spot.SHR) {
                    startFishing(p, s);
                    return;
                }

                String def = ItemDefinition.forId(s.getEquipment()).getName().toLowerCase();
                p.getPacketSender().sendMessage("You need " + Misc.anOrA(def) + " " + def + " to fish here.");
            }
        } else {
            p.getPacketSender().sendMessage("You need a fishing level of at least " + s.getLevelReq()[0] + " to fish here.");
        }
    }

    public static void startFishing(final Player p, final Spot s) {
        p.getSkillManager().stopSkilling();
        final int fishIndex = RandomUtility.inclusiveRandom(100) >= 70 ? getMax(p, s.fishingReqs) : (getMax(p, s.fishingReqs) != 0 ? getMax(p, s.fishingReqs) - 1 : 0);
        if (p.getInteractingObject() != null && p.getInteractingObject().getId() != 8702)
            p.setDirection(s == Spot.MONK_FISH ? Direction.WEST : Direction.NORTH);

        if (p.getInventory().contains(221028) || p.getEquipment().contains(221028) ||
                p.getInventory().contains(221031) || p.getEquipment().contains(221031) ||
                p.getInventory().contains(223762) || p.getEquipment().contains(223762) ||
                ((p.getInventory().contains(13661) || p.getEquipment().contains(13661)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 75)) {
            p.performAnimation(new Animation(618));
        } else {
            p.performAnimation(new Animation(s.getAnim()));
        }


        p.setCurrentTask(new Task(2, p, false) {
            int cycle = 0;
            final int reqCycle = Fishing.getDelay(s.getLevelReq()[fishIndex], p);

            @Override
            public void execute() {
                if (p.getInventory().getFreeSlots() == 0) {
                    p.getPacketSender().sendMessage("You have run out of inventory space.");
                    stop();
                    return;
                }
                if (s.getBait() == 314 && p.getInventory().contains(2950)) {
                } else if (!p.getInventory().contains(s.getBait())) {
                    p.getPacketSender().sendMessage("You need feathers to fish here.");
                    stop();
                    return;
                }
                cycle++;
                if (p.getInventory().contains(221028) || p.getEquipment().contains(221028) ||
                        p.getInventory().contains(221031) || p.getEquipment().contains(221031) ||
                        p.getInventory().contains(223762) || p.getEquipment().contains(223762) ||
                        ((p.getInventory().contains(13661) || p.getEquipment().contains(13661)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 75)) {
                    p.performAnimation(new Animation(618));
                } else {
                    p.performAnimation(new Animation(s.getAnim()));
                }
                if (cycle >= RandomUtility.inclusiveRandom(1) + reqCycle) {
                    String def = ItemDefinition.forId(s.getRawFish()[fishIndex]).getName();
                    if (def.endsWith("s"))
                        def = def.substring(0, def.length() - 1);
                    if (p.personalFilterGathering) {
                        p.getPacketSender().sendMessage("You catch " + Misc.anOrA(def) + " " + def.toLowerCase().replace("_", " ") + ".");
                    }
                    if (s.getBait() != -1)
                        p.getInventory().delete(s.getBait(), 1);

                    int qty = p.acceleratedResources();

                    if (p.getSkillManager().hasInfernalTools(Skill.FISHING, Skill.COOKING)) {

                        if (p.checkAchievementAbilities(p, "gatherer")) {
                            p.getInventory().add(s.getCookedFish()[fishIndex] + 1, qty);
                        } else if (p.getGameMode() == GameMode.SEASONAL_IRONMAN && p.Harvester) {
                            p.getInventory().add(s.getCookedFish()[fishIndex] + 1, qty);
                        } else {
                            p.getInventory().add(s.getCookedFish()[fishIndex], qty);
                        }

                        if (def.toLowerCase().contains("shrimp")) {
                            p.getAchievementTracker().progress(AchievementData.COOK_10_SHRIMP, qty);
                        } else if (def.toLowerCase().contains("trout")) {
                            p.getAchievementTracker().progress(AchievementData.COOK_25_TROUT, qty);
                        } else if (def.toLowerCase().contains("salmon")) {
                            p.getAchievementTracker().progress(AchievementData.COOK_50_SALMON, qty);
                        } else if (def.toLowerCase().contains("lobster")) {
                            p.getAchievementTracker().progress(AchievementData.COOK_100_LOBSTER, qty);
                        } else if (def.toLowerCase().contains("shark")) {
                            p.getAchievementTracker().progress(AchievementData.COOK_250_SHARK, qty);
                        } else if (def.toLowerCase().contains("rocktail")) {
                            p.getAchievementTracker().progress(AchievementData.COOK_1000_ROCKTAIL, qty);
                        }
                    } else {
                        if (p.checkAchievementAbilities(p, "gatherer")) {
                            p.getInventory().add(s.getRawFish()[fishIndex] + 1, qty);
                        } else if (p.getGameMode() == GameMode.SEASONAL_IRONMAN && p.Harvester) {
                            p.getInventory().add(s.getRawFish()[fishIndex] + 1, qty);
                        } else {
                            p.getInventory().add(s.getRawFish()[fishIndex], qty);
                        }
                    }


                    if (def.toLowerCase().contains("shrimp")) {
                        p.getAchievementTracker().progress(AchievementData.FISH_10_SHRIMP, qty);
                    } else if (def.toLowerCase().contains("trout")) {
                        p.getAchievementTracker().progress(AchievementData.FISH_25_TROUT, qty);
                    } else if (def.toLowerCase().contains("salmon")) {
                        p.getAchievementTracker().progress(AchievementData.FISH_50_SALMON, qty);
                    } else if (def.toLowerCase().contains("lobster")) {
                        p.getAchievementTracker().progress(AchievementData.FISH_100_LOBSTER, qty);
                    } else if (def.toLowerCase().contains("shark")) {
                        p.getAchievementTracker().progress(AchievementData.FISH_250_SHARK, qty);
                    } else if (def.toLowerCase().contains("rocktail")) {
                        p.getAchievementTracker().progress(AchievementData.FISH_1000_ROCKTAIL, qty);
                    }

                    int addxp = s.getXp()[fishIndex];
                    double bonusxp = 1;

                    if (p.getEquipment().contains(213258))
                        bonusxp += .05;
                    if (p.getEquipment().contains(213259))
                        bonusxp += .05;
                    if (p.getEquipment().contains(213260))
                        bonusxp += .05;
                    if (p.getEquipment().contains(213261))
                        bonusxp += .05;

                    addxp *= bonusxp;

                    int clueChance = 400;

                    if (p.Detective == 1)
                        clueChance = (clueChance * (100 - (p.completedLogs / 4))) / 100;

                    int chance = RandomUtility.inclusiveRandom(clueChance);
                    int clueAmt = 1;

                    if (GlobalEventHandler.effectActive(GlobalEvent.Effect.DOUBLE_CLUES))
                        clueAmt = 2;

                    if (p.canObtainSkillingClues(p)) {
                        if (chance == 1)
                            p.getInventory().add(213651, clueAmt);
                        else if (chance > 1 && chance <= 3)
                            p.getInventory().add(213650, clueAmt);
                        else if (chance > 3 && chance <= 6)
                            p.getInventory().add(213649, clueAmt);
                        else if (chance > 6 && chance <= 10)
                            p.getInventory().add(213648, clueAmt);
                    }


                    if ((p.getInventory().contains(221031) || p.getEquipment().contains(221031)
                            || p.getInventory().contains(13661) || p.getEquipment().contains(13661))
                            && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 75 && p.getSkillManager().getMaxLevel(Skill.COOKING) >= 75) {
                        p.getSkillManager().addExperience(Skill.FISHING, addxp * qty);
                        p.getSkillManager().addExperience(Skill.COOKING, addxp * qty / 2);
                        p.performGraphic(new Graphic(446, GraphicHeight.MIDDLE));
                        if (p.personalFilterAdze) {
                            p.getPacketSender().sendMessage("Your infernal tool cooks the fish, granting you Cooking experience.");
                        }
                    } else {
                        p.getSkillManager().addExperience(Skill.FISHING, addxp * qty);
                    }


                    if (def.toLowerCase().contains("shrimp"))
                        def = "shrimp";
                    if (def.toLowerCase().contains("salmon"))
                        def = "salmon";
                    if (def.toLowerCase().contains("trout"))
                        def = "trout";
                    if (def.toLowerCase().contains("tuna"))
                        def = "tuna";
                    if (def.toLowerCase().contains("lobster"))
                        def = "lobster";
                    if (def.toLowerCase().contains("swordfish"))
                        def = "swordfish";
                    if (def.toLowerCase().contains("monkfish"))
                        def = "monkfish";
                    if (def.toLowerCase().contains("shark"))
                        def = "shark";
                    if (def.toLowerCase().contains("rocktail"))
                        def = "rocktail";
                    if (def.toLowerCase().contains("anglerfish"))
                        def = "anglerfish";


                    Item rawFish = new Item(s.getRawFish()[fishIndex]);
                    String name = rawFish.getDefinition().getName();
                    String check = name.replace("raw ", "");

                    if (p.getSkiller().getSkillerTask().name().toLowerCase().contains(def)) {
                        for (int k = 0; k < qty; k++) {
                            p.getSkiller().handleSkillerTaskGather(true);
                            p.getSkillManager().addExperience(Skill.SKILLER, p.getSkiller().getSkillerTask().getXP());
                        }
                    }

                    p.fishActions++;


                    if (p.fishActions > 27) {
                        p.getCurrentTask().stop();
                        p.fishActions = 0;
                        return;
                    }

                    setupFishing(p, s);
                    setEventRunning(false);
                }
            }

            @Override
            public void stop() {
                setEventRunning(false);
                p.performAnimation(new Animation(65535));
                p.getMovementQueue().setLockMovement(false);
                p.getPacketSender().sendInterfaceRemoval();
            }
        });

        TaskManager.submit(p.getCurrentTask());
    }

    public static int getMax(Player p, int[] reqs) {
        int tempInt = -1;
        for (int i : reqs) {
            if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= i) {
                tempInt++;
            }
        }
        return tempInt;
    }

    private static int getDelay(int req, Player p) {
        int timer = 1;
        timer += req * 0.08;

        if (timer > 5)
            timer = 5;

        if ((p.getInventory().contains(13661) || p.getEquipment().contains(13661)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 75)
            timer /= 4;

        else if ((p.getInventory().contains(223762) || p.getEquipment().contains(223762)) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 70)
            timer /= 3;

        else if ((p.getInventory().contains(221031) || p.getEquipment().contains(221031) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 75) ||
                (p.getInventory().contains(221028) || p.getEquipment().contains(221028) && p.getSkillManager().getMaxLevel(Skill.FISHING) >= 60))
            timer /= 2;


        if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= req + 30)
            timer /= 3;
        else if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= req + 20)
            timer /= 2;

        return timer;
    }

}
