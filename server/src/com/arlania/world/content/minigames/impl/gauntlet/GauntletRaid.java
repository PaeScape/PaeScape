package com.arlania.world.content.minigames.impl.gauntlet;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.GameObject;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.minigames.impl.strongholdraids.Rewards;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.regionalspawns.tirannwn.GauntletObjectData;
import org.javacord.api.entity.message.MessageBuilder;


public class GauntletRaid {

    public static void start(final Player p) {

        p.getPacketSender().sendInterfaceRemoval();
        p.setRegionInstance(null);
        p.getMovementQueue().reset();
        p.getClickDelay().reset();
        p.getSkillManager().stopSkilling();
        p.getPacketSender().sendClientRightClickRemoval();
        p.getPacketSender().sendCameraNeutrality();
        p.getPacketSender().sendInteractionOption("null", 2, true);

        p.sendMessage("@red@Welcome to the Gauntlet!");

        final int height = p.getIndex() * 4;
        p.instanceHeight = height;
        p.gauntletTick = 0;

        p.moveTo(new Position(1874, 5650, height));
        GauntletObjectData.load(p.instanceHeight);
        p.setRegionInstance(new RegionInstance(p, RegionInstance.RegionInstanceType.GAUNTLET));


        World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.GAUNTLET, p.getPosition().getZ()));
        NPC hunllef = new NPC(23021, new Position(1878, 5655, height));
        World.register(hunllef);
        p.getRegionInstance().getNpcsList().add(hunllef);

        if (p.difficulty > 0)
            hunllef.difficultyPerks(hunllef, p.difficulty);

        p.getRegionInstance().getNpcsList().add(hunllef);

    }

    public static void roomMechanics(Player p) {

        p.gauntletTick++;

        //Spikes special (setup)
        if (p.gauntletTick == 20) {
            p.gauntletSpikes = p.getPosition();
            GauntletSpecials.spikes(p);
        }

        //object 320001 (purple fire) - Healing special
        if (p.gauntletTick == 60) {
            GauntletSpecials.healingFlame(p);
        }


        if (p.gauntletTick >= 100) {
            p.gauntletTick = 0;
        }

    }




    public static void leave(final Player p) {
        /*p.getEquipment().deleteAll();
        p.getInventory().deleteAll();
        p.getEquipment().refreshItems();
        p.getInventory().refreshItems();*/

        if (p.getRegionInstance() != null)
            p.getRegionInstance().destruct();

        p.moveTo(new Position(3081, 3421, 0), true);

        p.getMinigameAttributes().getRaidsAttributes().setInsideRaid(false);

    }


}
