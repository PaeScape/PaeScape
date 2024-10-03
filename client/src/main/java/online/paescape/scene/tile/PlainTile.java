package online.paescape.scene.tile;

import net.runelite.rs.api.RSSceneTilePaint;

public final class PlainTile implements RSSceneTilePaint {

    public final int northEastColor;
    public final int northColor;
    public final int centerColor;
    public final int eastColor;
    public final int texture;
    public final int colorRGB;
    public final boolean textured;
    public boolean aBoolean721;

    public PlainTile(int northEastColor, int northColor, int centerColor, int eastColor, int texture, int colorRGB, boolean flag, boolean tex) {
        aBoolean721 = true;
        this.northEastColor = northEastColor;
        this.northColor = northColor;
        this.centerColor = centerColor;
        this.eastColor = eastColor;
        this.texture = texture;
        this.colorRGB = colorRGB;
        aBoolean721 = flag;
        textured = tex;
    }

    public int bufferOffset = -1;
    public int uVBufferOffset = -1;
    public int bufferLength = -1;

    @Override
    public int getBufferOffset() {
        return bufferOffset;
    }

    @Override
    public void setBufferOffset(int bufferOffset) {
        this.bufferOffset = bufferOffset;
    }

    @Override
    public int getUvBufferOffset() {
        return uVBufferOffset;
    }

    @Override
    public void setUvBufferOffset(int bufferOffset) {
        uVBufferOffset = bufferOffset;
    }

    @Override
    public int getBufferLen() {
        return bufferLength;
    }

    @Override
    public void setBufferLen(int bufferLen) {
        bufferLength = bufferLen;
    }

    @Override
    public int getRBG() {
        return colorRGB;
    }

    @Override
    public int getSwColor() {
        return northEastColor;
    }

    @Override
    public int getSeColor() {
        return northColor;
    }

    @Override
    public int getNwColor() {
        return eastColor;
    }

    @Override
    public int getNeColor() {
        return centerColor;
    }

    @Override
    public boolean getIsFlat() {
        return false;
    }

    @Override
    public int getTexture() {
        return texture;
    }

    @Override
    public void setRBG(int val) {
    }

    @Override
    public void setSwColor(int val) {
    }

    @Override
    public void setSeColor(int val) {
    }

    @Override
    public void setNwColor(int val) {
    }

    @Override
    public void setNeColor(int val) {
    }

    @Override
    public void setIsFlat(boolean val) {
    }

    @Override
    public void setTexture(int val) {
    }
}
