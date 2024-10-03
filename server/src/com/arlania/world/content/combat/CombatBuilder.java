package com.arlania.world.content.combat;

import com.arlania.engine.task.TaskManager;
import com.arlania.util.Misc;
import com.arlania.util.Stopwatch;
import com.arlania.world.content.combat.CombatContainer.CombatHit;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.Entity;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Holds methods for running the entire combat process.
 *
 * @author lare96
 */
public class CombatBuilder {

    /**
     * The entity controlling this builder.
     */
    private final Character character;

    /**
     * The entity this controller is currently attacking.
     */
    private Character victim;

    /**
     * The entity that last attacked you.
     */
    private Character lastAttacker;

    /**
     * The task used to handle the combat process.
     */
    private CombatHookTask combatTask;
    private CombatDistanceTask distanceTask;

    /**
     * A map of all players who have inflicted damage on this controller.
     */
    private Map<Player, CombatDamageCache> damageMap = new HashMap<>();

    public Map<Player, CombatDamageCache> getDamageMap() {
        return damageMap;
    }

    public void setDamageMap(Map<Player, CombatDamageCache> damageMap) {
        this.damageMap = damageMap;
    }

    /**
     * The combat strategy this entity is using to attack.
     */
    private CombatStrategy strategy;

    /**
     * The time that must be waited in order to attack.
     */
    protected int attackTimer;

    /**
     * The time that must be waited before the entity can be attacked.
     */
    protected int cooldown;

    /**
     * Did the player's auto retaliate start the combat sequence?
     */
    private boolean retaliated;

    private CombatContainer container;

    private final Stopwatch lastAttack = new Stopwatch();

    /**
     * Create a new {@link CombatBuilder}.
     *
     * @param entity the entity controlling this builder.
     */
    public CombatBuilder(Character entity) {
        this.character = entity;
    }

    private boolean transforming;

    public boolean isTransforming() {
        return transforming;
    }

    public void setTransforming(boolean transforming) {
        this.transforming = transforming;
    }

    /**
     * Prompts the controller to begin attacking the argued entity. Wilderness
     * and multicombat checks are still applied, although later on in the
     * process. If this controller is already attack
     *
     * @param target the entity that this controller will attempt to attack.
     */
    public void attack(Character target) {

        if (character.isPlayer()) {
            if (character.asPlayer().hasAttribute("pickingUp"))
                return;
        }

        // Make sure we aren't attacking ourself.
        if (character.equals(target)) {
            return;
        }

        if (target.equals(victim)) {
            determineStrategy();

            if (!character.getPosition().equals(victim.getPosition()) && character.getPosition().isWithinDistance(victim.getPosition(), strategy.attackDistance(character))) {
                character.getMovementQueue().reset();
            }
        }

        // Start following the victim right away.
        character.getMovementQueue().setFollowCharacter(target);
        if (character.getInteractingEntity() != target)
            character.setEntityInteraction(target);

        // If the combat task is running, change targets.
        if (combatTask != null && combatTask.isRunning()) {
            victim = target;

            if (lastAttacker == null || lastAttacker != victim) {
                retaliated = false;
            }
            if (character.isPlayer()) {
                Player player = (Player) character;
                if (player.isAutocast() || player.getCastSpell() == null || attackTimer < 1) {
                    cooldown = 0;
                }
            }
            return;
        }

        // Start the event listener implementation that will allow the
        // controller to attack the victim once we're close enough.
        TaskManager.cancelTasks(this);

        if (distanceTask != null) {
            if (distanceTask.isRunning()) {
                distanceTask.stop();
            }
        }
        distanceTask = new CombatDistanceTask(this, target);
        TaskManager.submit(distanceTask);

    }

    /**
     * Resets this combat builder by discarding various values associated with
     * the combat process.
     */
    public void reset(boolean resetAttack) {

        // Reset and discard all the builder's fields.

        if (distanceTask != null) {
            distanceTask.stop();
        }

        if (combatTask != null) {
            combatTask.stop();
        }

        victim = null;
        distanceTask = null;
        combatTask = null;
        container = null;
        if (resetAttack) {
            attackTimer = 0;
        }
        strategy = null;
        cooldown = 0;
        character.setEntityInteraction(null);
        character.getMovementQueue().setFollowCharacter(null);
    }

    /**
     * Starts the cooldown sequence.
     */
    public void cooldown(boolean resetAttack) {

        // Check if we're even actively in combat.
        if (strategy == null)
            return;

        // Start the cooldown.
        cooldown = 10;

        // Stop following whomever.
        character.getMovementQueue().setFollowCharacter(null);
        character.setEntityInteraction(null);

        // Reset attack timer if needed.
        if (resetAttack) {
            attackTimer = strategy.attackDelay(character);
        }
    }

    public void resetCooldown() {
        this.cooldown = 0;
    }

    /**
     * Performs a search on the <code>damageMap</code> to find which
     * {@link Player} dealt the most damage on this controller.
     *
     * @param clearMap <code>true</code> if the map should be discarded once the
     *                 killer is found, <code>false</code> if no data in the map
     *                 should be modified.
     * @return the player who killed this entity, or <code>null</code> if an npc
     * or something else killed this entity.
     */
    public Player getKiller(boolean clearMap) {

        // Return null if no players killed this entity.
        if (damageMap.size() == 0) {
            return null;
        }

        // The damage and killer placeholders.
        int damage = 0;
        Player killer = null;

        for (Entry<Player, CombatDamageCache> entry : damageMap.entrySet()) {

            // Check if this entry is valid.
            if (entry == null) {
                continue;
            }

            // Check if the cached time is valid.
            long timeout = entry.getValue().getStopwatch().elapsed();
            if (timeout > CombatFactory.DAMAGE_CACHE_TIMEOUT) {
                continue;
            }

            // Check if the key for this entry is dead or has logged
            // out.
            Player player = entry.getKey();
            if (player.getConstitution() <= 0 || !player.isRegistered()) {
                continue;
            }

            // If their damage is above the placeholder value, they become the
            // new 'placeholder'.
            if (entry.getValue().getDamage() > damage) {
                damage = entry.getValue().getDamage();
                killer = entry.getKey();
            }
        }

        // Clear the damage map if needed.
        if (clearMap)
            damageMap.clear();

        // Return the killer placeholder.
        return killer;
    }

