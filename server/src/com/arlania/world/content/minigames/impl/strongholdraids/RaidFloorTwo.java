package com.arlania.world.content.minigames.impl.strongholdraids;

import com.arlania.model.Position;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;


public class RaidFloorTwo {


    public static void spawnBoss(RaidsParty party, int instanceHeight) {
        NPC n = new NPC(10126, new Position(3352, 9647, instanceHeight));
        n.setDefaultConstitution(15000 + (5000 * party.getOwner().teamSize));
        n.setConstitution(15000 + (5000 * party.getOwner().teamSize));
        World.register(n);

        n.difficultyPerks(n, party.getOwner().difficulty);

        party.getOwner().getRegionInstance().getNpcsList().add(n);
    }

    public static void assignBossKey(Player player) {

        final RaidsParty party = player.getRaidsParty();

        if(party.shrFloorTwoBossKey < 18000) {
            int randKey = RandomUtility.inclusiveRandom(3);
            party.shrFloorTwoBossKey = 18234 + (randKey * 2);
            for (Player member : party.getPlayers())
                member.getPacketSender().sendMessage("@gre@Your Floor Two Boss Key is: " + ItemDefinition.forId(player.getRaidsParty().shrFloorTwoBossKey).getName() + ".");
        }

    }
}
