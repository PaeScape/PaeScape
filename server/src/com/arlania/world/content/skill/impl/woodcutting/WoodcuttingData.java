package com.arlania.world.content.skill.impl.woodcutting;

import com.arlania.model.Skill;
import com.arlania.model.container.impl.Equipment;
import com.arlania.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;

public class WoodcuttingData {

    public enum Hatchet {
        BRONZE(1351, 1, 879, 1.0),
        IRON(1349, 1, 877, 1.5),
        STEEL(1353, 6, 875, 2),
        BLACK(1361, 6, 873, 2.5),
        MITHRIL(1355, 21, 871, 3),
        ADAMANT(1357, 31, 869, 3.5),
        RUNE(1359, 41, 867, 4),
        DRAGON(6739, 61, 2846, 4.5),
        THIRD(220011, 65, 867, 5),
        CRYSTAL(223673, 70, 867, 6),
        INFERNAL(213241, 75, 867, 5),
        ADZE(13661, 80, 10227, 6.5);

        private final int id;
        private final int req;
        private final int anim;
        private final double speed;

        Hatchet(int id, int level, int animation, double speed) {
            this.id = id;
            this.req = level;
            this.anim = animation;
            this.speed = speed;
        }

        public static Map<Integer, Hatchet> hatchets = new HashMap<Integer, Hatchet>();


        public static Hatchet forId(int id) {
            return hatchets.get(id);
        }

        static {
            for (Hatchet hatchet : Hatchet.values()) {
                hatchets.put(hatchet.getId(), hatchet);
            }
        }

        public int getId() {
            return id;
        }

        public int getRequiredLevel() {
            return req;
        }

        public int getAnim() {
            return anim;
        }

        public double getSpeed() {
            return speed;
        }
    }

    public enum Trees {
        NORMAL(1, 25, 40, 1511, new int[]{301276, 301278, 301279, 310041, 314309, 314308}, 4, true),
        ACHEY(1, 25, 40, 2862, new int[]{302023}, 4, false),
        OAK(15, 37, 60, 1521, new int[]{301751, 301281, 303037, 310820}, 5, true),
        WILLOW(30, 66, 90, 1519, new int[]{1308, 5551, 5552, 5553, 310819, 301756, 310829, 310833, 310831}, 6, true),
        TEAK(35, 75, 105, 6333, new int[]{9036, 309036}, 7, true),
        DRAMEN(36, 85, 0, 771, new int[]{1292}, 7, true),
        MAPLE(45, 100, 135, 1517, new int[]{1307, 4677, 310832}, 7, true),
        MAHOGANY(50, 130, 158, 6332, new int[]{309034, 9034}, 7, true),
        YEW(60, 175, 203, 1515, new int[]{1309, 10822, 310822}, 8, true),
        MAGIC(75, 250, 304, 1513, new int[]{1306, 10834, 310834}, 9, true),
        REDWOOD(90, 350, 400, 219669, new int[]{329668, 329670}, 9, true),
        PHREN(1, 100, 0, 223962, new int[]{336066}, 1, true),
        BRUMA(50, 120, 0, 220695, new int[]{329311}, 1, true),
        EVIL_TREE(80, 100, 0, 14666, new int[]{11434}, 9, true);

        private final int[] objects;
        private final int req;
        private final int xp;
        private final int log;
        private final int ticks;
        private final int fmXp;
        private final boolean multi;

        Trees(int req, int xp, int fmXp, int log, int[] obj, int ticks, boolean multi) {
            this.req = req;
            this.xp = xp;
            this.fmXp = fmXp;
            this.log = log;
            this.objects = obj;
            this.ticks = ticks;
            this.multi = multi;
        }

        public boolean isMulti() {
            return multi;
        }

        public int getTicks() {
            return ticks;
        }

        public int getReward() {
            return log;
        }

        public int getXp() {
            return xp;
        }

        public int getFmXp() {
            return fmXp;
        }

        public int getReq() {
            return req;
        }

        private static final Map<Integer, Trees> tree = new HashMap<Integer, Trees>();

        public static Trees forId(int id) {
            return tree.get(id);
        }

        static {
            for (Trees t : Trees.values()) {
                for (int obj : t.objects) {
                    tree.put(obj, t);
                }
            }
        }
    }

    public static int getHatchet(Player p) {
        for (Hatchet h : Hatchet.values()) {

            switch (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {

                case 13661:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 75)
                        return 13661;
                case 213241:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 75)
                        return 213241;
                case 223673:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 70)
                        return 223673;
                case 6739:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 61)
                        return 6739;
                case 1359:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 41)
                        return 1359;
                case 1357:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 31)
                        return 1357;
                case 1355:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 21)
                        return 1355;
                case 1353:
                    if (p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 6)
                        return 1353;
                case 1349:
                    return 1349;
                case 1351:
                    return 1351;
            }

            if (p.getInventory().contains(13661) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 75)
                return 13661;
            if (p.getInventory().contains(213241) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 75)
                return 213241;
            if (p.getInventory().contains(223673) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 70)
                return 223673;
            if (p.getInventory().contains(6739) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 60)
                return 6739;
            if (p.getInventory().contains(1359) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 40)
                return 1359;
            if (p.getInventory().contains(1357) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 30)
                return 1357;
            if (p.getInventory().contains(1355) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 20)
                return 1355;
            if (p.getInventory().contains(1353) && p.getSkillManager().getMaxLevel(Skill.WOODCUTTING) >= 5)
                return 1353;
            if (p.getInventory().contains(1349))
                return 1349;
            if (p.getInventory().contains(1351))
                return 1351;
			
			
			
			/*if (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == h.getId()) {
				return h.getId();
			} else if (p.getInventory().contains(h.getId())) {
				return h.getId();
			}*/
        }
        return -1;
    }

    public static int getChopTimer(Player player, Hatchet h) {
        int skillReducement = (int) (player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) * 0.05);
        int axeReducement = (int) h.getSpeed();
        return skillReducement + axeReducement;
    }
}