    /**
     * Adds damage to the damage map, as long as the argued amount of damage is
     * above 0 and the argued entity is a player.
     *
     * @param entity the entity to add damage for.
     * @param amount the amount of damage to add for the argued entity.
     */
    public void addDamage(Character entity, int amount) {

        // No damage below 0, and no npcs can be added to the map.
        if (amount < 1 || entity.isNpc()) {
            return;
        }

        // Add on to the damage for existing players.
        Player player = (Player) entity;
        if (damageMap.containsKey(player)) {
            damageMap.get(player).incrementDamage(amount);
            return;
        }

        // Or add a completely new entry.
        damageMap.put(player, new CombatDamageCache(amount));
    }

    /**
     * Determines if this entity is attacking another entity.
     *
     * @return true if this entity is attacking another entity.
     */
    public boolean isAttacking() {
        return victim != null;
    }

    /**
     * Determines if this entity is being attacked by another entity.
     *
     * @return true if this entity is being attacked by another entity.
     */
    public boolean isBeingAttacked() {
        return !character.getLastCombat().elapsed(5000);
    }

    /**
     * Gets the entity controlling this builder.
     *
     * @return the entity controlling this builder.
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Gets the entity this controller is currently attacking.
     *
     * @return the entity this controller is currently attacking.
     */
    public Character getVictim() {
        return victim;
    }

    public void setVictim(Character victim) {
        this.victim = victim;
    }

    /**
     * Determines if the builder is in cooldown mode.
     *
     * @return true if the builder is in cooldown mode.
     */
    public boolean isCooldown() {
        return cooldown > 0;
    }

    public void setAttackTimer(int attackTimer) {
        this.attackTimer = attackTimer;
    }

    public CombatBuilder incrementAttackTimer(int amount) {
        this.attackTimer += amount;
        return this;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    /**
     * Gets the entity that last attacked you.
     *
     * @return the entity that last attacked you.
     */
    public Character getLastAttacker() {
        return lastAttacker;
    }

    /**
     * Sets the entity that last attacked you.
     *
     * @param lastAttacker the entity that last attacked you.
     */
    public void setLastAttacker(Character lastAttacker) {
        this.lastAttacker = lastAttacker;
    }

    /**
     * Gets the current combat strategy this npc is using.
     *
     * @return the current combat strategy this npc is using.
     */
    public CombatStrategy getStrategy() {
        return strategy;
    }

    /**
     * Gets the combat task that runs the combat process.
     *
     * @return the combat task that runs the combat process.
     */
    public CombatHookTask getCombatTask() {
        return combatTask;
    }

    public CombatDistanceTask getDistanceTask() {
        return distanceTask;
    }

    /**
     * Sets the combat task that runs the combat process.
     *
     * @param combatTask the combat task that runs the combat process.
     */
    public void setCombatTask(CombatHookTask combatTask) {
        this.combatTask = combatTask;
    }

    public void setDistanceTask(CombatDistanceTask distanceTask) {
        this.distanceTask = distanceTask;
    }

    /**
     * Calculates and sets the combat strategy.
     */
    public void determineStrategy() {
        this.strategy = character.determineStrategy();
    }

    public CombatContainer getContainer() {
        if (this.container != null)
            return container;
        return strategy.attack(character, victim);
    }

    public boolean didAutoRetaliate() {
        return retaliated;
    }

    public void setDidAutoRetaliate(boolean retaliated) {
        this.retaliated = retaliated;
    }

    public Stopwatch getLastAttack() {
        return lastAttack;
    }

    public void setContainer(CombatContainer customContainer) {
        if (customContainer != null && customContainer.getHits() != null && this.container != null) {
            CombatHit[] totalHits = Misc.concat(this.container.getHits(), customContainer.getHits());
            this.container = customContainer;
            if (!(totalHits.length > 4 || totalHits.length < 0)) {
                this.container.setHits(totalHits);
            }
        } else
            this.container = customContainer;
    }

    /**
     * A value held in the damage map for caching damage dealt against an
     * {@link Entity}.
     *
     * @author lare96
     */
    public static class CombatDamageCache {

        /**
         * The amount of cached damage.
         */
        private int damage;

        /**
         * The stopwatch to time how long the damage is cached.
         */
        private final Stopwatch stopwatch;

        /**
         * Create a new {@link CombatDamageCache}.
         *
         * @param damage the amount of cached damage.
         */
        public CombatDamageCache(int damage) {
            this.damage = damage;
            this.stopwatch = new Stopwatch().reset();
        }

        /**
         * Gets the amount of cached damage.
         *
         * @return the amount of cached damage.
         */
        public int getDamage() {
            return damage;
        }

        /**
         * Increments the amount of cached damage.
         *
         * @param damage the amount of cached damage to add.
         */
        public void incrementDamage(int damage) {
            this.damage += damage;
            this.stopwatch.reset();
        }

        /**
         * Gets the stopwatch to time how long the damage is cached.
         *
         * @return the stopwatch to time how long the damage is cached.
         */
        public Stopwatch getStopwatch() {
            return stopwatch;
        }
    }

    /**
     * Executes the task instantly
     **/
    public void instant() {
        combatTask.execute();
    }
}
