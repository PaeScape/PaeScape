package com.arlania.world.content.combat.equipment;

import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class FullSets {

    public static boolean fullTorags(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals(
                "Torag the Corrupted")
                : ((Player) entity).getEquipment().containsAll(4745, 4749, 4751,
                4747);
    }

    public static boolean fullGuthans(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals(
                "Guthan the Infested")
                : ((Player) entity).getEquipment().containsAll(4724, 4728, 4730,
                4726);
    }

    public static boolean fullAhrims(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals(
                "Ahrim the Blighted")
                : ((Player) entity).getEquipment().containsAll(4708, 4712, 4714,
                4710);
    }

    public static boolean fullDharoks(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals(
                "Dharok the Wretched")
                : ((Player) entity).getEquipment().containsAll(4716, 4720, 4722,
                4718);
    }

    public static boolean fullVeracs(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals(
                "Verac the Defiled")
                : ((Player) entity).getEquipment().containsAll(4753, 4757, 4759,
                4755);
    }

    public static boolean fullKarils(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals(
                "Karil the Tainted")
                : ((Player) entity).getEquipment().containsAll(4732, 4736, 4738,
                4734);
    }

}
