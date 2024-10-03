package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks;


import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.CombatIcon;
import com.arlania.model.Projectile;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.Olm;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.player.Player;

public class MagicAttack {

    public static void performAttack(RaidsParty party, int height) {
        party.setOlmAttackTimer(6);
        party.getGreatOlmNpc().performGreatOlmAttack(party);
        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {
                if (party.getGreatOlmNpc().isDying() || party.isSwitchingPhases()) {
                    stop();
                }
                if (tick == 1) {
                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                            new Projectile(party.getGreatOlmNpc(), member, Attacks.DARK_GREEN_FLYING, 60, 8, 70, 31, 0)
                                    .sendProjectile();
                            Attacks.hitPlayer(member, Olm.getOlmMinHit(party), Olm.getOlmMaxHit(party), CombatType.MAGIC, CombatIcon.MAGIC, 0, true);

                        }
                    }
                }
                if (tick == 2) {
                    OlmAnimations.resetAnimation(party);
                    stop();
                }
                tick++;
            }
        });
    }

}
