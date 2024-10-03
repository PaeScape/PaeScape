package online.paescape.media.animable.entity;


import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.Import;
import net.runelite.rs.api.*;
import online.paescape.Client;
import online.paescape.cache.def.NPCDef;
import online.paescape.cache.media.Animation;
import online.paescape.cache.media.SpotAnim;
import online.paescape.media.FrameReader;
import online.paescape.media.animable.Model;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class NPC extends Entity implements RSNPC {

    public NPCDef desc;

    public NPC() {
    }

    private Model getAnimatedModel() {

        if (super.anim >= 0 && super.animationDelay == 0) {
            Animation animation = Animation.anims[super.anim];
            int currentFrame = animation.frameIDs[super.currentAnimFrame];
            int nextFrame = animation.frameIDs[super.nextAnimationFrame];
            int cycle1 = animation.delays[super.currentAnimFrame];
            int cycle2 = super.anInt1528;
            //int frame = Animation.anims[super.anim].anIntArray353[super.anInt1527];
            int i1 = -1;
            if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511)
                i1 = Animation.anims[super.anInt1517].frameIDs[super.currentForcedAnimFrame];
            return desc.method164(i1, currentFrame, Animation.anims[super.anim].animationFlowControl, nextFrame, cycle1, cycle2);
        }

        int currentFrame = -1;
        int nextFrame = -1;
        int cycle1 = 0;
        int cycle2 = 0;
        if (super.anInt1517 >= 0) {
            Animation animation = Animation.anims[super.anInt1517];
            currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
            nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
            cycle1 = animation.delays[super.currentForcedAnimFrame];
            cycle2 = super.anInt1519;
        }
        return desc.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
    }

    public Model getRotatedModel() {
        if (desc == null)
            return null;
        Model model = getAnimatedModel();
        if (model == null)
            return null;
        super.height = model.modelBaseY;
        if (super.anInt1520 != -1 && super.currentAnim != -1) {
            SpotAnim spotAnim = SpotAnim.cache[super.anInt1520];
            Model model_1 = spotAnim.getModel();
            if (model_1 != null) {
                int j = spotAnim.animation.frameIDs[super.currentAnim];
                Model model_2 = new Model(true, FrameReader.isNullFrame(j), false, model_1);
                model_2.translate(0, -super.graphicHeight, 0);
                model_2.createBones();
                model_2.applyTransform(j, spotAnim.animation.dataType);
                model_2.triangleSkin = null;
                model_2.vertexSkin = null;
                if (spotAnim.sizeXY != 128 || spotAnim.sizeZ != 128)
                    model_2.scaleT(spotAnim.sizeXY, spotAnim.sizeXY, spotAnim.sizeZ);
                model_2.light(64 + spotAnim.shadow, 850 + spotAnim.lightness, -30, -50, -30, true);
                Model[] aModel = {
                        model, model_2
                };
                model = new Model(aModel);
            }
        }
        if (desc.squaresNeeded == 1)
            model.rendersWithinOneTile = true;
        return model;
    }

    public boolean isVisible() {
        return desc != null;
    }


    @Override
    public int getCombatLevel() {
        return desc.combatLevel;
    }

    @Nullable
    @Override
    public NPCComposition getTransformedComposition() {
        return null;
    }

    @Override
    public void onDefinitionChanged(NPCComposition composition) {

    }


    @Override
    public int getId() {
        return desc.id;
    }

    @Nullable
    @Override
    public String getName() {
        return desc.name;
    }

    @Override
    public Actor getInteracting() {
        return null;
    }

    @Override
    public int getHealthRatio() {
        return 0;
    }

    @Override
    public int getHealthScale() {
        return 0;
    }

    @Override
    public WorldPoint getWorldLocation() {
        return WorldPoint.fromLocal(Client.instance, getX(), getY(), Client.instance.getPlane());
    }

    @Override
    public LocalPoint getLocalLocation() {
        return new LocalPoint(this.x, this.y);
    }

    @Override
    public void setIdleRotateLeft(int animationID) {

    }

    @Override
    public void setIdleRotateRight(int animationID) {

    }

    @Override
    public void setWalkAnimation(int animationID) {

    }

    @Override
    public void setWalkRotateLeft(int animationID) {

    }

    @Override
    public void setWalkRotateRight(int animationID) {

    }

    @Override
    public void setWalkRotate180(int animationID) {

    }

    @Override
    public void setRunAnimation(int animationID) {

    }

    @Override
    public Polygon getCanvasTilePoly() {
        return Perspective.getCanvasTilePoly(online.paescape.Client.instance, this.getLocalLocation());
    }

    @Nullable
    @Override
    public net.runelite.api.Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset) {
        return Perspective.getCanvasTextLocation(online.paescape.Client.instance, graphics, getLocalLocation(), text, zOffset);
    }

    @Override
    public net.runelite.api.Point getCanvasImageLocation(BufferedImage image, int zOffset) {
        return Perspective.getCanvasImageLocation(Client.instance, getLocalLocation(), image, zOffset);
    }

    @Override
    public net.runelite.api.Point getCanvasSpriteLocation(SpritePixels sprite, int zOffset) {
        return Perspective.getCanvasSpriteLocation(Client.instance, getLocalLocation(), sprite, zOffset);
    }

    @Override
    public Point getMinimapLocation() {
        return null;
    }

    @Override
    public Shape getConvexHull() {
        RSModel model = getModel();
        if (model == null)
        {
            return null;
        }

        int tileHeight = Perspective.getTileHeight(Client.instance, new LocalPoint(getX(), getY()), Client.instance.getPlane());

        return model.getConvexHull(getX(), getY(), getOrientation(), tileHeight);
    }



    @Override
    public WorldArea getWorldArea() {
        return new WorldArea(getWorldLocation(), 1, 1);
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public boolean isMoving() {
        return false;
    }

    @Override
    public Polygon getConvexHullPoly() {
        RSModel model = getModel();
        if (model == null)
        {
            return null;
        }
        return model.getConvexHullPoly(getX(), getY(), getOrientation());
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public int getRSInteracting() {
        return 0;
    }

    @Override
    public String getOverheadText() {
        return null;
    }

    @Override
    public void setOverheadText(String overheadText) {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int[] getPathX() {
        return pathX;
    }

    @Override
    public int[] getPathY() {
        return pathY;
    }

    @Override
    public int getAnimation() {
        return desc.standAnim;
    }

    @Override
    public void setAnimation(int animation) {

    }

    @Override
    public int getAnimationFrame() {
        return 0;
    }

    @Override
    public int getActionFrame() {
        return 0;
    }

    @Override
    public void setAnimationFrame(int frame) {

    }

    @Override
    public void setActionFrame(int frame) {

    }

    @Override
    public int getActionFrameCycle() {
        return 0;
    }

    @Override
    public int getGraphic() {
        return 0;
    }

    @Override
    public void setGraphic(int id) {

    }

    @Override
    public int getSpotAnimFrame() {
        return 0;
    }

    @Override
    public void setSpotAnimFrame(int id) {

    }

    @Override
    public int getSpotAnimationFrameCycle() {
        return 0;
    }

    @Override
    public int getIdlePoseAnimation() {
        return 0;
    }

    @Override
    public void setIdlePoseAnimation(int animation) {

    }

    @Override
    public int getPoseAnimation() {
        return 0;
    }

    @Override
    public void setPoseAnimation(int animation) {

    }

    @Override
    public int getPoseFrame() {
        return 0;
    }

    @Override
    public void setPoseFrame(int frame) {

    }

    @Override
    public int getPoseFrameCycle() {
        return 0;
    }

    @Override
    public int getLogicalHeight() {
        return 0;
    }

    @Override
    public int getOrientation() {
        return 0;
    }

    @Override
    public int getCurrentOrientation() {
        return 0;
    }

    @Override
    public RSIterableNodeDeque getHealthBars() {
        return null;
    }

    @Override
    public int[] getHitsplatValues() {
        return new int[0];
    }

    @Override
    public int[] getHitsplatTypes() {
        return new int[0];
    }

    @Override
    public int[] getHitsplatCycles() {
        return new int[0];
    }

    @Override
    public int getIdleRotateLeft() {
        return 0;
    }

    @Override
    public int getIdleRotateRight() {
        return 0;
    }

    @Override
    public int getWalkAnimation() {
        return anim;
    }

    @Override
    public int getWalkRotate180() {
        return 0;
    }

    @Override
    public int getWalkRotateLeft() {
        return 0;
    }

    @Override
    public int getWalkRotateRight() {
        return 0;
    }

    @Override
    public int getRunAnimation() {
        return runAnimation;
    }

    @Override
    public void setDead(boolean dead) {

    }

    @Override
    public int getPathLength() {
        return pathLength;
    }

    @Override
    public int getOverheadCycle() {
        return 0;
    }

    @Override
    public void setOverheadCycle(int cycle) {

    }

    @Override
    public int getPoseAnimationFrame() {
        return 0;
    }

    @Override
    public void setPoseAnimationFrame(int frame) {

    }

    @Override
    public RSNPCComposition getComposition() {
        return desc;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int id) {
        index = id;
    }

    @Override
    public RSNode getNext() {
        return next;
    }

    @Override
    public long getHash() {
        return 0;
    }

    @Override
    public RSNode getPrevious() {
        return null;
    }

    @Override
    public void onUnlink() {

    }
    @Override
    public int getModelHeight() {
        return height;
    }

//    @Override
//    public void setModelHeight(int modelHeight) {
//
//    }

    @Override
    public RSModel getModel() {
        return getAnimatedModel();
    }


}
