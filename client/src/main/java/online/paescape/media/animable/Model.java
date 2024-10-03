package online.paescape.media.animable;

//import com.sun.deploy.util.ArrayUtil;

import net.runelite.api.AABB;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.model.Jarvis;
import net.runelite.api.model.Triangle;
import net.runelite.api.model.Vertex;
import net.runelite.rs.api.RSFrames;
import net.runelite.rs.api.RSModel;
import online.paescape.Client;
import online.paescape.media.*;
import online.paescape.media.particle.Particle;
import online.paescape.media.particle.ParticleAttachment;
import online.paescape.media.particle.ParticleDefinition;
import online.paescape.media.particle.ParticleVector;
import online.paescape.net.Stream;
import online.paescape.net.requester.OnDemandFetcherParent;
import online.paescape.util.Configuration;
import online.paescape.util.DataType;

import java.awt.*;
import java.util.*;
import java.util.List;

@SuppressWarnings("all")
public class Model extends Animable implements RSModel {

    final static int[] PLAYER_MODELS = {208, 251, 292, 170, 176, 534, 265, 181, 48, 83, 534, 48, 83, 534, 230, 210, 214, 217, 223, 215, 235, 206, 203, 203, 249, 250, 251, 247, 246, 248, 253, 252, 292, 292, 322, 292, 326, 292, 320, 292, 327, 292, 324, 310, 297, 151, 167, 170, 162, 163, 158, 158, 176, 176, 176, 254, 271, 267, 274, 275, 275, 181, 183, 183, 403, 378, 387, 390, 393, 399, 391, 408, 382, 379, 379, 456, 469, 470, 471, 474, 332, 351, 346, 348, 343, 343, 353, 353, 353, 419, 433, 431, 428, 421, 438, 437, 429, 429, 358, 360, 360, 7117, 7118};
    private static final Set<Integer> osrsCapeModels = new HashSet<>(Arrays.asList(29630, 34149, 34146, 34148, 33103, 33102, 34148));
    public static int MAX_POLYGON = 10000;
    public static boolean newmodel[];
    public static String ccString = "Cla";
    public static String xxString = "at Cl";
    public static String vvString = "nt";
    public static String aString9_9 = "" + ccString + "n Ch" + xxString + "ie" + vvString + " ";
    public static int anInt1620;
    public static Model entityModelDesc = new Model(true);
    public static boolean objectExists;
    public static int lineOffsets[];
    public static int currentCursorX;
    public static int currentCursorY;
    public static int objectsRendered;
    public static int objectsInCurrentRegion[] = new int[1000];
    public static int mapObjectIds[] = new int[1000];
    public static int SINE[];
    public static int COSINE[];
    protected static int anIntArray1622[] = new int[3000];
    protected static int anIntArray1623[] = new int[3000];
    protected static int anIntArray1624[] = new int[3000];
    protected static int anIntArray1625[] = new int[3000];
    static ModelHeader modelHeader[];
    static ModelHeader modelHeaderOldschool[];
    static OnDemandFetcherParent resourceManager;
    static boolean hasAnEdgeToRestrict[] = new boolean[MAX_POLYGON];
    static boolean outOfReach[] = new boolean[MAX_POLYGON];
    static int projected_vertex_x[] = new int[MAX_POLYGON];
    static int projected_vertex_y[] = new int[MAX_POLYGON];
    static int projected_vertex_z[] = new int[MAX_POLYGON];
    static int anIntArray1667[] = new int[MAX_POLYGON];
    static int camera_vertex_y[] = new int[MAX_POLYGON];
    static int camera_vertex_x[] = new int[MAX_POLYGON];
    static int camera_vertex_z[] = new int[MAX_POLYGON];//jesus fucking christ why are all these 65535? 65k?> l0l no wonder mem usage went up through the roof jesus
    static int depthListIndices[] = new int[10050];
    static int faceLists[][] = new int[10050][512];
    static int anIntArray1673[] = new int[12];
    static int anIntArrayArray1674[][] = new int[12][6000];
    static int anIntArray1675[] = new int[6000];
    static int anIntArray1676[] = new int[6000];
    static int anIntArray1677[] = new int[12];
    static int anIntArray1678[] = new int[10];
    static int anIntArray1679[] = new int[10];
    static int anIntArray1680[] = new int[10];
    static int anIntArray1638[] = new int[10];
    static int anInt1681;
    static int anInt1682;
    static int anInt1683;
    static int hsl2rgb[];
    static int lightDecay[];
    static DataType dataTypeModel = DataType.REGULAR;
    private static boolean upscaled = true;
    private static int[] OFFSETS_512_334 = null;
    private static int[] OFFSETS_765_503 = null;

    static {
        SINE = Rasterizer.anIntArray1470;
        COSINE = Rasterizer.anIntArray1471;
        hsl2rgb = Rasterizer.hsl2rgb;
        lightDecay = Rasterizer.anIntArray1469;
    }

    public int renderAtPointZ = 0;
    public int renderAtPointY = 0;
    public int[] verticesParticle;
    public int verticesCount;
    //public static Model fetchModel(int j) {
    //	return fetchModel(j, DataType.REGULAR);
    //}
    public int verticesX[];
    public int verticesY[];
    public int verticesZ[];
    public int numberOfTriangleFaces;
    public int face_a[];
    public int face_b[];
    public int face_c[];
    public int face_shade_a[];
    public int face_shade_b[];
    public int face_shade_c[];
    public int face_render_type[];
    public int face_render_priorities[];
    public int face_alpha[];
    public int face_color[];
    public int face_priority;
    public int numberOfTexturesFaces;
    public int textures_face_a[];
    public int textures_face_b[];
    public int textures_face_c[];
    public int anInt1646;
    public int anInt1647;
    public int anInt1648;
    public int anInt1649;
    public int diagonal2DAboveOrigin;
    public int maxY;
    public int diagonal3D;
    public int diagonal3DAboveOrigin;
    public int myPriority;
    public int vertexData[];
    public int triangleData[];
    public int vertexSkin[][];
    public int triangleSkin[][];
    public boolean rendersWithinOneTile;
    public VertexNormal vertexNormalOffset[];
    private int lastRenderedRotation = 0;
    private int renderAtPointX;
    private boolean filtered = false;
    private boolean aBoolean1618;

    public Model() {
    }

    private Model(boolean flag) {
        aBoolean1618 = true;
        rendersWithinOneTile = false;
    }

    public Model(boolean flag, boolean flag1, boolean flag2, Model model) {
        aBoolean1618 = true;
        rendersWithinOneTile = false;
        anInt1620++;
        verticesCount = model.verticesCount;
        numberOfTriangleFaces = model.numberOfTriangleFaces;
        numberOfTexturesFaces = model.numberOfTexturesFaces;
        if (flag2) {
            this.verticesParticle = model.verticesParticle;
            verticesX = model.verticesX;
            verticesY = model.verticesY;
            verticesZ = model.verticesZ;
        } else {
            verticesParticle = new int[verticesCount];
            verticesX = new int[verticesCount];
            verticesY = new int[verticesCount];
            verticesZ = new int[verticesCount];
            for (int j = 0; j < verticesCount; j++) {
                verticesParticle[j] = model.verticesParticle[j];
                verticesX[j] = model.verticesX[j];
                verticesY[j] = model.verticesY[j];
                verticesZ[j] = model.verticesZ[j];
            }

        }
        if (flag) {
            face_color = model.face_color;
        } else {
            face_color = new int[numberOfTriangleFaces];
            for (int k = 0; k < numberOfTriangleFaces; k++)
                face_color[k] = model.face_color[k];

        }
        if (flag1) {
            face_alpha = model.face_alpha;
        } else {
            face_alpha = new int[numberOfTriangleFaces];
            if (model.face_alpha == null) {
                for (int l = 0; l < numberOfTriangleFaces; l++)
                    face_alpha[l] = 0;

            } else {
                for (int i1 = 0; i1 < numberOfTriangleFaces; i1++)
                    face_alpha[i1] = model.face_alpha[i1];

            }
        }
        vertexData = model.vertexData;
        triangleData = model.triangleData;
        face_render_type = model.face_render_type;
        face_a = model.face_a;
        face_b = model.face_b;
        face_c = model.face_c;
        face_render_priorities = model.face_render_priorities;
        face_priority = model.face_priority;
        textures_face_a = model.textures_face_a;
        textures_face_b = model.textures_face_b;
        textures_face_c = model.textures_face_c;
    }

    public Model(boolean flag, boolean flag1, Model model) {
        aBoolean1618 = true;
        rendersWithinOneTile = false;
        anInt1620++;
        verticesCount = model.verticesCount;
        numberOfTriangleFaces = model.numberOfTriangleFaces;
        numberOfTexturesFaces = model.numberOfTexturesFaces;
        if (flag) {
            verticesY = new int[verticesCount];
            for (int j = 0; j < verticesCount; j++)
                verticesY[j] = model.verticesY[j];

        } else {
            verticesY = model.verticesY;
        }
        if (flag1) {
            face_shade_a = new int[numberOfTriangleFaces];
            face_shade_b = new int[numberOfTriangleFaces];
            face_shade_c = new int[numberOfTriangleFaces];
            for (int k = 0; k < numberOfTriangleFaces; k++) {
                face_shade_a[k] = model.face_shade_a[k];
                face_shade_b[k] = model.face_shade_b[k];
                face_shade_c[k] = model.face_shade_c[k];
            }

            face_render_type = new int[numberOfTriangleFaces];
            if (model.face_render_type == null) {
                for (int l = 0; l < numberOfTriangleFaces; l++)
                    face_render_type[l] = 0;

            } else {
                for (int i1 = 0; i1 < numberOfTriangleFaces; i1++)
                    face_render_type[i1] = model.face_render_type[i1];

            }
            super.vertexNormals = new VertexNormal[verticesCount];
            for (int j1 = 0; j1 < verticesCount; j1++) {
                VertexNormal vertNormal = super.vertexNormals[j1] = new VertexNormal();
                VertexNormal class33_1 = model.vertexNormals[j1];
                vertNormal.anInt602 = class33_1.anInt602;
                vertNormal.anInt603 = class33_1.anInt603;
                vertNormal.anInt604 = class33_1.anInt604;
                vertNormal.anInt605 = class33_1.anInt605;
            }

            vertexNormalOffset = model.vertexNormalOffset;
        } else {
            face_shade_a = model.face_shade_a;
            face_shade_b = model.face_shade_b;
            face_shade_c = model.face_shade_c;
            face_render_type = model.face_render_type;
        }
        this.verticesParticle = model.verticesParticle;
        verticesX = model.verticesX;
        verticesZ = model.verticesZ;
        face_color = model.face_color;
        face_alpha = model.face_alpha;
        face_render_priorities = model.face_render_priorities;
        face_priority = model.face_priority;
        face_a = model.face_a;
        face_b = model.face_b;
        face_c = model.face_c;
        textures_face_a = model.textures_face_a;
        textures_face_b = model.textures_face_b;
        textures_face_c = model.textures_face_c;
        super.modelBaseY = model.modelBaseY;

        diagonal2DAboveOrigin = model.diagonal2DAboveOrigin;
        diagonal3DAboveOrigin = model.diagonal3DAboveOrigin;
        diagonal3D = model.diagonal3D;
        anInt1646 = model.anInt1646;
        anInt1648 = model.anInt1648;
        anInt1649 = model.anInt1649;
        anInt1647 = model.anInt1647;
    }

    public Model(int modelId, DataType dataType) {
        byte[] is;

        if (dataType == DataType.OLDSCHOOL) {
            is = modelHeaderOldschool[modelId].modelData;
        } else {
            is = modelHeader[modelId].modelData;
        }
        if (is[is.length - 1] == -1 && is[is.length - 2] == -1)
            read622Model(is, modelId, dataType);
        else
            readOldModel(modelId, dataType);
        int[] newBoots = new int[]{14400, 34508, 32655, 32657, 32658, 29249, 29254, 29250, 29255, 29252, 29253, 29630, 34149, 34146, 34148, 33103, 33102};
        tryOsrsCapePriorityFix(modelId);
        for (int i : newBoots) {
            if (modelId == i) {
                for (int j = 0; j < face_render_priorities.length; j++) {
                    face_render_priorities[j] = 10;
                }
                break;
            }
        }
        if (newmodel[modelId]) {
            if ((Configuration.upscaling)) {
                scale2(32, 32, 32);// 2 is too big -- 3 is almost right
                //	upscaled = false;
            }
            recolour(0, 255);
            if (face_render_priorities != null) { // rofl
                for (int j = 0; j < face_render_priorities.length; j++)
                    face_render_priorities[j] = 10;
            }
        }
        if (modelId == 48841 || modelId == 48825 || modelId == 48817 || modelId == 48802 || modelId == 48840
                || modelId == 45536 || modelId == 38284 || modelId == 45522 || modelId == 45517 || modelId == 45514
                || modelId == 45490 || modelId == 48790 || modelId == 59583) {
            scaleT(32, 32, 32); // rofl owner blade
            translate(0, 6, 0);
        }

        int[][] attachments = ParticleAttachment.getAttachments(modelId);
        if (attachments != null) {
            for (int n = 0; n < attachments.length; n++) {
                int[] attach = attachments[n];
                if (attach[0] == -1) {
                    for (int z = 0; z < face_a.length; ++z) {
                        verticesParticle[face_a[z]] = attach[1] + 1;
                    }
                } else if (attach[0] == -2) {
                    for (int z = 0; z < face_b.length; ++z) {
                        verticesParticle[face_b[z]] = attach[1] + 1;
                    }
                } else if (attach[0] == -3) {
                    for (int z = 0; z < face_c.length; ++z) {
                        verticesParticle[face_c[z]] = attach[1] + 1;
                    }
                } else if (attach[0] == -4) {
                    for (int z = 0; z < face_a.length; ++z) {
                        verticesParticle[face_a[z]] = attach[1] + 1;
                    }

                    for (int z = 0; z < face_b.length; ++z) {
                        verticesParticle[face_b[z]] = attach[1] + 1;
                    }

                    for (int z = 0; z < face_c.length; ++z) {
                        verticesParticle[face_c[z]] = attach[1] + 1;
                    }
                } else {
                    verticesParticle[attach[0]] = attach[1] + 1;
                }
            }
        }
    }

    public Model(int i, Model amodel[]) {
        aBoolean1618 = true;
        rendersWithinOneTile = false;
        anInt1620++;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        verticesCount = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        face_priority = -1;
        for (int k = 0; k < i; k++) {
            Model model = amodel[k];
            if (model != null) {
                verticesCount += model.verticesCount;
                numberOfTriangleFaces += model.numberOfTriangleFaces;
                numberOfTexturesFaces += model.numberOfTexturesFaces;
                flag |= model.face_render_type != null;
                if (model.face_render_priorities != null) {
                    flag1 = true;
                } else {
                    if (face_priority == -1)
                        face_priority = model.face_priority;
                    if (face_priority != model.face_priority)
                        flag1 = true;
                }
                flag2 |= model.face_alpha != null;
                flag3 |= model.triangleData != null;
            }
        }

        this.verticesParticle = new int[verticesCount];
        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        vertexData = new int[verticesCount];
        face_a = new int[numberOfTriangleFaces];
        face_b = new int[numberOfTriangleFaces];
        face_c = new int[numberOfTriangleFaces];
        textures_face_a = new int[numberOfTexturesFaces];
        textures_face_b = new int[numberOfTexturesFaces];
        textures_face_c = new int[numberOfTexturesFaces];
        if (flag)
            face_render_type = new int[numberOfTriangleFaces];
        if (flag1)
            face_render_priorities = new int[numberOfTriangleFaces];
        if (flag2)
            face_alpha = new int[numberOfTriangleFaces];
        if (flag3)
            triangleData = new int[numberOfTriangleFaces];
        face_color = new int[numberOfTriangleFaces];
        verticesCount = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        int l = 0;
        for (int i1 = 0; i1 < i; i1++) {
            Model model_1 = amodel[i1];
            if (model_1 != null) {
                for (int j1 = 0; j1 < model_1.numberOfTriangleFaces; j1++) {
                    if (flag)
                        if (model_1.face_render_type == null) {
                            face_render_type[numberOfTriangleFaces] = 0;
                        } else {
                            int k1 = model_1.face_render_type[j1];
                            if ((k1 & 2) == 2)
                                k1 += l << 2;
                            face_render_type[numberOfTriangleFaces] = k1;
                        }
                    if (flag1)
                        if (model_1.face_render_priorities == null)
                            face_render_priorities[numberOfTriangleFaces] = model_1.face_priority;
                        else
                            face_render_priorities[numberOfTriangleFaces] = model_1.face_render_priorities[j1];
                    if (flag2)
                        if (model_1.face_alpha == null)
                            face_alpha[numberOfTriangleFaces] = 0;
                        else
                            face_alpha[numberOfTriangleFaces] = model_1.face_alpha[j1];

                    if (flag3 && model_1.triangleData != null)
                        triangleData[numberOfTriangleFaces] = model_1.triangleData[j1];
                    face_color[numberOfTriangleFaces] = model_1.face_color[j1];
                    face_a[numberOfTriangleFaces] = method465(model_1, model_1.face_a[j1]);
                    face_b[numberOfTriangleFaces] = method465(model_1, model_1.face_b[j1]);
                    face_c[numberOfTriangleFaces] = method465(model_1, model_1.face_c[j1]);
                    numberOfTriangleFaces++;
                }

                for (int l1 = 0; l1 < model_1.numberOfTexturesFaces; l1++) {
                    textures_face_a[numberOfTexturesFaces] = method465(model_1, model_1.textures_face_a[l1]);
                    textures_face_b[numberOfTexturesFaces] = method465(model_1, model_1.textures_face_b[l1]);
                    textures_face_c[numberOfTexturesFaces] = method465(model_1, model_1.textures_face_c[l1]);
                    numberOfTexturesFaces++;
                }

                l += model_1.numberOfTexturesFaces;
            }
        }

    }

