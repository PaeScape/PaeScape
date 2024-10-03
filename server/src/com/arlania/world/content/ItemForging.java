package com.arlania.world.content;

import com.arlania.model.Graphic;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles item forging, such as Spirit shields making etc.
 *
 * @author Gabriel Hannason and Samy
 */
public class ItemForging {

    public static void forgeItem(final Player p, final int item1, final int item2) {
        if (item1 == item2)
            return;
        ItemForgeData data = ItemForgeData.getDataForItems(item1, item2);
        if (data == null || !p.getInventory().contains(item1) || !p.getInventory().contains(item2))
            return;
        if (!p.getClickDelay().elapsed(250))
            return;
        if (p.getInterfaceId() > 0) {
            p.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
            return;
        }
        Skill skill = Skill.forId(data.skillRequirement[0]);
        int skillReq = data.skillRequirement[1];
        if (p.getSkillManager().getCurrentLevel(skill) >= skillReq) {
            for (Item reqItem : data.requiredItems) {
                if (!p.getInventory().contains(reqItem.getId()) || p.getInventory().getAmount(reqItem.getId()) < reqItem.getAmount()) {
                    p.getPacketSender().sendMessage("You need " + Misc.anOrA(reqItem.getDefinition().getName()) + " " + reqItem.getDefinition().getName() + " to forge a new item.");
                    return;
                }
            }
            p.performGraphic(new Graphic(2010));
            for (Item reqItem : data.requiredItems) {
                if (reqItem.getId() == 1755 || reqItem.getId() == 1595 || reqItem.getId() == 1597 || reqItem.getId() == 11065 || reqItem.getId() == 1592 || reqItem.getId() == 4)
                    continue;
                p.getInventory().delete(reqItem);
            }
            p.getInventory().add(data.product, true);
            final String itemName = Misc.formatText(ItemDefinition.forId(data.product.getId()).getName().toLowerCase());
            p.getPacketSender().sendMessage("You make " + Misc.anOrA(itemName) + " " + itemName + ".");
            p.getClickDelay().reset();
            p.getSkillManager().addExperience(skill, data.skillRequirement[2]);

        } else {
            p.getPacketSender().sendMessage("You need " + Misc.anOrA(skill.getFormatName()) + " " + skill.getFormatName() + " level of at least " + skillReq + " to forge this item.");
        }
    }

    /**
     * * The enum holding all our data
     */
    private enum ItemForgeData {
        BLESSED_SPIRIT_SHIELD(new Item[]{new Item(13754), new Item(13734)}, new Item(13736), new int[]{13, -1, 0}),
        SPECTRAL_SPIRIT_SHIELD(new Item[]{new Item(13752), new Item(13736)}, new Item(13744), new int[]{13, 85, 10000}),
        ARCANE_SPIRIT_SHIELD(new Item[]{new Item(13746), new Item(13736)}, new Item(13738), new int[]{13, 85, 10000}),
        ELYSIAN_SPIRIT_SHIELD(new Item[]{new Item(13750), new Item(13736)}, new Item(13742), new int[]{13, 85, 10000}),
        DIVINE_SPIRIT_SHIELD(new Item[]{new Item(13748), new Item(13736)}, new Item(13740), new int[]{13, 85, 10000}),

        PRIMORDIAL_BOOTS(new Item[]{new Item(6640), new Item(11732)}, new Item(13239), new int[]{12, 85, 10000}),
        PEGASIAN_BOOTS(new Item[]{new Item(6642), new Item(2577)}, new Item(12708), new int[]{12, 85, 10000}),
        ETERNAL_BOOTS(new Item[]{new Item(6645), new Item(6920)}, new Item(13235), new int[]{12, 85, 10000}),

        STEADFAST_BOOTS(new Item[]{new Item(4671), new Item(13239)}, new Item(20000), new int[]{12, 92, 15000}),
        GLAIVEN_BOOTS(new Item[]{new Item(4671), new Item(12708)}, new Item(20001), new int[]{12, 92, 15000}),
        RAGEFIRE_BOOTS(new Item[]{new Item(4671), new Item(13235)}, new Item(20002), new int[]{12, 92, 15000}),

        //DRAGON_SQ_SHIELD(new Item[] {new Item(2368), new Item(2366)}, new Item(1187), new int[] {13, 60, 10000}),
        //DRAGON_PLATEBY(new Item[] {new Item(14472), new Item(14474), new Item(14476)}, new Item(14479), new int[] {13, 92, 120000}),
        DRAGONFIRE_SHIELD(new Item[]{new Item(11286), new Item(1540)}, new Item(11283), new int[]{13, 85, 10000}),

        //GODSWORD_BLADE(new Item[] {new Item(11710), new Item(11712), new Item(11714)}, new Item(11690), new int[] {1, -1, 0}),
        ARMADYL_GODSWORD(new Item[]{new Item(11702), new Item(11690)}, new Item(11694), new int[]{13, 85, 0}),
        BANDOS_GODSWORD(new Item[]{new Item(11704), new Item(11690)}, new Item(11696), new int[]{13, 85, 0}),
        SARADOMIN_GODSWORD(new Item[]{new Item(11706), new Item(11690)}, new Item(11698), new int[]{13, 85, 0}),
        ZAMORAK_GODSWORD(new Item[]{new Item(11708), new Item(11690)}, new Item(11700), new int[]{13, 85, 0}),

