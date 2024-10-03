package com.arlania.world.content.skill.impl.summoning;

import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class BossPets {

    public enum BossPet {


        PET_FLAX(14001, 14001, 201779, "skilling"),

        PET_FROST_DRAGON(51, 3047, 11991, "slayer"),
        PET_JUNGLE_STRYKEWYRM(9467, 3055, 11986, "slayer"),
        PET_DESERT_STRYKEWYRM(9465, 3056, 11985, "slayer"),
        PET_ICE_STRYKEWYRM(9463, 3057, 11984, "slayer"),
        PET_GREEN_DRAGON(941, 3058, 11983, "slayer"),
        PET_BABY_BLUE_DRAGON(52, 3059, 11982, "slayer"),
        PET_BLUE_DRAGON(55, 3060, 11981, "slayer"),
        PET_BLACK_DRAGON(54, 3061, 11979, "slayer"),
        PET_MITHRIL(5363, 5384, 20091, "slayer"),

        PET_VETION(2006, 3022, 20079, "boss"),
        PET_KRAKEN(7136, 2007, 20103, "boss"),
        PET_CERBERUS(1999, 3023, 20080, "boss"),
        PET_SCORPIA(2001, 3024, 20081, "boss"),
        PET_SKOTIZO(7286, 3025, 20082, "boss"),
        PET_VENENATIS(2000, 3026, 20083, "boss"),
        PET_CRIMSON_ZULRAH(2044, 3028, 14914, "boss"),
        PET_GREEN_ZULRAH(2043, 3029, 20086, "boss"),
        PET_BLUE_ZULRAH(2042, 3041, 14916, "boss"),
        PET_LIZARDMAN_SHAMAN(6766, 3042, 20087, "boss"),
        PET_WILDYWYRM(3334, 3043, 20088, "boss"),
        PET_THERMONUCLEAR_SMOKE_DEVIL(499, 3044, 20089, "boss"),
        PET_ABYSSAL_SIRE(5886, 3045, 20090, "boss"),
        PET_TORMENTED_DEMON(8349, 3048, 11992, "boss"),
        PET_KALPHITE_QUEEN(1158, 3050, 11993, "boss"),
        PET_SLASH_BASH(2060, 3051, 11994, "boss"),
        //PET_PHOENIX(8549, 3052, 11989),
        PET_BANDOS_AVATAR(4540, 3053, 11988, "boss"),
        PET_CALLISTO(2009, 3027, 20085, "boss"),
        PET_CHAOS_ELEMENTAL(3200, 3033, 11995, "boss"),
        PET_KING_BLACK_DRAGON(50, 3030, 11996, "boss"),
        PET_GENERAL_GRAARDOR(6260, 3031, 11997, "boss"),
        PET_TZTOK_JAD(2745, 3032, 11978, "boss"),
        PET_CORPOREAL_BEAST(8133, 3034, 12001, "boss"),
        PET_KREE_ARRA(6222, 3035, 12002, "boss"),
        PET_KRIL_TSUTSAROTH(6203, 3036, 12003, "boss"),
        PET_COMMANDER_ZILYANA(6247, 3037, 12004, "boss"),
        PET_DAGANNOTH_SUPREME(2881, 3038, 12005, "boss"),
        PET_DAGANNOTH_PRIME(2882, 3039, 12006, "boss"),
        PET_DAGANNOTH_REX(2883, 3040, 11990, "boss"),

        PET_VERZIK(22336, 22336, 222473, "raid"),
        PET_OLMLET(21519, 21519, 220851, "raid"),
        PET_HELLCAT(20668, 20668, 207582, "raid"),
        PET_AHRIM(2025, 3046, 20104, "raid"),
        PET_DHAROK(2026, 3062, 20105, "raid"),
        PET_GUTHAN(2027, 3063, 20106, "raid"),
        PET_KARIL(2028, 3064, 20107, "raid"),
        PET_TORAG(2029, 3065, 20108, "raid"),
        PET_VERAC(2030, 3066, 20109, "raid"),
        PET_NEX(13447, 3054, 11987, "raid"),

        //npc id that you are copying, npc id for the pet, item id
        PET_YOUNGLLEF(22729, 22729, 223757, "skilling"),
        PET_HERBI(21759, 21759, 221509, "skilling"),
        PET_SQUIRREL(21351, 21351, 220659, "skilling"),
        PET_TANGLEROOT(21352, 21352, 220661, "skilling"),
        PET_ROCKY(21353, 21353, 220663, "skilling"),
        PET_GUARDIAN(21354, 21354, 220665, "skilling"),
        PET_PHOENIX(21368, 21368, 220693, "skilling"),
        PET_GOLEM(21439, 21439, 221187, "skilling"),
        PET_BEAVER(20717, 20717, 213322, "skilling"),
        PET_HERON(20722, 20722, 211320, "skilling"),
        PET_MOLE(19780, 19780, 212646, "boss"),
        PET_NIGHTMARE(23398, 23398, 224491, "boss"),
        PET_VORKI(22025, 22025, 221992, "boss"),
        PET_BLOODHOUND(21232, 21232, 219730, "misc"),
        PET_GOAT(18147, 18147, 9735, "misc"),
        PET_FROG(1828, 1828, 224373, "misc"),
        PET_GHAST(14946, 14946, 202959, "misc"),
        PET_GNOME_CHILD(20079, 20079, 213655, "misc"),
        PET_WISE_OLD_MAN(2253, 2253, 212399, "misc"),
        PET_GENIE(409, 409, 11157, "misc"),
        PET_LAZY_CAT(2663, 2663, 6550, "misc"),
        PET_DURADEAD(1599, 14405, 8740, "misc"),
        PET_HATIUS(19523, 19523, 212335, "misc"),
        PET_TRYOUT(2082, 2082, 9941, "misc"),
        PET_LEPRECHAUN(3021, 3021, 2948, "misc"),
        PET_EASTER_BUNNY(1835, 1835, 7930, "misc"),
        PET_DEATH(2862, 2862, 964, "misc"),
        PET_SANTA(14385, 14386, 3619, "misc");

        private final int npcId;
        private final int spawnNpcId;
        private final int itemId;
        private final String petType;

        BossPet(int npcId, int spawnNpcId, int itemId, String petType) {
            this.npcId = npcId;
            this.spawnNpcId = spawnNpcId;
            this.itemId = itemId;
            this.petType = petType;
        }

        public static BossPet forNpcId(int npcId) {
            for (BossPet p : BossPet.values()) {
                if (p != null && p.getNpcId() == npcId) {
                    return p;
                }
            }
            return null;
        }

        public static BossPet forId(int itemId) {
            for (BossPet p : BossPet.values()) {
                if (p != null && p.getItemId() == itemId) {
                    return p;
                }
            }
            return null;
        }

        public static BossPet forSpawnId(int spawnNpcId) {
            for (BossPet p : BossPet.values()) {
                if (p != null && p.getSpawnNpcId() == spawnNpcId) {
                    return p;
                }
            }
            return null;
        }

        public static BossPet forPetType(String petType) {
            for (BossPet p : BossPet.values()) {
                if (p != null && p.getPetType() == petType) {
                    return p;
                }
            }
            return null;
        }

        public int getNpcId() {
            return npcId;
        }

        public int getSpawnNpcId() {
            return spawnNpcId;
        }

        public int getItemId() {
            return itemId;
        }

        public String getPetType() {
            return petType;
        }
    }

    public static boolean pickup(Player player, NPC npc) {
        BossPet pet = BossPet.forSpawnId(npc.getId());
        if (pet != null) {
            if (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null && player.getSummoning().getFamiliar().getSummonNpc().isRegistered()) {
                if (player.getSummoning().getFamiliar().getSummonNpc().getId() == pet.getSpawnNpcId() && player.getSummoning().getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
                    player.getSummoning().unsummon(true, true);
                    player.getPacketSender().sendMessage("You pick up your pet.");
                    return true;
                } else {
                    player.getPacketSender().sendMessage("This is not your pet to pick up.");
                }
            } else {
                player.getPacketSender().sendMessage("This is not your pet to pick up.");
            }
        }
        return false;
    }

}
