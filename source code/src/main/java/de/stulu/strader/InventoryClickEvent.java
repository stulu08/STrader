package de.stulu.strader;

import de.stulu.strader.util.SellAble;
import de.stulu.strader.util.Trader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryClickEvent implements Listener {
    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event){
        InventoryView view = event.getView();
        if(view.getPlayer().hasPermission("strader.acces") && view.getPlayer() instanceof Player){
            Player p = Bukkit.getPlayer(view.getPlayer().getName());
            for (Trader trader : main.instance.configs.traders.BList){
                if(event.getInventory() == trader.inventory){
                    event.setCancelled(true);
                    if(event.getClickedInventory() == trader.inventory) {
                        int slot = event.getSlot();
                        if (!trader.items.containsA(slot))
                            return;
                        SellAble sellAble = trader.items.findB(slot);
                        switch (event.getClick()) {
                            case LEFT:
                                if (sellAble.buy(p)) {
                                    p.sendMessage(main.getPrefix() + "Item bought");
                                } else {
                                    p.sendMessage(main.getPrefix() + "Item could not be bought");
                                }
                                break;
                            case RIGHT:
                                if (sellAble.sell(p)) {
                                    p.sendMessage(main.getPrefix() + "Item soled");
                                } else {
                                    p.sendMessage(main.getPrefix() + "Item could not be soled");
                                }
                                break;
                        }
                    }
                    return;
                }
            }
        }
        event.setCancelled(false);
    }
}
