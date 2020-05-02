package me.Lozke.tasks.actionbar;

import me.Lozke.FallingAutism;
import me.Lozke.data.ActionBarMessage;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static org.bukkit.Bukkit.getScheduler;

public class ActionBarMessageTickTask extends BukkitRunnable {

    private FallingAutism plugin;

    private ActionBarMessage message;
    private UUID recipient;


    public ActionBarMessageTickTask(ActionBarMessage message, UUID recipient) {
        this.plugin = FallingAutism.getPluginInstance();
        this.message = message;
        this.recipient = recipient;

        plugin.getActionBarMessenger().addMessage(this);

        if(isInvalid()) {
            return;
        }

        //Send initial message
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getActionBarMessenger().displayMessage(recipient);
            }
        }.runTask(FallingAutism.getPluginInstance());
        //Send follow-up messages
        runTaskTimerAsynchronously(plugin, 20, 20);
    }


    public ActionBarMessage getMessage() {
        return message;
    }
    public UUID getRecipient() {
        return recipient;
    }

    @Override
    public void run() {
        //Tick message down immediately to send broadcast
        int time = message.getTime() - 1;
        message.setTime(time);

        //End task if message is expired
        if (time <= 0) {
            this.cancel();
            return;
        }

        //TODO try to get more into the asynchronous portion later
        getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.getActionBarMessenger().displayMessage(recipient);
            }
        });
    }

    @Override
    public void cancel() {
        message.setTime(0);
        super.cancel();
    }

    public boolean isInvalid() {
        return message.getTime() <= 0;
    }
}
