package online.paescape.util;

/**
 * @author <a href="https://rune-server.org/members/294280-era_/">Era</a> || 2/1/2024 - 2:01 PM
 * @Discord <a href="https://discordapp.com/users/232266021413584896">era_rsps</a>
 */
public class TaskUtils {

    public static void sleep(long wait) {
        if (wait > 0L) {
            if (0L == wait % 10L) {

                try {
                    Thread.sleep(1L);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

        }
    }

}
