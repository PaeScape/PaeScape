package online.paescape.media;

import org.apache.commons.lang3.StringUtils;

public class EmojiHandler {

    public static int checkEmoji(String message) {

        if (StringUtils.isNumeric(message)) {
            return Integer.parseInt(message);
        }

        switch (message.toLowerCase()) {

            case "heart":
                return 26;
            case "smile":
                return 24;
            case "happy":
                return 25;
            case "sad":
                return 28;
            case "coins":
                return 17;

        }

        return -1;
    }

}
