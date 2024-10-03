package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm;


import com.arlania.GameSettings;
import com.arlania.model.Animation;
import com.arlania.model.Direction;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;

public class OlmAnimations {

    // 1350s

    /*
     * OLM ANIMS
     */
    final public static Animation nothing = new Animation(7334 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation goingUp = new Animation(7335 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation faceMiddle = new Animation(7336 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation faceRight = new Animation(7337 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation faceLeft = new Animation(7338 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation middleToRight = new Animation(7339 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation rightToMiddle = new Animation(7340 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation middleToLeft = new Animation(7341 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation leftToMiddle = new Animation(7342 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation leftToRight = new Animation(7343 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation rightToLeft = new Animation(7344 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation shootMiddle = new Animation(7345 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation shootRight = new Animation(7346 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation shootLeft = new Animation(7347 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation goingDown = new Animation(7348 + GameSettings.OSRS_ANIM_OFFSET);

    /*
     * OLM ANIMS
     */
    // final public static Animation nothingEnraged = new Animation(7334+ GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation goingUpEnraged = new Animation(7383 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation faceMiddleEnraged = new Animation(7374 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation faceRightEnraged = new Animation(7376 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation faceLeftEnraged = new Animation(7375 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation middleToRightEnraged = new Animation(7379 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation rightToMiddleEnraged = new Animation(7382 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation middleToLeftEnraged = new Animation(7377 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation leftToMiddleEnraged = new Animation(7378 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation leftToRightEnraged = new Animation(7381 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation rightToLeftEnraged = new Animation(7380 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation shootMiddleEnraged = new Animation(7371 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation shootRightEnraged = new Animation(7373 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation shootLeftEnraged = new Animation(7372 + GameSettings.OSRS_ANIM_OFFSET);
    final public static Animation goingDownEnraged = new Animation(7348 + GameSettings.OSRS_ANIM_OFFSET);

    /*
     * LEFT HAND ANIMS
     */
    public static Animation deadLeftHand = new Animation(7353 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation goingUpLeftHand = new Animation(7354 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation leftHand = new Animation(7355 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation flashingCrystalLeftHand = new Animation(7356 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation flashingInfinityLeftHand = new Animation(7357 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation flashingLightningLeftHand = new Animation(7358 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation flashingCirclesLeftHand = new Animation(7359 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation clinchingLeftHand = new Animation(7360 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation clenchedLeftHand = new Animation(7361 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation backToNormalLeftHand = new Animation(7362 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation defenceLeftHand = new Animation(7363 + GameSettings.OSRS_ANIM_OFFSET);

    // UP AND DOWN
    public static Animation upAndDownLeftHand = new Animation(7365 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation UADflashingCrystalLeftHand = new Animation(7366 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation UADflashingInfinityLeftHand = new Animation(7367 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation UADflashingLightningLeftHand = new Animation(7368 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation UADflashingCirclesLeftHand = new Animation(7369 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation goingDownLeftHand = new Animation(7370 + GameSettings.OSRS_ANIM_OFFSET);

    /*
     * RIGHT HAND ANIMS
     */
    public static Animation goingUpRightHand = new Animation(7350 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation rightHand = new Animation(7351 + GameSettings.OSRS_ANIM_OFFSET);
    public static Animation goingDownRightHand = new Animation(7352 + GameSettings.OSRS_ANIM_OFFSET);

    public static void resetAnimation(RaidsParty party) {
        if (party.getCurrentPhase() == 3) {
            if (party.getGreatOlmNpc().getPosition().getX() >= 3238) {
                if (party.getGreatOlmNpc().directionFacing == Direction.NONE)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceMiddleEnraged);
                else if (party.getGreatOlmNpc().directionFacing == Direction.NORTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceRightEnraged);
                else if (party.getGreatOlmNpc().directionFacing == Direction.SOUTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceLeftEnraged);
            } else {
                if (party.getGreatOlmNpc().directionFacing == Direction.NONE)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceMiddleEnraged);
                else if (party.getGreatOlmNpc().directionFacing == Direction.NORTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceLeftEnraged);
                else if (party.getGreatOlmNpc().directionFacing == Direction.SOUTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceRightEnraged);
            }
        } else {
            if (party.getGreatOlmNpc().getPosition().getX() >= 3238) {
                if (party.getGreatOlmNpc().directionFacing == Direction.NONE)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceMiddle);
                else if (party.getGreatOlmNpc().directionFacing == Direction.NORTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceRight);
                else if (party.getGreatOlmNpc().directionFacing == Direction.SOUTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceLeft);
            } else {
                if (party.getGreatOlmNpc().directionFacing == Direction.NONE)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceMiddle);
                else if (party.getGreatOlmNpc().directionFacing == Direction.NORTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceLeft);
                else if (party.getGreatOlmNpc().directionFacing == Direction.SOUTH)
                    party.getGreatOlmObject().performAnimation(OlmAnimations.faceRight);
            }
        }
        party.setOlmAttacking(false);
    }

}
