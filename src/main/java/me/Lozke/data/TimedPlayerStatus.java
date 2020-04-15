/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.data;

import me.Lozke.utils.Text;

public enum TimedPlayerStatus {
    MOB_COMBAT(5, true, Text.colorize("&cMob Combat"), Text.colorize("&a&lMob Combat Ended")),
    PLAYER_COMBAT(20, true, Text.colorize("&cPlayer Combat"), Text.colorize("&a&lPlayer Combat Ended"));

    public static final String timePrefix = " [";
    public static final String timeSuffix = "s]";
    public static final String spacer = Text.colorize(" &0&l● ");

    private final int seconds;
    private final boolean extendable;
    private final String message;
    private final String endMessage;

    TimedPlayerStatus(int seconds, boolean extendable, String message, String endMessage) {
        this.seconds = seconds;
        this.extendable = extendable;
        this.message = message;
        this.endMessage = endMessage;
    }

    public int getSeconds() {
        return seconds;
    }

    public boolean isExtendable() {
        return this.extendable;
    }

    public String getMessage() {
        return message;
    }

    public String getEndMessage() {
        return endMessage;
    }
}
