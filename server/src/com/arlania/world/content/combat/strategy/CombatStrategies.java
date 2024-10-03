package com.arlania.world.content.combat.strategy;

import com.arlania.world.content.combat.strategy.impl.*;
import com.arlania.world.content.combat.strategy.impl.bots.Zerker;
import com.arlania.world.content.combat.strategy.impl.bots.ZerkerHybrid;

import java.util.HashMap;
import java.util.Map;


public class CombatStrategies {

    private static final DefaultMeleeCombatStrategy defaultMeleeCombatStrategy = new DefaultMeleeCombatStrategy();
    private static final DefaultMagicCombatStrategy defaultMagicCombatStrategy = new DefaultMagicCombatStrategy();
    private static final DefaultRangedCombatStrategy defaultRangedCombatStrategy = new DefaultRangedCombatStrategy();
    private static final Map<Integer, CombatStrategy> STRATEGIES = new HashMap<Integer, CombatStrategy>();

    public static void init() {
        DefaultMagicCombatStrategy defaultMagicStrategy = new DefaultMagicCombatStrategy();
        STRATEGIES.put(13, defaultMagicStrategy);
        STRATEGIES.put(172, defaultMagicStrategy);
        STRATEGIES.put(174, defaultMagicStrategy);
        STRATEGIES.put(2025, defaultMagicStrategy);
        STRATEGIES.put(3495, defaultMagicStrategy);
        STRATEGIES.put(3496, defaultMagicStrategy);
        STRATEGIES.put(3491, defaultMagicStrategy);
        STRATEGIES.put(2882, defaultMagicStrategy);
        STRATEGIES.put(13451, defaultMagicStrategy);
        STRATEGIES.put(13452, defaultMagicStrategy);
        STRATEGIES.put(13453, defaultMagicStrategy);
        STRATEGIES.put(13454, defaultMagicStrategy);
        STRATEGIES.put(1643, defaultMagicStrategy);
        STRATEGIES.put(6254, defaultMagicStrategy);
        STRATEGIES.put(6257, defaultMagicStrategy);
        STRATEGIES.put(6278, defaultMagicStrategy);
        STRATEGIES.put(6221, defaultMagicStrategy);
        STRATEGIES.put(10565, defaultMagicStrategy);
        STRATEGIES.put(10586, defaultMagicStrategy);
        STRATEGIES.put(10599, defaultMagicStrategy);
        STRATEGIES.put(10603, defaultMagicStrategy);
        STRATEGIES.put(22360, defaultMagicStrategy); //maiden tob
        STRATEGIES.put(1580, defaultMagicStrategy); //maiden tob
        STRATEGIES.put(21604, defaultMagicStrategy); //Mystic cox
        STRATEGIES.put(21605, defaultMagicStrategy); //Mystic cox
        STRATEGIES.put(21606, defaultMagicStrategy); //Mystic cox
        STRATEGIES.put(22061, new Vorkath()); //Vorkath

        DefaultRangedCombatStrategy defaultRangedStrategy = new DefaultRangedCombatStrategy();
        STRATEGIES.put(688, defaultRangedStrategy);
        STRATEGIES.put(2028, defaultRangedStrategy);
        STRATEGIES.put(6220, defaultRangedStrategy);
        STRATEGIES.put(6256, defaultRangedStrategy);
        STRATEGIES.put(6276, defaultRangedStrategy);
        STRATEGIES.put(6252, defaultRangedStrategy);
        STRATEGIES.put(27, defaultRangedStrategy);
        STRATEGIES.put(23034, defaultRangedStrategy);
        STRATEGIES.put(10327, defaultRangedStrategy);
        STRATEGIES.put(10342, defaultRangedStrategy);
        STRATEGIES.put(10352, defaultRangedStrategy);
        STRATEGIES.put(10362, defaultRangedStrategy);

        STRATEGIES.put(103, new BloodSpawn());
        STRATEGIES.put(1648, new BloodSpawn());
        STRATEGIES.put(22367, new BloodSpawn());
        STRATEGIES.put(22374, new VerzikVitur());
        STRATEGIES.put(2745, new Jad());
        STRATEGIES.put(8528, new Nomad());
        STRATEGIES.put(8349, new TormentedDemon());
        STRATEGIES.put(3200, new ChaosElemental());
        STRATEGIES.put(4540, new Glacio());
        STRATEGIES.put(289, new CorporealBeast());
        STRATEGIES.put(8133, new CorporealBeast());
        STRATEGIES.put(13447, new Nex());
        STRATEGIES.put(2896, new Spinolyp());
        STRATEGIES.put(2881, new DagannothSupreme());
        STRATEGIES.put(6260, new Graardor());
        STRATEGIES.put(20494, new Graardor());
        STRATEGIES.put(6263, new Steelwill());
        STRATEGIES.put(6265, new Grimspike());
        STRATEGIES.put(6222, new KreeArra());
        STRATEGIES.put(20492, new KreeArra());
        STRATEGIES.put(6223, new WingmanSkree());
        STRATEGIES.put(6225, new Geerin());
        STRATEGIES.put(6203, new Tsutsuroth());
        STRATEGIES.put(20495, new Tsutsuroth());
        STRATEGIES.put(6208, new Tsutsuroth());
        STRATEGIES.put(6206, new Gritch());
        STRATEGIES.put(6247, new Zilyana());
        STRATEGIES.put(20493, new Zilyana());
        STRATEGIES.put(6250, new Growler());
        STRATEGIES.put(1382, new Glacor());
        //STRATEGIES.put(2043, new GreenZulrah());
        //STRATEGIES.put(2042, new BlueZulrah());
        STRATEGIES.put(1581, new Alucard());
        STRATEGIES.put(4809, new Aviansie());
        STRATEGIES.put(4810, new Aviansie());
        STRATEGIES.put(2042, new BlueZulrah());
        STRATEGIES.put(2043, new GreenZulrah());
        STRATEGIES.put(2044, new CrimsonZulrah());

        //Chaos Raids
        STRATEGIES.put(12766, new Tsutsuroth());
        STRATEGIES.put(7286, new Skotizo());
        STRATEGIES.put(9939, new PlaneFreezer());


        //Stronghold Raid
        STRATEGIES.put(9767, new Rammernaut());
        STRATEGIES.put(10126, new UnholyCursebearer());
        STRATEGIES.put(12751, new WarpedGulega());
        STRATEGIES.put(22097, new Galvek());

        //PKers
        STRATEGIES.put(9, new Zerker());
        STRATEGIES.put(8, new ZerkerHybrid());


        Dragon dragonStrategy = new Dragon();
        STRATEGIES.put(50, dragonStrategy);
        STRATEGIES.put(941, dragonStrategy);
        STRATEGIES.put(55, dragonStrategy);
        STRATEGIES.put(53, dragonStrategy);
        STRATEGIES.put(54, dragonStrategy);
        STRATEGIES.put(51, dragonStrategy);
        STRATEGIES.put(1590, dragonStrategy);
        STRATEGIES.put(1591, dragonStrategy);
        STRATEGIES.put(1592, dragonStrategy);
        STRATEGIES.put(5362, dragonStrategy);
        STRATEGIES.put(5363, dragonStrategy);
        STRATEGIES.put(23033, dragonStrategy);

        Aviansie aviansieStrategy = new Aviansie();
        STRATEGIES.put(6246, aviansieStrategy);
        STRATEGIES.put(6230, aviansieStrategy);
        STRATEGIES.put(6231, aviansieStrategy);

        KalphiteQueen kalphiteQueenStrategy = new KalphiteQueen();
        STRATEGIES.put(1158, kalphiteQueenStrategy);
        STRATEGIES.put(1159, kalphiteQueenStrategy);
        STRATEGIES.put(1160, kalphiteQueenStrategy);

        STRATEGIES.put(2043, new Zulrah());
        STRATEGIES.put(2042, new Zulrah());

        Revenant revenantStrategy = new Revenant();
        STRATEGIES.put(6715, revenantStrategy);
        STRATEGIES.put(6716, revenantStrategy);
        STRATEGIES.put(6701, revenantStrategy);
        STRATEGIES.put(6725, revenantStrategy);
        STRATEGIES.put(6691, revenantStrategy);

        STRATEGIES.put(2000, new Venenatis());
        STRATEGIES.put(2006, new Vetion());
        STRATEGIES.put(2010, new Callisto());
        STRATEGIES.put(1999, new Cerberus());
        STRATEGIES.put(6766, new LizardMan());
        STRATEGIES.put(499, new Thermonuclear());
        STRATEGIES.put(23425, new Nightmare());
        STRATEGIES.put(23426, new Nightmare());
        STRATEGIES.put(5886, new Sire());
    }

    public static CombatStrategy getStrategy(int npc) {
        if (STRATEGIES.get(npc) != null) {
            return STRATEGIES.get(npc);
        }
        return defaultMeleeCombatStrategy;
    }

    public static CombatStrategy getDefaultMeleeStrategy() {
        return defaultMeleeCombatStrategy;
    }

    public static CombatStrategy getDefaultMagicStrategy() {
        return defaultMagicCombatStrategy;
    }


    public static CombatStrategy getDefaultRangedStrategy() {
        return defaultRangedCombatStrategy;
    }
}
