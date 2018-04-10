package com.legendsofesper.wac.loesmithing;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.parser.ParseException;

/**
 * Get a copy of the new, LoE specific materials for smithing testing
 *
 * @version 9-Apr-2018
 */
public class GiveOre implements CommandExecutor{
    // give player requested special ore for tetsing
    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;

            if((args.length == 1 || args.length == 2) &&
                    label.equals("giveore")){
                int amount;
                if(args.length == 1)
                    amount = 1;
                else{
                    try{
                        amount = Integer.parseInt(args[1]);
                    }catch(NumberFormatException nfe){
                        return true;
                    }
                }

                ItemStack newItem;
                String itemName;
                if(args[0].equals("andaryll")){
                    newItem = new ItemStack(Material.GOLD_INGOT, amount);
                    itemName = "Andaryll";
                }else if(args[0].equals("serasyll")){
                    newItem = new ItemStack(Material.GLOWSTONE_DUST, amount);
                    itemName = "Serasyll";
                }else if(args[0].equals("mythril")){
                    newItem = new ItemStack(Material.EMERALD, amount);
                    itemName = "Mythril";
                }else if(args[0].equals("adamantium")){
                    newItem = new ItemStack(Material.DIAMOND, amount);
                    itemName = "Adamantium";
                }else if(args[0].equals("iron")){
                    newItem = new ItemStack(Material.IRON_INGOT, amount);
                    itemName = "Iron Ingot";
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
