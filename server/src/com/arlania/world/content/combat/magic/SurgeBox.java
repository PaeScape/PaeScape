package com.arlania.world.content.combat.magic;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Projectile;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.Optional;

/**
 * A {@link Spell} implementation used for combat related spells.
 *
 * @author lare96
 */
public class SurgeBox {

    public static void handleCast(Player plr) {
        
        switch (plr.getCurrentlyCasting().spellId()) {

            case 11831:
                if (plr.getSurgeboxCharges() < 1) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You have run out of charges in your Surgebox.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1183));
                }
                if (!plr.getEquipment().contains(19868)) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You do not have a Surgebox equipped.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1183));
                }
                break;
            case 11851:
                if (plr.getSurgeboxCharges() < 1) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You have run out of charges in your Surgebox.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1185));
                }
                if (!plr.getEquipment().contains(19868)) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You do not have a Surgebox equipped.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1185));
                }
                break;
            case 11871:
                if (plr.getSurgeboxCharges() < 1) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You have run out of charges in your Surgebox.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1187));
                }
                if (!plr.getEquipment().contains(19868)) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You do not have a Surgebox equipped.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1187));
                }
                break;
            case 11891:
                if (plr.getSurgeboxCharges() < 1) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You have run out of charges in your Surgebox.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1189));
                }
                if (!plr.getEquipment().contains(19868)) {
                    plr.getPacketSender().sendMessage("@red@Your autocast has been reset. You do not have a Surgebox equipped.");
                    plr.setAutocastSpell(CombatSpells.getSpell(1189));
                }
                break;
        }

        int runeReduction = 0;

        runeReduction = plr.Prophetic * 20;

        if ((plr.Prophetic == 0) || (plr.Prophetic >= 1 && RandomUtility.inclusiveRandom(100) >= runeReduction))
            plr.setSurgeboxCharges(plr.getSurgeboxCharges() - 1);


    }
}