        ABYSSAL_VINE_WHIP(new Item[]{new Item(4151), new Item(19981)}, new Item(20061), new int[]{13, 85, 0}),
        ABYSSAL_TENTACLE(new Item[]{new Item(4151), new Item(19982)}, new Item(212006), new int[]{13, 85, 0}),
        TWISTED_BOW(new Item[]{new Item(11235), new Item(19977)}, new Item(20998), new int[]{13, 85, 0}),


        //Superior Slayer weapons
        DRAGONFIRE_WHIP(new Item[]{new Item(4151), new Item(2380)}, new Item(15441), new int[]{13, 85, 0}),
        FROST_WHIP(new Item[]{new Item(4151), new Item(2381)}, new Item(15442), new int[]{13, 85, 0}),
        VAMPIRIC_WHIP(new Item[]{new Item(4151), new Item(2382)}, new Item(15443), new int[]{13, 85, 0}),
        VENOMOUS_WHIP(new Item[]{new Item(4151), new Item(2383)}, new Item(15444), new int[]{13, 85, 0}),

        DRAGONFIRE_BOW(new Item[]{new Item(11235), new Item(2380)}, new Item(15701), new int[]{13, 85, 0}),
        FROST_BOW(new Item[]{new Item(11235), new Item(2381)}, new Item(15702), new int[]{13, 85, 0}),
        VAMPIRIC_BOW(new Item[]{new Item(11235), new Item(2382)}, new Item(15703), new int[]{13, 85, 0}),
        VENOMOUS_BOW(new Item[]{new Item(11235), new Item(2383)}, new Item(15704), new int[]{13, 85, 0}),

        DRAGONFIRE_STAFF(new Item[]{new Item(15486), new Item(2380)}, new Item(14004), new int[]{13, 85, 0}),
        FROST_STAFF(new Item[]{new Item(15486), new Item(2381)}, new Item(14005), new int[]{13, 85, 0}),
        VAMPIRIC_STAFF(new Item[]{new Item(15486), new Item(2382)}, new Item(14006), new int[]{13, 85, 0}),
        VENOMOUS_STAFF(new Item[]{new Item(15486), new Item(2383)}, new Item(14007), new int[]{13, 85, 0}),

        GRANITE_MAUL_OR(new Item[]{new Item(4153), new Item(212849)}, new Item(18903), new int[]{13, 85, 0}),
        ARCLIGHT(new Item[]{new Item(219677), new Item(6746)}, new Item(219675), new int[]{13, 85, 0}),

        DRAGON_PLATEBODY(new Item[]{new Item(14472), new Item(3140)}, new Item(14479), new int[]{13, 85, 0}),


        AMULET_OF_FURY_ORNAMENT(new Item[]{new Item(19333), new Item(6585)}, new Item(19335), new int[]{12, -1, 0}),
        DRAGON_FULL_HELM_SPIKE(new Item[]{new Item(19354), new Item(11335)}, new Item(19341), new int[]{13, -1, 0}),
        DRAGON_PLATELEGS_SPIKE(new Item[]{new Item(19356), new Item(4087)}, new Item(19343), new int[]{13, -1, 0}),
        DRAGON_PLATEBODY_SPIKE(new Item[]{new Item(19358), new Item(14479)}, new Item(19342), new int[]{13, -1, 0}),
        DRAGON_SQUARE_SHIELD_SPIKE(new Item[]{new Item(19360), new Item(1187)}, new Item(19345), new int[]{13, -1, 0}),
        DRAGON_FULL_HELM_GOLD(new Item[]{new Item(19346), new Item(11335)}, new Item(19336), new int[]{13, -1, 0}),
        DRAGON_PLATELEGS_GOLD(new Item[]{new Item(19348), new Item(4087)}, new Item(19338), new int[]{13, -1, 0}),
        DRAGON_PLATEBODY_GOLD(new Item[]{new Item(19350), new Item(14479)}, new Item(19337), new int[]{13, -1, 0}),
        DRAGON_SQUARE_SHIELD_GOLD(new Item[]{new Item(19352), new Item(1187)}, new Item(19340), new int[]{13, -1, 0}),

