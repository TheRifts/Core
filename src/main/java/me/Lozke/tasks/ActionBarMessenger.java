package me.Lozke.tasks;

import me.Lozke.data.ActionBarType.ActionBarMessage;
import me.Lozke.utils.Logger;
import me.Lozke.utils.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ActionBarMessenger extends BukkitRunnable {

    private final String delimiter = Text.colorize(" &0&l●&r ");
    private Plugin plugin;
    private HashMap<UUID, List<ActionBarMessage>> pendingMap;

    public ActionBarMessenger(Plugin plugin) {
        this.plugin = plugin;
        pendingMap = new HashMap<>();
        runTaskTimerAsynchronously(plugin, 0, 5L);
    }

    @Override
    public void run() {
        if (pendingMap.isEmpty()) {
            return;
        }

        for (Map.Entry<UUID, List<ActionBarMessage>> setEntry : pendingMap.entrySet()) {
            UUID key = setEntry.getKey();
            Player player = Bukkit.getPlayer(key);

            if (player == null) {
                pendingMap.remove(key);
                continue;
            }

            List<ActionBarMessage> messages = setEntry.getValue();

            if (messages.size() > 1) {
                StringJoiner actionBarMessage = new StringJoiner(delimiter);
                messages.sort(Collections.reverseOrder());
                pendingMap.put(key, messages);
                Iterator<ActionBarMessage> iterator = messages.iterator();
                while (iterator.hasNext()) {
                    ActionBarMessage message = iterator.next();
                    if (message == null || !messages.contains(message)) {
                        iterator.remove();
                        continue;
                    }
                    actionBarMessage.add(message.getMessage());
                    message.tick(5);
                    if (!message.isValid()) {
                        iterator.remove();
                    }
                }
                displayBar(key, actionBarMessage.toString());
            }
            else {
                ActionBarMessage message = messages.get(0);
                message.tick(5);
                if (!message.isValid()) {
                    pendingMap.remove(key);
                }
                displayBar(key, messages.get(0).getMessage());
            }
        }
    }

    /**
     * Queue up specified message to be sent to the player. Overwrites ActionBarMessage if the player already had an
     * ActionBarMessage with the same ID.
     * @param playerUUID target player UUID
     * @param message    an ActionBarMessage
     */
    public void addMessage(UUID playerUUID, ActionBarMessage message) {
        List<ActionBarMessage> messageList = pendingMap.get(playerUUID);
        if (messageList == null) {
            messageList = new ArrayList<>();
        }
        else {
            for (ActionBarMessage actionBarMessage : messageList) {
                if (actionBarMessage.getID().equalsIgnoreCase(message.getID())) {
                    actionBarMessage.overwrite(message);
                    return;
                }
            }
        }
        messageList.add(message);
        pendingMap.put(playerUUID, messageList);
    }

    /**
     * Queue up specified message to be sent to the player.
     * @param player  target player
     * @param message an ActionBarMessage
     */
    public void addMessage(Player player, ActionBarMessage message) {
        addMessage(player.getUniqueId(), message);
    }

    /**
     * This will not immediately remove the specified ActionBarMessage, instead it will be removed next time the target
     * player's actionbar attempts to update.
     * @param playerUUID target player UUID
     * @param messageID  string ID of ActionBarMessage
     */
    public void removeMessage(UUID playerUUID, String messageID) {
        List<ActionBarMessage> messages = pendingMap.get(playerUUID);
        if (messages == null || messages.isEmpty()) {
            return;
        }
        for (ActionBarMessage message : messages) {
            if (message.getID().equalsIgnoreCase(messageID)) {
                message.setTime(0);
            }
        }
    }

    /**
     * Runs {@link ActionBarMessenger#removeMessage(UUID, String)}
     * @param player    target player
     * @param messageID string ID of ActionBarMessage
     */
    public void removeMessage(Player player, String messageID) {
        removeMessage(player.getUniqueId(), messageID);
    }

    /**
     * Get ActionBarMessage object for the specified ID
     * @param playerUUID uuid of target player
     * @param messageID  id of wanted ActionBarMessage
     * @return           requested ActionBarMessage if exists, otherwise returns null.
     */
    public ActionBarMessage getMessage(UUID playerUUID, String messageID) {
        List<ActionBarMessage> messages = pendingMap.get(playerUUID);
        if (messages == null || messages.isEmpty()) {
            return null;
        }
        for (ActionBarMessage message : messages) {
            if (message.getID().equalsIgnoreCase(messageID)) {
                return message;
            }
        }
        return null;
    }

    /**
     * Get ActionBarMessage object for the specified ID
     * @param player    target player
     * @param messageID id of wanted ActionBarMessage
     * @return          requested ActionBarMessage if exists, otherwise returns null.
     */
    public ActionBarMessage getMessage(Player player, String messageID) {
        return getMessage(player.getUniqueId(), messageID);
    }

    /**
     * Send player an Action Bar on a synchronized thread
     * @param uuid    uuid of target player
     * @param message action bar message
     */
    private void displayBar(UUID uuid, String message) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.getPlayer(uuid).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize(message)));
        });
    }

    /**
     * Sends the player a static snapshot of the current actionbar to the player. This method does not update the
     * message lists or tick the actionbar messages, for thread safety.
     * @param playerUUID uuid of target player
     */
    public void forceDisplay(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            Logger.warn(plugin, "Attempted to force actionbar display for " + Bukkit.getPlayer(playerUUID) + " but they are offline");
            return;
        }

        List<ActionBarMessage> messages = pendingMap.get(playerUUID);
        if (messages == null || messages.isEmpty()) {
            Logger.warn(plugin, "Attempted to force actionbar display for " + Bukkit.getPlayer(playerUUID) + " but they had no messages");
            return;
        }

        if (messages.size() > 1) {
            StringJoiner actionBarMessage = new StringJoiner(delimiter);
            messages.sort(Collections.reverseOrder());
            for (ActionBarMessage message : messages) {
                if (message == null) {
                    continue;
                }
                actionBarMessage.add(message.getMessage());
            }
            displayBar(playerUUID, actionBarMessage.toString());
        }
        else {
            displayBar(playerUUID, messages.get(0).getMessage());
        }
    }

    /**
     * Runs {@link ActionBarMessenger#forceDisplay(UUID)}
     * @param player target player
     */
    public void forceDisplay(Player player) {
        forceDisplay(player.getUniqueId());
    }
}
