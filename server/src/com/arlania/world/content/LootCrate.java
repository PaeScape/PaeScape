package com.arlania.world.content;

import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class LootCrate {


    public static void openCrate(Player player) {

        //player.getCollectionLog().updateCollectionlogKills(CustomCollection.MysteryBox.getId());

        int[] table = {
                15259, //dragon pickaxe
                6739, // dragon hatchet
                221028, //dragon harpoon
                4020, //personal event token
                4021, //personal event token
                4022, //personal event token
                4023, //personal event token
                4024, //personal event token
                4025, //personal event token
                4027, //personal event token
                4028, //personal event token
                4029, //personal event token
                4030, //personal event token
                4032, //personal event token
                6199, //mystery box
                6199, //mystery box
                6199, //mystery box
                219782, //Elite clue
                212542, //hard clue
                212542, //hard clue
                212542, //hard clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                212021, // medium clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                219833, //easy clue
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                2677, //500 PaePoints
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                4562, //Rare candy
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                9475, //Mint cake
                4034, //Legendary upgrade token
                4033, //High upgrade token
                4033, //High upgrade token
                6199, //Mystery box
                15501, //Elite mystery box
                2957, //Candy pouch
        };

        player.getInventory().add(table[RandomUtility.inclusiveRandom(1, table.length)], 1);


    }


}
