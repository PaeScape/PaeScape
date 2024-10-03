package online.paescape.scene.tile;


import net.runelite.api.Model;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.geometry.Shapes;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSRenderable;
import net.runelite.rs.api.RSWallDecoration;
import online.paescape.Client;
import online.paescape.cache.def.ObjectDef;
import online.paescape.media.animable.Animable;
import online.paescape.util.ObjectKeyUtil;

import java.awt.*;

public final class WallDecoration implements RSWallDecoration {

    public Animable node;
    public int uid;
    public int zPos;
    public int xPos;
    public int yPos;
    public int configurationBits;
    public int rotation;
    public int wallDecorUID;
    public byte objConfig;

    public WallDecoration() {
    }

    @Override
    public Shape getConvexHull() {
        RSModel model = node.getModel();

        if (model == null)
        {
            return null;
        }

        int tileHeight = Perspective.getTileHeight(Client.instance, new LocalPoint(getX(), getY()), Client.instance.getPlane());
        return model.getConvexHull(getX(), getY(), configurationBits, tileHeight);
    }

    @Override
    public Shape getConvexHull2() {
        RSModel model = node.getModel();

        if (model == null)
        {
            return null;
        }

        int tileHeight = Perspective.getTileHeight(Client.instance, new LocalPoint(getX(), getY()), Client.instance.getPlane());

        return model.getConvexHull(getX(), getY(), rotation, tileHeight);
    }
    @Override
    public int getPlane() {
        return zPos;
    }
    @Override
    public int getId() {
        return ObjectKeyUtil.getObjectId(uid);
    }
    @Override
    public net.runelite.api.Point getCanvasLocation() {
        return Perspective.localToCanvas(Client.instance, getLocalLocation(), getPlane(), 0);
    }

    @Override
    public net.runelite.api.Point getCanvasLocation(int zOffset) {
        return Perspective.localToCanvas(Client.instance, this.getLocalLocation(), this.getPlane(), zOffset);
    }

    @Override
    public Polygon getCanvasTilePoly() {
        return Perspective.getCanvasTilePoly(Client.instance, this.getLocalLocation());
    }

    @Override
    public net.runelite.api.Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset) {
        return Perspective.getCanvasTextLocation(Client.instance, graphics, getLocalLocation(), text, zOffset);
    }

    @Override
    public Point getMinimapLocation() {
        return Perspective.localToMinimap(Client.instance, getLocalLocation());
    }

    public RSModel getModel1()
    {
        RSRenderable renderable = getRenderable();
        if (renderable == null)
        {
            return null;
        }

        RSModel model;

        if (renderable instanceof Model)
        {
            model = (RSModel) renderable;
        }
        else
        {
            model = renderable.getModel();
        }

        return model;
    }

    public RSModel getModel2()
    {
        RSRenderable renderable = getRenderable2();
        if (renderable == null)
        {
            return null;
        }

        RSModel model;

        if (renderable instanceof Model)
        {
            model = (RSModel) renderable;
        }
        else
        {
            model = renderable.getModel();
        }

        return model;
    }

    @Override
    public Shape getClickbox() {
        LocalPoint lp = getLocalLocation();
        Shape clickboxA = Perspective.getClickbox(Client.instance, getModel1(), 0, new LocalPoint(lp.getX() + getXOffset(), lp.getY() + getYOffset()));
        Shape clickboxB = Perspective.getClickbox(Client.instance, getModel2(), 0, lp);

        if (clickboxA == null && clickboxB == null)
        {
            return null;
        }

        if (clickboxA != null && clickboxB != null)
        {
            return new Shapes(new Shape[]{clickboxA, clickboxB});
        }

        if (clickboxA != null)
        {
            return clickboxA;
        }

        return clickboxB;
    }
    @Override
    public String getName() {
        return ObjectDef.forID(getId()).name;
    }

    @Override
    public String[] getActions() {
        return ObjectDef.forID(getId()).actions;
    }
    @Override
    public WorldPoint getWorldLocation() {
        return WorldPoint.fromLocal(Client.instance, this.getX(), this.getY(), this.getPlane());
    }

    @Override
    public LocalPoint getLocalLocation() {
        return new LocalPoint(this.getX(), this.getY());
    }

    @Override
    public long getHash() {
        return uid;
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }
    @Override
    public int getXOffset() {
        return 0;
    }
    @Override
    public int getYOffset() {
        return 0;
    }
    @Override
    public int getOrientation() {
        return configurationBits;
    }
    @Override
    public RSRenderable getRenderable() {
        return node;
    }
    @Override
    public RSRenderable getRenderable2() {
        return node;
    }
    @Override
    public void setPlane(int plane) {
        zPos = plane;
    }

    @Override
    public int getConfig()
    {
        return objConfig;
    }

}
