package de.stulu.strader.util;

import de.stulu.strader.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sun.security.util.Debug;

import java.io.File;


public class Trader {
    public EntityType entityType;
    public Inventory inventory;
    public SMap<Integer, SellAble> items = new SMap<Integer, SellAble>();
    public String name;
    public String InvName;
    public Entity entity;
    Configuration invFile;
    Configuration file;
    public Trader(String file){
        if(!new File("./plugins/Stulu-Trader/" + file + ".yml").exists()){
            main.instance.SendConsoleMessage(main.getPrefix() + "Trader File for " + file + " couldn't not be found");
            return;
        }else{
            if(main.debug)
                main.instance.SendConsoleMessage(main.getPrefix() + "Trader File for " + file + " was found");
        }
        this.file = new Configuration(file);
        InvName = (ChatColor.valueOf(this.file.get("Namens_Farbe").toString()) + this.file.get("Name").toString());
        if(main.debug){
            main.instance.SendConsoleMessage(main.getPrefix() + "Inventory name for " + file + " was registered as:" + InvName);
        }
        inventory = Bukkit.createInventory(null,Integer.parseInt(this.file.get("Slots")+""), InvName);
        if(main.debug){
            main.instance.SendConsoleMessage(main.getPrefix() + "Inventory for " + file + " was registered as:" + inventory.toString());
        }
        entityType = EntityType.valueOf(this.file.get("Entity_Type")+"");
        if(main.debug){
            main.instance.SendConsoleMessage(main.getPrefix() + "Entitytype for " + file + " was registered as:" + entityType.name());
        }
        loadInventoryFile(this.file.get("Inventory").toString());
    }
    public void addItem(int slot, SellAble item){
        inventory.setItem(slot,item.item);
        items.put(slot,item);
        if(main.debug){
            main.instance.SendConsoleMessage(main.getPrefix() + "Item for " + file.name + " was registered from "+ invFile.name + " as:" + item.toString() + "| slot:" + items.findA(item));
        }
    }
    public Entity spawn(Location location) {
        if(entityType == null)
            entityType = EntityType.VILLAGER;
        Entity ent = location.getWorld().spawnEntity(location, entityType);
        ent.setCustomNameVisible(true);
        ent.setCustomName(InvName);
        ent.setInvulnerable(true);
        entity = ent;
        return ent;
    }
    public void loadInventoryFile(String name){
        if(!new File("./plugins/Stulu-Trader/" + name + ".yml").exists()){
            main.instance.SendConsoleMessage(main.getPrefix() + "Trader Inventory File for " + name + " couldn't not be found");
            return;
        }else{
            if(main.debug)
                main.instance.SendConsoleMessage(main.getPrefix() + "Trader Inventory File for " + name + " was found");
        }
        invFile = new Configuration(name);
        SMap<String,String> map = invFile.getValuesAsString(true);
        for(int i = 0; i != map.count();i++){
            String[] arr = map.getB(i).split("\\|");
            Material material = Material.valueOf(arr[0]);
            double buy = Double.parseDouble(arr[1]);
            double sell = Double.parseDouble(arr[2]);
            int stack = Integer.parseInt(arr[3]);
            if(material == null)
                continue;
            addItem(Integer.parseInt(map.getA(i)),new SellAble(buy,sell,new ItemStack(material,stack)));
        }

    }
}