        SLAYER_HELMET(new Item[]{new Item(8921), new Item(13277), new Item(4168), new Item(4551)}, new Item(13263), new int[]{18, 50, 0}),
        FULL_SLAYER_HELMET(new Item[]{new Item(13263), new Item(15490), new Item(15488)}, new Item(15492), new int[]{18, 75, 0}),
        RED_SLAYER(new Item[]{new Item(7979), new Item(15492)}, new Item(219647), new int[]{12, -1, 0}),
        PURPLE_SLAYER(new Item[]{new Item(221275), new Item(15492)}, new Item(221264), new int[]{12, -1, 0}),
        TWISTED_SLAYER(new Item[]{new Item(224466), new Item(15492)}, new Item(224370), new int[]{12, -1, 0}),
        GREEN_SLAYER(new Item[]{new Item(7981), new Item(15492)}, new Item(219643), new int[]{12, -1, 0}),
        BLACK_SLAYER(new Item[]{new Item(7980), new Item(15492)}, new Item(219639), new int[]{12, -1, 0}),
        HYDRA_SLAYER(new Item[]{new Item(223077), new Item(15492)}, new Item(223073), new int[]{12, -1, 0}),
        BLUE_SLAYER(new Item[]{new Item(221907), new Item(15492)}, new Item(221888), new int[]{12, -1, 0}),


        //imbues
        BERSERKER_RING_I(new Item[]{new Item(6737), new Item(10506)}, new Item(15220), new int[]{12, -1, 0}),
        ARCHERS_RING_I(new Item[]{new Item(6733), new Item(10506)}, new Item(15019), new int[]{12, -1, 0}),
        SEERS_RING_I(new Item[]{new Item(6731), new Item(10506)}, new Item(15018), new int[]{12, -1, 0}),
        WARRIOR_RING_I(new Item[]{new Item(6735), new Item(10506)}, new Item(15020), new int[]{12, -1, 0}),

        HARMONISED_STAFF(new Item[]{new Item(20568), new Item(20572)}, new Item(20569), new int[]{12, -1, 0}),
        VOLATILE_STAFF(new Item[]{new Item(20568), new Item(20573)}, new Item(20570), new int[]{12, -1, 0}),
        ELDRITCH_STAFF(new Item[]{new Item(20568), new Item(20574)}, new Item(20571), new int[]{12, -1, 0}),

        IMBUED_ZAMORAK_CAPE(new Item[]{new Item(2414), new Item(10506)}, new Item(221795), new int[]{12, -1, 0}),
        IMBUED_SARADOMIN_CAPE(new Item[]{new Item(2412), new Item(10506)}, new Item(221791), new int[]{12, -1, 0}),
        IMBUED_GUTHIX_CAPE(new Item[]{new Item(2413), new Item(10506)}, new Item(221793), new int[]{12, -1, 0}),

        ZAMORAK_MAX(new Item[]{new Item(213280), new Item(221795)}, new Item(221780), new int[]{12, -1, 0}),
        SARADOMIN_MAX(new Item[]{new Item(213280), new Item(221791)}, new Item(221776), new int[]{12, -1, 0}),
        GUTHIX_MAX(new Item[]{new Item(213280), new Item(221793)}, new Item(221784), new int[]{12, -1, 0}),
        INFERNAL_MAX(new Item[]{new Item(213280), new Item(221295)}, new Item(221285), new int[]{12, -1, 0}),
        ASSEMBLER_MAX(new Item[]{new Item(213280), new Item(222109)}, new Item(221898), new int[]{12, -1, 0}),
        FIRE_MAX(new Item[]{new Item(213280), new Item(6570)}, new Item(213329), new int[]{12, -1, 0}),

        ASSEMBLER(new Item[]{new Item(10499), new Item(221907)}, new Item(222109), new int[]{12, -1, 0}),

        NEITIZNOT_FACEGUARD(new Item[]{new Item(10828), new Item(20558)}, new Item(224271), new int[]{13, -1, 0}),
        ZENYTE(new Item[]{new Item(6573), new Item(20566)}, new Item(219496), new int[]{12, -1, 0}),

        //Stronghold Raids
        ELIDINIS_F(new Item[]{new Item(765), new Item(13746)}, new Item(766), new int[]{12, -1, 0}),
        ELIDINIS_OR(new Item[]{new Item(766), new Item(764)}, new Item(767), new int[]{12, -1, 0}),
        KERIS_BREACH(new Item[]{new Item(21078), new Item(21079)}, new Item(21080), new int[]{12, -1, 0}),
        KERIS_CORRUPTION(new Item[]{new Item(21078), new Item(21081)}, new Item(21082), new int[]{12, -1, 0}),
        KERIS_SUN(new Item[]{new Item(21078), new Item(21083)}, new Item(21084), new int[]{12, -1, 0}),
        MASORI_ASSEMBLER(new Item[]{new Item(222109), new Item(21068)}, new Item(21069), new int[]{12, -1, 0}),
        DIVINE_RUNE_POUCH(new Item[]{new Item(212791), new Item(21068)}, new Item(762), new int[]{12, -1, 0}),
        OSMUMTEN_OR(new Item[]{new Item(21063), new Item(21065)}, new Item(21064), new int[]{12, -1, 0}),
        MASORI_MASK_F(new Item[]{new Item(21070), new Item(21065)}, new Item(21073), new int[]{12, -1, 0}),
        MASORI_BODY_F(new Item[]{new Item(21071), new Item(21065)}, new Item(21074), new int[]{12, -1, 0}),
        MASORI_CHAPS_F(new Item[]{new Item(21072), new Item(21065)}, new Item(21075), new int[]{12, -1, 0}),
        MASK_OF_REBIRTH(new Item[]{new Item(21068), new Item(15492)}, new Item(21076), new int[]{12, -1, 0}),

