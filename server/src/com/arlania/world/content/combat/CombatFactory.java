package com.arlania.world.content.combat;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.CombatSkullEffect;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.movement.MovementQueue;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.BonusManager;
import com.arlania.world.content.ItemDegrading;
import com.arlania.world.content.ItemDegrading.DegradingItem;
import com.arlania.world.content.combat.calculations.NpcMaxHit;
import com.arlania.world.content.combat.calculations.PlayerMaxHit;
import com.arlania.world.content.combat.calculations.PlayerMeleeAccuracy;
import com.arlania.world.content.combat.effect.CombatPoisonEffect;
import com.arlania.world.content.combat.effect.CombatPoisonEffect.PoisonType;
import com.arlania.world.content.combat.equipment.FullSets;
import com.arlania.world.content.combat.magic.CombatAncientSpell;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.combat.weapon.FightStyle;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator.CoordinateState;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

/**
 * A static factory class containing all miscellaneous methods related to, and
 * used for combat.
 *
 * @author lare96
 * @author Scu11
 * @author Graham
 */
public final class CombatFactory {

    /**
     * The amount of time it takes for cached damage to timeout.
     */
    // Damage cached for currently 60 seconds will not be accounted for.
    public static final long DAMAGE_CACHE_TIMEOUT = 60000;

    /**
     * The amount of damage that will be drained by combat protection prayer.
     */
    // Currently at .20 meaning 20% of damage drained when using the right
    // protection prayer.
    public static final double PRAYER_DAMAGE_REDUCTION = 1;

    /**
     * The amount of damage that will be drained by combat protection prayer.
     */
    // Currently at .40 meaning 40% of damage drained when using the right
    // protection prayer.
    public static final double PRAYER_DAMAGE_REDUCTION_PLAYER = .4;

    /**
     * The rate at which accuracy will be reduced by combat protection prayer.
     */
    // Currently at .255 meaning 25.5% percent chance of canceling damage when
    // using the right protection prayer.
    public static final double PRAYER_ACCURACY_REDUCTION = 1;

    /**
     * The rate at which accuracy will be reduced by combat protection prayer.
     */
    // Currently at .4 meaning 40% percent chance of canceling damage when
    // using the right protection prayer.
    public static final double PRAYER_ACCURACY_REDUCTION_PLAYER = .4;

    /**
     * The amount of hitpoints the redemption prayer will heal.
     */
    // Currently at .25 meaning hitpoints will be healed by 25% of the remaining
    // prayer points when using redemption.
    public static final double REDEMPTION_PRAYER_HEAL = .25;

    /**
     * The maximum amount of damage inflicted by retribution.
     */
    // Damage between currently 0-15 will be inflicted if in the specified
    // radius when the retribution prayer effect is activated.
    public static final int MAXIMUM_RETRIBUTION_DAMAGE = 150;

    /**
     * The radius that retribution will hit players in.
     */
    // All players within currently 5 squares will get hit by the retribution
    // effect.
    public static final int RETRIBUTION_RADIUS = 5;


    public static boolean crystalBow(Player player) {
        Item item = player.getEquipment().get(Equipment.WEAPON_SLOT);
        if (item == null)
            return false;

        if (player.getEquipment().get(Equipment.WEAPON_SLOT).getDefinition().getName().toLowerCase().contains("bow") &&
                (player.getLocation() == Location.SHR))
            return true;


        return item.getDefinition().getName().toLowerCase().contains(
                "crystal bow");
    }

    public static boolean zaryteBow(Player player) {
        Item item = player.getEquipment().get(Equipment.WEAPON_SLOT);
        if (item == null)
            return false;
        return item.getDefinition().getName().toLowerCase().contains(
                "zaryte bow");
    }

    public static boolean darkBow(Player player) {
        Item item = player.getEquipment().get(Equipment.WEAPON_SLOT);
        if (item == null)
            return false;
        return item.getDefinition().getName().toLowerCase().contains(
                "dark bow");
    }

    /**
     * Attempts to poison the argued {@link Character} with the argued
     * {@link PoisonType}. This method will have no effect if the entity is
     * already poisoned.
     *
     * @param entity     the entity that will be poisoned, if not already.
     * @param poisonType the poison type that this entity is being inflicted with.
     */
    public static void poisonEntity(Character entity, PoisonType poisonType) {

        // We are already poisoned or the poison type is invalid, do nothing.
        if (entity.isPoisoned()) {
            return;
        }

        // If the entity is a player, we check for poison immunity. If they have
        // no immunity then we send them a message telling them that they are
        // poisoned.
        if (entity.isPlayer()) {
            Player player = (Player) entity;

            //equipment that counts as anti-poison
            if (player.getEquipment().contains(12282))
                return;
            if (player.getPoisonImmunity() > 0)
                return;
            player.getPacketSender().sendMessage("You have been poisoned!");
        }

        entity.setPoisonDamage(poisonType.getDamage());
        TaskManager.submit(new CombatPoisonEffect(entity));
    }

    /**
     * Attempts to poison the argued {@link Character} with the argued
     * {@link PoisonType}. This method will have no effect if the entity is
     * already poisoned.
     *
     * @param entity     the entity that will be poisoned, if not already.
     * @param poisonType the poison type that this entity is being inflicted with.
     */
    /*public static void poisonEntity(Character entity, PoisonType poisonType) {
        poisonEntity(entity, Optional.ofNullable(poisonType));
    }*/

