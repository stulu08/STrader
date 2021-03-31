package de.stulu.strader;

import de.stulu.strader.util.Trader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class DamageEvent implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        for(Entity trader : main.instance.spawnedTraders.AList){
            if(trader == event.getEntity() || trader == event.getDamager()){
                event.setCancelled(true);
                return;
            }
        }
        event.setCancelled(false);
    }
    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        for(Entity trader : main.instance.spawnedTraders.AList){
            if(trader == event.getRightClicked()){
                player.openInventory(main.instance.configs.traders.findB(main.instance.spawnedTraders.findB(trader)).inventory);
                event.setCancelled(true);
                return;
            }
        }
        event.setCancelled(false);
    }
}
