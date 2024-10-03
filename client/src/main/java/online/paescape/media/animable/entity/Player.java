package online.paescape.media.animable.entity;


import net.runelite.api.*;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.kit.KitType;
import net.runelite.rs.api.*;
import online.paescape.Client;
import online.paescape.cache.def.ItemDef;
import online.paescape.cache.def.NPCDef;
import online.paescape.cache.media.Animation;
import online.paescape.cache.media.IDK;
import online.paescape.cache.media.SpotAnim;
import online.paescape.collection.MemCache;
import online.paescape.media.FrameReader;
import online.paescape.media.animable.Model;
import online.paescape.net.Stream;
import online.paescape.util.DataType;
import online.paescape.util.TextClass;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public final class Player extends Entity implements RSPlayer {

    public static boolean isBot = false;
    public static String displayName = " ";
    public static MemCache modelCache = new MemCache(260);
    public final int[] equipment;
    public final int[] anIntArray1700;
    private final int anInt1715;
    public int frontLight = 68;
    public int backLight = 820;
    public int rightLight = 0;
    public int middleLight = -1; // Cannot be 0
    public int leftLight = 0;
    public int[][] modifiedColors = new int[12][];
    public int constitution, maxConstitution;
    public int playerRights;
    public String playerTitle;
    public NPCDef desc;
    public int team;
    public int myGender;
    public String name;
    public int combatLevel;
    public int headIcon;
    public int bountyHunterIcon;
    public int hintIcon;
    public boolean skulled;
    public int summonLevel;
    public int SummonLevel;
    public int startTimeTransform;
    public boolean aBoolean1699;
    public int transformedTimer;
    public int z;
    public boolean visible;
    public int resizeX;
    public int resizeZ;
    public int resizeY;
    public Model transformIntoModel;
    public int extendedXMin;
    public int extendedYMin;
    public int extendedXMax;
    public int extendedYMax;
    private long aLong1697;
    private long aLong1718;

    public Player() {
        aLong1697 = -1L;
        aBoolean1699 = false;
        anIntArray1700 = new int[5];
        visible = false;
        anInt1715 = 9;
        equipment = new int[12];
    }

    public Model getRotatedModel() {
        if (!visible)
            return null;
        Model model = method452();
        if (model == null)
            return null;
        super.height = model.modelBaseY;
        model.rendersWithinOneTile = true;
        if (aBoolean1699)
            return model;
        if (super.anInt1520 != -1 && super.currentAnim != -1) {
            SpotAnim spotAnim = SpotAnim.cache[super.anInt1520];
            Model model_2 = spotAnim.getModel();
            if (spotAnim.animation != null) {
                if (FrameReader.animationlist[Integer.parseInt(Integer.toHexString(spotAnim.animation.frameIDs[0]).substring(0, Integer.toHexString(spotAnim.animation.frameIDs[0])
                        .length() - 4), 16)].length == 0) {
                    model_2 = null;

                }
            }

            if (model_2 != null) {
                Model model_3 = new Model(true, FrameReader.isNullFrame(super.currentAnim), false, model_2);
                model_3.translate(0, -super.graphicHeight, 0);
                model_3.createBones();
                model_3.scaleT(132, 132, 132);
                model_3.applyTransform(spotAnim.animation.frameIDs[super.currentAnim], spotAnim.animation.dataType);
                model_3.triangleSkin = null;
                model_3.vertexSkin = null;
                if (spotAnim.sizeXY != 128 || spotAnim.sizeZ != 128)
                    model_3.scaleT(spotAnim.sizeXY, spotAnim.sizeXY, spotAnim.sizeZ);
                model_3.light(64 + spotAnim.shadow, 850 + spotAnim.lightness, -30, -50, -30, true);
                Model[] aclass30_sub2_sub4_sub6_1s = {model, model_3};
                model = new Model(aclass30_sub2_sub4_sub6_1s);
            }
        }
        if (transformIntoModel != null) {
            if (Client.loopCycle >= transformedTimer)
                transformIntoModel = null;
            if (Client.loopCycle >= startTimeTransform && Client.loopCycle < transformedTimer) {
                Model model_1 = transformIntoModel;
                model_1.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
                if (super.turnDirection == 512) {
                    model_1.rotateBy90();
                    model_1.rotateBy90();
                    model_1.rotateBy90();
                } else if (super.turnDirection == 1024) {
                    model_1.rotateBy90();
                    model_1.rotateBy90();
                } else if (super.turnDirection == 1536)
                    model_1.rotateBy90();
                Model[] models = {model, model_1};
                model = new Model(models);
                if (super.turnDirection == 512)
                    model_1.rotateBy90();
                else if (super.turnDirection == 1024) {
                    model_1.rotateBy90();
                    model_1.rotateBy90();
                } else if (super.turnDirection == 1536) {
                    model_1.rotateBy90();
                    model_1.rotateBy90();
                    model_1.rotateBy90();
                }
                model_1.translate(super.x - resizeX, z - resizeZ, super.y - resizeY);
            }
        }
        model.rendersWithinOneTile = true;
        return model;
    }

    public void updatePlayerAppearance(Stream stream) {
        stream.currentOffset = 0;
        myGender = stream.readUnsignedByte();
        headIcon = stream.readUnsignedByte();
        bountyHunterIcon = stream.readUnsignedByte();
        skulled = stream.readUnsignedWord() == 1;
        if (bountyHunterIcon > 4 && bountyHunterIcon != 255)
            bountyHunterIcon = 4;
        desc = null;
        team = 0;
        for (int partId = 0; partId < 12; partId++) {
            int firstByte = stream.readUnsignedByte();
            if (firstByte == 0) {
                equipment[partId] = 0;
                continue;
            }
            int secondByte = stream.readUnsignedByte();
            equipment[partId] = (firstByte << 8) + secondByte;
            if (partId == 0 && equipment[0] == 65535) {
                desc = NPCDef.forID(stream.readUnsignedWord());
                break;
            }
            if (partId == 1) {
                int length = stream.readUnsignedByte();
                if (length > 0) {
                    int[] colors = new int[length];
                    for (int i = 0; i < length; i++) {
                        colors[i] = stream.readInt();
                    }
                    if (!Arrays.equals(modifiedColors[partId], colors)) {
                        modelCache.clear();
                    }
                    modifiedColors[partId] = colors;
                } else {
                    if (modifiedColors[partId] != null) {
                        modelCache.clear();
                    }
                    modifiedColors[partId] = null;
                }
            }
            if (partId == 8)
                Client.myHeadAndJaw[0] = equipment[partId] - 256;
            if (partId == 11)
                Client.myHeadAndJaw[1] = equipment[partId] - 256;
            if (equipment[partId] >= 512 && equipment[partId] - 512 < ItemDef.totalItems) {
                int l1 = ItemDef.forID(equipment[partId] - 512).team;
                if (l1 != 0)
                    team = l1;
            }
        }

        for (int l = 0; l < 5; l++) {
            int j1 = stream.readUnsignedByte();
            if (j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length)
                j1 = 0;
            anIntArray1700[l] = j1;
        }

        super.anInt1511 = stream.readUnsignedWord();
        if (super.anInt1511 == 65535)
            super.anInt1511 = -1;
        super.anInt1512 = stream.readUnsignedWord();
        if (super.anInt1512 == 65535)
            super.anInt1512 = -1;
        super.anInt1554 = stream.readUnsignedWord();
        if (super.anInt1554 == 65535)
            super.anInt1554 = -1;
        super.anInt1555 = stream.readUnsignedWord();
        if (super.anInt1555 == 65535)
            super.anInt1555 = -1;
        super.anInt1556 = stream.readUnsignedWord();
        if (super.anInt1556 == 65535)
            super.anInt1556 = -1;
        super.anInt1557 = stream.readUnsignedWord();
        if (super.anInt1557 == 65535)
            super.anInt1557 = -1;
        super.runAnimation = stream.readUnsignedWord();
        if (super.runAnimation == 65535)
            super.runAnimation = -1;

        name = TextClass.fixName(TextClass.nameForLong(stream.readQWord()));
        combatLevel = stream.readUnsignedByte();
        playerRights = stream.readUnsignedWord();
        playerTitle = stream.readString();

        for (int partId = 0; partId < 12; partId++) {
            int id = stream.readDWord();
            if (id > 0) {
                equipment[partId] = id;
            }
        }

        displayName = stream.readString();

        //if (!displayName.contains("null")) {
        //name=displayName;

        //name = name.substring(name.indexOf("M"));

        //}

        int isBot = stream.readUnsignedByte();

        Player.isBot = isBot != 0;


        visible = true;
        aLong1718 = 0L;

        if (desc != null) {
            combatLevel = desc.combatLevel;
            super.anInt1511 = desc.standAnim;
            super.anInt1512 = desc.standAnim;
            super.anInt1554 = desc.walkAnim;
            super.anInt1555 = desc.standAnim;
            super.anInt1556 = desc.walkAnim;
            super.anInt1557 = desc.walkAnim;
            super.runAnimation = desc.walkAnim;
        }


        for (int k1 = 0; k1 < 12; k1++) {
            aLong1718 <<= 4;
            if (equipment[k1] >= 256)
                aLong1718 += equipment[k1] - 256;
        }

        if (equipment[0] >= 256)
            aLong1718 += equipment[0] - 256 >> 4;
        if (equipment[1] >= 256)
            aLong1718 += equipment[1] - 256 >> 8;
        for (int i2 = 0; i2 < 5; i2++) {
            aLong1718 <<= 3;
            aLong1718 += anIntArray1700[i2];
        }

        aLong1718 <<= 1;
        aLong1718 += myGender;
    }

    public Model method452() {
        if (desc != null) {
            int j = -1;
            if (super.anim >= 0 && super.animationDelay == 0) {
                j = Animation.anims[super.anim].frameIDs[super.currentAnimFrame];
            } else if (super.anInt1517 >= 0) {
                j = Animation.anims[super.anInt1517].frameIDs[super.currentForcedAnimFrame];
            }
            Model model = desc.getAnimatedModel(-1, j, null);
            return model;
        }

		/*if(desc != null)
		{
			int currentFrame = -1;
			int nextFrame = -1;
			int cycle1 = 0;
			int cycle2 = 0;
			if(super.anim >= 0 && super.animationDelay == 0) {
				Animation animation = Animation.anims[super.anim];
				currentFrame = animation.frameIDs[super.currentAnimFrame];
				nextFrame = animation.frameIDs[super.nextAnimationFrame];
				cycle1 = animation.delays[super.currentAnimFrame];
				cycle2 = super.anInt1528;
			} else if(super.anInt1517 >= 0) {
				Animation animation = Animation.anims[super.anInt1517];
				currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
				nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
				cycle1 = animation.delays[super.currentForcedAnimFrame];
				cycle2 = super.anInt1519;
			}
			Model model = desc.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
			return model;
		}*/


        long l = aLong1718;
        int currentFrame = -1;
        int nextFrame = -1;
        int cycle1 = 0;
        int cycle2 = 0;
        int i1 = -1;
        int j1 = -1;
        int k1 = -1;
        Animation animation1 = null;
        if (super.anim >= 0 && super.animationDelay == 0) {
            Animation animation = Animation.anims[super.anim];
            currentFrame = animation.frameIDs[super.currentAnimFrame];
            if (super.nextAnimationFrame < animation.frameIDs.length)
                nextFrame = animation.frameIDs[super.nextAnimationFrame];
            cycle1 = animation.delays[super.currentAnimFrame];
            cycle2 = super.anInt1528;
            if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511)
                i1 = Animation.anims[super.anInt1517].frameIDs[super.currentForcedAnimFrame];
            if (animation.leftHandItem >= 0) {
                j1 = animation.leftHandItem;
                l += j1 - equipment[5] << 40;
            }
            if (animation.rightHandItem >= 0) {
                k1 = animation.rightHandItem;
                l += k1 - equipment[3] << 48;
            }
            animation1 = animation;
        } else if (super.anInt1517 >= 0) {
            Animation animation = Animation.anims[super.anInt1517];
            currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
            nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
            cycle1 = animation.delays[super.currentForcedAnimFrame];
            cycle2 = super.anInt1519;
            animation1 = animation;

        }


        Model model_1 = (Model) modelCache.get(l);
        if (model_1 == null) {
            boolean fetchModels = false;
            for (int bodyPartId = 0; bodyPartId < 12; bodyPartId++) {
                int partId = equipment[bodyPartId];
                if (k1 >= 0 && bodyPartId == 3)
                    partId = k1;
                if (j1 >= 0 && bodyPartId == 5)
                    partId = j1;
                if ((partId - 256) != 846) {

                    if (partId >= 256 && partId < 512 && !IDK.cache[partId - 256].bodyModelIsFetched())
                        fetchModels = true;
                    if (partId >= 512 && !ItemDef.forID(partId - 512).equipModelFetched(myGender, partId - 512 > ItemDef.OSRS_ITEMS_OFFSET ? DataType.OLDSCHOOL : DataType.REGULAR))
                        fetchModels = true;

                }
            }

            if (fetchModels) {
                if (aLong1697 != -1L)
                    model_1 = (Model) modelCache.get(aLong1697);
                if (model_1 == null)
                    return null;
            }
        }
        if (model_1 == null) {
            Model[] bodyPartModels = new Model[13];
            int j2 = 0;
            for (int currentPart = 0; currentPart < 12; currentPart++) {
                int i3 = equipment[currentPart];
                if (k1 >= 0 && currentPart == 3)
                    i3 = k1;
                if (j1 >= 0 && currentPart == 5)
                    i3 = j1;
                if (i3 >= 256 && i3 < 512) {
                    Model model_3 = null;
                    if ((i3 - 256) < IDK.cache.length)
                        model_3 = IDK.cache[i3 - 256].fetchBodyModel();
                    if (model_3 != null)
                        bodyPartModels[j2++] = model_3;
                }
                if (i3 >= 512) {
                    ItemDef def = ItemDef.forID(i3 - 512);
                    Model model_4 = def.getEquipModel(myGender);
					/*if(def.id == 14019) {
						if(name.equalsIgnoreCase("Apache Ah64")) {
							System.out.println("changing color");
							int[] originalModelColor = new int[4];
							originalModelColor[0] = 926;//cape
							originalModelColor[1] = 350770;//cape
							originalModelColor[2] = 926;//outline
							originalModelColor[3] = 130770;//cape
							for (int i11 = 0; i11 < def.editedModelColor.length; i11++)
								model_4.recolour(def.editedModelColor[i11], originalModelColor[i11]);
						} else if(name.equalsIgnoreCase("Apache Ah66")) {
							System.out.println("changing color2");
							int[] originalModelColor = new int[4];
							originalModelColor[0] = 926;//cape
							originalModelColor[1] = 302770;//cape
							originalModelColor[2] = 926;//outline
							originalModelColor[3] = 302770;//cape
							for (int i11 = 0; i11 < def.editedModelColor.length; i11++)
								model_4.recolour(def.editedModelColor[i11], originalModelColor[i11]);
						}
					}*/
                    //System.out.println(name+" - "+currentPart+": "+Arrays.toString(modifiedColors[currentPart]));
                    if (modifiedColors[currentPart] != null) {
                        for (int i11 = 0; i11 < def.editedModelColor.length; i11++)
                            model_4.recolour(def.editedModelColor[i11], modifiedColors[currentPart][i11]);
                    }
                    if (model_4 != null)
                        bodyPartModels[j2++] = model_4;
                }
            }
            model_1 = new Model(j2, bodyPartModels);
            for (int j3 = 0; j3 < 5; j3++)
                if (anIntArray1700[j3] != 0) {
                    model_1.recolour(Client.anIntArrayArray1003[j3][0], Client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
                    if (j3 == 1)
                        model_1.recolour(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j3]]);
                }

            model_1.createBones();
            model_1.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
            modelCache.put(model_1, l);
            aLong1697 = l;
        }
        if (aBoolean1699)
            return model_1;
        Model model_2 = Model.entityModelDesc;
        model_2.method464(model_1, FrameReader.isNullFrame(currentFrame) & FrameReader.isNullFrame(i1));
        if (currentFrame != -1 && i1 != -1) {
            model_2.method471(Animation.anims[super.anim].animationFlowControl, i1, currentFrame, animation1.dataType);
        } else if (currentFrame != -1 && nextFrame != -1) {
            model_2.applyTransform(currentFrame, nextFrame, cycle1, cycle2, animation1.dataType);
        } else {
            if (animation1 != null) {
                model_2.applyTransform(currentFrame, animation1.dataType);
            }
        }
        model_2.calculateDiagonals();
        model_2.triangleSkin = null;
        model_2.vertexSkin = null;
        return model_2;
    }

    public boolean isVisible() {
        return visible;
    }

    public Model getPlayerModel() {
        if (!visible)
            return null;
        if (desc != null)
            return desc.getHeadModel();
        boolean flag = false;
        for (int i = 0; i < 12; i++) {
            int j = equipment[i];
            try {
                if (j >= 256 && j < 512 && !IDK.cache[j - 256].headModelFetched())
                    flag = true;
                if (j >= 512 && !ItemDef.forID(j - 512).dialogueModelFetched(myGender)) {
                    flag = true;
                }
            } catch (Exception e) {
                flag = true;
            }
        }

        if (flag)
            return null;
        Model[] models = new Model[12];
        int k = 0;
        for (int l = 0; l < 12; l++) {
            int i1 = equipment[l];
            if (i1 >= 256 && i1 < 512) {
                Model model_1 = IDK.cache[i1 - 256].fetchHeadModel();
                if (model_1 != null)
                    models[k++] = model_1;
            }
            if (i1 >= 512) {
                Model model_2 = ItemDef.forID(i1 - 512).getDialogueModel(myGender);
                if (model_2 != null)
                    models[k++] = model_2;
            }
        }

        Model model = new Model(k, models);
        for (int j1 = 0; j1 < 5; j1++)
            if (anIntArray1700[j1] != 0) {
                model.recolour(Client.anIntArrayArray1003[j1][0], Client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
                if (j1 == 1)
                    model.recolour(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j1]]);
            }

        return model;
    }

    @Override
    public int getRSInteracting() {
        return 0;
    }

    @Override
    public String getOverheadText() {
        return "";
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
        return anim;
    }

    @Override
    public void setAnimation(int animation) {
        anim = animation;
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
        return height;
    }

    @Override
    public int getOrientation() {
        return anInt1552;
    }

    @Override
    public int getCurrentOrientation() {
        return anInt1552;
    }

    @Override
    public RSIterableNodeDeque getHealthBars() {
        return null;
    }

    @Override
    public int[] getHitsplatValues() {
        return null;
    }

    @Override
    public int[] getHitsplatTypes() {
        return hitMarkTypes;
    }

    @Override
    public int[] getHitsplatCycles() {
        return hitsLoopCycle;
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
        return 0;
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
        return 0;
    }

    @Override
    public void setDead(boolean dead) {
    }

    @Override
    public int getPathLength() {
        return 0;
    }

    @Override
    public int getOverheadCycle() {
        return 0;
    }

    @Override
    public void setOverheadCycle(int cycle) {
    }

    @Override
    public RSModel getModel() {
        return null;
//TODO        return getRotatedModel();
    }

    @Override
    public RSNode getNext() {
        return null;
    }

    @Override
    public RSNode getPrevious() {
        return null;
    }

    @Override
    public void onUnlink() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Actor getInteracting() {
        int index = interactingEntity;
        if (index == -1 || index == 65535)
        {
            return null;
        }
        Client client = Client.instance;

        if (index < 32768)
        {
            NPC[] npcs = client.getCachedNPCs();
            return npcs[index];
        }

        index -= 32768;
        Player[] players = client.playerArray;
        return players[index];
    }

    @Override
    public int getHealthRatio() {
        return Math.round(this.currentHealth / this.maxHealth);
    }

    @Override
    public int getHealthScale() {
        return currentHealth;
    }

    @Override
    public WorldPoint getWorldLocation() {
        return WorldPoint.fromLocal(Client.instance,
                this.getPathX()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
                this.getPathY()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
                Client.instance.getPlane());
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
        return Perspective.getCanvasTilePoly(Client.instance, this.getLocalLocation());
    }

    @Nullable
    @Override
    public net.runelite.api.Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset) {
        return Perspective.getCanvasTextLocation(Client.instance, (Graphics2D) Client.instance.getGameComponent().getGraphics(), getLocalLocation(), text, zOffset);
    }


    @Override
    public net.runelite.api.Point getCanvasImageLocation(BufferedImage image, int zOffset) {
        return Perspective.getCanvasImageLocation(Client.instance, getLocalLocation(), image, zOffset);
    }

    @Override
    public net.runelite.api.Point getCanvasSpriteLocation(SpritePixels sprite, int zOffset) {
        return null;
    }

    @Override
    public Point getMinimapLocation() {
        return Perspective.localToMinimap(Client.instance, getLocalLocation());
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
        return currentHealth <= 0;
    }

    @Override
    public boolean isMoving() {
        return false;
    }

    @Override
    public Polygon getConvexHullPoly() {
        return null;
    }


    @Override
    public Polygon[] getPolygons()
    {
        RSModel model = getModel();

        if (model == null)
        {
            return null;
        }

        int[] x2d = new int[model.getVerticesCount()];
        int[] y2d = new int[model.getVerticesCount()];

        int localX = getX();
        int localY = getY();

        final int tileHeight = Perspective.getTileHeight(Client.instance, new LocalPoint(localX, localY), Client.instance.getPlane());

        Perspective.modelToCanvas(Client.instance, model.getVerticesCount(), localX, localY, tileHeight, getOrientation(), model.getVerticesX(), model.getVerticesZ(), model.getVerticesY(), x2d, y2d);
        ArrayList polys = new ArrayList(model.getFaceCount());

        int[] trianglesX = model.getFaceIndices1();
        int[] trianglesY = model.getFaceIndices2();
        int[] trianglesZ = model.getFaceIndices3();

        for (int triangle = 0; triangle < model.getFaceCount(); ++triangle)
        {
            int[] xx =
                    {
                            x2d[trianglesX[triangle]], x2d[trianglesY[triangle]], x2d[trianglesZ[triangle]]
                    };

            int[] yy =
                    {
                            y2d[trianglesX[triangle]], y2d[trianglesY[triangle]], y2d[trianglesZ[triangle]]
                    };

            polys.add(new Polygon(xx, yy, 3));
        }

        return (Polygon[]) polys.toArray(new Polygon[0]);
    }


    @Override
    public HeadIcon getOverheadIcon() {
        return null;
    }

    @Override
    public SkullIcon getSkullIcon() {
        return null;
    }

    @Override
    public RSUsername getRsName() {
        return null;
    }

    @Override
    public int getPlayerId() {
        return 0;
    }

    @Override
    public RSPlayerComposition getPlayerComposition() {
        return new RSPlayerComposition() {
            @Override
            public boolean isFemale() {
                return myGender == 1;
            }

            @Override
            public int[] getColors() {
                return anIntArray1700;
            }

            @Override
            public int[] getEquipmentIds() {
                return equipment;
            }

            @Override
            public int getEquipmentId(KitType type) {
                return 0;
            }

            @Override
            public void setTransformedNpcId(int id) {

            }

            @Override
            public int getKitId(KitType type) {
                return equipment[type.getIndex()];
            }

            @Override
            public long getHash() {
                return 0;
            }

            @Override
            public void setHash() {
            }
        };
    }

    @Override
    public int getCombatLevel() {
        return combatLevel;
    }

    @Override
    public int getTotalLevel() {
        return 0;
    }

    @Override
    public int getTeam() {
        return team;
    }

    @Override
    public boolean isFriendsChatMember() {
        return false;
    }

    @Override
    public boolean isClanMember() {
        return false;
    }

    @Override
    public boolean isFriend() {
        return Client.instance.isFriended(displayName, true);
    }

    @Override
    public boolean isFriended() {
        return false;
    }

    @Override
    public int getRsOverheadIcon() {
        return headIcon;
    }

    @Override
    public int getRsSkullIcon() {
        return headIcon;
    }

    @Override
    public int getRSSkillLevel() {
        return 0;
    }

    @Override
    public String[] getActions() {
        return null;
    }

    @Override
    public int getPoseAnimationFrame() {
        return 0;
    }

    @Override
    public void setPoseAnimationFrame(int frame) {

    }

}

