package com.arlania.net.packet.impl;

import com.arlania.GameSettings;
import com.arlania.model.*;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.content.combat.magic.MagicSpells;
import com.arlania.world.content.combat.magic.Spell;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionMagicOnGroundItems;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionMagicOnItems;

/**
 * Handles magic on items.
 *
 * @author Gabriel Hannason
 */
public class MagicOnItemsPacketListener implements PacketListener {

    @SuppressWarnings("unused")
    @Override
    public void handleMessage(Player player, Packet packet) {
        if (packet.getOpcode() == MAGIC_ON_GROUNDITEMS) {
            final int itemY = packet.readLEShort();
            final int itemId = packet.readShort();
            final int itemX = packet.readLEShort();
            final int spellId = packet.readUnsignedShortA();
            final MagicSpells spell = MagicSpells.forSpellId(spellId);
            if (spell == null)
                return;
            player.getActionTracker().offer(new ActionMagicOnGroundItems(itemX, itemY, itemId, spellId));
            player.getMovementQueue().reset();
            //switch(spell) {}
        } else if (packet.getOpcode() == MAGIC_ON_ITEMS) {
            int slot = packet.readShort();
            int itemId = packet.readInt();
            int childId = packet.readShort();
            int spellId = packet.readShortA();
            if (!player.getClickDelay().elapsed(1300))
                return;
            if (slot < 0 || slot > player.getInventory().capacity())
                return;
            if (player.getInventory().getItems()[slot].getId() != itemId)
                return;
            Item item = new Item(itemId);
            MagicSpells magicSpell = MagicSpells.forSpellId(spellId);
            if (magicSpell == null)
                return;
            player.getActionTracker().offer(new ActionMagicOnItems(slot, itemId, childId, spellId));
            Spell spell = magicSpell.getSpell();
            switch (magicSpell) {
                case LOW_ALCHEMY:
                case HIGH_ALCHEMY:
                    if (!item.tradeable() || !item.sellable() || item.getId() == 995) {
                        player.getPacketSender().sendMessage("This spell can not be cast on this item.");
                        return;
                    }
                    if (spell == null || !spell.canCast(player, true))
                        return;
                    player.getInventory().delete(itemId, 1).add(995, (int) (item.getDefinition().getValue() * (0.6)));
                    player.performAnimation(new Animation(712));
                    player.performGraphic(new Graphic(magicSpell == MagicSpells.HIGH_ALCHEMY ? 113 : 112, GraphicHeight.LOW));
                    player.getSkillManager().addExperience(Skill.MAGIC, spell.baseExperience());
                    player.getPacketSender().sendTab(GameSettings.MAGIC_TAB);
                    break;
                case SUPERHEAT_ITEM:
                    for (int i = 0; i < ORE_DATA.length; i++) {
                        if (item.getId() == ORE_DATA[i][0]) {
                            if (player.getInventory().getAmount(ORE_DATA[i][2]) < ORE_DATA[i][3]) {
                                player.getPacketSender().sendMessage("You do not have enough " + new Item(ORE_DATA[i][2]).getDefinition().getName() + "s for this spell.");
                                return;
                            }
                            if (spell == null || !spell.canCast(player, true))
                                return;
                            player.getInventory().delete(item.getId(), 1);
                            for (int k = 0; k < ORE_DATA[i][3]; k++)
                                player.getInventory().delete(ORE_DATA[i][2], 1);
                            player.performAnimation(new Animation(725));
                            player.performGraphic(new Graphic(148, GraphicHeight.HIGH));
                            player.getInventory().add(ORE_DATA[i][4], 1);
                            player.getPacketSender().sendTab(GameSettings.MAGIC_TAB);
                            player.getSkillManager().addExperience(Skill.MAGIC, spell.baseExperience());
                            return;
                        }
                    }
                    player.getPacketSender().sendMessage("This spell can only be cast on Mining ores.");
                    break;
                case BAKE_PIE:
                    if (itemId == 2317 || itemId == 2319 || itemId == 2321) {
                        player.getSkillManager().addExperience(Skill.MAGIC, spell.baseExperience());
                        player.performAnimation(new Animation(4413));
                        player.performGraphic(new Graphic(746, GraphicHeight.HIGH));
                        player.getInventory().delete(item.getId(), 1);
                        player.getPacketSender().sendMessage("You bake the pie");
                        player.getInventory().add(itemId == 2317 ? 2323 : itemId == 2319 ? 2327 : itemId == 2321 ? 2325 : -1, 1);
                    } else
                        player.getPacketSender().sendMessage("This spell can only be cast on an uncooked pie.");
                    break;
                default:
                    break;
            }
            player.getClickDelay().reset();
            player.getInventory().refreshItems();
        }
    }

    final static int[][] ORE_DATA = {
            {436, 1, 438, 1, 2349, 53}, // TIN
            {438, 1, 436, 1, 2349, 53}, // COPPER
            {440, 1, -1, -1, 2351, 53}, // IRON ORE
            {442, 1, -1, -1, 2355, 53}, // SILVER ORE
            {444, 1, -1, -1, 2357, 23}, // GOLD BAR
            {447, 1, 453, 4, 2359, 30}, // MITHRIL ORE
            {449, 1, 453, 6, 2361, 38}, // ADDY ORE
            {451, 1, 453, 8, 2363, 50}, // RUNE ORE
    };

    public static final int MAGIC_ON_GROUNDITEMS = 181;
    public static final int MAGIC_ON_ITEMS = 237;
}
