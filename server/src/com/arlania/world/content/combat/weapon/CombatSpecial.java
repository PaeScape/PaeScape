package com.arlania.world.content.combat.weapon;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.PlayerSpecialAmountTask;
import com.arlania.engine.task.impl.StaffOfLightSpecialAttackTask;
import com.arlania.model.*;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.definitions.WeaponInterfaces.WeaponInterface;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.Consumables;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.minigames.impl.Dueling;
import com.arlania.world.content.minigames.impl.Dueling.DuelRule;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.PerkHandler;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;

/**
 * Holds constants that hold data for all of the special attacks that can be
 * used.
 *
 * @author lare96
 */
public enum CombatSpecial {

    DRAGON_DAGGER(new int[]{1215, 1231, 5680, 5698}, 25, 1.05, 1.05, CombatType.MELEE, WeaponInterface.DAGGER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1062));
            player.performGraphic(new Graphic(252, GraphicHeight.HIGH));

            return new CombatContainer(player, target, 2, CombatType.MELEE,
                    true);
        }
    },
    FROSTBITE_DAGGER(new int[]{17275}, 25, 1.1, 1.1, CombatType.MELEE, WeaponInterface.DAGGER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1062));
            player.performGraphic(new Graphic(252, GraphicHeight.HIGH));

            return new CombatContainer(player, target, 2, CombatType.MELEE,
                    true) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    target.performGraphic(new Graphic(369));
                    if (!target.isFrozen()) {
                        if (target.getSize() == 1) {
                            target.getMovementQueue().freeze(15);
                        }
                    }
                }
            };
        }
    },
    ABYSSAL_DAGGER(new int[]{13047}, 25, 1.10, 1.10, CombatType.MELEE, WeaponInterface.DAGGER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1062));
            player.performGraphic(new Graphic(252, GraphicHeight.HIGH));

            return new CombatContainer(player, target, 2, CombatType.MELEE,
                    true);
        }
    },
    ABYSSAL_BLUDGEON(new int[]{13045}, 50, 1.15, 1.30, CombatType.MELEE, WeaponInterface.WARHAMMER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(2661));
            target.performGraphic(new Graphic(2233, GraphicHeight.MIDDLE));
            player.poisonVictim(target, CombatType.MELEE);
            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    true);
        }
    },
    SCYTHE_OF_VITUR(new int[]{21006}, 50, 1.5, 1.5, CombatType.MELEE, WeaponInterface.HALBERD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(2661));
            target.performGraphic(new Graphic(2233, GraphicHeight.MIDDLE));

            return new CombatContainer(player, target, 1, CombatType.MELEE, false) {
                @Override
                public void onHit(int dmg, boolean accurate) {
                    if (accurate) {
                        int damageHeal = (int) (dmg * 0.20);
                        int damagePrayerHeal = (int) (dmg * 0.1);
                        if (player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) < player.maxHealth() && player.healingEffects) {
                            int level = player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal > player.maxHealth() ? player.maxHealth() : player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal;
                            player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, level);
                        }
                        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager().getMaxLevel(Skill.PRAYER)) {
                            int level = player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal > player.getSkillManager().getMaxLevel(Skill.PRAYER) ? player.getSkillManager().getMaxLevel(Skill.PRAYER) : player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal;
                            player.getSkillManager().setCurrentLevel(Skill.PRAYER, level);
                        }
                    }
                }
            };
        }
    },
    KORASIS_SWORD(new int[]{19780}, 60, 1.5, 10, CombatType.MELEE, WeaponInterface.SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {

            player.performAnimation(new Animation(14788));
            player.performGraphic(new Graphic(1729));

            return new CombatContainer(player, target, 1, 1, CombatType.MAGIC, true) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    target.performGraphic(new Graphic(1730));
                }
            };
        }
    },
    MORRIGANS_JAVELIN(new int[]{13879}, 50, 1.25, 1.30, CombatType.RANGED, WeaponInterface.JAVELIN) {
        @Override
        public CombatContainer container(Player player, Character target) {

            player.performAnimation(new Animation(10501));
            player.performGraphic(new Graphic(1836));

            return new CombatContainer(player, target, 1, CombatType.RANGED, true);
        }
    },
    MORRIGANS_THROWNAXE(new int[]{13883}, 50, 1.25, 1.30, CombatType.RANGED, WeaponInterface.THROWNAXE) {
        @Override
        public CombatContainer container(Player player, Character target) {

            player.performAnimation(new Animation(10504));
            player.performGraphic(new Graphic(1838));

            return new CombatContainer(player, target, 1, CombatType.RANGED, true);
        }
    },
    ARMADYL_CROSSBOW(new int[]{13051}, 40, 1.1, 2, CombatType.RANGED, WeaponInterface.CROSSBOW) {
        @Override
        public CombatContainer container(Player player, Character target) {

            return new CombatContainer(player, target, 2, CombatType.RANGED, true);
        }
    },
    GRANITE_MAUL(new int[]{4153, 18903, 221742, 221646}, 50, 1.10, 1.10, CombatType.MELEE, WeaponInterface.WARHAMMER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1667));
            player.performGraphic(new Graphic(337, GraphicHeight.HIGH));
            player.getCombatBuilder().setAttackTimer(1);
            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    true);
        }
    },
    ABYSSAL_WHIP(new int[]{4151, 15441, 15442, 15443, 15444, 20061}, 50, 1.1, 1.5, CombatType.MELEE, WeaponInterface.WHIP) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1658));
            target.performGraphic(new Graphic(341, GraphicHeight.HIGH));
            if (target.isPlayer()) {
                Player p = (Player) target;
                int totalRunEnergy = p.getRunEnergy() - 25;
                if (totalRunEnergy < 0) {
                    totalRunEnergy = 0;
                }
                p.setRunEnergy(totalRunEnergy);
            }
            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    false);
        }
    },
    VINE_WHIP(new int[]{4705}, 60, 1.1, 1.5, CombatType.MELEE, WeaponInterface.WHIP) {
        @Override
        public CombatContainer container(Player player, Character target) {
            int amountToDrain = RandomUtility.inclusiveRandom(500);
            player.performAnimation(new Animation(1658));
            target.performGraphic(new Graphic(436, GraphicHeight.HIGH));
            if (target.isPlayer()) {
                Player p = (Player) target;
                p.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getCurrentLevel(Skill.PRAYER) - amountToDrain);
                p.getPacketSender().sendMessage("Your prayer has been drained " + amountToDrain + " prayer points from the vine whip spec!");

            }
            player.getPacketSender().sendMessage("You drain your opponents prayer by a random amount of " + amountToDrain + " prayer points");

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    false);
        }
    },
    ZARYTE(new int[]{4706, 18971}, 50, 1.50, 2.87, CombatType.RANGED, WeaponInterface.SHORTBOW) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(426));

            TaskManager.submit(new Task(1, player, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 0) {
                        new Projectile(player, target, 311, 44, 3, 32, 31, 0).sendProjectile();
                        new Projectile(player, target, 311, 60, 3, 32, 31, 0).sendProjectile();
                    } else if (tick >= 1) {
                        target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
                        this.stop();
                    }
                    tick++;
                }
            });

            return new CombatContainer(player, target, 2, CombatType.RANGED,
                    true);
        }
    },
    DRAGON_LONGSWORD(new int[]{1305}, 25, 1.15, 1.20, CombatType.MELEE, WeaponInterface.LONGSWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1058));
            player.performGraphic(new Graphic(248, GraphicHeight.HIGH));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    true);
        }
    },
    BARRELSCHEST_ANCHOR(new int[]{10887}, 50, 1.25, 1.25, CombatType.MELEE, WeaponInterface.WARHAMMER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(5870));
            player.performGraphic(new Graphic(1027, GraphicHeight.MIDDLE));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    true);
        }
    },
    SARADOMIN_SWORD(new int[]{11730, 13461}, 100, 1.25, 1.2, CombatType.MELEE, WeaponInterface.TWO_HANDED_SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {

            player.performAnimation(new Animation(11993));
            player.setEntityInteraction(target);

            return new CombatContainer(player, target, 2, CombatType.MAGIC, true) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    target.performGraphic(new Graphic(1194));
                }
            };
        }
    },
    VESTAS_LONGSWORD(new int[]{13899}, 25, 1.25, 2, CombatType.MELEE, WeaponInterface.LONGSWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(10502));

            return new CombatContainer(player, target, 1, CombatType.MELEE, true);
        }
    },
    DRAGON_WARHAMMER(new int[]{20555}, 50, 1.1, 1.50, CombatType.MELEE, WeaponInterface.WARHAMMER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(2066));
            target.performGraphic(new Graphic(437));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    false) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    if (target != null && target.isPlayer() && accurate) {
                        int skillDrain = 1;
                        int damageDrain = (int) (damage * 0.1);
                        if (damageDrain < 0) {
                            return;
                        }
                        ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), player.getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) - damageDrain);
                        if (((Player) target).getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) < 1) {
                            ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), 1);
                        }
                        player.getPacketSender().sendMessage("You've drained " + ((Player) target).getUsername() + "'s " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level by " + damageDrain + ".");
                        ((Player) target).getPacketSender().sendMessage("Your " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level has been drained.");


                    }
                    if (target != null && target.isNpc()) {
                        NPC npc = (NPC) target;

                        npc.getDefenceWeakened()[1] = true;
                        player.getPacketSender().sendMessage("You've lowered it's defences. You will now have an increased max hit!");
                    }
                }
            };
        }
    },
    TOXIC_BLOWPIPE(new int[]{12926}, 50, 1.2, 2, CombatType.RANGED, WeaponInterface.DART) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(5061));
            player.performGraphic(new Graphic(1123));
            return new CombatContainer(player, target, 1, CombatType.RANGED, false) {
                @Override
                public void onHit(int dmg, boolean accurate) {
                    if (accurate) {
                        int damageHeal = (int) (dmg * 0.5);
                        if (player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) < player.maxHealth()) {
                            int level = player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal > player.maxHealth() ? player.maxHealth() : player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal;
                            player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, level);
                        }
                    }
                }
            };
        }
    },
    MAGMA_BLOWPIPE(new int[]{12927}, 50, 1.2, 2, CombatType.RANGED, WeaponInterface.DART) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(5061));
            player.performGraphic(new Graphic(1123));
            return new CombatContainer(player, target, 2, CombatType.RANGED, true);
        }
    },
    STATIUS_WARHAMMER(new int[]{13902}, 35, 1.2, 1.45, CombatType.MELEE, WeaponInterface.WARHAMMER) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(2066));
            target.performGraphic(new Graphic(437));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    false) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    if (target != null && target.isPlayer() && accurate) {
                        int skillDrain = 1;
                        int damageDrain = (int) (damage * 0.1);
                        if (damageDrain < 0) {
                            return;
                        }
                        ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), player.getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) - damageDrain);
                        if (((Player) target).getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) < 1) {
                            ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), 1);
                        }
                        player.getPacketSender().sendMessage("You've drained " + ((Player) target).getUsername() + "'s " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level by " + damageDrain + ".");
                        ((Player) target).getPacketSender().sendMessage("Your " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level has been drained.");


                    }
                    if (target != null && target.isNpc()) {
                        NPC npc = (NPC) target;

                        npc.getDefenceWeakened()[1] = true;
                        player.getPacketSender().sendMessage("You've lowered it's defences. You will now have an increased max hit!");
                    }
                }
            };
        }
    },
    MAGIC_SHORTBOW(new int[]{861, 6724}, 50, 1.2, 1.5, CombatType.RANGED, WeaponInterface.SHORTBOW) {
        @Override
        public CombatContainer container(Player player, Character target) {

            player.performAnimation(new Animation(1074));
            player.performGraphic(new Graphic(250, GraphicHeight.HIGH));
            new Projectile(player, target, 249, 44, 3, 43, 31, 0).sendProjectile();

            TaskManager.submit(new Task(1, player, false) {
                @Override
                public void execute() {

                    new Projectile(player, target, 249, 44, 3, 43, 31, 0).sendProjectile();
                    this.stop();
                }
            });

            return new CombatContainer(player, target, 2, CombatType.RANGED,
                    true);
        }
    },
    MAGIC_LONGBOW(new int[]{859}, 35, 1.20, 5, CombatType.RANGED, WeaponInterface.LONGBOW) {
        @Override
        public CombatContainer container(Player player, Character target) {

            player.performAnimation(new Animation(426));
            player.performGraphic(new Graphic(250, GraphicHeight.HIGH));
            new Projectile(player, target, 249, 44, 3, 43, 31, 0).sendProjectile();

            return new CombatContainer(player, target, 1, CombatType.RANGED,
                    true);
        }
    },
    DARK_BOW(new int[]{11235, 15701, 15702, 15703, 15704}, 55, 1.4, 1.5, CombatType.RANGED, WeaponInterface.LONGBOW) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(426));

            TaskManager.submit(new Task(1, player, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 0) {
                        new Projectile(player, target, 1099, 44, 3, 43, 31, 0).sendProjectile();
                        new Projectile(player, target, 1099, 60, 3, 43, 31, 0).sendProjectile();
                    } else if (tick >= 1) {
                        target.performGraphic(new Graphic(1100, GraphicHeight.HIGH));
                        this.stop();
                    }
                    tick++;
                }
            });

            return new CombatContainer(player, target, 2, CombatType.RANGED,
                    true);
        }
    },
    HAND_CANNON(new int[]{15241}, 60, 1.4, 1.5, CombatType.RANGED, WeaponInterface.SHORTBOW) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(12175));
            player.getCombatBuilder().setAttackTimer(8);

            TaskManager.submit(new Task(1, player, false) {
                @Override
                public void execute() {
                    player.performGraphic(new Graphic(2141));
                    new Projectile(player, target, 2143, 44, 3, 43, 31, 0).sendProjectile();
                    new CombatHitTask(player.getCombatBuilder(), new CombatContainer(player, target, 1, CombatType.RANGED, true)).handleAttack();
                    player.getCombatBuilder().setAttackTimer(3);
                    stop();
                }
            });
            return new CombatContainer(player, target, 1, 1, CombatType.RANGED,
                    true);
        }
    },
    DRAGON_BATTLEAXE(new int[]{1377}, 100, 1, 1, CombatType.MELEE, WeaponInterface.BATTLEAXE) {
        @Override
        public void onActivation(Player player, Character target) {
            player.performGraphic(new Graphic(246, GraphicHeight.LOW));
            player.performAnimation(new Animation(1056));
            player.forceChat("Raarrrrrgggggghhhhhhh!");
            CombatSpecial.drain(player, DRAGON_BATTLEAXE.drainAmount);
            Consumables.drinkStatPotion(player, -1, -1, -1, Skill.STRENGTH.ordinal(), true);
            player.getSkillManager().setCurrentLevel(Skill.ATTACK, player.getSkillManager().getCurrentLevel(Skill.ATTACK) - 7);
            player.getCombatBuilder().cooldown(true);
        }

        @Override
        public CombatContainer container(Player player, Character target) {
            throw new UnsupportedOperationException(
                    "Dragon battleaxe does not have a special attack!");
        }
    },
    HARMONISED_NIGHTMARE_STAFF(new int[]{20569}, 100, 1, 1, CombatType.MELEE, WeaponInterface.LONGSWORD) {
        @Override
        public void onActivation(Player player, Character target) {
            player.performGraphic(new Graphic(1518, GraphicHeight.HIGH));
            CombatSpecial.drain(player, HARMONISED_NIGHTMARE_STAFF.drainAmount);
            player.Harmonised = true;
        }

        @Override
        public CombatContainer container(Player player, Character target) {
            throw new UnsupportedOperationException(
                    "Dragon battleaxe does not have a special attack!");
        }
    },
    VOLATILE_NIGHTMARE_STAFF(new int[]{20570}, 100, 1, 1, CombatType.MELEE, WeaponInterface.LONGSWORD) {
        @Override
        public void onActivation(Player player, Character target) {
            player.performGraphic(new Graphic(531, GraphicHeight.LOW));
            CombatSpecial.drain(player, VOLATILE_NIGHTMARE_STAFF.drainAmount);
            player.Volatile = true;
        }

        @Override
        public CombatContainer container(Player player, Character target) {
            throw new UnsupportedOperationException(
                    "Dragon battleaxe does not have a special attack!");
        }
    },
    ELDRITCH_NIGHTMARE_STAFF(new int[]{20571}, 100, 1, 1, CombatType.MELEE, WeaponInterface.LONGSWORD) {
        @Override
        public void onActivation(Player player, Character target) {
            player.performGraphic(new Graphic(1590, GraphicHeight.HIGH));
            CombatSpecial.drain(player, ELDRITCH_NIGHTMARE_STAFF.drainAmount);
            player.Eldritch = true;
        }

        @Override
        public CombatContainer container(Player player, Character target) {
            throw new UnsupportedOperationException(
                    "Dragon battleaxe does not have a special attack!");
        }
    },
    STAFF_OF_LIGHT(new int[]{14004, 14005, 14006, 14007, 15486}, 100, 1, 1, CombatType.MELEE, WeaponInterface.LONGSWORD) {
        @Override
        public void onActivation(Player player, Character target) {
            player.performGraphic(new Graphic(1958));
            player.performAnimation(new Animation(10516));
            CombatSpecial.drain(player, STAFF_OF_LIGHT.drainAmount);
            player.setStaffOfLightEffect(200);
            TaskManager.submit(new StaffOfLightSpecialAttackTask(player));
            player.getPacketSender().sendMessage("You are shielded by the spirits of the Staff of light!");
            player.getCombatBuilder().cooldown(true);
        }

        @Override
        public CombatContainer container(Player player, Character target) {
            throw new UnsupportedOperationException(
                    "Dragon battleaxe does not have a special attack!");
        }
    },
    DRAGON_SPEAR(new int[]{1249, 1263, 5716, 5730}, 25, 1, 1, CombatType.MELEE, WeaponInterface.SPEAR) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1064));
            player.performGraphic(new Graphic(253));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    true) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    if (target.isPlayer()) {
                        int moveX = target.getPosition().getX() - player.getPosition().getX();
                        int moveY = target.getPosition().getY() - player.getPosition().getY();
                        if (moveX > 0) {
                            moveX = 1;
                        } else if (moveX < 0) {
                            moveX = -1;
                        }
                        if (moveY > 0) {
                            moveY = 1;
                        } else if (moveY < 0) {
                            moveY = -1;
                        }
                        if (target.getMovementQueue().canWalk(moveX, moveY)) {
                            target.setEntityInteraction(player);
                            target.getMovementQueue().reset();
                            target.getMovementQueue().walkStep(moveX, moveY);
                        }
                    }
                    target.performGraphic(new Graphic(254, GraphicHeight.HIGH));
                    TaskManager.submit(new Task(1, false) {
                        @Override
                        public void execute() {
                            target.getMovementQueue().freeze(6);
                            stop();
                        }
                    });
                }
            };
        }
    },
    DRAGON_MACE(new int[]{1434}, 25, 1.45, 1.25, CombatType.MELEE, WeaponInterface.MACE) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1060));
            player.performGraphic(new Graphic(251, GraphicHeight.HIGH));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    true);
        }
    },
    DRAGON_SCIMITAR(new int[]{4587}, 55, 1.1, 1.1, CombatType.MELEE, WeaponInterface.SCIMITAR) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1872));
            player.performGraphic(new Graphic(347, GraphicHeight.HIGH));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    true);
        }
    },
    DRAGON_2H_SWORD(new int[]{7158}, 60, 1.25, 1, CombatType.MELEE, WeaponInterface.TWO_HANDED_SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(3157));
            player.performGraphic(new Graphic(559));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    false) {
                @Override
                public void onHit(int damage, boolean accurate) {
                            /*if (Location.inMulti(player)) {
                             List<GameCharacter> localEntities;

                             if (target.isPlayer()) {
                             localEntities = Optional.of(player.getLocalPlayers());
                             } else if (target.isNpc()) {
                             localEntities = Optional.of(player.getLocalNpcs());
                             }

                             for (GameCharacter e : localEntities.get()) {
                             if (e == null) {
                             continue;
                             }

                             if (e.getPosition().isWithinDistance(
                             target.getPosition(), 1) && !e.equals(target) && !e.equals(player) && e.getConstitution() > 0 && !e.isDead()) {
                             Hit hit = CombatFactory.getHit(player, target,
                             CombatType.MELEE);
                             e.dealDamage(hit);
                             e.getCombatBuilder().addDamage(player,
                             hit.getDamage());
                             }
                             }
                             }*/
                }
            };
        }
    },
    DRAGON_HALBERD(new int[]{3204}, 30, 1.07, 1.08, CombatType.MELEE, WeaponInterface.HALBERD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(1203));
            player.performGraphic(new Graphic(282, GraphicHeight.HIGH));

            return new CombatContainer(player, target, 2, CombatType.MELEE,
                    true);
        }
    },
    ARMADYL_GODSWORD(new int[]{11694, 13450, 220368}, 50, 1.25, 1.25, CombatType.MELEE, WeaponInterface.TWO_HANDED_SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(11989));
            player.performGraphic(new Graphic(2113));

            return new CombatContainer(player, target, 1, CombatType.MELEE, true);
        }
    },
    ZAMORAK_GODSWORD(new int[]{11700, 13453, 220374}, 50, 1.1, 1.25, CombatType.MELEE, WeaponInterface.TWO_HANDED_SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(7070));

            return new CombatContainer(player, target, 1, CombatType.MELEE, true) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    if (accurate) {
                        target.performGraphic(new Graphic(1221));
                        if (!target.isFrozen()) {
                            if (target.getSize() == 1) {
                                target.getMovementQueue().freeze(15);
                            }
                        }
                    }
                }
            };
        }
    },
    BANDOS_GODSWORD(new int[]{11696, 13451, 220370}, 50, 1.1, 1.25, CombatType.MELEE, WeaponInterface.TWO_HANDED_SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(11991));
            player.performGraphic(new Graphic(2114));

            return new CombatContainer(player, target, 1, CombatType.MELEE,
                    false) {
                @Override
                public void onHit(int damage, boolean accurate) {
                    if (target != null && target.isPlayer() && accurate) {
                        int skillDrain = 1;
                        int damageDrain = (int) (damage * 0.1);
                        if (damageDrain < 0) {
                            return;
                        }
                        ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), player.getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) - damageDrain);
                        if (((Player) target).getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) < 1) {
                            ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), 1);
                        }
                        player.getPacketSender().sendMessage("You've drained " + ((Player) target).getUsername() + "'s " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level by " + damageDrain + ".");
                        ((Player) target).getPacketSender().sendMessage("Your " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level has been drained.");
                    }
                    if (target != null && target.isNpc()) {
                        NPC npc = (NPC) target;

                        npc.getDefenceWeakened()[2] = true;
                        player.getPacketSender().sendMessage("You've lowered it's defences. You will now have an increased max hit!");
                    }
                }
            };
        }
    },
    SARADOMIN_GODSWORD(new int[]{11698, 13452, 220372}, 50, 1.1, 1.25, CombatType.MELEE, WeaponInterface.TWO_HANDED_SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(7071));
            player.performGraphic(new Graphic(1220));

            return new CombatContainer(player, target, 1, CombatType.MELEE, false) {
                @Override
                public void onHit(int dmg, boolean accurate) {
                    if (accurate) {
                        int damageHeal = (int) (dmg * 0.5);
                        int damagePrayerHeal = (int) (dmg * 0.25);
                        if (player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) < player.maxHealth()) {
                            int level = player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal > player.maxHealth() ? player.maxHealth() : player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal;
                            player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, level);
                        }
                        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager().getMaxLevel(Skill.PRAYER)) {
                            int level = player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal > player.getSkillManager().getMaxLevel(Skill.PRAYER) ? player.getSkillManager().getMaxLevel(Skill.PRAYER) : player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal;
                            player.getSkillManager().setCurrentLevel(Skill.PRAYER, level);
                        }
                    }
                }
            };
        }
    },
    ZAROS_GODSWORD(new int[]{21035}, 50, 1.25, 1.25, CombatType.MELEE, WeaponInterface.TWO_HANDED_SWORD) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(11993));

            return new CombatContainer(player, target, 1, CombatType.MELEE, false) {
                @Override
                public void onHit(int dmg, boolean accurate) {
                    if (accurate) {
                        target.performGraphic(new Graphic(1221 + GameSettings.OSRS_GFX_OFFSET));
                        if (!target.isFrozen()) {
                            if (target.getSize() == 1) {
                                target.getMovementQueue().freeze(15);
                            }
                        }
                        int damageHeal = (int) (dmg * 0.5);
                        int damagePrayerHeal = (int) (dmg * 0.25);
                        if (player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) < player.maxHealth()) {
                            int level = player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal > player.maxHealth() ? player.maxHealth() : player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) + damageHeal;
                            player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, level);
                        }
                        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager().getMaxLevel(Skill.PRAYER)) {
                            int level = player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal > player.getSkillManager().getMaxLevel(Skill.PRAYER) ? player.getSkillManager().getMaxLevel(Skill.PRAYER) : player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal;
                            player.getSkillManager().setCurrentLevel(Skill.PRAYER, level);
                        }

                        if (target != null && target.isPlayer() && accurate) {
                            int skillDrain = 1;
                            int damageDrain = (int) (dmg * 0.1);
                            if (damageDrain < 0) {
                                return;
                            }
                            ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), player.getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) - damageDrain);
                            if (((Player) target).getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) < 1) {
                                ((Player) target).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), 1);
                            }
                            player.getPacketSender().sendMessage("You've drained " + ((Player) target).getUsername() + "'s " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level by " + damageDrain + ".");
                            ((Player) target).getPacketSender().sendMessage("Your " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level has been drained.");
                        }
                        if (target != null && target.isNpc()) {
                            NPC npc = (NPC) target;

                            npc.getDefenceWeakened()[2] = true;
                            player.getPacketSender().sendMessage("You've lowered it's defences. You will now have an increased max hit!");
                        }
                    }
                }
            };
        }
    },


    DRAGON_CLAWS(new int[]{14484, 14486, 224000}, 50, 1.25, 1.25, CombatType.MELEE, WeaponInterface.CLAWS) {
        @Override
        public CombatContainer container(Player player, Character target) {
            player.performAnimation(new Animation(10961));
            player.performGraphic(new Graphic(1950));

            return new CombatContainer(player, target, 4, CombatType.MELEE, true);
        }
    };

    /**
     * The weapon ID's that perform this special when activated.
     */
    private final int[] identifiers;

    /**
     * The amount of special energy this attack will drain.
     */
    private final int drainAmount;

    /**
     * The strength bonus when performing this special attack.
     */
    private final double strengthBonus;

    /**
     * The accuracy bonus when performing this special attack.
     */
    private final double accuracyBonus;

    /**
     * The combat type used when performing this special attack.
     */
    private final CombatType combatType;

    /**
     * The weapon interface used by the identifiers.
     */
    private final WeaponInterface weaponType;

    /**
     * Create a new {@link CombatSpecial}.
     *
     * @param identifers    the weapon ID's that perform this special when
     *                      activated.
     * @param drainAmount   the amount of special energy this attack will drain.
     * @param strengthBonus the strength bonus when performing this special
     *                      attack.
     * @param accuracyBonus the accuracy bonus when performing this special
     *                      attack.
     * @param combatType    the combat type used when performing this special
     *                      attack.
     * @param weaponType    the weapon interface used by the identifiers.
     */
    CombatSpecial(int[] identifiers, int drainAmount, double strengthBonus, double accuracyBonus, CombatType combatType,
                  WeaponInterface weaponType) {
        this.identifiers = identifiers;
        this.drainAmount = drainAmount;
        this.strengthBonus = strengthBonus;
        this.accuracyBonus = accuracyBonus;
        this.combatType = combatType;
        this.weaponType = weaponType;
    }

    /**
     * Drains the special bar for the argued {@link Player}.
     *
     * @param player the player who's special bar will be drained.
     * @param amount the amount of energy to drain from the special bar.
     */
    public static void drain(Player player, int amount) {

        int factor = 0;

        if (player.getLocation() == Locations.Location.WILDERNESS && PerkHandler.canUseWildernessPerks(player)) {
            factor = player.wildSpecialist * 5;
        } else if (PerkHandler.canUseNormalPerks(player) && player.Vampirism > 0) {
            factor = player.Vampirism * 5;
        }

        int newAmount = amount - factor;

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.SPECIALIZED) && PerkHandler.canUseNormalPerks(player)) {
            newAmount = 10;
        }

        if (newAmount < 10) {
            newAmount = 10;
        }

        if (player.getEquipment().contains(1377) ||
                player.getEquipment().contains(15486) ||
                player.getEquipment().contains(14004) ||
                player.getEquipment().contains(14005) ||
                player.getEquipment().contains(14006) ||
                player.getEquipment().contains(14007) ||
                player.getEquipment().contains(20568) ||
                player.getEquipment().contains(20569) ||
                player.getEquipment().contains(20570) ||
                player.getEquipment().contains(20571)) {
            if ((player.Vampirism > 0 && PerkHandler.canUseNormalPerks(player)) || (player.wildSpecialist > 0 && PerkHandler.canUseWildernessPerks(player) && player.getInteractingEntity().isNpc())) {
                player.decrementSpecialPercentage(newAmount);
            } else {
                player.decrementSpecialPercentage(amount);
            }
        } else {

            if ((player.Vampirism > 0 && PerkHandler.canUseNormalPerks(player)) || (player.wildSpecialist > 0 && PerkHandler.canUseWildernessPerks(player) && player.getInteractingEntity().isNpc())) {
                player.decrementSpecialPercentage(newAmount);
            } else {
                player.decrementSpecialPercentage(amount);
            }
        }

        player.getPacketSender().sendSpriteChange(41006, 945);
        player.setSpecialActivated(false);
        CombatSpecial.updateBar(player);
        if (!player.isRecoveringSpecialAttack()) {
            TaskManager.submit(new PlayerSpecialAmountTask(player));
        }
    }

    /**
     * Restores the special bar for the argued {@link Player}.
     *
     * @param player the player who's special bar will be restored.
     * @param amount the amount of energy to restore to the special bar.
     */
    public static void restore(Player player, int amount) {
        player.incrementSpecialPercentage(amount);
        CombatSpecial.updateBar(player);
    }

    /**
     * Updates the special bar with the amount of special energy the argued
     * {@link Player} has.
     *
     * @param player the player who's special bar will be updated.
     */
    public static void updateBar(Player player) {
        if (player.getWeapon().getSpecialBar() == -1 || player.getWeapon().getSpecialMeter() == -1) {
            return;
        }
        int specialCheck = 10;
        int specialBar = player.getWeapon().getSpecialMeter();
        int specialAmount = player.getSpecialPercentage() / 10;

        for (int i = 0; i < 10; i++) {
            player.getPacketSender().sendInterfaceComponentMoval(specialAmount >= specialCheck ? 500 : 0, 0, --specialBar);
            specialCheck--;
        }
        player.getPacketSender().updateSpecialAttackOrb().sendString(player.getWeapon().getSpecialMeter(), player.isSpecialActivated() ? ("@yel@ Special Attack (" + player.getSpecialPercentage() + "%)") : ("@bla@ Special Attack (" + player.getSpecialPercentage() + "%"));

    }

    /**
     * Assigns special bars to the attack style interface if needed.
     *
     * @param player the player to assign the special bar for.
     */
    public static void assign(Player player) {
        if (player.getWeapon().getSpecialBar() == -1) {
            //if(!player.isPerformingSpecialAttack()) {
            player.getPacketSender().sendSpriteChange(41006, 945);
            player.setSpecialActivated(false);
            player.setCombatSpecial(null);
            CombatSpecial.updateBar(player);
            //}

            return;
        }

        for (CombatSpecial c : CombatSpecial.values()) {
            if (player.getWeapon() == c.getWeaponType()) {
                if (Arrays.stream(c.getIdentifiers()).anyMatch(
                        id -> player.getEquipment().get(Equipment.WEAPON_SLOT).getId() == id)) {
                    player.getPacketSender().sendInterfaceDisplayState(player.getWeapon().getSpecialBar(), false);
                    player.setCombatSpecial(c);
                    return;
                }
            }
        }

        player.getPacketSender().sendInterfaceDisplayState(player.getWeapon().getSpecialBar(), true);
        player.setCombatSpecial(null);
    }

    public static void activate(Player player) {
        if (Dueling.checkRule(player, DuelRule.NO_SPECIAL_ATTACKS)) {
            player.getPacketSender().sendMessage("Special Attacks have been turned off in this duel.");
            return;
        }
        if (player.getCombatSpecial() == null) {
            return;
        }
        if (player.isSpecialActivated()) {
            player.getPacketSender().sendSpriteChange(41006, 945);
            player.setSpecialActivated(false);
            CombatSpecial.updateBar(player);
        } else {
            if (player.getSpecialPercentage() >= 10 && GlobalEventHandler.effectActive(GlobalEvent.Effect.SPECIALIZED) && PerkHandler.canUseNormalPerks(player)) {
            } else if (player.getSpecialPercentage() >= 20 && player.Vampirism >= 4 && PerkHandler.canUseNormalPerks(player)) {
            } else if (player.getSpecialPercentage() >= 25 && player.Vampirism >= 2 && PerkHandler.canUseNormalPerks(player)) {
            } else if (player.getSpecialPercentage() < player.getCombatSpecial().getDrainAmount()) {
                player.getPacketSender().sendMessage("You do not have enough special attack energy left!");
                return;
            }
            final CombatSpecial spec = player.getCombatSpecial();
            boolean instantSpecial = spec == CombatSpecial.GRANITE_MAUL || spec == CombatSpecial.DRAGON_BATTLEAXE || spec == CombatSpecial.STAFF_OF_LIGHT;
            boolean mageSpec = spec == CombatSpecial.ELDRITCH_NIGHTMARE_STAFF || spec == CombatSpecial.HARMONISED_NIGHTMARE_STAFF || spec == CombatSpecial.STAFF_OF_LIGHT || spec == CombatSpecial.VOLATILE_NIGHTMARE_STAFF;
            if (!mageSpec && player.isAutocast()) {
                // Autocasting.resetAutocast(player, true);
            } else if (spec == CombatSpecial.STAFF_OF_LIGHT && player.hasStaffOfLightEffect()) {
                player.getPacketSender().sendMessage("You are already being protected by the Staff of Light!");
                return;
            }
            player.getPacketSender().sendSpriteChange(41006, 946);
            player.setSpecialActivated(true);
            if (instantSpecial) {
                spec.onActivation(player, player.getCombatBuilder().getVictim());
                if (spec == CombatSpecial.GRANITE_MAUL && player.getCombatBuilder().isAttacking() && !player.getCombatBuilder().isCooldown()) {
                    player.getCombatBuilder().setAttackTimer(0);
                    player.getCombatBuilder().attack(player.getCombatBuilder().getVictim());
                    player.getCombatBuilder().instant();
                } else {
                    CombatSpecial.updateBar(player);
                }
            } else {
                CombatSpecial.updateBar(player);
                TaskManager.submit(new Task(1, false) {
                    @Override
                    public void execute() {
                        if (!player.isSpecialActivated()) {
                            this.stop();
                            return;
                        }
                        spec.onActivation(player, player.getCombatBuilder().getVictim());
                        this.stop();
                    }
                }.bind(player));
            }
        }
    }

    /**
     * Fired when the argued {@link Player} activates the special attack bar.
     *
     * @param player the player activating the special attack bar.
     * @param target the target when activating the special attack bar, will be
     *               <code>null</code> if the player is not in combat while activating the
     *               special bar.
     */
    public void onActivation(Player player, Character target) {

    }

    /**
     * Fired when the argued {@link Player} is about to attack the argued
     * target.
     *
     * @param player the player about to attack the target.
     * @param target the entity being attacked by the player.
     * @return the combat container for this combat hook.
     */
    public abstract CombatContainer container(Player player, Character target);

    /**
     * Gets the weapon ID's that perform this special when activated.
     *
     * @return the weapon ID's that perform this special when activated.
     */
    public int[] getIdentifiers() {
        return identifiers;
    }

    /**
     * Gets the amount of special energy this attack will drain.
     *
     * @return the amount of special energy this attack will drain.
     */
    public int getDrainAmount() {
        return drainAmount;
    }

    /**
     * Gets the strength bonus when performing this special attack.
     *
     * @return the strength bonus when performing this special attack.
     */
    public double getStrengthBonus() {
        return strengthBonus;
    }

    /**
     * Gets the accuracy bonus when performing this special attack.
     *
     * @return the accuracy bonus when performing this special attack.
     */
    public double getAccuracyBonus() {
        return accuracyBonus;
    }

    /**
     * Gets the combat type used when performing this special attack.
     *
     * @return the combat type used when performing this special attack.
     */
    public CombatType getCombatType() {
        return combatType;
    }

    /**
     * Gets the weapon interface used by the identifiers.
     *
     * @return the weapon interface used by the identifiers.
     */
    public WeaponInterface getWeaponType() {
        return weaponType;
    }
}
