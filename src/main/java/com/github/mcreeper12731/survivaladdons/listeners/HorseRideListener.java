package com.github.mcreeper12731.survivaladdons.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.HashMap;
import java.util.UUID;

public class HorseRideListener implements Listener {

    private final NamespacedKey mobsKey;

    public HorseRideListener(NamespacedKey mobsKey) {
        this.mobsKey = mobsKey;
    }

    private final HashMap<UUID, Horse> riddenHorses = new HashMap<>();

    @EventHandler
    public void onHorseRide(EntityMountEvent event) {

        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getMount().getType() != EntityType.HORSE) return;

        riddenHorses.remove(player.getUniqueId());

        Horse horse = (Horse) event.getMount();

        PersistentDataContainer pdc = horse.getPersistentDataContainer();
        String value = pdc.get(mobsKey, PersistentDataType.STRING);

        if (value == null || !value.equals("pegasus")) return;

        horse.setGravity(false);
        horse.setInvulnerable(true);
        riddenHorses.put(player.getUniqueId(), horse);

    }

    @EventHandler
    public void onMoveWithHorse(PlayerMoveEvent event) {

        if (event.getFrom().equals(event.getTo())) return;

        Player player = event.getPlayer();

        if (!riddenHorses.containsKey(player.getUniqueId())) return;
        if (!player.isInsideVehicle()) return;
        if (player.getVehicle() == null || player.getVehicle().getType() != EntityType.HORSE) return;

        Vector velocity = player.getVelocity().setY(0);
        Horse horse = riddenHorses.get(player.getUniqueId());

        if (!velocity.isZero())
            velocity.normalize();

        velocity.multiply(player.getVelocity().isZero() ? 0.8 : 1);

        if (player.getVelocity().getX() != 0 && player.getVelocity().getZ() != 0)
            velocity.setY(player.getEyeLocation().getDirection().normalize().getY());

        horse.setVelocity(velocity);

    }

    private double modify(double num, double speed) {
        return (num * speed);
    }

}
