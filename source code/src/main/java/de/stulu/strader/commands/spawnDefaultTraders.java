package de.stulu.strader.commands;

import de.stulu.strader.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class spawnDefaultTraders implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        main.instance.configs.spawnDefaultTraders();
        return false;
    }
}
