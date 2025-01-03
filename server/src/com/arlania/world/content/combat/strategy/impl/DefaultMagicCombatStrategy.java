package com.arlania.world.content.combat.strategy.impl;

import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.magic.CombatSpells;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.content.minigames.impl.Dueling;
import com.arlania.world.content.minigames.impl.Dueling.DuelRule;
import com.arlania.world.entity.Entity;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * The default combat strategy assigned to an {@link Entity} during a magic
 * based combat session.
 *
 * @author lare96
 */
public class DefaultMagicCombatStrategy implements CombatStrategy {

    @Override
    public boolean canAttack(Character entity, Character victim) {

        // Npcs don't need to be checked.
        if (entity.isNpc()) {
            if (victim.isPlayer()) {
                Player p = (Player) victim;

            }
            return true;
        }

        // Create the player instance.
        Player player = (Player) entity;

        if (Dueling.checkRule(player, DuelRule.NO_MAGIC)) {
            player.getPacketSender().sendMessage("Magic-attacks have been turned off in this duel!");
            player.getCombatBuilder().reset(true);
            return false;
        }

        // We can't attack without a spell.
        if (player.getCastSpell() == null)
            player.setCastSpell(player.getAutocastSpell());

        if (player.getCastSpell() == null) {
            return false;
        }

        // Check the cast using the spell implementation.
        return player.getCastSpell().canCast(player, true);
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {

        if (entity.isPlayer()) {
            Player player = (Player) entity;
            player.prepareSpell(player.getCastSpell(), victim);
            if (player.isAutocast() && player.getAutocastSpell() != null)
                player.setCastSpell(player.getAutocastSpell());
            player.setPreviousCastSpell(player.getCastSpell());
        } else if (entity.isNpc()) {
            NPC npc = (NPC) entity;

            switch (npc.getId()) {
                case 13:
                case 172:
                case 174:
                    npc.prepareSpell(RandomUtility.randomElement(new CombatSpells[]{CombatSpells.WEAKEN, CombatSpells.FIRE_STRIKE, CombatSpells.EARTH_STRIKE, CombatSpells.WATER_STRIKE}).getSpell(), victim);
                    break;
                case 2025:
                case 1643:
                    npc.prepareSpell(RandomUtility.randomElement(new CombatSpells[]{CombatSpells.FIRE_WAVE, CombatSpells.EARTH_WAVE, CombatSpells.WATER_WAVE}).getSpell(), victim);
                    break;
                case 3495:
                    npc.prepareSpell(RandomUtility.randomElement(new CombatSpells[]{CombatSpells.SMOKE_BLITZ, CombatSpells.ICE_BLITZ, CombatSpells.ICE_BURST, CombatSpells.SHADOW_BARRAGE}).getSpell(), victim);
                    break;
                case 3496:
                    npc.prepareSpell(RandomUtility.randomElement(new CombatSpells[]{CombatSpells.BLOOD_BARRAGE, CombatSpells.BLOOD_BURST, CombatSpells.BLOOD_BLITZ, CombatSpells.BLOOD_RUSH}).getSpell(), victim);
                    break;
                case 3491:
                    npc.prepareSpell(RandomUtility.randomElement(new CombatSpells[]{CombatSpells.ICE_BARRAGE, CombatSpells.ICE_BLITZ, CombatSpells.ICE_BURST, CombatSpells.ICE_RUSH}).getSpell(), victim);
                    break;
                case 13454:
                    npc.prepareSpell(CombatSpells.ICE_BARRAGE.getSpell(), victim);
                    break;
                case 13453:
                case 21604:
                    npc.prepareSpell(CombatSpells.BLOOD_BARRAGE.getSpell(), victim);
                    break;
                case 13452:
                case 21606:
                    npc.prepareSpell(CombatSpells.SHADOW_BARRAGE.getSpell(), victim);
                    break;
                case 13451:
                case 21605:
                    npc.prepareSpell(CombatSpells.SMOKE_BARRAGE.getSpell(), victim);
                    break;
                case 2896:
                    npc.prepareSpell(CombatSpells.WATER_STRIKE.getSpell(), victim);
                    break;
                case 2882:
                    npc.prepareSpell(CombatSpells.DAGANNOTH_PRIME.getSpell(), victim);
                    break;
                case 6254:
                    npc.prepareSpell(CombatSpells.WIND_WAVE.getSpell(), victim);
                    break;
                case 6257:
                    npc.prepareSpell(CombatSpells.WATER_WAVE.getSpell(), victim);
                    break;
                case 6278:
                    npc.prepareSpell(CombatSpells.SHADOW_RUSH.getSpell(), victim);
                    break;
                case 6221:
                    npc.prepareSpell(CombatSpells.FIRE_BLAST.getSpell(), victim);
                    break;
                case 22360: //maiden tob
                    npc.prepareSpell(CombatSpells.MAIDEN_BARRAGE.getSpell(), victim);
                    break;
                case 10565: //Wildercress mage
                    npc.prepareSpell(CombatSpells.FIRE_BOLT.getSpell(), victim);
                    break;
                case 10586: //Roseblood mage
                    npc.prepareSpell(CombatSpells.FIRE_BLAST.getSpell(), victim);
                    break;
                case 10599: //Spiritbloom mage
                    npc.prepareSpell(CombatSpells.FIRE_WAVE.getSpell(), victim);
                    break;
                case 10603: //Celestial mage
                    npc.prepareSpell(CombatSpells.FIRE_SURGE.getSpell(), victim);
                    break;
            }

            if (npc.getCurrentlyCasting() == null)
                npc.prepareSpell(CombatSpells.WIND_STRIKE.getSpell(), victim);
        }

        if (entity.getCurrentlyCasting().maximumHit() == -1) {
            return new CombatContainer(entity, victim, CombatType.MAGIC, true);
        }

        return new CombatContainer(entity, victim, 1, CombatType.MAGIC, true);
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        int distance = 8;
        if (entity.isNpc()) {
            switch (((NPC) entity).getId()) {
                case 2896:
                case 13451:
                case 13452:
                case 13453:
                case 13454:
                    distance = 40;
                    break;
            }
        }
        return distance;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        return false;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MAGIC;
    }
}
