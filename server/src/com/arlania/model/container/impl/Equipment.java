package com.arlania.model.container.impl;

import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.content.combat.equipment.FullSets;
import com.arlania.world.entity.impl.player.Player;

/**
 * Represents a player's equipment item container.
 *
 * @author relex lawl
 */

public class Equipment extends ItemContainer {

    /**
     * The Equipment constructor.
     *
     * @param player The player who's equipment is being represented.
     */
    public Equipment(Player player) {
        super(player);
    }

    @Override
    public int capacity() {
        return 14;
    }

    @Override
    public StackType stackType() {
        return StackType.DEFAULT;
    }

    @Override
    public ItemContainer refreshItems() {
        getPlayer().getPacketSender().sendItemContainer(this, INVENTORY_INTERFACE_ID);
        return this;
    }

    @Override
    public Equipment full() {
        return this;
    }

    /**
     * The equipment inventory interface id.
     */
    public static final int INVENTORY_INTERFACE_ID = 1688;
    public static final int HEAD_SLOT = 0;
    public static final int CAPE_SLOT = 1;
    public static final int AMULET_SLOT = 2;
    public static final int WEAPON_SLOT = 3;
    public static final int BODY_SLOT = 4;
    public static final int SHIELD_SLOT = 5;
    public static final int LEG_SLOT = 7;
    public static final int HANDS_SLOT = 9;
    public static final int FEET_SLOT = 10;
    public static final int RING_SLOT = 12;
    public static final int AMMUNITION_SLOT = 13;

    public boolean wearingNexAmours() {
        int head = getPlayer().getEquipment().getItems()[HEAD_SLOT].getId();
        int body = getPlayer().getEquipment().getItems()[BODY_SLOT].getId();
        int legs = getPlayer().getEquipment().getItems()[LEG_SLOT].getId();
        boolean torva = head == 14008 && body == 14009 && legs == 14010;
        boolean pernix = head == 14011 && body == 14012 && legs == 14013;
        boolean virtus = head == 14014 && body == 14015 && legs == 14016;
        return torva || pernix || virtus;
    }

    public boolean wearingJusticiar() {
        int head = getPlayer().getEquipment().getItems()[HEAD_SLOT].getId();
        int body = getPlayer().getEquipment().getItems()[BODY_SLOT].getId();
        int legs = getPlayer().getEquipment().getItems()[LEG_SLOT].getId();

        return (head == 21000) && (body == 21001) && (legs == 21002);
    }

    public boolean wearingMasori() {
        int head = getPlayer().getEquipment().getItems()[HEAD_SLOT].getId();
        int body = getPlayer().getEquipment().getItems()[BODY_SLOT].getId();
        int legs = getPlayer().getEquipment().getItems()[LEG_SLOT].getId();

        return (head == 21070 || head == 21073) && (body == 21071 || body == 21074) && (legs == 21072 || legs == 21075);
    }

    public boolean wearingAncestral() {
        int head = getPlayer().getEquipment().getItems()[HEAD_SLOT].getId();
        int body = getPlayer().getEquipment().getItems()[BODY_SLOT].getId();
        int legs = getPlayer().getEquipment().getItems()[LEG_SLOT].getId();

        boolean ancestralHead = head == 2051 || head == 2765 || head == 18888 || head == 18892 || head == 18899 || head == 18905;
        boolean ancestralBody = body == 2052 || body == 2766 || body == 18889 || body == 18893 || body == 18900 || body == 18906;
        boolean ancestralLegs = legs == 2053 || legs == 2767 || legs == 18890 || legs == 18894 || legs == 18901 || legs == 18907;

        return ancestralHead && ancestralBody && ancestralLegs;
    }

    public int countStatius(Player player) {
        int count = 0;

        if (getPlayer().getEquipment().getItems()[HEAD_SLOT].getName().contains("Statiu"))
            count++;
        if (getPlayer().getEquipment().getItems()[BODY_SLOT].getName().contains("Statiu"))
            count++;
        if (getPlayer().getEquipment().getItems()[LEG_SLOT].getName().contains("Statiu"))
            count++;
        if (getPlayer().getEquipment().getItems()[WEAPON_SLOT].getName().contains("Statiu"))
            count++;

        return count;
    }

