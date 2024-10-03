package com.arlania.world.content.combat;

import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A container that holds all of the data needed for a single combat hook.
 *
 * @author lare96
 */
public class CombatContainer {

    /**
     * The attacker in this combat hook.
     */
    private final Character attacker;

    /**
     * The victim in this combat hook.
     */
    private final Character victim;

    /**
     * The hits that will be dealt during this combat hook.
     */
    private CombatHit[] hits;

    /**
     * The skills that will be given experience.
     */
    private final int[] experience;

    /**
     * The combat type that is being used during this combat hook.
     */
    private CombatType combatType;

    /**
     * If accuracy should be taken into account.
     */
    private boolean checkAccuracy;

    /**
     * If at least one hit in this container is accurate.
     */
    private boolean accurate;

    /**
     * The modified damage, used for bolt effects etc
     **/
    private int modifiedDamage;

    /**
     * The delay before the hit is executed
     **/
    private final int hitDelay;

    /**
     * Create a new {@link CombatContainer}.
     *
     * @param attacker      the attacker in this combat hook.
     * @param victim        the victim in this combat hook.
     * @param hitAmount     the amount of hits to deal this combat hook.
     * @param hitType       the combat type that is being used during this combat hook
     * @param checkAccuracy if accuracy should be taken into account.
     */
    public CombatContainer(Character attacker, Character victim, int hitAmount, CombatType hitType, boolean checkAccuracy) {
        this.attacker = attacker;
        this.victim = victim;
        this.combatType = hitType;
        this.checkAccuracy = checkAccuracy;
        this.hits = prepareHits(hitAmount);
        this.experience = getSkills(hitType);
        this.hitDelay = hitType == CombatType.MELEE ? 0 : hitType == CombatType.RANGED ? 2 : hitType == CombatType.MAGIC || hitType == CombatType.DRAGON_FIRE ? 3 : 1;
    }

    public CombatContainer(Character attacker, Character victim, int hitAmount, int hitDelay, CombatType hitType, boolean checkAccuracy) {
        this.attacker = attacker;
        this.victim = victim;
        this.combatType = hitType;
        this.checkAccuracy = checkAccuracy;
        this.hits = prepareHits(hitAmount);
        this.experience = getSkills(hitType);
        this.hitDelay = hitDelay;
    }

    /**
     * Create a new {@link CombatContainer} that will deal no hit this turn.
     * Used for things like spells that have special effects but don't deal
     * damage.
     *
     * @param checkAccuracy if accuracy should be taken into account.
     */
    public CombatContainer(Character attacker, Character victim, CombatType hitType, boolean checkAccuracy) {
        this(attacker, victim, 0, hitType, checkAccuracy);
    }


    public void dealDamage(CombatHit[] hits) {
        if (hits.length == 1) {
            victim.dealDamage(hits[0].getHit());
        } else if (hits.length == 2) {
            victim.dealDoubleDamage(hits[0].getHit(), hits[1].getHit());
        } else if (hits.length == 3) {
            victim.dealTripleDamage(hits[0].getHit(), hits[1].getHit(), hits[2].getHit());
        } else if (hits.length == 4) {
            victim.dealQuadrupleDamage(hits[0].getHit(), hits[1].getHit(), hits[2].getHit(), hits[3].getHit());
        }
    }

