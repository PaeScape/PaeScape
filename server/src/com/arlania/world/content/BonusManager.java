package com.arlania.world.content;

import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.model.Prayerbook;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.minigames.impl.barrows.Rewards;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

public class BonusManager {

    public static void update(Player player, int upgradedItem, int upgrades) {
        double[] bonuses = new double[18];

        for (Item item : player.getEquipment().getItems()) {
            ItemDefinition definition = ItemDefinition.forId(item.getId());

            for (int i = 0; i < definition.getBonus().length; i++) {

                //Negative bonuses

                if (((definition.getName().toLowerCase().contains("vesta")) ||
                        (definition.getName().toLowerCase().contains("statius")) ||
                        (definition.getName().toLowerCase().contains("morrigan")) ||
                        (definition.getName().toLowerCase().contains("zuriel"))) &&
                        (player.getLocation() != Location.WILDERNESS)) {
                    bonuses[i] = 0;
                } else if ((definition.getName().toLowerCase().contains("death")) &&
                        ((player.getLocation() != Location.RAIDS_LOBBY) &&
                                (player.getLocation() != Location.PESTILENT_BLOAT) &&
                                (player.getLocation() != Location.MAIDEN_SUGADINTI) &&
                                (player.getLocation() != Location.VERZIK_VITUR))) {
                    bonuses[i] = 0;
                } else {
                    bonuses[i] += definition.getBonus()[i];
                }


                //Positive bonuses

                if (player.getLocation() == Location.CORPOREAL_BEAST) {
                    if (definition.getName().toLowerCase().contains("spear")) {
                        bonuses[i] += definition.getBonus()[i];
                    }
                }

               //Lightbearer bonuses
                if (definition.getId() == 21077) {
                    bonuses[i] += player.getEquipment().handleLightbearerBonuses(i);
                }


                //Equip Upgrades

                if (definition.getId() > 15706 && definition.getId() < 17261) {
                    double factor = (.05 * player.prestige);
                    bonuses[i] += (int) (definition.getBonus()[i] * factor);
                }

                if (PerkHandler.canUseEquipmentUpgrades(player)) {
                    if (player.qtyHelmUpgrade > 0 && player.idHelmUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyHelmUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyBodyUpgrade > 0 && player.idBodyUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyBodyUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyLegsUpgrade > 0 && player.idLegsUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyLegsUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyBootsUpgrade > 0 && player.idBootsUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyBootsUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyGlovesUpgrade > 0 && player.idGlovesUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyGlovesUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyWeaponUpgrade > 0 && player.idWeaponUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyWeaponUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyShieldUpgrade > 0 && player.idShieldUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyShieldUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyRingUpgrade > 0 && player.idRingUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyRingUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyCapeUpgrade > 0 && player.idCapeUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyCapeUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    } else if (player.qtyAmuletUpgrade > 0 && player.idAmuletUpgrade == definition.getId()) {
                        double factor = (.05 * player.qtyAmuletUpgrade);
                        bonuses[i] += (int) (definition.getBonus()[i] * factor);
                    }
                }

                player.totalUpgradeTiers = 0;

                if (player.qtyHelmUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyHelmUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyBodyUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyBodyUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyLegsUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyLegsUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyBootsUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyBootsUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyWeaponUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyWeaponUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyShieldUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyShieldUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyRingUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyRingUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyCapeUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyCapeUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyGlovesUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyGlovesUpgrade >= 5)
                    player.totalUpgradeTiers += .01;

                if (player.qtyAmuletUpgrade >= 10)
                    player.totalUpgradeTiers += .03;
                else if (player.qtyAmuletUpgrade >= 5)
                    player.totalUpgradeTiers += .01;


                //Events
                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.BARROWS_BASH) && PerkHandler.canUseNormalPerks(player)) {
                    for (int j = 0; j < Rewards.uniques.length; j++)
                        if (definition.getId() == Rewards.uniques[j])
                            bonuses[i] += (int) (definition.getBonus()[i] * .25);
                }
                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.GODS_GIFTS) && PerkHandler.canUseNormalPerks(player)) {
                    for (int j = 0; j < GameSettings.gwdUniques.length; j++)
                        if (definition.getId() == GameSettings.gwdUniques[j])
                            bonuses[i] += (int) (definition.getBonus()[i] * .25);
                }
            }
        }
        for (int i = 0; i < STRING_ID.length; i++) {
            if (i <= 4) {
                player.getBonusManager().attackBonus[i] = bonuses[i];
            } else if (i <= 13) {
                int index = i - 5;
                player.getBonusManager().defenceBonus[index] = bonuses[i];
            } else if (i <= 17) {
                int index = i - 14;
                player.getBonusManager().otherBonus[index] = bonuses[i];
            }
            player.getPacketSender().sendString(Integer.valueOf(STRING_ID[i][0]), STRING_ID[i][1] + ": " + bonuses[i]);
        }
    }

    public double[] getAttackBonus() {
        return attackBonus;
    }

    public double[] getDefenceBonus() {
        return defenceBonus;
    }

    public double[] getOtherBonus() {
        return otherBonus;
    }

    private final double[] attackBonus = new double[5];

    private final double[] defenceBonus = new double[9];

    private final double[] otherBonus = new double[4];

    private static final String[][] STRING_ID = {
            {"1675", "Stab"}, //0
            {"1676", "Slash"},//1
            {"1677", "Crush"},//2
            {"1678", "Magic"},//3
            {"1679", "Range"},//4

            {"1680", "Stab"},//5
            {"1681", "Slash"},//6
            {"1682", "Crush"},//7
            {"1683", "Magic"},//8
            {"1684", "Range"},//9
            {"19148", "Summoning"},//10
            {"19149", "Absorb Melee"},//11
            {"19150", "Absorb Magic"},//12
            {"19151", "Absorb Ranged"},//13

            {"1686", "Strength"},//14
            {"19152", "Ranged Strength"},//15
            {"1687", "Prayer"},//16
            {"19153", "Magic Damage"}//17
    };

    public static final int
            ATTACK_STAB = 0,
            ATTACK_SLASH = 1,
            ATTACK_CRUSH = 2,
            ATTACK_MAGIC = 3,
            ATTACK_RANGE = 4,

    DEFENCE_STAB = 0,
            DEFENCE_SLASH = 1,
            DEFENCE_CRUSH = 2,
            DEFENCE_MAGIC = 3,
            DEFENCE_RANGE = 4,
            DEFENCE_SUMMONING = 5,
            ABSORB_MELEE = 6,
            ABSORB_MAGIC = 7,
            ABSORB_RANGED = 8,

    BONUS_STRENGTH = 0,
            RANGED_STRENGTH = 1,
            BONUS_PRAYER = 2,
            MAGIC_DAMAGE = 3;

    /**
     * CURSES
     **/

    public static void sendCurseBonuses(final Player p) {
        if (p.getPrayerbook() == Prayerbook.CURSES) {
            sendAttackBonus(p);
            sendDefenceBonus(p);
            sendStrengthBonus(p);
            sendRangedBonus(p);
            sendMagicBonus(p);
        }
    }

    public static void sendAttackBonus(Player p) {
        double boost = p.getLeechedBonuses()[0];
        int bonus = 0;
        if (p.getCurseActive()[CurseHandler.LEECH_ATTACK]) {
            bonus = 5;
        } else if (p.getCurseActive()[CurseHandler.TURMOIL])
            bonus = 15;
        bonus += boost;
        if (bonus < -25)
            bonus = -25;
        p.getPacketSender().sendString(690, getColor(bonus) + bonus + "%");
    }

    public static void sendRangedBonus(Player p) {
        double boost = p.getLeechedBonuses()[4];
        int bonus = 0;
        if (p.getCurseActive()[CurseHandler.LEECH_RANGED])
            bonus = 5;
        bonus += boost;
        if (bonus < -25)
            bonus = -25;
        p.getPacketSender().sendString(693, getColor(bonus) + bonus + "%");
    }

    public static void sendMagicBonus(Player p) {
        double boost = p.getLeechedBonuses()[6];
        int bonus = 0;
        if (p.getCurseActive()[CurseHandler.LEECH_MAGIC])
            bonus = 5;
        bonus += boost;
        if (bonus < -25)
            bonus = -25;
        p.getPacketSender().sendString(694, getColor(bonus) + bonus + "%");
    }

    public static void sendDefenceBonus(Player p) {
        double boost = p.getLeechedBonuses()[1];
        int bonus = 0;
        if (p.getCurseActive()[CurseHandler.LEECH_DEFENCE])
            bonus = 5;
        else if (p.getCurseActive()[CurseHandler.TURMOIL])
            bonus = 15;
        bonus += boost;
        if (bonus < -25)
            bonus = -25;
        p.getPacketSender().sendString(692, getColor(bonus) + bonus + "%");
    }

    public static void sendStrengthBonus(Player p) {
        double boost = p.getLeechedBonuses()[2];
        int bonus = 0;
        if (p.getCurseActive()[CurseHandler.LEECH_STRENGTH])
            bonus = 5;
        else if (p.getCurseActive()[CurseHandler.TURMOIL])
            bonus = 23;
        bonus += boost;
        if (bonus < -25)
            bonus = -25;
        p.getPacketSender().sendString(691, getColor(bonus) + bonus + "%");
    }

    public static String getColor(int i) {
        if (i > 0)
            return "@gre@+";
        if (i < 0)
            return "@red@";
        return "";
    }
}
