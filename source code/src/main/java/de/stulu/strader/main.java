package de.stulu.strader;

import de.stulu.strader.commands.spawn;
import de.stulu.strader.commands.spawnDefaultTraders;
import de.stulu.strader.util.ConfigHandler;
import de.stulu.strader.util.SMap;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Objects;
import java.util.logging.Logger;

public final class main extends JavaPlugin {
    public static main instance = null;
    public ConfigHandler configs = new ConfigHandler();
    public static boolean debug = true;
    public SMap<Entity, String> spawnedTraders = new SMap<>();
    @Override
    public void onEnable() {
        instance = this;
        ListenerRegistration();
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        configs.create();
        debug = configs.getDebug();
        configs.handle();
        LoadCommands();
        SendConsoleMessage(getPrefix() + ChatColor.GREEN + "Stulu Trader Plugin is now enabled");
    }

    @Override
    public void onDisable() {
        for (Entity trader : spawnedTraders.AList){
            trader.remove();
        }
        SendConsoleMessage(getPrefix() + ChatColor.RED + "Stulu Trader Plugin was disabled");
    }
    private void LoadCommands(){
        Objects.requireNonNull(getCommand("traderspawn")).setExecutor(new spawn());
        Objects.requireNonNull(getCommand("spawnDefaultTraders")).setExecutor(new spawnDefaultTraders());
    }
    private void ListenerRegistration(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new DamageEvent(),this);
        pluginManager.registerEvents(new InventoryClickEvent(),this);
    }
    public static String getPrefix(){
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "STrader" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    }
    public void SendConsoleMessage(String msg){ Log().info(msg); }
    public static Logger Log(){ return Bukkit.getLogger(); }
    public static boolean hasMoney(Player p, double money){
        return getEconomy().getBalance(p) > money;
    }
    public static void addMoney(Player p, double money){
        getEconomy().depositPlayer(p,money);
    }
    public static boolean removeMoney(Player p, double money){
        if(hasMoney(p,money)){
            getEconomy().withdrawPlayer(p,money);
            return true;
        }else{
            return false;
        }
    }
    //Economy
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public static Economy getEconomy() {
        return econ;
    }
}