    public Model(Model amodel[]) {
        int i = 2;
        aBoolean1618 = true;
        rendersWithinOneTile = false;
        anInt1620++;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        verticesCount = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        face_priority = -1;
        for (int k = 0; k < i; k++) {
            Model model = amodel[k];
            if (model != null) {
                verticesCount += model.verticesCount;
                numberOfTriangleFaces += model.numberOfTriangleFaces;
                numberOfTexturesFaces += model.numberOfTexturesFaces;
                flag1 |= model.face_render_type != null;
                if (model.face_render_priorities != null) {
                    flag2 = true;
                } else {
                    if (face_priority == -1)
                        face_priority = model.face_priority;
                    if (face_priority != model.face_priority)
                        flag2 = true;
                }
                flag3 |= model.face_alpha != null;
                flag4 |= model.face_color != null;
            }
        }

        this.verticesParticle = new int[verticesCount];
        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        face_a = new int[numberOfTriangleFaces];
        face_b = new int[numberOfTriangleFaces];
        face_c = new int[numberOfTriangleFaces];
        face_shade_a = new int[numberOfTriangleFaces];
        face_shade_b = new int[numberOfTriangleFaces];
        face_shade_c = new int[numberOfTriangleFaces];
        textures_face_a = new int[numberOfTexturesFaces];
        textures_face_b = new int[numberOfTexturesFaces];
        textures_face_c = new int[numberOfTexturesFaces];
        if (flag1)
            face_render_type = new int[numberOfTriangleFaces];
        if (flag2)
            face_render_priorities = new int[numberOfTriangleFaces];
        if (flag3)
            face_alpha = new int[numberOfTriangleFaces];
        if (flag4)
            face_color = new int[numberOfTriangleFaces];
        verticesCount = 0;
        numberOfTriangleFaces = 0;
        numberOfTexturesFaces = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < i; j1++) {
            Model model_1 = amodel[j1];
            if (model_1 != null) {
                int k1 = verticesCount;
                for (int l1 = 0; l1 < model_1.verticesCount; l1++) {
                    this.verticesParticle[verticesCount] = model_1.verticesParticle[l1];
                    verticesX[verticesCount] = model_1.verticesX[l1];
                    verticesY[verticesCount] = model_1.verticesY[l1];
                    verticesZ[verticesCount] = model_1.verticesZ[l1];
                    verticesCount++;
                }

                for (int i2 = 0; i2 < model_1.numberOfTriangleFaces; i2++) {
                    face_a[numberOfTriangleFaces] = model_1.face_a[i2] + k1;
                    face_b[numberOfTriangleFaces] = model_1.face_b[i2] + k1;
                    face_c[numberOfTriangleFaces] = model_1.face_c[i2] + k1;
                    face_shade_a[numberOfTriangleFaces] = model_1.face_shade_a[i2];
                    face_shade_b[numberOfTriangleFaces] = model_1.face_shade_b[i2];
                    face_shade_c[numberOfTriangleFaces] = model_1.face_shade_c[i2];
                    if (flag1)
                        if (model_1.face_render_type == null) {
                            face_render_type[numberOfTriangleFaces] = 0;
                        } else {
                            int j2 = model_1.face_render_type[i2];
                            if ((j2 & 2) == 2)
                                j2 += i1 << 2;
                            face_render_type[numberOfTriangleFaces] = j2;
                        }
                    if (flag2)
                        if (model_1.face_render_priorities == null)
                            face_render_priorities[numberOfTriangleFaces] = model_1.face_priority;
                        else
                            face_render_priorities[numberOfTriangleFaces] = model_1.face_render_priorities[i2];
                    if (flag3)
                        if (model_1.face_alpha == null)
                            face_alpha[numberOfTriangleFaces] = 0;
                        else
                            face_alpha[numberOfTriangleFaces] = model_1.face_alpha[i2];
                    if (flag4 && model_1.face_color != null)
                        face_color[numberOfTriangleFaces] = model_1.face_color[i2];

                    numberOfTriangleFaces++;
                }

                for (int k2 = 0; k2 < model_1.numberOfTexturesFaces; k2++) {
                    textures_face_a[numberOfTexturesFaces] = model_1.textures_face_a[k2] + k1;
                    textures_face_b[numberOfTexturesFaces] = model_1.textures_face_b[k2] + k1;
                    textures_face_c[numberOfTexturesFaces] = model_1.textures_face_c[k2] + k1;
                    numberOfTexturesFaces++;
                }

                i1 += model_1.numberOfTexturesFaces;
            }
        }

