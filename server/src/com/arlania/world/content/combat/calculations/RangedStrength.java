package com.arlania.world.content.combat.calculations;

import com.arlania.world.content.combat.range.CombatRangedAmmo;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.collect.Multiset;

public class RangedStrength {
    public static int getRangedStr(Player plr) {
        int rangedStrength = plr.getEquipment().rangeDamageBonus(plr);


        if (plr.getEquipment().usingBlowpipe(plr)) {

            int ammoType = 0;
            int ammoQty = 0;

            for (Multiset.Entry<Integer> dartItem : plr.getBlowpipeLoading().getContents().entrySet()) {
                ammoType = dartItem.getElement();
                //ammoQty = new Integer(player.getBlowpipeLoading().getContents().count(dartItem.getElement));
            }

            switch (ammoType) {

                case 806:
                    rangedStrength += CombatRangedAmmo.AmmunitionData.BRONZE_DART.getStrength();
                    break;
                case 807:
                    rangedStrength += CombatRangedAmmo.AmmunitionData.IRON_DART.getStrength();
                    break;
                case 808:
                    rangedStrength += CombatRangedAmmo.AmmunitionData.STEEL_DART.getStrength();
                    break;
                case 809:
                    rangedStrength += CombatRangedAmmo.AmmunitionData.MITHRIL_DART.getStrength();
                    break;
                case 810:
                    rangedStrength += CombatRangedAmmo.AmmunitionData.ADAMANT_DART.getStrength();
                    break;
                case 811:
                    rangedStrength += CombatRangedAmmo.AmmunitionData.RUNE_DART.getStrength();
                    break;
                case 11230:
                    rangedStrength += CombatRangedAmmo.AmmunitionData.DRAGON_DART.getStrength();
                    break;
            }
        } else {
            if (plr.getRangedWeaponData() != null)
                rangedStrength += (CombatRangedAmmo.RangedWeaponData.getAmmunitionData(plr).getStrength());
        }

        return rangedStrength;
    }
}
