package com.arlania.world.content.skill.impl.mining;

import com.arlania.model.Skill;
import com.arlania.model.container.impl.Equipment;
import com.arlania.world.entity.impl.player.Player;

public class MiningData {

    public static final int[] RANDOM_GEMS = {1623, 1621, 1619, 1617, 1631};

    public enum Pickaxe {

        Bronze(1265, 1, 625, 1.0),
        Iron(1267, 1, 626, 1.05),
        Steel(1269, 6, 627, 1.1),
        Mithril(1273, 21, 628, 1.2),
        Adamant(1271, 31, 629, 1.25),
        Rune(1275, 41, 624, 1.3),
        Dragon(15259, 61, 12188, 1.50),
        ThirdAge(220014, 65, 624, 1.6),
        Crystal(223680, 70, 624, 1.75),
        Infernal(213243, 75, 12188, 1.5),
        Adze(13661, 80, 10226, 2.0);

        private final int id;
        private final int req;
        private final int anim;
        private final double speed;

        Pickaxe(int id, int req, int anim, double speed) {
            this.id = id;
            this.req = req;
            this.anim = anim;
            this.speed = speed;
        }

        public int getId() {
            return id;
        }

        public int getReq() {
            return req;
        }

        public int getAnim() {
            return anim;
        }

        public double getSpeed() {
            return this.speed;
        }
    }

