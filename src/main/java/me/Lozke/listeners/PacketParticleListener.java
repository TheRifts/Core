package me.Lozke.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.Lozke.AgorianRifts;
import org.bukkit.Particle;

public class PacketParticleListener {
    public PacketParticleListener(AgorianRifts plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_PARTICLES) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer container = event.getPacket();
                        if (container.getNewParticles().read(0).getParticle() == Particle.DAMAGE_INDICATOR) {
                            event.setCancelled(true);
                        }
                    }
                }
        );
    }
}