    public int countVesta(Player player) {
        int count = 0;

        if (getPlayer().getEquipment().getItems()[HEAD_SLOT].getName().contains("Vesta"))
            count++;
        if (getPlayer().getEquipment().getItems()[BODY_SLOT].getName().contains("Vesta"))
            count++;
        if (getPlayer().getEquipment().getItems()[LEG_SLOT].getName().contains("Vesta"))
            count++;
        if (getPlayer().getEquipment().getItems()[WEAPON_SLOT].getName().contains("Vesta"))
            count++;

        return count;
    }

    public int countMorrigan(Player player) {
        int count = 0;

        if (getPlayer().getEquipment().getItems()[HEAD_SLOT].getName().contains("Morrigan"))
            count++;
        if (getPlayer().getEquipment().getItems()[BODY_SLOT].getName().contains("Morrigan"))
            count++;
        if (getPlayer().getEquipment().getItems()[LEG_SLOT].getName().contains("Morrigan"))
            count++;
        if (getPlayer().getEquipment().getItems()[WEAPON_SLOT].getName().contains("Morrigan"))
            count++;

        return count;
    }

    public int countZuriel(Player player) {
        int count = 0;

        if (getPlayer().getEquipment().getItems()[HEAD_SLOT].getName().contains("Zuriel"))
            count++;
        if (getPlayer().getEquipment().getItems()[BODY_SLOT].getName().contains("Zuriel"))
            count++;
        if (getPlayer().getEquipment().getItems()[LEG_SLOT].getName().contains("Zuriel"))
            count++;
        if (getPlayer().getEquipment().getItems()[WEAPON_SLOT].getName().contains("Zuriel"))
            count++;

        return count;
    }

    public boolean wearingFullInquisitor() {

        return getPlayer().getEquipment().contains(224419) &&
                getPlayer().getEquipment().contains(224420) &&
                getPlayer().getEquipment().contains(224421) &&
                getPlayer().getEquipment().contains(224417);
    }


    public boolean wearingMeleeVoid() {
        return getPlayer().getEquipment().contains(voidMeleeHelm) &&
                getPlayer().getEquipment().contains(voidTop) &&
                getPlayer().getEquipment().contains(voidBottoms) &&
                getPlayer().getEquipment().contains(voidGloves);
    }

    public boolean wearingRangedVoid() {
        return getPlayer().getEquipment().contains(voidRangeHelm) &&
                getPlayer().getEquipment().contains(voidTop) &&
                getPlayer().getEquipment().contains(voidBottoms) &&
                getPlayer().getEquipment().contains(voidGloves);
    }

    public boolean wearingMagicVoid() {
        return getPlayer().getEquipment().contains(voidMagicHelm) &&
                getPlayer().getEquipment().contains(voidTop) &&
                getPlayer().getEquipment().contains(voidBottoms) &&
                getPlayer().getEquipment().contains(voidGloves);
    }

    public boolean wearingMeleeEliteVoid() {
        return getPlayer().getEquipment().contains(voidMeleeHelm) &&
                getPlayer().getEquipment().contains(eliteVoidTop) &&
                getPlayer().getEquipment().contains(eliteVoidBottoms) &&
                getPlayer().getEquipment().contains(voidGloves);
    }

    public boolean wearingRangedEliteVoid() {
        return getPlayer().getEquipment().contains(voidRangeHelm) &&
                getPlayer().getEquipment().contains(eliteVoidTop) &&
                getPlayer().getEquipment().contains(eliteVoidBottoms) &&
                getPlayer().getEquipment().contains(voidGloves);
    }

    public boolean wearingMagicEliteVoid() {
        return getPlayer().getEquipment().contains(voidMagicHelm) &&
                getPlayer().getEquipment().contains(eliteVoidTop) &&
                getPlayer().getEquipment().contains(eliteVoidBottoms) &&
                getPlayer().getEquipment().contains(voidGloves);
    }