        AGS_OR(new Item[]{new Item(220068), new Item(11694)}, new Item(220368), new int[]{13, -1, 0}),
        BGS_OR(new Item[]{new Item(220071), new Item(11696)}, new Item(220370), new int[]{13, -1, 0}),
        SGS_OR(new Item[]{new Item(220074), new Item(11698)}, new Item(220372), new int[]{13, -1, 0}),
        ZGS_OR(new Item[]{new Item(220077), new Item(11700)}, new Item(220374), new int[]{13, -1, 0}),
        DD_OR(new Item[]{new Item(220143), new Item(20072)}, new Item(219722), new int[]{13, -1, 0}),
        OCCULT_OR(new Item[]{new Item(220065), new Item(18897)}, new Item(219720), new int[]{12, -1, 0}),

        TORTURE_OR(new Item[]{new Item(18896), new Item(220062)}, new Item(220366), new int[]{12, -1, 0}),
        ANGUISH_OR(new Item[]{new Item(18895), new Item(222246)}, new Item(222249), new int[]{12, -1, 0}),
        TORMENTED_OR(new Item[]{new Item(18847), new Item(223348)}, new Item(223444), new int[]{12, -1, 0}),

        CRYSTAL_AXE(new Item[]{new Item(223804), new Item(6739)}, new Item(223673), new int[]{13, -1, 0}),
        CRYSTAL_PICKAXE(new Item[]{new Item(223804), new Item(15259)}, new Item(223680), new int[]{13, -1, 0}),
        CRYSTAL_HARPOON(new Item[]{new Item(223804), new Item(221028)}, new Item(223762), new int[]{13, -1, 0}),

        INFERNAL_AXE(new Item[]{new Item(213233), new Item(6739)}, new Item(213241), new int[]{13, -1, 0}),
        INFERNAL_PICKAXE(new Item[]{new Item(213233), new Item(15259)}, new Item(213243), new int[]{13, -1, 0}),
        INFERNAL_HARPOON(new Item[]{new Item(213233), new Item(221028)}, new Item(221031), new int[]{13, -1, 0}),

        BLUE_SCARF(new Item[]{new Item(9470), new Item(1767)}, new Item(2699), new int[]{12, -1, 0}),
        PURPLE_SCARF(new Item[]{new Item(9470), new Item(1773)}, new Item(2700), new int[]{12, -1, 0}),
        WHITE_SCARF(new Item[]{new Item(9470), new Item(2696)}, new Item(2701), new int[]{12, -1, 0}),
        PINK_SCARF(new Item[]{new Item(9470), new Item(206955)}, new Item(2702), new int[]{12, -1, 0}),
        RED_SCARF(new Item[]{new Item(9470), new Item(1763)}, new Item(2703), new int[]{12, -1, 0}),
        BLACK_SCARF(new Item[]{new Item(9470), new Item(2697)}, new Item(2704), new int[]{12, -1, 0}),
        YELLOW_SCARF(new Item[]{new Item(9470), new Item(1765)}, new Item(2705), new int[]{12, -1, 0}),
        GREEN_SCARF(new Item[]{new Item(9470), new Item(1771)}, new Item(2706), new int[]{12, -1, 0}),

        BLUE_RANGER(new Item[]{new Item(2577), new Item(1767)}, new Item(2693), new int[]{12, -1, 0}),
        PURPLE_RANGER(new Item[]{new Item(2577), new Item(1773)}, new Item(2694), new int[]{12, -1, 0}),
        WHITE_RANGER(new Item[]{new Item(2577), new Item(2696)}, new Item(2692), new int[]{12, -1, 0}),
        PINK_RANGER(new Item[]{new Item(2577), new Item(206955)}, new Item(2691), new int[]{12, -1, 0}),
        RED_RANGER(new Item[]{new Item(2577), new Item(1763)}, new Item(2690), new int[]{12, -1, 0}),
        BLACK_RANGER(new Item[]{new Item(2577), new Item(2697)}, new Item(2689), new int[]{12, -1, 0}),
        YELLOW_RANGER(new Item[]{new Item(2577), new Item(1765)}, new Item(2695), new int[]{12, -1, 0}),
        GREEN_RANGER(new Item[]{new Item(2577), new Item(1771)}, new Item(2698), new int[]{12, -1, 0}),

        BLOOD_FURY(new Item[]{new Item(6585), new Item(224777)}, new Item(224780), new int[]{12, -1, 0}),

        CRYSTAL_HELM_ATTUNED(new Item[]{new Item(223962, 100), new Item(223877), new Item(223878), new Item(223886)}, new Item(223887), new int[]{13, 1, 100}),
        CRYSTAL_HELM_PERFECTED(new Item[]{new Item(223962, 250), new Item(223877, 2), new Item(223878, 2), new Item(223887)}, new Item(223888), new int[]{13, 1, 250}),

