package com.arlania.world.content.skill;

import com.arlania.model.input.impl.*;
import com.arlania.world.content.ExchangeTable;
import com.arlania.world.content.MagicalPumpkin;
import com.arlania.world.content.skill.impl.cooking.Cooking;
import com.arlania.world.content.skill.impl.crafting.Flax;
import com.arlania.world.content.skill.impl.crafting.Gems;
import com.arlania.world.content.skill.impl.crafting.JewelryMaking;
import com.arlania.world.content.skill.impl.farming.SeedsOnPatch;
import com.arlania.world.content.skill.impl.fletching.Fletching;
import com.arlania.world.content.skill.impl.prayer.BonesOnAltar;
import com.arlania.world.entity.impl.player.Player;

public class ChatboxInterfaceSkillAction {

    public static void handleChatboxInterfaceButtons(Player player, int buttonId) {
        if (!player.getClickDelay().elapsed(3000) || player.getInputHandling() != null && handleMakeXInterfaces(player, buttonId))
            return;
        int amount = buttonId == 2799 ? 1 : buttonId == 2798 ? 5 : buttonId == 1747 ? 28 : -1;
        if (player.getInputHandling() == null || amount <= 0) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        if (player.getInputHandling() instanceof EnterAmountOfGemsToCut)
            Gems.cutGem(player, amount, player.getSelectedSkillingItem());
        else if (player.getInputHandling() instanceof EnterAmountToCraft)
            JewelryMaking.craftJewelry(player, amount, player.getSelectedSkillingItem());
        else if (player.getInputHandling() instanceof EnterAmountToCook)
            Cooking.cook(player, player.getSelectedSkillingItem(), amount);
        else if (player.getInputHandling() instanceof EnterAmountToSpin)
            Flax.spinFlax(player, amount);
        else if (player.getInputHandling() instanceof EnterAmountOfBonesToSacrifice)
            BonesOnAltar.offerBones(player, -1, amount);
        else if (player.getInputHandling() instanceof EnterAmountOfBowsToString)
            Fletching.stringBow(player, amount);
        else if (player.getInputHandling() instanceof EnterAmountOfSeedsToPlant)
            SeedsOnPatch.offerSeeds(player, amount);
        else if (player.getInputHandling() instanceof EnterAmountToExchange)
            ExchangeTable.handleExchange(player, amount);
        else if (player.getInputHandling() instanceof EnterAmountToCredit)
            MagicalPumpkin.handleCredit(player, ((EnterAmountToCredit) player.getInputHandling()).getItem(), amount);
        //else if(player.getInputHandling() instanceof EnterAmountOfItemsToForge)
        //ItemForging.forgingItems(player, amount);
        player.getClickDelay().reset();
    }

    public static boolean handleMakeXInterfaces(Player player, int buttonId) {
        if (buttonId == 8886 || buttonId == 8890 || buttonId == 8894 || buttonId == 8871 || buttonId == 8875 || buttonId == 1748) { // Fletching X amount

            if (player.getInputHandling() instanceof EnterAmountToFletch) {
                ((EnterAmountToFletch) player.getInputHandling()).setButton(buttonId);
            }

            if (player.getInputHandling() instanceof EnterAmountOfLeatherToCraft) {
                ((EnterAmountOfLeatherToCraft) player.getInputHandling()).setButton(buttonId);
            }

            player.getPacketSender().sendEnterAmountPrompt("How many would you like to make?");
            return true;
        }
        return false;
    }
}