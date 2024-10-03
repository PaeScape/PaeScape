package com.arlania.world.content.minigames.impl.strongholdraids;

import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;


public class RaidFloorOne {


    public static void createInstance(Player player, int height) {
        player.getPacketSender().sendMessage("@red@You move to the 1st Floor!");

        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.SHR));

        for (Player member : player.getRaidsParty().getPlayers()) {
            member.strongholdRaidFloor = 1;
            World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.SHR, player.getPosition().getZ()));
        }

        //Mobs.spawnMobsF1(player);
    }

    public static void spawnBoss(RaidsParty party, int instanceHeight) {
        NPC n = new NPC(9767, new Position(3352, 9630, instanceHeight));
        n.setDefaultConstitution(10000 + (5000 * party.getOwner().teamSize));
        n.setConstitution(10000 + (5000 * party.getOwner().teamSize));
        World.register(n);

        n.difficultyPerks(n, party.getOwner().difficulty);

        party.getOwner().getRegionInstance().getNpcsList().add(n);
    }

    public static void assignBossKey(Player player) {

        final RaidsParty party = player.getRaidsParty();

        if(party.shrFloorOneBossKey < 18000) {
            int randKey = RandomUtility.inclusiveRandom(3);
            party.shrFloorOneBossKey = 18218 + (randKey * 2);
            for (Player member : party.getPlayers())
                member.getPacketSender().sendMessage("@gre@Your Floor One Boss Key is: " + ItemDefinition.forId(player.getRaidsParty().shrFloorOneBossKey).getName() + ".");
        }

    }
}
