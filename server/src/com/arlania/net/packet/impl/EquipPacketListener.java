package com.arlania.net.packet.impl;

import com.arlania.model.Flag;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.container.impl.Inventory;
import com.arlania.model.definitions.WeaponAnimations;
import com.arlania.model.definitions.WeaponInterfaces;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.Misc;
import com.arlania.world.content.BonusManager;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.Sounds.Sound;
import com.arlania.world.content.combat.magic.Autocasting;
import com.arlania.world.content.combat.magic.CombatSpell;
import com.arlania.world.content.combat.magic.CombatSpells;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionEquip;

/**
 * This packet listener manages the equip action a player
 * executes when wielding or equipping an item.
 *
 * @author relex lawl
 */

public class EquipPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;
        int id = packet.readInt();
        int slot = packet.readShortA();
        int interfaceId = packet.readShortA();
        if (player.getInterfaceId() > 0 && player.getInterfaceId() != 21172 /* EQUIP SCREEN */) {
            player.getPacketSender().sendInterfaceRemoval();
            //return;
        }

        switch (interfaceId) {
            case Inventory.INTERFACE_ID:
                /*
                 * Making sure slot is valid.
                 */
                if (slot >= 0 && slot <= 28) {
                    Item item = player.getInventory().getItems()[slot].copy();
                    if (!player.getInventory().contains(item.getId()))
                        return;

                    /*
                     * Making sure item exists and that id is consistent.
                     */
                    if (item != null && id == item.getId()) {

                        if(item.getDefinition().getId() == 219476 && !player.getAchievementTracker().hasCompletedAll()) {
                            player.getPacketSender().sendMessage("You must have all Achievements completed to wear this cape.");
                            return;
                        }

                        for (Skill skill : Skill.values()) {
                            if (skill == Skill.SKILLER)
                                continue;
                            if (item.getDefinition().getRequirement()[skill.ordinal()] > player.getSkillManager().getMaxLevel(skill)) {
                                StringBuilder vowel = new StringBuilder();
                                if (skill.getName().startsWith("a") || skill.getName().startsWith("e") || skill.getName().startsWith("i") || skill.getName().startsWith("o") || skill.getName().startsWith("u")) {
                                    vowel.append("an ");
                                } else {
                                    vowel.append("a ");
                                }
                                player.getPacketSender().sendMessage("You need " + vowel + Misc.formatText(skill.getName()) + " level of at least " + item.getDefinition().getRequirement()[skill.ordinal()] + " to wear this.");
                                return;
                            }
                        }
                        int equipmentSlot = item.getDefinition().getEquipmentSlot();
                        Item equipItem = player.getEquipment().forSlot(equipmentSlot).copy();

                        if (item.getDefinition().getName().contains("nguish"))
                            player.getPacketSender().sendMessage("@blu@Your Turmoil curse now gives the power of Anguish!");
                        if (item.getDefinition().getName().contains("ormented"))
                            player.getPacketSender().sendMessage("@blu@Your Turmoil curse now gives the power of Torment!");

                        if (player.hasStaffOfLightEffect() && equipItem.getDefinition().getName().toLowerCase().contains("staff of light")) {
                            player.setStaffOfLightEffect(-1);
                            player.getPacketSender().sendMessage("You feel the spirit of the Staff of Light begin to fade away...");
                        }
                        if (equipItem.getDefinition().isStackable() && equipItem.getId() == item.getId()) {
                            int amount = equipItem.getAmount() + item.getAmount() <= 0 ? Integer.MAX_VALUE : equipItem.getAmount() + item.getAmount();
                            player.getInventory().delete(item);
                            player.getEquipment().getItems()[equipmentSlot].setAmount(amount);
                            equipItem.setAmount(amount);
                            player.getEquipment().refreshItems();
                        } else {
                            if (item.getDefinition().isTwoHanded() && item.getDefinition().getEquipmentSlot() == Equipment.WEAPON_SLOT) {
                                int slotsNeeded = 0;
                                if (player.getEquipment().isSlotOccupied(Equipment.SHIELD_SLOT) && player.getEquipment().isSlotOccupied(Equipment.WEAPON_SLOT)) {
                                    slotsNeeded++;
                                }
                                if (player.getInventory().getFreeSlots() >= slotsNeeded) {
                                    int shield = player.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId();
                                    player.getInventory().delete(item);
                                    player.getEquipment().delete(shield, 1);
                                    player.getInventory().add(equipItem);
                                    player.getInventory().add(shield, 1);
                                    player.getEquipment().setItem(equipmentSlot, item);
                                } else {
                                    player.getInventory().full();
                                    return;
                                }
                            } else if (equipmentSlot == Equipment.SHIELD_SLOT && player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().isTwoHanded()) {
                                player.getInventory().setItem(slot, player.getEquipment().getItems()[Equipment.WEAPON_SLOT]);
                                player.getEquipment().setItem(Equipment.WEAPON_SLOT, new Item(-1));
                                player.getEquipment().setItem(Equipment.SHIELD_SLOT, item);
                                resetWeapon(player);
                            } else {
                                if (item.getDefinition().getEquipmentSlot() == equipItem.getDefinition().getEquipmentSlot() && equipItem.getId() != -1) {
                                    if (player.getInventory().contains(equipItem.getId())) {
                                        player.getInventory().delete(item);
                                        player.getInventory().add(equipItem);
                                    } else
                                        player.getInventory().setItem(slot, equipItem);
                                    player.getEquipment().setItem(equipmentSlot, item);
                                } else {
                                    player.getInventory().setItem(slot, new Item(-1, 0));
                                    player.getEquipment().setItem(item.getDefinition().getEquipmentSlot(), item);
                                }
                            }
                        }
                        if (equipmentSlot == Equipment.WEAPON_SLOT) {
                            resetWeapon(player);
                        } else if (equipmentSlot == Equipment.RING_SLOT && item.getId() == 2570) {
                            player.getPacketSender().sendMessage("<img=10> <col=996633>Warning! The Ring of Life special effect does not work in the Wilderness or").sendMessage("<col=996633> Duel Arena.");
                        }

                        if (player.getEquipment().get(Equipment.WEAPON_SLOT).getId() != 4153) {
                            player.getCombatBuilder().cooldown(false);
                        }
                        player.getActionTracker().offer(new ActionEquip(interfaceId, slot, id));
                        player.getCollectionLog().loadUpgrades();
                        player.setCastSpell(null);
                        BonusManager.update(player, 0, 0);
                        player.getEquipment().refreshItems();
                        player.getInventory().refreshItems();
                        player.getUpdateFlag().flag(Flag.APPEARANCE);
                        Sounds.sendSound(player, Sound.EQUIP_ITEM);
                    }
                }
                break;
        }
    }

    public static void resetWeapon(Player player) {
        Item weapon = player.getEquipment().get(Equipment.WEAPON_SLOT);
        WeaponInterfaces.assign(player, weapon);
        WeaponAnimations.assign(player, weapon);

        if (weapon.getDefinition().getName().toLowerCase().contains("trident") || weapon.getDefinition().getName().toLowerCase().contains("sanguinesti") ||
            weapon.getDefinition().getName().toLowerCase().contains("staff") || weapon.getDefinition().getName().toLowerCase().contains("wand") || weapon.getDefinition().getName().toLowerCase().contains("tumeken")) {
            equipStaff(player);
        }
        else {
            player.setAutocast(false);
            player.setAutocastSpell(null);
            player.setCastSpell(null);
        }

        player.setSpecialActivated(false);
        player.getPacketSender().sendSpriteChange(41006, 945);
        CombatSpecial.updateBar(player);
    }

    public static void equipStaff(Player player) {
        Item weapon = player.getEquipment().get(Equipment.WEAPON_SLOT);


        if (weapon.getDefinition().getName().toLowerCase().contains("catalytic")) {

            //Ice Barrage
            CombatSpell cbSpell = CombatSpells.getSpell(12891);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Ice Barrage has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("grave creepe")) {

            //Shadow Barrage
            CombatSpell cbSpell = CombatSpells.getSpell(13023);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Shadow Barrage has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("entgallow")) {

            //Smoke Barrage
            CombatSpell cbSpell = CombatSpells.getSpell(12975);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Smoke Barrage has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("corpsethorn")) {

            //Earth Wave
            CombatSpell cbSpell = CombatSpells.getSpell(1188);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Earth Wave has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("thigat")) {

            //Fire Blast
            CombatSpell cbSpell = CombatSpells.getSpell(1181);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Fire Blast has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("bovistrangler")) {

            //Earth Blast
            CombatSpell cbSpell = CombatSpells.getSpell(1177);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Earth Blast has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("spinebeam")) {

            //Fire Bolt
            CombatSpell cbSpell = CombatSpells.getSpell(1169);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Fire Bolt has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("utuku")) {

            //Earth Bolt
            CombatSpell cbSpell = CombatSpells.getSpell(1166);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Earth Bolt has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("blood spindle")) {

            //Fire Strike
            CombatSpell cbSpell = CombatSpells.getSpell(1158);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Fire Strike has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("seeping elm")) {

            //Earth Strike
            CombatSpell cbSpell = CombatSpells.getSpell(1156);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Earth Strike has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }

        else if (weapon.getDefinition().getName().toLowerCase().contains("tangle gum")) {

            //Wind Strike
            CombatSpell cbSpell = CombatSpells.getSpell(1152);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Wind Strike has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }


        else if (weapon.getDefinition().getName().contains("Trident") || weapon.getDefinition().getName().contains("trident")) {
            CombatSpell cbSpell = CombatSpells.getSpell(12446);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@blu@Trident null");
                //player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@blu@Trident is equipped, a special spell has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        } else if (weapon.getDefinition().getName().contains("Sanguin") || weapon.getDefinition().getName().contains("sanguin")) {
            CombatSpell cbSpell = CombatSpells.getSpell(12447);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@red@Sanguinesti null");
                player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@red@Sanguinesti Staff is equipped, a special spell has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        }  else if (weapon.getDefinition().getName().contains("Tumeken") || weapon.getDefinition().getName().contains("tumeken")) {
            CombatSpell cbSpell = CombatSpells.getSpell(12448);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@red@Tumeken null");
                player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@red@Tumeken's Shadow is equipped, a special spell has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        } else if (weapon.getDefinition().getName().contains("Zamorak staf")) {
            CombatSpell cbSpell = CombatSpells.getSpell(1192);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@red@Zamorak null");
                player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@red@Zamorak Staff is equipped, a special spell has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        } else if (weapon.getDefinition().getName().contains("Saradomin staf")) {
            CombatSpell cbSpell = CombatSpells.getSpell(1190);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@red@Saradomin null");
                player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@red@Saradomin Staff is equipped, a special spell has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        } else if (weapon.getDefinition().getName().contains("Guthix staf")) {
            CombatSpell cbSpell = CombatSpells.getSpell(1191);

            if (cbSpell == null) {
                player.getPacketSender().sendMessage("@red@Guthix null");
                player.getMovementQueue().reset();
            }
            player.getPacketSender().sendMessage("@red@Guthix Staff is equipped, a special spell has been set to autocast.");

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        } else if ((weapon.getDefinition().getName().contains("staff") || weapon.getDefinition().getName().contains("wand") ||
                weapon.getDefinition().getName().contains("Staff") || weapon.getDefinition().getName().contains("Wand"))
                && (player.savedSpell > 0)) {
            CombatSpell cbSpell = CombatSpells.getSpell(player.savedSpell);

            cbSpell = Autocasting.handleSurgebox(player, cbSpell);

            player.setAutocast(true);
            player.setAutocastSpell(cbSpell);
            player.setCastSpell(cbSpell);
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
            player.getPacketSender().sendConfig(108, 1);
        } else if (player.isAutocast()) {
            Autocasting.resetAutocastEquipment(player, true);
            player.getPacketSender().sendMessage("Autocast spell cleared.");
        }
    }

    public static final int OPCODE = 41;
}