package online.paescape.cache.media.sprite;

import online.paescape.Client;
import online.paescape.net.requester.OnDemandFetcher;

// requires bsp 3.0 to function
public class SpriteCache {

    public static Sprite[] spriteCache;
    public static Sprite[] spriteLink;
    private static OnDemandFetcher onDemandFetcher;
    private static Client c;

    public static void initialise(int total, OnDemandFetcher onDemandFetcher_) {
        spriteCache = new Sprite[total + 100];
        spriteLink = new Sprite[total + 100];
        onDemandFetcher = onDemandFetcher_;
    }

    public static void loadSprite(final int spriteId, final Sprite sprite, boolean priority) {
        if (sprite != null) {
            spriteLink[spriteId] = sprite;
        }
        if (spriteCache[spriteId] == null) {
            onDemandFetcher.requestFileData(Client.IMAGE_IDX - 1, spriteId);
        }
        //if(priority)
        //	c.processOnDemandQueue();

    }

    public static void loadSprite(final int spriteId, final Sprite sprite) {
        loadSprite(spriteId, sprite, false);
    }

    public static void fetchIfNeeded(int spriteId) {

        if (spriteCache[spriteId] != null)
            return;
        onDemandFetcher.requestFileData(Client.IMAGE_IDX - 1, spriteId);
    }

    public static Sprite get(int spriteId) {
        fetchIfNeeded(spriteId);
        return spriteCache[spriteId];
    }

    public static void load(Client c) {
        SpriteCache.c = c;
        preloadLowPriorityImages();
    }

    public static void preloadLowPriorityImages() {
        loadSprite(678, c.search);//c.search = new Sprite("1");
        loadSprite(679, c.Search);//c.Search = new Sprite("2");
        loadSprite(27, c.SubmitBuy);//("Interfaces/GE/SubmitBuy");
        loadSprite(28, c.SubmitSell);//("Interfaces/GE/SubmitSell");
        loadSprite(29, c.Buy);//("Interfaces/GE/buySubmit");
        loadSprite(30, c.Sell);//("Interfaces/GE/sellSubmit");
        loadSprite(31, c.loadingPleaseWait);//loadingPleaseWait = new Sprite("loadingPleaseWait");
        loadSprite(32, c.reestablish);//reestablish = new Sprite("reestablish");
        /* Custom sprite unpacking */
        loadSprite(33, null);//HPBarFull = new Sprite("Player/HP 0");
        loadSprite(34, null);//HPBarEmpty = new Sprite("Player/HP 1");
        loadSprite(35, null);//HPBarBigEmpty = new Sprite("Player/HP 2");
        loadSprite(47, c.magicAuto);//magicAuto = new Sprite("Player/magicauto");
		/*for (int i = 0; i <= 4; i++) {
			loadSprite(48+i, c.LOGOUT[i]);//LOGOUT[i] = new Sprite("Frame/X " + i);
		}*/
        for (int i = 0; i <= 4; i++) {
            c.ADVISOR[i] = new Sprite("Gameframe/A " + i);
        }
        /* End custom sprites */
        //loadSprite(80, c.newMapBack);//newMapBack = new Sprite("Frame/Mapback");
        for (int i4 = 0; i4 < 33; i4++)
            loadSprite(81 + i4, c.hitMark[i4]);//hitMark[i4] = new Sprite("/Hitmarks/hit " + i4);
        for (int i4 = 0; i4 < 6; i4++)
            loadSprite(114 + i4, c.hitIcon[i4]);//hitIcon[i4] = new Sprite("/Hitmarks/icon " + i4);
        loadSprite(681, null);//modIcons[l4] = new Sprite("modicons " + l4);
    }
}