    /**
     * Prepares the hits that will be dealt this combat hook.
     *
     * @param hitAmount the amount of hits to deal, maximum 4 and minimum 0.
     * @return the hits that will be dealt this combat hook.
     */
    private final CombatHit[] prepareHits(int hitAmount) {

        // Check the hit amounts.
        if (hitAmount > 4) {
            throw new IllegalArgumentException(
                    "Illegal number of hits! The maximum number of hits per turn is 4.");
        } else if (hitAmount < 0) {
            throw new IllegalArgumentException(
                    "Illegal number of hits! The minimum number of hits per turn is 0.");
        }

        // No hit for this turn, but we still need to calculate accuracy.
        if (hitAmount == 0) {
            accurate = !checkAccuracy || CombatFactory.rollAccuracy(attacker, victim, combatType);
            return new CombatHit[]{};
        }

        // Create the new array of hits, and populate it. Here we do the maximum
        // hit and accuracy calculations.

        boolean accuracy = !checkAccuracy;


        if (attacker.isPlayer()) {
            Player player = attacker.asPlayer();


            if (player.getEquipment().contains(21063) && victim.isNpc()) {
                if (RandomUtility.inclusiveRandom(100) < 5) {
                    hitAmount++;
                }
            }

            if (player.getEquipment().contains(21064) && victim.isNpc()) {
                if (RandomUtility.inclusiveRandom(100) < 10) {
                    hitAmount++;
                }
            }


            if (hitAmount > 4)
                hitAmount = 4;

            if(player.Impulsive && RandomUtility.inclusiveRandom(0, 3) == 1) {
                hitAmount++;
            }

            if (player.Flurry == 1 && PerkHandler.canUseMasteryPerks(player)) {
                if (RandomUtility.inclusiveRandom(100) < player.completedLogs / 3) {
                    hitAmount++;
                    player.performGraphic(new Graphic(2011, GraphicHeight.MIDDLE));
                    //player.getPacketSender().sendMessage("@blu@Your Flurry perk activates!");
                }
            }
        }

        if(attacker.isNpc()) {
            if(attacker.getAsNpc().swiftly > 0){
                if(attacker.getAsNpc().swiftly >= RandomUtility.inclusiveRandom(1, 10)) {
                    hitAmount++;
                }
            }
        }


        CombatHit[] array = new CombatHit[hitAmount];
        for (int i = 0; i < array.length; i++) {

            if (GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCURACY) && !victim.isPlayer())
                accuracy = true;
            else if (attacker.isPlayer() && attacker.asPlayer().accuracyEvent && attacker.asPlayer().personalEvent && !victim.isPlayer())
                accuracy = true;
            else if (attacker.isPlayer() && victim.isPlayer() && attacker.asPlayer().getEquipment().contains(21080) && RandomUtility.inclusiveRandom(1) == 1)
                accuracy = true;
            else if (attacker.isNpc() && attacker.getAsNpc().precision > 0) {
                accuracy = attacker.getAsNpc().precision > RandomUtility.inclusiveRandom(10);
            } else
                accuracy = !checkAccuracy || CombatFactory.rollAccuracy(attacker, victim, combatType);

            /*if(attacker.isNpc()) {
                int maxMeleeHit = CombatCalculations.npcMaxHit(attacker.getAsNpc());
                int maxRangedHit = CombatCalculations.npcMaxHit(attacker.getAsNpc());
                int maxMagicHit = CombatCalculations.npcMaxHit(attacker.getAsNpc());

                //victim.asPlayer().sendMessage("Savagery: " + attacker.getAsNpc().savagery);
            }*/

            array[i] = new CombatHit(CombatFactory.getHit(attacker, victim, combatType), accuracy);

            if (array[i].isAccurate()) {
                accurate = true;
            }
        }

        /** SPECS **/

        if (attacker.isPlayer() && ((Player) attacker).isSpecialActivated()) {
            if (((Player) attacker).getCombatSpecial() == CombatSpecial.DRAGON_CLAWS && hitAmount == 4) {
                int first = array[0].getHit().getDamage();

                if (victim.isPlayer() && first > 360)
                    first = 360;

                int second = first <= 0 ? array[1].getHit().getDamage() : (first / 2);
                int third = first <= 0 && second > 0 ? (second / 2) : first <= 0 && second <= 0 ? array[2].getHit().getDamage() : RandomUtility.inclusiveRandom(second);
                int fourth = first <= 0 && second <= 0 && third <= 0 ? array[3].getHit().getDamage() + RandomUtility.inclusiveRandom(7) : first <= 0 && second <= 0 ? array[3].getHit().getDamage() : third;
                array[0].getHit().setDamage(first);
                array[1].getHit().setDamage(second);
                array[2].getHit().setDamage(third);
                array[3].getHit().setDamage(fourth);
            } else if (((Player) attacker).getCombatSpecial() == CombatSpecial.DARK_BOW && hitAmount == 2) {
                for (int i = 0; i < hitAmount; i++) {
                    if (array[i].getHit().getDamage() < 80) {
                        array[i].getHit().setDamage(80);
                    }
                    array[i].setAccurate(true);
                }
            }
        }