        calculateDiagonals();
    }

    public static boolean modelIsFetched(int i, DataType dataType) {
        if (dataType == DataType.OLDSCHOOL) {
            if (modelHeaderOldschool == null)
                return false;
        } else {
            if (modelHeader == null)
                return false;
        }

        ModelHeader modelHeader1;
        ModelHeader modelREGULAR = modelHeader[i];
        ;
        ModelHeader modelOSRS = modelHeaderOldschool[i];
        ;

        if (i == 1637 || i == 1570 || i == 2399 || i == 2737 || i == 2652 || i == 2789 || i == 2697 || i == 2384 || i == 2621 || i == 2432) { //OSRS MODEL FIXES GRAB FROM RS2 INSTEAD
            modelHeader1 = modelHeader[i];

            if (modelHeader1 == null) {
                resourceManager.get(Client.MODEL_IDX - 1, i);
                return false;
            } else {
                return true;
            }
        }


        if (i == 187 || i == 363 || i == 69 || i == 127 || i == 87 || i == 29 || i == 6031 || i == 6033) {
            modelHeader1 = modelHeaderOldschool[i];

            if (modelHeader1 == null) {
                resourceManager.get(Client.OSRS_MODEL_IDX - 1, i);
                return false;
            } else {
                return true;
            }
        }

        if (dataType == DataType.OLDSCHOOL) {
            if (modelREGULAR != null) {
                dataType = DataType.REGULAR;
            }

        }


/**
 //GRAB OLDSCHOOL CHARACTER
 for(int k = 0; k < PLAYER_MODELS.length; k++){
 if(i == PLAYER_MODELS[k]){
 modelHeader1 = modelHeaderOldschool[i];

 if (modelHeader1 == null) {
 resourceManager.get(Client.OSRS_MODEL_IDX - 1, i);
 return false;
 } else {
 return true;
 }
 }
 }
 **/
        if (dataType == DataType.OLDSCHOOL) {
            modelHeader1 = modelHeaderOldschool[i];

            if (modelHeader1 == null) {
                resourceManager.get(Client.OSRS_MODEL_IDX - 1, i);
                return false;
            } else {
                return true;
            }
        } else {
            modelHeader1 = modelHeader[i];

            if (modelHeader1 == null) {
                resourceManager.get(Client.MODEL_IDX - 1, i);
                return false;
            } else {
                return true;
            }
        }

    }

    public static Model fetchModel(int fileId, DataType dataType) {

        ModelHeader modelREGULAR = modelHeader[fileId];
        ModelHeader modelOSRS = modelHeaderOldschool[fileId];
        if (fileId == 1637 || fileId == 1570 || fileId == 2399 || fileId == 2399 || fileId == 2737 || fileId == 2652 || fileId == 2789 || fileId == 2697 || fileId == 2384 || fileId == 2621 || fileId == 2432) {
            dataType = DataType.REGULAR;
        }
        if (fileId == 187 || fileId == 363 || fileId == 69 || fileId == 127 || fileId == 87 || fileId == 29 || fileId == 6031 || fileId == 6033) {
            dataType = DataType.OLDSCHOOL;
        }

        if (dataType == DataType.OLDSCHOOL) {
            if (modelREGULAR != null) {
                dataType = DataType.REGULAR;
            }

        }

/**
 //GRAB OLDSCHOOL CHARACTER
 for(int k = 0; k < PLAYER_MODELS.length; k++){
 if(fileId == PLAYER_MODELS[k]){
 dataType = DataType.OLDSCHOOL;
 }
 }
 **/
        if (dataType == DataType.OLDSCHOOL) {
            if (modelHeaderOldschool == null) {
                return null;
            }
        } else {
            if (modelHeader == null)
                return null;
        }

        if (fileId == 0)
            return null;

        ModelHeader modelHeader;

        if (dataType == DataType.OLDSCHOOL) {
            modelHeader = modelHeaderOldschool[fileId];
            if (modelHeader == null) {
                resourceManager.get(Client.OSRS_MODEL_IDX - 1, fileId);
                return null;
            } else {
                return new Model(fileId, dataType);
            }
        } else {
            modelHeader = Model.modelHeader[fileId];
            if (modelHeader == null) {
                resourceManager.get(Client.MODEL_IDX - 1, fileId);
                return null;
            } else {
                return new Model(fileId, dataType);
            }
        }
    }

    public static int[] getOffsets(int j, int k) {
        if (j == 512 && k == 334 && OFFSETS_512_334 != null)
            return OFFSETS_512_334;

        if (j == 765 + 1 && k == 503 && OFFSETS_765_503 != null)
            return OFFSETS_765_503;

        int[] t = new int[k];
        for (int l = 0; l < k; l++)
            t[l] = j * l;

        if (j == 512 && k == 334)
            OFFSETS_512_334 = t;

        if (j == 765 + 1 && k == 503)
            OFFSETS_765_503 = t;

        return t;
    }

    public static void initialise(int i, OnDemandFetcherParent onDemandFetcher) {
        modelHeader = new ModelHeader[110000];
        modelHeaderOldschool = new ModelHeader[110000];
        newmodel = new boolean[110000];
        resourceManager = onDemandFetcher;
    }

    public static int method481(int i, int j, int k) {
        if (i == 65535)
            return 0;
        if ((k & 2) == 2) {
            if (j < 0)
                j = 0;
            else if (j > 127)
                j = 127;
            j = 127 - j;
            return j;
        }

        j = j * (i & 0x7f) >> 7;
        if (j < 2)
            j = 2;
        else if (j > 126)
            j = 126;
        return (i & 0xff80) + j;
    }

    /**
     * public static boolean modelIsFetched(int i) {
     * if (modelHeader == null)
     * return false;
     * <p>
     * ModelHeader class21 = modelHeader[i];
     * if (class21 == null) {
     * resourceManager.get(i);
     * return false;
     * } else {
     * return true;
     * }
     * }
     **/
    public static void nullLoader() {
        modelHeader = null;
        modelHeaderOldschool = null;
        hasAnEdgeToRestrict = null;
        outOfReach = null;
        projected_vertex_y = null;
        anIntArray1667 = null;
        camera_vertex_y = null;
        camera_vertex_x = null;
        camera_vertex_z = null;
        depthListIndices = null;
        faceLists = null;
        anIntArray1673 = null;
        anIntArrayArray1674 = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        anIntArray1677 = null;
        SINE = null;
        COSINE = null;
        hsl2rgb = null;
        lightDecay = null;
    }

    public static void readFirstModelData(byte abyte0[], int j, DataType dataType) {
        try {
            dataTypeModel = dataType;
            if (abyte0 == null) {
                ModelHeader class21 = modelHeader[j] = new ModelHeader();

                if (dataType == DataType.OLDSCHOOL) {
                    class21 = modelHeaderOldschool[j] = new ModelHeader();
                } else {
                    class21 = modelHeader[j] = new ModelHeader();
                }
                class21.verticeCount = 0;
                class21.triangleCount = 0;
                class21.texturedTriangleCount = 0;
                return;
            }
            Stream stream = new Stream(abyte0);
            stream.currentOffset = abyte0.length - 18;
            ModelHeader class21_1;

            if (dataType == DataType.OLDSCHOOL) {
                class21_1 = modelHeaderOldschool[j] = new ModelHeader();
            } else {
                class21_1 = modelHeader[j] = new ModelHeader();
            }
            class21_1.modelData = abyte0;
            class21_1.verticeCount = stream.readUnsignedWord();
            class21_1.triangleCount = stream.readUnsignedWord();
            class21_1.texturedTriangleCount = stream.readUnsignedByte();
            int k = stream.readUnsignedByte();
            int l = stream.readUnsignedByte();
            int i1 = stream.readUnsignedByte();
            int j1 = stream.readUnsignedByte();
            int k1 = stream.readUnsignedByte();
            int l1 = stream.readUnsignedWord();
            int i2 = stream.readUnsignedWord();
            int j2 = stream.readUnsignedWord();
            int k2 = stream.readUnsignedWord();
            int l2 = 0;
            class21_1.verticesModOffset = l2;
            l2 += class21_1.verticeCount;
            class21_1.triMeshLinkOffset = l2;
            l2 += class21_1.triangleCount;
            class21_1.facePriorityBasePos = l2;
            if (l == 255)
                l2 += class21_1.triangleCount;
            else
                class21_1.facePriorityBasePos = -l - 1;
            class21_1.tskinBasepos = l2;
            if (j1 == 1)
                l2 += class21_1.triangleCount;
            else
                class21_1.tskinBasepos = -1;
            class21_1.drawTypeBasePos = l2;
            if (k == 1)
                l2 += class21_1.triangleCount;
            else
                class21_1.drawTypeBasePos = -1;
            class21_1.vskinBasePos = l2;
            if (k1 == 1)
                l2 += class21_1.verticeCount;
            else
                class21_1.vskinBasePos = -1;
            class21_1.alphaBasepos = l2;
            if (i1 == 1)
                l2 += class21_1.triangleCount;
            else
                class21_1.alphaBasepos = -1;
            class21_1.triVPointOffset = l2;
            l2 += k2;
            class21_1.triColourOffset = l2;
            l2 += class21_1.triangleCount * 2;
            class21_1.textureInfoBasePos = l2;
            l2 += class21_1.texturedTriangleCount * 6;
            class21_1.verticesXOffset = l2;
            l2 += l1;
            class21_1.verticesYOffset = l2;
            l2 += i2;
            class21_1.verticesZOffset = l2;
            l2 += j2;
        } catch (Exception _ex) {
        }
    }

    public static void removeFromHeader(int j) {
        modelHeader[j] = null;
    }

    private void tryOsrsCapePriorityFix(int modelId) {
        if (!osrsCapeModels.contains(modelId))
            return;
        for (int i = 0; i < face_render_priorities.length; i++) {
            //tested on zamorak imbued cape, idea is to keep the symbols on the cape cleanly visible while not seeing the cape through the body/head when viewing player from the front
            if (face_render_priorities[i] != 6) {
                face_render_priorities[i] = 10;
            }
        }
    }

    public void applyTransform(int i, DataType dataType) {
        if (vertexSkin == null)
            return;
        if (i == -1)
            return;
        FrameReader class36 = FrameReader.forID(i, dataType);
        if (class36 == null)
            return;
        SkinList class18 = class36.mySkinList;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        for (int k = 0; k < class36.stepCount; k++) {
            int l = class36.opCodeLinkTable[k];
            method472(class18.opcodes[l], class18.skinList[l], class36.xOffset[k], class36.yOffset[k],
                    class36.zOffset[k]);
        }

    }

    public void mixTransform(int ai[], int j, int k, DataType dataType) {
        if (k == -1)
            return;
        if (ai == null || j == -1) {
            applyTransform(k, dataType);
            return;
        }
        FrameReader class36 = FrameReader.forID(k, dataType);
        if (class36 == null)
            return;
        FrameReader class36_1 = FrameReader.forID(j, dataType);
        if (class36_1 == null) {
            applyTransform(k, dataType);
            return;
        }
        SkinList class18 = class36.mySkinList;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        int l = 0;
        int i1 = ai[l++];
        for (int j1 = 0; j1 < class36.stepCount; j1++) {
            int k1;
            for (k1 = class36.opCodeLinkTable[j1]; k1 > i1; i1 = ai[l++])
                ;
            if (k1 != i1 || class18.opcodes[k1] == 0)
                method472(class18.opcodes[k1], class18.skinList[k1], class36.xOffset[j1], class36.yOffset[j1], class36.zOffset[j1]);
        }

        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        l = 0;
        i1 = ai[l++];
        for (int l1 = 0; l1 < class36_1.stepCount; l1++) {
            int i2;
            for (i2 = class36_1.opCodeLinkTable[l1]; i2 > i1; i1 = ai[l++])
                ;
            if (i2 == i1 || class18.opcodes[i2] == 0)
                method472(class18.opcodes[i2], class18.skinList[i2], class36_1.xOffset[l1], class36_1.yOffset[l1], class36_1.zOffset[l1]);
        }
    }

    public void applyTransform(int firstFrame, int nextFrame, int end, int cycle, DataType dataType) {

        try {
            if (vertexSkin != null && firstFrame != -1) {
                FrameReader currentAnimation = FrameReader.forID(firstFrame, dataType);
                SkinList list1 = currentAnimation.mySkinList;
                anInt1681 = 0;
                anInt1682 = 0;
                anInt1683 = 0;
                FrameReader nextAnimation = null;
                SkinList list2 = null;
                if (nextFrame != -1) {
                    nextAnimation = FrameReader.forID(nextFrame, dataType);
                    if (nextAnimation.mySkinList != list1)
                        nextAnimation = null;
                    list2 = nextAnimation.mySkinList;
                }
                if (nextAnimation == null || list2 == null) {
                    for (int i_263_ = 0; i_263_ < currentAnimation.stepCount; i_263_++) {
                        int i_264_ = currentAnimation.opCodeLinkTable[i_263_];
                        method472(list1.opcodes[i_264_], list1.skinList[i_264_], currentAnimation.xOffset[i_263_], currentAnimation.yOffset[i_263_], currentAnimation.zOffset[i_263_]);

                    }
                } else {
                    for (int i1 = 0; i1 < currentAnimation.stepCount; i1++) {
                        int n1 = currentAnimation.opCodeLinkTable[i1];
                        int opcode = list1.opcodes[n1];
                        int[] skin = list1.skinList[n1];
                        int x = currentAnimation.xOffset[i1];
                        int y = currentAnimation.yOffset[i1];
                        int z = currentAnimation.zOffset[i1];
                        boolean found = false;
                        for (int i2 = 0; i2 < nextAnimation.stepCount; i2++) {
                            int n2 = nextAnimation.opCodeLinkTable[i2];
                            if (list2.skinList[n2].equals(skin)) {
                                if (opcode != 2) {
                                    x += (nextAnimation.xOffset[i2] - x) * cycle / end;
                                    y += (nextAnimation.yOffset[i2] - y) * cycle / end;
                                    z += (nextAnimation.zOffset[i2] - z) * cycle / end;
                                } else {
                                    x &= 0x7ff;
                                    y &= 0x7ff;
                                    z &= 0x7ff;
                                    int dx = nextAnimation.xOffset[i2] - x & 0x7ff;
                                    int dy = nextAnimation.yOffset[i2] - y & 0x7ff;
                                    int dz = nextAnimation.zOffset[i2] - z & 0x7ff;
                                    if (dx >= 1024) {
                                        dx -= 2048;
                                    }
                                    if (dy >= 1024) {
                                        dy -= 2048;
                                    }
                                    if (dz >= 1024) {
                                        dz -= 2048;
                                    }
                                    x = x + dx * cycle / end & 0x7ff;
                                    y = y + dy * cycle / end & 0x7ff;
                                    z = z + dz * cycle / end & 0x7ff;
                                }
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            if (opcode != 3 && opcode != 2) {
                                x = x * (end - cycle) / end;
                                y = y * (end - cycle) / end;
                                z = z * (end - cycle) / end;
                            } else if (opcode == 3) {
                                x = (x * (end - cycle) + (cycle << 7)) / end;
                                y = (y * (end - cycle) + (cycle << 7)) / end;
                                z = (z * (end - cycle) + (cycle << 7)) / end;
                            } else {
                                x &= 0x7ff;
                                y &= 0x7ff;
                                z &= 0x7ff;
                                int dx = -x & 0x7ff;
                                int dy = -y & 0x7ff;
                                int dz = -z & 0x7ff;
                                if (dx >= 1024) {
                                    dx -= 2048;
                                }
                                if (dy >= 1024) {
                                    dy -= 2048;
                                }
                                if (dz >= 1024) {
                                    dz -= 2048;
                                }
                                x = x + dx * cycle / end & 0x7ff;
                                y = y + dy * cycle / end & 0x7ff;
                                z = z + dz * cycle / end & 0x7ff;
                            }
                        }
                        method472(opcode, skin, x, y, z);
                    }
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
            applyTransform(firstFrame, dataType); //Cheap fix
        }
    }

    public void applyTransform_2(int i, int frame2, DataType dataType) {
        if (vertexSkin == null)
            return;
        if (i == -1)
            return;
        FrameReader class36 = FrameReader.forID(i, dataType);
        if (class36 == null)
            return;
        FrameReader class36_1 = FrameReader.forID(frame2, dataType);
        FrameReader fr = FrameReader.getTween(class36, class36_1);
        class36 = fr;
        SkinList class18 = class36.mySkinList;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        for (int k = 0; k < class36.stepCount; k++) {
            int l = class36.opCodeLinkTable[k];
            method472(class18.opcodes[l], class18.skinList[l], class36.xOffset[k], class36.yOffset[k],
                    class36.zOffset[k]);
        }

    }

    public void calcDiagonalsAndStats(int i) {
        super.modelBaseY = 0;
        diagonal2DAboveOrigin = 0;
        maxY = 0;
        anInt1646 = 0xf423f;
        anInt1647 = 0xfff0bdc1;
        anInt1648 = 0xfffe7961;
        anInt1649 = 0x1869f;
        for (int j = 0; j < verticesCount; j++) {
            int k = verticesX[j];
            int l = verticesY[j];
            int i1 = verticesZ[j];
            if (k < anInt1646)
                anInt1646 = k;
            if (k > anInt1647)
                anInt1647 = k;
            if (i1 < anInt1649)
                anInt1649 = i1;
            if (i1 > anInt1648)
                anInt1648 = i1;
            if (-l > super.modelBaseY)
                super.modelBaseY = -l;
            if (l > maxY)
                maxY = l;
            int j1 = k * k + i1 * i1;
            if (j1 > diagonal2DAboveOrigin)
                diagonal2DAboveOrigin = j1;
        }

        diagonal2DAboveOrigin = (int) Math.sqrt(diagonal2DAboveOrigin);
        diagonal3DAboveOrigin = (int) Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + super.modelBaseY * super.modelBaseY);
        if (i != 21073) {
            return;
        } else {
            diagonal3D = diagonal3DAboveOrigin + (int) Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + maxY * maxY);
            return;
        }
    }

    public void calculateDiagonals() {
        super.modelBaseY = 0;
        diagonal2DAboveOrigin = 0;
        maxY = 0;
        for (int i = 0; i < verticesCount; i++) {
            int j = verticesX[i];
            int k = verticesY[i];
            int l = verticesZ[i];
            if (-k > super.modelBaseY)
                super.modelBaseY = -k;
            if (k > maxY)
                maxY = k;
            int i1 = j * j + l * l;
            if (i1 > diagonal2DAboveOrigin)
                diagonal2DAboveOrigin = i1;
        }
        diagonal2DAboveOrigin = (int) (Math.sqrt(diagonal2DAboveOrigin) + 0.98999999999999999D);
        diagonal3DAboveOrigin = (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + super.modelBaseY * super.modelBaseY)
                + 0.98999999999999999D);
        diagonal3D = diagonal3DAboveOrigin
                + (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + maxY * maxY) + 0.98999999999999999D);
    }

    public void createBones() {
        if (vertexData != null) {
            int ai[] = new int[256];
            int j = 0;
            for (int l = 0; l < verticesCount; l++) {
                int j1 = vertexData[l];
                ai[j1]++;
                if (j1 > j)
                    j = j1;
            }
            vertexSkin = null;
            vertexSkin = new int[j + 1][];
            for (int k1 = 0; k1 <= j; k1++) {
                vertexSkin[k1] = new int[ai[k1]];
                ai[k1] = 0;
            }

            for (int j2 = 0; j2 < verticesCount; j2++) {
                int l2 = vertexData[j2];
                vertexSkin[l2][ai[l2]++] = j2;
            }

            vertexData = null;
            ai = null;
        }
        if (triangleData != null) {
            int ai1[] = new int[256];
            int k = 0;
            for (int i1 = 0; i1 < numberOfTriangleFaces; i1++) {
                int l1 = triangleData[i1];
                ai1[l1]++;
                if (l1 > k)
                    k = l1;
            }
            triangleSkin = null;
            triangleSkin = new int[k + 1][];
            for (int i2 = 0; i2 <= k; i2++) {
                triangleSkin[i2] = new int[ai1[i2]];
                ai1[i2] = 0;
            }

            for (int k2 = 0; k2 < numberOfTriangleFaces; k2++) {
                int i3 = triangleData[k2];
                triangleSkin[i3][ai1[i3]++] = k2;
            }

            triangleData = null;
            ai1 = null;
        }
    }

    private final boolean cursorOn(int cursorX, int cursorY, int proj_vertex_yA, int proj_vertex_yB, int proj_vertex_yC,
                                   int proj_vertex_xA, int proj_vertex_xB, int proj_vertex_xC) {
        if (cursorY < proj_vertex_yA && cursorY < proj_vertex_yB && cursorY < proj_vertex_yC)
            return false;
        if (cursorY > proj_vertex_yA && cursorY > proj_vertex_yB && cursorY > proj_vertex_yC)
            return false;
        if (cursorX < proj_vertex_xA && cursorX < proj_vertex_xB && cursorX < proj_vertex_xC)
            return false;
        return cursorX <= proj_vertex_xA || cursorX <= proj_vertex_xB || cursorX <= proj_vertex_xC;
    }

    public void filterTriangles() {
        for (int triangleId = 0; triangleId < numberOfTriangleFaces; triangleId++) {
            int l = face_a[triangleId];
            int k1 = face_b[triangleId];
            int j2_ = face_c[triangleId];
            boolean b = true;
            label2:
            for (int triId = 0; triId < numberOfTriangleFaces; triId++) {
                if (triId == triangleId)
                    continue label2;
                if (face_a[triId] == l) {
                    b = false;
                    break label2;
                }
                if (face_b[triId] == k1) {
                    b = false;
                    break label2;
                }
                if (face_c[triId] == j2_) {
                    b = false;
                    break label2;
                }
            }
            if (b) {
                if (face_render_type != null)
                    // face_render_type[triangleId] = -1;
                    face_alpha[triangleId] = 255;

            }
        }
    }

    public void light(int i, int j, int k, int l, int i1, boolean flag) {
        try {
            int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
            int k1 = j * j1 >> 8;
            if (face_shade_a == null) {
                face_shade_a = new int[numberOfTriangleFaces];
                face_shade_b = new int[numberOfTriangleFaces];
                face_shade_c = new int[numberOfTriangleFaces];
            }
            if (super.vertexNormals == null) {
                super.vertexNormals = new VertexNormal[verticesCount];
                for (int l1 = 0; l1 < verticesCount; l1++)
                    super.vertexNormals[l1] = new VertexNormal();

            }
            for (int i2 = 0; i2 < numberOfTriangleFaces; i2++) {
                if (face_color != null && face_alpha != null) {
                    if (face_color[i2] == 65535 || face_color[i2] == 1 || face_color[i2] == 16705
                            || face_color[i2] == 255)
                        face_alpha[i2] = 255;
                }
                int j2 = face_a[i2];
                int l2 = face_b[i2];
                int i3 = face_c[i2];
                int j3 = verticesX[l2] - verticesX[j2];
                int k3 = verticesY[l2] - verticesY[j2];
                int l3 = verticesZ[l2] - verticesZ[j2];
                int i4 = verticesX[i3] - verticesX[j2];
                int j4 = verticesY[i3] - verticesY[j2];
                int k4 = verticesZ[i3] - verticesZ[j2];
                int l4 = k3 * k4 - j4 * l3;
                int i5 = l3 * i4 - k4 * j3;
                int j5;
                for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192
                        || j5 < -8192; j5 >>= 1) {
                    l4 >>= 1;
                    i5 >>= 1;
                }

                int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
                if (k5 <= 0)
                    k5 = 1;
                l4 = (l4 * 256) / k5;
                i5 = (i5 * 256) / k5;
                j5 = (j5 * 256) / k5;

                if (face_render_type == null || (face_render_type[i2] & 1) == 0) {

                    VertexNormal vNormal = super.vertexNormals[j2];
                    vNormal.anInt602 += l4;
                    vNormal.anInt603 += i5;
                    vNormal.anInt604 += j5;
                    vNormal.anInt605++;
                    vNormal = super.vertexNormals[l2];
                    vNormal.anInt602 += l4;
                    vNormal.anInt603 += i5;
                    vNormal.anInt604 += j5;
                    vNormal.anInt605++;
                    vNormal = super.vertexNormals[i3];
                    vNormal.anInt602 += l4;
                    vNormal.anInt603 += i5;
                    vNormal.anInt604 += j5;
                    vNormal.anInt605++;
                    vNormal = null;

                } else {

                    int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
                    face_shade_a[i2] = method481(face_color[i2], l5, face_render_type[i2]);

                }
            }

            if (flag) {
                method480(i, k1, k, l, i1);
            } else {
                vertexNormalOffset = new VertexNormal[verticesCount];
                for (int k2 = 0; k2 < verticesCount; k2++) {
                    VertexNormal vNormal = super.vertexNormals[k2];
                    vertexNormalOffset[k2] = new VertexNormal();
                    vertexNormalOffset[k2].anInt602 = vNormal.anInt602;
                    vertexNormalOffset[k2].anInt603 = vNormal.anInt603;
                    vertexNormalOffset[k2].anInt604 = vNormal.anInt604;
                    vertexNormalOffset[k2].anInt605 = vNormal.anInt605;
                }

            }
            if (flag) {
                calculateDiagonals();
                return;
            } else {
                calcDiagonalsAndStats(21073);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void method464(Model model, boolean flag) {
        verticesCount = model.verticesCount;
        numberOfTriangleFaces = model.numberOfTriangleFaces;
        numberOfTexturesFaces = model.numberOfTexturesFaces;
        if (anIntArray1622.length < verticesCount) {
            anIntArray1622 = new int[verticesCount + 10000];
            anIntArray1623 = new int[verticesCount + 10000];
            anIntArray1624 = new int[verticesCount + 10000];
        }
        verticesParticle = new int[verticesCount];
        verticesX = anIntArray1622;
        verticesY = anIntArray1623;
        verticesZ = anIntArray1624;
        for (int k = 0; k < verticesCount; k++) {
            verticesParticle[k] = model.verticesParticle[k];
            verticesX[k] = model.verticesX[k];
            verticesY[k] = model.verticesY[k];
            verticesZ[k] = model.verticesZ[k];
        }

        if (flag) {
            face_alpha = model.face_alpha;
        } else {
            if (anIntArray1625.length < numberOfTriangleFaces)
                anIntArray1625 = new int[numberOfTriangleFaces + 100];
            face_alpha = anIntArray1625;
            if (model.face_alpha == null) {
                for (int l = 0; l < numberOfTriangleFaces; l++)
                    face_alpha[l] = 0;

            } else {
                for (int i1 = 0; i1 < numberOfTriangleFaces; i1++)
                    face_alpha[i1] = model.face_alpha[i1];

            }
        }
        face_render_type = model.face_render_type;
        face_color = model.face_color;
        face_render_priorities = model.face_render_priorities;
        face_priority = model.face_priority;
        triangleSkin = model.triangleSkin;
        vertexSkin = model.vertexSkin;
        face_a = model.face_a;
        face_b = model.face_b;
        face_c = model.face_c;
        face_shade_a = model.face_shade_a;
        face_shade_b = model.face_shade_b;
        face_shade_c = model.face_shade_c;
        textures_face_a = model.textures_face_a;
        textures_face_b = model.textures_face_b;
        textures_face_c = model.textures_face_c;
    }

    private final int method465(Model model, int i) {
        int j = -1;
        int var4 = model.verticesParticle[i];
        int k = model.verticesX[i];
        int l = model.verticesY[i];
        int i1 = model.verticesZ[i];
        for (int j1 = 0; j1 < verticesCount; j1++) {
            if (k != verticesX[j1] || l != verticesY[j1] || i1 != verticesZ[j1])
                continue;
            j = j1;
            break;
        }

        if (j == -1) {
            verticesParticle[verticesCount] = var4;
            verticesX[verticesCount] = k;
            verticesY[verticesCount] = l;
            verticesZ[verticesCount] = i1;
            if (model.vertexData != null)
                vertexData[verticesCount] = model.vertexData[i];
            j = verticesCount++;
        }
        return j;
    }

    public void method471(int ai[], int j, int k, DataType dataType) {
        if (k == -1)
            return;
        if (ai == null || j == -1) {
            applyTransform(k, dataType);
            return;
        }
        FrameReader class36 = FrameReader.forID(k, dataType);
        if (class36 == null)
            return;
        FrameReader class36_1 = FrameReader.forID(j, dataType);
        if (class36_1 == null) {
            applyTransform(k, dataType);
            return;
        }
        SkinList class18 = class36.mySkinList;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        int l = 0;
        int i1 = ai[l++];
        for (int j1 = 0; j1 < class36.stepCount; j1++) {
            int k1;
            for (k1 = class36.opCodeLinkTable[j1]; k1 > i1; i1 = ai[l++])
                ;
            if (k1 != i1 || class18.opcodes[k1] == 0)
                method472(class18.opcodes[k1], class18.skinList[k1], class36.xOffset[j1], class36.yOffset[j1],
                        class36.zOffset[j1]);
        }

        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        l = 0;
        i1 = ai[l++];
        try {
            for (int l1 = 0; l1 < class36_1.stepCount; l1++) {
                int i2;
                for (i2 = class36_1.opCodeLinkTable[l1]; i2 > i1; i1 = ai[l++])
                    ;
                if (i2 == i1 || class18.opcodes[i2] == 0)
                    method472(class18.opcodes[i2], class18.skinList[i2], class36_1.xOffset[l1], class36_1.yOffset[l1],
                            class36_1.zOffset[l1]);
            }
        } catch (Exception e) {
        }

    }

    public void method471_2(int mixingData[], int j, int frameId, int frameId2, DataType dataType) {
        if (frameId == -1)
            return;
        if (mixingData == null || j == -1) {
            applyTransform(frameId, dataType);
            return;
        }
        FrameReader class36 = FrameReader.forID(frameId, dataType);
        if (class36 == null)
            return;
        FrameReader class36_1 = FrameReader.forID(j, dataType);
        if (class36_1 == null) {
            applyTransform(frameId, dataType);
            return;
        }
        FrameReader fr = FrameReader.forID(frameId2, dataType);
        FrameReader tween = FrameReader.getTween(class36_1, fr);
        class36 = tween;
        SkinList class18 = class36.mySkinList;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        int l = 0;
        int i1 = mixingData[l++];
        for (int j1 = 0; j1 < class36.stepCount; j1++) {
            int k1;
            for (k1 = class36.opCodeLinkTable[j1]; k1 > i1; i1 = mixingData[l++])
                ;
            if (k1 != i1 || class18.opcodes[k1] == 0)
                method472(class18.opcodes[k1], class18.skinList[k1], class36.xOffset[j1], class36.yOffset[j1],
                        class36.zOffset[j1]);
        }

        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        l = 0;
        i1 = mixingData[l++];
        for (int l1 = 0; l1 < class36_1.stepCount; l1++) {
            int i2;
            for (i2 = class36_1.opCodeLinkTable[l1]; i2 > i1; i1 = mixingData[l++])
                ;
            if (i2 == i1 || class18.opcodes[i2] == 0)
                method472(class18.opcodes[i2], class18.skinList[i2], class36_1.xOffset[l1], class36_1.yOffset[l1],
                        class36_1.zOffset[l1]);
        }

    }

    /*private void method472(int i, int ai[], int j, int k, int l) {

		int i1 = ai.length;
		if (i == 0) {
			int j1 = 0;
			anInt1681 = 0;
			anInt1682 = 0;
			anInt1683 = 0;
			for (int k2 = 0; k2 < i1; k2++) {
				int l3 = ai[k2];
				if (l3 < vertexSkin.length) {
					int ai5[] = vertexSkin[l3];
					for (int i5 = 0; i5 < ai5.length; i5++) {
						int j6 = ai5[i5];
						anInt1681 += verticesXCoordinate[j6];
						anInt1682 += verticesYCoordinate[j6];
						anInt1683 += verticesZCoordinate[j6];
						j1++;
					}

				}
			}

			if (j1 > 0) {
				anInt1681 = anInt1681 / j1 + j;
				anInt1682 = anInt1682 / j1 + k;
				anInt1683 = anInt1683 / j1 + l;
				return;
			} else {
				anInt1681 = j;
				anInt1682 = k;
				anInt1683 = l;
				return;
			}
		}
		if (i == 1) {
			for (int k1 = 0; k1 < i1; k1++) {
				int l2 = ai[k1];
				if (l2 < vertexSkin.length) {
					int ai1[] = vertexSkin[l2];
					for (int i4 = 0; i4 < ai1.length; i4++) {
						int j5 = ai1[i4];
						verticesXCoordinate[j5] += j;
						verticesYCoordinate[j5] += k;
						verticesZCoordinate[j5] += l;
					}

				}
			}

			return;
		}
		if (i == 2) {
			for (int l1 = 0; l1 < i1; l1++) {
				int i3 = ai[l1];
				if (i3 < vertexSkin.length) {
					int ai2[] = vertexSkin[i3];
					for (int j4 = 0; j4 < ai2.length; j4++) {
						int k5 = ai2[j4];
						verticesXCoordinate[k5] -= anInt1681;
						verticesYCoordinate[k5] -= anInt1682;
						verticesZCoordinate[k5] -= anInt1683;
						int k6 = (j & 0xff) * 8;
						int l6 = (k & 0xff) * 8;
						int i7 = (l & 0xff) * 8;
						if (i7 != 0) {
							int j7 = SINE[i7];
							int i8 = COSINE[i7];
							int l8 = verticesYCoordinate[k5] * j7 + verticesXCoordinate[k5] * i8 >> 16;
							verticesYCoordinate[k5] = verticesYCoordinate[k5] * i8 - verticesXCoordinate[k5] * j7 >> 16;
							verticesXCoordinate[k5] = l8;
						}
						if (k6 != 0) {
							int k7 = SINE[k6];
							int j8 = COSINE[k6];
							int i9 = verticesYCoordinate[k5] * j8 - verticesZCoordinate[k5] * k7 >> 16;
							verticesZCoordinate[k5] = verticesYCoordinate[k5] * k7 + verticesZCoordinate[k5] * j8 >> 16;
							verticesYCoordinate[k5] = i9;
						}
						if (l6 != 0) {
							int l7 = SINE[l6];
							int k8 = COSINE[l6];
							int j9 = verticesZCoordinate[k5] * l7 + verticesXCoordinate[k5] * k8 >> 16;
							verticesZCoordinate[k5] = verticesZCoordinate[k5] * k8 - verticesXCoordinate[k5] * l7 >> 16;
							verticesXCoordinate[k5] = j9;
						}
						verticesXCoordinate[k5] += anInt1681;
						verticesYCoordinate[k5] += anInt1682;
						verticesZCoordinate[k5] += anInt1683;
					}

				}
			}
			return;
		}
		if (i == 3) {
			for (int i2 = 0; i2 < i1; i2++) {
				int j3 = ai[i2];
				if (j3 < vertexSkin.length) {
					int ai3[] = vertexSkin[j3];
					for (int k4 = 0; k4 < ai3.length; k4++) {
						int l5 = ai3[k4];
						verticesXCoordinate[l5] -= anInt1681;
						verticesYCoordinate[l5] -= anInt1682;
						verticesZCoordinate[l5] -= anInt1683;
						verticesXCoordinate[l5] = (verticesXCoordinate[l5] * j) / 128;
						verticesYCoordinate[l5] = (verticesYCoordinate[l5] * k) / 128;
						verticesZCoordinate[l5] = (verticesZCoordinate[l5] * l) / 128;
						verticesXCoordinate[l5] += anInt1681;
						verticesYCoordinate[l5] += anInt1682;
						verticesZCoordinate[l5] += anInt1683;
					}
				}
			}
			return;
		}
		if (i == 5 && triangleSkin != null && face_alpha != null) {
			for (int j2 = 0; j2 < i1; j2++) {
				int k3 = ai[j2];
				if (k3 < triangleSkin.length) {
					int ai4[] = triangleSkin[k3];
					for (int l4 = 0; l4 < ai4.length; l4++) {
						int i6 = ai4[l4];
						(face_alpha)[i6] += j * 8;
						if ((face_alpha)[i6] < 0)
							(face_alpha)[i6] = 0;
						if ((face_alpha)[i6] > 255)
							(face_alpha)[i6] = 255;
					}
				}
			}
		}
	}*/
    private void method472(int animationType, int skin[], int xOffset, int yOffset, int zOffset) {

        int i1 = skin.length;
        if (animationType == 0) {
            int j1 = 0;
            anInt1681 = 0;
            anInt1682 = 0;
            anInt1683 = 0;
            for (int k2 = 0; k2 < i1; k2++) {
                int l3 = skin[k2];
                if (l3 < vertexSkin.length) {
                    int ai5[] = vertexSkin[l3];
                    for (int i5 = 0; i5 < ai5.length; i5++) {
                        int j6 = ai5[i5];
                        anInt1681 += verticesX[j6];
                        anInt1682 += verticesY[j6];
                        anInt1683 += verticesZ[j6];
                        j1++;
                    }

                }
            }

            if (j1 > 0) {
                anInt1681 = (int) (anInt1681 / j1 + xOffset);
                anInt1682 = (int) (anInt1682 / j1 + yOffset);
                anInt1683 = (int) (anInt1683 / j1 + zOffset);
                return;
            } else {
                anInt1681 = (int) xOffset;
                anInt1682 = (int) yOffset;
                anInt1683 = (int) zOffset;
                return;
            }
        }
        if (animationType == 1) {
            for (int k1 = 0; k1 < i1; k1++) {
                int l2 = skin[k1];
                if (l2 < vertexSkin.length) {
                    int ai1[] = vertexSkin[l2];
                    for (int i4 = 0; i4 < ai1.length; i4++) {
                        int j5 = ai1[i4];
                        verticesX[j5] += xOffset;
                        verticesY[j5] += yOffset;
                        verticesZ[j5] += zOffset;
                    }

                }
            }

            return;
        }
        if (animationType == 2) {
            for (int l1 = 0; l1 < i1; l1++) {
                int i3 = skin[l1];
                if (i3 < vertexSkin.length) {
                    int ai2[] = vertexSkin[i3];
                    for (int j4 = 0; j4 < ai2.length; j4++) {
                        int k5 = ai2[j4];
                        verticesX[k5] -= anInt1681;
                        verticesY[k5] -= anInt1682;
                        verticesZ[k5] -= anInt1683;
                        //int j, int k, int l
                        if (zOffset != 0) {
                            int j7 = SINE[zOffset];
                            int i8 = COSINE[zOffset];
                            int l8 = verticesY[k5] * j7 + verticesX[k5] * i8 >> 16;
                            verticesY[k5] = verticesY[k5] * i8 - verticesX[k5] * j7 >> 16;
                            verticesX[k5] = l8;
                        }
                        if (xOffset != 0) {
                            int k7 = SINE[xOffset];
                            int j8 = COSINE[xOffset];
                            int i9 = verticesY[k5] * j8 - verticesZ[k5] * k7 >> 16;
                            verticesZ[k5] = verticesY[k5] * k7 + verticesZ[k5] * j8 >> 16;
                            verticesY[k5] = i9;
                        }
                        if (yOffset != 0) {
                            int l7 = SINE[yOffset];
                            int k8 = COSINE[yOffset];
                            int j9 = verticesZ[k5] * l7 + verticesX[k5] * k8 >> 16;
                            verticesZ[k5] = verticesZ[k5] * k8 - verticesX[k5] * l7 >> 16;
                            verticesX[k5] = j9;
                        }
                        verticesX[k5] += anInt1681;
                        verticesY[k5] += anInt1682;
                        verticesZ[k5] += anInt1683;
                    }

                }
            }
            return;
        }
		/*if (animationType == 2) {
			for (int l1 = 0; l1 < i1; l1++) {
				int i3 = skin[l1];
				if (i3 < vertexSkin.length) {
					int ai2[] = vertexSkin[i3];
					for (int j4 = 0; j4 < ai2.length; j4++) {
						int k5 = ai2[j4];
						verticesXCoordinate[k5] -= xAnimOffset;
						verticesYCoordinate[k5] -= yAnimOffset;
						verticesZCoordinate[k5] -= zAnimOffset;

						double x_ = xOffset - Math.floor(xOffset);
						double y_ = yOffset - Math.floor(yOffset);
						double z_ = zOffset - Math.floor(zOffset);
						xOffset = (int) xOffset & 0xff;
						yOffset = (int) yOffset & 0xff;
						zOffset = (int) zOffset & 0xff;
						xOffset += x_;
						yOffset += y_;
						zOffset += z_;
						int k6 = (int)((xOffset) * 8);
						int l6 = (int)((yOffset) * 8);
						int i7 = (int)((zOffset) * 8);
						if (i7 != 0) {
							int j7 = SINE[i7];
							int i8 = COSINE[i7];
							int l8 = verticesYCoordinate[k5] * j7 + verticesXCoordinate[k5] * i8 >> 16;
							verticesYCoordinate[k5] = verticesYCoordinate[k5] * i8 - verticesXCoordinate[k5] * j7 >> 16;
							verticesXCoordinate[k5] = l8;
						}
						if (k6 != 0) {
							int k7 = SINE[k6];
							int j8 = COSINE[k6];
							int i9 = verticesYCoordinate[k5] * j8 - verticesZCoordinate[k5] * k7 >> 16;
							verticesZCoordinate[k5] = verticesYCoordinate[k5] * k7 + verticesZCoordinate[k5] * j8 >> 16;
							verticesYCoordinate[k5] = i9;
						}
						if (l6 != 0) {
							int l7 = SINE[l6];
							int k8 = COSINE[l6];
							int j9 = verticesZCoordinate[k5] * l7 + verticesXCoordinate[k5] * k8 >> 16;
							verticesZCoordinate[k5] = verticesZCoordinate[k5] * k8 - verticesXCoordinate[k5] * l7 >> 16;
							verticesXCoordinate[k5] = j9;
						}
						verticesXCoordinate[k5] += xAnimOffset;
						verticesYCoordinate[k5] += yAnimOffset;
						verticesZCoordinate[k5] += zAnimOffset;
					}

				}
			}

			return;
		}*/
        if (animationType == 3) {
            for (int i2 = 0; i2 < i1; i2++) {
                int j3 = skin[i2];
                if (j3 < vertexSkin.length) {
                    int ai3[] = vertexSkin[j3];
                    for (int k4 = 0; k4 < ai3.length; k4++) {
                        int l5 = ai3[k4];
                        verticesX[l5] -= anInt1681;
                        verticesY[l5] -= anInt1682;
                        verticesZ[l5] -= anInt1683;
                        verticesX[l5] = (int) ((verticesX[l5] * xOffset) / 128);
                        verticesY[l5] = (int) ((verticesY[l5] * yOffset) / 128);
                        verticesZ[l5] = (int) ((verticesZ[l5] * zOffset) / 128);
                        verticesX[l5] += anInt1681;
                        verticesY[l5] += anInt1682;
                        verticesZ[l5] += anInt1683;
                    }

                }
            }

            return;
        }
        if (animationType == 5 && triangleSkin != null && face_alpha != null) {
            for (int j2 = 0; j2 < i1; j2++) {
                int k3 = skin[j2];
                if (k3 < triangleSkin.length) {
                    int ai4[] = triangleSkin[k3];
                    for (int l4 = 0; l4 < ai4.length; l4++) {
                        int i6 = ai4[l4];
                        face_alpha[i6] += xOffset * 8;
                        if (face_alpha[i6] < 0)
                            face_alpha[i6] = 0;
                        if (face_alpha[i6] > 255)
                            face_alpha[i6] = 255;
                    }

                }
            }

        }
    }

    public void method480(int i, int j, int k, int l, int i1) {
        for (int j1 = 0; j1 < numberOfTriangleFaces; j1++) {
            int k1 = face_a[j1];
            int i2 = face_b[j1];
            int j2 = face_c[j1];
            if (face_render_type == null) {
                int i3 = face_color[j1];
                VertexNormal class33 = super.vertexNormals[k1];
                int k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604)
                        / (j * class33.anInt605);
                face_shade_a[j1] = method481(i3, k2, 0);
                class33 = super.vertexNormals[i2];
                k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604) / (j * class33.anInt605);
                face_shade_b[j1] = method481(i3, k2, 0);
                class33 = super.vertexNormals[j2];
                k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604) / (j * class33.anInt605);
                face_shade_c[j1] = method481(i3, k2, 0);
            } else if ((face_render_type[j1] & 1) == 0) {
                int j3 = face_color[j1];
                int k3 = face_render_type[j1];
                VertexNormal class33_1 = super.vertexNormals[k1];
                int l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604)
                        / (j * class33_1.anInt605);
                face_shade_a[j1] = method481(j3, l2, k3);
                class33_1 = super.vertexNormals[i2];
                l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604)
                        / (j * class33_1.anInt605);
                face_shade_b[j1] = method481(j3, l2, k3);
                class33_1 = super.vertexNormals[j2];
                l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604)
                        / (j * class33_1.anInt605);
                face_shade_c[j1] = method481(j3, l2, k3);
            }
        }

        super.vertexNormals = null;
        vertexNormalOffset = null;
        vertexData = null;
        triangleData = null;
        if (face_render_type != null) {
            for (int l1 = 0; l1 < numberOfTriangleFaces; l1++)
                if ((face_render_type[l1] & 2) == 2)
                    return;

        }
        //face_color = null;
    }

    public void mirrorModel() {
        for (int j = 0; j < verticesCount; j++)
            verticesZ[j] = -verticesZ[j];
        for (int k = 0; k < numberOfTriangleFaces; k++) {
            int l = face_a[k];
            face_a[k] = face_c[k];
            face_c[k] = l;
        }
    }

    public void normalise() {
        super.modelBaseY = 0;
        maxY = 0;
        for (int i = 0; i < verticesCount; i++) {
            int j = verticesY[i];
            if (-j > super.modelBaseY)
                super.modelBaseY = -j;
            if (j > maxY)
                maxY = j;
        }

        diagonal3DAboveOrigin = (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + super.modelBaseY * super.modelBaseY)
                + 0.98999999999999999D);
        diagonal3D = diagonal3DAboveOrigin
                + (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + maxY * maxY) + 0.98999999999999999D);
    }

    private final void rasterise(int i, int bufferOffset) {
        if (outOfReach[i]) {
            reduce(i, bufferOffset);
            return;
        }
        int j = face_a[i];
        int k = face_b[i];
        int l = face_c[i];
        Rasterizer.aBoolean1462 = hasAnEdgeToRestrict[i];
        if (face_alpha == null)
            Rasterizer.anInt1465 = 0;
        else
            Rasterizer.anInt1465 = (face_alpha)[i];
        int i1;
        if (face_render_type == null)
            i1 = 0;
        else
            i1 = face_render_type[i] & 3;
        if (i1 == 0) {
            Rasterizer.drawGouraudTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], face_shade_a[i],
                    face_shade_b[i], face_shade_c[i], projected_vertex_z[j], projected_vertex_z[k],
                    projected_vertex_z[l], bufferOffset);
            return;
        }
        if (i1 == 1) {
            Rasterizer.drawFlatTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], hsl2rgb[face_shade_a[i]],
                    projected_vertex_z[j], projected_vertex_z[k], projected_vertex_z[l], bufferOffset);
            return;
        }
        if (i1 == 2) {
            int j1 = face_render_type[i] >> 2;
            int l1 = textures_face_a[j1];
            int j2 = textures_face_b[j1];
            int l2 = textures_face_c[j1];
            Rasterizer.drawTexturedTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], face_shade_a[i],
                    face_shade_b[i], face_shade_c[i], camera_vertex_y[l1], camera_vertex_y[j2], camera_vertex_y[l2],
                    camera_vertex_x[l1], camera_vertex_x[j2], camera_vertex_x[l2], camera_vertex_z[l1],
                    camera_vertex_z[j2], camera_vertex_z[l2], face_color[i], projected_vertex_z[j],
                    projected_vertex_z[k], projected_vertex_z[l], bufferOffset);
            return;
        }
        if (i1 == 3) {
            int k1 = face_render_type[i] >> 2;
            int i2 = textures_face_a[k1];
            int k2 = textures_face_b[k1];
            int i3 = textures_face_c[k1];
            Rasterizer.drawTexturedTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], face_shade_a[i],
                    face_shade_a[i], face_shade_a[i], camera_vertex_y[i2], camera_vertex_y[k2], camera_vertex_y[i3],
                    camera_vertex_x[i2], camera_vertex_x[k2], camera_vertex_x[i3], camera_vertex_z[i2],
                    camera_vertex_z[k2], camera_vertex_z[i3], face_color[i], projected_vertex_z[j],
                    projected_vertex_z[k], projected_vertex_z[l], bufferOffset);
        }
    }

    public void read525Model(byte abyte0[], int modelID, DataType dataType) {
        Stream nc1 = new Stream(abyte0);
        Stream nc2 = new Stream(abyte0);
        Stream nc3 = new Stream(abyte0);
        Stream nc4 = new Stream(abyte0);
        Stream nc5 = new Stream(abyte0);
        Stream nc6 = new Stream(abyte0);
        Stream nc7 = new Stream(abyte0);
        nc1.currentOffset = abyte0.length - 23;
        int numVertices = nc1.readUnsignedWord();
        int numTriangles = nc1.readUnsignedWord();
        int numTexTriangles = nc1.readUnsignedByte();
        ModelHeader ModelDef_1;
        if (dataType == DataType.OLDSCHOOL) {
            ModelDef_1 = modelHeaderOldschool[modelID] = new ModelHeader();
        } else {
            ModelDef_1 = modelHeader[modelID] = new ModelHeader();
        }
        ModelDef_1.modelData = abyte0;
        ModelDef_1.verticeCount = numVertices;
        ModelDef_1.triangleCount = numTriangles;
        ModelDef_1.texturedTriangleCount = numTexTriangles;
        int l1 = nc1.readUnsignedByte();
        boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
        boolean bool_78_ = (l1 & 0x2 ^ 0xffffffff) == -3;
        int i2 = nc1.readUnsignedByte();
        int j2 = nc1.readUnsignedByte();
        int k2 = nc1.readUnsignedByte();
        int l2 = nc1.readUnsignedByte();
        int i3 = nc1.readUnsignedByte();
        int j3 = nc1.readUnsignedWord();
        int k3 = nc1.readUnsignedWord();
        int l3 = nc1.readUnsignedWord();
        int i4 = nc1.readUnsignedWord();
        int j4 = nc1.readUnsignedWord();
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        int v = 0;
        int hb = 0;
        int P = 0;
        byte G = 0;
        byte[] x = null;
        byte[] O = null;
        byte[] J = null;
        byte[] F = null;
        byte[] cb = null;
        byte[] gb = null;
        byte[] lb = null;
        int[] ab = null;
        int[] kb = null;
        int[] y = null;
        int[] N = null;
        short[] D = null;
        int[] triangleColours2 = new int[numTriangles];
        if (numTexTriangles > 0) {
            O = new byte[numTexTriangles];
            nc1.currentOffset = 0;
            for (int j5 = 0; j5 < numTexTriangles; j5++) {
                byte byte0 = O[j5] = nc1.readSignedByte();
                if (byte0 == 0)
                    k4++;
                if (byte0 >= 1 && byte0 <= 3)
                    l4++;
                if (byte0 == 2)
                    i5++;
            }
        }
        int k5 = numTexTriangles;
        int l5 = k5;
        k5 += numVertices;
        int i6 = k5;
        if (l1 == 1)
            k5 += numTriangles;
        int j6 = k5;
        k5 += numTriangles;
        int k6 = k5;
        if (i2 == 255)
            k5 += numTriangles;
        int l6 = k5;
        if (k2 == 1)
            k5 += numTriangles;
        int i7 = k5;
        if (i3 == 1)
            k5 += numVertices;
        int j7 = k5;
        if (j2 == 1)
            k5 += numTriangles;
        int k7 = k5;
        k5 += i4;
        int l7 = k5;
        if (l2 == 1)
            k5 += numTriangles * 2;
        int i8 = k5;
        k5 += j4;
        int j8 = k5;
        k5 += numTriangles * 2;
        int k8 = k5;
        k5 += j3;
        int l8 = k5;
        k5 += k3;
        int i9 = k5;
        k5 += l3;
        int j9 = k5;
        k5 += k4 * 6;
        int k9 = k5;
        k5 += l4 * 6;
        int l9 = k5;
        k5 += l4 * 6;
        int i10 = k5;
        k5 += l4;
        int j10 = k5;
        k5 += l4;
        int k10 = k5;
        k5 += l4 + i5 * 2;
        v = numVertices;
        hb = numTriangles;
        P = numTexTriangles;
        this.verticesParticle = new int[numVertices];
        int[] vertexX = new int[numVertices];
        int[] vertexY = new int[numVertices];
        int[] vertexZ = new int[numVertices];
        int[] facePoint1 = new int[numTriangles];
        int[] facePoint2 = new int[numTriangles];
        int[] facePoint3 = new int[numTriangles];
        vertexData = new int[numVertices];
        face_render_type = new int[numTriangles];
        face_render_priorities = new int[numTriangles];
        face_alpha = new int[numTriangles];
        triangleData = new int[numTriangles];
        if (i3 == 1)
            vertexData = new int[numVertices];
        if (bool)
            face_render_type = new int[numTriangles];
        if (i2 == 255)
            face_render_priorities = new int[numTriangles];
        else
            G = (byte) i2;
        if (j2 == 1)
            face_alpha = new int[numTriangles];
        if (k2 == 1)
            triangleData = new int[numTriangles];
        if (l2 == 1)
            D = new short[numTriangles];
        if (l2 == 1 && numTexTriangles > 0)
            x = new byte[numTriangles];
        triangleColours2 = new int[numTriangles];
        int i_115_ = k5;
        int[] texTrianglesPoint1 = null;
        int[] texTrianglesPoint2 = null;
        int[] texTrianglesPoint3 = null;
        if (numTexTriangles > 0) {
            texTrianglesPoint1 = new int[numTexTriangles];
            texTrianglesPoint2 = new int[numTexTriangles];
            texTrianglesPoint3 = new int[numTexTriangles];
            if (l4 > 0) {
                kb = new int[l4];
                N = new int[l4];
                y = new int[l4];
                gb = new byte[l4];
                lb = new byte[l4];
                F = new byte[l4];
            }
            if (i5 > 0) {
                cb = new byte[i5];
                J = new byte[i5];
            }
        }
        nc1.currentOffset = l5;
        nc2.currentOffset = k8;
        nc3.currentOffset = l8;
        nc4.currentOffset = i9;
        nc5.currentOffset = i7;
        int l10 = 0;
        int i11 = 0;
        int j11 = 0;
        for (int k11 = 0; k11 < numVertices; k11++) {
            int l11 = nc1.readUnsignedByte();
            int j12 = 0;
            if ((l11 & 1) != 0)
                j12 = nc2.method421();
            int l12 = 0;
            if ((l11 & 2) != 0)
                l12 = nc3.method421();
            int j13 = 0;
            if ((l11 & 4) != 0)
                j13 = nc4.method421();
            vertexX[k11] = l10 + j12;
            vertexY[k11] = i11 + l12;
            vertexZ[k11] = j11 + j13;
            l10 = vertexX[k11];
            i11 = vertexY[k11];
            j11 = vertexZ[k11];
            if (vertexData != null)
                vertexData[k11] = nc5.readUnsignedByte();
        }
        nc1.currentOffset = j8;
        nc2.currentOffset = i6;
        nc3.currentOffset = k6;
        nc4.currentOffset = j7;
        nc5.currentOffset = l6;
        nc6.currentOffset = l7;
        nc7.currentOffset = i8;
        for (int i12 = 0; i12 < numTriangles; i12++) {
            triangleColours2[i12] = nc1.readUnsignedWord();
            if (l1 == 1) {
                face_render_type[i12] = nc2.readSignedByte();
                if (face_render_type[i12] == 2)
                    triangleColours2[i12] = 65535;
                face_render_type[i12] = 0;
            }
            if (i2 == 255) {
                face_render_priorities[i12] = nc3.readSignedByte();
            }
            if (j2 == 1) {
                face_alpha[i12] = nc4.readSignedByte();
                if (face_alpha[i12] < 0)
                    face_alpha[i12] = (256 + face_alpha[i12]);
            }
            if (k2 == 1)
                triangleData[i12] = nc5.readUnsignedByte();
            if (l2 == 1)
                D[i12] = (short) (nc6.readUnsignedWord() - 1);
            if (x != null)
                if (D[i12] != -1)
                    x[i12] = (byte) (nc7.readUnsignedByte() - 1);
                else
                    x[i12] = -1;
        }
        nc1.currentOffset = k7;
        nc2.currentOffset = j6;
        int k12 = 0;
        int i13 = 0;
        int k13 = 0;
        int l13 = 0;
        for (int i14 = 0; i14 < numTriangles; i14++) {
            int j14 = nc2.readUnsignedByte();
            if (j14 == 1) {
                k12 = nc1.method421() + l13;
                l13 = k12;
                i13 = nc1.method421() + l13;
                l13 = i13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 2) {
                i13 = k13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 3) {
                k12 = k13;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 4) {
                int l14 = k12;
                k12 = i13;
                i13 = l14;
                k13 = nc1.method421() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
        }
        nc1.currentOffset = j9;
        nc2.currentOffset = k9;
        nc3.currentOffset = l9;
        nc4.currentOffset = i10;
        nc5.currentOffset = j10;
        nc6.currentOffset = k10;
        for (int k14 = 0; k14 < numTexTriangles; k14++) {
            int i15 = O[k14] & 0xff;
            if (i15 == 0) {
                texTrianglesPoint1[k14] = nc1.readUnsignedWord();
                texTrianglesPoint2[k14] = nc1.readUnsignedWord();
                texTrianglesPoint3[k14] = nc1.readUnsignedWord();
            }
            if (i15 == 1) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                kb[k14] = nc3.readUnsignedWord();
                N[k14] = nc3.readUnsignedWord();
                y[k14] = nc3.readUnsignedWord();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
            if (i15 == 2) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                kb[k14] = nc3.readUnsignedWord();
                N[k14] = nc3.readUnsignedWord();
                y[k14] = nc3.readUnsignedWord();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
                cb[k14] = nc6.readSignedByte();
                J[k14] = nc6.readSignedByte();
            }
            if (i15 == 3) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                kb[k14] = nc3.readUnsignedWord();
                N[k14] = nc3.readUnsignedWord();
                y[k14] = nc3.readUnsignedWord();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
        }
        if (i2 != 255) {
            for (int i12 = 0; i12 < numTriangles; i12++)
                face_render_priorities[i12] = i2;
        }
        face_color = triangleColours2;
        verticesCount = numVertices;
        numberOfTriangleFaces = numTriangles;
        verticesX = vertexX;
        verticesY = vertexY;
        verticesZ = vertexZ;
        face_a = facePoint1;
        face_b = facePoint2;
        face_c = facePoint3;
        //	filterTriangles();
        convertTexturesTo317(modelID, D, texTrianglesPoint1, texTrianglesPoint2, texTrianglesPoint3, x, dataType);
    }

    public void read622Model(byte abyte0[], int modelID, DataType dataType) {
        Stream nc1 = new Stream(abyte0);
        Stream nc2 = new Stream(abyte0);
        Stream nc3 = new Stream(abyte0);
        Stream nc4 = new Stream(abyte0);
        Stream nc5 = new Stream(abyte0);
        Stream nc6 = new Stream(abyte0);
        Stream nc7 = new Stream(abyte0);
        nc1.currentOffset = abyte0.length - 23;
        int numVertices = nc1.readUnsignedWord();
        int numTriangles = nc1.readUnsignedWord();
        int numTexTriangles = nc1.readUnsignedByte();
        ModelHeader ModelDef_1;

        if (dataType == DataType.OLDSCHOOL) {
            ModelDef_1 = modelHeaderOldschool[modelID] = new ModelHeader();
        } else {
            ModelDef_1 = modelHeader[modelID] = new ModelHeader();
        }
        ModelDef_1.modelData = abyte0;
        ModelDef_1.verticeCount = numVertices;
        ModelDef_1.triangleCount = numTriangles;
        ModelDef_1.texturedTriangleCount = numTexTriangles;
        int l1 = nc1.readUnsignedByte();
        boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
        boolean bool_78_ = (l1 & 0x2 ^ 0xffffffff) == -3;
        boolean bool_25_ = (0x4 & l1) == 4;
        boolean bool_26_ = (0x8 & l1) == 8;
        if (!bool_26_) {
            read525Model(abyte0, modelID, dataType);
            return;
        }
        int newformat = 0;
        if (bool_26_) {
            nc1.currentOffset -= 7;
            newformat = nc1.readUnsignedByte();
            nc1.currentOffset += 6;
        }
        if (newformat == 15)
            newmodel[modelID] = true;
        int i2 = nc1.readUnsignedByte();
        int j2 = nc1.readUnsignedByte();
        int k2 = nc1.readUnsignedByte();
        int l2 = nc1.readUnsignedByte();
        int i3 = nc1.readUnsignedByte();
        int j3 = nc1.readUnsignedWord();
        int k3 = nc1.readUnsignedWord();
        int l3 = nc1.readUnsignedWord();
        int i4 = nc1.readUnsignedWord();
        int j4 = nc1.readUnsignedWord();
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        int v = 0;
        int hb = 0;
        int P = 0;
        byte G = 0;
        byte[] x = null;
        byte[] O = null;
        byte[] J = null;
        byte[] F = null;
        byte[] cb = null;
        byte[] gb = null;
        byte[] lb = null;
        int[] ab = null;
        int[] kb = null;
        int[] y = null;
        int[] N = null;
        short[] D = null;
        int[] triangleColours2 = new int[numTriangles];
        if (numTexTriangles > 0) {
            O = new byte[numTexTriangles];
            nc1.currentOffset = 0;
            for (int j5 = 0; j5 < numTexTriangles; j5++) {
                byte byte0 = O[j5] = nc1.readSignedByte();
                if (byte0 == 0)
                    k4++;
                if (byte0 >= 1 && byte0 <= 3)
                    l4++;
                if (byte0 == 2)
                    i5++;
            }
        }
        int k5 = numTexTriangles;
        int l5 = k5;
        k5 += numVertices;
        int i6 = k5;
        if (bool)
            k5 += numTriangles;
        if (l1 == 1)
            k5 += numTriangles;
        int j6 = k5;
        k5 += numTriangles;
        int k6 = k5;
        if (i2 == 255)
            k5 += numTriangles;
        int l6 = k5;
        if (k2 == 1)
            k5 += numTriangles;
        int i7 = k5;
        if (i3 == 1)
            k5 += numVertices;
        int j7 = k5;
        if (j2 == 1)
            k5 += numTriangles;
        int k7 = k5;
        k5 += i4;
        int l7 = k5;
        if (l2 == 1)
            k5 += numTriangles * 2;
        int i8 = k5;
        k5 += j4;
        int j8 = k5;
        k5 += numTriangles * 2;
        int k8 = k5;
        k5 += j3;
        int l8 = k5;
        k5 += k3;
        int i9 = k5;
        k5 += l3;
        int j9 = k5;
        k5 += k4 * 6;
        int k9 = k5;
        k5 += l4 * 6;
        int i_59_ = 6;
        if (newformat != 14) {
            if (newformat >= 15)
                i_59_ = 9;
        } else
            i_59_ = 7;
        int l9 = k5;
        k5 += i_59_ * l4;
        int i10 = k5;
        k5 += l4;
        int j10 = k5;
        k5 += l4;
        int k10 = k5;
        k5 += l4 + i5 * 2;
        v = numVertices;
        hb = numTriangles;
        P = numTexTriangles;
        this.verticesParticle = new int[numVertices];
        int[] vertexX = new int[numVertices];
        int[] vertexY = new int[numVertices];
        int[] vertexZ = new int[numVertices];
        int[] facePoint1 = new int[numTriangles];
        int[] facePoint2 = new int[numTriangles];
        int[] facePoint3 = new int[numTriangles];
        vertexData = new int[numVertices];
        face_render_type = new int[numTriangles];
        face_render_priorities = new int[numTriangles];
        face_alpha = new int[numTriangles];
        triangleData = new int[numTriangles];
        if (i3 == 1)
            vertexData = new int[numVertices];
        if (bool)
            face_render_type = new int[numTriangles];
        if (i2 == 255)
            face_render_priorities = new int[numTriangles];
        else
            G = (byte) i2;
        if (j2 == 1)
            face_alpha = new int[numTriangles];
        if (k2 == 1)
            triangleData = new int[numTriangles];
        if (l2 == 1)
            D = new short[numTriangles];
        if (l2 == 1 && numTexTriangles > 0)
            x = new byte[numTriangles];
        triangleColours2 = new int[numTriangles];
        int i_115_ = k5;
        int[] texTrianglesPoint1 = null;
        int[] texTrianglesPoint2 = null;
        int[] texTrianglesPoint3 = null;
        if (numTexTriangles > 0) {
            texTrianglesPoint1 = new int[numTexTriangles];
            texTrianglesPoint2 = new int[numTexTriangles];
            texTrianglesPoint3 = new int[numTexTriangles];
            if (l4 > 0) {
                kb = new int[l4];
                N = new int[l4];
                y = new int[l4];
                gb = new byte[l4];
                lb = new byte[l4];
                F = new byte[l4];
            }
            if (i5 > 0) {
                cb = new byte[i5];
                J = new byte[i5];
            }
        }
        nc1.currentOffset = l5;
        nc2.currentOffset = k8;
        nc3.currentOffset = l8;
        nc4.currentOffset = i9;
        nc5.currentOffset = i7;
        int l10 = 0;
        int i11 = 0;
        int j11 = 0;
        for (int k11 = 0; k11 < numVertices; k11++) {
            int l11 = nc1.readUnsignedByte();
            int j12 = 0;
            if ((l11 & 1) != 0)
                j12 = nc2.method421();
            int l12 = 0;
            if ((l11 & 2) != 0)
                l12 = nc3.method421();
            int j13 = 0;
            if ((l11 & 4) != 0)
                j13 = nc4.method421();
            vertexX[k11] = l10 + j12;
            vertexY[k11] = i11 + l12;
            vertexZ[k11] = j11 + j13;
            l10 = vertexX[k11];
            i11 = vertexY[k11];
            j11 = vertexZ[k11];
            if (vertexData != null)
                vertexData[k11] = nc5.readUnsignedByte();
        }
        nc1.currentOffset = j8;
        nc2.currentOffset = i6;
        nc3.currentOffset = k6;
        nc4.currentOffset = j7;
        nc5.currentOffset = l6;
        nc6.currentOffset = l7;
        nc7.currentOffset = i8;
        for (int i12 = 0; i12 < numTriangles; i12++) {
            triangleColours2[i12] = nc1.readUnsignedWord();
            if (l1 == 1) {
                face_render_type[i12] = nc2.readSignedByte();
                if (face_render_type[i12] == 2)
                    triangleColours2[i12] = 65535;
                face_render_type[i12] = 0;
            }
            if (i2 == 255) {
                face_render_priorities[i12] = nc3.readSignedByte();
            }
            if (j2 == 1) {
                face_alpha[i12] = nc4.readSignedByte();
                if (face_alpha[i12] < 0)
                    face_alpha[i12] = (256 + face_alpha[i12]);
            }
            if (k2 == 1)
                triangleData[i12] = nc5.readUnsignedByte();
            if (l2 == 1)
                D[i12] = (short) (nc6.readUnsignedWord() - 1);
            if (x != null)
                if (D[i12] != -1)
                    x[i12] = (byte) (nc7.readUnsignedByte() - 1);
                else
                    x[i12] = -1;
        }
        nc1.currentOffset = k7;
        nc2.currentOffset = j6;
        int point1 = 0;
        int point2 = 0;
        int point3 = 0;
        int offset = 0;
        for (int i14 = 0; i14 < numTriangles; i14++) {
            int triangleType = nc2.readUnsignedByte();
            if (triangleType == 1) {
                point1 = nc1.method421() + offset;
                offset = point1;
                point2 = nc1.method421() + offset;
                offset = point2;
                point3 = nc1.method421() + offset;
                offset = point3;
                facePoint1[i14] = point1;
                facePoint2[i14] = point2;
                facePoint3[i14] = point3;
            }
            if (triangleType == 2) {
                point2 = point3;
                point3 = nc1.method421() + offset;
                offset = point3;
                facePoint1[i14] = point1;
                facePoint2[i14] = point2;
                facePoint3[i14] = point3;
            }
            if (triangleType == 3) {
                point1 = point3;
                point3 = nc1.method421() + offset;
                offset = point3;
                facePoint1[i14] = point1;
                facePoint2[i14] = point2;
                facePoint3[i14] = point3;
            }
            if (triangleType == 4) {
                int pointOffset = point1;
                point1 = point2;
                point2 = pointOffset;
                point3 = nc1.method421() + offset;
                offset = point3;
                facePoint1[i14] = point1;
                facePoint2[i14] = point2;
                facePoint3[i14] = point3;
            }
        }
        nc1.currentOffset = j9;
        nc2.currentOffset = k9;
        nc3.currentOffset = l9;
        nc4.currentOffset = i10;
        nc5.currentOffset = j10;
        nc6.currentOffset = k10;
        for (int k14 = 0; k14 < numTexTriangles; k14++) {
            int i15 = O[k14] & 0xff;
            if (i15 == 0) {
                texTrianglesPoint1[k14] = nc1.readUnsignedWord();
                texTrianglesPoint2[k14] = nc1.readUnsignedWord();
                texTrianglesPoint3[k14] = nc1.readUnsignedWord();
            }
            if (i15 == 1) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                if (newformat < 15) {
                    kb[k14] = nc3.readUnsignedWord();
                    if (newformat >= 14)
                        N[k14] = nc3.v(-1);
                    else
                        N[k14] = nc3.readUnsignedWord();
                    y[k14] = nc3.readUnsignedWord();
                } else {
                    kb[k14] = nc3.v(-1);
                    N[k14] = nc3.v(-1);
                    y[k14] = nc3.v(-1);
                }
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
            if (i15 == 2) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                if (newformat >= 15) {
                    kb[k14] = nc3.v(-1);
                    N[k14] = nc3.v(-1);
                    y[k14] = nc3.v(-1);
                } else {
                    kb[k14] = nc3.readUnsignedWord();
                    if (newformat < 14)
                        N[k14] = nc3.readUnsignedWord();
                    else
                        N[k14] = nc3.v(-1);
                    y[k14] = nc3.readUnsignedWord();
                }
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
                cb[k14] = nc6.readSignedByte();
                J[k14] = nc6.readSignedByte();
            }
            if (i15 == 3) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                if (newformat < 15) {
                    kb[k14] = nc3.readUnsignedWord();
                    if (newformat < 14)
                        N[k14] = nc3.readUnsignedWord();
                    else
                        N[k14] = nc3.v(-1);
                    y[k14] = nc3.readUnsignedWord();
                } else {
                    kb[k14] = nc3.v(-1);
                    N[k14] = nc3.v(-1);
                    y[k14] = nc3.v(-1);
                }
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
        }
        if (i2 != 255) {
            for (int i12 = 0; i12 < numTriangles; i12++)
                face_render_priorities[i12] = i2;
        }
        face_color = triangleColours2;
        verticesCount = numVertices;
        numberOfTriangleFaces = numTriangles;
        verticesX = vertexX;
        verticesY = vertexY;
        verticesZ = vertexZ;
        face_a = facePoint1;
        face_b = facePoint2;
        face_c = facePoint3;
        //filterTriangles();
        convertTexturesTo317(modelID, D, texTrianglesPoint1, texTrianglesPoint2, texTrianglesPoint3, x, dataType);
    }

    private void readOldModel(int i, DataType dataType) {
        int j = -870;
        aBoolean1618 = true;
        rendersWithinOneTile = false;
        anInt1620++;
        ModelHeader header;
        if (dataType == DataType.OLDSCHOOL) {
            header = modelHeaderOldschool[i];
        } else {
            header = modelHeader[i];
        }
        verticesCount = header.verticeCount;
        numberOfTriangleFaces = header.triangleCount;
        numberOfTexturesFaces = header.texturedTriangleCount;
        this.verticesParticle = new int[verticesCount];
        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        face_a = new int[numberOfTriangleFaces];
        face_b = new int[numberOfTriangleFaces];
        while (j >= 0)
            aBoolean1618 = !aBoolean1618;
        face_c = new int[numberOfTriangleFaces];
        textures_face_a = new int[numberOfTexturesFaces];
        textures_face_b = new int[numberOfTexturesFaces];
        textures_face_c = new int[numberOfTexturesFaces];
        if (header.vskinBasePos >= 0)
            vertexData = new int[verticesCount];
        if (header.drawTypeBasePos >= 0)
            face_render_type = new int[numberOfTriangleFaces];
        if (header.facePriorityBasePos >= 0)
            face_render_priorities = new int[numberOfTriangleFaces];
        else
            face_priority = -header.facePriorityBasePos - 1;
        if (header.alphaBasepos >= 0)
            face_alpha = new int[numberOfTriangleFaces];
        if (header.tskinBasepos >= 0)
            triangleData = new int[numberOfTriangleFaces];
        face_color = new int[numberOfTriangleFaces];
        Stream stream = new Stream(header.modelData);
        stream.currentOffset = header.verticesModOffset;
        Stream stream_1 = new Stream(header.modelData);
        stream_1.currentOffset = header.verticesXOffset;
        Stream stream_2 = new Stream(header.modelData);
        stream_2.currentOffset = header.verticesYOffset;
        Stream stream_3 = new Stream(header.modelData);
        stream_3.currentOffset = header.verticesZOffset;
        Stream stream_4 = new Stream(header.modelData);
        stream_4.currentOffset = header.vskinBasePos;
        int k = 0;
        int l = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < verticesCount; j1++) {
            int k1 = stream.readUnsignedByte();
            int i2 = 0;
            if ((k1 & 1) != 0)
                i2 = stream_1.method421();
            int k2 = 0;
            if ((k1 & 2) != 0)
                k2 = stream_2.method421();
            int i3 = 0;
            if ((k1 & 4) != 0)
                i3 = stream_3.method421();
            verticesX[j1] = k + i2;
            verticesY[j1] = l + k2;
            verticesZ[j1] = i1 + i3;
            k = verticesX[j1];
            l = verticesY[j1];
            i1 = verticesZ[j1];
            if (vertexData != null)
                vertexData[j1] = stream_4.readUnsignedByte();
        }
        stream.currentOffset = header.triColourOffset;
        stream_1.currentOffset = header.drawTypeBasePos;
        stream_2.currentOffset = header.facePriorityBasePos;
        stream_3.currentOffset = header.alphaBasepos;
        stream_4.currentOffset = header.tskinBasepos;
        for (int l1 = 0; l1 < numberOfTriangleFaces; l1++) {
            face_color[l1] = stream.readUnsignedWord();
            if (face_render_type != null)
                face_render_type[l1] = stream_1.readUnsignedByte();
            if (face_render_priorities != null)
                face_render_priorities[l1] = stream_2.readUnsignedByte();
            if (face_alpha != null) {
                face_alpha[l1] = stream_3.readUnsignedByte();
            }
            if (triangleData != null)
                triangleData[l1] = stream_4.readUnsignedByte();
        }
        stream.currentOffset = header.triVPointOffset;
        stream_1.currentOffset = header.triMeshLinkOffset;
        int j2 = 0;
        int l2 = 0;
        int j3 = 0;
        int k3 = 0;
        for (int l3 = 0; l3 < numberOfTriangleFaces; l3++) {
            int i4 = stream_1.readUnsignedByte();
            if (i4 == 1) {
                j2 = stream.method421() + k3;
                k3 = j2;
                l2 = stream.method421() + k3;
                k3 = l2;
                j3 = stream.method421() + k3;
                k3 = j3;
                face_a[l3] = j2;
                face_b[l3] = l2;
                face_c[l3] = j3;
            }
            if (i4 == 2) {
                j2 = j2;
                l2 = j3;
                j3 = stream.method421() + k3;
                k3 = j3;
                face_a[l3] = j2;
                face_b[l3] = l2;
                face_c[l3] = j3;
            }
            if (i4 == 3) {
                j2 = j3;
                l2 = l2;
                j3 = stream.method421() + k3;
                k3 = j3;
                face_a[l3] = j2;
                face_b[l3] = l2;
                face_c[l3] = j3;
            }
            if (i4 == 4) {
                int k4 = j2;
                j2 = l2;
                l2 = k4;
                j3 = stream.method421() + k3;
                k3 = j3;
                face_a[l3] = j2;
                face_b[l3] = l2;
                face_c[l3] = j3;
            }
        }
        stream.currentOffset = header.textureInfoBasePos;
        for (int j4 = 0; j4 < numberOfTexturesFaces; j4++) {
            textures_face_a[j4] = stream.readUnsignedWord();
            textures_face_b[j4] = stream.readUnsignedWord();
            textures_face_c[j4] = stream.readUnsignedWord();
        }
        // filterTriangles();
    }


    public void convertTexturesTo317(int modelId, short[] textureIds, int[] texa, int[] texb, int[] texc, byte[] texture_coordinates, DataType dataType) {
        if (textureIds == null)
            return;
        int set = 0;
        int set2 = 0;
        boolean osrs = modelId == 91288;
        int max = osrs ? 60 : 50;

        if (dataType == DataType.OLDSCHOOL) {
            max = 60;
        }

        textures_face_a = new int[numberOfTriangleFaces];
        textures_face_b = new int[numberOfTriangleFaces];
        textures_face_c = new int[numberOfTriangleFaces];
        for (int i = 0; i < numberOfTriangleFaces; i++) {
            if (textureIds[i] == -1 && face_render_type[i] == 2) {
                face_color[i] = 65535;
                face_render_type[i] = 0;
            }

            if (textureIds[i] >= max || textureIds[i] < 0 || textureIds[i] == 39) {
                face_render_type[i] = 0;
                continue;
            }

            face_render_type[i] = 2 + set2;
            set2 += 4;
            int a = face_a[i];
            int b = face_b[i];
            int c = face_c[i];
            face_color[i] = textureIds[i];
            int texture_type = -1;

            if (texture_coordinates != null) {
                texture_type = texture_coordinates[i] & 0xff;
                if (texture_type != 0xff) {
                    if (texa[texture_type] >= camera_vertex_x.length || texb[texture_type] >= camera_vertex_y.length || texc[texture_type] >= camera_vertex_z.length) {
                        texture_type = -1;
                    }
                }
            }

            if (texture_type == 0xff) {
                texture_type = -1;
            }

            textures_face_a[set] = texture_type == -1 ? a : texa[texture_type];
            textures_face_b[set] = texture_type == -1 ? b : texb[texture_type];
            textures_face_c[set++] = texture_type == -1 ? c : texc[texture_type];
        }
        numberOfTexturesFaces = set;
    }

    public void forceRecolour(int i, int j) {
        for (int k = 0; k < numberOfTriangleFaces; k++)
            face_color[k] = j;
    }

    public void recolour(int itemId, int editedColor, int originalColor) {
        for (int k = 0; k < numberOfTriangleFaces; k++) {
            if (face_color[k] == editedColor) {
                face_color[k] = originalColor;
            }
        }
    }

    public void recolour(int i, int j) {
        for (int k = 0; k < numberOfTriangleFaces; k++)
            if (face_color[k] == i) {
                face_color[k] = j;
            }
    }

    private final void reduce(int i, int bufferOffset) {
        if (face_color != null)
            if (face_color[i] == 65535)
                return;

        int j = Rasterizer.textureInt1;
        int k = Rasterizer.textureInt2;
        int l = 0;
        int i1 = face_a[i];
        int j1 = face_b[i];
        int k1 = face_c[i];
        int l1 = camera_vertex_z[i1];
        int i2 = camera_vertex_z[j1];
        int j2 = camera_vertex_z[k1];

        if (l1 >= 50) {
            anIntArray1678[l] = projected_vertex_x[i1];
            anIntArray1679[l] = projected_vertex_y[i1];
            anIntArray1680[l++] = face_shade_a[i];
        } else {
            int k2 = camera_vertex_y[i1];
            int k3 = camera_vertex_x[i1];
            int k4 = face_shade_a[i];
            if (j2 >= 50) {
                int k5 = (50 - l1) * lightDecay[j2 - l1];
                anIntArray1678[l] = j + (k2 + ((camera_vertex_y[k1] - k2) * k5 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1679[l] = k + (k3 + ((camera_vertex_x[k1] - k3) * k5 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1680[l++] = k4 + ((face_shade_c[i] - k4) * k5 >> 16);
            }
            if (i2 >= 50) {
                int l5 = (50 - l1) * lightDecay[i2 - l1];
                anIntArray1678[l] = j + (k2 + ((camera_vertex_y[j1] - k2) * l5 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1679[l] = k + (k3 + ((camera_vertex_x[j1] - k3) * l5 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1680[l++] = k4 + ((face_shade_b[i] - k4) * l5 >> 16);
            }
        }
        if (i2 >= 50) {
            anIntArray1678[l] = projected_vertex_x[j1];
            anIntArray1679[l] = projected_vertex_y[j1];
            anIntArray1680[l++] = face_shade_b[i];
        } else {
            int l2 = camera_vertex_y[j1];
            int l3 = camera_vertex_x[j1];
            int l4 = face_shade_b[i];
            if (l1 >= 50) {
                int i6 = (50 - i2) * lightDecay[l1 - i2];
                anIntArray1678[l] = j + (l2 + ((camera_vertex_y[i1] - l2) * i6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1679[l] = k + (l3 + ((camera_vertex_x[i1] - l3) * i6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1680[l++] = l4 + ((face_shade_a[i] - l4) * i6 >> 16);
            }
            if (j2 >= 50) {
                int j6 = (50 - i2) * lightDecay[j2 - i2];
                anIntArray1678[l] = j + (l2 + ((camera_vertex_y[k1] - l2) * j6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1679[l] = k + (l3 + ((camera_vertex_x[k1] - l3) * j6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1680[l++] = l4 + ((face_shade_c[i] - l4) * j6 >> 16);
            }
        }
        if (j2 >= 50) {
            anIntArray1678[l] = projected_vertex_x[k1];
            anIntArray1679[l] = projected_vertex_y[k1];
            anIntArray1680[l++] = face_shade_c[i];
        } else {
            int i3 = camera_vertex_y[k1];
            int i4 = camera_vertex_x[k1];
            int i5 = face_shade_c[i];
            if (i2 >= 50) {
                int k6 = (50 - j2) * lightDecay[i2 - j2];
                anIntArray1678[l] = j + (i3 + ((camera_vertex_y[j1] - i3) * k6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1679[l] = k + (i4 + ((camera_vertex_x[j1] - i4) * k6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1680[l++] = i5 + ((face_shade_b[i] - i5) * k6 >> 16);
            }
            if (l1 >= 50) {
                int l6 = (50 - j2) * lightDecay[l1 - j2];
                anIntArray1678[l] = j + (i3 + ((camera_vertex_y[i1] - i3) * l6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1679[l] = k + (i4 + ((camera_vertex_x[i1] - i4) * l6 >> 16)) * Rasterizer.fieldOfView / 50;
                anIntArray1680[l++] = i5 + ((face_shade_a[i] - i5) * l6 >> 16);
            }
        }
        int j3 = anIntArray1678[0];
        int j4 = anIntArray1678[1];
        int j5 = anIntArray1678[2];
        int i7 = anIntArray1679[0];
        int j7 = anIntArray1679[1];
        int k7 = anIntArray1679[2];
        if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
            Rasterizer.aBoolean1462 = false;
            if (l == 3) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > DrawingArea.viewportRX || j4 > DrawingArea.viewportRX
                        || j5 > DrawingArea.viewportRX)
                    Rasterizer.aBoolean1462 = true;
                int l7;
                if (face_render_type == null)
                    l7 = 0;
                else
                    l7 = face_render_type[i] & 3;
                if (l7 == 0)
                    Rasterizer.drawGouraudTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], -1, -1, -1, bufferOffset);
                else if (l7 == 1)
                    Rasterizer.drawFlatTriangle(i7, j7, k7, j3, j4, j5, hsl2rgb[face_shade_a[i]], -1, -1, -1,
                            bufferOffset);
                else if (l7 == 2) {
                    int j8 = face_render_type[i] >> 2;
                    int k9 = textures_face_a[j8];
                    int k10 = textures_face_b[j8];
                    int k11 = textures_face_c[j8];
                    Rasterizer.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], camera_vertex_y[k9], camera_vertex_y[k10], camera_vertex_y[k11],
                            camera_vertex_x[k9], camera_vertex_x[k10], camera_vertex_x[k11], camera_vertex_z[k9],
                            camera_vertex_z[k10], camera_vertex_z[k11], face_color[i], -1, -1, -1, bufferOffset);
                } else if (l7 == 3) {
                    int k8 = face_render_type[i] >> 2;
                    int l9 = textures_face_a[k8];
                    int l10 = textures_face_b[k8];
                    int l11 = textures_face_c[k8];
                    Rasterizer.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, face_shade_a[i], face_shade_a[i],
                            face_shade_a[i], camera_vertex_y[l9], camera_vertex_y[l10], camera_vertex_y[l11],
                            camera_vertex_x[l9], camera_vertex_x[l10], camera_vertex_x[l11], camera_vertex_z[l9],
                            camera_vertex_z[l10], camera_vertex_z[l11], face_color[i], -1, -1, -1, bufferOffset);
                }
            }
            if (l == 4) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > DrawingArea.viewportRX || j4 > DrawingArea.viewportRX
                        || j5 > DrawingArea.viewportRX || anIntArray1678[3] < 0
                        || anIntArray1678[3] > DrawingArea.viewportRX)
                    Rasterizer.aBoolean1462 = true;
                int i8;
                if (face_render_type == null)
                    i8 = 0;
                else
                    i8 = face_render_type[i] & 3;
                if (i8 == 0) {
                    Rasterizer.drawGouraudTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], -1, -1, -1, bufferOffset);
                    Rasterizer.drawGouraudTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3],
                            anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], -1, -1, -1, bufferOffset);
                    return;
                }
                if (i8 == 1) {
                    int l8 = hsl2rgb[face_shade_a[i]];
                    Rasterizer.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8, -1, -1, -1, bufferOffset);
                    Rasterizer.drawFlatTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], l8, -1, -1, -1,
                            bufferOffset);
                    return;
                }
                if (i8 == 2) {
                    int i9 = face_render_type[i] >> 2;
                    int i10 = textures_face_a[i9];
                    int i11 = textures_face_b[i9];
                    int i12 = textures_face_c[i9];

                    Rasterizer.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
                            anIntArray1680[2], camera_vertex_y[i10], camera_vertex_y[i11], camera_vertex_y[i12],
                            camera_vertex_x[i10], camera_vertex_x[i11], camera_vertex_x[i12], camera_vertex_z[i10],
                            camera_vertex_z[i11], camera_vertex_z[i12], face_color[i], -1, -1, -1, bufferOffset);
                    Rasterizer.drawTexturedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3],
                            anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], camera_vertex_y[i10],
                            camera_vertex_y[i11], camera_vertex_y[i12], camera_vertex_x[i10], camera_vertex_x[i11],
                            camera_vertex_x[i12], camera_vertex_z[i10], camera_vertex_z[i11], camera_vertex_z[i12],
                            face_color[i], -1, -1, -1, bufferOffset);
                    return;
                }
                if (i8 == 3) {
                    int j9 = face_render_type[i] >> 2;
                    int j10 = textures_face_a[j9];
                    int j11 = textures_face_b[j9];
                    int j12 = textures_face_c[j9];
                    Rasterizer.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, face_shade_a[i], face_shade_a[i],
                            face_shade_a[i], camera_vertex_y[j10], camera_vertex_y[j11], camera_vertex_y[j12],
                            camera_vertex_x[j10], camera_vertex_x[j11], camera_vertex_x[j12], camera_vertex_z[j10],
                            camera_vertex_z[j11], camera_vertex_z[j12], face_color[i], -1, -1, -1, bufferOffset);
                    Rasterizer.drawTexturedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3],
                            face_shade_a[i], face_shade_a[i], face_shade_a[i], camera_vertex_y[j10],
                            camera_vertex_y[j11], camera_vertex_y[j12], camera_vertex_x[j10], camera_vertex_x[j11],
                            camera_vertex_x[j12], camera_vertex_z[j10], camera_vertex_z[j11], camera_vertex_z[j12],
                            face_color[i], -1, -1, -1, bufferOffset);
                }
            }
        }
    }

    @Override
    public void renderAtPoint(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int id, int bufferOffset) {
        renderAtPointX = j1 + Client.instance.xCameraPos;
        renderAtPointY = l1 + Client.instance.yCameraPos;
        renderAtPointZ = k1 + Client.instance.zCameraPos;
        lastRenderedRotation = i;
        int j2 = l1 * i1 - j1 * l >> 16;
        int k2 = k1 * j + j2 * k >> 16;
        int l2 = diagonal2DAboveOrigin * k >> 16;
        int i3 = k2 + l2;

        if (i3 <= 50 || k2 >= 3500)
            return;
        int j3 = l1 * l + j1 * i1 >> 16;
        int k3 = (j3 - diagonal2DAboveOrigin) * Rasterizer.fieldOfView;
        if (k3 / i3 >= DrawingArea.viewport_centerX)
            return;
        int l3 = (j3 + diagonal2DAboveOrigin) * Rasterizer.fieldOfView;
        if (l3 / i3 <= -DrawingArea.viewport_centerX)
            return;
        int i4 = k1 * k - j2 * j >> 16;
        int j4 = diagonal2DAboveOrigin * j >> 16;
        int k4 = (i4 + j4) * Rasterizer.fieldOfView;
        if (k4 / i3 <= -DrawingArea.viewport_centerY)
            return;
        int l4 = j4 + (super.modelBaseY * k >> 16);
        int i5 = (i4 - l4) * Rasterizer.fieldOfView;
        if (i5 / i3 >= DrawingArea.viewport_centerY)
            return;
        int j5 = l2 + (super.modelBaseY * j >> 16);
        boolean flag = false;
        if (k2 - j5 <= 50)
            flag = true;
        boolean flag1 = false;
        if (i2 > 0 && objectExists) {
            int k5 = k2 - l2;
            if (k5 <= 50)
                k5 = 50;
            if (j3 > 0) {
                k3 /= i3;
                l3 /= k5;
            } else {
                l3 /= i3;
                k3 /= k5;
            }
            if (i4 > 0) {
                i5 /= i3;
                k4 /= k5;
            } else {
                k4 /= i3;
                i5 /= k5;
            }
            int i6 = currentCursorX - Rasterizer.textureInt1;
            int k6 = currentCursorY - Rasterizer.textureInt2;
            if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4)
                if (rendersWithinOneTile) {
                    mapObjectIds[objectsRendered] = id;
                    objectsInCurrentRegion[objectsRendered++] = i2;
                } else
                    flag1 = true;
        }
        int l5 = Rasterizer.textureInt1;
        int j6 = Rasterizer.textureInt2;
        int l6 = 0;
        int i7 = 0;
        if (i != 0) {
            l6 = SINE[i];
            i7 = COSINE[i];
        }
        for (int j7 = 0; j7 < verticesCount; j7++) {
            int k7 = verticesX[j7];
            int l7 = verticesY[j7];
            int i8 = verticesZ[j7];
            if (i != 0) {
                int j8 = i8 * l6 + k7 * i7 >> 16;
                i8 = i8 * i7 - k7 * l6 >> 16;
                k7 = j8;
            }
            k7 += j1;
            l7 += k1;
            i8 += l1;
            int k8 = i8 * l + k7 * i1 >> 16;
            i8 = i8 * i1 - k7 * l >> 16;
            k7 = k8;
            k8 = l7 * k - i8 * j >> 16;
            i8 = l7 * j + i8 * k >> 16;
            l7 = k8;
            anIntArray1667[j7] = i8 - k2;
            if (i8 >= 50) {
                projected_vertex_x[j7] = l5 + k7 * Rasterizer.fieldOfView / i8;
                projected_vertex_y[j7] = j6 + l7 * Rasterizer.fieldOfView / i8;
                projected_vertex_z[j7] = i8;
            } else {
                projected_vertex_x[j7] = -5000;
                flag = true;
            }
            if (flag || numberOfTexturesFaces > 0) {
                camera_vertex_y[j7] = k7;
                camera_vertex_x[j7] = l7;
                camera_vertex_z[j7] = i8;
            }
        }

        try {
            translateToScreen(flag, flag1, i2, id, bufferOffset);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    public void renderSingle(int rotation_2, int offsetX, int rotation_1, int offsetY, int zoom_sine, int zoom_cosine) {
        try {
            int i = 0;
            int base_draw_x = Rasterizer.textureInt1;
            int base_draw_y = Rasterizer.textureInt2;
            int base_sine = SINE[i];
            int base_cosine = COSINE[i];
            int rot_2_sine = SINE[rotation_2];
            int rot_2_cosine = COSINE[rotation_2];
            int offsetX_sine = SINE[offsetX];
            int offsetX_cosine = COSINE[offsetX];
            int rot_1_sine = SINE[rotation_1];
            int rot_1_cosine = COSINE[rotation_1];
            int calculated_zoom = zoom_sine * rot_1_sine + zoom_cosine * rot_1_cosine >> 16;
            for (int vertexId = 0; vertexId < verticesCount; vertexId++) {
                int baseVertexX = verticesX[vertexId];
                int baseVertexY = verticesY[vertexId];
                int baseVertexZ = verticesZ[vertexId];
                if (offsetX != 0) {
                    int calculatedVertexX = baseVertexY * offsetX_sine + baseVertexX * offsetX_cosine >> 16;
                    baseVertexY = baseVertexY * offsetX_cosine - baseVertexX * offsetX_sine >> 16;
                    baseVertexX = calculatedVertexX;
                }
                if (i != 0) {
                    int calculatedVertexY = baseVertexY * base_cosine - baseVertexZ * base_sine >> 16;
                    baseVertexZ = baseVertexY * base_sine + baseVertexZ * base_cosine >> 16;
                    baseVertexY = calculatedVertexY;
                }
                if (rotation_2 != 0) {
                    int calculatedVertexZ = baseVertexZ * rot_2_sine + baseVertexX * rot_2_cosine >> 16;
                    baseVertexZ = baseVertexZ * rot_2_cosine - baseVertexX * rot_2_sine >> 16;
                    baseVertexX = calculatedVertexZ;
                }
                baseVertexX += offsetY;
                baseVertexY += zoom_sine;
                baseVertexZ += zoom_cosine;
                int j6 = baseVertexY * rot_1_cosine - baseVertexZ * rot_1_sine >> 16;
                baseVertexZ = baseVertexY * rot_1_sine + baseVertexZ * rot_1_cosine >> 16;
                baseVertexY = j6;
                anIntArray1667[vertexId] = baseVertexZ - calculated_zoom;
                projected_vertex_x[vertexId] = base_draw_x + (baseVertexX << 9) / baseVertexZ;
                projected_vertex_y[vertexId] = base_draw_y + (baseVertexY << 9) / baseVertexZ;
                projected_vertex_z[vertexId] = baseVertexZ;
                if (numberOfTexturesFaces > 0) {
                    camera_vertex_y[vertexId] = baseVertexX;
                    camera_vertex_x[vertexId] = baseVertexY;
                    camera_vertex_z[vertexId] = baseVertexZ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            translateToScreen(false, false, 0, -1, 20);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    public void reset() {
        verticesX = null;
        verticesY = null;
        verticesZ = null;
        face_a = null;
        face_b = null;
        face_c = null;
        face_shade_a = null;
        face_shade_b = null;
        face_shade_c = null;
        face_render_type = null;
        face_render_priorities = null;
        face_alpha = null;
        face_color = null;
        textures_face_a = textures_face_b = textures_face_c = null;
        vertexData = null;
        triangleData = null;
        vertexSkin = null;
        triangleSkin = null;
        vertexNormalOffset = null;
    }

    public void rotateBy90() {
        for (int j = 0; j < verticesCount; j++) {
            int k = verticesX[j];
            verticesX[j] = verticesZ[j];
            verticesZ[j] = -k;
        }
    }

    public void rotateX(int i) {
        int k = SINE[i];
        int l = COSINE[i];
        for (int i1 = 0; i1 < verticesCount; i1++) {
            int j1 = verticesY[i1] * l - verticesZ[i1] * k >> 16;
            verticesZ[i1] = verticesY[i1] * k + verticesZ[i1] * l >> 16;
            verticesY[i1] = j1;
        }
    }

    public void scale2(int i, int j, int k) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] = verticesX[i1] * i >> 7;
            verticesY[i1] = verticesY[i1] * j >> 7;
            verticesZ[i1] = verticesZ[i1] * k >> 7;
        }
    }

    public void scaleT(int i, int j, int l) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] = (verticesX[i1] * i) / 128;
            verticesY[i1] = (verticesY[i1] * l) / 128;
            verticesZ[i1] = (verticesZ[i1] * j) / 128;
        }

    }

    public void translate(int i, int j, int l) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] += i;
            verticesY[i1] += j;
            verticesZ[i1] += l;
        }
    }

    private final void translateToScreen(boolean flag, boolean needAddToSelectedObjects, int i, int id, int bufferOffset) {
        for (int j = 0; j < diagonal3D; j++)
            depthListIndices[j] = 0;

        // filterTriangles();
        for (int triangleId = 0; triangleId < numberOfTriangleFaces; triangleId++) {
            if (face_render_type != null && face_render_type[triangleId] == -1
                    || face_alpha != null && face_alpha[triangleId] >= 255)
                continue;
            int face_a_pos = face_a[triangleId];
            int face_b_pos = face_b[triangleId];
            int face_c_pos = face_c[triangleId];

            int vertexXA = projected_vertex_x[face_a_pos];
            int vertexXB = projected_vertex_x[face_b_pos];
            int vertexXC = projected_vertex_x[face_c_pos];
            if (flag && (vertexXA == -5000 || vertexXB == -5000 || vertexXC == -5000)) {
                outOfReach[triangleId] = true;
                int j5 = (anIntArray1667[face_a_pos] + anIntArray1667[face_b_pos] + anIntArray1667[face_c_pos]) / 3
                        + diagonal3DAboveOrigin;
                faceLists[j5][depthListIndices[j5]++] = triangleId;
                continue;
            }
            if (needAddToSelectedObjects && cursorOn(currentCursorX, currentCursorY, projected_vertex_y[face_a_pos],
                    projected_vertex_y[face_b_pos], projected_vertex_y[face_c_pos], vertexXA, vertexXB, vertexXC)) {
                mapObjectIds[objectsRendered] = id;
                objectsInCurrentRegion[objectsRendered++] = i;
                needAddToSelectedObjects = false;
            }
            if ((vertexXA - vertexXB) * (projected_vertex_y[face_c_pos] - projected_vertex_y[face_b_pos])
                    - (projected_vertex_y[face_a_pos] - projected_vertex_y[face_b_pos]) * (vertexXC - vertexXB) > 0) {
                outOfReach[triangleId] = false;
                hasAnEdgeToRestrict[triangleId] = vertexXA < 0 || vertexXB < 0 || vertexXC < 0
                        || vertexXA > DrawingArea.viewportRX || vertexXB > DrawingArea.viewportRX
                        || vertexXC > DrawingArea.viewportRX;
                int k5 = (anIntArray1667[face_a_pos] + anIntArray1667[face_b_pos] + anIntArray1667[face_c_pos]) / 3
                        + diagonal3DAboveOrigin;
                faceLists[k5][depthListIndices[k5]++] = triangleId;
            }
        }

        if (face_render_priorities == null) {
            for (int i1 = diagonal3D - 1; i1 >= 0; i1--) {
                int l1 = depthListIndices[i1];
                if (l1 > 0) {
                    int ai[] = faceLists[i1];
                    for (int j3 = 0; j3 < l1; j3++)
                        rasterise(ai[j3], bufferOffset);

                }
            }

            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            anIntArray1673[j1] = 0;
            anIntArray1677[j1] = 0;
        }

        for (int i2 = diagonal3D - 1; i2 >= 0; i2--) {
            int k2 = depthListIndices[i2];
            if (k2 > 0) {
                int ai1[] = faceLists[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    int l5 = face_render_priorities[l4];
                    int j6 = anIntArray1673[l5]++;
                    anIntArrayArray1674[l5][j6] = l4;
                    if (l5 < 10)
                        anIntArray1677[l5] += i2;
                    else if (l5 == 10)
                        anIntArray1675[j6] = i2;
                    else
                        anIntArray1676[j6] = i2;
                }

            }
        }

        int l2 = 0;
        if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0)
            l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
        int k3 = 0;
        if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0)
            k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
        int j4 = 0;
        if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0)
            j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);
        int i6 = 0;
        int k6 = anIntArray1673[10];
        int ai2[] = anIntArrayArray1674[10];
        int ai3[] = anIntArray1675;
        if (i6 == k6) {
            i6 = 0;
            k6 = anIntArray1673[11];
            ai2 = anIntArrayArray1674[11];
            ai3 = anIntArray1676;
        }
        int i5;
        if (i6 < k6)
            i5 = ai3[i6];
        else
            i5 = -1000;
        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                rasterise(ai2[i6++], bufferOffset);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 3 && i5 > k3) {
                rasterise(ai2[i6++], bufferOffset);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 5 && i5 > j4) {
                rasterise(ai2[i6++], bufferOffset);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            int i7 = anIntArray1673[l6];
            int ai4[] = anIntArrayArray1674[l6];
            for (int j7 = 0; j7 < i7; j7++)
                rasterise(ai4[j7], bufferOffset);

        }

        while (i5 != -1000) {
            rasterise(ai2[i6++], bufferOffset);
            if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                i6 = 0;
                ai2 = anIntArrayArray1674[11];
                k6 = anIntArray1673[11];
                ai3 = anIntArray1676;
            }
            if (i6 < k6)
                i5 = ai3[i6];
            else
                i5 = -500;
        }

        for (int vertex = 0; vertex < verticesCount; ++vertex) {
            int pid = verticesParticle[vertex] - 1;
            if (pid >= 0) {
                ParticleDefinition def = ParticleDefinition.cache[pid];
                int x = verticesX[vertex];
                int y = verticesY[vertex];
                int z = verticesX[vertex];
                int depth = projected_vertex_z[vertex];
                if (lastRenderedRotation != 0) {
                    int sine = SINE[lastRenderedRotation];
                    int cosine = COSINE[lastRenderedRotation];
                    int rotatedX = z * sine + x * cosine >> 16;
                    z = z * cosine - x * sine >> 16;
                    x = rotatedX;
                }
                x += renderAtPointX;
                z += renderAtPointY;

                ParticleVector pos = new ParticleVector(x, -y, z);
                for (int p = 0; p < def.getSpawnRate(); p++) {
                    Particle particle = new Particle(def, pos, depth, pid);
                    Client.instance.addParticle(particle);
                }
            }
        }
    }

    public int bufferOffset;
    public int uvBufferOffset;



    @Override
    public List<Vertex> getVertices() {
        int[] verticesX = getVerticesX();
        int[] verticesY = getVerticesY();
        int[] verticesZ = getVerticesZ();
        ArrayList<Vertex> vertices = new ArrayList<>(getVerticesCount());
        for (int i = 0; i < getVerticesCount(); i++) {
            Vertex vertex = new Vertex(verticesX[i], verticesY[i], verticesZ[i]);
            vertices.add(vertex);
        }
        return vertices;
    }

    @Override
    public List<Triangle> getTriangles() {
        int[] trianglesX = getFaceIndices1();
        int[] trianglesY = getFaceIndices2();
        int[] trianglesZ = getFaceIndices3();

        List<Vertex> vertices = getVertices();
        List<Triangle> triangles = new ArrayList<>(getFaceCount());

        for (int i = 0; i < getFaceCount(); ++i)
        {
            int triangleX = trianglesX[i];
            int triangleY = trianglesY[i];
            int triangleZ = trianglesZ[i];

            Triangle triangle = new Triangle(vertices.get(triangleX),vertices.get(triangleY),vertices.get(triangleZ));
            triangles.add(triangle);
        }

        return triangles;
    }

    @Override
    public int getVerticesCount() {
        return verticesCount;
    }

    @Override
    public int[] getVerticesX() {
        return verticesX;
    }

    @Override
    public int[] getVerticesY() {
        return verticesY;
    }

    @Override
    public int[] getVerticesZ() {
        return verticesZ;
    }

    @Override
    public int getFaceCount() {
        return this.numberOfTriangleFaces;
    }

    @Override
    public int[] getFaceIndices1() {
        return face_a;
    }

    @Override
    public int[] getFaceIndices2() {
        return face_b;
    }

    @Override
    public int[] getFaceIndices3() {
        return face_c;
    }

    @Override
    public int[] getFaceColors1() {
        return this.face_shade_a;
    }

    @Override
    public int[] getFaceColors2() {
        return face_shade_b;
    }

    @Override
    public int[] getFaceColors3() {
        return face_shade_c;
    }

    @Override
    public int[] getFaceTransparencies() {
        return face_alpha;
    }

    private int sceneId;

    @Override
    public int getSceneId() {
        return sceneId;
    }

    @Override
    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

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
        return uvBufferOffset;
    }

    @Override
    public void setUvBufferOffset(int uvBufferOffset) {
        this.uvBufferOffset = uvBufferOffset;
    }

    @Override
    public int getModelBaseY() {
        return modelBaseY;
    }

    @Override
    public void animate(int type, int[] list, int x, int y, int z) {

    }

    @Override
    public void calculateBoundsCylinder() {

    }

    @Override
    public byte[] getFaceRenderPriorities() {
        return null;
//TODO        return this.face_render_priorities;
    }

    @Override
    public int[][] getVertexGroups() {
        return new int[0][];
    }

    @Override
    public int getRadius() {
        return diagonal3DAboveOrigin;
    }

    @Override
    public int getDiameter() {
        return diagonal3D;
    }

    @Override
    public short[] getFaceTextures() {
//        return materials;
        return new short[0];
    }

    @Override
    public void calculateExtreme(int orientation) {
        calculateBoundingBox(orientation);
    }

    public void resetBounds() {
//        this.boundsType = 0;
        this.aabb.clear();
    }

    @Override
    public RSModel toSharedModel(boolean b) {
        return null;
    }

    @Override
    public RSModel toSharedSpotAnimModel(boolean b) {
        return null;
    }

    void invalidate() {
//        vertexNormals = null;
//        this.normals = null;
//        this.faceNormals = null;
//        this.isBoundsCalculated = false;
    }

    @Override
    public void rotateY90Ccw() {
        rotateBy90();
    }

    @Override
    public void rotateY180Ccw() {
        for (int vert = 0; vert < this.verticesCount; ++vert)
        {
            this.verticesX[vert] = -this.verticesX[vert];
            this.verticesZ[vert] = -this.verticesZ[vert];
        }

        this.resetBounds();
        invalidate();
    }

    @Override
    public void rotateY270Ccw() {
        for (int vert = 0; vert < this.verticesCount; ++vert)
        {
            int var2 = this.verticesZ[vert];
            this.verticesZ[vert] = this.verticesX[vert];
            this.verticesX[vert] = -var2;
        }

        this.resetBounds();
        invalidate();
    }

    @Override
    public int getXYZMag() {
        return diagonal2DAboveOrigin;
    }

    @Override
    public boolean isClickable() {
        return aBoolean1618;
    }

    @Override
    public void interpolateFrames(RSFrames frames, int frameId, RSFrames nextFrames, int nextFrameId, int interval, int intervalCount) {

    }

    @Override
    public int[] getVertexNormalsX() {
//        if(vertexNormalsX == null)
        return getVerticesX();
//        return vertexNormalsX;
    }

    @Override
    public void setVertexNormalsX(int[] vertexNormalsX) {
//        this.vertexNormalsX = vertexNormalsX;
    }

    @Override
    public int[] getVertexNormalsY() {
//        if(vertexNormalsY == null)
        return getVerticesY();
//        return vertexNormalsY;
    }

    @Override
    public void setVertexNormalsY(int[] vertexNormalsY) {
//        this.vertexNormalsY = vertexNormalsY;
    }

    @Override
    public int[] getVertexNormalsZ() {
//        if(vertexNormalsZ == null)
        return getVerticesZ();
//        return vertexNormalsZ;
    }

    @Override
    public void setVertexNormalsZ(int[] vertexNormalsZ) {
//        this.vertexNormalsZ = vertexNormalsZ;
    }

    @Override
    public byte getOverrideAmount() {
        return 0;
    }

    @Override
    public byte getOverrideHue() {
        return 0;
    }

    @Override
    public byte getOverrideSaturation() {
        return 0;
    }

    @Override
    public byte getOverrideLuminance() {
        return 0;
    }

    HashMap<Integer, net.runelite.api.AABB> aabb = new HashMap<Integer, net.runelite.api.AABB>();
    @Override
    public HashMap<Integer, net.runelite.api.AABB> getAABBMap() {
        return aabb;
    }

    @Override
    public Shape getConvexHull(int localX, int localY, int orientation, int tileHeight) {
        int[] x2d = new int[getVerticesCount()];
        int[] y2d = new int[getVerticesCount()];

        Perspective.modelToCanvas(Client.instance, getVerticesCount(), localX, localY, tileHeight, orientation, getVerticesX(), getVerticesZ(), getVerticesY(), x2d, y2d);

        return Jarvis.convexHull(x2d, y2d);
    }

    @Override
    public Polygon getConvexHullPoly(int localX, int localY, int orientation)
    {
        List<Vertex> vertices = getVertices();

        // rotate vertices
        for (int i = 0; i < vertices.size(); ++i)
        {
            Vertex v = vertices.get(i);
            vertices.set(i, v.rotate(orientation));
        }

        List<net.runelite.api.Point> points = new ArrayList<net.runelite.api.Point>();

        for (Vertex v : vertices)
        {
            // Compute canvas location of vertex
            net.runelite.api.Point p = Perspective.worldToCanvas(Client.instance,
                    localX - v.getX(),
                    localY - v.getZ(),
                    -v.getY());
            if (p != null)
            {
                points.add(p);
            }
        }

        // Run Jarvis march algorithm
        points = Jarvis.convexHull(points);
        if (points == null)
        {
            return null;
        }

        // Convert to a polygon
        Polygon p = new Polygon();
        for (Point point : points)
        {
            p.addPoint(point.getX(), point.getY());
        }

        return p;
    }

    @Override
    public int getBottomY() {
        return maxY;
    }

    @Override
    public void drawFace(int face) {

    }

