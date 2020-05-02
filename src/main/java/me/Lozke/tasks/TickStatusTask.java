/*
 * Created by Noah Pritchard on 4/25/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import me.Lozke.data.ActionBarMessage;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.tasks.actionbar.ActionBarMessageTickTask;

public class TickStatusTask extends ActionBarMessageTickTask {
    private static int weight = 1000;

    TimedPlayerStatus status;
    boolean extended = false;


    public TickStatusTask(TimedPlayerStatus status, AutisticPlayer autisticPlayer) {
        super(new ActionBarMessage(status.getMessage(), weight, status.getTime(), true), autisticPlayer.getUUID());
        this.status = status;
    }


    @Override
    public void cancel() {
        AutisticPlayer autisticPlayer = FallingAutism.getPluginInstance().getPlayerManager().getAutisticPlayer(getRecipient());
        if (!extended) {
            new ActionBarMessageTickTask(new ActionBarMessage(status.getEndMessage(), weight), autisticPlayer.getUUID());
        }
        autisticPlayer.getStatusMap().remove(status);
        super.cancel();
    }

    public void setExtended() {
        extended = true;
    }
}
