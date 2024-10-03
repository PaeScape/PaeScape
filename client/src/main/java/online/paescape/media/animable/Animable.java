package online.paescape.media.animable;

import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSNode;
import net.runelite.rs.api.RSRenderable;
import online.paescape.collection.QueueNode;
import online.paescape.media.VertexNormal;

public class Animable extends QueueNode implements RSRenderable {

    public int modelBaseY;
    public VertexNormal[] vertexNormals;

    public Animable() {
        modelBaseY = 1000;
    }

    public void renderAtPoint(int i, int j, int k, int l, int i1, int j1,
                              int k1, int l1, int i2, int newuid, int bufferOffset) {
        Model model = getRotatedModel();
        if (model != null) {
            modelBaseY = model.modelBaseY;
            model.renderAtPoint(i, j, k, l, i1, j1, k1, l1, i2, newuid, bufferOffset);
        }
        // System.gc();
    }
    public void renderAtPoint(int i, int j, int k, int l, int i1, int j1, int k1,
                              int l1, long uid) {
        Model model = getRotatedModel();
        if (model != null) {
            modelBaseY = model.modelBaseY;
            model.renderAtPoint(i, j, k, l, i1, j1, k1, l1, uid);
        }
    }

    public Model getRotatedModel() {
        return null;
    }

    public Model getRotatedModelHD() {
        return null;
    }

    @Override
    public RSNode getNext() {
        return null;
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
    public int getModelBaseY() {
        return modelBaseY;
    }

    @Override
    public void setModelBaseY(int modelBaseY) {
        modelBaseY = modelBaseY;
    }

    @Override
    public void draw(int orientation, int pitchSin, int pitchCos, int yawSin, int yawCos, int x, int y, int z, long hash) {
        renderAtPoint(orientation,pitchSin,pitchCos,yawSin,yawCos,x,y,z,hash);
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public RSModel getModel() {
//        return getRotatedModel();
        return null;
    }

}