        if(victim.isNpc()) {

            //Scythe multi-hit
            if (attacker.isPlayer() && ((Player) attacker).getEquipment().contains(21006) && victim.getAsNpc().getDefinition().getCombatLevel() >= 250) {
                int first = array[0].getHit().getDamage();
                int second = first / 2;
                int third = first / 4;

                if (GlobalEventHandler.effectActive(GlobalEvent.Effect.ACCURACY) && !victim.isPlayer())
                    accuracy = true;
                else if (attacker.isPlayer() && attacker.asPlayer().accuracyEvent && attacker.asPlayer().personalEvent  && !victim.isPlayer())
                    accuracy = true;
                else
                    accuracy = !checkAccuracy || CombatFactory.rollAccuracy(attacker, victim, combatType);

                CombatHit[] scytheArray = new CombatHit[3];
                for (int i = 0; i < scytheArray.length; i++) {
                    scytheArray[i] = new CombatHit(CombatFactory.getHit(attacker, victim, combatType), accuracy);
                }

                scytheArray[0].getHit().setDamage(first);
                scytheArray[1].getHit().setDamage(second);
                scytheArray[2].getHit().setDamage(third);

                return scytheArray;
            }
        }
        return array;
    }

    public void setHits(CombatHit[] hits) {
        this.hits = hits;
        prepareHits(hits.length);
    }

    /**
     * Performs an action on every single hit in this container.
     *
     * @param action the action to perform on every single hit.
     */
    protected final void allHits(Consumer<CombatHit> c) {
        Arrays.stream(hits).filter(Objects::nonNull).forEach(c);
    }

    public final int getDamage() {
        int damage = 0;
        for (CombatHit hit : hits) {
            if (hit == null)
                continue;
            if (!hit.accurate) {
                int absorb = hit.getHit().getAbsorb();
                hit.hit = new Hit(0, Hitmask.RED, CombatIcon.BLOCK);
                hit.hit.setAbsorb(absorb);
            }
            damage += hit.hit.getDamage();
        }
        return damage;
    }

    public void dealDamage() {
        if (hits.length == 1) {
            victim.dealDamage(hits[0].getHit());
        } else if (hits.length == 2) {
            victim.dealDoubleDamage(hits[0].getHit(), hits[1].getHit());
        } else if (hits.length == 3) {
            victim.dealTripleDamage(hits[0].getHit(), hits[1].getHit(), hits[2].getHit());
        } else if (hits.length == 4) {
            victim.dealQuadrupleDamage(hits[0].getHit(), hits[1].getHit(), hits[2].getHit(), hits[3].getHit());
        }
    }

    /**
     * Gets all of the skills that will be trained.
     *
     * @param type the combat type being used.
     * @return an array of skills that this attack will train.
     */
    private final int[] getSkills(CombatType type) {
        if (attacker.isNpc()) {
            return new int[]{};
        }
        return ((Player) attacker).getFightType().getStyle().skill(type);
    }

    public void setModifiedDamage(int modifiedDamage) {
        this.modifiedDamage = modifiedDamage;
    }

    public int getModifiedDamage() {
        return modifiedDamage;
    }

    /**
     * A dynamic method invoked when the victim is hit with an attack. An
     * example of usage is using this to do some sort of special effect when the
     * victim is hit with a spell. <b>Do not reset combat builder in this
     * method!</b>
     *
     * @param damage   the damage inflicted with this attack, always 0 if the attack
     *                 isn't accurate.
     * @param accurate if the attack is accurate.
     */
    public void onHit(int damage, boolean accurate) {
    }

    /**
     * Gets the hits that will be dealt during this combat hook.
     *
     * @return the hits that will be dealt during this combat hook.
     */
    public final CombatHit[] getHits() {
        return hits;
    }

    /**
     * Gets the skills that will be given experience.
     *
     * @return the skills that will be given experience.
     */
    public final int[] getExperience() {
        return experience;
    }

    /**
     * Sets the amount of hits that will be dealt during this combat hook.
     *
     * @param hitAmount the amount of hits that will be dealt during this combat hook.
     */
    public final void setHitAmount(int hitAmount) {
        this.hits = prepareHits(hitAmount);
    }

    /**
     * Gets the combat type that is being used during this combat hook.
     *
     * @return the combat type that is being used during this combat hook.
     */
    public final CombatType getCombatType() {
        return combatType;
    }

    /**
     * Sets the combat type that is being used during this combat hook.
     *
     * @param combatType the combat type that is being used during this combat hook.
     */
    public final void setCombatType(CombatType combatType) {
        this.combatType = combatType;
    }

    /**
     * Gets if accuracy should be taken into account.
     *
     * @return true if accuracy should be taken into account.
     */
    public final boolean isCheckAccuracy() {
        return checkAccuracy;
    }

    /**
     * Sets if accuracy should be taken into account.
     *
     * @param checkAccuracy true if accuracy should be taken into account.
     */
    public final void setCheckAccuracy(boolean checkAccuracy) {
        this.checkAccuracy = checkAccuracy;
    }

    /**
     * Gets if at least one hit in this container is accurate.
     *
     * @return true if at least one hit in this container is accurate.
     */
    public final boolean isAccurate() {
        return accurate;
    }

    /**
     * Gets the hit delay before the hit is executed.
     *
     * @return the hit delay.
     */
    public int getHitDelay() {
        return hitDelay;
    }

    /**
     * A single hit that is dealt during a combat hook.
     *
     * @author lare96
     */
    public static class CombatHit {

        /**
         * The actual hit that will be dealt.
         */
        private Hit hit;

        /**
         * The accuracy of the hit to be dealt.
         */
        private boolean accurate;

        /**
         * Create a new {@link CombatHit}.
         *
         * @param hit      the actual hit that will be dealt.
         * @param accurate the accuracy of the hit to be dealt.
         */
        public CombatHit(Hit hit, boolean accurate) {
            this.hit = hit;
            this.accurate = accurate;
        }

        /**
         * Gets the actual hit that will be dealt.
         *
         * @return the actual hit that will be dealt.
         */
        public Hit getHit() {
            return hit;
        }

        /**
         * Sets the actual hit that will be dealt.
         *
         * @param hit the actual hit that will be dealt.
         */
        public void setHit(Hit hit) {
            this.hit = hit;
        }

        /**
         * Gets the accuracy of the hit to be dealt.
         *
         * @return the accuracy of the hit to be dealt.
         */
        public boolean isAccurate() {
            return accurate;
        }

        /**
         * Sets the accuracy of the hit to be dealt.
         *
         * @param accurate the accuracy of the hit to be dealt.
         */
        public void setAccurate(boolean accurate) {
            this.accurate = accurate;
        }
    }
}
