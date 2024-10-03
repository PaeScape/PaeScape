package com.arlania.world.entity.impl.npc;

import com.arlania.GameServer;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.NPCDeathTask;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.util.JsonLoader;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.NpcClickType;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.effect.CombatPoisonEffect.PoisonType;
import com.arlania.world.content.combat.equipment.FullSets;
import com.arlania.world.content.combat.strategy.CombatStrategies;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.content.combat.strategy.impl.KalphiteQueen;
import com.arlania.world.content.combat.strategy.impl.Zulrah;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.content.skill.impl.hunter.Hunter;
import com.arlania.world.content.skill.impl.hunter.PuroPuro;
import com.arlania.world.content.skill.impl.runecrafting.DesoSpan;
import com.arlania.world.content.skill.impl.summoning.FamiliarData;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator.Coordinator;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Represents a non-playable character, which players can interact with.
 *
 * @author Gabriel Hannason
 */

public class NPC extends Character {

    public NPC(int id, Position position) {
        super(position);
        NpcDefinition definition = NpcDefinition.forId(id);
        if (definition == null)
            throw new NullPointerException("NPC " + id + " is not defined!");
        this.defaultPosition = position;
        this.id = id;
        this.definition = definition;
        this.defaultConstitution = definition.getHitpoints() < 100 ? 100 : definition.getHitpoints();
        this.constitution = defaultConstitution;
        setLocation(Location.getLocation(this));
    }

