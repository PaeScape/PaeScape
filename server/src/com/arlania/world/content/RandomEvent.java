package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

public class RandomEvent {

    private final static int[] randomLoot = {989, 4562, 11137};

    public static void randomStart(Player player) {

        player.randomx = player.getPosition().getX();
        player.randomy = player.getPosition().getY();
        player.randomz = player.getPosition().getZ();

        player.inRandom = true;
        player.getSkillManager().stopSkilling();


        //when we add more random events, have the random events selected at random.
        randomMaze(player);

    }

    public static void randomEnd(Player player) {
        if (player.inRandom) {
            String discordLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " has left the Maze Random.";

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_RANDOM_EVENT_LOG_CH).get());

            player.inRandom = false;
            Emotes.doEmote(player, 171);
            player.moveTo(new Position(player.randomx, player.randomy, player.randomz));
            player.setRandomEventTimer(0);
            player.getInventory().add(randomLoot[RandomUtility.inclusiveRandom(randomLoot.length - 1)], 1);
        } else {
            player.inRandom = false;
            Emotes.doEmote(player, 171);
            player.moveTo(new Position(player.randomx, player.randomy, player.randomz));
            player.setRandomEventTimer(0);
        }
    }

    public static void randomMaze(Player player) {

        String discordLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " was sent to the Maze Random.";

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_RANDOM_EVENT_LOG_CH).get());


        int randomX = 2911;
        int randomY = 4575;

        if (RandomUtility.inclusiveRandom(1) == 1)
            randomX += (3 + RandomUtility.inclusiveRandom(5));
        else
            randomX -= (3 + RandomUtility.inclusiveRandom(5));

        if (RandomUtility.inclusiveRandom(1) == 1)
            randomY += (3 + RandomUtility.inclusiveRandom(5));
        else
            randomY -= (3 + RandomUtility.inclusiveRandom(5));


        player.moveTo(new Position(randomX, randomY, 0));

    }

    public static void evilChicken(Player player) {

        NPC n = new NPC(1, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ())).setSpawnedFor(player);
        World.register(n);
        n.getCombatBuilder().attack(player);

        player.inRandom = true;
        player.getSkillManager().stopSkilling();


        //when we add more random events, have the random events selected at random.
        randomMaze(player);

    }


}
