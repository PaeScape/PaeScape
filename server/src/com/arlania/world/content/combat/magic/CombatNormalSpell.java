package com.arlania.world.content.combat.magic;

import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.Iterator;

/**
 * A {@link Spell} implementation primarily used for spells that have no effects
 * at all when they hit the player.
 *
 * @author lare96
 */
public abstract class CombatNormalSpell extends CombatSpell {

    @Override
    public void finishCast(Character cast, Character castOn, boolean accurate,
                           int damage) {

        if (GlobalEventHandler.effectActive(GlobalEvent.Effect.COLLATERAL)) {

            // The spell wasn't accurate, so do nothing.
            if (!accurate || damage <= 0) {
                return;
            }

            // Do the spell effect here.
            //spellEffect(cast, castOn, damage);

            // The spell doesn't support multiple targets or we aren't in a
            // multicombat zone, so do nothing.
            if (!Locations.Location.inMulti(castOn)) {
                return;
            }

            // We passed the checks, so now we do multiple target stuff.
            Iterator<? extends Character> it = null;
            if (cast.isPlayer() && castOn.isPlayer()) {
                it = ((Player) cast).getLocalPlayers().iterator();
            } else if (cast.isPlayer() && castOn.isNpc()) {
                it = ((Player) cast).getLocalNpcs().iterator();
            } else if (cast.isNpc() && castOn.isNpc()) {
                it = World.getNpcs().iterator();
            } else if (cast.isNpc() && castOn.isPlayer()) {
                it = World.getPlayers().iterator();
            }

            for (Iterator<? extends Character> $it = it; $it.hasNext(); ) {
                Character next = $it.next();

                if (next == null) {
                    continue;
                }

                if (next.isNpc()) {
                    NPC n = (NPC) next;
                    if (!n.getDefinition().isAttackable() || n.isSummoningNpc()) {
                        continue;
                    }
                } else {
                    Player p = (Player) next;
                    if (p.getLocation() != Locations.Location.WILDERNESS || !Locations.Location.inMulti(p)) {
                        continue;
                    }
                }


                if (next.getPosition().isWithinDistance(castOn.getPosition(), 3) && !next.equals(cast) && !next.equals(castOn) && next.getConstitution() > 0 && next.getConstitution() > 0) {
                    cast.getCurrentlyCasting().endGraphic().ifPresent(next::performGraphic);
                    int calc = RandomUtility.inclusiveRandom(0, maximumHit());
                    next.dealDamage(new Hit(calc, Hitmask.RED, CombatIcon.MAGIC));

                    //add experience for AoE attacks like Ice Barrage
                    if (cast.isPlayer()) {
                        cast.asPlayer().getSkillManager().addExperience(Skill.MAGIC, (int) (calc * .90));
                        //cast.asPlayer().getPacketSender().sendMessage("Magic damage: " + calc);
                    }

                    next.getCombatBuilder().addDamage(cast, calc);
                    //spellEffect(cast, next, calc);
                    next.getCombatBuilder().attack(cast);
                }
            }



        }
    }

}