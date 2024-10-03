package online.paescape.media.particle;

import java.util.HashMap;
import java.util.Map;

public class ParticleAttachment {

    private static final Map<Integer, int[][]> attachments = new HashMap<>();

    static {
        /*Completionist cape*/
        //attachments.put(65297, new int[][] { { 494, 0 }, { 488, 0 }, { 485, 0 }, { 476, 0 }, { 482, 0 }, { 479, 0 }, { 491, 0 } });
        //attachments.put(65316, new int[][] { { 494, 0 }, { 488, 0 }, { 485, 0 }, { 476, 0 }, { 482, 0 }, { 479, 0 }, { 491, 0 } });

        /*Trimmed Completionist Cape*/
        //attachments.put(65295, new int[][] { { 494, 1 }, { 488, 1 }, { 485, 1 }, { 476, 1 }, { 482, 1 }, { 479, 1 }, { 491, 1 } });
        //attachments.put(65328, new int[][] { { 494, 1 }, { 488, 1 }, { 485, 1 }, { 476, 1 }, { 482, 1 }, { 479, 1 }, { 491, 1 } });

        /*Master Dung. Cape*/
        //attachments.put(59885, new int[][] { { 120, 2 }, { 121, 2 }, { 122, 2 }, { 388, 2 }, { 386, 2 }, { 385, 2 }, { 390, 2 }, { 377, 2 }, { 376, 2 }, { 380, 2 }, { 384, 2 }, { 152, 2 }, { 382, 2 }, { 383, 2 }, { 379, 2 }, { 378, 2 } });
        //attachments.put(59885, new int[][] { { 120, 2 }, { 121, 2 }, { 122, 2 }, { 388, 2 }, { 386, 2 }, { 385, 2 }, { 390, 2 }, { 377, 2 }, { 376, 2 }, { 380, 2 }, { 384, 2 }, { 152, 2 }, { 382, 2 }, { 383, 2 }, { 379, 2 }, { 378, 2 } });

    }

    public static int[][] getAttachments(int model) {
        return attachments.get(model);
    }
}