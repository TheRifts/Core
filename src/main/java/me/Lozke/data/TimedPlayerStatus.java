/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.data;

public enum TimedPlayerStatus {
    MOB_COMBAT(5, true, "&cMob Combat", "&a&lMob Combat Ended"),
    PLAYER_COMBAT(20, true, "&cPlayer Combat", "&a&lPlayer Combat Ended");


    private final int time;
    private final boolean extendable;
    private final String message;
    private final String endMessage;


    TimedPlayerStatus(int time, boolean extendable, String message, String endMessage) {
        this.time = time;
        this.extendable = extendable;
        this.message = message;
        this.endMessage = endMessage;
    }


    public int getTime() {
        return time;
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
