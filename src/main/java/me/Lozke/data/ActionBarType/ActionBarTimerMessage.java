package me.Lozke.data.ActionBarType;

public class ActionBarTimerMessage extends ActionBarMessage {

    protected boolean showTime;

    public ActionBarTimerMessage(String id, String message, int time, boolean showTime) {
        super(id, message, time);
        this.showTime = showTime;
    }

    @Override
    public String getMessage() {
        if (showTime) {
            return message + " [" + getTime() + "]";
        }
        return message;
    }

    @Override
    public void overwrite(ActionBarMessage message) {
        super.overwrite(message);
        if (message instanceof ActionBarTimerMessage) {
            this.showTime = ((ActionBarTimerMessage) message).showTime;
        }
    }

    public void showTime(boolean bool) {
        this.showTime = bool;
    }
}
