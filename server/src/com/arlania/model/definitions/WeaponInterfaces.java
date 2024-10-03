package com.arlania.model.definitions;

import com.arlania.model.Item;
import com.arlania.util.JsonLoader;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.combat.weapon.FightType;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A static utility class that displays holds and displays data for weapon
 * interfaces.
 *
 * @author lare96
 */
public final class WeaponInterfaces {

    /**
     * A map of items and their respective interfaces.
     */
    private static final Map<Integer, WeaponInterface> interfaces = new HashMap<>(500);

    /**
     * All of the interfaces for weapons and the data needed to display these
     * interfaces properly.
     *
     * @author lare96
     */
    public enum WeaponInterface {
        STAFF(328, 331, 6, new FightType[]{FightType.STAFF_BASH, FightType.STAFF_POUND, FightType.STAFF_FOCUS}, 7474, 7486),

        WARHAMMER(425, 428, 6, new FightType[]{FightType.WARHAMMER_POUND,
                FightType.WARHAMMER_PUMMEL, FightType.WARHAMMER_BLOCK}, 7474, 7486),

        FASTSPEED0(2276, 2279, 0, new FightType[]{FightType.SWORD_STAB,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_BLOCK}, 7574, 7586),
        //Fastest Speed
        FASTSPEED(2276, 2279, 1, new FightType[]{FightType.SWORD_STAB,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_BLOCK}, 7574, 7586),
        // Second fastest
        FASTSPEED1(2276, 2279, 2, new FightType[]{FightType.SWORD_STAB,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_BLOCK}, 7574, 7586),
        //Third fastest
        FASTSPEED2(2276, 2279, 3, new FightType[]{FightType.SWORD_STAB,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_BLOCK}, 7574, 7586),
        //Fourth fastest

        FASTSPEED3(2276, 2279, 4, new FightType[]{FightType.SWORD_STAB,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_BLOCK}, 7574, 7586),


        BATTLEAXE(1698, 1701, 6, new FightType[]{FightType.BATTLEAXE_CHOP,
                FightType.BATTLEAXE_HACK, FightType.BATTLEAXE_SMASH,
                FightType.BATTLEAXE_BLOCK}, 7499, 7511),

        CROSSBOW(1764, 1767, 5, new FightType[]{FightType.CROSSBOW_ACCURATE,
                FightType.CROSSBOW_RAPID, FightType.CROSSBOW_LONGRANGE}, 7549, 7561),

        SHORTBOW(1764, 1767, 5, new FightType[]{FightType.SHORTBOW_ACCURATE,
                FightType.SHORTBOW_RAPID, FightType.SHORTBOW_LONGRANGE}, 7549, 7561),

        LONGBOW(1764, 1767, 6, new FightType[]{FightType.LONGBOW_ACCURATE,
                FightType.LONGBOW_RAPID, FightType.LONGBOW_LONGRANGE}, 7549, 7561),

        DAGGER(2276, 2279, 3, new FightType[]{FightType.DAGGER_STAB,
                FightType.DAGGER_LUNGE, FightType.DAGGER_SLASH,
                FightType.DAGGER_BLOCK}, 7574, 7586),
        SWORD(2276, 2279, 5, new FightType[]{FightType.SWORD_STAB,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_BLOCK}, 7574, 7586),

        SCIMITAR(2423, 2426, 5, new FightType[]{FightType.SCIMITAR_CHOP,
                FightType.SCIMITAR_SLASH, FightType.SCIMITAR_LUNGE,
                FightType.SCIMITAR_BLOCK}, 7599, 7611),

        LONGSWORD(2423, 2426, 6, new FightType[]{FightType.LONGSWORD_CHOP,
                FightType.LONGSWORD_SLASH, FightType.LONGSWORD_LUNGE,
                FightType.LONGSWORD_BLOCK}, 7599, 7611),

        MACE(3796, 3799, 4, new FightType[]{FightType.MACE_POUND,
                FightType.MACE_PUMMEL, FightType.MACE_SPIKE,
                FightType.MACE_BLOCK}, 7624, 7636),

