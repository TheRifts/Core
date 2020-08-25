package me.Lozke.data.ActionBarType;

public class ActionBarToggleMessage extends ActionBarMessage {

    protected boolean toggle = true;
    protected String alt;

    public ActionBarToggleMessage(String id, String def, String alt, int time) {
        super(id, def, time);
        this.alt = alt;
    }

    public void toggle() {
        toggle = !toggle;
    }

    @Override
    public String getMessage() {
        if (toggle) return message;
        else return alt;
    }

    @Override
    public void overwrite(ActionBarMessage message) {
        super.overwrite(message);
        if (message instanceof ActionBarToggleMessage) {
            this.toggle = ((ActionBarToggleMessage) message).toggle;
            this.alt = ((ActionBarToggleMessage) message).alt;
        }
    }
}
