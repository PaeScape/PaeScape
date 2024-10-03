package com.arlania.world.content.seasonal;

import com.arlania.model.Position;
import com.arlania.world.content.seasonal.Interface.*;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.player.Player;

public class SeasonalTeleportHandler {

    public static void Teleport(Player player, Position checkPosition, TeleportType teleportType) {

        for (final Training.TrainingTeleports b : Training.TrainingTeleports.values()) {

            if (checkPosition == b.getPosition() && player.seasonalTrainingTeleports[b.getSeasonalUnlockId() - 1] == 1) {
                TeleportHandler.teleportPlayer(player, checkPosition, teleportType);
                return;
            } else if (checkPosition == b.getPosition() && player.seasonalTrainingTeleports[b.getSeasonalUnlockId() - 1] == 0) {
                player.getPacketSender().sendMessage("@red@You must unlock this teleport through the Seasonal interface!");
                return;
            }

        }

        for (final Dungeons.DungeonTeleports c : Dungeons.DungeonTeleports.values()) {

            if (checkPosition == c.getPosition() && player.seasonalDungeonTeleports[c.getSeasonalUnlockId() - 1] == 1) {
                TeleportHandler.teleportPlayer(player, checkPosition, teleportType);
                return;
            } else if (checkPosition == c.getPosition() && player.seasonalDungeonTeleports[c.getSeasonalUnlockId() - 1] == 0) {
                player.getPacketSender().sendMessage("@red@You must unlock this teleport through the Seasonal interface!");
                return;
            }

        }

        for (final Bosses.BossesTeleports d : Bosses.BossesTeleports.values()) {

            if (checkPosition == d.getPosition() && player.seasonalBossTeleports[d.getSeasonalUnlockId() - 1] == 1) {
                TeleportHandler.teleportPlayer(player, checkPosition, teleportType);
                return;
            } else if (checkPosition == d.getPosition() && player.seasonalBossTeleports[d.getSeasonalUnlockId() - 1] == 0) {
                player.getPacketSender().sendMessage("@red@You must unlock this teleport through the Seasonal interface!");
                return;
            }

        }

        for (final Minigames.MinigamesTeleports e : Minigames.MinigamesTeleports.values()) {

            if (checkPosition == e.getPosition() && player.seasonalMinigameTeleports[e.getSeasonalUnlockId() - 1] == 1) {
                TeleportHandler.teleportPlayer(player, checkPosition, teleportType);
                return;
            } else if (checkPosition == e.getPosition() && player.seasonalMinigameTeleports[e.getSeasonalUnlockId() - 1] == 0) {
                player.getPacketSender().sendMessage("@red@You must unlock this teleport through the Seasonal interface!");
                return;
            }

        }

        for (final Raids.RaidsTeleports f : Raids.RaidsTeleports.values()) {

            if (checkPosition == f.getPosition() && player.seasonalRaidsTeleports[f.getSeasonalUnlockId() - 1] == 1) {
                TeleportHandler.teleportPlayer(player, checkPosition, teleportType);
                return;
            } else if (checkPosition == f.getPosition() && player.seasonalRaidsTeleports[f.getSeasonalUnlockId() - 1] == 0) {
                player.getPacketSender().sendMessage("@red@You must unlock this teleport through the Seasonal interface!");
                return;
            }

        }

        TeleportHandler.teleportPlayer(player, checkPosition, teleportType);
        //player.getPacketSender().sendMessage("@red@You must unlock this teleport through the Seasonal interface!");
    }

}