        KNIFE(4446, 4449, 4, new FightType[]{FightType.KNIFE_ACCURATE,
                FightType.KNIFE_RAPID, FightType.KNIFE_LONGRANGE}, 7649, 7661),

        SPEAR(4679, 4682, 4, new FightType[]{FightType.SPEAR_LUNGE,
                FightType.SPEAR_SWIPE, FightType.SPEAR_POUND,
                FightType.SPEAR_BLOCK}, 7674, 7686),

        TWO_HANDED_SWORD(4705, 4708, 6, new FightType[]{
                FightType.TWOHANDEDSWORD_CHOP, FightType.TWOHANDEDSWORD_SLASH,
                FightType.TWOHANDEDSWORD_SMASH, FightType.TWOHANDEDSWORD_BLOCK}, 7699, 7711),

        PICKAXE(5570, 5573, 6, new FightType[]{FightType.PICKAXE_SPIKE,
                FightType.PICKAXE_IMPALE, FightType.PICKAXE_SMASH,
                FightType.PICKAXE_BLOCK}),

        CLAWS(7762, 7765, 4, new FightType[]{FightType.CLAWS_CHOP,
                FightType.CLAWS_SLASH, FightType.CLAWS_LUNGE,
                FightType.CLAWS_BLOCK}, 7800, 7812),

        HALBERD(8460, 8463, 6, new FightType[]{FightType.HALBERD_JAB,
                FightType.HALBERD_SWIPE, FightType.HALBERD_FEND}, 8493, 8505),

        UNARMED(5855, 5857, 6, new FightType[]{FightType.UNARMED_PUNCH,
                FightType.UNARMED_KICK, FightType.UNARMED_BLOCK}),

        WHIP(12290, 12293, 4, new FightType[]{FightType.WHIP_FLICK,
                FightType.WHIP_LASH, FightType.WHIP_DEFLECT}, 12323, 12335),

        THROWNAXE(4446, 4449, 6, new FightType[]{
                FightType.THROWNAXE_ACCURATE, FightType.THROWNAXE_RAPID,
                FightType.THROWNAXE_LONGRANGE}, 7649, 7661),

        DART(4446, 4449, 4, new FightType[]{FightType.DART_ACCURATE,
                FightType.DART_RAPID, FightType.DART_LONGRANGE}, 7649, 7661),

        JAVELIN(4446, 4449, 6, new FightType[]{FightType.JAVELIN_ACCURATE,
                FightType.JAVELIN_RAPID, FightType.JAVELIN_LONGRANGE}, 7649, 7661);

        /**
         * The interface that will be displayed on the sidebar.
         */
        private final int interfaceId;

        /**
         * The line that the name of the item will be printed to.
         */
        private final int nameLineId;

        /**
         * The attack speed of weapons using this interface.
         */
        private final int speed;

        /**
         * The fight types that correspond with this interface.
         */
        private final FightType[] fightType;

        /**
         * The id of the special bar for this interface.
         */
        private final int specialBar;

        /**
         * The id of the special meter for this interface.
         */
        private final int specialMeter;

        /**
         * Creates a new weapon interface.
         *
         * @param interfaceId  the interface that will be displayed on the sidebar.
         * @param nameLineId   the line that the name of the item will be printed to.
         * @param speed        the attack speed of weapons using this interface.
         * @param fightType    the fight types that correspond with this interface.
         * @param specialBar   the id of the special bar for this interface.
         * @param specialMeter the id of the special meter for this interface.
         */
        WeaponInterface(int interfaceId, int nameLineId, int speed,
                        FightType[] fightType, int specialBar, int specialMeter) {
            this.interfaceId = interfaceId;
            this.nameLineId = nameLineId;
            this.speed = speed;
            this.fightType = fightType;
            this.specialBar = specialBar;
            this.specialMeter = specialMeter;
        }

        /**
         * Creates a new weapon interface.
         *
         * @param interfaceId the interface that will be displayed on the sidebar.
         * @param nameLineId  the line that the name of the item will be printed to.
         * @param speed       the attack speed of weapons using this interface.
         * @param fightType   the fight types that correspond with this interface.
         */
        WeaponInterface(int interfaceId, int nameLineId, int speed,
                        FightType[] fightType) {
            this(interfaceId, nameLineId, speed, fightType, -1, -1);
        }

