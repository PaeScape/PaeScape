package com.arlania.world.content.skill.impl.agility;

import com.arlania.model.*;
import com.arlania.world.entity.impl.player.Player;

import java.util.Objects;

public enum ShortcutData {

    BARBARIAN_OUTPOST_TO_LIGHTHOUSE(new Position(2522, 3597, 0), 304551, false, new Position(2514, 3619, 0), 1, Regions.KANDARIN, Regions.FREMENNIK,null),
    LIGHTHOUSE_TO_BARBARIAN_OUTPOST(new Position(2514, 3617, 0), 304558, false,  new Position(2522, 3595, 0), 1, Regions.KANDARIN, Regions.FREMENNIK,null),
    FALADOR_TO_TAVERLY(new Position(2935, 3355, 0), 304558, true,  new Position(2936, 3355, 0), 5, Regions.ASGARNIA, Regions.ASGARNIA,  new Position(2934, 3355, 0)),
    YANILLE_GRAPPLE_IN(new Position(2556, 3075, 0), 317047, false,  new Position(2936, 3071, 0), 5, Regions.KANDARIN, Regions.KANDARIN,  null),
    YANILLE_GRAPPLE_OUT(new Position(2556, 3072, 0), 317047, false,  new Position(2556, 3076, 0), 5, Regions.KANDARIN, Regions.KANDARIN,  null),
    LUMBY_ALKHARID(new Position(3244, 3179, 0), 317039, false,  new Position(3259, 3179, 0), 8, Regions.MISTHALIN, Regions.KHARIDIAN,  null),
    ALKHARID_LUMBY(new Position(3260, 3178, 0), 317036, false,  new Position(3246, 3179, 0), 8, Regions.KHARIDIAN, Regions.MISTHALIN,  null),
    CORSAIR_COVE_TO_FELDIP_HILLS(new Position(2546, 2872, 0), 331757, true,  new Position(2546, 2873, 0), 10, Regions.KANDARIN, Regions.KANDARIN,  new Position(2546, 2871, 0)),
    MOSS_GIANT_ISLAND_TO_BRIMHAVEN(new Position(2703, 3205, 0), 323569, false, new Position(2709, 3209, 0), 10, Regions.KARAMJA, Regions.KARAMJA,null),
    BRIMHAVEN_TO_MOSS_GIANT_ISLAND(new Position(2705, 3209, 0), 323568, false,  new Position(2705, 3205, 0), 10, Regions.KARAMJA, Regions.KARAMJA,null),
    FALADOR_GRAPPLE_OUT(new Position(3032, 3389, 0), 317050, false, new Position(3033, 3390, 0), 11, Regions.ASGARNIA, Regions.ASGARNIA,null),
    FALADOR_GRAPPLE_IN(new Position(3033, 3390, 0), 317049, false,  new Position(3032, 3388, 0), 11, Regions.ASGARNIA, Regions.ASGARNIA,null),
    BRIMHAVEN_DUNGEON_STEPPING_STONES_NORTH(new Position(2649, 9561, 0), 321738, false, new Position(2647, 9557, 0), 12, Regions.KARAMJA, Regions.KARAMJA,null),
    BRIMHAVEN_DUNGEON_STEPPING_STONES_SOUTH(new Position(2647, 9558, 0), 321739, false,  new Position(2649, 9562, 0), 12, Regions.KARAMJA, Regions.KARAMJA,null),
    VARROCK_FENCE_SOUTH(new Position(3240, 3335, 0), 316518, true,  new Position(3240, 3335, 0), 13, Regions.MISTHALIN, Regions.MISTHALIN,  new Position(3240, 3334, 0)),
    BURTHROPE_TO_CHAOS_TEMPLE(new Position(2928, 3521, 0), 316468, false, new Position(2927, 3523, 0), 14, Regions.ASGARNIA, Regions.ASGARNIA, null),
    CHAOS_TEMPLE_TO_BURTHROPE(new Position(2927, 3522, 0), 316468, false, new Position(2928, 3520, 0), 14, Regions.ASGARNIA, Regions.ASGARNIA, null),


    GRAND_TREE_TO_BARBARIAN_OUTPOST(new Position(2487, 3515, 0), 316534, false, new Position(2489, 3521, 0), 37, Regions.KANDARIN, Regions.KANDARIN, null),
    BARBARIAN_OUTPOST_TO_GRAND_TREE(new Position(2489, 3520, 0), 316535, false, new Position(2486, 3515, 0), 37, Regions.KANDARIN, Regions.KANDARIN, null);

    ShortcutData(Position startPosition, int object, boolean commonObject, Position endPosition, int reqLvl, Regions Region1, Regions Region2, Position optionalPosition) {
        this.startPosition = startPosition;
        this.object = object;
        this.commonObject = commonObject;
        this.endPosition = endPosition;
        this.reqLvl = reqLvl;
        this.Region1 = Region1;
        this.Region2 = Region2;
        this.optionalPosition = optionalPosition;
    }

    private final Position startPosition;
    private final int object;
    private final boolean commonObject;
    private final Position endPosition;
    private final int reqLvl;
    private final Regions Region1;
    private final Regions Region2;
    private final Position optionalPosition;

    public int getObject() {
        return object;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public int getReqLvl() {
        return reqLvl;
    }

    public void cross(final Player player, final ShortcutData shortcutData) {

        Position endPosition = shortcutData.endPosition;

        if(shortcutData.commonObject) {
            if ((player.getPosition().getX() > shortcutData.getStartPosition().getX()) || (player.getPosition().getY() > shortcutData.getStartPosition().getY())) {
                endPosition = shortcutData.optionalPosition;
            }
        }

        player.moveTo(endPosition);
        player.setCrossingObstacle(false);
        player.getSkillManager().addExperience(Skill.AGILITY, shortcutData.getReqLvl() / 10);
    }

    public static ShortcutData forPosition(Position startPosition) {

        for (ShortcutData shortcutData : ShortcutData.values()) {
            if (Objects.equals(shortcutData.getStartPosition(), startPosition))
                return shortcutData;
        }

        return null;
    }
}