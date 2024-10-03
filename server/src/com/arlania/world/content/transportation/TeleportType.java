package com.arlania.world.content.transportation;

import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.util.RandomUtility;

public enum TeleportType {

    NORMAL(3, new Animation(8939, 20), new Animation(8941), new Graphic(1745), null),
    THRONE(3, new Animation(2939, 15), Animations.DEFAULT_RESET_ANIMATION, new Graphic(94), null),
    ANCIENT(5, new Animation(9599), new Animation(8941), new Graphic(1681, 0), null),
    LUNAR(4, new Animation(9606), new Animation(9013), new Graphic(1685), null),
    TELE_TAB(2, new Animation(4731), Animations.DEFAULT_RESET_ANIMATION, new Graphic(678), null),
    RING_TELE(2, new Animation(9603), Animations.DEFAULT_RESET_ANIMATION, new Graphic(1684), null),
    CLIMB(2, new Animation(828), Animations.DEFAULT_RESET_ANIMATION, null, null),
    LEVER(-1, null, null, null, null),
    PURO_PURO(9, new Animation(6601), Animations.DEFAULT_RESET_ANIMATION, new Graphic(1118), null);

    TeleportType(int startTick, Animation startAnim, Animation endAnim, Graphic startGraphic, Graphic endGraphic) {
        this.startTick = startTick;
        this.startAnim = startAnim;
        this.endAnim = endAnim;
        this.startGraphic = startGraphic;
        this.endGraphic = endGraphic;
    }

    private final Animation startAnim;
    private final Animation endAnim;
    private final Graphic startGraphic;
    private final Graphic endGraphic;
    private final int startTick;

    public Animation getStartAnimation() {
        return startAnim;
    }

    public Animation getEndAnimation() {
        return endAnim;
    }

    public Graphic getStartGraphic() {
        return this == NORMAL ? new Graphic(1512 + RandomUtility.inclusiveRandom(2)) : startGraphic;
    }

    public Graphic getEndGraphic() {
        return endGraphic;
    }

    public int getStartTick() {
        return startTick;
    }

    static class Animations {
        static Animation DEFAULT_RESET_ANIMATION = new Animation(65535);
    }
}