        CRYSTAL_BODY_ATTUNED(new Item[]{new Item(223962, 100), new Item(223877), new Item(223878), new Item(223889)}, new Item(223890), new int[]{13, 1, 100}),
        CRYSTAL_BODY_PERFECTED(new Item[]{new Item(223962, 250), new Item(223877, 2), new Item(223878, 2), new Item(223890)}, new Item(223891), new int[]{13, 1, 250}),

        CRYSTAL_LEGS_ATTUNED(new Item[]{new Item(223962, 100), new Item(223877), new Item(223878), new Item(223892)}, new Item(223893), new int[]{13, 1, 100}),
        CRYSTAL_LEGS_PERFECTED(new Item[]{new Item(223962, 250), new Item(223877, 2), new Item(223878, 2), new Item(223893)}, new Item(223894), new int[]{13, 1, 250}),

        CRYSTAL_HALBERD_ATTUNED(new Item[]{new Item(223962, 100), new Item(223871), new Item(223895)}, new Item(223896), new int[]{13, 1, 100}),
        CRYSTAL_HALBERD_PERFECTED(new Item[]{new Item(223962, 250), new Item(223868), new Item(223896)}, new Item(223897), new int[]{13, 1, 250}),

        CRYSTAL_BOW_ATTUNED(new Item[]{new Item(223962, 100), new Item(223871), new Item(223901)}, new Item(223902), new int[]{13, 1, 100}),
        CRYSTAL_BOW_PERFECTED(new Item[]{new Item(223962, 250), new Item(223869), new Item(223902)}, new Item(223903), new int[]{13, 1, 250}),

        CRYSTAL_STAFF_ATTUNED(new Item[]{new Item(223962, 100), new Item(223871), new Item(223898)}, new Item(223899), new int[]{13, 1, 100}),
        CRYSTAL_STAFF_PERFECTED(new Item[]{new Item(223962, 250), new Item(223870), new Item(223899)}, new Item(223900), new int[]{13, 1, 250}),

        CRYSTAL_HELM(new Item[]{new Item(223962, 500), new Item(223956, 1)}, new Item(223971), new int[]{13, 1, 100}),
        CRYSTAL_BODY(new Item[]{new Item(223962, 1500), new Item(223956, 3)}, new Item(223975), new int[]{13, 1, 300}),
        CRYSTAL_LEGS(new Item[]{new Item(223962, 1000), new Item(223956, 2)}, new Item(223979), new int[]{13, 1, 200}),
        CRYSTAL_HALBERD(new Item[]{new Item(223962, 1000), new Item(223956, 2)}, new Item(223896), new int[]{13, 1, 200}),
        CRYSTAL_BOW(new Item[]{new Item(223962, 1000), new Item(223956, 2)}, new Item(223902), new int[]{13, 1, 200}),
        CRYSTAL_STAFF(new Item[]{new Item(223962, 1000), new Item(223956, 2)}, new Item(223899), new int[]{13, 1, 200}),


        SOUL_ACP(new Item[]{new Item(11718), new Item(21031)}, new Item(21007), new int[]{12, -1, 0}),
        SOUL_ACS(new Item[]{new Item(11720), new Item(21031)}, new Item(21008), new int[]{12, -1, 0}),
        SOUL_AH(new Item[]{new Item(11722), new Item(21031)}, new Item(21009), new int[]{12, -1, 0}),

        BLOOD_ACP(new Item[]{new Item(11718), new Item(21032)}, new Item(21010), new int[]{12, -1, 0}),
        BLOOD_ACS(new Item[]{new Item(11720), new Item(21032)}, new Item(21011), new int[]{12, -1, 0}),
        BLOOD_AH(new Item[]{new Item(11722), new Item(21032)}, new Item(21012), new int[]{12, -1, 0}),

        GILDED_ACP(new Item[]{new Item(11718), new Item(21033)}, new Item(21013), new int[]{12, -1, 0}),
        GILDED_ACS(new Item[]{new Item(11720), new Item(21033)}, new Item(21014), new int[]{12, -1, 0}),
        GILDED_AH(new Item[]{new Item(11722), new Item(21033)}, new Item(21015), new int[]{12, -1, 0}),

        PINK_ACP(new Item[]{new Item(11718), new Item(21034)}, new Item(21025), new int[]{12, -1, 0}),
        PINK_ACS(new Item[]{new Item(11720), new Item(21034)}, new Item(21026), new int[]{12, -1, 0}),
        PINK_AH(new Item[]{new Item(11722), new Item(21034)}, new Item(21027), new int[]{12, -1, 0}),

        TAINTED_ACP(new Item[]{new Item(11718), new Item(2050)}, new Item(2045), new int[]{12, -1, 0}),
        TAINTED_ACS(new Item[]{new Item(11720), new Item(2050)}, new Item(2046), new int[]{12, -1, 0}),
        TAINTED_AH(new Item[]{new Item(11722), new Item(2050)}, new Item(2047), new int[]{12, -1, 0}),