        /**
         * Gets the interface that will be displayed on the sidebar.
         *
         * @return the interface id.
         */
        public int getInterfaceId() {
            return interfaceId;
        }

        /**
         * Gets the line that the name of the item will be printed to.
         *
         * @return the name line id.
         */
        public int getNameLineId() {
            return nameLineId;
        }

        /**
         * Gets the attack speed of weapons using this interface.
         *
         * @return the attack speed of weapons using this interface.
         */
        public int getSpeed() {
            return speed;
        }

        /**
         * Gets the fight types that correspond with this interface.
         *
         * @return the fight types that correspond with this interface.
         */
        public FightType[] getFightType() {
            return fightType;
        }

        /**
         * Gets the id of the special bar for this interface.
         *
         * @return the id of the special bar for this interface.
         */
        public int getSpecialBar() {
            return specialBar;
        }

        /**
         * Gets the id of the special meter for this interface.
         *
         * @return the id of the special meter for this interface.
         */
        public int getSpecialMeter() {
            return specialMeter;
        }
    }

    /**
     * Assigns an interface to the combat sidebar based on the argued weapon.
     *
     * @param player the player that the interface will be assigned for.
     * @param item   the item that the interface will be chosen for.
     */
    public static void assign(Player player, Item item) {
        WeaponInterface weapon;
        FightType fightStyle;

        fightStyle = player.getFightType();

        if (item == null || item.getId() == -1) {
            weapon = WeaponInterface.UNARMED;
        } else {
            weapon = interfaces.get(item.getId());
        }

        if (weapon == null)
            weapon = WeaponInterface.UNARMED;

        if (weapon == WeaponInterface.UNARMED) {
            player.getPacketSender().sendTabInterface(0, weapon.getInterfaceId());
            player.getPacketSender().sendString(weapon.getNameLineId(), "Unarmed");
            player.setWeapon(WeaponInterface.UNARMED);
        } else if (weapon == WeaponInterface.CROSSBOW) {
            player.getPacketSender().sendString(weapon.getNameLineId() - 1, "Weapon: ");
        } else if (weapon == WeaponInterface.WHIP) {
            player.getPacketSender().sendString(weapon.getNameLineId() - 1, "Weapon: ");
        }

        player.getPacketSender().sendItemOnInterface(
                weapon.getInterfaceId() + 1, 200, item.getId());
        player.getPacketSender().sendTabInterface(0,
                weapon.getInterfaceId());
        player.getPacketSender().sendString(
                weapon.getNameLineId(), item.getDefinition().getName());
        player.setWeapon(weapon);
        CombatSpecial.assign(player);
        CombatSpecial.updateBar(player);

        for (FightType type : weapon.getFightType()) {
            if (type.getStyle() == player.getFightType().getStyle()) {
                player.setFightType(type);
                player.getPacketSender().sendConfig(player.getFightType().getParentId(), player.getFightType().getChildId());
                return;
            }
        }

        int fightTypeIndex = 0;

        if (fightStyle != null) { //Fix for weapon style changes
            if (fightStyle.getChildId() < weapon.getFightType().length) {
                fightTypeIndex = fightStyle.getChildId();
            }
        }

        player.setFightType(player.getWeapon().getFightType()[fightTypeIndex]);
        player.getPacketSender().sendConfig(player.getFightType().getParentId(), player.getFightType().getChildId());
    }

    /**
     * Prepares the dynamic json loader for loading weapon interfaces.
     *
     * @return the dynamic json loader.
     * @throws Exception if any errors occur while preparing for load.
     */
    public static JsonLoader parseInterfaces() {
        return new JsonLoader() {
            @Override
            public void load(JsonObject reader, Gson builder) {
                int id = reader.get("item-id").getAsInt();
                WeaponInterface animation = builder.fromJson(
                        reader.get("interface"), WeaponInterface.class);
                interfaces.put(id, animation);
            }

            @Override
            public String filePath() {
                return "./data/def/json/weapon_interfaces.json";
            }
        };
    }
}