    public static Pickaxe forPick(int id) {
        for (Pickaxe p : Pickaxe.values()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public enum Ores {
        Rune_essence(new int[]{2491, 334773}, 1, 10, 1436, 2, -1, -1, -1, -1),
        Pure_essence(new int[]{2492}, 30, 25, 7936, 3, -1, -1, -1, -1),
        Dark_essence(new int[]{2493}, 85, 50, 7938, 3, -1, -1, -1, -1),

        Clay(new int[]{9711, 9712, 9713, 15503, 15504, 15505, 311162, 311163}, 1, 15, 434, 3, 2, 1761, 0, 500),
        Copper(new int[]{9708, 9709, 9710, 11936, 11960, 11961, 11962, 11189, 11190, 11191, 29231, 29230, 2090, 311161, 310943, 310079}, 1, 25, 436, 4, 4, 2349, 10, 300),
        Tin(new int[]{9714, 9715, 9716, 11933, 11957, 11958, 11959, 11186, 11187, 11188, 2094, 29227, 29229, 311360, 311361, 310080}, 1, 25, 438, 4, 4, 2349, 10, 300),
        Iron(new int[]{9717, 9718, 9719, 2093, 2092, 11954, 11955, 11956, 29221, 29222, 29223, 311364, 311365}, 15, 50, 440, 5, 5, 2351, 15, 275),
        Silver(new int[]{2100, 2101, 29226, 29225, 11948, 11949, 311368, 311369}, 20, 60, 442, 5, 7, 2355, 25, 250),
        Coal(new int[]{2096, 2097, 5770, 29216, 29215, 29217, 11965, 11964, 11963, 11930, 11931, 11932, 311366, 311367}, 30, 80, 453, 5, 7, 2353, 40, 225),
        Gold(new int[]{9720, 9721, 9722, 11951, 11183, 11184, 11185, 2099, 311370, 311371}, 40, 100, 444, 5, 10, 2357, 50, 200),
        Mithril(new int[]{2102, 2103, 25370, 25368, 5786, 5784, 11942, 11943, 11944, 11945, 11946, 29236, 11947, 311372, 311373}, 50, 150, 447, 6, 11, 2359, 75, 175),
        Adamantite(new int[]{2105, 11941, 11939, 29233, 29235, 311374, 311375}, 70, 200, 449, 7, 14, 2361, 100, 150),
        Runite(new int[]{14860, 14859, 4860, 2106, 2107, 311376, 311377}, 85, 250, 451, 7, 20, 2363, 150, 100),
        Gem(new int[]{2111, 17004, 311381, 311380}, 40, 100, -1, 7, 20, -1, -1, -1),
        Rockfall(new int[]{326679, 326680}, 30, 80, 212011, 4, -1, -1, -1, 150),
        Vein(new int[]{326666, 326667, 326668}, 30, 60, 212011, 5, -1, -1, -1, -1),
        Crystal(new int[]{336064}, 1, 25, 223962, 1, -1, -1, -1, -1),
        Amethyst(new int[]{315250}, 90, 300, 221347, 1, -1, -1, -1, 75),

        CRASHED_STAR(new int[]{38660}, 80, 300, 13727, 4, -1, -1, -1, 50);


        private final int[] objid;
        private final int itemid;
        private final int req;
        private final int xp;
        private final int ticks;
        private final int respawnTimer;
        private final int productId;
        private final int smithingXp;
        private final int gemOdds;

        Ores(int[] objid, int req, int xp, int itemid, int ticks, int respawnTimer, int productId, int smithingXp, int gemOdds) {
            this.objid = objid;
            this.req = req;
            this.xp = xp;
            this.itemid = itemid;
            this.ticks = ticks;
            this.respawnTimer = respawnTimer;
            this.productId = productId;
            this.smithingXp = smithingXp;
            this.gemOdds = gemOdds;
        }

        public int getRespawn() {
            return respawnTimer;
        }

        public int getLevelReq() {
            return req;
        }

        public int getXpAmount() {
            return xp;
        }

        public int getItemId() {
            return itemid;
        }

        public int getTicks() {
            return ticks;
        }

        public int getProductId() {
            return productId;
        }

        public int getSmithingXp() {
            return smithingXp;
        }

        public int getGemOdds() {
            return gemOdds;
        }
    }

    public static Ores forRock(int id) {
        for (Ores ore : Ores.values()) {
            for (int obj : ore.objid) {
                if (obj == id) {
                    return ore;
                }
            }
        }
        return null;
    }

    public static int getPickaxe(final Player plr) {
        for (Pickaxe p : Pickaxe.values()) {

            switch (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {

                case 13661:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 75)
                        return 13661;
                    break;
                case 213243:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 75)
                        return 213243;
                    break;
                case 223680:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 70)
                        return 223680;
                    break;
                case 220014:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 65)
                        return 220014;
                    break;
                case 15259:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 61)
                        return 15259;
                    break;
                case 1275:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 40)
                        return 1275;
                    break;
                case 1271:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 30)
                        return 1271;
                    break;
                case 1273:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 20)
                        return 1273;
                    break;
                case 1269:
                    if (plr.getSkillManager().getMaxLevel(Skill.MINING) >= 5)
                        return 1269;
                    break;
                case 1267:
                    return 1267;
                case 1265:
                    return 1265;
            }

            if (plr.getInventory().contains(13661) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 75)
                return 13661;
            if (plr.getInventory().contains(213243) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 75)
                return 213243;
            if (plr.getInventory().contains(223680) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 70)
                return 223680;
            if (plr.getInventory().contains(220014) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 65)
                return 220014;
            if (plr.getInventory().contains(15259) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 61)
                return 15259;
            if (plr.getInventory().contains(1275) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 40)
                return 1275;
            if (plr.getInventory().contains(1271) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 30)
                return 1271;
            if (plr.getInventory().contains(1273) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 20)
                return 1273;
            if (plr.getInventory().contains(1269) && plr.getSkillManager().getMaxLevel(Skill.MINING) >= 5)
                return 1269;
            if (plr.getInventory().contains(1267))
                return 1267;
            if (plr.getInventory().contains(1265))
                return 1265;
			
			
			
			/*if (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == p.getId())
				return p.getId();
			else if (plr.getInventory().contains(p.getId()))
				return p.getId();*/
        }
        return -1;
    }

    public static int getReducedTimer(final Player plr, Pickaxe pickaxe) {
        int skillReducement = (int) (plr.getSkillManager().getMaxLevel(Skill.MINING) * 0.03);
        int pickaxeReducement = (int) pickaxe.getSpeed();
        return skillReducement + pickaxeReducement;
    }
}