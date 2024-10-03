package com.arlania.world.content.combat.calculations;

import com.arlania.model.GameMode;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.model.Skill;
import com.arlania.model.container.impl.Equipment;
import com.arlania.world.content.BonusManager;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.magic.CombatSpell;
import com.arlania.world.content.combat.magic.SurgeBox;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

import static com.arlania.world.content.combat.calculations.PerkModifier.perkModifier;
import static com.arlania.world.content.combat.calculations.PvMModifier.bossImmunity;
import static com.arlania.world.content.combat.calculations.PvMModifier.pvmModifier;
import static com.arlania.world.content.combat.calculations.SlayerModifier.slayerModifier;

public class PlayerMaxHit {
    public static int playerMaxHit(Player plr, Character victim, CombatType combatType) {

        BonusManager.update(plr, 0, 0);

        int maxHit = 0;
        int base = 0;
        double damageMultiplier = 1;
        double damageModifier = 1;
        double effective = 0;

        double rangedStrength = RangedStrength.getRangedStr(plr);
        double meleeStrength = plr.getBonusManager().getOtherBonus()[0];
        double magicStrength = plr.getEquipment().magicDamageBonus(plr);

        CombatSpell spell = plr.getCurrentlyCasting();

        int meleeStr = plr.getSkillManager().getCurrentLevel(Skill.STRENGTH);
        int rangedStr = plr.getSkillManager().getCurrentLevel(Skill.RANGED);
        int magicStr = plr.getSkillManager().getCurrentLevel(Skill.MAGIC);

        if(plr.getLocation() == Locations.Location.WILDERNESS && (plr.wildBotanist == 0 || victim.isPlayer())) {
            meleeStr = Math.min(120, meleeStr);
            rangedStr = Math.min(120, rangedStr);
            magicStr = Math.min(120, magicStr);
        }


        switch (combatType) {
            case MELEE:
                effective = ((meleeStr) * PrayerStrength.getPrayerStr(plr, combatType)) + 8; //level, prayer bonus
                base = (int) ((0.5 + effective) * ((meleeStrength + 64) / 640));
                break;
            case RANGED:
                effective = ((rangedStr) * PrayerStrength.getPrayerStr(plr, combatType)) + 8; //level, prayer bonus
                base = (int) ((0.5) + ((effective) * (rangedStrength + 64) / 640));
                break;
            case MAGIC: //Magic prayer strength added at the end
                effective = (magicStr); //level, prayer bonus without prayer?
                if (spell != null) {
                    if (spell.maximumHit() > 0)
                        base = (int) (((spell.maximumHit() * (1 + magicStrength)) + (effective / 64)) / 9);
                }
                break;
        }


        double specialBonus = 1;
        if (plr.isSpecialActivated()) {
            specialBonus = plr.getCombatSpecial().getStrengthBonus();

            if (plr.Critical == 1 && PerkHandler.canUseMasteryPerks(plr)) {
                double factor = plr.completedLogs * .005;
                specialBonus += factor;
            }
        }

        //Amulet of the Damned (Barrows)
        if (plr.getEquipment().amuletOfTheDamnedEffect())
            damageMultiplier += .15;

        //Regular Void Sets
        if (plr.getEquipment().wearingMeleeVoid() && combatType == CombatType.MELEE)
            damageMultiplier += .05;
        if (plr.getEquipment().wearingRangedVoid() && combatType == CombatType.RANGED)
            damageMultiplier += .05;
        if (plr.getEquipment().wearingMagicVoid() && combatType == CombatType.MAGIC)
            damageMultiplier += .05;

        //Elite Void Set
        if (plr.getEquipment().wearingMeleeEliteVoid() && combatType == CombatType.MELEE)
            damageMultiplier += .1;
        if (plr.getEquipment().wearingRangedEliteVoid() && combatType == CombatType.RANGED)
            damageMultiplier += .1;
        if (plr.getEquipment().wearingMagicEliteVoid() && combatType == CombatType.MAGIC)
            damageMultiplier += .1;

        //Inquisitor Effect
        if (plr.getEquipment().wearingFullInquisitor() && combatType == CombatType.MELEE)
            damageMultiplier += .05;

        //Obsidian Set
        if (Equipment.hasObsidianEffect(plr) && combatType == CombatType.MELEE)
            damageMultiplier += .2;

        if (plr.getEquipment().getItems()[3].getId() == 4718 && plr.getEquipment().getItems()[0].getId() == 4716 && plr.getEquipment().getItems()[4].getId() == 4720 && plr.getEquipment().getItems()[7].getId() == 4722)
            base += (int) ((plr.getSkillManager().getMaxLevel(Skill.CONSTITUTION) - plr.getConstitution()) * .045) + 1;
        if (specialBonus > 1)
            base = (int) (base * specialBonus);

        //PVM Boosts
        if (victim.isNpc()) {
            NPC npc = victim.getAsNpc();

            damageModifier += 1 + pvmModifier(plr, npc, combatType) + slayerModifier(plr, npc) + perkModifier(plr, combatType);

            if (bossImmunity(plr, npc, combatType))
                damageModifier = 0;
        }
        else if (victim.isPlayer()) {
            Player p = (Player) victim;
            if (p.hasStaffOfLightEffect()) {
                maxHit = maxHit / 2;
                p.performGraphic(new Graphic(2319));
            }
        }

        if (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getId() == 21005) {

            if (plr.getSanguinestiCharges() == 0) {
                damageMultiplier = 0;
                plr.getPacketSender().sendMessage("Your Sanguinesti Staff has no charges.");
            } else if (plr.getSanguinestiCharges() > 0 && plr.getAutocastSpell().spellId() == 12447) {
                if (plr.getSanguinestiCharges() % 500 == 0) {
                    plr.getPacketSender().sendMessage("Your Sanguinesti Staff now has " + plr.getSanguinestiCharges() + " charges.");
                }
            }
        }

        //Surge Box Handler
        if(plr.getCastSpell() != null) {
            if(plr.getCastSpell().spellId() == 11831 || plr.getCastSpell().spellId() == 11851 || plr.getCastSpell().spellId() == 11871 || plr.getCastSpell().spellId() == 11891) {

                if(plr.getLocation() == Locations.Location.SHR) {
                    //We don't need to run the Surge Box Handler if we are in SHR
                }
                else
                    SurgeBox.handleCast(plr);
            }
        }

        if (plr.getLocation() != Locations.Location.WILDERNESS) {

            if ((plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("vesta")) ||
                    (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("statius")) ||
                    (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("zuriel")) ||
                    (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase().contains("morrigan"))) {
                damageMultiplier = 0;
                plr.getPacketSender().sendMessage("PVP weapons don't hit outside of the wilderness.");
            }
        }

        maxHit = (int) (base * damageMultiplier * damageModifier * 10);

        if (plr.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            if (plr.Gladiator) { //TODO SEASONAL test for max hit boost
                double gladiatorBoost = plr.maxHealth() - plr.getSkillManager().getCurrentLevel(Skill.CONSTITUTION);

                maxHit *= (1 + (gladiatorBoost / 1000));
            }
        }

        if(combatType == CombatType.MAGIC && plr.getCastSpell() != null) {
            maxHit *= PrayerStrength.getPrayerStr(plr, combatType);
        }

        return maxHit;
    }
}
