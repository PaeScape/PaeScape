package com.arlania.world.content.skill.impl.dungeoneering;

import com.arlania.model.Locations;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class ItemBinding {

    private static final int[][] BINDABLE_ITEMS = {
    {16691, 15914}, {16693, 15915}, {16695, 15916}, {16697, 15917}, {16699, 15918}, {16701, 15919}, {16703, 15920}, {16705, 15921}, {16707, 15922}, {16709, 15923}, {16709, 15924},
    {17239, 16013}, {17241, 16014}, {17243, 16015}, {17245, 16016}, {17247, 16017}, {17249, 16018}, {17251, 16019}, {17253, 16020}, {17255, 16021}, {17257, 16022}, {17259, 16023},
    {16669, 15925}, {16671, 15926}, {16673, 15927}, {16675, 15928}, {16677, 15929}, {16679, 15930}, {16681, 15931}, {16683, 15932}, {16685, 15933}, {16687, 15934}, {16689, 15935},
    {16935, 16035}, {16937, 16036}, {16939, 16037}, {16941, 16038}, {16943, 16039}, {16945, 16040}, {16947, 16041}, {16949, 16042}, {16951, 16043}, {16953, 16044}, {16955, 16045},
    {16383, 16024}, {16385, 16025}, {16387, 16026}, {16389, 16027}, {16391, 16028}, {16393, 16029}, {16395, 16030}, {16397, 16031}, {16399, 16032}, {16401, 16033}, {16403, 16034},
    {16405, 16174}, {16407, 16175}, {16409, 16176}, {16411, 16177}, {16413, 16178}, {16415, 16179}, {16417, 16180}, {16419, 16181}, {16421, 16182}, {16423, 16183}, {16425, 16184},
    {17341, 15808}, {17343, 15809}, {17345, 15810}, {17347, 15811}, {17349, 15812}, {17351, 15813}, {17353, 15814}, {17355, 15815}, {17357, 15816}, {17359, 15817}, {17361, 15818},

    {17041, 16046}, {17043, 16047}, {17045, 16048}, {17047, 16049}, {17049, 16050}, {17051, 16051}, {17053, 16052}, {17055, 16053}, {17057, 16054}, {17059, 16055}, {17061, 16056},
    {17173, 16068}, {17175, 16069}, {17177, 16070}, {17179, 16071}, {17181, 16072}, {17183, 16073}, {17185, 16074}, {17187, 16075}, {17189, 16076}, {17191, 16077}, {17193, 16078},
    {17319, 16057}, {17321, 16058}, {17323, 16059}, {17325, 16060}, {17327, 16061}, {17329, 16062}, {17331, 16063}, {17333, 16064}, {17335, 16065}, {17337, 16066}, {17339, 16067},
    {16867, 15775}, {16869, 15776}, {16871, 15777}, {16873, 15778}, {16875, 15779}, {16877, 15780}, {16879, 15781}, {16881, 15782}, {16883, 15783}, {16885, 15784}, {16887, 15785},

    {16735, 15892}, {16737, 15893}, {16739, 15894}, {16741, 15895}, {16743, 15896}, {16745, 15897}, {16747, 15898}, {16749, 15899}, {16751, 15900}, {16753, 15901}, {16755, 15902},
    {17217, 15837}, {17219, 15838}, {17221, 15839}, {17223, 15840}, {17225, 15841}, {17227, 15842}, {17229, 15843}, {17231, 15844}, {17233, 15845}, {17235, 15846}, {17237, 15847},
    {16845, 15797}, {16847, 15798}, {16849, 15799}, {16851, 15800}, {16853, 15801}, {16855, 15802}, {16857, 15803}, {16859, 15804}, {16861, 15805}, {16863, 15806}, {16865, 15807},
    {16977, 16153}, {16979, 16154}, {16981, 16155}, {16983, 16156}, {16985, 16157}, {16987, 16158}, {16989, 16159}, {16991, 16160}, {16993, 16161}, {16995, 16162}, {17017, 16173}


};

    public static int getRandomBindableItem() {
        int index = RandomUtility.inclusiveRandom(BINDABLE_ITEMS.length - 1);
        if (ItemDefinition.forId(BINDABLE_ITEMS[index][0]).getName().toLowerCase().contains("body") || ItemDefinition.forId(BINDABLE_ITEMS[index][0]).getName().toLowerCase().contains("legs") || ItemDefinition.forId(BINDABLE_ITEMS[index][0]).getName().toLowerCase().contains("skirt")) {
            index = index + 1 >= BINDABLE_ITEMS[0].length ? index - 1 : index + 1;
        }
        return BINDABLE_ITEMS[index][0];
    }

    public static boolean isBindable(int item) {
        for (int i = 0; i < BINDABLE_ITEMS.length; i++) {
            if (BINDABLE_ITEMS[i][0] == item)
                return true;
        }
        return false;
    }

    public static boolean isBoundItem(int item) {
        for (int i = 0; i < BINDABLE_ITEMS.length; i++) {
            if (BINDABLE_ITEMS[i][1] == item)
                return true;
        }
        return false;
    }

    public static int getItem(int currentId) {
        for (int i = 0; i < BINDABLE_ITEMS.length; i++) {
            if (BINDABLE_ITEMS[i][0] == currentId)
                return BINDABLE_ITEMS[i][1];
        }
        return -1;
    }

    public static void unbindItem(Player p, int item) {
        //if (Dungeoneering.doingDungeoneering(p)) {
        for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
            if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] == item) {
                p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] = 0;
                p.getPacketSender().sendMessage("You unbind the item..");
                break;
            }
        }
        //}
    }

    public static void bindItem(Player p, int item) {
        //if (Dungeoneering.doingDungeoneering(p)) {

        if(p.getLocation() != Locations.Location.SHR) {
            p.getPacketSender().sendMessage("You can only bind in Stronghold Raids.");
            return;
        }

        if (!isBindable(item))
            return;
        int amountBound = 0;
        for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
            if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] != 0)
                amountBound++;
        }
        if (amountBound == 5) {
            p.getPacketSender().sendMessage("You have already binded the maximum 5 items.");
            return;
        } else if (amountBound == 4 && p.getshrKC() < 500) {
            p.getPacketSender().sendMessage("You need to complete 500 raids to bind 5 items.");
            return;
        } else if (amountBound == 3 && p.getshrKC() < 250) {
            p.getPacketSender().sendMessage("You need to complete 250 raids to bind 4 items.");
            return;
        } else if (amountBound == 2 && p.getshrKC() < 100) {
            p.getPacketSender().sendMessage("You need to complete 100 raids to bind 3 items.");
            return;
        } else if (amountBound == 1 && p.getshrKC() < 50) {
            p.getPacketSender().sendMessage("You need to complete 50 raids to bind 2 items.");
            return;
        }
        int bind = getItem(item);
        int index = -1;
        for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
            if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] != 0)
                continue;
            index = i;
            break;
        }
        if (bind != -1 && index != -1) {
            p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[index] = bind;
            p.getInventory().delete(item, 1).add(bind, 1);
            p.getPacketSender().sendMessage("You bind the item..");
        }
        //}
    }

    public static void onDungeonEntrance(Player p) {
        for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
            if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] != 0) {
                p.getInventory().add(p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i], 1);
            }
        }
    }
}