//    private void vertexNormals()
//    {
//
//        if (vertexNormalsX == null)
//        {
//            int verticesCount = getVerticesCount();
//
//            vertexNormalsX = new int[verticesCount];
//            vertexNormalsY = new int[verticesCount];
//            vertexNormalsZ = new int[verticesCount];
//
//            int[] trianglesX = getFaceIndices1();
//            int[] trianglesY = getFaceIndices2();
//            int[] trianglesZ = getFaceIndices3();
//            int[] verticesX = getVerticesX();
//            int[] verticesY = getVerticesY();
//            int[] verticesZ = getVerticesZ();
//
//            for (int i = 0; i < numberOfTriangleFaces; ++i)
//            {
//                int var9 = trianglesX[i];
//                int var10 = trianglesY[i];
//                int var11 = trianglesZ[i];
//
//                int var12 = verticesX[var10] - verticesX[var9];
//                int var13 = verticesY[var10] - verticesY[var9];
//                int var14 = verticesZ[var10] - verticesZ[var9];
//                int var15 = verticesX[var11] - verticesX[var9];
//                int var16 = verticesY[var11] - verticesY[var9];
//                int var17 = verticesZ[var11] - verticesZ[var9];
//
//                int var18 = var13 * var17 - var16 * var14;
//                int var19 = var14 * var15 - var17 * var12;
//
//                int var20;
//                for (var20 = var12 * var16 - var15 * var13; var18 > 8192 || var19 > 8192 || var20 > 8192 || var18 < -8192 || var19 < -8192 || var20 < -8192; var20 >>= 1)
//                {
//                    var18 >>= 1;
//                    var19 >>= 1;
//                }
//
//                int var21 = (int) Math.sqrt(var18 * var18 + var19 * var19 + var20 * var20);
//                if (var21 <= 0)
//                {
//                    var21 = 1;
//                }
//
//                var18 = var18 * 256 / var21;
//                var19 = var19 * 256 / var21;
//                var20 = var20 * 256 / var21;
//
//                vertexNormalsX[var9] += var18;
//                vertexNormalsY[var9] += var19;
//                vertexNormalsZ[var9] += var20;
//
//                vertexNormalsX[var10] += var18;
//                vertexNormalsY[var10] += var19;
//                vertexNormalsZ[var10] += var20;
//
//                vertexNormalsX[var11] += var18;
//                vertexNormalsY[var11] += var19;
//                vertexNormalsZ[var11] += var20;
//            }
//        }
//    }

    public int lastOrientation = -1;

    @Override
    public int getLastOrientation() {
        return lastOrientation;
    }

    @Override
    public AABB getAABB(int orientation) {
        calculateExtreme(orientation);
        lastOrientation = orientation;
        return (AABB) getAABBMap().get(lastOrientation);
    }

    @Override
    public void calculateBoundingBox(int orientation) {

    }

    @Override
    public byte[] getTextureFaces() {
        return new byte[0];
    }

    @Override
    public int[] getTexIndices1() {
        return new int[0];
    }

    @Override
    public int[] getTexIndices2() {
        return new int[0];
    }

    @Override
    public int[] getTexIndices3() {
        return new int[0];
    }
}