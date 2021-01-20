package me.Lozke.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.Lozke.RiftsCore;

public class PacketParticleListener {
    public PacketParticleListener(RiftsCore plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_PARTICLES) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer container = event.getPacket();
                        switch (container.getNewParticles().read(0).getParticle()) {
                            case DAMAGE_INDICATOR:
                            case SWEEP_ATTACK:
                                event.setCancelled(true);
                        }
                    }
                }
        );
    }
}
