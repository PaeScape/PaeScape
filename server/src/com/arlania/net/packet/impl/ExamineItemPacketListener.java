package com.arlania.net.packet.impl;

import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.util.Misc;
import com.arlania.world.content.UniqueTables;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionExamineItem;

public class ExamineItemPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int item = packet.readInt();
        player.getActionTracker().offer(new ActionExamineItem(item));
        if (item == 995 || item == 18201) {
            player.getPacketSender().sendMessage("Mhmm... Shining coins...");
            return;
        }
        ItemDefinition itemDef = ItemDefinition.forId(item);
        if (itemDef != null) {
            String rareType = "";
            Item itemA = new Item(item);

            if (UniqueTables.isMasterUnique(player, itemA))
                rareType = "@red@[MASTER]@bla@ ";
            if (UniqueTables.isLegendaryUnique(player, itemA))
                rareType = "@red@[LEGENDARY]@bla@ ";
            if (UniqueTables.isHighUnique(player, itemA))
                rareType = "@red@[HIGH]@bla@ ";
            if (UniqueTables.isMediumUnique(player, itemA))
                rareType = "@red@[MEDIUM]@bla@ ";
            if (UniqueTables.isLowUnique(player, itemA))
                rareType = "@red@[LOW]@bla@ ";

            player.getPacketSender().sendMessage(rareType + itemDef.getDescription() + " @red@value: " + itemDef.getValue());
            for (Skill skill : Skill.values()) {
                if (itemDef.getRequirement()[skill.ordinal()] > player.getSkillManager().getMaxLevel(skill)) {
                    player.getPacketSender().sendMessage("@red@WARNING: You need " + (skill.getName().startsWith("a") || skill.getName().startsWith("e") || skill.getName().startsWith("i") || skill.getName().startsWith("o") || skill.getName().startsWith("u") ? "an " : "a ") + Misc.formatText(skill.getName()) + " level of at least " + itemDef.getRequirement()[skill.ordinal()] + " to wear this.");
                }
            }
        }
    }

}
