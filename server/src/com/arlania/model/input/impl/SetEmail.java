package com.arlania.model.input.impl;

import com.arlania.GameSettings;
import com.arlania.model.input.Input;
import com.arlania.world.content.PlayerPanel;
import com.arlania.world.entity.impl.player.Player;

public class SetEmail extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        player.getPacketSender().sendInterfaceRemoval();
        if (!GameSettings.MYSQL_ENABLED) {
            player.getPacketSender().sendMessage("This service is currently unavailable.");
            return;
        }
        if (syntax.length() <= 3 || !syntax.contains("@") || syntax.endsWith("@")) {
            player.getPacketSender().sendMessage("Invalid email, please enter a valid one.");
            return;
        }
        if (player.getBankPinAttributes().hasBankPin() && !player.getBankPinAttributes().hasEnteredBankPin()) {
            player.getPacketSender().sendMessage("Please visit the nearest bank and enter your pin before doing this.");
            return;
        }

        if (player.getEmailAddress() != null && syntax.equalsIgnoreCase(player.getEmailAddress())) {
            player.getPacketSender().sendMessage("This is already your email-address!");
            return;
        }

        player.setEmailAddress(syntax);
        player.getPacketSender().sendMessage("Your account's email-adress is now: " + syntax);
        //Achievements.finishAchievement(player, AchievementData.SET_AN_EMAIL_ADDRESS);
        PlayerPanel.refreshPanel(player);

    }
}

