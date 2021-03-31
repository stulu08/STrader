package de.stulu.strader.commands;

import de.stulu.strader.main;
import de.stulu.strader.util.Trader;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.Console;

public class spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player && args.length == 2){
            Player player = (Player)sender;
            if(main.instance.configs.traders.containsA(args[0])){
                try{
                    Trader trader = main.instance.configs.traders.findB(args[0]);
                    Location loc = player.getTargetBlockExact(12).getLocation();
                    if(trader == null){
                        player.sendMessage(main.getPrefix() + ChatColor.RED + "Trader is null");
                        return false;
                    }else if(loc == null){
                        player.sendMessage(main.getPrefix() + ChatColor.RED + "Location is null");
                        return false;
                    }
                    loc.setY(loc.getY() + 1);
                    loc.setYaw(player.getLocation().getYaw());
                    Entity ent = trader.spawn(loc);
                    if(ent == null)
                        player.sendMessage("null");
                    main.instance.spawnedTraders.add(ent, args[0]);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ent.setVelocity(new Vector(0,0,0));
                        }
                    }.runTaskTimerAsynchronously(main.instance, 0, 0);
                    player.sendMessage(main.getPrefix() + "Trader was spawned");
                    if(args[1].equalsIgnoreCase("true")){
                        main.instance.configs.default_traders_config.set(args[0],loc.getBlockX() + "|" + loc.getBlockY() + "|" + loc.getBlockZ() + "|" + loc.getWorld().getName());
                    }
                } catch(Exception exception){
                    player.sendMessage(main.getPrefix() + ChatColor.RED + "Error trader couldn't be spawned! ->" + exception.getMessage());
                }
            }else{
                player.sendMessage("Trader was not Found or argument missing. Usage: /traderspawn <trader_name> <add to default(true|false)> | Trader List");
                for(String trader : main.instance.configs.traders.AList){
                    player.sendMessage(trader);
                }
            }

        }
        return false;
    }
}
