package com.legendsofesper.wac.loesmithing;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by liveastro on 4/5/2018.
 */
public class RenameCommand implements CommandExecutor{
    // give player requested special ore for tetsing
    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 1 && label.equals("giveore")){
                ItemStack newItem;
                String itemName;
                if(args[0].equals("andaryll")){
                    newItem = new ItemStack(Material.NETHER_BRICK_ITEM);
                    itemName = "Andaryll";
                }else if(args[0].equals("serasyll")){
                    newItem = new ItemStack(Material.REDSTONE);
                    itemName = "Serasyll";
                }else if(args[0].equals("mythril")){
                    newItem = new ItemStack(Material.EMERALD);
                    itemName = "Mythril";
                }else if(args[0].equals("adamantium")){
                    newItem = new ItemStack(Material.DIAMOND);
                    itemName = "Adamantium";
                }else{
                    return true;
                }

                ItemMeta newMeta = newItem.getItemMeta();
                newMeta.setLocalizedName(itemName);

                newItem.setItemMeta(newMeta);

                player.getInventory().addItem(newItem);
            }
        }

        return true;
    }
}
