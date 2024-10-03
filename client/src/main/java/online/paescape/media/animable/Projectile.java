package online.paescape.media.animable;


import online.paescape.cache.media.SpotAnim;
import online.paescape.media.FrameReader;

public final class Projectile extends Animable {

    public final int startTime;
    public final int speed;
    public final int endHeight;
    public final int lockOn;
    public final int plane;
    private final int startX;
    private final int startY;
    private final int drawHeight;
    private final int slopeHeight;
    private final int radius;
    private final SpotAnim gfx;
    public double currentPositionX;
    public double currentPositionY;
    public double currentPositionZ;
    public int rotationY;
    private double speedX;
    private double speedY;
    private double speedOverall;
    private double speedZ;
    private double timeLeftTillFinishZ;
    private boolean isMoving;
    private int currentFrame;
    private int tick;
    private int rotationX;

    public Projectile(int i, int j, int l, int i1, int j1, int k1,
                      int l1, int i2, int j2, int k2, int gfxId) {
        isMoving = false;
        gfx = SpotAnim.cache[gfxId];
        plane = k1;
        startX = j2;
        startY = i2;
        drawHeight = l1;
        startTime = l;
        speed = i1;
        slopeHeight = i;
        radius = j1;
        lockOn = k2;
        endHeight = j;
        isMoving = false;
    }

    public void calculateTracking(int currentTime, int targetY, int targetZ, int targetX) {
        if (!isMoving) {
            double distanceX = targetX - startX;
            double distanceY = targetY - startY;
            double distanceOverall = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            currentPositionX = (double) startX + (distanceX * (double) radius) / distanceOverall;
            currentPositionY = (double) startY + (distanceY * (double) radius) / distanceOverall;
            currentPositionZ = drawHeight;
        }
        double d1 = (speed + 1) - currentTime;
        speedX = ((double) targetX - currentPositionX) / d1;
        speedY = ((double) targetY - currentPositionY) / d1;
        speedOverall = Math.sqrt(speedX * speedX + speedY * speedY);
        if (!isMoving)
            speedZ = -speedOverall * Math.tan((double) slopeHeight * 0.02454369D);
        timeLeftTillFinishZ = (2D * ((double) targetZ - currentPositionZ - speedZ * d1)) / (d1 * d1);
    }

    public Model getRotatedModel() {
        Model model = gfx.getModel();
        if (model == null)
            return null;
        int j = -1;
        if (gfx.animation != null)
            j = gfx.animation.frameIDs[currentFrame];
        Model model_1 = new Model(true, FrameReader.isNullFrame(j), false, model);
        if (j != -1) {
            model_1.createBones();
            model_1.applyTransform(j, gfx.animation.dataType);
            model_1.triangleSkin = null;
            model_1.vertexSkin = null;
        }
        if (gfx.sizeXY != 128 || gfx.sizeZ != 128)
            model_1.scaleT(gfx.sizeXY, gfx.sizeXY, gfx.sizeZ);
        model_1.rotateX(rotationX);
        model_1.light(64 + gfx.shadow, 5050 + gfx.lightness, -90, -580, -90, true);
        return model_1;
    }

    public void processMovement(int nextFrame) {
        isMoving = true;
        currentPositionX += speedX * (double) nextFrame;
        currentPositionY += speedY * (double) nextFrame;
        currentPositionZ += speedZ * (double) nextFrame + 0.5D * timeLeftTillFinishZ * (double) nextFrame * (double) nextFrame;
        speedZ += timeLeftTillFinishZ * (double) nextFrame;
        rotationY = (int) (Math.atan2(speedX, speedY) * 325.94900000000001D) + 1024 & 0x7ff;
        rotationX = (int) (Math.atan2(speedZ, speedOverall) * 325.94900000000001D) & 0x7ff;
        if (gfx.animation != null)
            for (tick += nextFrame; tick > gfx.animation.getFrameLength(currentFrame); ) {
                tick -= gfx.animation.getFrameLength(currentFrame) + 1;
                currentFrame++;
                if (currentFrame >= gfx.animation.frameCount)
                    currentFrame = 0;
            }

    }
}