    /**
     * Attempts to put the skull icon on the argued player, including the effect
     * where the player loses all item upon death. This method will have no
     * effect if the argued player is already skulled.
     *
     * @param player the player to attempt to skull to.
     */
    public static void skullPlayer(Player player) {

        // We are already skulled, return.
        if (player.getSkullTimer() > 0) {
            return;
        }

        // Otherwise skull the player as normal.

        if (player.getSkullTimer() < 1)
            player.getPacketSender().sendMessage("@red@You have been skulled!");

        player.setSkullTimer(300);
        player.setSkullIcon(1);
        TaskManager.submit(new CombatSkullEffect(player));
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    /**
     * Calculates the combat level difference for wilderness player vs. player
     * combat.
     *
     * @param combatLevel      the combat level of the first person.
     * @param otherCombatLevel the combat level of the other person.
     * @return the combat level difference.
     */
    public static int combatLevelDifference(int combatLevel,
                                            int otherCombatLevel) {
        if (combatLevel > otherCombatLevel) {
            return (combatLevel - otherCombatLevel);
        } else if (otherCombatLevel > combatLevel) {
            return (otherCombatLevel - combatLevel);
        } else {
            return 0;
        }
    }

    public static int getLevelDifference(Player player, boolean up) {
        int max = player.getLocation() == Location.WILDERNESS ? 126 : 138;
        int wildLevel = player.getWildernessLevel() + 5; //+ 5 to make wild more active
        int combatLevel = player.getSkillManager().getCombatLevel();
        int difference = up ? combatLevel + wildLevel : combatLevel - wildLevel;
        return difference < 3 ? 3 : difference > max && up ? max : difference;
    }

    /**
     * Generates a random {@link Hit} based on the argued entity's stats.
     *
     * @param entity the entity to generate the random hit for.
     * @param victim the victim being attacked.
     * @param type   the combat type being used.
     * @return the melee hit.
     */
    public static Hit getHit(Character entity, Character victim, CombatType type) {

        if (victim.isTeleporting())
            return new Hit(0, Hitmask.RED, CombatIcon.NONE);

        if (victim.getLocation() == Location.HOME_BANK)
            return new Hit(0, Hitmask.RED, CombatIcon.NONE);


        int minimumMeleeHit = 1;
        int maxMeleeHit = 1;
        int minimumRangedHit = 1;
        int maxRangedHit = 1;
        int minimumMagicHit = 1;
        int maxMagicHit = 1;

        if (entity.isPlayer()) {
            Player.activateSpecial(entity.asPlayer());
            maxMeleeHit = PlayerMaxHit.playerMaxHit(entity.asPlayer(), victim, CombatType.MELEE);
            maxRangedHit = PlayerMaxHit.playerMaxHit(entity.asPlayer(), victim, CombatType.RANGED);
            maxMagicHit = PlayerMaxHit.playerMaxHit(entity.asPlayer(), victim, CombatType.MAGIC);
        } else if (entity.isNpc()) {

            if (entity.getAsNpc().isSummoningNpc() && victim.isPlayer() && victim.getLocation() != Location.WILDERNESS) {
                entity.getCombatBuilder().reset(true);
                maxMeleeHit = 0;
                maxRangedHit = 0;
                maxMagicHit = 0;
            } else {
                maxMeleeHit = NpcMaxHit.npcMaxHit(entity.getAsNpc());
                maxRangedHit = NpcMaxHit.npcMaxHit(entity.getAsNpc());
                maxMagicHit = NpcMaxHit.npcMaxHit(entity.getAsNpc());

                if (entity.getAsNpc().savagery > 0) {
                    double factor = 1 + (.10 * entity.getAsNpc().savagery);
                    maxMeleeHit = (int) (factor * maxMeleeHit);
                    maxRangedHit = (int) (factor * maxRangedHit);
                    maxMagicHit = (int) (factor * maxMagicHit);
                }
            }
        }


        if (victim.isPlayer()) {
            if (victim.isTeleporting())
                return new Hit(0, Hitmask.RED, CombatIcon.NONE);
            if (victim.getLocation() == Location.RAIDS_LOBBY)
                return new Hit(0, Hitmask.RED, CombatIcon.NONE);
        }

        //handle Player
        if (entity.isPlayer()) {
            Player player = entity.asPlayer();

            if (victim.isNpc()) {

                double minimumHitBoost = 0;

                minimumHitBoost = (.01 * player.getEquipment().taintedEquipBonus());

                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.EFFICIENCY)) {
                    minimumHitBoost += .5;
                }

                if (player.Consistent == 1) {
                    minimumHitBoost += player.completedLogs * .005;
                }

                if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
                    if (player.Warlord && !player.isSpecialActivated()) { //TODO SEASONAL check this perk and minimum hits in general
                        minimumHitBoost += .25;
                    }
                }


                minimumMeleeHit = (int) (minimumHitBoost * maxMeleeHit);
                minimumRangedHit = (int) (minimumHitBoost * maxRangedHit);
                minimumMagicHit = (int) (minimumHitBoost * maxMagicHit);

                if (player.getEquipment().contains(21082)) {
                    minimumMeleeHit *= (int) 1.10;
                }


                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.BERSERKER)) {
                    double boost = 1.5;
                    maxMeleeHit = (int) (boost * maxMeleeHit);
                    maxRangedHit = (int) (boost * maxRangedHit);
                    maxMagicHit = (int) (boost * maxMagicHit);
                }
                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.WARRIOR)) {
                    double boost = 2;
                    maxMeleeHit = (int) (boost * maxMeleeHit);
                }
                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.MARKSMAN)) {
                    double boost = 1.5;
                    maxRangedHit = (int) (boost * maxRangedHit);
                }
                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.SORCERER)) {
                    double boost = 1.5;
                    maxMagicHit = (int) (boost * maxMagicHit);
                }
            }

            if (Math.random() > .85 && !victim.isPlayer()) {

                int randHit = RandomUtility.inclusiveRandom(100, 200);

                //Dragonfire
                if (player.getEquipment().contains(15441) || player.getEquipment().contains(15701) || player.getEquipment().contains(14004)) {
                    victim.dealDamage(new Hit(randHit * 2, Hitmask.DARK_RED, CombatIcon.NONE));
                    new Projectile(player, victim, 393, 44, 3, 43, 43, 0).sendProjectile();
                }
                //Frozen
                else if (player.getEquipment().contains(15442) || player.getEquipment().contains(15702) || player.getEquipment().contains(14005)) {
                    victim.dealDamage(new Hit(randHit, Hitmask.DARK_RED, CombatIcon.NONE));
                    victim.performGraphic(new Graphic(369, 0));
                    victim.getMovementQueue().freeze(5);
                }
                //Vampiric
                else if (player.getEquipment().contains(15443) || player.getEquipment().contains(15703) || player.getEquipment().contains(14006)) {
                    victim.dealDamage(new Hit(randHit, Hitmask.DARK_RED, CombatIcon.NONE));
                    player.performGraphic(new Graphic(265, GraphicHeight.MIDDLE));
                    player.heal(randHit);
                }
                //Venomous
                else if (player.getEquipment().contains(15444) || player.getEquipment().contains(15704) || player.getEquipment().contains(14007)) {
                    victim.performGraphic(new Graphic(267, GraphicHeight.MIDDLE));
                    if (victim.isNpc())
                        victim.getAsNpc().setVenomDamage(true);
                }


            }

            player.meleeMaxHit = maxMeleeHit;
            player.rangedMaxHit = maxRangedHit;
            player.magicMaxHit = maxMagicHit;
        }

        switch (type) {
            case MELEE: //TODO SEASONAL test max hit for Gladiator on full HP npcs. Also check Executioner for npcs with HP below player's max hit
                if (entity.isPlayer() && victim.isNpc() && entity.asPlayer().getGameMode() == GameMode.SEASONAL_IRONMAN && entity.asPlayer().Executioner && maxMeleeHit >= victim.getAsNpc().getConstitution()) {
                    return new Hit(maxMeleeHit, Hitmask.CRITICAL, CombatIcon.MELEE);
                } else if (entity.isPlayer() && victim.isNpc() && entity.asPlayer().getGameMode() == GameMode.SEASONAL_IRONMAN && entity.asPlayer().Gladiator && victim.getAsNpc().getConstitution() >= victim.getAsNpc().getDefinition().getHitpoints()) {
                    return new Hit(maxMeleeHit, Hitmask.CRITICAL, CombatIcon.MELEE);
                } else if (entity.isPlayer() && victim.isNpc() && entity.asPlayer().getGameMode() == GameMode.SEASONAL_IRONMAN && entity.asPlayer().Gladiator && victim.getAsNpc().getConstitution() >= victim.getAsNpc().getDefaultConstitution()) {
                    return new Hit(maxMeleeHit, Hitmask.CRITICAL, CombatIcon.MELEE);
                } else if ((GlobalEventHandler.effectActive(GlobalEvent.Effect.MAX_HIT) && !victim.isPlayer()) || (entity.isPlayer() && entity.asPlayer().maxHitEvent && entity.asPlayer().personalEvent && !victim.isPlayer()))
                    return new Hit(maxMeleeHit, Hitmask.CRITICAL, CombatIcon.MELEE);
                else if (entity.isNpc() && entity.getAsNpc().optimum > 0 && entity.getAsNpc().optimum > RandomUtility.inclusiveRandom(100))
                    return new Hit(maxMeleeHit, Hitmask.CRITICAL, CombatIcon.MELEE);
                else if (entity.isPlayer() && entity.asPlayer().getEquipment().taintedEquipBonus() >= RandomUtility.inclusiveRandom(100))
                    return new Hit(maxMeleeHit, Hitmask.CRITICAL, CombatIcon.MELEE);
                return new Hit(RandomUtility.inclusiveRandom(minimumMeleeHit, maxMeleeHit), Hitmask.RED, CombatIcon.MELEE);

            case RANGED:
                if (entity.isPlayer() && victim.isNpc() && entity.asPlayer().getGameMode() == GameMode.SEASONAL_IRONMAN && entity.asPlayer().Executioner && maxMeleeHit >= victim.getAsNpc().getConstitution()) {
                    return new Hit(maxRangedHit, Hitmask.CRITICAL, CombatIcon.RANGED);
                } else if (entity.isPlayer() && victim.isNpc() && entity.asPlayer().getGameMode() == GameMode.SEASONAL_IRONMAN && entity.asPlayer().Gladiator && victim.getAsNpc().getConstitution() >= victim.getAsNpc().getDefinition().getHitpoints()) {
                    return new Hit(maxRangedHit, Hitmask.CRITICAL, CombatIcon.RANGED);
                } else if ((GlobalEventHandler.effectActive(GlobalEvent.Effect.MAX_HIT) && !victim.isPlayer()) || (entity.isPlayer() && entity.asPlayer().maxHitEvent && entity.asPlayer().personalEvent && !victim.isPlayer()))
                    return new Hit(maxRangedHit, Hitmask.CRITICAL, CombatIcon.RANGED);
                else if (entity.isNpc() && entity.getAsNpc().optimum > 0 && entity.getAsNpc().optimum > RandomUtility.inclusiveRandom(100))
                    return new Hit(maxRangedHit, Hitmask.CRITICAL, CombatIcon.RANGED);
                else if (entity.isPlayer() && entity.asPlayer().getEquipment().taintedEquipBonus() >= RandomUtility.inclusiveRandom(100))
                    return new Hit(maxRangedHit, Hitmask.CRITICAL, CombatIcon.RANGED);
                return new Hit(RandomUtility.inclusiveRandom(minimumRangedHit, maxRangedHit), Hitmask.RED, CombatIcon.RANGED);

            case MAGIC:
                if (entity.isPlayer() && victim.isNpc() && entity.asPlayer().getGameMode() == GameMode.SEASONAL_IRONMAN && entity.asPlayer().Executioner && maxMeleeHit >= victim.getAsNpc().getConstitution()) {
                    return new Hit(maxMagicHit, Hitmask.CRITICAL, CombatIcon.MAGIC);
                } else if (entity.isPlayer() && victim.isNpc() && entity.asPlayer().getGameMode() == GameMode.SEASONAL_IRONMAN && entity.asPlayer().Gladiator && victim.getAsNpc().getConstitution() >= victim.getAsNpc().getDefinition().getHitpoints()) {
                    return new Hit(maxMagicHit, Hitmask.CRITICAL, CombatIcon.MAGIC);
                } else if ((GlobalEventHandler.effectActive(GlobalEvent.Effect.MAX_HIT) && !victim.isPlayer()) || (entity.isPlayer() && entity.asPlayer().maxHitEvent && entity.asPlayer().personalEvent && !victim.isPlayer()))
                    return new Hit(maxMagicHit, Hitmask.CRITICAL, CombatIcon.MAGIC);
                else if (entity.isNpc() && entity.getAsNpc().optimum > 0 && entity.getAsNpc().optimum > RandomUtility.inclusiveRandom(100))
                    return new Hit(maxMagicHit, Hitmask.CRITICAL, CombatIcon.MAGIC);
                else if (entity.isPlayer() && entity.asPlayer().getEquipment().taintedEquipBonus() >= RandomUtility.inclusiveRandom(100))
                    return new Hit(maxMagicHit, Hitmask.CRITICAL, CombatIcon.MAGIC);
                return new Hit(RandomUtility.inclusiveRandom(minimumMagicHit, maxMagicHit), Hitmask.RED, CombatIcon.MAGIC);

            case DRAGON_FIRE:
                return new Hit(RandomUtility.inclusiveRandom(0, CombatFactory.calculateMaxDragonFireHit(entity, victim)), Hitmask.RED, CombatIcon.MAGIC);
            default:
                throw new IllegalArgumentException("Invalid combat type: " + type);
        }
    }

    /**
     * A flag that determines if the entity's attack will be successful based on
     * the argued attacker's and victim's stats.
     *
     * @param attacker the attacker who's hit is being calculated for accuracy.
     * @param victim   the victim who's awaiting to either be hit or dealt no damage.
     * @param type     the type of combat being used to deal the hit.
     * @return true if the hit was successful, or in other words accurate.
     */
    @SuppressWarnings("incomplete-switch")
    public static boolean rollAccuracy(Character attacker, Character victim, CombatType type) {

        if (attacker.isPlayer() && victim.isPlayer()) {
            Player p1 = (Player) attacker;
            Player p2 = (Player) victim;
            switch (type) {
                case MAGIC:
                    int mageAttk = DesolaceFormulas.getMagicAttack(p1);
                    return RandomUtility.inclusiveRandom(DesolaceFormulas.getMagicDefence(p2)) < RandomUtility.inclusiveRandom((mageAttk / 2)) + RandomUtility.inclusiveRandom((int) (mageAttk / 2.1));
                case MELEE:
                    int def = 1 + DesolaceFormulas.getMeleeDefence(p2);
                    return RandomUtility.inclusiveRandom(def) < RandomUtility.inclusiveRandom(1 + PlayerMeleeAccuracy.playerMeleeAccuracy(p1, victim)) + (def / 4.5);
                case RANGED:
                    return RandomUtility.inclusiveRandom(10 + DesolaceFormulas.getRangedDefence(p2)) < RandomUtility.inclusiveRandom(15 + DesolaceFormulas.getRangedAttack(p1));
            }
        } else if (attacker.isPlayer() && victim.isNpc() && type != CombatType.MAGIC) {
            Player p1 = (Player) attacker;
            NPC n = (NPC) victim;

            if (p1.getGameMode() == GameMode.SEASONAL_IRONMAN && p1.Warlord && p1.isSpecialActivated()) {
                return true;
            }

            switch (type) {
                case MELEE:
                    int def = 1 + n.getDefinition().getDefenceMelee();
                    return RandomUtility.inclusiveRandom(def) < RandomUtility.inclusiveRandom(5 + PlayerMeleeAccuracy.playerMeleeAccuracy(p1, victim)) + (def / 4);
                case RANGED:
                    return RandomUtility.inclusiveRandom(5 + n.getDefinition().getDefenceRange()) < RandomUtility.inclusiveRandom(5 + DesolaceFormulas.getRangedAttack(p1));
            }
        }

        boolean veracEffect = false;

        if (type == CombatType.MELEE) {
            if (FullSets.fullVeracs(attacker)) {
                veracEffect = true;
            }
        }

        if (type == CombatType.DRAGON_FIRE)
            type = CombatType.MAGIC;

        double prayerMod = 1;
        double equipmentBonus = 1;
        double specialBonus = 1;
        int styleBonus = 0;
        int bonusType = -1;
        if (attacker.isPlayer()) {
            Player player = (Player) attacker;

            equipmentBonus = type == CombatType.MAGIC ? player.getBonusManager().getAttackBonus()[BonusManager.ATTACK_MAGIC]
                    : player.getBonusManager().getAttackBonus()[player.getFightType().getBonusType()];
            bonusType = player.getFightType().getCorrespondingBonus();

            if (type == CombatType.MELEE) {
                if (PrayerHandler.isActivated(player,
                        PrayerHandler.CLARITY_OF_THOUGHT)) {
                    prayerMod = 1.05;
                } else if (PrayerHandler.isActivated(player,
                        PrayerHandler.IMPROVED_REFLEXES)) {
                    prayerMod = 1.10;
                } else if (PrayerHandler.isActivated(player,
                        PrayerHandler.INCREDIBLE_REFLEXES)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player,
                        PrayerHandler.CHIVALRY)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player,
                        PrayerHandler.PIETY)) {
                    prayerMod = 1.20;
                } else if (PrayerHandler.isActivated(player,
                        PrayerHandler.RIGOUR)) {
                    prayerMod = 1.20;
                } else if (PrayerHandler.isActivated(player,
                        PrayerHandler.AUGURY)) {
                    prayerMod = 1.20;
                } else if (CurseHandler.isActivated(player, CurseHandler.LEECH_ATTACK)) {
                    prayerMod = 1.05 + +(player.getLeechedBonuses()[0] * 0.01);
                } else if (CurseHandler.isActivated(player, CurseHandler.TURMOIL)) {
                    prayerMod = 1.15 + +(player.getLeechedBonuses()[2] * 0.01);
                }
            } else if (type == CombatType.RANGED) {
                if (PrayerHandler.isActivated(player, PrayerHandler.SHARP_EYE)) {
                    prayerMod = 1.05;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.HAWK_EYE)) {
                    prayerMod = 1.10;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.EAGLE_EYE)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                    prayerMod = 1.22;
                } else if (CurseHandler.isActivated(player, CurseHandler.LEECH_RANGED)) {
                    prayerMod = 1.05 + +(player.getLeechedBonuses()[4] * 0.01);
                }
            } else if (type == CombatType.MAGIC) {
                if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_WILL)) {
                    prayerMod = 1.05;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_LORE)) {
                    prayerMod = 1.10;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_MIGHT)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                    prayerMod = 1.22;
                } else if (CurseHandler.isActivated(player, CurseHandler.LEECH_MAGIC)) {
                    prayerMod = 1.05 + +(player.getLeechedBonuses()[6] * 0.01);
                }
            }

            if (player.getFightType().getStyle() == FightStyle.ACCURATE) {
                styleBonus = 3;
            } else if (player.getFightType().getStyle() == FightStyle.CONTROLLED) {
                styleBonus = 1;
            }

            if (player.isSpecialActivated()) {
                specialBonus = player.getCombatSpecial().getAccuracyBonus();
            }
        }

        double attackCalc = Math.floor(equipmentBonus + attacker.getBaseAttack(type)) + 8;

        attackCalc *= prayerMod;
        attackCalc += styleBonus;

        if (equipmentBonus < -67) {
            attackCalc = RandomUtility.exclusiveRandom(8) == 0 ? attackCalc : 0;
        }
        attackCalc *= specialBonus;


        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCURACY) && !victim.isPlayer())
            return true;

        equipmentBonus = 1;
        prayerMod = 1;
        styleBonus = 0;
        if (victim.isPlayer()) {
            Player player = (Player) victim;

            if (bonusType == -1) {
                equipmentBonus = type == CombatType.MAGIC ? player.getBonusManager().getDefenceBonus()[BonusManager.DEFENCE_MAGIC]
                        : player.getSkillManager().getCurrentLevel(Skill.DEFENCE);
            } else {
                equipmentBonus = type == CombatType.MAGIC ? player.getBonusManager().getDefenceBonus()[BonusManager.DEFENCE_MAGIC]
                        : player.getBonusManager().getDefenceBonus()[bonusType];
            }

            if (PrayerHandler.isActivated(player, PrayerHandler.THICK_SKIN)) {
                prayerMod = 1.05;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.ROCK_SKIN)) {
                prayerMod = 1.10;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.STEEL_SKIN)) {
                prayerMod = 1.15;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.CHIVALRY)) {
                prayerMod = 1.20;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.PIETY)) {
                prayerMod = 1.25;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                prayerMod = 1.25;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                prayerMod = 1.25;
            } else if (CurseHandler.isActivated(player, CurseHandler.LEECH_DEFENCE)) {
                prayerMod = 1.05 + +(player.getLeechedBonuses()[1] * 0.01);
            } else if (CurseHandler.isActivated(player,
                    CurseHandler.TURMOIL)) {
                prayerMod = 1.15 + +(player.getLeechedBonuses()[1] * 0.01);
            }

            if (player.getFightType().getStyle() == FightStyle.DEFENSIVE) {
                styleBonus = 3;
            } else if (player.getFightType().getStyle() == FightStyle.CONTROLLED) {
                styleBonus = 1;
            }
        }

        double defenceCalc = Math.floor(equipmentBonus + victim.getBaseDefence(type)) + 8;
        defenceCalc *= prayerMod;
        defenceCalc += styleBonus;

        if (equipmentBonus < -67) {
            defenceCalc = RandomUtility.exclusiveRandom(8) == 0 ? defenceCalc : 0;
        }
        if (veracEffect) {
            defenceCalc = 0;
        }
        double A = Math.floor(attackCalc);
        double D = Math.floor(defenceCalc);
        double hitSucceed = A < D ? (A - 1.0) / (2.0 * D)
                : 1.0 - (D + 1.0) / (2.0 * A);
        hitSucceed = hitSucceed >= 1.0 ? 0.99 : hitSucceed <= 0.0 ? 0.01
                : hitSucceed;
        return hitSucceed >= RandomUtility.RANDOM.nextDouble();
    }


    public static int calculateMaxDragonFireHit(Character e, Character v) {
        int baseMax = 250;

        if (e.isNpc() && v.isPlayer()) {
            Player victim = (Player) v;
            NPC npc = (NPC) e;
            baseMax = (int) (npc.getDefinition().getMaxHit() * 2.5);

        }

        if (baseMax >= 650)
            baseMax = 650;

        if (v.isPlayer()) {
            Player victim = (Player) v;

            int immunity = 0;

            immunity += victim.getEquipment().dragonfireEquipment(victim);

            if (((Player) v).getDragonfireImmunity() > 0) {
                immunity += 70;
            }
            if (PrayerHandler.isActivated(((Player) v), PrayerHandler.PROTECT_FROM_MAGIC) || CurseHandler.isActivated(((Player) v), CurseHandler.DEFLECT_MAGIC)) {
                immunity += 30;
            }

            immunity += victim.getEquipment().dragonfireEquipment(victim);

            if (immunity >= 100)
                return 0;

            baseMax -= baseMax * immunity / 100;

        }

        return baseMax;
    }

    /**
     * A series of checks performed before the entity attacks the victim.
     *
     * @param builder the builder to perform the checks with.
     * @return true if the entity passed the checks, false if they did not.
     */
    public static boolean checkHook(Character entity, Character victim) {

        // Check if we need to reset the combat session.
        if (!victim.isRegistered() || !entity.isRegistered() || entity.getConstitution() <= 0 || victim.getConstitution() <= 0) {
            entity.getCombatBuilder().reset(true);
            return false;
        }

        // Here we check if the victim has teleported away.
        if (victim.isPlayer()) {
            if (victim.isTeleporting() || !Location.ignoreFollowDistance(entity) && !Locations.goodDistance(victim.getPosition(), entity.getPosition(), 40) || ((Player) victim).isPlayerLocked()) {
                entity.getCombatBuilder().cooldown = 10;
                entity.getMovementQueue().setFollowCharacter(null);
                return false;
            }
            else {
                return true;
            }
        }

        if (victim.isNpc() && entity.isPlayer()) {
            NPC npc = (NPC) victim;
            if (npc.getSpawnedFor() != null && npc.getSpawnedFor().getIndex() != entity.getIndex()) {
                ((Player) entity).getPacketSender().sendMessage("That's not your enemy to fight.");
                entity.getCombatBuilder().reset(true);
                return false;
            }

            if (npc.isSummoningNpc()) {
                Player player = ((Player) entity);
                if (player.getLocation() != Location.WILDERNESS) {
                    player.getPacketSender().sendMessage("You can only attack familiars in the wilderness.");
                    player.getCombatBuilder().reset(true);
                    return false;
                } else if (npc.getLocation() != Location.WILDERNESS) {
                    player.getPacketSender().sendMessage("That familiar is not in the wilderness.");
                    player.getCombatBuilder().reset(true);
                    return false;
                }
                /** DEALING DMG TO THEIR OWN FAMILIAR **/
                if (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null && player.getSummoning().getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
                    return false;
                }
            }
			/*if(((Player)entity).getLocation() != Location.CHAOS_TUNNELS)
			{
			if(Nex.nexMob(npc.getId()) || npc.getId() == 6260 || npc.getId() == 6261 || npc.getId() == 6263 || npc.getId() == 6265 || npc.getId() == 6222 || npc.getId() == 6223 || npc.getId() == 6225 || npc.getId() == 6227 || npc.getId() == 6203 || npc.getId() == 6208 || npc.getId() == 6204 || npc.getId() == 6206 || npc.getId() == 6247 || npc.getId() == 6248 || npc.getId() == 6250 || npc.getId() == 6252) {
				if(!((Player)entity).getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom()) {
					((Player)entity).getPacketSender().sendMessage("You must enter the room before being able to attack.");
					entity.getCombatBuilder().reset(true);
					return false;
				}
			}
			}*/
            if (npc.getId() == 6222) { //Kree'arra
                if (entity.getCombatBuilder().getStrategy().getCombatType() == CombatType.MELEE) {
                    ((Player) entity).getPacketSender().sendMessage("Kree'arra is resistant to melee attacks.");
                    entity.getCombatBuilder().reset(true);
                    return false;
                }
            }
            if (npc.getDefinition().getSlayerLevel() > ((Player) entity).getSkillManager().getCurrentLevel(Skill.SLAYER)) {
                ((Player) entity).getPacketSender().sendMessage("You need a Slayer level of at least " + npc.getDefinition().getSlayerLevel() + " to attack this creature.");
                entity.getCombatBuilder().reset(true);
                return false;
            }
            if (npc.getId() == 6715 || npc.getId() == 6716 || npc.getId() == 6701 || npc.getId() == 6725 || npc.getId() == 6691) {
                if (entity.getLocation() != Location.WILDERNESS) {
                    ((Player) entity).getPacketSender().sendMessage("You cannot reach that.");
                    entity.getCombatBuilder().reset(true);
                    return false;
                }
            }
            if (npc.getId() == 4291 && entity.getPosition().getZ() == 2 && !((Player) entity).getMinigameAttributes().getWarriorsGuildAttributes().enteredTokenRoom()) {
                ((Player) entity).getPacketSender().sendMessage("You cannot reach that.");
                entity.getCombatBuilder().reset(true);
                return false;
            }
        }

        // Here we check if we are already in combat with another entity.
        if (entity.getCombatBuilder().getLastAttacker() != null && !Location.inMulti(entity) && entity.getCombatBuilder().isBeingAttacked() && !victim.equals(entity.getCombatBuilder().getLastAttacker())) {
            if (entity.isPlayer())
                ((Player) entity).getPacketSender().sendMessage("You are already under attack!");
            entity.getCombatBuilder().reset(true);
            return false;
        }

        // Here we check if the entity we are attacking is already in
        // combat.
        if (!(entity.isNpc() && ((NPC) entity).isSummoningNpc())) {
            boolean allowAttack = false;
            if (victim.getCombatBuilder().getLastAttacker() != null && !Location.inMulti(entity) && victim.getCombatBuilder().isBeingAttacked() && !victim.getCombatBuilder().getLastAttacker().equals(entity)) {

                if (victim.getCombatBuilder().getLastAttacker().isNpc()) {
                    NPC npc = (NPC) victim.getCombatBuilder().getLastAttacker();
                    if (npc.isSummoningNpc()) {
                        if (entity.isPlayer()) {
                            Player player = (Player) entity;
                            if (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null && player.getSummoning().getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
                                allowAttack = true;
                            }
                        }
                    }
                }

                if (!allowAttack) {
                    if (entity.isPlayer())
                        ((Player) entity).getPacketSender().sendMessage(
                                "They are already under attack!");
                    entity.getCombatBuilder().reset(true);
                    return false;
                }
            }
        }

        // Check if the victim is still in the wilderness, and check if the
        if (entity.isPlayer()) {
            if (victim.isPlayer()) {
                if (!properLocation((Player) entity, (Player) victim)) {
                    entity.getCombatBuilder().reset(true);
                    entity.setPositionToFace(victim.getPosition());
                    return false;
                }
            }
            if (victim.isNpc() && victim.getLocation() == Location.OLM) {
                //Olm head
                if (entity.asPlayer().getRaidsParty() == null)
                    return false;

                if (victim.getAsNpc().getId() == 21554 && entity.asPlayer().getRaidsParty().getOwner() != null) {
                    if (!entity.asPlayer().getRaidsParty().getOwner().getRaidsParty().isLeftHandDead() ||
                            !entity.asPlayer().getRaidsParty().getOwner().getRaidsParty().isRightHandDead()) {
                        entity.getCombatBuilder().reset(true);
                        entity.setPositionToFace(victim.getPosition());
                        entity.asPlayer().sendMessage("Please kill both hands before attacking Olm!");
                        return false;
                    }
                } else if (victim.getAsNpc().getId() == 21554) {
                    entity.asPlayer().getPacketSender().sendMessage("You can't attack Olm if you're not in a party.");
                    return false;
                }

            }
            if (((Player) entity).isCrossingObstacle()) {
                entity.getCombatBuilder().reset(true);
                return false;
            }
        }


        // Check if the npc needs to retreat.
        if (entity.isNpc()) {
            NPC n = (NPC) entity;
            if (!Location.ignoreFollowDistance(n) && !n.isSummoningNpc()) { //Stops combat for npcs if too far away
                if (n.getPosition().isWithinDistance(victim.getPosition(), 1)) {
                    return true;
                }
                if (!n.getPosition().isWithinDistance(n.getDefaultPosition(), 10 + n.getMovementCoordinator().getCoordinator().getRadius())) {
                    n.getMovementQueue().reset();
                    n.getMovementCoordinator().setCoordinateState(CoordinateState.AWAY);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the entity is close enough to attack.
     *
     * @param builder the builder used to perform the check.
     * @return true if the entity is close enough to attack, false otherwise.
     */
    public static boolean checkAttackDistance(CombatBuilder builder) {

        if (builder.getVictim().getLocation() == Location.CHAOS_RAIDS)
            return true;

        return checkAttackDistance(builder.getCharacter(), builder.getVictim());
    }

    public static boolean checkAttackDistance(Character a, Character b) {

        Position attacker = a.getPosition();
        Position victim = b.getPosition();

        if (a.isNpc() && ((NPC) a).isSummoningNpc()) {
            return Locations.goodDistance(attacker, victim, a.getSize());
        }

        if (a.getCombatBuilder().getStrategy() == null)
            a.getCombatBuilder().determineStrategy();
        CombatStrategy strategy = a.getCombatBuilder().getStrategy();
        int distance = strategy.attackDistance(a);
        if (a.isPlayer() && strategy.getCombatType() != CombatType.MELEE) {
            if (b.getSize() >= 2)
                distance += b.getSize() - 1;
        }

        MovementQueue movement = a.getMovementQueue();
        MovementQueue otherMovement = b.getMovementQueue();

        // We're moving so increase the distance.
        if (!movement.isMovementDone() && !otherMovement.isMovementDone() && !movement.isLockMovement() && !a.isFrozen()) {
            distance += 1;

            // We're running so increase the distance even more.
            // XXX: Might have to change this back to 1 or even remove it, not
            // sure what it's like on actual runescape. Are you allowed to
            // attack when the entity is trying to run away from you?
            if (movement.isRunToggled()) {
                distance += 2;
            }
        }

        /*
         *  Clipping checks and diagonal blocking by gabbe
         */

        boolean sameSpot = attacker.equals(victim) && !a.getMovementQueue().isMoving() && !b.getMovementQueue().isMoving();
        boolean goodDistance = !sameSpot && Locations.goodDistance(attacker.getX(), attacker.getY(), victim.getX(), victim.getY(), distance);
        boolean projectilePathBlocked = false;
        if (a.isPlayer() && (strategy.getCombatType() == CombatType.RANGED || strategy.getCombatType() == CombatType.MAGIC && ((Player) a).getCastSpell() != null && !(((Player) a).getCastSpell() instanceof CombatAncientSpell)) || a.isNpc() && strategy.getCombatType() == CombatType.MELEE) {
            if (!RegionClipping.canProjectileAttack(b, a))
                projectilePathBlocked = true;
        }
        if (b.isNpc() && ((b.getAsNpc().getId() == 21555 || b.getAsNpc().getId() == 21554 || b.getAsNpc().getId() == 21553))) {
            distance = 7;
            projectilePathBlocked = false;
        }
        if (!projectilePathBlocked && goodDistance) {
            /*if (strategy.getCombatType() == CombatType.MELEE && RegionClipping.isInDiagonalBlock(b, a)) {
                PathFinder.findPath(a, victim.getX(), victim.getY() + 1, true, 1, 1);
                return false;
            } else*/
            if (strategy.getCombatType() == CombatType.MELEE && goodDistance) {
                return true;
            } else
                a.getMovementQueue().reset();
            return true;
        } else if (projectilePathBlocked || !goodDistance) {
            a.getMovementQueue().setFollowCharacter(b);
            return false;
        }
        // Check if we're within the required distance.
        return attacker.isWithinDistance(victim, distance);
    }

    /**
     *
     */

    static void applyDamageAbsorb(CombatContainer container, CombatBuilder builder) {

        // If we aren't checking the accuracy, then don't bother doing any of
        // this.
        if (!container.isCheckAccuracy() || builder.getVictim() == null) {
            return;
        }

        if (builder.getCharacter().isNpc()) {

        }

        if (builder.getVictim().isNpc()) {
            if (builder.getVictim().getAsNpc().reduction > 0) {
                container.allHits(context -> {
                    if (context.getHit().getDamage() > 0) {
                        context.getHit().setDamage(context.getHit().getDamage() - (context.getHit().getDamage() * builder.getVictim().getAsNpc().reduction / 50));
                    }
                });
            }
        }
    }

    /**
     * Applies combat prayer effects to the calculated hits.
     *
     * @param container the combat container that holds the hits.
     * @param builder   the builder to apply prayer effects to.
     */
    static void applyPrayerProtection(CombatContainer container, CombatBuilder builder) {

        // If we aren't checking the accuracy, then don't bother doing any of
        // this.
        if (!container.isCheckAccuracy() || builder.getVictim() == null) {
            return;
        }


        // The attacker is an npc, and the victim is a player so we completely
        // cancel the hits if the right prayer is active.

        if (builder.getVictim().isPlayer()) {
            Player victim = (Player) builder.getVictim();


            if (victim.getEquipment().damageAbsorb() > 0) {
                container.allHits(context -> {
                    if (context.getHit().getDamage() > 0) {
                        //context.getHit().incrementAbsorbedDamage((int)(context.getHit().getDamage() - (context.getHit().getDamage() * victim.getEquipment().damageAbsorb() / 100)));

                        //victim.getPacketSender().sendMessage("Hit: " + context.getHit().getDamage());
                        //victim.getPacketSender().sendMessage("Reduced Hit: " + (context.getHit().getDamage() - (context.getHit().getDamage() * victim.getEquipment().damageAbsorb() / 100)));

                        context.getHit().setDamage(context.getHit().getDamage() - (context.getHit().getDamage() * victim.getEquipment().damageAbsorb() / 100));

                        //reset Harmonised staff
                        victim.Harmonised = false;

                        if (victim.getEquipment().contains(13740)) {
                            if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) > 0) {
                                int prayerLost = (int) (context.getHit().getDamage() * 0.09);
                                if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) >= prayerLost) {
                                    victim.getSkillManager().setCurrentLevel(Skill.PRAYER, victim.getSkillManager().getCurrentLevel(Skill.PRAYER) - prayerLost);
                                }
                            }
                        }
                    }
                });
            }
            if (builder.getCharacter().isNpc()) {
                NPC attacker = (NPC) builder.getCharacter();
                // Except for verac of course :)

                if (attacker.piercing > 0) {
                    if (attacker.piercing > RandomUtility.inclusiveRandom(20))
                        return;
                }

                if (attacker.getId() == 2030) {
                    return;
                }
                if (attacker.getId() == 6260) {
                    return;
                }
                if (attacker.getId() == 6261) {
                    return;
                }
                if (attacker.getId() == 6203) {
                    return;
                }
                if (attacker.getId() == 6247) {
                    return;
                }
                if (attacker.getId() == 6222) {
                    return;
                }
                if (attacker.getId() == 1615) {
                    return;
                }
                if (attacker.getId() == 7286) {
                    return;
                }
                if (attacker.getId() == 8349) {
                    return;
                }
                if (attacker.getId() == 22382) {
                    return;
                }
                if (attacker.getId() == 22383) {
                    return;
                }
                if (attacker.getId() == 22385) {
                    return;
                }
                // It's not verac so we cancel all of the hits.
                if (PrayerHandler.isActivated(victim, PrayerHandler.getProtectingPrayer(container.getCombatType())) || CurseHandler.isActivated(victim, CurseHandler.getProtectingPrayer(container.getCombatType()))) {
                    container.allHits(context -> {
                        int hit = context.getHit().getDamage();
                        if (attacker.getId() == 2745) { //Jad
                            context.setAccurate(false);
                            context.getHit().incrementAbsorbedDamage(hit);
                        } else {
                            double reduceRatio = attacker.getId() == 1158 || attacker.getId() == 1160 ? 0.4 : 0.8;
                            double mod = Math.abs(1 - reduceRatio);
                            context.getHit().incrementAbsorbedDamage((int) (hit - (hit * mod)));
                            mod = Math.round(RandomUtility.RANDOM.nextDouble() * 100.0) / 100.0;
                            if (mod <= CombatFactory.PRAYER_ACCURACY_REDUCTION) {
                                context.setAccurate(false);
                            }
                        }
                    });
                }
            } else if (builder.getCharacter().isPlayer()) {
                Player attacker = (Player) builder.getCharacter();
                // If wearing veracs, the attacker will hit through prayer
                // protection.
                if (FullSets.fullVeracs(attacker)) {
                    return;
                }


                // They aren't wearing veracs so lets reduce the accuracy and hits.
                if (PrayerHandler.isActivated(victim, PrayerHandler.getProtectingPrayer(container.getCombatType())) || CurseHandler.isActivated(victim, CurseHandler.getProtectingPrayer(container.getCombatType()))) {
                    container.allHits(context -> {
                        // First reduce the damage.
                        int hit = context.getHit().getDamage();
                        double mod = Math.abs(1 - 0.5);
                        context.getHit().incrementAbsorbedDamage((int) (hit - (hit * mod)));
                        // Then reduce the accuracy.
                        mod = Math.round(RandomUtility.RANDOM.nextDouble() * 100.0) / 100.0;
                        if (mod <= CombatFactory.PRAYER_ACCURACY_REDUCTION_PLAYER) {
                            context.setAccurate(false);
                        }
                    });
                }
            }
        } else if (builder.getVictim().isNpc() && builder.getCharacter().isPlayer()) {
            Player attacker = (Player) builder.getCharacter();
            NPC npc = (NPC) builder.getVictim();
            if (npc.getId() == 8349 && container.getCombatType() == CombatType.MELEE) {
                container.allHits(context -> {
                    int hit = context.getHit().getDamage();
                    double mod = Math.abs(1 - 0.5);
                    context.getHit().incrementAbsorbedDamage((int) (hit - (hit * mod)));
                    mod = Math.round(RandomUtility.RANDOM.nextDouble() * 100.0) / 100.0;
                    if (mod <= CombatFactory.PRAYER_ACCURACY_REDUCTION) {
                        context.setAccurate(false);
                    }
                });
            } else if (npc.getId() == 13347) {
                container.allHits(context -> {
                    int hit = context.getHit().getDamage();
                    double mod = Math.abs(1 - 0.4);
                    context.getHit().incrementAbsorbedDamage((int) (hit - (hit * mod)));
                    mod = Math.round(RandomUtility.RANDOM.nextDouble() * 100.0) / 100.0;
                    if (mod <= CombatFactory.PRAYER_ACCURACY_REDUCTION) {
                        context.setAccurate(false);
                    }
                });
            }
        }
    }

    /**
     * Gives experience for the total amount of damage dealt in a combat hit.
     *
     * @param builder   the attacker's combat builder.
     * @param container the attacker's combat container.
     * @param damage    the total amount of damage dealt.
     */
    static void giveExperience(CombatBuilder builder,
                               CombatContainer container, int damage) {

        // This attack does not give any experience.
        if (container.getExperience().length == 0 && container.getCombatType() != CombatType.MAGIC) {
            return;
        }

        // Otherwise we give experience as normal.
        if (builder.getCharacter().isPlayer()) {
            Player player = (Player) builder.getCharacter();

            if (container.getCombatType() == CombatType.MAGIC) {
                if (player.getCurrentlyCasting() != null)
                    player.getSkillManager().addExperience(Skill.MAGIC, (int) (((damage * .90) * Skill.MAGIC.getExperienceMultiplier()) / container.getExperience().length) + builder.getCharacter().getCurrentlyCasting().baseExperience());
            } else {
                for (int i : container.getExperience()) {
                    Skill skill = Skill.forId(i);
                    player.getSkillManager().addExperience(skill, (int) (((damage * .90) * skill.getExperienceMultiplier()) / container.getExperience().length));
                }
            }

            if (player.getSkillManager().getExperience(Skill.CONSTITUTION) < 1000000000)
                player.getSkillManager().addExperience(Skill.CONSTITUTION, (int) (damage * .33) * Skill.CONSTITUTION.getExperienceMultiplier());
        }
    }

    /**
     * Handles various armor effects for the attacker and victim.
     *
     * @param builder   the attacker's combat builder.
     * @param container the attacker's combat container.
     * @param damage    the total amount of damage dealt.
     */
    // TODO: Use abstraction for this, will need it when more effects are added.
    static void handleArmorEffects(Character attacker, Character target, int damage, CombatType combatType) {

        if (attacker.getConstitution() > 0 && damage > 0) {


            //Barrows set effects
            if (attacker.isPlayer()) {

                int barrowsChance = 3;

                if (attacker.asPlayer().getEquipment().contains(18845))
                    barrowsChance = 2;

                if (RandomUtility.exclusiveRandom(barrowsChance) == 0) {

                    if (FullSets.fullGuthans(attacker)) {
                        target.performGraphic(new Graphic(398));
                        attacker.heal(damage);
                    }
                }
            }


            if (target != null && target.isPlayer()) {
                Player t2 = (Player) target;

                /** RECOIL **/
                if (t2.getEquipment().getItems()[Equipment.RING_SLOT].getId() == 2550) {
                    int recDamage = (int) (damage * 0.10);
                    if (recDamage <= 0)
                        return;
                    if (recDamage > t2.getConstitution())
                        recDamage = t2.getConstitution();
                    attacker.dealDamage(new Hit(recDamage, Hitmask.RED, CombatIcon.DEFLECT));
                    ItemDegrading.handleItemDegrading(t2, DegradingItem.RING_OF_RECOIL);
                }

                /** Suffering **/
                else if (t2.getEquipment().getItems()[Equipment.RING_SLOT].getId() == 18898) {
                    int recDamage = (int) (damage * 0.25);
                    if (recDamage <= 0)
                        return;
                    if (recDamage > t2.getConstitution())
                        recDamage = t2.getConstitution();
                    attacker.dealDamage(new Hit(recDamage, Hitmask.RED, CombatIcon.DEFLECT));
                }

                /** PHOENIX NECK **/
                if (t2.getEquipment().getItems()[Equipment.AMULET_SLOT].getId() == 11090) {
                    int restore = (int) (t2.getSkillManager().getMaxLevel(Skill.CONSTITUTION) * .3);
                    if (t2.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) <= t2.getSkillManager().getMaxLevel(Skill.CONSTITUTION) * .2) {
                        t2.performGraphic(new Graphic(1690));
                        t2.getEquipment().delete(t2.getEquipment().getItems()[Equipment.AMULET_SLOT]);
                        t2.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, t2.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + restore);
                        t2.getPacketSender().sendMessage("Your Phoenix Necklace restored your Constitution, but was destroyed in the process.");
                        t2.getUpdateFlag().flag(Flag.APPEARANCE);
                    }
                }

                /** RING OF LIFE **/
                else if (t2.getEquipment().getItems()[Equipment.RING_SLOT].getId() == 2570 && t2.getLocation() != Location.WILDERNESS) {
                    if (t2.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) <= t2.getSkillManager().getMaxLevel(Skill.CONSTITUTION) * .1) {
                        t2.getEquipment().delete(t2.getEquipment().getItems()[Equipment.RING_SLOT]);
                        TeleportHandler.teleportPlayer(t2, GameSettings.DEFAULT_POSITION.copy(), TeleportType.RING_TELE);
                        t2.getPacketSender().sendMessage("Your Ring of Life tried to teleport you away, but was destroyed in the process.");
                    }
                }
                //WeaponPoison.handleWeaponPoison(((Player)attacker), t2);
            }
        }
    }

    /**
     * Handles various prayer effects for the attacker and victim.
     *
     * @param builder   the attacker's combat builder.
     * @param container the attacker's combat container.
     * @param damage    the total amount of damage dealt.
     */
    static void handleNPCperks(Character attacker, Character target, int damage) {
        if (attacker == null || target == null)
            return;
        // Prayer effects can only be done with victims that are players.
        if (target.isPlayer() && damage > 0) {
            Player victim = (Player) target;
            NPC n = (NPC) attacker;

            if (n.parasitic > 0) {
                int restore = damage * n.parasitic / 20;

                if (n.getConstitution() + restore <= n.getDefaultConstitution()) {
                    n.setConstitution(n.getConstitution() + restore);
                }

            }

            if (n.confusion > 0) {
                if (n.confusion >= RandomUtility.inclusiveRandom(20)) {
                    victim.getCombatBuilder().reset(true);
                    victim.getPacketSender().sendMessage("@red@You are confused by the attack.");
                }
            }
        }
    }

    /**
     * Handles various prayer effects for the attacker and victim.
     *
     * @param builder   the attacker's combat builder.
     * @param container the attacker's combat container.
     * @param damage    the total amount of damage dealt.
     */
    static void handlePrayerEffects(Character attacker, Character target, int damage, CombatType combatType) {
        if (attacker == null || target == null)
            return;
        // Prayer effects can only be done with victims that are players.
        if (target.isPlayer() && damage > 0) {
            Player victim = (Player) target;

            // The redemption prayer effect.
            if (PrayerHandler.isActivated(victim, PrayerHandler.REDEMPTION) && victim.getConstitution() <= (victim.getSkillManager().getMaxLevel(Skill.CONSTITUTION) / 10)) {
                int amountToHeal = (int) (victim.getSkillManager().getMaxLevel(Skill.PRAYER) * .25);
                victim.performGraphic(new Graphic(436));
                victim.getSkillManager().setCurrentLevel(Skill.PRAYER, 0);
                victim.getSkillManager().updateSkill(Skill.PRAYER);
                victim.getSkillManager().setCurrentLevel(Skill.CONSTITUTION,
                        victim.getConstitution() + amountToHeal);
                victim.getSkillManager().updateSkill(Skill.CONSTITUTION);
                victim.getPacketSender().sendMessage(
                        "You've run out of prayer points!");
                PrayerHandler.deactivateAll(victim);
                return;
            }

            // These last prayers can only be done with player attackers.
            if (attacker.isPlayer()) {

                Player p = (Player) attacker;
                // The retribution prayer effect.
                if (PrayerHandler.isActivated(victim, PrayerHandler.RETRIBUTION) && victim.getConstitution() < 1) {
                    victim.performGraphic(new Graphic(437));
                    if (p.getPosition().isWithinDistance(victim.getPosition(), CombatFactory.RETRIBUTION_RADIUS)) {
                        p.dealDamage(new Hit(RandomUtility.inclusiveRandom(CombatFactory.MAXIMUM_RETRIBUTION_DAMAGE), Hitmask.RED, CombatIcon.DEFLECT));
                    }
                } else if (CurseHandler.isActivated(victim, CurseHandler.WRATH) && victim.getConstitution() < 1) {
                    victim.performGraphic(new Graphic(2259));
                    victim.performAnimation(new Animation(12583));
                    if (p.getPosition().isWithinDistance(victim.getPosition(), CombatFactory.RETRIBUTION_RADIUS)) {
                        p.performGraphic(new Graphic(2260));
                        p.dealDamage(new Hit(RandomUtility.inclusiveRandom(CombatFactory.MAXIMUM_RETRIBUTION_DAMAGE), Hitmask.RED, CombatIcon.DEFLECT));
                    }
                }

                if (PrayerHandler.isActivated((Player) attacker,
                        PrayerHandler.SMITE)) {
                    victim.getSkillManager().setCurrentLevel(Skill.PRAYER,
                            victim.getSkillManager().getCurrentLevel(Skill.PRAYER) - damage / 4);
                    if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) < 0)
                        victim.getSkillManager().setCurrentLevel(Skill.PRAYER, 0);
                    victim.getSkillManager().updateSkill(Skill.PRAYER);
                }
            }
        }

        if (attacker.isPlayer()) {

            Player p = (Player) attacker;
            if (CurseHandler.isActivated(p, CurseHandler.TURMOIL)) {
                if (RandomUtility.inclusiveRandom(5) >= 3) {
                    int increase = RandomUtility.inclusiveRandom(2);
                    if (p.getLeechedBonuses()[increase] + 1 < 30) {
                        p.getLeechedBonuses()[increase] += 1;
                        BonusManager.sendCurseBonuses(p);
                    }
                }
            }

            //Scythe of Vitur (healing)
            if (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getId() == 21006 && combatType == CombatType.MELEE && (damage > 0)) {

                if (!p.healingEffects)
                    return;

                if (p.getScytheCharges() > 0) {

                    if (RandomUtility.inclusiveRandom(100) >= p.Berserker * 20) {
                        p.setScytheCharges(p.getScytheCharges() - 1);
                    }

                    p.performGraphic(new Graphic(377, GraphicHeight.MIDDLE));
                    vampiricDamage(p, damage, 10);

                    if (p.getScytheCharges() % 1000 == 0) {
                        p.getPacketSender().sendMessage("Your Scythe of Vitur now has " + p.getScytheCharges() + " charges.");
                    } else if (p.getScytheCharges() < 1000 && p.getScytheCharges() % 100 == 0) {
                        p.getPacketSender().sendMessage("Your Scythe of Vitur now has " + p.getScytheCharges() + " charges.");
                    }

                    //p.performAnimation(new Animation(1203));
                    //p.performGraphic(new Graphic(282, GraphicHeight.HIGH));
                }
            }

            //Sanguinesti Staff (healing)
            if (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getId() == 21005 && combatType == CombatType.MAGIC && (damage > 0)) {

                if (!p.healingEffects)
                    return;

                if (p.getSanguinestiCharges() > 0 && p.getAutocastSpell().spellId() == 12447) {

                    if (RandomUtility.inclusiveRandom(100) >= p.Prophetic * 20) {
                        p.setSanguinestiCharges(p.getSanguinestiCharges() - 1);
                    }

                    p.performGraphic(new Graphic(377, GraphicHeight.HIGH));
                    vampiricDamage(p, damage, 10);

                    if (p.getSanguinestiCharges() % 1000 == 0) {
                        p.getPacketSender().sendMessage("Your Sanguinesti Staff now has " + p.getSanguinestiCharges() + " charges.");
                    } else if (p.getSanguinestiCharges() < 1000 && p.getSanguinestiCharges() % 100 == 0) {
                        p.getPacketSender().sendMessage("Your Sanguinesti Staff now has " + p.getSanguinestiCharges() + " charges.");
                    }
                }
            }

            //Vampirism
            if ((p.Vampirism > 0) && (damage > 0) && !target.isPlayer() && PerkHandler.canUseNormalPerks(p)) {
                vampiricDamage(p, damage, 2.5 * p.Vampirism);
            }

            //Keris Partisan (sun)
            if (p.getEquipment().contains(21084) && !target.isPlayer()) {
                vampiricDamage(p, damage, 3);
            }


            if ((GlobalEventHandler.effectActive(GlobalEvent.Effect.LIFELINK)) && (damage > 0) && PerkHandler.canUseNormalPerks(p) && p.healingEffects) {
                NPC npc = target.getAsNpc();

                if (npc.getDefinition().getName().contains("pvp")) {
                } else {
                    final int form = Math.round(damage / 10);
                    p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + form);
                    if (p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) > p.maxHealth())
                        p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.maxHealth());
                }
            }

            if (p.getEquipment().bloodEquipBonus() > 0 && p.healingEffects) {
                int bloodBonus = 100 / p.getEquipment().bloodEquipBonus();
                final int form = Math.round(damage / bloodBonus * 5);
                p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + form);
                if (p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) > p.maxHealth())
                    p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.maxHealth());
            }

            if (p.getEquipment().soulEquipBonus() > 0) {
                int soulBonus = 100 / p.getEquipment().soulEquipBonus();
                final int form = Math.round(damage / soulBonus * 2);
                p.getSkillManager().setCurrentLevel(Skill.PRAYER, p.getSkillManager().getCurrentLevel(Skill.PRAYER) + form);
                if (p.getSkillManager().getCurrentLevel(Skill.PRAYER) > p.maxHealth())
                    p.getSkillManager().setCurrentLevel(Skill.PRAYER, p.maxHealth());
            }

            if (p.getEquipment().contains(224780) && (damage > 0) && p.healingEffects) {

                if (RandomUtility.inclusiveRandom(4) == 1) {
                    final int form = Math.round(damage / 10 * 3);
                    //p.getPacketSender().sendMessage("@blu@Your Amulet of Blood Fury triggers! You heal @red@" +display+ " @blu@hp!");
                    p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + form);
                    if (p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) > p.maxHealth())
                        p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.maxHealth());
                }
                //else {p.getPacketSender().sendMessage("");

            }
            if (p.Eldritch && p.healingEffects) {
                final int form = damage / 2;
                p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + form);
                if (p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) > p.maxHealth())
                    p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.maxHealth());
            }

            if (CurseHandler.isActivated(p, CurseHandler.SOUL_SPLIT) && damage > 0 && p.healingEffects) {
                final int form = damage / 5;
                new Projectile(attacker, target, 2263, 44, 3, 43, 31, 0).sendProjectile();
                TaskManager.submit(new Task(1, p, false) {
                    @Override
                    public void execute() {
                        if (!(attacker == null || target == null || attacker.getConstitution() <= 0)) {
                            target.performGraphic(new Graphic(2264, GraphicHeight.LOW));
                            p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + form);
                            if (p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) > p.maxHealth())
                                p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.maxHealth());
                            if (target.isPlayer()) {
                                Player victim = (Player) target;
                                victim.getSkillManager().setCurrentLevel(Skill.PRAYER, victim.getSkillManager().getCurrentLevel(Skill.PRAYER) - form);
                                if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) < 0) {
                                    victim.getSkillManager().setCurrentLevel(Skill.PRAYER, 0);
                                    CurseHandler.deactivateCurses(victim);
                                    PrayerHandler.deactivatePrayers(victim);
                                }
                                victim.getSkillManager().updateSkill(Skill.PRAYER);
                            }
                        }
                        stop();
                    }
                });
            }
            if (p.getCurseActive()[CurseHandler.LEECH_ATTACK] || p.getCurseActive()[CurseHandler.LEECH_DEFENCE] || p.getCurseActive()[CurseHandler.LEECH_STRENGTH] || p.getCurseActive()[CurseHandler.LEECH_MAGIC] || p.getCurseActive()[CurseHandler.LEECH_RANGED] || p.getCurseActive()[CurseHandler.LEECH_SPECIAL_ATTACK] || p.getCurseActive()[CurseHandler.LEECH_ENERGY]) {
                int i, gfx, projectileGfx;
                i = gfx = projectileGfx = -1;
                if (RandomUtility.inclusiveRandom(10) >= 7 && p.getCurseActive()[CurseHandler.LEECH_ATTACK]) {
                    i = 0;
                    projectileGfx = 2252;
                    gfx = 2253;
                } else if (RandomUtility.inclusiveRandom(15) >= 11 && p.getCurseActive()[CurseHandler.LEECH_DEFENCE]) {
                    i = 1;
                    projectileGfx = 2248;
                    gfx = 2250;
                } else if (RandomUtility.inclusiveRandom(11) <= 3 && p.getCurseActive()[CurseHandler.LEECH_STRENGTH]) {
                    i = 2;
                    projectileGfx = 2236;
                    gfx = 2238;
                } else if (RandomUtility.inclusiveRandom(20) >= 16 && p.getCurseActive()[CurseHandler.LEECH_RANGED]) {
                    i = 4;
                    projectileGfx = 2236;
                    gfx = 2238;
                } else if (RandomUtility.inclusiveRandom(30) >= 24 && p.getCurseActive()[CurseHandler.LEECH_MAGIC]) {
                    i = 6;
                    projectileGfx = 2244;
                    gfx = 2242;
                } else if (RandomUtility.inclusiveRandom(30) <= 4 && p.getCurseActive()[CurseHandler.LEECH_SPECIAL_ATTACK]) {
                    i = 7;
                    projectileGfx = 2256;
                    gfx = 2257;
                } else if (RandomUtility.inclusiveRandom(30) <= 4 && p.getCurseActive()[CurseHandler.LEECH_ENERGY]) {
                    i = 8;
                    projectileGfx = 2256;
                    gfx = 2257;
                }
                if (i != -1) {
                    p.performAnimation(new Animation(12575));
                    if (i != 7 && i != 8) {
                        if (p.getLeechedBonuses()[i] < 2)
                            p.getLeechedBonuses()[i] += RandomUtility.inclusiveRandom(2);
                        BonusManager.sendCurseBonuses(p);
                    }
                    if (target.isPlayer()) {
                        Player victim = (Player) target;
                        new Projectile(attacker, target, projectileGfx, 44, 3, 43, 31, 0).sendProjectile();
                        victim.performGraphic(new Graphic(gfx));
                        if (i != 7 && i != 8) {
                            CurseHandler.handleLeech(victim, i, 2, -25, true);
                            BonusManager.sendCurseBonuses(victim);
                        } else if (i == 7) {
                            //Leech spec
                            boolean leeched = false;
                            if ((victim.getSpecialPercentage() - 10) >= 0) {
                                victim.setSpecialPercentage(victim.getSpecialPercentage() - 10);
                                CombatSpecial.updateBar(victim);
                                victim.getPacketSender().sendMessage("Your Special Attack has been leeched by an enemy curse!");
                                leeched = true;
                            }
                            if (leeched) {
                                p.setSpecialPercentage(p.getSpecialPercentage() + 10);
                                if (p.getSpecialPercentage() > 100)
                                    p.setSpecialPercentage(100);
                            }
                        } else if (i == 8) {
                            //Leech energy
                            boolean leeched = false;
                            if ((victim.getRunEnergy() - 30) >= 0) {
                                victim.setRunEnergy(victim.getRunEnergy() - 30);
                                victim.getPacketSender().sendMessage("Your energy has been leeched by an enemy curse!");
                                leeched = true;
                            }
                            if (leeched) {
                                p.setRunEnergy(p.getRunEnergy() + 30);
                                if (p.getRunEnergy() > 100)
                                    p.setRunEnergy(100);
                            }
                        }
                    }
                    if (p.personalFilterCurses) {
                        p.getPacketSender().sendMessage("You manage to leech your target's " + (i == 8 ? ("energy") : i == 7 ? ("Special Attack") : Misc.formatText(Skill.forId(i).toString().toLowerCase())) + ".");
                    }
                }
            } else {
                boolean sapWarrior = p.getCurseActive()[CurseHandler.SAP_WARRIOR];
                boolean sapRanger = p.getCurseActive()[CurseHandler.SAP_RANGER];
                boolean sapMage = p.getCurseActive()[CurseHandler.SAP_MAGE];
                if (sapWarrior || sapRanger || sapMage) {
                    if (sapWarrior && RandomUtility.inclusiveRandom(8) <= 2) {
                        CurseHandler.handleLeech(target, 0, 1, -10, true);
                        CurseHandler.handleLeech(target, 1, 1, -10, true);
                        CurseHandler.handleLeech(target, 2, 1, -10, true);
                        p.performGraphic(new Graphic(2214));
                        p.performAnimation(new Animation(12575));
                        new Projectile(p, target, 2215, 44, 3, 43, 31, 0).sendProjectile();
                        if (p.personalFilterCurses) {
                            p.getPacketSender().sendMessage("You decrease your target's Attack, Strength and Defence level..");
                        }
                    } else if (sapRanger && RandomUtility.inclusiveRandom(16) >= 9) {
                        CurseHandler.handleLeech(target, 4, 1, -10, true);
                        CurseHandler.handleLeech(target, 1, 1, -10, true);
                        p.performGraphic(new Graphic(2217));
                        p.performAnimation(new Animation(12575));
                        new Projectile(p, target, 2218, 44, 3, 43, 31, 0).sendProjectile();
                        if (p.personalFilterCurses) {
                            p.getPacketSender().sendMessage("You decrease your target's Ranged and Defence level..");
                        }
                    } else if (sapMage && RandomUtility.inclusiveRandom(15) >= 10) {
                        CurseHandler.handleLeech(target, 6, 1, -10, true);
                        CurseHandler.handleLeech(target, 1, 1, -10, true);
                        p.performGraphic(new Graphic(2220));
                        p.performAnimation(new Animation(12575));
                        new Projectile(p, target, 2221, 44, 3, 43, 31, 0).sendProjectile();
                        if (p.personalFilterCurses) {
                            p.getPacketSender().sendMessage("You decrease your target's Magic and Defence level..");
                        }
                    }
                }
            }
        }
        if (target.isPlayer()) {
            Player victim = (Player) target;
            if (damage > 0 && RandomUtility.inclusiveRandom(10) <= 4) {
                int deflectDamage = -1;
                if (CurseHandler.isActivated(victim, CurseHandler.DEFLECT_MAGIC) && combatType == CombatType.MAGIC) {
                    victim.performGraphic(new Graphic(2228, GraphicHeight.MIDDLE));
                    victim.performAnimation(new Animation(12573));
                    deflectDamage = (int) (damage * 0.20);
                } else if (CurseHandler.isActivated(victim, CurseHandler.DEFLECT_MISSILES) && combatType == CombatType.RANGED) {
                    victim.performGraphic(new Graphic(2229, GraphicHeight.MIDDLE));
                    victim.performAnimation(new Animation(12573));
                    deflectDamage = (int) (damage * 0.20);
                } else if (CurseHandler.isActivated(victim, CurseHandler.DEFLECT_MELEE) && combatType == CombatType.MELEE) {
                    victim.performGraphic(new Graphic(2230, GraphicHeight.MIDDLE));
                    victim.performAnimation(new Animation(12573));
                    deflectDamage = (int) (damage * 0.20);
                }
                if (deflectDamage > 0) {
                    if (deflectDamage > attacker.getConstitution())
                        deflectDamage = attacker.getConstitution();
                    final int toDeflect = deflectDamage;
					/*TaskManager.submit(new Task(1, victim, false) {
						@Override
						public void execute() {
						}
							if(attacker == null || attacker.getConstitution() <= 0) {
								stop();
							} else
								attacker.dealDamage(new Hit(toDeflect, Hitmask.RED, CombatIcon.DEFLECT));
							stop();
						}*/
                }
            }
        }


    }

    static void handleSpellEffects(Character attacker, Character target, int damage, CombatType combatType) {
        if (damage <= 0)
            return;
        if (target.isPlayer()) {
            Player t = (Player) target;
            if (t.hasVengeance()) {
                t.setHasVengeance(false);
                t.forceChat("Taste Vengeance!");
                int returnDamage = (int) (damage * 0.75);
                if (attacker.getConstitution() < returnDamage)
                    returnDamage = attacker.getConstitution();
                attacker.dealDamage(new Hit(returnDamage, Hitmask.RED, CombatIcon.DEFLECT));
            }
            if (t.Reflect > 0 && PerkHandler.canUseMasteryPerks(t)) {
                //t.forceChat("Taste Vengeance!");

                if (t.completedLogs / 2 > RandomUtility.inclusiveRandom(100)) {
                    t.performGraphic(new Graphic(1965, GraphicHeight.HIGH));
                    int returnDamage = (int) (damage * 0.75);
                    if (attacker.getConstitution() < returnDamage)
                        returnDamage = attacker.getConstitution();
                    attacker.dealDamage(new Hit(returnDamage, Hitmask.RED, CombatIcon.DEFLECT));
                    if (!t.chatFilter && t.personalFilterMasteryTriggers)
                        t.getPacketSender().sendMessage("@blu@Your Reflect Mastery Perk triggers...");
                }
            }
        } else if (attacker.isPlayer()) {
            NPC n = (NPC) target;
            if (n.hasVengeance()) {
                n.setHasVengeance(false);
                n.forceChat("Taste Vengeance!");
                int returnDamage = (int) (damage * 0.75);
                if (attacker.getConstitution() < returnDamage)
                    returnDamage = attacker.getConstitution();
                attacker.dealDamage(new Hit(returnDamage, Hitmask.RED, CombatIcon.DEFLECT));

            }
        }
    }


    public static void chargeDragonFireShield(Player player) {
        if (player.getDfsCharges() >= 20) {
            //player.getPacketSender().sendMessage("Your Dragonfire shield is fully charged and can be operated.");
            return;
        }
        player.performAnimation(new Animation(6695));
        player.performGraphic(new Graphic(1164));
        player.incrementDfsCharges(1);
        BonusManager.update(player, 0, 0);
        //player.getPacketSender().sendMessage("Your shield absorbs some of the Dragon's fire..");
    }

    public static void handleDragonFireShield(final Player player, final Character target) {
        if (player == null || target == null || target.getConstitution() <= 0 || player.getConstitution() <= 0)
            return;
        player.getCombatBuilder().cooldown(false);
        player.setEntityInteraction(target);
        player.performAnimation(new Animation(6696));
        player.performGraphic(new Graphic(1165));

        TaskManager.submit(new Task(1, player, false) {
            int ticks = 0;

            @Override
            public void execute() {
                switch (ticks) {
                    case 3:
                        new Projectile(player, target, 1166, 44, 3, 43, 31, 0).sendProjectile();
                        break;
                    case 4:
                        if (player.getEquipment().contains(11283) || player.getEquipment().contains(20564) || player.getEquipment().contains(1540))
                            break;

                        Hit h = new Hit(50 + RandomUtility.inclusiveRandom(150), Hitmask.RED, CombatIcon.MAGIC);
                        target.dealDamage(h);
                        target.performGraphic(new Graphic(1167, GraphicHeight.HIGH));
                        target.getCombatBuilder().addDamage(player, h.getDamage());
                        target.getLastCombat().reset();
                        stop();
                        break;
                }
                ticks++;
            }
        });
        player.incrementDfsCharges(-20);
        BonusManager.update(player, 0, 0);
    }

    public static void vampiricDamage(Player p, int damage, double factor) {

        int restore = (int) (damage * factor / 100);

        if (p.healingEffects) {
            p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + restore);
        }
        p.getSkillManager().setCurrentLevel(Skill.PRAYER, p.getSkillManager().getCurrentLevel(Skill.PRAYER) + restore);
        if (p.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) > p.maxHealth())
            p.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, p.maxHealth());
        if (p.getSkillManager().getCurrentLevel(Skill.PRAYER) > p.getSkillManager().getMaxLevel(Skill.PRAYER))
            p.getSkillManager().setCurrentLevel(Skill.PRAYER, p.getSkillManager().getMaxLevel(Skill.PRAYER));

        //else {p.getPacketSender().sendMessage("Your draining strike does not trigger :(");}

    }

    public static boolean properLocation(Player player, Player player2) {
        return player.getLocation().canAttack(player, player2);
    }
}
