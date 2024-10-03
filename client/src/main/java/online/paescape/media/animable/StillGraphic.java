package online.paescape.media.animable;


import online.paescape.cache.media.SpotAnim;
import online.paescape.media.FrameReader;

public final class StillGraphic extends Animable {

    public final int plane;
    public final int xTile;
    public final int yTile;
    public final int drawHeight;
    public final int startTime;
    private final SpotAnim gfx;
    public boolean animFinished;
    private int currentFrame;
    private int tick;

    public StillGraphic(int i, int j, int l, int gfxId, int height, int k1,
                        int l1) {
        animFinished = false;
        gfx = SpotAnim.cache[gfxId];
        plane = i;
        xTile = l1;
        yTile = k1;
        drawHeight = height;
        startTime = j + l;
        animFinished = false;
    }

    public Model getRotatedModel() {
        Model model = gfx.getModel();
        if (model == null)
            return null;
        int frameToPlay = gfx.animation.frameIDs[currentFrame];
        Model animableModel = new Model(true, FrameReader.isNullFrame(frameToPlay), false, model);
        if (!animFinished) {
            animableModel.createBones();
            animableModel.applyTransform(frameToPlay, gfx.animation.dataType);
            animableModel.triangleSkin = null;
            animableModel.vertexSkin = null;
        }
        if (gfx.sizeXY != 128 || gfx.sizeZ != 128)
            animableModel.scaleT(gfx.sizeXY, gfx.sizeXY, gfx.sizeZ);
        if (gfx.rotation != 0) {
            if (gfx.rotation == 90)
                animableModel.rotateBy90();
            if (gfx.rotation == 180) {
                animableModel.rotateBy90();
                animableModel.rotateBy90();
            }
            if (gfx.rotation == 270) {
                animableModel.rotateBy90();
                animableModel.rotateBy90();
                animableModel.rotateBy90();
            }
        }
        animableModel.light(64 + gfx.shadow, 5050 + gfx.lightness, -90, -580, -90, true);
        return animableModel;
    }

    public void processAnimation(int i) {
        for (tick += i; tick > gfx.animation.getFrameLength(currentFrame); ) {
            tick -= gfx.animation.getFrameLength(currentFrame) + 1;
            currentFrame++;
            if (currentFrame >= gfx.animation.frameCount && (currentFrame < 0 || currentFrame >= gfx.animation.frameCount)) {
                currentFrame = 0;
                animFinished = true;
            }
        }

    }
}
