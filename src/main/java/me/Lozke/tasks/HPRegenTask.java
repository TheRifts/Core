/*
 * Created by Noah Pritchard on 4/14/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HPRegenTask extends BukkitRunnable {

    private AutisticPlayer autisticPlayer;


    public HPRegenTask(AutisticPlayer autisticPlayer) {
        this.autisticPlayer = autisticPlayer;
        runTaskTimerAsynchronously(FallingAutism.getPluginInstance(), 0L, 20L);
    }


    @Override
    public void run() {
        Player player = Bukkit.getPlayer(autisticPlayer.getUUID());
        if (player != null) {
            double health = player.getHealth();
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            if(health != maxHealth) {
                health+= autisticPlayer.getHpRegen();
                //Prevent health overflow
                if(health > maxHealth) {
                    health = maxHealth;
                }
            }

            player.setHealth(health);
        }
    }
}
