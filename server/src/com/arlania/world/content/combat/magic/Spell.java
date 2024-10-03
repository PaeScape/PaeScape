package com.arlania.world.content.combat.magic;

import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.entity.Entity;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.player.Player;

import java.util.Optional;


/**
 * A parent class represented by any generic spell able to be cast by an
 * {@link Entity}.
 *
 * @author lare96
 */
public abstract class Spell {

    /**
     * Determines if this spell is able to be cast by the argued {@link Player}.
     * We do not include {@link Npc}s here since no checks need to be made for
     * them when they cast a spell.
     *
     * @param player the player casting the spell.
     * @return <code>true</code> if the spell can be cast by the player,
     * <code>false</code> otherwise.
     */
    public boolean canCast(Player player, boolean delete) {

        // We first check the level required.
        if (player.getSkillManager().getCurrentLevel(Skill.MAGIC) < levelRequired()) {
            player.getPacketSender().sendMessage(
                    "You need a Magic level of " + levelRequired() + " to cast this spell.");
            player.getCombatBuilder().reset(true);
            return false;
        }
        if (player.isSpecialActivated()) {
            player.getPacketSender().sendSpriteChange(41006, 945);
            player.setSpecialActivated(false);
            CombatSpecial.updateBar(player);
        }

        // Then we check the items required.
        if (itemsRequired(player).isPresent()) {

            // Suppress the runes based on the staff, we then use the new array
            // of items that don't include suppressed runes.
            Item[] items = PlayerMagicStaff.suppressRunes(player, itemsRequired(player).get());

            if (player.getEquipment().contains(20580) || player.getEquipment().contains(18346)) {
                items = PlayerMagicTomes.suppressRunes(player, items);
            }

            if (player.getInventory().contains(212791) || player.getEquipment().contains(212791)) {
                items = PlayerRunePouch.suppressRunes(player, items);
            }

            if (player.getInventory().contains(762) || player.getEquipment().contains(762)) {
                items = PlayerRunePouch.suppressRunes(player, items);
            }


            // Now check if we have all of the runes.
            if (!player.getInventory().containsAll(items)) {

                // We don't, so we can't cast.
                player.getPacketSender().sendMessage("You do not have the required items to cast this spell.");
                resetPlayerSpell(player);
                player.getCombatBuilder().reset(true);
                return false;
            }


            // We've made it through the checks, so we have the items and can
            // remove them now.
            if (delete) {

                int runeReduction = 0;

                runeReduction = player.Prophetic * 20;

                if ((player.Prophetic == 0) || (player.Prophetic >= 1 && RandomUtility.inclusiveRandom(100) >= runeReduction)) {
                    for (Item it : items) {
                        if (it != null)
                            player.getInventory().delete(it);
                    }
                }
            }


            // Finally, we check the equipment required.
            if (equipmentRequired(player).isPresent()) {
                if (!player.getEquipment().containsAll(
                        equipmentRequired(player).get())) {
                    player.getPacketSender().sendMessage(
                            "You do not have the required equipment to cast this spell.");
                    resetPlayerSpell(player);
                    player.getCombatBuilder().reset(true);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Resets the argued player's autocasting if they're currently in combat.
     *
     * @param player the player to reset.
     */
    // To prevent a bit of boilerplate code.
    private void resetPlayerSpell(Player player) {
        if (player.getCombatBuilder().isAttacking() || player.getCombatBuilder().isBeingAttacked() && player.isAutocast()) {
            //    player.setAutocastSpell(null);
            //    player.setAutocast(false);
            //    player.getPacketSender().sendConfig(108, 0);
            player.setCastSpell(null);
        }
    }

    public abstract int spellId();

    /**
     * The level required to cast this spell.
     *
     * @return the level required to cast this spell.
     */
    public abstract int levelRequired();

    /**
     * The base experience given when this spell is cast.
     *
     * @return the base experience given when this spell is cast.
     */
    public abstract int baseExperience();

    /**
     * The items required to cast this spell.
     *
     * @param player the player's inventory to check for these items.
     * @return the items required to cast this spell, or <code>null</code> if
     * there are no items required.
     */
    public abstract Optional<Item[]> itemsRequired(Player player);

    /**
     * The equipment required to cast this spell.
     *
     * @param player the player's equipment to check for these items.
     * @return the equipment required to cast this spell, or <code>null</code>
     * if there is no equipment required.
     */
    public abstract Optional<Item[]> equipmentRequired(Player player);

    /**
     * The method invoked when the spell is cast.
     *
     * @param cast   the entity casting the spell.
     * @param castOn the target of the spell.
     */
    public abstract void startCast(Character cast, Character castOn);
}
