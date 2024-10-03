package com.arlania.world.content.combat.effect;

import com.arlania.model.container.impl.Equipment;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.entity.impl.player.Player;

public class EquipmentBonus {

    public static boolean wearingVoid(Player player, CombatType attackType) {
        int correctEquipment = 0;
        int helmet = attackType == CombatType.MAGIC ? MAGE_VOID_HELM :
                attackType == CombatType.RANGED ? RANGED_VOID_HELM : MELEE_VOID_HELM;
        for (int[] armour : VOID_ARMOUR) {
            if (player.getEquipment().getItems()[armour[0]].getId() == armour[1] ||
                    player.getEquipment().getItems()[armour[0]].getId() == ELITE_VOID_ARMOUR[0]) {
                correctEquipment++;
            }
        }
        if (player.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == VOID_KNIGHT_DEFLECTOR) {
            correctEquipment++;
        }
        return correctEquipment >= 3 && player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == helmet;
    }

    private static final int MAGE_VOID_HELM = 11663;

    private static final int RANGED_VOID_HELM = 11664;

    private static final int MELEE_VOID_HELM = 11665;

    private static final int VOID_KNIGHT_DEFLECTOR = 19712;

    public static final int[][] VOID_ARMOUR = {
            {Equipment.BODY_SLOT, 8839},
            {Equipment.LEG_SLOT, 8840},
            {Equipment.HANDS_SLOT, 8842}
    };

    public static final int[] ELITE_VOID_ARMOUR = {
            19785,
            19786,
            8842
    };
}
