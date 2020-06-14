package me.Lozke.tasks;

import me.Lozke.utils.Logger;
import me.Lozke.utils.Text;
import me.Lozke.data.ActionBarMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ActionBarMessenger {

    public static final String delimiter = Text.colorize(" &0&l● ");

    private ConcurrentHashMap<UUID, List<ActionBarMessageTickTask>> queuedMessages;


    public ActionBarMessenger() {
        queuedMessages = new ConcurrentHashMap<>();
    }


    void addMessage(ActionBarMessageTickTask messageTickTask) {
        UUID recipient = messageTickTask.getRecipient();
        if (queuedMessages.containsKey(recipient)) {
            queuedMessages.get(recipient).add(messageTickTask);
        }
        else {
            List<ActionBarMessageTickTask> messages = new ArrayList<>();
            messages.add(messageTickTask);
            queuedMessages.put(recipient, messages);
        }
    }

    void displayMessage(UUID uuid) {
        removeInvalidMessages(uuid);
        if (queuedMessages.containsKey(uuid)) {
            Player player = Bukkit.getServer().getPlayer(uuid);
            if (player == null) {
                Logger.broadcast("Attempted to send message to offline/null player");
                return;
            }

            //Sort messages
            List<ActionBarMessageTickTask> messageTickTasks = queuedMessages.get(uuid);
            List<ActionBarMessage> messages = new ArrayList<>();
            for (ActionBarMessageTickTask messageTickTask : messageTickTasks) {
                messages.add(messageTickTask.getMessage());
            }
            messages.sort(Collections.reverseOrder());

            //Build single message
            StringJoiner actionBarMessage = new StringJoiner(delimiter);
            for (ActionBarMessage message: messages) {
                actionBarMessage.add(message.getMessage());
            }

            //Save the messages in a sorted order
            queuedMessages.put(uuid, messageTickTasks);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize(actionBarMessage.toString())));
        }
    }
    private void removeInvalidUUID(UUID uuid) {
        if (queuedMessages.containsKey(uuid) && queuedMessages.get(uuid).isEmpty()) {
            queuedMessages.remove(uuid);
        }
    }
    private void removeInvalidMessages(UUID uuid) {
        List<ActionBarMessageTickTask> messageTickTasks= queuedMessages.get(uuid);
        if (messageTickTasks == null) {
            return;
        }

        queuedMessages.get(uuid).removeIf(this::isInvalidMessage);

        removeInvalidUUID(uuid);
    }
    private boolean isInvalidMessage(ActionBarMessageTickTask messageTickTask) {
        return messageTickTask.isInvalid();
    }
}
