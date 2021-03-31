package de.stulu.strader.util;

import de.stulu.strader.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SellAble {
    public ItemStack item;
    public double buyPrice;
    public double sellPrice;
    public boolean canBuy = true;
    public boolean canSell = true;
    public SellAble(double buyPrice, double sellPrice, ItemStack itemStack){
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.item = itemStack;

        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Buy" + ChatColor.WHITE + ":" + " " + buyPrice + "$",ChatColor.GREEN + "Sell" + ChatColor.WHITE + ":" + " " + sellPrice + "$"));
        item.setItemMeta(meta);
    }
    public SellAble(double buyPrice, double sellPrice, ItemStack itemStack,boolean canBuy, boolean canSell){
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.item = itemStack;
        this.canBuy = canBuy;
        this.canSell = canSell;

        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Buy" + ChatColor.WHITE + ":" + " " + buyPrice + "$",ChatColor.GREEN + "Sell" + ChatColor.WHITE + ":" + " " + sellPrice + "$"));
        item.setItemMeta(meta);
    }
    public boolean buy(Player p){
        ItemStack item = new ItemStack(this.item.getType(),this.item.getAmount());
        if(p.hasPermission("strader.buy")){
            if(main.removeMoney(p,buyPrice)){
                p.getInventory().addItem(item);
                return true;
            }
        }
        return false;
    }
    public boolean sell(Player p){
        ItemStack item = new ItemStack(this.item.getType(),this.item.getAmount());
        if(p.hasPermission("strader.sell")){
            if(p.getInventory().containsAtLeast(item, item.getAmount())){
                List<ItemStack> content = new ArrayList<ItemStack>();
                boolean found = false;
                for(ItemStack itemStack : p.getInventory().getContents()){
                    if(itemStack != null && !found){
                        if(itemStack.getType() == item.getType()){
                            itemStack.setAmount(itemStack.getAmount() - item.getAmount());
                            main.addMoney(p,sellPrice);
                            found = true;
                        }
                    }
                    content.add(itemStack);
                }
                p.getInventory().setContents(content.toArray(new ItemStack[0]));
                return found;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "SellAble{" +
                "item=" + item.getType().name() +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", canBuy=" + canBuy +
                ", canSell=" + canSell +
                '}';
    }
}
