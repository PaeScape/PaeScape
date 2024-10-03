package com.arlania.world.content.minigames.impl.strongholdraids;

import com.arlania.model.Position;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;


public class RaidFloorFour {


    public static void spawnBoss(RaidsParty party, int instanceHeight) {
        NPC n = new NPC(22097, new Position(3371, 9629, instanceHeight));
        n.setDefaultConstitution(25000 + (5000 * party.getOwner().teamSize));
        n.setConstitution(25000 + (5000 * party.getOwner().teamSize));
        World.register(n);

        n.difficultyPerks(n, party.getOwner().difficulty);

        party.getOwner().getRegionInstance().getNpcsList().add(n);
    }

    public static void assignBossKey(Player player) {

        final RaidsParty party = player.getRaidsParty();

        if(party.shrFloorFourBossKey < 18000) {
            int randKey = RandomUtility.inclusiveRandom(3);
            party.shrFloorFourBossKey = 18298 + (randKey * 2);
            for (Player member : party.getPlayers())
                member.getPacketSender().sendMessage("@gre@Your Floor Four Boss Key is: " + ItemDefinition.forId(player.getRaidsParty().shrFloorFourBossKey).getName() + ".");
        }

    }
}
