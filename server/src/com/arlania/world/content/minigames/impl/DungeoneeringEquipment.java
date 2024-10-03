package com.arlania.world.content.minigames.impl;

import com.arlania.model.Item;
import com.arlania.model.Locations;
import com.arlania.world.entity.impl.player.Player;


public class DungeoneeringEquipment {

    //Equipment

    public static int[][][] dungeoneeringEquipment =
                    //melee helm
                    {{{16691, 15914}, {16693, 15915}, {16695, 15916}, {16697, 15917}, {16699, 15918}, {16701, 15919}, {16703, 15920}, {16705, 15921}, {16707, 15922}, {16709, 15923}, {16709, 15924}},
                    //melee body
                    {{17239, 16013}, {17241, 16014}, {17243, 16015}, {17245, 16016}, {17247, 16017}, {17249, 16018}, {17251, 16019}, {17253, 16020}, {17255, 16021}, {17257, 16022}, {17259, 16023}},
                    //melee legs
                    {{16669, 15925}, {16671, 15926}, {16673, 15927}, {16675, 15928}, {16677, 15929}, {16679, 15930}, {16681, 15931}, {16683, 15932}, {16685, 15933}, {16687, 15934}, {16689, 15935}},
                    //melee rapier
                    {{16935, 16035}, {16937, 16036}, {16939, 16037}, {16941, 16038}, {16943, 16039}, {16945, 16040}, {16947, 16041}, {16949, 16042}, {16951, 16043}, {16953, 16044}, {16955, 16045}},
                    //melee maul
                    {{16405, 16174}, {16407, 16175}, {16409, 16176}, {16411, 16177}, {16413, 16178}, {16415, 16179}, {16417, 16180}, {16419, 16181}, {16421, 16182}, {16423, 16183}, {16425, 16184}},
                    //melee longsword
                    {{16383, 16024}, {16385, 16025}, {16387, 16026}, {16389, 16027}, {16391, 16028}, {16393, 16029}, {16395, 16030}, {16397, 16031}, {16399, 16032}, {16401, 16033}, {16403, 16034}},
                    //melee shield
                    {{17341, 15808}, {17343, 15809}, {17345, 15810}, {17347, 15811}, {17349, 15812}, {17351, 15813}, {17353, 15814}, {17355, 15815}, {17357, 15816}, {17359, 15817}, {17361, 15818}},
                    //range helm
                    {{17041, 16046}, {17043, 16047}, {17045, 16048}, {17047, 16049}, {17049, 16050}, {17051, 16051}, {17053, 16052}, {17055, 16053}, {17057, 16054}, {17059, 16055}, {17061, 16056}},
                    //range body
                    {{17173, 16068}, {17175, 16069}, {17177, 16070}, {17179, 16071}, {17181, 16072}, {17183, 16073}, {17185, 16074}, {17187, 16075}, {17189, 16076}, {17191, 16077}, {17193, 16078}},
                    //range legs
                    {{17319, 16057}, {17321, 16058}, {17323, 16059}, {17325, 16060}, {17327, 16061}, {17329, 16062}, {17331, 16063}, {17333, 16064}, {17335, 16065}, {17337, 16066}, {17339, 16067}},
                    //range bow
                    {{16867, 15775}, {16869, 15776}, {16871, 15777}, {16873, 15778}, {16875, 15779}, {16877, 15780}, {16879, 15781}, {16881, 15782}, {16883, 15783}, {16885, 15784}, {16887, 15785}},
                    //magic helm
                    {{16735, 15892}, {16737, 15893}, {16739, 15894}, {16741, 15895}, {16743, 15896}, {16745, 15897}, {16747, 15898}, {16749, 15899}, {16751, 15900}, {16753, 15901}, {16755, 15902}},
                    //magic body
                    {{17217, 15837}, {17219, 15838}, {17221, 15839}, {17223, 15840}, {17225, 15841}, {17227, 15842}, {17229, 15843}, {17231, 15844}, {17233, 15845}, {17235, 15846}, {17237, 15847}},
                    //magic legs
                    {{16845, 15797}, {16847, 15798}, {16849, 15799}, {16851, 15800}, {16853, 15801}, {16855, 15802}, {16857, 15803}, {16859, 15804}, {16861, 15805}, {16863, 15806}, {16865, 15807}},
                    //magic staff
                    {{16977, 16153}, {16979, 16154}, {16981, 16155}, {16983, 16156}, {16985, 16157}, {16987, 16158}, {16989, 16159}, {16991, 16160}, {16993, 16161}, {16995, 16162}, {17017, 16173}}};


    public static void upgradeItem(Player player, int itemId, boolean token) {

        if (player.getLocation() != Locations.Location.SHR) {
            player.getPacketSender().sendMessage("You can only do this in Stronghold Raids.");
            return;
        }

        int newItemId = 0;
        int upgradeToken = 0;
        String upgradeNeeded = "";


        player.getPacketSender().sendInterfaceRemoval();
        //player.getPacketSender().sendMessage("itemId: " + itemId);


        Item item = new Item(itemId);

        //if (item.getDefinition().getName().contains("(b)"))
        //newItemId = itemId + 1;
        //else
        newItemId = itemId + 2;

        if (newItemId == 16997)
            newItemId = 17017;


        Item upgradedItem = new Item(newItemId);

        if (!player.getInventory().contains(itemId)) {
            player.getPacketSender().sendMessage("@red@Your inventory does not contain this item. Tell Pat if this happens.");
            return;
        }

        //Types of Equipment
        for (int i = 0; i < 15; i++) {
            //Tiers of Each type
            for (int j = 0; j < 11; j++) {
                if (dungeoneeringEquipment[i][j][0] == itemId) {

                    switch (j) {

                        case 0:
                        case 1:
                            upgradeToken = 4037;
                            upgradeNeeded = "Low";
                            break;
                        case 2:
                        case 3:
                            upgradeToken = 4036;
                            upgradeNeeded = "Medium";
                            break;
                        case 4:
                        case 5:
                            upgradeToken = 4033;
                            upgradeNeeded = "High";
                            break;
                        case 6:
                        case 7:
                            upgradeToken = 4034;
                            upgradeNeeded = "Legendary";
                            break;
                        case 8:
                        case 9:
                            upgradeToken = 4035;
                            upgradeNeeded = "Master";
                            break;

                    }

                }

            }
        }

        if (!player.getInventory().contains(upgradeToken)) {
            player.getPacketSender().sendMessage("@red@This item requires a " + upgradeNeeded + " Upgrade Token.");
            return;
        }


        player.getInventory().delete(upgradeToken, 1);
        player.getInventory().delete(itemId, 1);
        player.getInventory().add(newItemId, 1);


        player.getPacketSender().sendMessage("@red@You upgrade your equipment to a " + upgradedItem.getDefinition().getName());

    }


}