        SOUL_BCP(new Item[]{new Item(11724), new Item(21031)}, new Item(21016), new int[]{12, -1, 0}),
        SOUL_TASSET(new Item[]{new Item(11726), new Item(21031)}, new Item(21017), new int[]{12, -1, 0}),
        SOUL_BBOOTS(new Item[]{new Item(11728), new Item(21031)}, new Item(21018), new int[]{12, -1, 0}),
        SOUL_FACEGUARD(new Item[]{new Item(224271), new Item(21031)}, new Item(2711), new int[]{12, -1, 0}),

        BLOOD_BCP(new Item[]{new Item(11724), new Item(21032)}, new Item(21019), new int[]{12, -1, 0}),
        BLOOD_TASSET(new Item[]{new Item(11726), new Item(21032)}, new Item(21020), new int[]{12, -1, 0}),
        BLOOD_BBOOTS(new Item[]{new Item(11728), new Item(21032)}, new Item(21021), new int[]{12, -1, 0}),
        BLOOD_FACEGUARD(new Item[]{new Item(224271), new Item(21032)}, new Item(2710), new int[]{12, -1, 0}),

        GILDED_BCP(new Item[]{new Item(11724), new Item(21033)}, new Item(21022), new int[]{12, -1, 0}),
        GILDED_TASSET(new Item[]{new Item(11726), new Item(21033)}, new Item(21023), new int[]{12, -1, 0}),
        GILDED_BBOOTS(new Item[]{new Item(11728), new Item(21033)}, new Item(21024), new int[]{12, -1, 0}),
        GILDED_FACEGUARD(new Item[]{new Item(224271), new Item(21033)}, new Item(2712), new int[]{12, -1, 0}),

        PINK_BCP(new Item[]{new Item(11724), new Item(21034)}, new Item(21028), new int[]{12, -1, 0}),
        PINK_TASSET(new Item[]{new Item(11726), new Item(21034)}, new Item(21029), new int[]{12, -1, 0}),
        PINK_BBOOTS(new Item[]{new Item(11728), new Item(21034)}, new Item(21030), new int[]{12, -1, 0}),
        PINK_FACEGUARD(new Item[]{new Item(224271), new Item(21034)}, new Item(2713), new int[]{12, -1, 0}),

        TAINTED_BCP(new Item[]{new Item(11724), new Item(2050)}, new Item(2042), new int[]{12, -1, 0}),
        TAINTED_TASSET(new Item[]{new Item(11726), new Item(2050)}, new Item(2043), new int[]{12, -1, 0}),
        TAINTED_BBOOTS(new Item[]{new Item(11728), new Item(2050)}, new Item(2044), new int[]{12, -1, 0}),
        TAINTED_FACEGUARD(new Item[]{new Item(224271), new Item(2050)}, new Item(2716), new int[]{12, -1, 0}),

        SOUL_BRIMSTONE(new Item[]{new Item(222951), new Item(21031)}, new Item(21040), new int[]{12, -1, 0}),
        SOUL_GLOVES(new Item[]{new Item(7462), new Item(21031)}, new Item(21044), new int[]{12, -1, 0}),

        BLOOD_BRIMSTONE(new Item[]{new Item(222951), new Item(21032)}, new Item(21041), new int[]{12, -1, 0}),
        BLOOD_GLOVES(new Item[]{new Item(7462), new Item(21032)}, new Item(21045), new int[]{12, -1, 0}),

        GILDED_BRIMSTONE(new Item[]{new Item(222951), new Item(21033)}, new Item(21042), new int[]{12, -1, 0}),
        GILDED_GLOVES(new Item[]{new Item(7462), new Item(21033)}, new Item(21046), new int[]{12, -1, 0}),

        PINK_BRIMSTONE(new Item[]{new Item(222951), new Item(21034)}, new Item(21043), new int[]{12, -1, 0}),
        PINK_GLOVES(new Item[]{new Item(7462), new Item(21034)}, new Item(21047), new int[]{12, -1, 0}),

        TAINTED_BRIMSTONE(new Item[]{new Item(222951), new Item(2050)}, new Item(2056), new int[]{12, -1, 0}),
        TAINTED_GLOVES(new Item[]{new Item(7462), new Item(2050)}, new Item(2057), new int[]{12, -1, 0}),

        SOUL_ANC_HAT(new Item[]{new Item(18888), new Item(21031)}, new Item(18905), new int[]{12, -1, 0}),
        SOUL_ANC_TOP(new Item[]{new Item(18889), new Item(21031)}, new Item(18906), new int[]{12, -1, 0}),
        SOUL_ANC_BOTTOMS(new Item[]{new Item(18890), new Item(21031)}, new Item(18907), new int[]{12, -1, 0}),