    public double wearingSlayerHelm() {

        int head = getPlayer().getEquipment().getItems()[HEAD_SLOT].getId();

        double bonus = 0;

        switch (head) {
            case 13263:
                bonus += .15;
                break;
            case 219639:
            case 219643:
            case 219647:
            case 221264:
            case 221888:
            case 223073:
            case 224370:
            case 2771:
            case 15492: //full slayer helm:
            case 21076: //Mask of Rebirth
                bonus += .30;
                break;
            case 8921: //black mask
            case 15488: //hexcrest
            case 15490: //focus sight
                bonus += .10;
                break;
        }

        if (getPlayer().getEquipment().contains(213392) || getPlayer().getInventory().contains(213392))
            bonus += .15;

        return bonus;
    }

    public boolean amuletOfTheDamnedEffect() {

        if (!getPlayer().getEquipment().contains(18845))
            return false;

        if (FullSets.fullAhrims(getPlayer()))
            return true;
        if (FullSets.fullDharoks(getPlayer()))
            return true;
        if (FullSets.fullKarils(getPlayer()))
            return true;
        if (FullSets.fullGuthans(getPlayer()))
            return true;
        if (FullSets.fullTorags(getPlayer()))
            return true;
        return FullSets.fullVeracs(getPlayer());
    }

    public static final int[] obsidianWeapons = {
            746, 747, 6523, 6525, 6526, 6527, 6528
    };

    public static boolean hasObsidianEffect(Player plr) {
        if (plr.getEquipment().getItems()[2].getId() != 11128)
            return false;

        //TODO add Obsidian armor

        for (int weapon : obsidianWeapons) {
            if (plr.getEquipment().getItems()[3].getId() == weapon)
                return true;
        }
        return false;
    }

    public boolean wearingSpiritShield() {
        int shield = getPlayer().getEquipment().getItems()[SHIELD_SLOT].getId();
        boolean arcane = shield == 13738;
        boolean divine = shield == 13740;
        boolean elysian = shield == 13472;
        boolean spectral = shield == 13744;
        return divine || elysian || arcane || spectral;
    }

    public boolean wearingHalberd() {
        ItemDefinition def = ItemDefinition.forId(getPlayer().getEquipment().getItems()[Equipment.WEAPON_SLOT].getId());
        return def != null && (def.getName().toLowerCase().endsWith("halberd") || def.getName().toLowerCase().endsWith("itur"));
    }

    public boolean ringOfFortune() {

        if (getPlayer().getEquipment().contains(221140))
            return true;
        return getPlayer().lightbearerOptions[4] && getPlayer().getEquipment().contains(21077);
    }

    public void handleLightbearer(int itemId) {

        //[0] Berserker Ring
        //[1] Warrior Ring
        //[2] Archers' Ring
        //[3] Seers' Ring
        //[4] Ring of Fortune
        //[5] Tyrannical Ring
        //[6] Treasonous Ring
        //[7] Ring of the Gods

        if (itemId == 6737 && !getPlayer().lightbearerOptions[0]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of a Berserker ring to your Lightbearer!");
            getPlayer().lightbearerOptions[0] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 6737 && getPlayer().lightbearerOptions[0]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }

        if (itemId == 6735 && !getPlayer().lightbearerOptions[1]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of a Warrior ring to your Lightbearer!");
            getPlayer().lightbearerOptions[1] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 6735 && getPlayer().lightbearerOptions[1]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }

        if (itemId == 6733 && !getPlayer().lightbearerOptions[2]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of an Archer ring to your Lightbearer!");
            getPlayer().lightbearerOptions[2] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 6733 && getPlayer().lightbearerOptions[2]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }

        if (itemId == 6731 && !getPlayer().lightbearerOptions[3]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of a Seers' ring to your Lightbearer!");
            getPlayer().lightbearerOptions[3] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 6731 && getPlayer().lightbearerOptions[3]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }

        if (itemId == 221140 && !getPlayer().lightbearerOptions[4]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of a Ring of Fortune to your Lightbearer!");
            getPlayer().lightbearerOptions[4] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 221140 && getPlayer().lightbearerOptions[4]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }

        if (itemId == 12603 && !getPlayer().lightbearerOptions[5]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of a Tyrannical ring to your Lightbearer!");
            getPlayer().lightbearerOptions[5] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 12603 && getPlayer().lightbearerOptions[5]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }

        if (itemId == 12605 && !getPlayer().lightbearerOptions[6]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of a Treasonous ring to your Lightbearer!");
            getPlayer().lightbearerOptions[6] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 12605 && getPlayer().lightbearerOptions[6]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }

        if (itemId == 12601 && !getPlayer().lightbearerOptions[7]) {
            getPlayer().getPacketSender().sendMessage("You've added the power of a Ring of the Gods to your Lightbearer!");
            getPlayer().lightbearerOptions[7] = true;
            getPlayer().getInventory().delete(itemId, 1);
        } else if (itemId == 12601 && getPlayer().lightbearerOptions[7]) {
            getPlayer().getPacketSender().sendMessage("Your ring already has this power.");
        }


    }