    public ArrayList<Player> getPossibleTargets() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        for (Player player : World.getPlayers()) {
            if (player == null || player.isBot) {
                continue;
            }
            if (this.getPosition().getDistance(player.getPosition()) <= 12 && this.getPosition().getZ() == player.getPosition().getZ()) {
                possibleTargets.add(player);
            }
        }
        return possibleTargets;
    }

    public boolean isDead = false;

    public Player getRandomTarget() {
        ArrayList<Player> possibleTargets = getPossibleTargets();

        if (possibleTargets.size() >= 1) {
            Collections.shuffle(possibleTargets);
            return possibleTargets.get(0);
        }

        return null;
    }

    public static void removalTask(NPC npc, int cycles) {
        World.register(npc);
        TaskManager.submit(new Task(cycles) {
            @Override
            public void execute() {
                World.deregister(npc);
                this.stop();
            }
        });
    }

    /**
     * When a player clicks a npc, this will initiate
     */
    public void clickNpc(Player player, NpcClickType npcClickType) {

    }

    public void walkToPosition(Position targetPosition) {
        Position myPosition = this.getPosition().copy();

        int x;
        int y;

        x = targetPosition.getX() - myPosition.getX();
        y = targetPosition.getY() - myPosition.getY();

        this.getMovementQueue().walkStep(x, y);
    }

    public boolean isDead() {
        return isDead;
    }

    /**
     * Creates a new instance of this npc with the given index.
     *
     * @param index the new index of this npc.
     * @return the new instance.
     */
    public NPC copy(int npcId, Position position) {
        throw new UnsupportedOperationException("This function is not supported.");
    }

    /**
     * RAIDS
     */
    public Direction previousDirectionFacing;
    public Direction directionFacing;

    public void performGreatOlmAttack(RaidsParty party) {
        // party.getGreatOlmObject().performAnimation(OlmAnimations.shootMiddle);
        if (directionFacing != null) {
            if (party.getCurrentPhase() == 3) {
                if (getPosition().getY() >= 3238) {
                    if (directionFacing.equals(Direction.SOUTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootLeftEnraged);
                    else if (directionFacing.equals(Direction.NORTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootRightEnraged);
                    else if (directionFacing.equals(Direction.NONE))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootMiddleEnraged);
                } else {
                    if (directionFacing.equals(Direction.SOUTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootRightEnraged);
                    else if (directionFacing.equals(Direction.NORTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootLeftEnraged);
                    else if (directionFacing.equals(Direction.NONE))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootMiddleEnraged);
                }
            } else {
                if (getPosition().getY() >= 3238) {
                    if (directionFacing.equals(Direction.SOUTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootLeft);
                    else if (directionFacing.equals(Direction.NORTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootRight);
                    else if (directionFacing.equals(Direction.NONE))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootMiddle);
                } else {
                    if (directionFacing.equals(Direction.SOUTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootRight);
                    else if (directionFacing.equals(Direction.NORTH))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootLeft);
                    else if (directionFacing.equals(Direction.NONE))
                        party.getGreatOlmObject().performAnimation(OlmAnimations.shootMiddle);
                }
            }
        }
    }

    public void sequence() {

        /**
         * HP restoration
         */
        if (constitution < defaultConstitution) {
            if (!isDying) {
                if (getLastCombat().elapsed((id == 13447 || id == 3200 ? 50000 : 5000)) && !getCombatBuilder().isAttacking()) {
                    setConstitution(constitution + (int) (defaultConstitution * 0.1));
                    if (constitution > defaultConstitution)
                        setConstitution(defaultConstitution);
                }
            }
        }
        if ((id == 1648) || (id == 22367) || (id == 22382) || (id == 22383) || (id == 22385)) {
            if (this.getInteractingEntity() == null) {
                World.deregister(this);
            }
        }

        if (hasVenomDamage()) {
            dealDamage(new Hit(50, Hitmask.LIGHT_YELLOW, CombatIcon.NONE));
        }

    }


    /**
     * Returns a new instance of a NPC.
     *
     * @param id
     * @param position
     * @return
     */
    public static NPC of(int npcId, Position position) {
        if (NpcDefinition.getDefinitions()[npcId] == null) {
            GameServer.getLogger().severe("Nulled npc spawned: " + npcId);
        }
        return new NPC(npcId, position);
    }

    @Override
    public void appendDeath() {
        if (!isDying && !summoningNpc) {
            TaskManager.submit(new NPCDeathTask(this));
            isDying = true;
        }
    }

    @Override
    public int getConstitution() {
        return constitution;
    }

    @Override
    public NPC setConstitution(int constitution) {
        this.constitution = constitution;
        if (this.constitution <= 0)
            appendDeath();
        return this;
    }

    @Override
    public void heal(int heal) {
        if ((this.constitution + heal) > getDefaultConstitution()) {
            setConstitution(getDefaultConstitution());
            return;
        }
        setConstitution(this.constitution + heal);
    }


    @Override
    public int getBaseAttack(CombatType type) {
        return getDefinition().getAttackBonus();
    }

    @Override
    public int getAttackSpeed() { return this.getDefinition().getAttackSpeed(); }


    @Override
    public int getBaseDefence(CombatType type) {

        if (type == CombatType.MAGIC)
            return getDefinition().getDefenceMage();
        else if (type == CombatType.RANGED)
            return getDefinition().getDefenceRange();

        return getDefinition().getDefenceMelee();
    }

    @Override
    public boolean isNpc() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof NPC && ((NPC) other).getIndex() == getIndex();
    }

    @Override
    public int getSize() {
        return getDefinition().getSize();
    }

    @Override
    public void poisonVictim(Character victim, CombatType type) {
        if (getDefinition().isPoisonous()) {
            CombatFactory.poisonEntity(
                    victim,
                    type == CombatType.RANGED || type == CombatType.MAGIC ? PoisonType.MILD
                            : PoisonType.EXTRA);
        }

    }

    /**
     * Prepares the dynamic json loader for loading world npcs.
     *
     * @return the dynamic json loader.
     * @throws Exception if any errors occur while preparing for load.
     */
    public static void init() {
        new JsonLoader() {
            @Override
            public void load(JsonObject reader, Gson builder) {

                int id = reader.get("npc-id").getAsInt();
                Position position = builder.fromJson(reader.get("position").getAsJsonObject(), Position.class);
                Coordinator coordinator = builder.fromJson(reader.get("walking-policy").getAsJsonObject(), Coordinator.class);
                Direction direction = Direction.valueOf(reader.get("face").getAsString());
                NPC npc = new NPC(id, position);
                npc.movementCoordinator.setCoordinator(coordinator);
                npc.setDirection(direction);
                World.register(npc);
                if (id > 5070 && id < 5081) {
                    Hunter.HUNTER_NPC_LIST.add(npc);
                }
                position = null;
                coordinator = null;
                direction = null;
            }

            @Override
            public String filePath() {
                return "./data/def/json/world_npcs.json";
            }
        }.load();

        PuroPuro.spawn();
        DesoSpan.spawn();


    }

    @Override
    public CombatStrategy determineStrategy() {
        return CombatStrategies.getStrategy(id);
    }

    public boolean switchesVictim() {
        if (getLocation() == Location.PESTILENT_BLOAT || getLocation() == Location.MAIDEN_SUGADINTI || getLocation() == Location.VERZIK_VITUR) {
            return true;
        }
        return id == 6263 || id == 6265 || id == 6203 || id == 6208 || id == 6206 || id == 6247 || id == 6250 || id == 3200 || id == 4540 || id == 1158 || id == 1160 || id == 8133 || id == 13447 || id == 13451 || id == 13452 || id == 13453 || id == 13454 || id == 2896 || id == 2882 || id == 2881 || id == 6260;
    }

    public boolean hasVengeance() {
        return hasVengeance;
    }

    public void setHasVengeance(boolean hasVengeance) {
        this.hasVengeance = hasVengeance;
    }

    public int getAggressionDistance() {
        int distance = 7;
		
		/*switch(id) {
		}*/
        if (id == 2896) {
            distance = 50;
        }
        return distance;
    }

    public double specialWeaknesses(Player plr, NPC npc, CombatType combatType) {
        double damageMultiplier = 0;

        //Tekton
        if (npc.getId() == 21541 && combatType == CombatType.MELEE) {
            if (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().contains("pickaxe") ||
                    plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().contains("adze")) {
                damageMultiplier += 2;
            } else {
                damageMultiplier = 0;
                plr.getPacketSender().sendMessage("You must use a pickaxe to deal damage to Tekton!");
            }
        }

        switch (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {
            case 20998: //Twisted Bow
                if (npc.getDefinition().getCombatLevel() > 250 && combatType == CombatType.RANGED) {
                    damageMultiplier += .80;
                }
                break;
            case 21061: //Tumeken's Shadow
                if (npc.getDefinition().getCombatLevel() > 250 && combatType == CombatType.MAGIC) {
                    damageMultiplier += .50;
                }
                break;
            case 18844: //DHCB
                if (npc.isDragon(npc) && combatType == CombatType.RANGED) {
                    damageMultiplier += .50;
                }
                break;
            case 18843: //DHL
                if (npc.isDragon(npc) && combatType == CombatType.MELEE) {
                    damageMultiplier += .5;
                }
                break;
            case 219675: //Arclight
                if (npc.isDemon(npc) && combatType == CombatType.MELEE) {
                    damageMultiplier += .5;
                }
                break;
            case 6746: //Darklight
                if (npc.isDemon(npc) && combatType == CombatType.MELEE) {
                    damageMultiplier += .25;
                }
                break;
            case 15403: //Balmung
                if (npc.isDagganoth(npc) && combatType == CombatType.MELEE) {
                    damageMultiplier += .5;
                }
                break;
        }

        return damageMultiplier;
    }

    public boolean immuneToMelee(NPC npc, Player player) {


        if (FullSets.fullVeracs(player))
            return false;

        switch (npc.getId()) {
            case 1160:
            case 21553:
            case 21554:
            case 22359:
            case 22360:
            case 23021:
                return true;
        }

        return player.npcImmuneMelee;
    }

    public boolean immuneToRange(NPC npc, Player player) {

        switch (npc.getId()) {
            case 1158:
            case 21553:
            case 21555:
            case 21541:
            case 22359:
            case 22369:
            case 23022:
            case 9939: //plane-freezer
                return true;
        }

        return player.npcImmuneRange;
    }

    public boolean immuneToMagic(NPC npc, Player player) {

        switch (npc.getId()) {
            case 1158:
            case 21554:
            case 21555:
            case 21541:
            case 22360:
            case 22369:
            case 23023:
            case 9939: //plane-freezer
                return true;
        }

        return player.npcImmuneMagic;
    }


    public boolean isDragon(NPC npc) {

        int npcId = npc.getDefinition().getId();

        switch (npcId) {

            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 742:
            case 941:
            case 1590:
            case 1591:
            case 1592:
            case 21553:
            case 21554:
            case 21555:
            case 22061:

                return true;
        }

        String npcName = npc.getDefinition().getName();

        if (npcName.contains("dragon") || npcName.contains("Dragon"))
            return true;

        return npcName.contains("wyvern") || npcName.contains("Wyvern");
    }

    public boolean isDagganoth(NPC npc) {

        int npcId = npc.getDefinition().getId();

        switch (npcId) {

            case 2881:
            case 2882:
            case 2883:

                return true;
        }

        String npcName = npc.getDefinition().getName();

        return npcName.contains("dagannoth") || npcName.contains("Dagannoth");
    }

    public boolean isDemon(NPC npc) {

        //int npcId = npc.getDefinition().getId();
		
		/*switch (npcId) {
			return true;
		}*/

        String npcName = npc.getDefinition().getName();

        return npcName.toLowerCase().contains("revenan") || npcName.toLowerCase().contains("fiend") ||
                npcName.toLowerCase().contains("demo") || npcName.toLowerCase().contains("nechry") ||
                npcName.toLowerCase().contains("abyssa") || npcName.toLowerCase().contains("hellhou") ||
                npcName.toLowerCase().contains("cerber") || npcName.toLowerCase().contains("skotiz") ||
                npcName.toLowerCase().contains("tsutar") || npcName.toLowerCase().contains("bloodv") ||
                npcName.toLowerCase().contains("balfru") || npcName.toLowerCase().contains("gritc") ||
                npcName.toLowerCase().contains("tstano") || npcName.toLowerCase().contains("alucar");
    }

    public void difficultyPerks(NPC npc, int difficulty) {
        npc.swiftly = difficulty; //NPC attacks x-ticks faster
        npc.savagery = difficulty; //NPC max hit increases by X%
        npc.precision = difficulty; //can't hit through prayer
        npc.parasitic = difficulty; //Heals NPC x% of damage dealt
        npc.reduction = difficulty; //absorbs x% of damage
        npc.optimum = difficulty; //% chance to hit Max Hit
        npc.confusion = difficulty; //% chance to stun player and turn off auto-retaliate
        npc.piercing = difficulty; //% chance to hit through prayer (still checks defence bonuses)
    }


    public boolean shouldRespawn(NPC npc) {

        if (npc.getLocation() == Location.GRAVEYARD)
            return false;
        if (npc.getLocation() == Location.INSTANCEDBOSSES)
            return false;
        if (npc.getLocation() == Location.INSTANCED_SLAYER)
            return false;
        return npc.getLocation() != Location.SHR;

    }

    /*
     * Fields
     */
    /**
     * INSTANCES
     **/
    private final Position defaultPosition;
    private final NPCMovementCoordinator movementCoordinator = new NPCMovementCoordinator(this);
    private Player spawnedFor;
    private final NpcDefinition definition;
    private Task currentTask;

    /**
     * INTS
     **/
    public int id;
    private int constitution = 100;
    private int defaultConstitution;
    private int transformationId = -1;
    public int minionSpawn = 0;

    /**
     * BOOLEANS
     **/
    private final boolean[] attackWeakened = new boolean[3];
    private final boolean[] strengthWeakened = new boolean[3];
    private boolean nightmareCopy = false;
    private boolean summoningNpc, summoningCombat;
    public boolean hasSummoner = false;
    private boolean isDying;
    private boolean visible = true;
    private boolean healed, chargingAttack;
    private boolean findNewTarget;
    private boolean hasVengeance = false;
    private boolean venomDamage = false;

    //NPC Perks
    public int swiftly = 0; //NPC attacks x-ticks faster
    public int savagery = 0; //NPC max hit increases by 10%
    public int precision = 0; //can't hit through prayer
    public int parasitic = 0; //Heals NPC x% of damage dealt
    public int reduction = 0; //reduce x% of damage
    public int optimum = 0; //% chance to hit Max Hit
    public int confusion = 0; //% chance to stun player
    public int piercing = 0; //% chance to hit through prayer (still checks defence bonuses)
    public int firePools = 0; //spawns fire pool at player's feet
    public int waterPools = 0; //spawns water pool at player's feet
    public int acidPools = 0; //spwans acid pool at player's feet
    //public int restoration; //chance to heal to full hp when Max hit

    public String weakness = "";




    /*
     * Getters and setters
     */

    public int getId() {
        return id;
    }

    public int getMinionSpawn() {
        return minionSpawn;
    }

    public void setMinionSpawn(int minionSpawn) {
        this.minionSpawn = minionSpawn;
    }


    public Position getDefaultPosition() {
        return defaultPosition;
    }

    public int getDefaultConstitution() {
        return defaultConstitution;
    }

    public int getTransformationId() {
        return transformationId;
    }

    public void setTransformationId(int transformationId) {
        this.transformationId = transformationId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean hasVenomDamage() {
        return venomDamage;
    }

    public void setVenomDamage(boolean venomDamage) {
        this.venomDamage = venomDamage;
    }

    public void setDying(boolean isDying) {
        this.isDying = isDying;
    }

    public void setDefaultConstitution(int defaultConstitution) {
        this.defaultConstitution = defaultConstitution;
    }

    /**
     * @return the statsWeakened
     */
    public boolean[] getDefenceWeakened() {
        return attackWeakened;
    }

    public boolean getNightmareCopy() {
        return nightmareCopy;
    }

    public void setNightmareCopy(boolean nightmareCopy) {
        this.nightmareCopy = nightmareCopy;
    }

    public void setSummoningNpc(boolean summoningNpc) {
        this.summoningNpc = summoningNpc;
    }

    public boolean isSummoningNpc() {
        return summoningNpc;
    }

    public boolean isDying() {
        return isDying;
    }

    /**
     * @return the statsBadlyWeakened
     */
    public boolean[] getStrengthWeakened() {
        return strengthWeakened;
    }

    public NPCMovementCoordinator getMovementCoordinator() {
        return movementCoordinator;
    }

    public NpcDefinition getDefinition() {
        return definition;
    }

    public Player getSpawnedFor() {
        return spawnedFor;
    }

    public NPC setSpawnedFor(Player spawnedFor) {
        this.spawnedFor = spawnedFor;
        return this;
    }

    public boolean hasHealed() {
        return healed;
    }

    public void setHealed(boolean healed) {
        this.healed = healed;
    }

    public boolean isChargingAttack() {
        return chargingAttack;
    }

    public NPC setChargingAttack(boolean chargingAttack) {
        this.chargingAttack = chargingAttack;
        return this;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public boolean findNewTarget() {
        return findNewTarget;
    }

    public void setFindNewTarget(boolean findNewTarget) {
        this.findNewTarget = findNewTarget;
    }

    public boolean summoningCombat() {
        return summoningCombat;
    }

    public void setSummoningCombat(boolean summoningCombat) {
        this.summoningCombat = summoningCombat;
    }

    public void removeInstancedNpcs(Location loc, int height) {
        int checks = loc.getX().length - 1;

        if (FamiliarData.forNPCId(this.getId()) != null)
            return;

        for (int i = 0; i <= checks; i += 2) {
            if (getPosition().getX() >= loc.getX()[i] && getPosition().getX() <= loc.getX()[i + 1]) {
                if (getPosition().getY() >= loc.getY()[i] && getPosition().getY() <= loc.getY()[i + 1]) {
                    if (getPosition().getZ() == height) {
                        World.deregister(this);
                    }
                }
            }
        }
    }

    public void countInstancedNpcs(Location loc, int height, int npcId) {
        int checks = loc.getX().length - 1;
        int ii = 0;
        for (int i = 0; i <= checks; i += 2) {
            if (getPosition().getX() >= loc.getX()[i] && getPosition().getX() <= loc.getX()[i + 1]) {
                if (getPosition().getY() >= loc.getY()[i] && getPosition().getY() <= loc.getY()[i + 1]) {
                    if (getPosition().getZ() == height) {
                        if (getId() == npcId) {
                            ii++;
                            System.out.println(ii);
                        }
                    }
                }
            }
        }
    }


}