        BLOOD_ANC_HAT(new Item[]{new Item(18888), new Item(21032)}, new Item(18899), new int[]{12, -1, 0}),
        BLOOD_ANC_TOP(new Item[]{new Item(18889), new Item(21032)}, new Item(18900), new int[]{12, -1, 0}),
        BLOOD_ANC_BOTTOMS(new Item[]{new Item(18890), new Item(21032)}, new Item(18901), new int[]{12, -1, 0}),

        GILDED_ANC_HAT(new Item[]{new Item(18888), new Item(21033)}, new Item(18892), new int[]{12, -1, 0}),
        GILDED_ANC_TOP(new Item[]{new Item(18889), new Item(21033)}, new Item(18893), new int[]{12, -1, 0}),
        GILDED_ANC_BOTTOMS(new Item[]{new Item(18890), new Item(21033)}, new Item(18894), new int[]{12, -1, 0}),

        PINK_ANC_HAT(new Item[]{new Item(18888), new Item(21034)}, new Item(2765), new int[]{12, -1, 0}),
        PINK_ANC_TOP(new Item[]{new Item(18889), new Item(21034)}, new Item(2766), new int[]{12, -1, 0}),
        PINK_ANC_BOTTOMS(new Item[]{new Item(18890), new Item(21034)}, new Item(2767), new int[]{12, -1, 0}),

        TAINTED_ANC_HAT(new Item[]{new Item(18888), new Item(2050)}, new Item(2051), new int[]{12, -1, 0}),
        TAINTED_ANC_TOP(new Item[]{new Item(18889), new Item(2050)}, new Item(2052), new int[]{12, -1, 0}),
        TAINTED_ANC_BOTTOMS(new Item[]{new Item(18890), new Item(2050)}, new Item(2053), new int[]{12, -1, 0}),

        FESTIVE_BCP(new Item[]{new Item(11724), new Item(225316)}, new Item(2768), new int[]{12, -1, 0}),
        FESTIVE_TASSET(new Item[]{new Item(11726), new Item(225316)}, new Item(2769), new int[]{12, -1, 0}),
        FESTIVE_BBOOTS(new Item[]{new Item(11728), new Item(225316)}, new Item(2770), new int[]{12, -1, 0}),
        FESTIVE_SLAYER_HELM(new Item[]{new Item(13263), new Item(225316)}, new Item(2771), new int[]{12, -1, 0}),
        FESTIVE_FACEGUARD(new Item[]{new Item(224271), new Item(225316)}, new Item(2772), new int[]{12, -1, 0}),
        FESTIVE_INFERNAL_MAX_CAPE(new Item[]{new Item(221285), new Item(225316)}, new Item(2773), new int[]{12, -1, 0}),
        FESTIVE_FIRE_MAX_CAPE(new Item[]{new Item(213329), new Item(225316)}, new Item(2774), new int[]{12, -1, 0}),
        FESTIVE_GLOVES(new Item[]{new Item(7462), new Item(225316)}, new Item(2775), new int[]{12, -1, 0}),


        INFERNO_ADZE(new Item[]{new Item(13659), new Item(213241), new Item(213243), new Item(221031)}, new Item(13661), new int[]{12, -1, 0}),


        CRYSTAL_CLAWS(new Item[]{new Item(14484), new Item(224002)}, new Item(224000), new int[]{12, -1, 0}),
        ZAROS_GODSWORD(new Item[]{new Item(11694), new Item(11696), new Item(11698), new Item(11700), new Item(224003)}, new Item(21035), new int[]{13, -1, 0}),

