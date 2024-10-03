package com.arlania.model.input.impl;

import com.arlania.model.container.impl.Bank.BankSearchAttributes;
import com.arlania.model.input.Input;
import com.arlania.world.entity.impl.player.Player;

public class EnterSyntaxToBankSearchFor extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        boolean searchingBank = player.isBanking() && player.getBankSearchingAttribtues().isSearchingBank();
        if (searchingBank)
            BankSearchAttributes.beginSearch(player, syntax);
    }
}
