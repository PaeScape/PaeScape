package com.arlania.net.packet.impl;

import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketListener;
import com.arlania.world.World;
import com.arlania.world.content.combat.magic.CombatSpell;
import com.arlania.world.content.combat.magic.CombatSpells;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.antibotting.actions.ActionMagicOnPlayer;

public class MagicOnPlayerPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int playerIndex = packet.readShortA();
        if (playerIndex < 0 || playerIndex > World.getPlayers().capacity())
            return;
        int spellId = packet.readLEShort();
        if (spellId < 0) {
            return;
        }

        Player attacked = World.getPlayers().get(playerIndex);

        if (attacked == null || attacked.equals(player)) {
            player.getMovementQueue().reset();
            return;
        }

        CombatSpell spell = CombatSpells.getSpell(spellId);
        if (spell == null) {
            player.getMovementQueue().reset();
            return;
        }

        if (attacked.getConstitution() <= 0) {
            player.getMovementQueue().reset();
            return;
        }
        player.getActionTracker().offer(new ActionMagicOnPlayer(playerIndex, spellId));
        // Start combat!
        player.setPositionToFace(attacked.getPosition());
        player.getCombatBuilder().resetCooldown();
        player.setCastSpell(spell);
        player.getCombatBuilder().attack(attacked);
    }

}