    public int handleLightbearerBonuses(int bonusType) {
        int bonus = 0;

        if (getPlayer().getEquipment().contains(21077)) {

            //[0] Berserker Ring
            //[1] Warrior Ring
            //[2] Archers' Ring
            //[3] Seers' Ring
            //[4] Ring of Fortune
            //[5] Tyrannical Ring
            //[6] Treasonous Ring
            //[7] Ring of the Gods
            switch (bonusType) {

                case 14:
                    if (getPlayer().lightbearerOptions[0]) {
                        bonus += 4;
                    }
                    break;

                case 1:
                case 6:
                    if (getPlayer().lightbearerOptions[1]) {
                        bonus += 4;
                    }
                    break;

                case 4:
                case 9:
                    if (getPlayer().lightbearerOptions[2]) {
                        bonus += 4;
                    }
                    break;

                case 3:
                case 8:
                    if (getPlayer().lightbearerOptions[3]) {
                        bonus += 4;
                    }
                    break;

                case 2:
                case 7:
                    if (getPlayer().lightbearerOptions[5]) {
                        bonus += 4;
                    }
                    break;

                case 0:
                case 5:
                    if (getPlayer().lightbearerOptions[6]) {
                        bonus += 4;
                    }
                    break;

                case 16:
                    if (getPlayer().lightbearerOptions[7]) {
                        bonus += 8;
                    }
                    break;

            }

        }

        bonus += (int) (bonus * (.12 * getPlayer().qtyRingUpgrade));

        return bonus;
    }

    public boolean hasWhip() {

        int weapon = getPlayer().getEquipment().getItems()[WEAPON_SLOT].getId();

        switch (weapon) {

            case 15441:
            case 15442:
            case 15443:
            case 15444:
            case 4151:
                return true;
        }
        return false;
    }

    public boolean hasDarkBow() {

        int weapon = getPlayer().getEquipment().getItems()[WEAPON_SLOT].getId();

        switch (weapon) {

            case 15701:
            case 15702:
            case 15703:
            case 15704:
            case 11235:
                return true;
        }
        return false;
    }

    public boolean hasStaffOfLight() {

        int weapon = getPlayer().getEquipment().getItems()[WEAPON_SLOT].getId();

        switch (weapon) {

            case 14004:
            case 14005:
            case 14006:
            case 14007:
            case 15486:
                return true;
        }
        return false;
    }

    public boolean properEquipmentForWilderness() {
        int count = 0;
        for (Item item : getValidItems()) {
            if (item != null && item.tradeable())
                count++;
        }
        return count >= 3;
    }

