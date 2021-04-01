package de.stulu.strader.util;

import de.stulu.strader.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ConfigHandler {
    public SMap<String,Trader> traders = new SMap<String, Trader>();
    Configuration config;
    Configuration trader_config;
    public Configuration default_traders_config;
    public ConfigHandler(){

    }
    public void create(){
        config = new Configuration("config");
        config.addDefault("debug","false");
        trader_config = new Configuration("trader_config");
        default_traders_config = new Configuration("default_traders");
    }
    public void handle(){
        handleTrader();
        if(config.getBool("spawnDefaultTraders")){
            spawnDefaultTraders();
        }
    }
    public boolean getDebug(){
        return config.getBool("debug");
    }
    public void handleTrader(){
        SMap<String,String> tra = trader_config.getValuesAsString(true);
        for(int i = 0; i != tra.count(); i++){
            traders.add(tra.getA(i),new Trader(tra.getB(i)));
            main.instance.SendConsoleMessage(main.getPrefix() + tra.getA(i) + " was added to trader list");
        }
    }
    public void spawnDefaultTraders(){
        SMap<String,String> tra = default_traders_config.getValuesAsString(true);
        main.instance.SendConsoleMessage(main.getPrefix() + "Spawning default traders-----------------------");
        for (String name : tra.AList){
            try{
                String[] values = tra.findB(name).split("\\|");
                int x = Integer.parseInt(values[0]);
                int y = Integer.parseInt(values[1]);
                int z = Integer.parseInt(values[2]);
                String world = values[3];
                Trader trader = main.instance.configs.traders.findB(name);
                Location loc = new Location(Bukkit.getWorld(world),x,y,z);
                if(trader == null){
                    main.instance.SendConsoleMessage(main.getPrefix() + ChatColor.RED + "Trader is null");
                    continue;
                }else if(loc == null){
                    main.instance.SendConsoleMessage(main.getPrefix() + ChatColor.RED + "Location is null");
                    continue;
                }
                Entity ent = trader.spawn(loc);
                if(ent == null)
                    main.instance.SendConsoleMessage("null");
                main.instance.spawnedTraders.add(ent, name);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ent.setVelocity(new Vector(0,0,0));
                    }
                }.runTaskTimerAsynchronously(main.instance, 0, 0);
                main.instance.SendConsoleMessage(main.getPrefix() + "Trader was spawned");
            } catch(Exception exception){
                main.instance.SendConsoleMessage(main.getPrefix() + ChatColor.RED + "Error trader couldn't be spawned! ->" + exception.getMessage());
            }
        }
        main.instance.SendConsoleMessage(main.getPrefix() + "Spawning end-----------------------");
    }
}
