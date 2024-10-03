package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

import java.text.NumberFormat;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class HUD {

    public static void showHUD(Player player) {

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        String color = player.hudColor;

        if (player.displayHUD) {
            player.getPacketSender().sendWalkableInterface(368, true);
            int displays = 369;

            if (player.hudOvl)
                player.getPacketSender().sendString(displays++, "@" + color + "@Overload: " + player.getOverloadPotionTimer()); //
            if (player.hudPoison)
                player.getPacketSender().sendString(displays++, "@" + color + "@Poison: " + player.getPoisonImmunity()); //
            if (player.hudFire)
                player.getPacketSender().sendString(displays++, "@" + color + "@Anti-Fire: " + player.getDragonfireImmunity()); //
            if (player.hudBonus)
                player.getPacketSender().sendString(displays++, "@" + color + "@Bonus Time: " + player.getBonusTime()); //
            if (player.hudSlayer)
                player.getPacketSender().sendString(displays++, "@" + color + "@" + player.getSlayer().getSlayerTask() + ": " + player.getSlayer().getAmountToSlay()); //
            if (player.hudSkiller)
                player.getPacketSender().sendString(displays++, "@" + color + "@" + player.getSkiller().getSkillerTask() + ": " + player.getSkiller().getAmountToSkill()); //
            if (player.hudCballs && player.getCannon() != null)
                player.getPacketSender().sendString(displays++, "@" + color + "@Cannonballs: " + player.getCannon().getCannonballs()); //
            if (player.hudBPexp)
                player.getPacketSender().sendString(displays++, "@" + color + "@Battle Pass XP: " + myFormat.format(player.bpExperience)); //
            if (player.hudBPkills)
                player.getPacketSender().sendString(displays++, "@" + color + "@Battle Pass Bosses: " + myFormat.format(player.bpBossKills)); //
            if (player.hudEPexp)
                player.getPacketSender().sendString(displays++, "@" + color + "@Event Pass XP: " + myFormat.format(player.epExperience)); //
            if (player.hudEPkills)
                player.getPacketSender().sendString(displays++, "@" + color + "@Event Pass Bosses: " + myFormat.format(player.epBossKills)); //
            if (player.hudPETimer)
                if (player.personalEvent) {

                    if (player.accelerateEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Accelerate Event: " + player.accelerateEventTimer);
                    else if (player.maxHitEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Max Hit Event:: " + player.maxHitEventTimer);
                    else if (player.accuracyEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Accuracy Event: " + player.accuracyEventTimer);
                    else if (player.loadedEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Loaded Event: " + player.loadedEventTimer);
                    else if (player.doubleExpEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Double XP Event: " + player.doubleExpEventTimer);
                    else if (player.doubleLoot)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Double Loot Event: " + player.doubleLootTimer);
                    else if (player.droprateEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Drop Rate Event: " + player.droprateEventTimer);
                    else if (player.doubleBossPointEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@2x Boss Point Event: " + player.doubleBossPointEventTimer);
                    else if (player.doubleSlayerPointsEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@2x Slayer Point Event: " + player.doubleSlayerPointsEventTimer);
                    else if (player.doubleSkillerPointsEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@2x Skiller Point Event: " + player.doubleSkillerPointsEventTimer);
                    else if (player.eventBoxEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Event Box Event: " + player.universalDropEventTimer);
                    else if (player.bossKillsEvent)
                        player.getPacketSender().sendString(displays++, "@" + color + "@Boss Kills Event: " + player.bossKillsEventTimer);

                } else
                    player.getPacketSender().sendString(displays++, "@" + color + "@No Active Personal Event");


            if (player.hudChoices < 5)
                player.getPacketSender().sendString(373, "");
            if (player.hudChoices < 4)
                player.getPacketSender().sendString(372, "");
            if (player.hudChoices < 3)
                player.getPacketSender().sendString(371, "");
            if (player.hudChoices < 2)
                player.getPacketSender().sendString(370, "");
            if (player.hudChoices < 1)
                player.getPacketSender().sendString(369, "");
        } else {
            player.getPacketSender().sendString(373, "");
            player.getPacketSender().sendString(372, "");
            player.getPacketSender().sendString(371, "");
            player.getPacketSender().sendString(370, "");
            player.getPacketSender().sendString(369, "");
        }
    }


}