    public int gildedEquipBonus() {

        int gildedBonus = 0;

        if (getPlayer().getEquipment().contains(21013))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(21014))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(21015))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(21022))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(21023))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(21024))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(21042))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(21046))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(18892))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(18893))
            gildedBonus++;
        if (getPlayer().getEquipment().contains(18894))
            gildedBonus++;
        if(getPlayer().getEquipment().contains(219476)){
            gildedBonus++;
        }

        return gildedBonus;
    }

    public int taintedEquipBonus() {

        int taintedBonus = 0;

        if (getPlayer().getEquipment().contains(2042))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2043))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2044))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2045))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2046))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2047))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2056))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2057))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2051))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2052))
            taintedBonus++;
        if (getPlayer().getEquipment().contains(2053))
            taintedBonus++;
        if(getPlayer().getEquipment().contains(219476)){
            taintedBonus++;
        }

        return taintedBonus;
    }

    public int bloodEquipBonus() {

        int bloodBonus = 0;

        if (getPlayer().getEquipment().contains(21010))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(21011))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(21012))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(21019))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(21020))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(21021))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(21041))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(21045))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(18899))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(18900))
            bloodBonus++;
        if (getPlayer().getEquipment().contains(18901))
            bloodBonus++;
        if(getPlayer().getEquipment().contains(219476)){
            bloodBonus++;
        }

        return bloodBonus;
    }

    public int soulEquipBonus() {

        int soulBonus = 0;

        if (getPlayer().getEquipment().contains(21007))
            soulBonus++;
        if (getPlayer().getEquipment().contains(21008))
            soulBonus++;
        if (getPlayer().getEquipment().contains(21009))
            soulBonus++;
        if (getPlayer().getEquipment().contains(21016))
            soulBonus++;
        if (getPlayer().getEquipment().contains(21017))
            soulBonus++;
        if (getPlayer().getEquipment().contains(21018))
            soulBonus++;
        if (getPlayer().getEquipment().contains(21040))
            soulBonus++;
        if (getPlayer().getEquipment().contains(21044))
            soulBonus++;
        if (getPlayer().getEquipment().contains(18905))
            soulBonus++;
        if (getPlayer().getEquipment().contains(18906))
            soulBonus++;
        if (getPlayer().getEquipment().contains(18907))
            soulBonus++;
        if (getPlayer().getEquipment().contains(2711))
            soulBonus++;
        if(getPlayer().getEquipment().contains(219476)){
            soulBonus++;
        }

        return soulBonus;
    }

    public int damageAbsorb() {

        int absorb = 0;

        //soul armor
            absorb += 3 * soulEquipBonus();
        //divine
        if (getPlayer().getEquipment().contains(13740))
            absorb += 25;
        //dinh's
        if (getPlayer().getEquipment().contains(4448))
            absorb += 50;
        //Harmonised staff spec
        if (getPlayer().Harmonised)
            absorb += 80;
        //justiciar
        if (getPlayer().getEquipment().contains(21000))
            absorb += 5;
        if (getPlayer().getEquipment().contains(21001))
            absorb += 5;
        if (getPlayer().getEquipment().contains(21002))
            absorb += 5;
        if (getPlayer().getEquipment().wearingJusticiar())
            absorb += 10;
        //masori
        if (getPlayer().getEquipment().contains(21070))
            absorb += 5;
        if (getPlayer().getEquipment().contains(21071))
            absorb += 5;
        if (getPlayer().getEquipment().contains(21072))
            absorb += 5;
        //masori (f)
        if (getPlayer().getEquipment().contains(21073))
            absorb += 5;
        if (getPlayer().getEquipment().contains(21074))
            absorb += 5;
        if (getPlayer().getEquipment().contains(21075))
            absorb += 5;
        if (getPlayer().getEquipment().wearingMasori())
            absorb += 10;
        //ancestral
        if (getPlayer().getEquipment().contains(2051) || getPlayer().getEquipment().contains(2765) || getPlayer().getEquipment().contains(18888) || getPlayer().getEquipment().contains(18892) || getPlayer().getEquipment().contains(18899) || getPlayer().getEquipment().contains(18905))
            absorb += 5;
        if (getPlayer().getEquipment().contains(2052) || getPlayer().getEquipment().contains(2766) || getPlayer().getEquipment().contains(18889) || getPlayer().getEquipment().contains(18893) || getPlayer().getEquipment().contains(18900) || getPlayer().getEquipment().contains(18906))
            absorb += 5;
        if (getPlayer().getEquipment().contains(2053) || getPlayer().getEquipment().contains(2767) || getPlayer().getEquipment().contains(18890) || getPlayer().getEquipment().contains(18894) || getPlayer().getEquipment().contains(18901) || getPlayer().getEquipment().contains(18907))
            absorb += 5;
        if (getPlayer().getEquipment().wearingAncestral())
            absorb += 10;


        if (getPlayer().qtyHelmUpgrade > 0 && (getPlayer().idHelmUpgrade == 21000 || getPlayer().idHelmUpgrade == 21070 || getPlayer().idHelmUpgrade == 18888)) {
            if (getPlayer().qtyHelmUpgrade == 10)
                absorb += 3;
            else if (getPlayer().qtyHelmUpgrade >= 5)
                absorb += 1;
        } else if (getPlayer().qtyBodyUpgrade > 0 && (getPlayer().idHelmUpgrade == 21001 || getPlayer().idHelmUpgrade == 21071 || getPlayer().idHelmUpgrade == 18889)) {
            if (getPlayer().qtyBodyUpgrade == 10)
                absorb += 3;
            else if (getPlayer().qtyBodyUpgrade >= 5)
                absorb += 1;
        } else if (getPlayer().qtyLegsUpgrade > 0 && (getPlayer().idHelmUpgrade == 21002 || getPlayer().idHelmUpgrade == 21072 || getPlayer().idHelmUpgrade == 18890)) {
            if (getPlayer().qtyLegsUpgrade == 10)
                absorb += 3;
            else if (getPlayer().qtyLegsUpgrade >= 5)
                absorb += 1;
        } else if (getPlayer().qtyShieldUpgrade > 0 && (getPlayer().idShieldUpgrade == 13740 || getPlayer().idShieldUpgrade == 4448)) {
            if (getPlayer().qtyShieldUpgrade == 10)
                absorb += 10;
            else if (getPlayer().qtyShieldUpgrade >= 5)
                absorb += 5;
        }

        getPlayer().absorbBonus = absorb;

        //getPlayer().getPacketSender().sendMessage("Absorb: " + absorb);

        return absorb;
    }

    public boolean usingBlowpipe(Player player) {

        boolean blowpipe = player.getEquipment().contains(12926);


        if (player.getEquipment().contains(12927))
            blowpipe = true;

        return blowpipe;
    }


    public int rangeDamageBonus(Player player) {

        int rangedStrength = 0;

        switch (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId()) {
            case 14011: //Pernix Cowl
                rangedStrength += 8;
                break;
            case 21070: //Masori Mask
                rangedStrength += 6;
                break;
            case 20013: //vanguard
            case 20012: //trickster
            case 11718: //Armadyl Helm
            case 21007:
            case 21010:
            case 21013:
            case 21025:
                rangedStrength += 5;
                break;
            case 13876: //Morrigan's Coif
                if (player.getLocation() == Location.WILDERNESS) {
                    rangedStrength += 10;
                    break;
                }
        }

        switch (player.getEquipment().getItems()[Equipment.BODY_SLOT].getId()) {
            case 14012: //Pernix Body
                rangedStrength += 15;
                break;
            case 21071: //Masori Body
                rangedStrength += 12;
                break;
            case 11720://Armadyl Chestplate
            case 20014://vanguard
            case 20010://trickster
            case 21008:
            case 21011:
            case 21014:
            case 21026:
                rangedStrength += 10;
                break;
            case 13870: //Morrigan's Body
                if (player.getLocation() == Location.WILDERNESS) {
                    rangedStrength += 18;
                    break;
                }
        }

        switch (player.getEquipment().getItems()[Equipment.LEG_SLOT].getId()) {
            case 14013: //Pernix Legs
                rangedStrength += 12;
                break;
            case 21072: //Masori Legs
                rangedStrength += 10;
                break;
            case 11722://Armadyl Chainskirt
            case 14062://vanguard
            case 20011://trickster
            case 21009:
            case 21012:
            case 21015:
            case 21027:
                rangedStrength += 8;
                break;
            case 13873: //Morrigan's Chaps
                if (player.getLocation() == Location.WILDERNESS) {
                    rangedStrength += 15;
                    break;
                }
        }

        switch (player.getEquipment().getItems()[Equipment.CAPE_SLOT].getId()) {
            case 21069: //Masori assembler
                rangedStrength += 8;
                break;
            case 221898: //Assembler Max
            case 222109: //Ava's assembler
                rangedStrength += 5;
                break;
            case 213069:
            case 219476:
                rangedStrength += 10;
                break;
            case 14056:
                if (player.getLocation() == Location.PESTILENT_BLOAT || player.getLocation() == Location.MAIDEN_SUGADINTI || player.getLocation() == Location.VERZIK_VITUR)
                    rangedStrength += 8;
        }

        switch (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {
            case 19143: //God Bow
            case 19146: //God Bow
            case 19149: //God Bow
                rangedStrength += 30;
                break;
            case 13879: //Morrigan's Javelins
                rangedStrength += 90;
                break;
            case 13953: //Corrupt Morrigan's Javelins
                rangedStrength += 81;
                break;
            case 20998: //Twisted Bow
                rangedStrength += 50;
                break;
            case 13051: //Armadyl Crossbow
                rangedStrength += 25;
                break;
            case 18844: //DHCB
                rangedStrength += 15;
                break;
            case 4453: //Dragon Crossbow
                rangedStrength += 10;
                break;
            case 16867:
            case 15775:
                rangedStrength += 10;
                break;
            case 16869:
            case 15776:
                rangedStrength += 20;
                break;
            case 16871:
            case 15777:
                rangedStrength += 30;
                break;
            case 16873:
            case 15778:
                rangedStrength += 40;
                break;
            case 16875:
            case 15779:
                rangedStrength += 50;
                break;
            case 16877:
            case 15780:
                rangedStrength += 60;
                break;
            case 16879:
            case 15781:
                rangedStrength += 70;
                break;
            case 16881:
            case 15782:
                rangedStrength += 80;
                break;
            case 16883:
            case 15783:
                rangedStrength += 90;
                break;
            case 16885:
            case 15784:
                rangedStrength += 100;
                break;
            case 16887:
            case 15785:
                rangedStrength += 110;
                break;

        }


        //Necklace of Anguish
        if (player.getEquipment().contains(18895))
            rangedStrength += 5;
        if (player.getEquipment().contains(222249))
            rangedStrength += 5;

        //Boots
        if (player.getEquipment().contains(20001))
            rangedStrength += 8;
        if (player.getEquipment().contains(12708))
            rangedStrength += 5;

        //Twisted Buckler
        if (player.getEquipment().contains(4452))
            rangedStrength += 5;

        //Dragonfire Ward
        if (player.getEquipment().contains(222002))
            rangedStrength += 4;

        //Mercenary Gloves
        if (player.getEquipment().contains(18347))
            rangedStrength += 5;

        return rangedStrength;
    }

    public int dragonfireEquipment(Player p) {
        int protection = 0;

        switch (p.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId()) {

            case 1540:
            case 11613:
            case 11283:
            case 222002:
                return 50;

        }

        return 0;

    }

    public double magicDamageBonus(Player p) {

        double damageMultiplier = 0;

        switch (p.getEquipment().getItems()[Equipment.AMULET_SLOT].getId()) {
            case 18897: //occult
            case 219720: //occult (or)
                damageMultiplier += .05;
                break;
        }

        switch (p.getEquipment().getItems()[Equipment.HANDS_SLOT].getId()) {
            case 18847: //tormented bracelet
            case 223444: //tormented bracelet (or)
                damageMultiplier += .05;
                break;
        }

        switch (p.getEquipment().getItems()[Equipment.HEAD_SLOT].getId()) {
            case 18888: //Ancestral hat
            case 2051:
            case 2765:
            case 18892:
            case 18899:
            case 18905:
            case 20012: //trickster
            case 20016: //battle-mage
                damageMultiplier += .05;
                break;
            case 14014: //Virtus mask
                damageMultiplier += .06;
                break;
            case 13864: //Zuriel's Hood
                if (p.getLocation() == Location.WILDERNESS) {
                    damageMultiplier += .08;
                    break;
                }
        }

        switch (p.getEquipment().getItems()[Equipment.BODY_SLOT].getId()) {
            case 18889: //Ancestral robe top
            case 2052:
            case 2766:
            case 18893:
            case 18900:
            case 18906:
            case 20010: //trickster
            case 20017: //battle-mage
                damageMultiplier += .10;
                break;
            case 14015: //Virtus robe top
                damageMultiplier += .12;
                break;
            case 13858: //Zuriel's Robe top
                if (p.getLocation() == Location.WILDERNESS) {
                    damageMultiplier += .15;
                    break;
                }
        }

        switch (p.getEquipment().getItems()[Equipment.LEG_SLOT].getId()) {
            case 18890: //Ancestral robe bottoms
            case 2053:
            case 2767:
            case 18894:
            case 18901:
            case 18907:
            case 20011: //trickster
            case 20018: //battle-mage
                damageMultiplier += .10;
                break;
            case 14016: //Virtus robe bottoms
                damageMultiplier += .12;
                break;
            case 13861: //Zuriel's Robe bottoms
                if (p.getLocation() == Location.WILDERNESS) {
                    damageMultiplier += .15;
                    break;
                }
        }

        switch (p.getEquipment().getItems()[Equipment.FEET_SLOT].getId()) {
            case 20002: //Ragefire Boots
                damageMultiplier += .08;
                break;
            case 13235: //Eternal Boots
                damageMultiplier += .05;
                break;
        }

        if (p.getInventory().contains(20560))
            damageMultiplier += .05;

        switch (p.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId()) {
            case 13738: //Arcane Spirit Shield
                damageMultiplier += .05;
                break;
            case 765: //Elidinis Ward
                damageMultiplier += .08;
                break;
            case 766: //Elidinis Ward (f)
                damageMultiplier += .10;
                break;
            case 767: //Elidinis Ward (or)
                damageMultiplier += .12;
                break;

        }


        switch (p.getEquipment().getItems()[Equipment.CAPE_SLOT].getId()) {
            case 14058:
            case 14059:
            case 14060:
            case 221795:
            case 221793:
            case 221791:
            case 221776:
            case 221780:
            case 221784:
                damageMultiplier += .05;
                break;
            case 219476:
            case 213069:
                damageMultiplier += .20;
                break;
            case 14056:
                if (p.getLocation() == Location.PESTILENT_BLOAT || p.getLocation() == Location.MAIDEN_SUGADINTI || p.getLocation() == Location.VERZIK_VITUR)
                    damageMultiplier += .15;

                break;
        }

        switch (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {
            case 4675: //Ancient Staff
                damageMultiplier += .10;
                break;
            case 6914: //Master's Wand
            case 17293: //Demon hunter staff
            case 20568: //Nightmare Staff
                damageMultiplier += .15;
                break;
            case 15486: //Staff of Light
                damageMultiplier += .18;
                break;
            case 12284: //Toxic Staff of the Dead
            case 20569: //Harmonised Nightmare Staff
            case 20570: //Volatile Nightmare Staff
            case 20571: //Eldritch Nightmare Staff
            case 21005: //Sanguinesti Staff
                damageMultiplier += .10;
                break;
            case 4454: //Kodai Wand
                damageMultiplier += .25;
                break;
            case 13867: //Zuriel
                if (p.getLocation() == Location.WILDERNESS)
                    damageMultiplier += .30;
                break;
            case 223898: //Crystal staff (basic)
                damageMultiplier += .10;
                break;
            case 223899: //Crystal staff (attuned)
                damageMultiplier += .20;
                break;
            case 223900: //Crystal staff (perfected)
                damageMultiplier += .30;
                break;
        }

        switch (p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId()) {
            case 19868: //Celestial Surgebox
                damageMultiplier += .05;
                break;
        }

        return damageMultiplier;
    }


    /**
     * Gets the amount of item of a type a player has, for example, gets how many Zamorak items a player is wearing for GWD
     *
     * @param p The player
     * @param s The item type to search for
     * @return The amount of item with the type that was found
     */
    public static int getItemCount(Player p, String s, boolean inventory) {
        int count = 0;
        for (Item t : p.getEquipment().getItems()) {
            if (t == null || t.getId() < 1 || t.getAmount() < 1)
                continue;
            if (t.getDefinition().getName().toLowerCase().contains(s.toLowerCase()))
                count++;
        }
        if (inventory)
            for (Item t : p.getInventory().getItems()) {
                if (t == null || t.getId() < 1 || t.getAmount() < 1)
                    continue;
                if (t.getDefinition().getName().toLowerCase().contains(s.toLowerCase()))
                    count++;
            }
        return count;
    }

    public boolean isNaked(Player player) {
        int count = 0;
        for (Item t : player.getEquipment().getItems()) {
            if (t == null || t.getId() < 1 || t.getAmount() < 1)
                continue;
            if (t != null)
                count++;
        }

        return count == 0;
    }


    final static int voidMeleeHelm = 11665;
    final static int voidRangeHelm = 11664;
    final static int voidMagicHelm = 11663;
    final static int voidTop = 8839;
    final static int voidBottoms = 8840;
    final static int voidGloves = 8842;
    final static int eliteVoidTop = 19785;
    final static int eliteVoidBottoms = 19786;

}