        PRIMAL_HELM(new Item[]{new Item(16691), new Item(16693), new Item(16695), new Item(16697), new Item(16699), new Item(16701), new Item(16703), new Item(16705), new Item(16707), new Item(16709)}, new Item(16711), new int[]{13, 99, 10000}),
        PRIMAL_BODY(new Item[]{new Item(17239), new Item(17241), new Item(17243), new Item(17245), new Item(17247), new Item(17249), new Item(17251), new Item(17253), new Item(17255), new Item(17257)}, new Item(17259), new int[]{13, 99, 10000}),
        PRIMAL_LEGS(new Item[]{new Item(16669), new Item(16671), new Item(16673), new Item(16675), new Item(16677), new Item(16679), new Item(16681), new Item(16683), new Item(16685), new Item(16687)}, new Item(16689), new int[]{13, 99, 10000}),
        PRIMAL_RAPIER(new Item[]{new Item(16935), new Item(16937), new Item(16939), new Item(16941), new Item(16943), new Item(16945), new Item(16947), new Item(16949), new Item(16951), new Item(16953)}, new Item(16955), new int[]{13, 99, 10000}),
        PRIMAL_LONG(new Item[]{new Item(16383), new Item(16385), new Item(16387), new Item(16389), new Item(16391), new Item(16393), new Item(16395), new Item(16397), new Item(16399), new Item(16401)}, new Item(16403), new int[]{13, 99, 10000}),
        PRIMAL_MAUL(new Item[]{new Item(16405), new Item(16407), new Item(16409), new Item(16411), new Item(16413), new Item(16415), new Item(16417), new Item(16419), new Item(16421), new Item(16423)}, new Item(16425), new int[]{13, 99, 10000}),
        PRIMAL_SHIELD(new Item[]{new Item(17341), new Item(17343), new Item(17345), new Item(17347), new Item(17349), new Item(17351), new Item(17353), new Item(17355), new Item(17357), new Item(17359)}, new Item(17361), new int[]{13, 99, 10000}),
        PRIMAL_GLOVES(new Item[]{new Item(16273), new Item(16275), new Item(16277), new Item(16279), new Item(16281), new Item(16283), new Item(16285), new Item(16287), new Item(16289), new Item(16291)}, new Item(16293), new int[]{13, 99, 10000}),
        PRIMAL_BOOTS(new Item[]{new Item(16339), new Item(16341), new Item(16343), new Item(16345), new Item(16347), new Item(16349), new Item(16351), new Item(16353), new Item(16355), new Item(16357)}, new Item(16359), new int[]{13, 99, 10000}),
        SAGIT_COIF(new Item[]{new Item(17041), new Item(17043), new Item(17045), new Item(17047), new Item(17049), new Item(17051), new Item(17053), new Item(17055), new Item(17057), new Item(17059)}, new Item(17061), new int[]{13, 99, 10000}),
        SAGIT_BODY(new Item[]{new Item(17173), new Item(17175), new Item(17177), new Item(17179), new Item(17181), new Item(17183), new Item(17185), new Item(17187), new Item(17189), new Item(17191)}, new Item(17193), new int[]{13, 99, 10000}),
        SAGIT_CHAPS(new Item[]{new Item(17319), new Item(17321), new Item(17323), new Item(17325), new Item(17327), new Item(17329), new Item(17331), new Item(17333), new Item(17335), new Item(17337)}, new Item(17339), new int[]{13, 99, 10000}),
        SAGIT_SHORT(new Item[]{new Item(16867), new Item(16869), new Item(16871), new Item(16873), new Item(16875), new Item(16877), new Item(16879), new Item(16881), new Item(16883), new Item(16885)}, new Item(16887), new int[]{13, 99, 10000}),
        SAGIT_VAMPS(new Item[]{new Item(17195), new Item(17197), new Item(17199), new Item(17201), new Item(17203), new Item(17205), new Item(17207), new Item(17209), new Item(17211), new Item(17213)}, new Item(17215), new int[]{13, 99, 10000}),
        SAGIT_BOOTS(new Item[]{new Item(17297), new Item(17299), new Item(17301), new Item(17303), new Item(17305), new Item(17307), new Item(17309), new Item(17311), new Item(17313), new Item(17315)}, new Item(17317), new int[]{13, 99, 10000}),
        CELEST_HOOD(new Item[]{new Item(16735), new Item(16737), new Item(16739), new Item(16741), new Item(16743), new Item(16745), new Item(16747), new Item(16749), new Item(16751), new Item(16753)}, new Item(16755), new int[]{13, 99, 10000}),
        CELEST_TOP(new Item[]{new Item(17217), new Item(17219), new Item(17221), new Item(17223), new Item(17225), new Item(17227), new Item(17229), new Item(17231), new Item(17233), new Item(17235)}, new Item(17237), new int[]{13, 99, 10000}),
        CELEST_BOTTOM(new Item[]{new Item(16845), new Item(16847), new Item(16849), new Item(16851), new Item(16853), new Item(16855), new Item(16857), new Item(16859), new Item(16861), new Item(16863)}, new Item(16865), new int[]{13, 99, 10000}),
        CELEST_STAFF(new Item[]{new Item(16977), new Item(16979), new Item(16981), new Item(16983), new Item(16985), new Item(16987), new Item(16989), new Item(16991), new Item(16993), new Item(16995)}, new Item(16997), new int[]{13, 99, 10000}),
        CELEST_GLOVES(new Item[]{new Item(17151), new Item(17153), new Item(17155), new Item(17157), new Item(17159), new Item(17161), new Item(17163), new Item(17165), new Item(17167), new Item(17169)}, new Item(17171), new int[]{13, 99, 10000}),
        CELEST_SHOES(new Item[]{new Item(16911), new Item(16913), new Item(16915), new Item(16917), new Item(16919), new Item(16921), new Item(16923), new Item(16925), new Item(16927), new Item(16929)}, new Item(16931), new int[]{13, 99, 10000}),


        ;


        ItemForgeData(Item[] requiredItems, Item product, int[] skillRequirement) {
            this.requiredItems = requiredItems;
            this.product = product;
            this.skillRequirement = skillRequirement;
        }

        private final Item[] requiredItems;
        private final Item product;
        private final int[] skillRequirement;

        public static ItemForgeData getDataForItems(int item1, int item2) {
            for (ItemForgeData shieldData : ItemForgeData.values()) {
                int found = 0;
                for (Item it : shieldData.requiredItems) {
                    if (it.getId() == item1 || it.getId() == item2)
                        found++;
                }
                if (found >= 2)
                    return shieldData;
            }
            return null;
        }
    }
}
