package com.arlania.world.content.combat.magic;

import com.arlania.model.Skill;
import com.arlania.world.entity.impl.player.Player;

public class Autocasting {

    private static final int[] spellIds = {1152, 1154, 1156, 1158, 1160, 1163, 1166, 1169,
            1171, 1172, 1175, 1177, 1181, 1183, 1185, 1188, 1189, 1539, 12037, 1190, 1191, 1192, 12939,
            12987, 12901, 12861, 12963, 13011, 13023, 12919, 12881, 12951, 12999, 12911,
            12871, 12975, 12929, 12891, 21744, 22168, 21745, 21746, 50163,
            50211, 50119, 50081, 50151, 50199, 50111, 50071, 50175, 50223,
            50129, 50091};

    public static boolean handleAutocast(final Player p, int actionButtonId) {
        switch (actionButtonId) {
            case 6666:
                resetAutocast(p, true);
                return true;
            case 6667:
                resetAutocast(p, false);
                return true;
        }
        for (int i = 0; i < spellIds.length; i++) {
            if (actionButtonId == spellIds[i]) {
                CombatSpell cbSpell = CombatSpells.getSpell(actionButtonId);
                if (cbSpell == null) {
                    p.getMovementQueue().reset();
                    return true;
                }
                if (cbSpell.levelRequired() > p.getSkillManager().getCurrentLevel(Skill.MAGIC)) {
                    p.getPacketSender().sendMessage("You need a Magic level of at least " + cbSpell.levelRequired() + " to cast this spell.");
                    resetAutocast(p, true);
                    return true;
                }

                cbSpell = handleSurgebox(p, cbSpell);

                p.setAutocast(true);
                p.setAutocastSpell(cbSpell);
                p.savedSpell = actionButtonId;
                p.setCastSpell(cbSpell);
                p.getPacketSender().sendAutocastId(p.getAutocastSpell().spellId());
                p.getPacketSender().sendConfig(108, 1);
                return true;
            }
        }
        return false;
    }

    public static CombatSpell handleSurgebox(Player p, CombatSpell cbSpell) {

        if (cbSpell.spellId() == 1183) {
            if (p.getSurgeboxCharges() > 0 && p.getEquipment().contains(19868)) {
                cbSpell = CombatSpells.getSpell(11831);
                p.getPacketSender().sendMessage("Your autocast spell is set to Wind Surge.");
            }
        }

        if (cbSpell.spellId() == 1185) {
            if (p.getSurgeboxCharges() > 0 && p.getEquipment().contains(19868)) {
                cbSpell = CombatSpells.getSpell(11851);
                p.getPacketSender().sendMessage("Your autocast spell is set to Water Surge.");
            }
        }

        if (cbSpell.spellId() == 1188) {
            if (p.getSurgeboxCharges() > 0 && p.getEquipment().contains(19868)) {
                cbSpell = CombatSpells.getSpell(11881);
                p.getPacketSender().sendMessage("Your autocast spell is set to Earth Surge.");
            }
        }

        if (cbSpell.spellId() == 1189) {
            if (p.getSurgeboxCharges() > 0 && p.getEquipment().contains(19868)) {
                cbSpell = CombatSpells.getSpell(11891);
                p.getPacketSender().sendMessage("Your autocast spell is set to Fire Surge.");
            }
        }

        return cbSpell;
    }

    public static void onLogin(Player player) {
        if (player.getAutocastSpell() == null || !player.isAutocast())
            resetAutocast(player, true);
        else {
            player.getPacketSender().sendAutocastId(player.getAutocastSpell().spellId());
        }
    }

    public static void resetAutocast(Player p, boolean clientReset) {
        if (clientReset)
            p.getPacketSender().sendAutocastId(-1);
        p.setAutocast(false);
        p.setAutocastSpell(null);
        p.setCastSpell(null);
        p.getPacketSender().sendConfig(108, 3);
    }

    public static void resetAutocastEquipment(Player p, boolean clientReset) {
        if (clientReset)
            p.getPacketSender().sendAutocastId(-1);
        p.setAutocast(false);
        p.setAutocastSpell(null);
        p.setCastSpell(null);
        p.getPacketSender().sendConfig(108, 3);
    }
}
